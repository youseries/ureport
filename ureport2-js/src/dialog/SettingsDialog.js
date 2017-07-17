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
                            报表配置
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
        <li class="active"><a href="#__page_setup" data-toggle="tab">页面配置</a></li>
        <li><a href="#__header_footer" data-toggle="tab">页眉页脚</a></li>
        <li><a href="#__paging" data-toggle="tab">分页配置</a></li>
        <li><a href="#__column" data-toggle="tab">分栏配置</a></li>
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
        const excelGroup=$(`<div class="form-group" style="margin-top: 12px;display: inline-block"><label>分Sheet导出Excel：</label></div>`);
        exportTab.append(excelGroup);
        this.disabledExcelSheetRadio=$(`<label class="checkbox-inline" style="padding-left: 5px">
            <input type="radio" name="useColumn" value="true" checked> 禁用
        </label>`);
        excelGroup.append(this.disabledExcelSheetRadio);
        this.enabledExcelSheetRadio=$(`<label class="checkbox-inline">
            <input type="radio" name="useColumn" value="true"> 启用
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
        const sheetSizeGroup=$(`<div class="form-group"><label>每Sheet页记录数：</label></div>`);
        this.sheetSizeEditor=$(`<input type="number" class="form-control" value="65535" style="display: inline-block;width:100px">`);
        sheetSizeGroup.append(this.sheetSizeEditor);
        exportTab.append(sheetSizeGroup);
        this.sheetSizeEditor.prop('disabled',true);
        this.sheetSizeEditor.change(function(){
            const value=$(this).val();
            if(parseInt(value)<1){
                alert("每Sheet页记录数不能少于1条！");
                return;
            }
            this.paper.sheetSize=value;
        });
    }
    initPageSetting(pageTab){
        const pageTypeGroup=$(`<div class="form-group" style="margin-top: 12px;display: inline-block"><label>页面类型：</label></div>`);
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
            <option value="CUSTOM">自定义</option>
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

        const pageWidthGroup=$(`<div class="form-group" style="display: inline-block;margin-left: 25px"><span>页面宽(毫米)：</span></div>`);
        pageTab.append(pageWidthGroup);
        this.pageWidthEditor=$(`<input type="number" class="form-control" readonly style="display: inline-block;width: 80px;">`);
        pageWidthGroup.append(this.pageWidthEditor);
        this.pageWidthEditor.change(function(){
            let value=$(this).val();
            if(!value || isNaN(value)){
                alert('请输入数字！');
                return;
            }
            _this.paper.width=mmToPoint(value);
            _this.context.printLine.refresh();
            setDirty();
        });

        const pageHeightGroup=$(`<div class="form-group" style="display: inline-block;margin-left: 15px"><span>页面高(毫米)：</span></div>`);
        pageTab.append(pageHeightGroup);
        this.pageHeightEditor=$(`<input type="number" class="form-control" readonly style="display: inline-block;width: 80px;">`);
        pageHeightGroup.append(this.pageHeightEditor);
        this.pageHeightEditor.change(function(){
            let value=$(this).val();
            if(!value || isNaN(value)){
                alert('请输入数字！');
                return;
            }
            _this.paper.height=mmToPoint(value);
            setDirty();
        });

        const leftMarginGroup=$(`<div class="form-group" style="display: inline-block;margin-top: 5px;"><label>左边距(毫米)：</label></div>`);
        pageTab.append(leftMarginGroup);
        this.leftMarginEditor=$(`<input type="number" class="form-control" style="display: inline-block;width: 70px">`);
        leftMarginGroup.append(this.leftMarginEditor);
        this.leftMarginEditor.change(function(){
            let value=$(this).val();
            if(!value || isNaN(value)){
                alert('请输入数字！');
                return;
            }
            _this.paper.leftMargin=mmToPoint(value);
            _this.context.printLine.refresh();
            setDirty();
        });

        const rightMarginGroup=$(`<div class="form-group" style="display: inline-block;margin-top: 5px;margin-left: 25px""><label>右边距(毫米)：</label></div>`);
        pageTab.append(rightMarginGroup);
        this.rightMarginEditor=$(`<input type="number" class="form-control" style="display: inline-block;width: 70px">`);
        rightMarginGroup.append(this.rightMarginEditor);
        pageTab.append('<div></div>');
        this.rightMarginEditor.change(function(){
            let value=$(this).val();
            if(!value || isNaN(value)){
                alert('请输入数字！');
                return;
            }
            _this.paper.rightMargin=mmToPoint(value);
            _this.context.printLine.refresh();
            setDirty();
        });

        const topMarginGroup=$(`<div class="form-group" style="display: inline-block;margin-top: 5px;"><label>上边距(毫米)：</label></div>`);
        pageTab.append(topMarginGroup);
        this.topMarginEditor=$(`<input type="number" class="form-control" style="display: inline-block;width: 70px">`);
        topMarginGroup.append(this.topMarginEditor);
        this.topMarginEditor.change(function(){
            let value=$(this).val();
            if(!value || isNaN(value)){
                alert('请输入数字！');
                return;
            }
            _this.paper.topMargin=mmToPoint(value);
            setDirty();
        });

        const bottomMarginGroup=$(`<div class="form-group" style="display: inline-block;margin-top: 5px;margin-left: 25px""><label>下边距(毫米)：</label></div>`);
        pageTab.append(bottomMarginGroup);
        this.bottomMarginEditor=$(`<input type="number" class="form-control" style="display: inline-block;width: 70px">`);
        bottomMarginGroup.append(this.bottomMarginEditor);
        this.bottomMarginEditor.change(function(){
            let value=$(this).val();
            if(!value || isNaN(value)){
                alert('请输入数字！');
                return;
            }
            _this.paper.bottomMargin=mmToPoint(value);
            setDirty();
        });

        const orientationGroup=$(`<div class="form-group"><label>方向：</label></div>`);
        pageTab.append(orientationGroup);
        this.orientationSelect=$(`<select class="form-control" style="display:inline-block;width: 312px">
            <option value="portrait">纵向</option>
            <option value="landscape">横向</option>
        </select>`);
        orientationGroup.append(this.orientationSelect);
        this.orientationSelect.change(function(){
            let value=$(this).val();
            _this.paper.orientation=value;
            _this.context.printLine.refresh();
            setDirty();
        });

        const htmlReportAlignGroup=$(`<div class="form-group"><label>HTML报表输出时对齐方式：</label></div>`);
        pageTab.append(htmlReportAlignGroup);
        this.htmlReportAlignSelect=$(`<select class="form-control" style="display:inline-block;width: 175px">
            <option value="left">居左</option>
            <option value="center">居中</option>
            <option value="right">居右</option>
        </select>`);
        this.htmlReportAlignSelect.change(function(){
            let value=$(this).val();
            _this.paper.htmlReportAlign=value;
            setDirty();
        });
        htmlReportAlignGroup.append(this.htmlReportAlignSelect);

        const bgImageGroup=$(`<div class="form-group"><label>套打背景图：</label></div>`);
        pageTab.append(bgImageGroup);
        this.bgImageEditor=$(`<input type="text" class="form-control" style="display: inline-block;width: 470px;" placeholder="请输入背景图片URL,图片分辨率需要为96dpi">`);
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
            页眉页脚采用表达式方式定义，如："第"+page()+"页,共"+pages()+"页"
        </div>`);
        headerFooterTab.append(descGroup);
        const headerTitle=$(`<label>页眉</label>`);
        headerFooterTab.append(headerTitle);
        const headerFontConfigButton=$(`<button type="button" class="btn btn-link" style="margin-left: 10px;">字体样式配置</button>`);
        headerFooterTab.append(headerFontConfigButton);

        const headerMarginGroup=$('<span style="margin-left:10px"><span>页眉顶端距离(毫米)：</span></span>');
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
        const leftHeaderGroup=$(`<span><span style="vertical-align: top">左</span></span>`);
        headerGroup.append(leftHeaderGroup);
        this.leftHeaderEditor=$(`<textarea class="form-control" style="font-size:10pt;font-family:'宋体';padding: 5px;display: inline-block;width: 160px;height: 80px;margin-top: 15px"></textarea>`);
        leftHeaderGroup.append(this.leftHeaderEditor);
        this.leftHeaderEditor.change(function(){
            const text=$(this).val();
            checkGrammar(text,function(){
                _this.header.left=text;
                setDirty();
            });
        });
        const centerHeaderGroup=$(`<span style="margin-left: 15px;"><span style="vertical-align: top">中</span></span>`);
        headerGroup.append(centerHeaderGroup);
        this.centerHeaderEditor=$(`<textarea class="form-control" style="padding: 5px;font-size:10pt;font-family:'宋体';display: inline-block;width: 160px;height: 80px;margin-top: 15px"></textarea>`);
        centerHeaderGroup.append(this.centerHeaderEditor);
        this.centerHeaderEditor.change(function(){
            const text=$(this).val();
            checkGrammar(text,function(){
                _this.header.center=text;
                setDirty();
            });
        });
        const rightHeaderGroup=$(`<span style="margin-left: 15px;"><span style="vertical-align: top">右</span></span>`);
        headerGroup.append(rightHeaderGroup);
        this.rightHeaderEditor=$(`<textarea class="form-control" style="padding: 5px;font-size:10pt;font-family:'宋体';display: inline-block;width: 160px;height: 80px;margin-top: 15px"></textarea>`);
        rightHeaderGroup.append(this.rightHeaderEditor);
        this.rightHeaderEditor.change(function(){
            const text=$(this).val();
            checkGrammar(text,function(){
                _this.header.right=text;
                setDirty();
            });
        });

        const footerTitle=$(`<label style="margin-top: 10px;">页脚</label>`);
        headerFooterTab.append(footerTitle);
        const footerFontConfigButton=$(`<button type="button" class="btn btn-link" style="margin-left: 10px;">字体样式配置</button>`);
        headerFooterTab.append(footerFontConfigButton);

        const footerMarginGroup=$('<span style="margin-left:10px"><span>页脚底端距离(毫米)：</span></span>');
        headerFooterTab.append(footerMarginGroup);
        this.footerMarginEditor=$(`<input type="number" class="form-control" style="display: inline-block;width:100px;padding: 5px;height: 26px;">`);
        footerMarginGroup.append(this.footerMarginEditor);
        this.footerMarginEditor.change(function(){
            _this.footer.margin=mmToPoint($(this).val());
            setDirty();
        });

        const footerGroup=$(`<div class="form-group" style="margin-bottom: 5px"></div>`);
        headerFooterTab.append(footerGroup);
        const leftFooterGroup=$(`<span><span style="vertical-align: top">左</span></span>`);
        footerGroup.append(leftFooterGroup);
        this.leftFooterEditor=$(`<textarea class="form-control" style="padding: 5px;font-size:10pt;font-family:'宋体';display: inline-block;width: 160px;height: 80px;margin-top: 15px"></textarea>`);
        leftFooterGroup.append(this.leftFooterEditor);
        this.leftFooterEditor.change(function(){
            const text=$(this).val();
            checkGrammar(text,function(){
                _this.footer.left=text;
                setDirty();
            });
        });
        const centerFooterGroup=$(`<span style="margin-left: 15px;"><span style="vertical-align: top">中</span></span>`);
        footerGroup.append(centerFooterGroup);
        this.centerFooterEditor=$(`<textarea class="form-control" style="padding: 5px;font-size:10pt;font-family:'宋体';display: inline-block;width: 160px;height: 80px;margin-top: 15px"></textarea>`);
        centerFooterGroup.append(this.centerFooterEditor);
        this.centerFooterEditor.change(function(){
            const text=$(this).val();
            checkGrammar(text,function(){
                _this.footer.center=text;
                setDirty();
            });
        });
        const rightFooterGroup=$(`<span style="margin-left: 15px;"><span style="vertical-align: top">右</span></span>`);
        footerGroup.append(rightFooterGroup);
        this.rightFooterEditor=$(`<textarea class="form-control" style="padding: 5px;font-size:10pt;font-family:'宋体';display: inline-block;width: 160px;height: 80px;margin-top: 15px"></textarea>`);
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
        const group=$(`<div class="form-group" style="margin-top: 10px;height: 12px;"><label>分页方式：</label></div>`);
        pagingTab.append(group);
        this.fitPage=$(`<label class="checkbox-inline" style="padding-left: 5px">
            <input type="radio" name="pagingType" value="true"> 自动
        </label>`);
        group.append(this.fitPage);
        this.fixNum=$(`<label class="checkbox-inline" style="padding-left: 5px">
            <input type="radio" name="pagingType" value="true"> 固定行数
        </label>`);
        group.append(this.fixNum);

        const rowNumberGroup=$(`<span style="margin-left: 15px"><span>每页行数：</span></span>`);
        group.append(rowNumberGroup);
        rowNumberGroup.hide();
        this.rowNumberEditor=$(`<input type="number" class="form-control" style="display: inline-block;width: 80px;padding: 5px;height: 30px;">`);
        rowNumberGroup.append(this.rowNumberEditor);
        this.rowNumberEditor.change(function(){
            const value=parseInt($(this).val());
            if(value<1){
                alert("固定行数不能少于一行");
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
        columnTab.append(`<div style="margin-top: 12px;color:#999999;font-size: 12px">分栏效果在分页时显示，同时在打印、导出报表时采用。</div>`);
        const _this=this;
        const group=$(`<div class="form-group" style="margin-top: 8px;"><label>分栏：</label></div>`);
        columnTab.append(group);
        this.disabledColumnRadio=$(`<label class="checkbox-inline" style="padding-left: 5px">
            <input type="radio" name="useColumn" value="true"> 禁用
        </label>`);
        group.append(this.disabledColumnRadio);
        this.enabledColumnRadio=$(`<label class="checkbox-inline">
            <input type="radio" name="useColumn" value="true"> 启用
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
        const columnGroup=$(`<div class="form-group" style="margin-top: 1px;display: inline-block"><label>栏数：</label></div>`);
        columnTab.append(columnGroup);
        this.columnCountSelect=$(`<select class="form-control" style="display: inherit;width: inherit;padding-left: 5px">
            <option value="2">2栏</option>
            <option value="3">3栏</option>
            <option value="4">4栏</option>
            <option value="5">5栏</option>
            <option value="6">6栏</option>
            <option value="7">7栏</option>
            <option value="8">8栏</option>
            <option value="9">9栏</option>
            <option value="10">10栏</option>
        </select>`);
        columnGroup.append(this.columnCountSelect);
        this.columnCountSelect.change(function(){
            let value=$(this).val();
            if(!value || isNaN(value)){
                alert('请输入数字！');
                return;
            }
            _this.paper.columnCount=value;
            setDirty();
        });
        const columnMarginGroup=$(`<span style="margin-left: 20px"><label>栏间距(毫米)：</label></span>`);
        columnGroup.append(columnMarginGroup);
        this.columnMarginEditor=$(`<input type="number" class="form-control" style="width: 50px;display: inline-block">`);
        columnMarginGroup.append(this.columnMarginEditor);
        this.columnMarginEditor.change(function(){
            let value=$(this).val();
            if(!value || isNaN(value)){
                alert('请输入数字！');
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
                alert(`表达式存在语法错误：${msg}`);
            }else{
                callback.call(this);
            }
        },
        error:function(){
            alert("语法检查失败！");
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