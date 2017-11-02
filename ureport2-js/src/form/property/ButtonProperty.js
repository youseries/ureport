/**
 * Created by Jacky.Gao on 2017-10-20.
 */
import Property from './Property.js';

export default class ButtonProperty extends Property{
    constructor(){
        super();
        const _this=this;
        this.buttonType=$(`<div class="form-group"></div>`);
        this.col.append(this.buttonType);
        const labelGroup=$(`<div class="form-group"><label>按钮标题</label></div>`);
        this.col.append(labelGroup);
        this.labelEditor=$(`<input type="text" class="form-control">`);
        this.labelEditor.change(function(){
            _this.current.setLabel($(this).val());
        });
        labelGroup.append(this.labelEditor);

        const selectGroup=$("<div class=\"form-group\"><label>按钮风格</label></div>");
        this.col.append(selectGroup);
        this.typeSelect=$("<select class='form-control'>");
        selectGroup.append(this.typeSelect);
        this.typeSelect.append("<option value='btn-default'>默认</option>");
        this.typeSelect.append("<option value='btn-primary'>基本</option>");
        this.typeSelect.append("<option value='btn-success'>成功</option>");
        this.typeSelect.append("<option value='btn-info'>信息</option>");
        this.typeSelect.append("<option value='btn-warning'>警告</option>");
        this.typeSelect.append("<option value='btn-danger'>危险</option>");
        this.typeSelect.append("<option value='btn-link'>链接</option>");
        this.typeSelect.change(function(){
            const style=$(this).children("option:selected").val();
            _this.current.setStyle(style);
        });

        const alignGroup=$(`<div class="form-group"><label>对齐方式</label></div>`);
        this.col.append(alignGroup);
        this.alignSelect=$(`<select class="form-control">
            <option value="left">左对齐</option>
            <option value="right">右对齐</option>
        </select>`);
        alignGroup.append(this.alignSelect);
        this.alignSelect.change(function(){
            _this.current.setAlign($(this).val());
        });
    }
    refreshValue(current){
        this.current=current;
        this.labelEditor.val(current.label);
        this.typeSelect.val(current.style);
        if(current.editorType==='reset-button'){
            this.buttonType.html("重置按钮");
        }else{
            this.buttonType.html("提交按钮");
        }
    }
}