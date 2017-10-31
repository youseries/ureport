/**
 * Created by Jacky.Gao on 2017-10-16.
 */
import Utils from '../Utils.js';
import CheckboxInstance from './CheckboxInstance.js';
export default class Checkbox{
    constructor(optionsInline){
        var seq=Utils.seq(Checkbox.ID);
        this.label="选项"+seq;
        this.value=this.label;
        this.checkbox=$("<input type='checkbox' value='"+this.value+"'>");
        var inlineClass=CheckboxInstance.LABEL_POSITION[0];
        if(optionsInline){
            inlineClass=CheckboxInstance.LABEL_POSITION[1];
        }
        this.element=$("<span class='"+inlineClass+"'></span>");
        this.element.append(this.checkbox);
        this.labelElement=$("<span style='margin-left: 15px'>"+this.label+"</span>");
        this.element.append(this.labelElement);
    }
    setValue(json){
        this.label=json.label;
        this.value=json.value;
        this.checkbox.prop("value",json.value);
        this.labelElement.html(json.label);
    }
    initFromJson(json){
        this.setValue(json);
    }
    toJson(){
        var json={
            value:this.value,
            label:this.label
        };
        return json;
    }
}
Checkbox.ID="Checkbox";