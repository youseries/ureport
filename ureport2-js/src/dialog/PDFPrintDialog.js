/**
 * Created by Jacky.Gao on 2017-02-07.
 */
import {mmToPoint,pointToMM,buildPageSizeList,getParameter,showLoading,hideLoading} from '../Utils.js';
import {alert} from '../MsgBox.js';

export default class PDFPrintDialog{
    constructor(){
        const w=$(window).width(),h=$(window).height();
        this.paperSizeList=buildPageSizeList();
        this.dialog=$(`<div class="modal fade" role="dialog" aria-hidden="true" style="z-index: 1110">
            <div class="modal-dialog modal-lg" style="width: 1250px;">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title">
                            ${window.i18n.pdfPrint.title}
                        </h4>
                    </div>
                    <div class="modal-body" style="padding-top:5px"></div>
                    <div class="modal-footer">
                    </div>
                </div>
            </div>
        </div>`);
        this.body=this.dialog.find('.modal-body');
        const footer=this.dialog.find(".modal-footer");
        this.initBody();
    }
    initBody(){
        const toolbar=$(`<fieldset style="width: 100%;height: 60px;font-size: 12px;border: solid 1px #ddd;border-radius: 5px;padding: 1px 8px;">
        <legend style="font-size: 12px;width: 60px;border-bottom: none;margin-bottom: 0;">${window.i18n.pdfPrint.setup}</legend>
        </fieldset>`);
        this.body.append(toolbar);
        const pageTypeGroup=$(`<div class="form-group" style="display: inline-block"><label>${window.i18n.pdfPrint.paper}</label></div>`);
        toolbar.append(pageTypeGroup);
        this.pageSelect=$(`<select class="form-control" style="display: inline-block;width: 68px;font-size: 12px;padding: 1px;height: 28px;">
            <option>A0</option>
            <option>A1</option>
            <option>A2</option>
            <option>A3</option>
            <option>A4</option>
            <option>A5</option>
            <option>A6</option>
            <option>A7</option>
            <option>A8</option>
            <option>A9</option>
            <option>A10</option>
            <option>B0</option>
            <option>B1</option>
            <option>B2</option>
            <option>B3</option>
            <option>B4</option>
            <option>B5</option>
            <option>B6</option>
            <option>B7</option>
            <option>B8</option>
            <option>B9</option>
            <option>B10</option>
            <option value="CUSTOM">${window.i18n.pdfPrint.custom}</option>
        </select>`);
        pageTypeGroup.append(this.pageSelect);
        const _this=this;
        this.pageSelect.change(function(){
            let value=$(this).val();
            if(value==='CUSTOM'){
                _this.pageWidthEditor.prop('readonly',false);
                _this.pageHeightEditor.prop('readonly',false);
            }else{
                _this.pageWidthEditor.prop('readonly',true);
                _this.pageHeightEditor.prop('readonly',true);
                let pageSize=_this.paperSizeList[value];
                _this.pageWidthEditor.val(pageSize.width);
                _this.pageHeightEditor.val(pageSize.height);
                _this.paper.width=mmToPoint(pageSize.width);
                _this.paper.height=mmToPoint(pageSize.height);
            }
            _this.paper.paperType=value;
        });

        const pageWidthGroup=$(`<div class="form-group" style="display: inline-block;margin-left: 6px"><span>${window.i18n.pdfPrint.width}</span></div>`);
        toolbar.append(pageWidthGroup);
        this.pageWidthEditor=$(`<input type="number" class="form-control" readonly style="display: inline-block;width: 40px;font-size: 12px;padding: 1px;height: 28px">`);
        pageWidthGroup.append(this.pageWidthEditor);
        this.pageWidthEditor.change(function(){
            let value=$(this).val();
            if(!value || isNaN(value)){
                alert(`${window.i18n.pdfPrint.numberTip}`);
                return;
            }
            _this.paper.width=mmToPoint(value);
            _this.context.printLine.refresh();
        });

        const pageHeightGroup=$(`<div class="form-group" style="display: inline-block;margin-left: 6px"><span>${window.i18n.pdfPrint.height}</span></div>`);
        toolbar.append(pageHeightGroup);
        this.pageHeightEditor=$(`<input type="number" class="form-control" readonly style="display: inline-block;width: 40px;font-size: 12px;padding: 1px;height: 28px">`);
        pageHeightGroup.append(this.pageHeightEditor);
        this.pageHeightEditor.change(function(){
            let value=$(this).val();
            if(!value || isNaN(value)){
                alert(`${window.i18n.pdfPrint.numberTip}`);
                return;
            }
            _this.paper.height=mmToPoint(value);
        });

        const orientationGroup=$(`<div class="form-group" style="display: inline-block;margin-left: 6px"><label>${window.i18n.pdfPrint.orientation}</label></div>`);
        toolbar.append(orientationGroup);
        this.orientationSelect=$(`<select class="form-control" style="display:inline-block;width: 60px;font-size: 12px;padding: 1px;height: 28px">
            <option value="portrait">${window.i18n.pdfPrint.portrait}</option>
            <option value="landscape">${window.i18n.pdfPrint.landscape}</option>
        </select>`);
        orientationGroup.append(this.orientationSelect);
        this.orientationSelect.change(function(){
            let value=$(this).val();
            _this.paper.orientation=value;
        });

        const marginGroup=$(`<div style="display: inline-block"></div>`);
        toolbar.append(marginGroup);

        const leftMarginGroup=$(`<div class="form-group" style="display: inline-block;margin-left:6px"><label>${window.i18n.pdfPrint.leftMargin}</label></div>`);
        marginGroup.append(leftMarginGroup);
        this.leftMarginEditor=$(`<input type="number" class="form-control" style="display: inline-block;width: 40px;font-size: 12px;padding: 1px;height: 28px">`);
        leftMarginGroup.append(this.leftMarginEditor);
        this.leftMarginEditor.change(function(){
            let value=$(this).val();
            if(!value || isNaN(value)){
                alert(`${window.i18n.pdfPrint.numberTip}`);
                return;
            }
            _this.paper.leftMargin=mmToPoint(value);
            _this.context.printLine.refresh();
        });

        const rightMarginGroup=$(`<div class="form-group" style="display: inline-block;margin-top: 5px;margin-left: 6px""><label>${window.i18n.pdfPrint.rightMargin}</label></div>`);
        marginGroup.append(rightMarginGroup);
        this.rightMarginEditor=$(`<input type="number" class="form-control" style="display: inline-block;width: 40px;font-size: 12px;padding: 1px;height: 28px">`);
        rightMarginGroup.append(this.rightMarginEditor);
        this.rightMarginEditor.change(function(){
            let value=$(this).val();
            if(!value || isNaN(value)){
                alert(`${window.i18n.pdfPrint.numberTip}`);
                return;
            }
            _this.paper.rightMargin=mmToPoint(value);
            _this.context.printLine.refresh();
        });

        const topMarginGroup=$(`<div class="form-group" style="display: inline-block;margin-left: 6px;"><label>${window.i18n.pdfPrint.topMargin}</label></div>`);
        marginGroup.append(topMarginGroup);
        this.topMarginEditor=$(`<input type="number" class="form-control" style="display: inline-block;width: 40px;font-size: 12px;padding: 1px;height: 28px">`);
        topMarginGroup.append(this.topMarginEditor);
        this.topMarginEditor.change(function(){
            let value=$(this).val();
            if(!value || isNaN(value)){
                alert(`${window.i18n.pdfPrint.numberTip}`);
                return;
            }
            _this.paper.topMargin=mmToPoint(value);
        });

        const bottomMarginGroup=$(`<div class="form-group" style="display: inline-block;margin-left: 6px""><label>${window.i18n.pdfPrint.bottomMargin}</label></div>`);
        marginGroup.append(bottomMarginGroup);
        this.bottomMarginEditor=$(`<input type="number" class="form-control" style="display: inline-block;width: 40px;font-size: 12px;padding: 1px;height: 28px">`);
        bottomMarginGroup.append(this.bottomMarginEditor);
        this.bottomMarginEditor.change(function(){
            let value=$(this).val();
            if(!value || isNaN(value)){
                alert(`${window.i18n.pdfPrint.numberTip}`);
                return;
            }
            _this.paper.bottomMargin=mmToPoint(value);
        });
        const file=getParameter('_u');
        const urlParameters=window.location.search;
        const button=$(`<button class="btn btn-primary" style="padding-top:5px;height: 30px;margin-left: 10px;">${window.i18n.pdfPrint.apply}</button>`);
        toolbar.append(button);
        let index=0;
        button.click(function(){
            showLoading();
            const paperData=JSON.stringify(_this.paper);
            $.ajax({
                type:'POST',
                data:{_paper:paperData},
                url:window._server+'/pdf/newPaging'+urlParameters,
                success:function(){
                    const newUrl=window._server+'/pdf/show'+urlParameters+'&_r='+(index++);
                    _this.iFrame.prop('src',newUrl);
                },
                error:function(){
                    hideLoading();
                    alert(`${window.i18n.pdfPrint.fail}`);
                }
            })
        });

        const printButton=$(`<button class="btn btn-danger" style="padding-top:5px;height: 30px;margin-left: 10px;">${window.i18n.pdfPrint.print}</button>`);
        toolbar.append(printButton);
        printButton.click(function(){
            window.frames['_iframe_for_pdf_print'].window.print();
        });
    }

    initIFrame(){
        if(this.iFrame){
           return;
        }
        const urlParameters=buildLocationSearchParameters();
        const h=$(window).height();
        const url=window._server+"/pdf/show"+urlParameters+"&_p=1";
        this.iFrame=$(`<iframe name="_iframe_for_pdf_print" style="width: 100%;height:${h}px;margin-top: 5px;border:solid 1px #c2c2c2" frameborder="0" src="${url}"></iframe>`);
        this.body.append(this.iFrame);
        const iframe=this.iFrame.get(0);
        const msie = window.navigator.appName.indexOf("Internet Explorer");
        const ie11=!!window.MSInputMethodContext && !!document.documentMode;
        if(msie===-1 && !ie11){
            showLoading();
        }
        this.iFrame.on('load',function(){
            hideLoading();
        });
    }

    show(paper){
        this.paper=paper;
        this.pageSelect.val(this.paper.paperType);
        this.pageWidthEditor.val(pointToMM(this.paper.width));
        this.pageHeightEditor.val(pointToMM(this.paper.height));
        this.pageSelect.trigger('change');
        this.leftMarginEditor.val(pointToMM(this.paper.leftMargin));
        this.rightMarginEditor.val(pointToMM(this.paper.rightMargin));
        this.topMarginEditor.val(pointToMM(this.paper.topMargin));
        this.bottomMarginEditor.val(pointToMM(this.paper.bottomMargin));
        this.orientationSelect.val(this.paper.orientation);
        this.dialog.modal('show');
        this.initIFrame();
    }
};