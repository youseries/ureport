/**
 * Created by Jacky.Gao on 2017-03-17.
 */
import Chart from "chart.js";
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import './form/external/bootstrap-datetimepicker.css';
import './form/external/bootstrap-datetimepicker.js';
import {getParameter,pointToMM,showLoading,hideLoading} from './Utils.js';
import {alert} from './MsgBox.js';
import PDFPrintDialog from './dialog/PDFPrintDialog.js';
import defaultI18nJsonData from './i18n/preview.json';
import en18nJsonData from './i18n/preview_en.json';

$(document).ready(function(){
    let language=window.navigator.language || window.navigator.browserLanguage;
    if(!language){
        language='zh-cn';
    }
    language=language.toLowerCase();
    window.i18n=defaultI18nJsonData;
    if(language!=='zh-cn'){
        window.i18n=en18nJsonData;
    }
    const urlParameters=window.location.search;
    $('.ureport-print').click(function(){
        const url=window._server+'/preview/loadPrintPages'+urlParameters;
        showLoading();
        $.ajax({
            url,
            type:'POST',
            success:function(result){
                $.get(window._server+'/preview/loadPagePaper'+urlParameters,function(paper){
                    hideLoading();
                    const html=result.html;
                    const iFrame=window.frames['_print_frame'];
                    let styles=`<style type="text/css">`;
                    styles+=buildPrintStyle(paper);
                    styles+=$('#_ureport_table_style').html();
                    styles+=`</style>`;
                    $(iFrame.document.body).html(styles+html);
                    iFrame.window.focus();
                    iFrame.window.print();
                });
            },
            error:function(){
                hideLoading();
                alert("服务端出错！");
            }
        });
    });
    let directPrintPdf=false,index=0;
    const pdfPrintDialog=new PDFPrintDialog();
    $(`.ureport-pdf-print`).click(function(){
        $.get(window._server+'/preview/loadPagePaper'+urlParameters,function(paper){
            pdfPrintDialog.show(paper);
        });
    });
    $(`.ureport-pdf-direct-print`).click(function(){
        showLoading();
        const url=window._server+'/pdf/show'+urlParameters+`&_i=${index++}`;
        const iframe=window.frames['_print_pdf_frame'];
        if(!directPrintPdf){
            directPrintPdf=true;
            $("iframe[name='_print_pdf_frame']").on("load",function(){
                hideLoading();
                iframe.window.focus();
                iframe.window.print();
            });
        }
        iframe.window.focus();
        iframe.location.href=url;
    });
    $(`.ureport-export-pdf`).click(function(){
        const url=window._server+'/pdf'+urlParameters;
        window.open(url,'_blank');
    });
    $(`.ureport-export-word`).click(function(){
        const url=window._server+'/word'+urlParameters;
        window.open(url,'_blank');
    });
    $(`.ureport-export-excel`).click(function(){
        const url=window._server+'/excel'+urlParameters;
        window.open(url,'_blank');
    });
    $(`.ureport-export-excel-paging`).click(function(){
        const url=window._server+'/excel/paging'+urlParameters;
        window.open(url,'_blank');
    });
    $(`.ureport-export-excel-paging-sheet`).click(function(){
        const url=window._server+'/excel/sheet'+urlParameters;
        window.open(url,'_blank');
    });
});

function buildPrintStyle(paper){
    const marginLeft=pointToMM(paper.leftMargin);
    const marginTop=pointToMM(paper.topMargin);
    const marginRight=pointToMM(paper.rightMargin);
    const marginBottom=pointToMM(paper.bottomMargin);
    const paperType=paper.paperType;
    let page=paperType;
    if(paperType==='CUSTOM'){
        page=pointToMM(paper.width)+'mm '+pointToMM(paper.height)+'mm';
    }
    const style=`
        @media print {
            .page-break{
                display: block;
                page-break-before: always;
            }
        }
        @page {
          size: ${page} ${paper.orientation};
          margin-left: ${marginLeft}mm;
          margin-top: ${marginTop}mm;
          margin-right:${marginRight}mm;
          margin-bottom:${marginBottom}mm;
        }
    `;
    return style;
};
window._intervalRefresh=function(value,file,totalPage,customParameters){
    if(!value){
        return;
    }
    const second=value*1000;
    setTimeout(function(){
        _refreshData(customParameters,file,totalPage,second);
    },second);
};


window._currentPageIndex=1;

function _refreshData(customParameters,file,totalPage,second){
    let url=window._server+"/preview/loadData?_u="+file+"";
    if(customParameters){
        url+=customParameters;
    }
    if(totalPage>0){
        if(window._currentPageIndex>totalPage){
            window._currentPageIndex=1;
        }
        url+="&_i="+window._currentPageIndex+"";
        $("#pageSelector").val(window._currentPageIndex);
        window._currentPageIndex++;
    }
    $.ajax({
        url,
        type:'GET',
        success:function(report){
            const tableContainer=$(`#_ureport_table`);
            tableContainer.empty();
            tableContainer.append(report.content);
            _buildChartDatas(report.chartDatas);
            setTimeout(function(){
                _refreshData(customParameters,file,totalPage,second);
            },second);
        },
        error:function(){
            alert('加载数据失败！');
        }
    });
};

window._buildChartDatas=function(chartData){
    if(!chartData){
        return;
    }
    for(let d of chartData){
        let json=d.json;
        json=JSON.parse(json);
        _buildChart(d.id,json);
    }
};
window._buildChart=function(canvasId,chartJson){
    const ctx=document.getElementById(canvasId);
    let options=chartJson.options;
    if(!options){
        options={};
        chartJson.options=options;
    }
    let animation=options.animation;
    if(!animation){
        animation={};
        options.animation=animation;
    }
    animation.onComplete=function(event){
        const chart=event.chart;
        const base64Image=chart.toBase64Image();
        const urlParameters=window.location.search;
        const url=window._server+'/chart/storeData'+urlParameters;
        const canvas=$("#"+canvasId);
        const width=parseInt(canvas.css('width'));
        const height=parseInt(canvas.css('height'));
        $.ajax({
            type:'POST',
            data:{_base64Data:base64Image,_chartId:canvasId,_width:width,_height:height},
            url
        });
    };
    const chart=new Chart(ctx,chartJson);
};
