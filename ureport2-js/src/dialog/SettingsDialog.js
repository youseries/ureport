/**
 * Created by Jacky.Gao on 2017-02-07.
 */
import {alert} from '../MsgBox.js';
import {pointToMM,mmToPoint,buildPageSizeList,setDirty} from '../Utils.js';
import FontSettingDialog from './FontSettingDialog.js';

export default class SettingsDialog{
    constructor(){
        this.paperSizeList=buildPageSizeList();

        this.dialog=$(`<div class="modal fade" role="dialog" aria-hidden="true" style="z-index: 10000">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title">
                            ${window.i18n.dialog.setting.title}
                        </h4>
                    </div>
                    <div class="modal-body"></div>
                    <div class="modal-footer"></div>
                </div>
            </div>
        </div>`);
        const body=this.dialog.find('.modal-body'),footer=this.dialog.find(".modal-footer");
        this.initBody(body,footer);
    }
    initBody(body,footer){
        const tabHeader=$(`<ul class="nav nav-tabs">
        <li class="active"><a href="#__page_setup" data-toggle="tab">${window.i18n.dialog.setting.pageSetting}</a></li>
        <li><a href="#__header_footer" data-toggle="tab">${window.i18n.dialog.setting.headerFooterSetting}</a></li>
        <li><a href="#__paging" data-toggle="tab">${window.i18n.dialog.setting.pagingSetting}</a></li>
        <li><a href="#__column" data-toggle="tab">${window.i18n.dialog.setting.columnSetting}</a></li>
        </ul>`);
        body.append(tabHeader);
        const tabContent=$(`<div class="tab-content"></div>`);
        body.append(tabContent);
        const pageTab=$(`<div class="tab-pane fade in active" id="__page_setup"></div>`);
        tabContent.append(pageTab);
        const headerFooterTab=$(`<div class="tab-pane fade" id="__header_footer"></div>`);
        tabContent.append(headerFooterTab);
        const pagingTab=$(`<div class="tab-pane fade" id="__paging"></div>`);
        tabContent.append(pagingTab);
        const columnTab=$(`<div class="tab-pane fade" id="__column"></div>`);
        tabContent.append(columnTab);
        //const exportTab=$(`<div class="tab-pane fade" id="__export"></div>`);
        //tabContent.append(exportTab);
        this.initPageSetting(pageTab);
        this.initHeaderFootSetting(headerFooterTab);
        this.initPagingSetting(pagingTab);
        this.initColumnSetting(columnTab);
        //this.initExportSetting(exportTab);
    }
    initExportSetting(exportTab){
        const excelGroup=$(`<div class="form-group" style="margin-top: 12px;display: inline-block"><label>${window.i18n.dialog.setting.sheetExport}</label></div>`);
        exportTab.append(excelGroup);
        this.disabledExcelSheetRadio=$(`<label class="checkbox-inline" style="padding-left: 5px">
            <input type="radio" name="useColumn" value="true" checked> ${window.i18n.dialog.setting.disable}
        </label>`);
        excelGroup.append(this.disabledExcelSheetRadio);
        this.enabledExcelSheetRadio=$(`<label class="checkbox-inline">
            <input type="radio" name="useColumn" value="true"> ${window.i18n.dialog.setting.enable}
        </label>`);
        excelGroup.append(this.enabledExcelSheetRadio);
        const _this=this;
        this.disabledExcelSheetRadio.children('input').click(function(){
            _this.paper.columnEnabled=false;
            _this.sheetSizeEditor.prop('disabled',true);
        });
        this.enabledExcelSheetRadio.children('input').click(function(){
            _this.paper.columnEnabled=true;
            _this.sheetSizeEditor.prop('disabled',false);
        });
        const sheetSizeGroup=$(`<div class="form-group"><label>${window.i18n.dialog.setting.recordCountPerPage}</label></div>`);
        this.sheetSizeEditor=$(`<input type="number" class="form-control" value="65535" style="display: inline-block;width:100px">`);
        sheetSizeGroup.append(this.sheetSizeEditor);
        exportTab.append(sheetSizeGroup);
        this.sheetSizeEditor.prop('disabled',true);
        this.sheetSizeEditor.change(function(){
            const value=$(this).val();
            if(parseInt(value)<1){
                alert(`${window.i18n.dialog.setting.recordCountTip}`);
                return;
            }
            this.paper.sheetSize=value;
        });
    }
    initPageSetting(pageTab){
        const pageTypeGroup=$(`<div class="form-group" style="margin-top: 12px;display: inline-block"><label>${window.i18n.dialog.setting.paperType}</label></div>`);
        pageTab.append(pageTypeGroup);
        this.pageSelect=$(`<select class="form-control" style="display: inline-block;width: 95px;">
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
            <option value="CUSTOM">${window.i18n.dialog.setting.custom}</option>
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
                _this.context.printLine.refresh();
            }
            _this.paper.paperType=value;
            setDirty();
        });

        const pageWidthGroup=$(`<div class="form-group" style="display: inline-block;margin-left: 25px"><span>${window.i18n.dialog.setting.paperWidth}</span></div>`);
        pageTab.append(pageWidthGroup);
        this.pageWidthEditor=$(`<input type="number" class="form-control" readonly style="display: inline-block;width: 80px;">`);
        pageWidthGroup.append(this.pageWidthEditor);
        this.pageWidthEditor.change(function(){
            let value=$(this).val();
            if(!value || isNaN(value)){
                alert(`${window.i18n.dialog.setting.numberTip}`);
                return;
            }
            _this.paper.width=mmToPoint(value);
            _this.context.printLine.refresh();
            setDirty();
        });

        const pageHeightGroup=$(`<div class="form-group" style="display: inline-block;margin-left: 15px"><span>${window.i18n.dialog.setting.paperHeight}</span></div>`);
        pageTab.append(pageHeightGroup);
        this.pageHeightEditor=$(`<input type="number" class="form-control" readonly style="display: inline-block;width: 80px;">`);
        pageHeightGroup.append(this.pageHeightEditor);
        this.pageHeightEditor.change(function(){
            let value=$(this).val();
            if(!value || isNaN(value)){
                alert(`${window.i18n.dialog.setting.numberTip}`);
                return;
            }
            _this.paper.height=mmToPoint(value);
            setDirty();
        });

        const leftMarginGroup=$(`<div class="form-group" style="display: inline-block;margin-top: 5px;"><label>${window.i18n.dialog.setting.leftMargin}</label></div>`);
        pageTab.append(leftMarginGroup);
        this.leftMarginEditor=$(`<input type="number" class="form-control" style="display: inline-block;width: 70px;padding: 2px">`);
        leftMarginGroup.append(this.leftMarginEditor);
        this.leftMarginEditor.change(function(){
            let value=$(this).val();
            if(!value || isNaN(value)){
                alert(`${window.i18n.dialog.setting.numberTip}`);
                return;
            }
            _this.paper.leftMargin=mmToPoint(value);
            _this.context.printLine.refresh();
            setDirty();
        });

        const rightMarginGroup=$(`<div class="form-group" style="display: inline-block;margin-top: 5px;margin-left: 25px""><label>${window.i18n.dialog.setting.rightMargin}</label></div>`);
        pageTab.append(rightMarginGroup);
        this.rightMarginEditor=$(`<input type="number" class="form-control" style="display: inline-block;width: 70px;padding: 2px">`);
        rightMarginGroup.append(this.rightMarginEditor);
        pageTab.append('<div></div>');
        this.rightMarginEditor.change(function(){
            let value=$(this).val();
            if(!value || isNaN(value)){
                alert(`${window.i18n.dialog.setting.numberTip}`);
                return;
            }
            _this.paper.rightMargin=mmToPoint(value);
            _this.context.printLine.refresh();
            setDirty();
        });

        const topMarginGroup=$(`<div class="form-group" style="display: inline-block;margin-top: 5px;"><label>${window.i18n.dialog.setting.topMargin}</label></div>`);
        pageTab.append(topMarginGroup);
        this.topMarginEditor=$(`<input type="number" class="form-control" style="display: inline-block;width: 70px;padding: 2px">`);
        topMarginGroup.append(this.topMarginEditor);
        this.topMarginEditor.change(function(){
            let value=$(this).val();
            if(!value || isNaN(value)){
                alert(`${window.i18n.dialog.setting.numberTip}`);
                return;
            }
            _this.paper.topMargin=mmToPoint(value);
            setDirty();
        });

        const bottomMarginGroup=$(`<div class="form-group" style="display: inline-block;margin-top: 5px;margin-left: 25px""><label>${window.i18n.dialog.setting.bottomMargin}</label></div>`);
        pageTab.append(bottomMarginGroup);
        this.bottomMarginEditor=$(`<input type="number" class="form-control" style="display: inline-block;width: 70px;padding: 2px">`);
        bottomMarginGroup.append(this.bottomMarginEditor);
        this.bottomMarginEditor.change(function(){
            let value=$(this).val();
            if(!value || isNaN(value)){
                alert(`${window.i18n.dialog.setting.numberTip}`);
                return;
            }
            _this.paper.bottomMargin=mmToPoint(value);
            setDirty();
        });

        const orientationGroup=$(`<div class="form-group"><label>${window.i18n.dialog.setting.orientation}</label></div>`);
        pageTab.append(orientationGroup);
        this.orientationSelect=$(`<select class="form-control" style="display:inline-block;width: 312px">
            <option value="portrait">${window.i18n.dialog.setting.portrait}</option>
            <option value="landscape">${window.i18n.dialog.setting.landscape}</option>
        </select>`);
        orientationGroup.append(this.orientationSelect);
        this.orientationSelect.change(function(){
            let value=$(this).val();
            _this.paper.orientation=value;
            _this.context.printLine.refresh();
            setDirty();
        });

        const htmlReportAlignGroup=$(`<div class="form-group"><label>${window.i18n.dialog.setting.htmlAlign}</label></div>`);
        pageTab.append(htmlReportAlignGroup);
        this.htmlReportAlignSelect=$(`<select class="form-control" style="display:inline-block;width: 80px">
            <option value="left">${window.i18n.dialog.setting.left}</option>
            <option value="center">${window.i18n.dialog.setting.center}</option>
            <option value="right">${window.i18n.dialog.setting.right}</option>
        </select>`);
        this.htmlReportAlignSelect.change(function(){
            let value=$(this).val();
            _this.paper.htmlReportAlign=value;
            setDirty();
        });
        htmlReportAlignGroup.append(this.htmlReportAlignSelect);

        const htmlIntervalReloadGroup=$(`<span style="margin-left: 35px;"><label>${window.i18n.dialog.setting.refreshSecond}</label></span>`);
        htmlReportAlignGroup.append(htmlIntervalReloadGroup);
        this.htmlIntervalEditor=$(`<input type="number" class="form-control" placeholder="${window.i18n.dialog.setting.tip1}" title="${window.i18n.dialog.setting.tip2}" value="0" style="width: 90px;display: inline-block">`);
        htmlIntervalReloadGroup.append(this.htmlIntervalEditor);
        this.htmlIntervalEditor.change(function(){
            let value=$(this).val();
            if(isNaN(value)){
                alert(`${window.i18n.dialog.setting.secondTip}`);
                return;
            }
            const num=parseInt(value);
            if(num<0){
                alert(`${window.i18n.dialog.setting.secondTip}`);
                return;
            }
            _this.paper.htmlIntervalRefreshValue=value;
            setDirty();
        });

        const bgImageGroup=$(`<div class="form-group"><label>${window.i18n.dialog.setting.bg}</label></div>`);
        pageTab.append(bgImageGroup);
        this.bgImageEditor=$(`<input type="text" class="form-control" style="display: inline-block;width: 470px;" placeholder="${window.i18n.dialog.setting.bgTip}">`);
        bgImageGroup.append(this.bgImageEditor);
        this.bgImageEditor.change(function(){
            let value=$(this).val();
            _this.paper.bgImage=value;
            if(value===''){
                $('.ht_master').css('background','transparent');
            }else{
                $('.ht_master').css('background',`url(${value}) 50px 26px no-repeat`);
            }
            setDirty();
        });
    }
    initHeaderFootSetting(headerFooterTab){
        const _this=this;
        const descGroup=$(`<div class="form-group" style="margin-top: 10px;color: #999999;">
            ${window.i18n.dialog.setting.hfdesc}
        </div>`);
        headerFooterTab.append(descGroup);
        const headerTitle=$(`<label>${window.i18n.dialog.setting.header}</label>`);
        headerFooterTab.append(headerTitle);
        const headerFontConfigButton=$(`<button type="button" class="btn btn-link" style="margin-left: 10px;">${window.i18n.dialog.setting.fontStyleSetting}</button>`);
        headerFooterTab.append(headerFontConfigButton);

        const headerMarginGroup=$(`<span style="margin-left:10px"><span>${window.i18n.dialog.setting.headerMargin}</span></span>`);
        headerFooterTab.append(headerMarginGroup);
        this.headerMarginEditor=$(`<input type="number" class="form-control" style="display: inline-block;width:100px;padding: 5px;height: 26px;">`);
        headerMarginGroup.append(this.headerMarginEditor);
        this.headerMarginEditor.change(function(){
            _this.header.margin=mmToPoint($(this).val());
            setDirty();
        });

        const fontSettingDialog=new FontSettingDialog();
        const headerGroup=$(`<div class="form-group"></div>`);
        headerFooterTab.append(headerGroup);
        const leftHeaderGroup=$(`<span><span style="vertical-align: top">${window.i18n.dialog.setting.hfLeft}</span></span>`);
        headerGroup.append(leftHeaderGroup);
        this.leftHeaderEditor=$(`<textarea class="form-control" style="font-size:10pt;font-family:'宋体';padding: 5px;display: inline-block;width: 140px;height: 80px;margin-top: 15px"></textarea>`);
        leftHeaderGroup.append(this.leftHeaderEditor);
        this.leftHeaderEditor.change(function(){
            const text=$(this).val();
            checkGrammar(text,function(){
                _this.header.left=text;
                setDirty();
            });
        });
        const centerHeaderGroup=$(`<span style="margin-left: 15px;"><span style="vertical-align: top">${window.i18n.dialog.setting.hfCenter}</span></span>`);
        headerGroup.append(centerHeaderGroup);
        this.centerHeaderEditor=$(`<textarea class="form-control" style="padding: 5px;font-size:10pt;font-family:'宋体';display: inline-block;width: 140px;height: 80px;margin-top: 15px"></textarea>`);
        centerHeaderGroup.append(this.centerHeaderEditor);
        this.centerHeaderEditor.change(function(){
            const text=$(this).val();
            checkGrammar(text,function(){
                _this.header.center=text;
                setDirty();
            });
        });
        const rightHeaderGroup=$(`<span style="margin-left: 15px;"><span style="vertical-align: top">${window.i18n.dialog.setting.hfRight}</span></span>`);
        headerGroup.append(rightHeaderGroup);
        this.rightHeaderEditor=$(`<textarea class="form-control" style="padding: 5px;font-size:10pt;font-family:'宋体';display: inline-block;width: 140px;height: 80px;margin-top: 15px"></textarea>`);
        rightHeaderGroup.append(this.rightHeaderEditor);
        this.rightHeaderEditor.change(function(){
            const text=$(this).val();
            checkGrammar(text,function(){
                _this.header.right=text;
                setDirty();
            });
        });

        const footerTitle=$(`<label style="margin-top: 10px;">${window.i18n.dialog.setting.footer}</label>`);
        headerFooterTab.append(footerTitle);
        const footerFontConfigButton=$(`<button type="button" class="btn btn-link" style="margin-left: 10px;">${window.i18n.dialog.setting.fontStyleSetting}</button>`);
        headerFooterTab.append(footerFontConfigButton);

        const footerMarginGroup=$(`<span style="margin-left:10px"><span>${window.i18n.dialog.setting.footerMargin}</span></span>`);
        headerFooterTab.append(footerMarginGroup);
        this.footerMarginEditor=$(`<input type="number" class="form-control" style="display: inline-block;width:100px;padding: 5px;height: 26px;">`);
        footerMarginGroup.append(this.footerMarginEditor);
        this.footerMarginEditor.change(function(){
            _this.footer.margin=mmToPoint($(this).val());
            setDirty();
        });

        const footerGroup=$(`<div class="form-group" style="margin-bottom: 5px"></div>`);
        headerFooterTab.append(footerGroup);
        const leftFooterGroup=$(`<span><span style="vertical-align: top">${window.i18n.dialog.setting.hfLeft}</span></span>`);
        footerGroup.append(leftFooterGroup);
        this.leftFooterEditor=$(`<textarea class="form-control" style="padding: 5px;font-size:10pt;font-family:'宋体';display: inline-block;width: 140px;height: 80px;margin-top: 15px"></textarea>`);
        leftFooterGroup.append(this.leftFooterEditor);
        this.leftFooterEditor.change(function(){
            const text=$(this).val();
            checkGrammar(text,function(){
                _this.footer.left=text;
                setDirty();
            });
        });
        const centerFooterGroup=$(`<span style="margin-left: 15px;"><span style="vertical-align: top">${window.i18n.dialog.setting.hfCenter}</span></span>`);
        footerGroup.append(centerFooterGroup);
        this.centerFooterEditor=$(`<textarea class="form-control" style="padding: 5px;font-size:10pt;font-family:'宋体';display: inline-block;width: 140px;height: 80px;margin-top: 15px"></textarea>`);
        centerFooterGroup.append(this.centerFooterEditor);
        this.centerFooterEditor.change(function(){
            const text=$(this).val();
            checkGrammar(text,function(){
                _this.footer.center=text;
                setDirty();
            });
        });
        const rightFooterGroup=$(`<span style="margin-left: 15px;"><span style="vertical-align: top">${window.i18n.dialog.setting.hfRight}</span></span>`);
        footerGroup.append(rightFooterGroup);
        this.rightFooterEditor=$(`<textarea class="form-control" style="padding: 5px;font-size:10pt;font-family:'宋体';display: inline-block;width: 140px;height: 80px;margin-top: 15px"></textarea>`);
        rightFooterGroup.append(this.rightFooterEditor);
        this.rightFooterEditor.change(function(){
            const text=$(this).val();
            checkGrammar(text,function(){
                _this.footer.right=text;
                setDirty();
            });
        });

        headerFontConfigButton.click(function(){
            fontSettingDialog.show(_this.header,function(style){
                setEditorStyle(_this.leftHeaderEditor,style);
                setEditorStyle(_this.centerHeaderEditor,style);
                setEditorStyle(_this.rightHeaderEditor,style);
            });
        });
        footerFontConfigButton.click(function(){
            fontSettingDialog.show(_this.footer,function(style){
                setEditorStyle(_this.leftFooterEditor,style);
                setEditorStyle(_this.centerFooterEditor,style);
                setEditorStyle(_this.rightFooterEditor,style);
            });
        });
    }
    initPagingSetting(pagingTab){
        const _this=this;
        const group=$(`<div class="form-group" style="margin-top: 10px;height: 12px;"><label>${window.i18n.dialog.setting.pagingType}</label></div>`);
        pagingTab.append(group);
        this.fitPage=$(`<label class="checkbox-inline" style="padding-left: 5px">
            <input type="radio" name="pagingType" value="true"> ${window.i18n.dialog.setting.auto}
        </label>`);
        group.append(this.fitPage);
        this.fixNum=$(`<label class="checkbox-inline" style="padding-left: 5px">
            <input type="radio" name="pagingType" value="true"> ${window.i18n.dialog.setting.fixRows}
        </label>`);
        group.append(this.fixNum);

        const rowNumberGroup=$(`<span style="margin-left: 15px"><span>${window.i18n.dialog.setting.rowsPerPage}</span></span>`);
        group.append(rowNumberGroup);
        rowNumberGroup.hide();
        this.rowNumberEditor=$(`<input type="number" class="form-control" style="display: inline-block;width: 80px;padding: 5px;height: 30px;">`);
        rowNumberGroup.append(this.rowNumberEditor);
        this.rowNumberEditor.change(function(){
            const value=parseInt($(this).val());
            if(value<1){
                alert(`${window.i18n.dialog.setting.fixRowsTip}`);
                return;
            }
            _this.paper.fixRows=value;
            setDirty();
        });
        this.fitPage.children('input').click(function(){
            rowNumberGroup.hide();
            _this.paper.pagingMode='fitpage';
            setDirty();
        });
        this.fixNum.children('input').click(function(){
            rowNumberGroup.show();
            _this.paper.pagingMode='fixrows';
            setDirty();
        });
    }
    initColumnSetting(columnTab){
        columnTab.append(`<div style="margin-top: 12px;color:#999999;font-size: 12px">${window.i18n.dialog.setting.colDesc}</div>`);
        const _this=this;
        const group=$(`<div class="form-group" style="margin-top: 8px;"><label>${window.i18n.dialog.setting.column}</label></div>`);
        columnTab.append(group);
        this.disabledColumnRadio=$(`<label class="checkbox-inline" style="padding-left: 5px">
            <input type="radio" name="useColumn" value="true"> ${window.i18n.dialog.setting.disable}
        </label>`);
        group.append(this.disabledColumnRadio);
        this.enabledColumnRadio=$(`<label class="checkbox-inline">
            <input type="radio" name="useColumn" value="true"> ${window.i18n.dialog.setting.enable}
        </label>`);
        group.append(this.enabledColumnRadio);
        this.disabledColumnRadio.children('input').click(function(){
            _this.paper.columnEnabled=false;
            _this.columnCountSelect.prop('disabled',true);
            _this.columnMarginEditor.prop('readonly',true);
        });
        this.enabledColumnRadio.children('input').click(function(){
            _this.paper.columnEnabled=true;
            _this.columnCountSelect.prop('disabled',false);
            _this.columnMarginEditor.prop('readonly',false);
        });
        const columnGroup=$(`<div class="form-group" style="margin-top: 1px;display: inline-block"><label>${window.i18n.dialog.setting.columnCount}</label></div>`);
        columnTab.append(columnGroup);
        this.columnCountSelect=$(`<select class="form-control" style="display: inherit;width: inherit;padding-left: 5px">
            <option value="2">2${window.i18n.dialog.setting.columnUnit}</option>
            <option value="3">3${window.i18n.dialog.setting.columnUnit}</option>
            <option value="4">4${window.i18n.dialog.setting.columnUnit}</option>
            <option value="5">5${window.i18n.dialog.setting.columnUnit}</option>
            <option value="6">6${window.i18n.dialog.setting.columnUnit}</option>
            <option value="7">7${window.i18n.dialog.setting.columnUnit}</option>
            <option value="8">8${window.i18n.dialog.setting.columnUnit}</option>
            <option value="9">9${window.i18n.dialog.setting.columnUnit}</option>
            <option value="10">10${window.i18n.dialog.setting.columnUnit}</option>
        </select>`);
        columnGroup.append(this.columnCountSelect);
        this.columnCountSelect.change(function(){
            let value=$(this).val();
            if(!value || isNaN(value)){
                alert(`${window.i18n.dialog.setting.columnTip}`);
                return;
            }
            _this.paper.columnCount=value;
            setDirty();
        });
        const columnMarginGroup=$(`<span style="margin-left: 20px"><label>${window.i18n.dialog.setting.columnMargin}</label></span>`);
        columnGroup.append(columnMarginGroup);
        this.columnMarginEditor=$(`<input type="number" class="form-control" style="width: 50px;display: inline-block">`);
        columnMarginGroup.append(this.columnMarginEditor);
        this.columnMarginEditor.change(function(){
            let value=$(this).val();
            if(!value || isNaN(value)){
                alert(`${window.i18n.dialog.setting.numTip}`);
                return;
            }
            _this.paper.columnMargin=mmToPoint(value);
            setDirty();
        });
    }
    show(context){
        this.context=context;
        this.reportDef=this.context.reportDef;
        this.paper=this.reportDef.paper;
        if(!this.reportDef.header){
            this.reportDef.header={margin:30};
        }
        if(!this.reportDef.footer){
            this.reportDef.footer={margin:30};
        }
        this.header=this.reportDef.header;
        this.footer=this.reportDef.footer;
        this.dialog.modal('show');
        this.pageSelect.val(this.paper.paperType);
        this.htmlReportAlignSelect.val(this.paper.htmlReportAlign);
        this.htmlIntervalEditor.val(this.paper.htmlIntervalRefreshValue);
        this.bgImageEditor.val(this.paper.bgImage || '');
        this.pageWidthEditor.val(pointToMM(this.paper.width));
        this.pageHeightEditor.val(pointToMM(this.paper.height));
        this.pageSelect.trigger('change');
        this.leftMarginEditor.val(pointToMM(this.paper.leftMargin));
        this.rightMarginEditor.val(pointToMM(this.paper.rightMargin));
        this.topMarginEditor.val(pointToMM(this.paper.topMargin));
        this.bottomMarginEditor.val(pointToMM(this.paper.bottomMargin));
        this.orientationSelect.val(this.paper.orientation);
        this.columnMarginEditor.val(pointToMM(this.paper.columnMargin));
        this.columnCountSelect.val(this.paper.columnCount);
        if(this.paper.columnEnabled){
            this.enabledColumnRadio.children('input').trigger('click');
            this.enabledColumnRadio.children('input').prop('checked',true);
        }else{
            this.disabledColumnRadio.children('input').trigger('click');
            this.disabledColumnRadio.children('input').prop('checked',true);
        }

        this.headerMarginEditor.val(pointToMM(this.header.margin));
        this.footerMarginEditor.val(pointToMM(this.footer.margin));

        setEditorStyle(this.leftHeaderEditor,this.header);
        setEditorStyle(this.centerHeaderEditor,this.header);
        setEditorStyle(this.rightHeaderEditor,this.header);
        setEditorStyle(this.leftFooterEditor,this.footer);
        setEditorStyle(this.centerFooterEditor,this.footer);
        setEditorStyle(this.rightFooterEditor,this.footer);

        this.leftHeaderEditor.val(this.header.left);
        this.centerHeaderEditor.val(this.header.center);
        this.rightHeaderEditor.val(this.header.right);
        this.leftFooterEditor.val(this.footer.left);
        this.centerFooterEditor.val(this.footer.center);
        this.rightFooterEditor.val(this.footer.right);

        const pagingMode=this.paper.pagingMode;
        if(pagingMode==='fitpage'){
            this.fitPage.children('input').trigger('click');
            this.fitPage.children('input').prop('checked',true);
        }else{
            this.fixNum.children('input').trigger('click');
            this.fixNum.children('input').prop('checked',true);
            this.rowNumberEditor.val(this.paper.fixRows);
        }
    }
}
function checkGrammar(text,callback){
    if(!text || text===''){
        callback.call(this);
        return;
    }
    const url=window._server+"/designer/scriptValidation";
    $.ajax({
        url,
        data:{content:text},
        type:'POST',
        success:function(infos){
            let msg='';
            for(let info of infos){
                msg+=info.message;
            }
            if(msg!==''){
                alert(`${window.i18n.dialog.setting.syntaxError}${msg}`);
            }else{
                callback.call(this);
            }
        },
        error:function(){
            alert(`${window.i18n.dialog.setting.syntaxCheckFail}`);
        }
    })
};
function setEditorStyle(editor,style){
    editor.css({
        'font-family':style.fontFamily,
        'font-size':style.fontSize+'pt',
        'color':"rgb("+style.forecolor+")"
    });
    if(style.bold && style.bold!=='false'){
        editor.css('font-weight','bold');
    }else{
        editor.css('font-weight','normal');
    }
    if(style.italic && style.italic!=='false'){
        editor.css('font-style','italic');
    }else{
        editor.css('font-style','normal');
    }
    if(style.underline && style.underline!=='false'){
        editor.css('text-decoration','underline');
    }else{
        editor.css('text-decoration','none');
    }
};