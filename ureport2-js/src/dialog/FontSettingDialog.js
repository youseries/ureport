/**
 * Created by Jacky.Gao on 2017-02-07.
 */
import {alert} from '../MsgBox.js';
import {setDirty} from '../Utils.js';

export default class FontSettingDialog{
    constructor(datasources){
        this.datasources=datasources;
        this.dialog=$(`<div class="modal fade" role="dialog" aria-hidden="true" style="z-index: 11005">
            <div class="modal-dialog" style="width: 320px;">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title">
                            ${window.i18n.dialog.fontSetting.title}
                        </h4>
                    </div>
                    <div class="modal-body"></div>
                    <div class="modal-footer">
                    </div>
                </div>
            </div>
        </div>`);
        const body=this.dialog.find('.modal-body'),footer=this.dialog.find(".modal-footer");
        this.initBody(body,footer);
    }
    initBody(body,footer){
        const _this=this;
        const fontFamilyGroup=$(`<div class="form-group" style="margin-top: 10px"><label>${window.i18n.dialog.fontSetting.font}</label></div>`);
        body.append(fontFamilyGroup);
        const fonts=[];
        fonts.push("宋体");
        fonts.push("仿宋");
        fonts.push("黑体");
        fonts.push("楷体");
        fonts.push("微软雅黑");
        fonts.push("Arial");
        fonts.push("Impact");
        fonts.push("Times New Roman");
        fonts.push("Comic Sans MS");
        fonts.push("Courier New");
        this.fontFamilySelect=$(`<select class="form-control" style="display: inline-block;width: 200px;"></select>`);
        for(let font of fonts){
            this.fontFamilySelect.append(`<option>${font}</option>`);
        }
        fontFamilyGroup.append(this.fontFamilySelect);
        this.fontFamilySelect.change(function(){
            _this.newStyle.fontFamily=$(this).val();
        });

        const colorGroup=$(`<div class="form-group"><label>${window.i18n.dialog.fontSetting.color}</label></div>`);
        body.append(colorGroup);
        this.colorEditor=$(`<div class="input-group colorpicker-component" style="width: 200px;height: 26px;display: inline-block;margin-left: 10px;vertical-align: bottom">
            <input type="text" value="#00AABB" class="form-control" style="width: 150px;height: 29px;"/>
            <span class="input-group-addon" style="width: 22px"><i></i></span>
        </div>`);
        colorGroup.append(this.colorEditor);
        this.colorEditor.colorpicker({
            color: '#000000',
            container: true,
            format:'rgb',
            colorSelectors: {
                'black': '#000000',
                'white': '#FFFFFF',
                'red': '#FF0000',
                'default': '#777777',
                'primary': '#337ab7',
                'success': '#5cb85c',
                'info': '#5bc0de',
                'warning': '#f0ad4e',
                'danger': '#d9534f'
            }
        });
        this.colorEditor.children('input').change(function(){
            let val=$(this).val();
            if(val.length>5){
                val=val.substring(4,val.length-1);
            }
            _this.newStyle.forecolor=val;
        });

        const fontSizeGroup=$(`<div class="form-group"><label>${window.i18n.dialog.fontSetting.size}</label></div>`);
        body.append(fontSizeGroup);
        this.fontSizeSelect=$(`<select class="form-control" style="display: inline-block;width: 200px;"></select>`);
        for(let i=1;i<=100;i++){
            this.fontSizeSelect.append(`<option>${i}</option>`);
        }
        fontSizeGroup.append(this.fontSizeSelect);
        this.fontSizeSelect.change(function(){
            _this.newStyle.fontSize=$(this).val();
        });

        const boldGroup=$(`<div class="form-group"><label>${window.i18n.dialog.fontSetting.bold}</label></div>`);
        body.append(boldGroup);
        this.boldSelect=$(`<select class="form-control" style="display: inline-block;width: 200px;">
            <option value="true">是</option>
            <option value="false">否</option>
        </select>`);
        boldGroup.append(this.boldSelect);
        this.boldSelect.change(function(){
            _this.newStyle.bold=$(this).val();
        });

        const italicGroup=$(`<div class="form-group"><label>${window.i18n.dialog.fontSetting.italic}</label></div>`);
        body.append(italicGroup);
        this.italicSelect=$(`<select class="form-control" style="display: inline-block;width: 200px;">
            <option value="true">是</option>
            <option value="false">否</option>
        </select>`);
        italicGroup.append(this.italicSelect);
        this.italicSelect.change(function(){
            _this.newStyle.italic=$(this).val();
        });

        const underlineGroup=$(`<div class="form-group"><label>${window.i18n.dialog.fontSetting.underline}</label></div>`);
        body.append(underlineGroup);
        this.underlineSelect=$(`<select class="form-control" style="display: inline-block;width: 185px;">
            <option value="true">是</option>
            <option value="false">否</option>
        </select>`);
        underlineGroup.append(this.underlineSelect);
        this.underlineSelect.change(function(){
            _this.newStyle.underline=$(this).val();
        });

        const confirmButton=$(`<button type="button" class="btn btn-primary">${window.i18n.dialog.fontSetting.ok}</button>`);
        footer.append(confirmButton);
        confirmButton.click(function(){
            _this.style=_this.newStyle;
            _this.callback.call(this,_this.newStyle);
            _this.dialog.modal('hide');
        });
    }
    show(style,callback){
        this.dialog.modal('show');
        this.callback=callback;
        this.style=style;
        this.newStyle=style;
        if(!this.newStyle.fontFamily){
            this.newStyle.fontFamily='宋体';
        }
        if(!this.newStyle.fontSize){
            this.newStyle.fontSize='10';
        }
        if(!this.newStyle.forecolor){
            this.newStyle.forecolor='0,0,0';
        }
        if(this.newStyle.bold===undefined || this.newStyle.bold===null){
            this.newStyle.bold="false";
        }
        if(this.newStyle.italic===undefined || this.newStyle.italic===null){
            this.newStyle.italic="false";
        }
        if(this.newStyle.underline===undefined || this.newStyle.underline===null){
            this.newStyle.underline="false";
        }

        this.fontFamilySelect.val(this.newStyle.fontFamily);
        this.fontSizeSelect.val(this.newStyle.fontSize);
        this.colorEditor.children('input').val("rgb("+this.newStyle.forecolor+")");
        this.colorEditor.children('input').trigger('change');
        this.boldSelect.val(this.newStyle.bold);
        this.italicSelect.val(this.newStyle.italic);
        this.underlineSelect.val(this.newStyle.underline);
    }
}