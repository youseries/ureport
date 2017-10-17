/**
 * Created by Jacky.Gao on 2017-10-16.
 */
import Property from './Property.js';
export default class CheckboxProperty extends Property{
    constructor(){
        super();
        this.init();
    }
    init(){
        this.col.append(this.buildBindTableGroup());

        this.positionLabelGroup=this.buildPositionLabelGroup();
        this.col.append(this.positionLabelGroup);
        this.col.append(this.buildLabelGroup());
        this.col.append(this.buildDefaultValueGroup());
        this.col.append(this.buildEnableGroup());
        this.col.append(this.buildVisibleGroup());
        this.col.append(this.buildOptionsInlineGroup());
        this.optionFormGroup=$("<div class='form-group'>");
        this.col.append(this.optionFormGroup);
        this.col.append(this.buildRequiredGroup());
        var checkboxlimitGroup=$("<div class='form-group'>");
        this.col.append(checkboxlimitGroup);
        var checkboxLimitDiv=$("<div class='checkbox'>");
        this.checkboxLimit=$("<input type='checkbox' style='margin-left: auto'><label>勾选数量限制</label>");
        checkboxLimitDiv.append(this.checkboxLimit);
        checkboxlimitGroup.append(checkboxLimitDiv);
        var self=this;
        this.checkboxLimit.change(function(){
            if($(this).prop("checked")===true){
                self.limitGroup.show();
            }else{
                self.limitGroup.hide();
            }
            self.current.isCheckbox=$(this).prop("checked");
        });

        this.limitGroup=$("<div class='input-group'>");
        checkboxlimitGroup.append(this.limitGroup);
        var minTextAddOn=$("<span class='input-group-addon'>最小</span>");
        this.limitGroup.append(minTextAddOn);
        this.minText=$("<input type='text' class='form-control'>");
        this.limitGroup.append(this.minText);
        this.minText.change(function(){
            self.current.checkboxMin=$(this).val();
        });
        var maxTextAddOn=$("<span class='input-group-addon'>最大</span>");
        this.limitGroup.append(maxTextAddOn);
        this.maxText=$("<input type='text' class='form-control'>");
        this.limitGroup.append(this.maxText);
        this.maxText.change(function(){
            self.current.checkboxMax=$(this).val();
        });
        this.limitGroup.hide();
    }
    addCheckboxEditor(checkbox){
        var self=this;
        var inputGroup=$("<div class='input-group'>");
        var text=$("<input type='text' class='form-control'>");
        inputGroup.append(text);
        text.change(function(){
            var value=$(this).val();
            var json={value:value,label:value};
            var array=value.split(",");
            if(array.length==2){
                json.label=array[0];
                json.value=array[1];
            }
            checkbox.setValue(json);
        });
        if(checkbox.label===checkbox.value){
            text.val(checkbox.label);
        }else{
            text.val(checkbox.label+","+checkbox.value);
        }
        var addon=$("<span class='input-group-addon'>");
        inputGroup.append(addon);
        var del=$("<span class='pb-icon-delete'><li class='glyphicon glyphicon-trash'></li></span>");
        del.click(function(){
            if(self.current.options.length===1){
                bootbox.alert("至少要保留一个选项!");
                return;
            }
            self.current.removeOption(checkbox);
            inputGroup.remove();
        });
        addon.append(del);
        var add=$("<span class='pb-icon-add' style='margin-left: 10px'><li class='glyphicon glyphicon-plus'></span>");
        add.click(function(){
            var newOption=self.current.addOption();
            self.addCheckboxEditor(newOption);
        });
        addon.append(add);
        this.optionFormGroup.append(inputGroup);
    }
    refreshValue(current){
        super.refreshValue(current);
        this.optionFormGroup.empty();
        this.optionFormGroup.append($("<label>选项(若显示值与实际值不同，则用“,”分隔，如“是,true”等)</label>"));
        var self=this;
        $.each(this.current.options,function(index,checkbox){
            self.addCheckboxEditor(checkbox);
        });
        if(current.isCheckbox){
            this.checkboxLimit.prop("checked",true);
            this.limitGroup.show();
        }
        this.maxText.val(current.checkboxMax);
        this.minText.val(current.checkboxMin);
    }
}