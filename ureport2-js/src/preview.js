/**
 * Created by Jacky.Gao on 2017-03-17.
 */
import './form/external/bootstrap-datetimepicker.css';
import {pointToMM,showLoading,hideLoading} from './Utils.js';
import {alert} from './MsgBox.js';
import PDFPrintDialog from './dialog/PDFPrintDialog.js';
import defaultI18nJsonData from './i18n/preview.json';
import en18nJsonData from './i18n/preview_en.json';
(function($){
    $.fn.datetimepicker.dates['zh-CN'] = {
        days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
        daysShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日"],
        daysMin:  ["日", "一", "二", "三", "四", "五", "六", "日"],
        months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        monthsShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        today: "今天",
        suffix: [],
        meridiem: ["上午", "下午"]
    };
}(jQuery));

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
    $('.ureport-print').click(function(){
        const urlParameters=buildLocationSearchParameters();
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
            error:function(response){
                hideLoading();
                if(response && response.responseText){
                    alert("服务端错误："+response.responseText+"");
                }else{
                    alert("服务端出错！");
                }
            }
        });
    });
    let directPrintPdf=false,index=0;
    const pdfPrintDialog=new PDFPrintDialog();
    $(`.ureport-pdf-print`).click(function(){
        const urlParameters=buildLocationSearchParameters();
        $.get(window._server+'/preview/loadPagePaper'+urlParameters,function(paper){
            pdfPrintDialog.show(paper);
        });
    });
    $(`.ureport-pdf-direct-print`).click(function(){
        showLoading();
        const urlParameters=buildLocationSearchParameters();
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
        const urlParameters=buildLocationSearchParameters();
        const url=window._server+'/pdf'+urlParameters;
        window.open(url,'_blank');
    });
    $(`.ureport-export-word`).click(function(){
        const urlParameters=buildLocationSearchParameters();
        const url=window._server+'/word'+urlParameters;
        window.open(url,'_blank');
    });
    $(`.ureport-export-excel`).click(function(){
        const urlParameters=buildLocationSearchParameters();
        const url=window._server+'/excel'+urlParameters;
        window.open(url,'_blank');
    });
    $(`.ureport-export-excel-paging`).click(function(){
        const urlParameters=buildLocationSearchParameters();
        const url=window._server+'/excel/paging'+urlParameters;
        window.open(url,'_blank');
    });
    $(`.ureport-export-excel-paging-sheet`).click(function(){
        const urlParameters=buildLocationSearchParameters();
        const url=window._server+'/excel/sheet'+urlParameters;
        window.open(url,'_blank');
    });
});

window._currentPageIndex=null;
window._totalPage=null;

window.buildLocationSearchParameters=function(exclude){
    let urlParameters=window.location.search;
    if(urlParameters.length>0){
        urlParameters=urlParameters.substring(1,urlParameters.length);
    }
    let parameters={};
    const pairs=urlParameters.split('&');
    for(let i=0;i<pairs.length;i++){
        const item=pairs[i];
        if(item===''){
            continue;
        }
        const param=item.split('=');
        let key=param[0];
        if(exclude && key===exclude){
            continue;
        }
        let value=param[1];
        parameters[key]=value;
    }
    if(window.searchFormParameters){
        for(let key in window.searchFormParameters){
            if(key===exclude){
                continue;
            }
            const value=window.searchFormParameters[key];
            if(value){
                parameters[key]=value;
            }
        }
    }
    let p='?';
    for(let key in parameters){
        if(p==='?'){
            p+=key+'='+parameters[key];
        }else{
            p+='&'+key+'='+parameters[key];
        }
    }
    return p;
};

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

window.buildPaging=function(pageIndex,totalPage){
    if(totalPage===0){
        return;
    }
    if(!pageIndex){
        return;
    }
    if(!window._currentPageIndex){
        window._currentPageIndex=pageIndex;
    }
    pageIndex=window._currentPageIndex;
    if(!window._totalPage){
        window._totalPage=totalPage;
    }

    const pageSelector=$('#pageSelector');
    pageSelector.change(function(){
        const parameters=window.buildLocationSearchParameters('_i');
        let url=window._server+`/preview${parameters}&_i=${$(this).val()}`;
        window.open(url,'_self');
    });
    pageSelector.val(pageIndex);
    if(totalPage===1){
        return;
    }
    const parameters=window.buildLocationSearchParameters('_i');
    const pagingContainer=$('#pageLinkContainer');
    pagingContainer.empty();
    if(pageIndex>1){
        let url=window._server+`/preview${parameters}&_i=${pageIndex-1}`;
        const prevPage=$(`<button type="button" class="btn btn-link btn-sm">上一页</button>`);
        pagingContainer.append(prevPage);
        prevPage.click(function(){
            window.open(url,'_self');
        });
    }
    if(pageIndex<totalPage){
        let url=window._server+`/preview${parameters}&_i=${pageIndex+1}`;
        const nextPage=$(`<button type="button" class="btn btn-link btn-sm">下一页</button>`);
        pagingContainer.append(nextPage);
        nextPage.click(function(){
            window.open(url,'_self');
        });
    }
};

window._intervalRefresh=function(value,totalPage){
    if(!value){
        return;
    }
    window._totalPage=totalPage;
    const second=value*1000;
    setTimeout(function(){
        _refreshData(second);
    },second);
};

function _refreshData(second){
    const params=buildLocationSearchParameters('_i');
    let url=window._server+`/preview/loadData${params}`;
    const totalPage=window._totalPage;
    if(totalPage>0){
        if(window._currentPageIndex){
            if(window._currentPageIndex>totalPage){
                window._currentPageIndex=1;
            }
            url+="&_i="+window._currentPageIndex+"";
        }
        $("#pageSelector").val(window._currentPageIndex);
    }
    $.ajax({
        url,
        type:'GET',
        success:function(report){
            const tableContainer=$(`#_ureport_table`);
            tableContainer.empty();
            window._totalPage=report.totalPageWithCol;
            tableContainer.append(report.content);
            _buildChartDatas(report.chartDatas);
            buildPaging(window._currentPageIndex,window._totalPage);
            if(window._currentPageIndex){
                window._currentPageIndex++;
            }
            setTimeout(function(){
                _refreshData(second);
            },second);
        },
        error:function(response){
            const tableContainer=$(`#_ureport_table`);
            tableContainer.empty();
            if(response && response.responseText){
                tableContainer.append("<h3 style='color: #d30e00;'>服务端错误："+response.responseText+"</h3>");
            }else{
                tableContainer.append("<h3 style='color: #d30e00;'>加载数据失败</h3>");
            }
            setTimeout(function(){
                _refreshData(second);
            },second);
        }
    });
};

window._buildChartDatas=function(chartData){
    if(!chartData){
        return;
    }
    for(let d of chartData){
        let json=d.json;
        json=JSON.parse(json,function (k, v) {
            if(v.indexOf && v.indexOf('function') > -1){
                return eval("(function(){return "+v+" })()")
            }
            return v;
        });
        _buildChart(d.id,json);
    }
};
window._buildChart=function(canvasId,chartJson){
    const ctx=document.getElementById(canvasId);
    if(!ctx){
        return;
    }
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

window.submitSearchForm=function(file,customParameters){
    window.searchFormParameters={};
    for(let fun of window.formElements){
        const json=fun.call(this);
        for(let key in json){
            let value=json[key];
            value=encodeURI(value);
            value=encodeURI(value);
            window.searchFormParameters[key]=value;
        }
    }
    const parameters=window.buildLocationSearchParameters('_i');
    let url=window._server+"/preview/loadData"+parameters;
    const pageSelector=$(`#pageSelector`);
    if(pageSelector.length>0){
        url+='&_i=1';
    }
    $.ajax({
        url,
        type:'POST',
        success:function(report){
            window._currentPageIndex=1;
            const tableContainer=$(`#_ureport_table`);
            tableContainer.empty();
            tableContainer.append(report.content);
            _buildChartDatas(report.chartDatas);
            const totalPage=report.totalPage;
            window._totalPage=totalPage;
            if(pageSelector.length>0){
                pageSelector.empty();
                for(let i=1;i<=totalPage;i++){
                    pageSelector.append(`<option>${i}</option>`);
                }
                const pageIndex=report.pageIndex || 1;
                pageSelector.val(pageIndex);
                $('#totalPageLabel').html(totalPage);
                buildPaging(pageIndex,totalPage);
            }
        },
        error:function(response){
            if(response && response.responseText){
                alert("服务端错误："+response.responseText+"");
            }else{
                alert('查询操作失败！');
            }
        }
    });
};