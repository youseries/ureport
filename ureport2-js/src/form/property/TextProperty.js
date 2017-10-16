/**
 * Created by Jacky.Gao on 2017-10-16.
 */
import Property from './Property.js';
export default class TextProperty extends Property{
    constructor(report){
        super();
        this.init(report);
    }
    init(report){
        this.col.append(this.buildBindFieldGroup());
        this.positionLabelGroup=this.buildPositionLabelGroup();
        this.col.append(this.positionLabelGroup);
        this.col.append(this.buildLabelGroup());
        if(report){
            return;
        }
        var typeGroup=$("<div class='form-group'><label class='control-label'>输入类型</label></div>");
        this.col.append(typeGroup);
        this.typeSelect=$("<select class='form-control'>");
        this.typeSelect.append($("<option value='text'>文本</option>"));
        this.typeSelect.append($("<option value='digits'>整数</option>"));
        this.typeSelect.append($("<option value='numeric'>带小数的数字</option>"));
        this.typeSelect.append($("<option value='email'>电子邮件</option>"));
        typeGroup.append(this.typeSelect);
        var self=this;
        this.typeSelect.change(function(){
            self.current.setType($(this).val());
        });
        this.col.append(this.buildDefaultValueGroup());
        this.col.append(this.buildEnableGroup());
        this.col.append(this.buildVisibleGroup());
        this.col.append(this.buildRequiredGroup());
        this.col.append(this.buildStringLengthGroup());
    }
    refreshValue(current){
        super.refreshValue(current);
        if(this.typeSelect){
            this.typeSelect.val(current.editorType);
        }
    }
}
