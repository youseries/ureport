/**
 * Created by Jacky.Gao on 2017-10-16.
 */
import Instance from './Instance.js';
export default class TextInstance extends Instance{
    constructor(label){
        super();
        this.element=this.newElement(label);
        this.inputElement=$("<div>");
        this.element.append(this.inputElement);
        this.textInput=$("<input type=\"text\" class=\"form-control\">");
        this.inputElement.append(this.textInput);
        this.element.uniqueId();
        this.id=this.element.prop("id");
        this.editorType="text";
    }
    initFromJson(json){
        super.fromJson(json);
        this.editorType=json.editorType;
        if(json.searchOperator){
            this.searchOperator=json.searchOperator;
        }
    }
    toJson(){
        const json={
            label:this.label,
            optionsInline:this.optionsInline,
            labelPosition:this.labelPosition,
            bindParameter:this.bindParameter,
            type:TextInstance.TYPE
        };
        return json;
    }
    toXml(){
        const xml=`<input-text label="${this.label}" type="${TextInstance.TYPE}" label-position="${this.labelPosition || 'top'}" bind-parameter="${this.bindParameter || ''}"></input-text>`;
        return xml;
    }
}
TextInstance.TYPE="Text";