/**
 * Created by Jacky.Gao on 2017-10-16.
 */
import Utils from '../Utils.js';
import CheckboxInstance from './CheckboxInstance.js';
export default class Radio{
    constructor(optionsInline){
        var seq=Utils.seq(Radio.ID);
        this.label="选项"+seq;
        this.value=this.label;
        this.radio=$("<input type='radio'>");
        var inlineClass=CheckboxInstance.LABEL_POSITION[0];
        if(optionsInline){
            inlineClass=CheckboxInstance.LABEL_POSITION[1];
        }
        this.element=$("<span class='"+inlineClass+"'></span>");
        this.element.append(this.radio);
        this.labelElement=$("<span>"+this.label+"</span>");
        this.element.append(this.labelElement);
    }
    setValue(json){
        this.label=json.label;
        this.value=json.value;
        this.radio.prop("value",this.value);
        this.labelElement.html(json.label);
    }
    initFromJson(json){
        this.setValue(json);
    }
    toJson(){
        return {label:this.label,value:this.value};
    }
}
Radio.ID="Radio";