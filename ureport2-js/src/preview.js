/**
 * Created by Jacky.Gao on 2017-03-17.
 */
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import {getParameter,pointToMM,showLoading,hideLoading} from './Utils.js';
import {alert} from './MsgBox.js';
import PDFPrintDialog from './dialog/PDFPrintDialog.js';

$(document).ready(function(){
    const urlParameters=window.location.search;
    $('.ureport-print').click(function(){
        const url=window._server+'/preview/loadPrintPages'+urlParameters;
        showLoading();
        $.ajax({
            url,
            type:'POST',
            success:function(result){
                $.get(window._server+'/preview/loadPagePaper'+urlParameters,function(paper){
                    const html=result.html;
                    const iFrame=window.frames['_print_frame'];
                    let styles=`<style type="text/css">`;
                    styles+=buildPrintStyle(paper);
                    styles+=$('#_ureport_table_style').html();
                    styles+=`</style>`;
                    $(iFrame.document.body).html(styles+html);
                    iFrame.window.focus();
                    hideLoading();
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
    let load=false;
    $(`.ureport-pdf-print`).click(function(){
        $.get(window._server+'/preview/loadPagePaper'+urlParameters,function(paper){
            pdfPrintDialog.show(paper);
            if(!load){
                showLoading();
            }
            load=true;
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
