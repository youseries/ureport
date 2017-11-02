/**
 * Created by Jacky.Gao on 2017-10-12.
 */
import Utils from '../Utils.js';

export default class Instance{
    constructor(){
        this.labelPosition=Instance.TOP;
        this.enable="true";
        this.visible="true";
    }
    newElement(label){
        this.element=$("<div class='form-group row' style='margin:0px'>");
        this.label=label;
        this.labelElement=$("<span class='control-label' style='font-size: 13px'></span>");
        this.element.append(this.labelElement);
        this.labelElement.text(label);
        return this.element;
    }
    setLabel(label){
        this.label=label;
        if(this.isRequired){
            this.labelElement.html(this.label+"<span style='color:red'>*</span>");
        }else{
            this.labelElement.html(this.label);
        }
    }
    setLabelPosition(position){
        if(this.labelPosition===position){
            return;
        }
        this.labelPosition=position;
        if(position===Instance.TOP){
            this.labelElement.removeClass(Instance.POS_CLASSES[0]);
            this.inputElement.removeClass(Instance.POS_CLASSES[1]);
        }else if(position===Instance.LEFT){
            this.labelElement.addClass(Instance.POS_CLASSES[0]);
            this.inputElement.addClass(Instance.POS_CLASSES[1]);
        }
    }
    setBindParameter(bindParameter){
        this.bindParameter=bindParameter;
    }
    getElementId(){
        if(Utils.binding){
            if(!this.bindTableName){
                this.bindTableName=formBuilder.bindTable.name;
            }
            if(this.bindTableName && this.bindField){
                return this.bindTableName+"."+this.bindField;
            }
            return null;
        }else{
            return this.label;
        }
    }
    fromJson(json){
        this.setLabel(json.label);
        this.setLabelPosition(json.labelPosition);
        this.setBindParameter(json.bindParameter);
    }
    initFromJson(json){

    }
}
Instance.LEFT="left";
Instance.TOP="top";
Instance.POS_CLASSES=["col-md-3","col-md-9"];