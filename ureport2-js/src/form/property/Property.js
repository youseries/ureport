/**
 * Created by Jacky.Gao on 2017-10-12.
 */
export default class Property{
    constructor(){
        this.propertyContainer=$("<div class='row'>");
        this.col=$("<div class='col-md-12'>");
        this.propertyContainer.append(this.col);
    }
    buildOptionsInlineGroup(){
        const inlineGroup=$("<div class='form-group'><label class='control-label'>选项换行显示</label></div>");
        this.optionsInlineSelect=$("<select class='form-control'>");
        this.optionsInlineSelect.append($("<option value='0'>是</option>"));
        this.optionsInlineSelect.append($("<option value='1'>否</option>"));
        inlineGroup.append(this.optionsInlineSelect);
        const self=this;
        this.optionsInlineSelect.change(function(){
            let value=false;
            if($(this).val()==="1"){
                value=true;
            }
            self.current.setOptionsInline(value);
        });
        return inlineGroup;
    }
    buildBindParameter(){
        const group=$("<div class='form-group'><label>绑定的查询参数</label></div>");
        this.bindFieldEditor=$("<input type='text' class='form-control'>");
        group.append(this.bindFieldEditor);
        const self=this;
        this.bindFieldEditor.change(function(){
            const value=$(this).val();
            self.current.setBindParameter(value);
        });
        return group;
    }
    buildLabelGroup(){
        const labelGroup=$("<div class='form-group'>");
        const labelLabel=$("<label>标题</label>");
        labelGroup.append(labelLabel);
        this.textLabel=$("<input type='text' class='form-control'>");
        const self=this;
        this.textLabel.change(function(){
            self.current.setLabel($(this).val());
        });
        labelGroup.append(this.textLabel);
        return labelGroup;
    }
    buildPositionLabelGroup(){
        const positionLabelGroup=$("<div class='form-group'>");
        const positionLabel=$("<label class='control-label'>标题位置</label>");
        positionLabelGroup.append(positionLabel);
        this.positionLabelSelect=$("<select class='form-control'>");
        positionLabelGroup.append(this.positionLabelSelect);
        this.positionLabelSelect.append("<option value='top' selected>上边</option>");
        this.positionLabelSelect.append("<option value='left'>左边</option>");
        const self=this;
        this.positionLabelSelect.change(function(){
            self.current.setLabelPosition($(this).val());
        });
        return positionLabelGroup;
    }

    refreshValue(instance){
        this.current=instance;
        if(this.optionsInlineSelect){
            if(instance.optionsInline){
                this.optionsInlineSelect.val("1");
            }else{
                this.optionsInlineSelect.val("0");
            }
        }
        this.positionLabelSelect.val(instance.labelPosition);
        this.textLabel.val(instance.label);
        this.bindFieldEditor.val(instance.bindParameter);
    }
}