/**
 * Created by Jacky.Gao on 2015/12/4.
 */
export default class Option{
    constructor(label){
        this.label=label;
        this.value=label;
        this.element=$("<option value='"+label+"'>"+label+"</option>");
    }
    initFromJson(json){
        this.setValue(json);
    }
    toJson(){
        return {
            label:this.label,
            value:this.value
        };
    }
    setValue(json){
        this.value=json.value;
        this.element.prop("value",json.value);
        this.label=json.label;
        this.element.text(json.label);
    }
    remove(){
        this.element.remove();
    }
}