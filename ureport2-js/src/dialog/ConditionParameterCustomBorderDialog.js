/**
 * Created by Jacky.Gao on 2017-02-07.
 */
import {alert} from '../MsgBox.js';

export default class ConditionParameterCustomBorderDialog{
    constructor(){
        this.dialog=$(`<div class="modal fade" role="dialog" aria-hidden="true" style="z-index: 11003">
            <div class="modal-dialog" style="width: 240px">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title">
                            自定义边框
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
        const container=$('<div></div>');
        body.append(container);
        const ul=$("<ul class='nav nav-tabs'></ul>");
        container.append(ul);
        const topLi=$("<li class='active'><a data-toggle='tab' href='#_topBorderConfig'>上</a></li>");
        ul.append(topLi);
        const bottomLi=$("<li><a data-toggle='tab' href='#_bottomBorderConfig'>下</a></li>");
        ul.append(bottomLi);
        const leftLi=$("<li><a data-toggle='tab' href='#_leftBorderConfig'>左</a></li>");
        ul.append(leftLi);
        const rightLi=$("<li><a data-toggle='tab' href='#_rightBorderConfig'>右</a></li>");
        ul.append(rightLi);

        const _this=this;

        const tabContent=$("<div class='tab-content'></div>");
        const topBorderConfig=$(`<div class="tab-pane fade in active" id="_topBorderConfig"></div>`);
        tabContent.append(topBorderConfig);

        let borderLineContainer=$(`<div style="margin: 20px 0 10px 0;"><span>线型：</span></div>`);
        topBorderConfig.append(borderLineContainer);
        this.topBorderLineList=$(`
                <select class="form-control" style="display: inline-block;width:120px">
                    <option value="solid">实线</option>
                    <option value="dashed">虚线</option>
                    <option value="none">无</option>
                </select>
            `);
        borderLineContainer.append(this.topBorderLineList);
        this.topBorderLineList.change(function(){
            _this.cellStyle.topBorder.style=$(this).val();
        });

        let borderSizeContainer=$(`<div style="margin: 20px 0 10px 0;"><span>尺寸：</span></div>`);
        topBorderConfig.append(borderSizeContainer);
        this.topBorderSizeList=$(`
                <select class="form-control" style="display: inline-block;width:120px">
                </select>
            `);
        for(let i=1;i<=10;i++){
            this.topBorderSizeList.append(`<option value="${i}">${i}</option>`);
        }
        borderSizeContainer.append(this.topBorderSizeList);
        this.topBorderSizeList.change(function(){
            _this.cellStyle.topBorder.width=$(this).val();
        });

        let borderColor=$(`<div></div>`);
        topBorderConfig.append(borderColor);
        this.topBorderColorContainer=$("<div><span>颜色：</span></div>");
        borderColor.append(this.topBorderColorContainer);
        this.topBorderColorContainer.colorpicker({
            container: true,
            inline: true,
            colorSelectors: {
                'black': '#000000',
                'red': '#FF0000',
                'default': '#777777',
                'primary': '#337ab7',
                'success': '#5cb85c',
                'info': '#5bc0de',
                'warning': '#f0ad4e',
                'danger': '#d9534f'
            }
        });
        this.topBorderColorContainer.colorpicker().on("changeColor",function(e){
            let rgb=e.color.toRGB();
            let color=rgb.r+","+rgb.g+","+rgb.b;
            _this.cellStyle.topBorder.color=color;
        });


        const bottomBorderConfig=$(`<div class="tab-pane fade" id="_bottomBorderConfig"></div>`);
        tabContent.append(bottomBorderConfig);

        borderLineContainer=$(`<div style="margin: 20px 0 10px 0;"><span>线型：</span></div>`);
        bottomBorderConfig.append(borderLineContainer);
        this.bottomBorderLineList=$(`
                <select class="form-control" style="display: inline-block;width:120px">
                    <option value="solid">实线</option>
                    <option value="dashed">虚线</option>
                    <option value="none">无</option>
                </select>
            `);
        borderLineContainer.append(this.bottomBorderLineList);
        this.bottomBorderLineList.change(function(){
            _this.cellStyle.bottomBorder.style=$(this).val();
        });

        borderSizeContainer=$(`<div style="margin: 20px 0 10px 0;"><span>尺寸：</span></div>`);
        bottomBorderConfig.append(borderSizeContainer);
        this.bottomBorderSizeList=$(`
                <select class="form-control" style="display: inline-block;width:120px">
                </select>
            `);
        for(let i=1;i<=10;i++){
            this.bottomBorderSizeList.append(`<option value="${i}">${i}</option>`);
        }
        borderSizeContainer.append(this.bottomBorderSizeList);
        this.bottomBorderSizeList.change(function(){
            _this.cellStyle.bottomBorder.width=$(this).val();
        });

        borderColor=$(`<div></div>`);
        bottomBorderConfig.append(borderColor);
        this.bottomBorderColorContainer=$("<div><span>颜色：</span></div>");
        borderColor.append(this.bottomBorderColorContainer);
        this.bottomBorderColorContainer.colorpicker({
            container: true,
            inline: true,
            colorSelectors: {
                'black': '#000000',
                'red': '#FF0000',
                'default': '#777777',
                'primary': '#337ab7',
                'success': '#5cb85c',
                'info': '#5bc0de',
                'warning': '#f0ad4e',
                'danger': '#d9534f'
            }
        });
        this.bottomBorderColorContainer.colorpicker().on("changeColor",function(e){
            let rgb=e.color.toRGB();
            let color=rgb.r+","+rgb.g+","+rgb.b;
            _this.cellStyle.bottomBorder.color=color;
        });

        const leftBorderConfig=$(`<div class="tab-pane fade" id="_leftBorderConfig"></div>`);
        tabContent.append(leftBorderConfig);

        borderLineContainer=$(`<div style="margin: 20px 0 10px 0;"><span>线型：</span></div>`);
        leftBorderConfig.append(borderLineContainer);
        this.leftBorderLineList=$(`
                <select class="form-control" style="display: inline-block;width:120px">
                    <option value="solid">实线</option>
                    <option value="dashed">虚线</option>
                    <option value="none">无</option>
                </select>
            `);
        borderLineContainer.append(this.leftBorderLineList);
        this.leftBorderLineList.change(function(){
            _this.cellStyle.leftBorder.style=$(this).val();
        });

        borderSizeContainer=$(`<div style="margin: 20px 0 10px 0;"><span>尺寸：</span></div>`);
        leftBorderConfig.append(borderSizeContainer);
        this.leftBorderSizeList=$(`
                <select class="form-control" style="display: inline-block;width:120px">
                </select>
            `);
        for(let i=1;i<=10;i++){
            this.leftBorderSizeList.append(`<option value="${i}">${i}</option>`);
        }
        borderSizeContainer.append(this.leftBorderSizeList);
        this.leftBorderSizeList.change(function(){
            _this.cellStyle.leftBorder.width=$(this).val();
        });

        borderColor=$(`<div></div>`);
        leftBorderConfig.append(borderColor);
        this.leftBorderColorContainer=$("<div><span>颜色：</span></div>");
        borderColor.append(this.leftBorderColorContainer);
        this.leftBorderColorContainer.colorpicker({
            container: true,
            inline: true,
            colorSelectors: {
                'black': '#000000',
                'red': '#FF0000',
                'default': '#777777',
                'primary': '#337ab7',
                'success': '#5cb85c',
                'info': '#5bc0de',
                'warning': '#f0ad4e',
                'danger': '#d9534f'
            }
        });
        this.leftBorderColorContainer.colorpicker().on("changeColor",function(e){
            let rgb=e.color.toRGB();
            let color=rgb.r+","+rgb.g+","+rgb.b;
            _this.cellStyle.leftBorder.color=color;
        });

        const rightBorderConfig=$(`<div class="tab-pane fade" id="_rightBorderConfig"></div>`);
        tabContent.append(rightBorderConfig);
        borderLineContainer=$(`<div style="margin: 20px 0 10px 0;"><span>线型：</span></div>`);
        rightBorderConfig.append(borderLineContainer);
        this.rightBorderLineList=$(`
                <select class="form-control" style="display: inline-block;width:120px">
                    <option value="solid">实线</option>
                    <option value="dashed">虚线</option>
                    <option value="none">无</option>
                </select>
            `);
        borderLineContainer.append(this.rightBorderLineList);
        this.rightBorderLineList.change(function(){
            _this.cellStyle.rightBorder.style=$(this).val();
        });

        borderSizeContainer=$(`<div style="margin: 20px 0 10px 0;"><span>尺寸：</span></div>`);
        rightBorderConfig.append(borderSizeContainer);
        this.rightBorderSizeList=$(`
                <select class="form-control" style="display: inline-block;width:120px">
                </select>
            `);
        for(let i=1;i<=10;i++){
            this.rightBorderSizeList.append(`<option value="${i}">${i}</option>`);
        }
        borderSizeContainer.append(this.rightBorderSizeList);
        this.rightBorderSizeList.change(function(){
            _this.cellStyle.rightBorder.width=$(this).val();
        });

        borderColor=$(`<div></div>`);
        rightBorderConfig.append(borderColor);
        this.rightBorderColorContainer=$("<div><span>颜色：</span></div>");
        borderColor.append(this.rightBorderColorContainer);
        this.rightBorderColorContainer.colorpicker({
            container: true,
            inline: true,
            colorSelectors: {
                'black': '#000000',
                'red': '#FF0000',
                'default': '#777777',
                'primary': '#337ab7',
                'success': '#5cb85c',
                'info': '#5bc0de',
                'warning': '#f0ad4e',
                'danger': '#d9534f'
            }
        });
        this.rightBorderColorContainer.colorpicker().on("changeColor",function(e){
            let rgb=e.color.toRGB();
            let color=rgb.r+","+rgb.g+","+rgb.b;
            _this.cellStyle.rightBorder.color=color;
        });

        container.append(tabContent);
    }
    show(cellStyle){
        this.cellStyle=cellStyle;
        this.dialog.modal('show');
        const topBorder=cellStyle.topBorder;
        this.topBorderSizeList.val(topBorder.width);
        this.topBorderLineList.val(topBorder.style);
        this.topBorderColorContainer.colorpicker("setValue","rgb("+topBorder.color+")");

        const bottomBorder=cellStyle.bottomBorder;
        this.bottomBorderSizeList.val(bottomBorder.width);
        this.bottomBorderLineList.val(bottomBorder.style);
        this.bottomBorderColorContainer.colorpicker("setValue","rgb("+bottomBorder.color+")");

        const leftBorder=cellStyle.leftBorder;
        this.leftBorderSizeList.val(leftBorder.width);
        this.leftBorderLineList.val(leftBorder.style);
        this.leftBorderColorContainer.colorpicker("setValue","rgb("+leftBorder.color+")");

        const rightBorder=cellStyle.rightBorder;
        this.rightBorderSizeList.val(rightBorder.width);
        this.rightBorderLineList.val(rightBorder.style);
        this.rightBorderColorContainer.colorpicker("setValue","rgb("+rightBorder.color+")");
    }
}

