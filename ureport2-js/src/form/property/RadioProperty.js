/**
 * Created by Jacky.Gao on 2017-10-16.
 */
import Property from './Property.js';
export default class RadioProperty extends Property{
    constructor(){
        super();
        this.init();
    }
    init(){
        this.col.append(this.buildBindTableGroup());

        this.col.append(this.buildBindFieldGroup());
        this.positionLabelGroup=this.buildPositionLabelGroup();
        this.col.append(this.positionLabelGroup);
        this.col.append(this.buildLabelGroup());
        this.col.append(this.buildOptionsInlineGroup());
        this.col.append(this.buildDefaultValueGroup());
        this.col.append(this.buildEnableGroup());
        this.col.append(this.buildVisibleGroup());
        this.optionFormGroup=$("<div class='form-group'>");
        this.col.append(this.optionFormGroup);
        this.col.append(this.buildRequiredGroup());
    }
    addRadioEditor(radio){
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
            radio.setValue(json);
        });
        if(radio.label===radio.value){
            text.val(radio.label);
        }else{
            text.val(radio.label+","+radio.value);
        }
        var addon=$("<span class='input-group-addon'>");
        inputGroup.append(addon);
        var del=$("<span class='pb-icon-delete'><li class='glyphicon glyphicon-trash'></li></span>");
        del.click(function(){
            if(self.current.options.length===1){
                bootbox.alert("至少要保留一个选项!");
                return;
            }
            self.current.removeOption(radio);
            inputGroup.remove();
        });
        addon.append(del);
        var add=$("<span class='pb-icon-add' style='margin-left: 10px'><li class='glyphicon glyphicon-plus'></span>");
        add.click(function(){
            var newOption=self.current.addOption();
            self.addRadioEditor(newOption);
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
            self.addRadioEditor(checkbox);
        });
    }
}