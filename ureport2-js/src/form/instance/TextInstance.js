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
    setType(type){
        if(this.editorType===type){
            return;
        }
        this.editorType=type;
        this.isDigits=false;
        this.isEmail=false;
        if(type==="digits"){
            this.isDigits=true;
        }else if(type==="email"){
            this.isEmail=true;
        }else if(type==="numeric"){
            this.isNumeric=true;
        }
    }
    initFromJson(json){
        Instance.prototype.fromJson.call(this,json);
        this.editorType=json.editorType;
        if(json.searchOperator){
            this.searchOperator=json.searchOperator;
        }
    }
    toJSON(){
        var json=Instance.prototype.toJSON.call(this);
        json.editorType=this.editorType;
        json.type=TextInstance.TYPE;
        if(this.searchOperator){
            json.searchOperator=this.searchOperator;
        }
        return json;
    }
}
TextInstance.TYPE="Text";