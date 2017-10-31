/**
 * Created by Jacky.Gao on 2017-10-16.
 */
import Instance from '../instance/Instance.js';
import Utils from '../Utils.js';
import Checkbox from './Checkbox.js';
export default class CheckboxInstance extends Instance{
    constructor(){
        super();
        var seq=Utils.seq(CheckboxInstance.ID);
        var label="复选框"+seq;
        this.element=this.newElement(label);
        this.inputElement=$("<div>");
        this.element.append(this.inputElement);
        this.options=[];
        this.optionsInline=false;
        this.element.uniqueId();
        this.id=this.element.prop("id");
        this.addOption();
        this.addOption();
        this.addOption();
    }
    setOptionsInline(optionsInline){
        if(optionsInline===this.optionsInline){
            return;
        }
        this.optionsInline=optionsInline;
        $.each(this.options,function(index,checkbox){
            var element=checkbox.element;
            element.removeClass();
            if(optionsInline){
                element.addClass(CheckboxInstance.LABEL_POSITION[1]);
                element.find("input").first().css("margin-left","");
            }else{
                element.addClass(CheckboxInstance.LABEL_POSITION[0]);
                element.find("input").first().css("margin-left","auto");
            }
        });
    }
    removeOption(option){
        var targetIndex;
        $.each(this.options,function(index,item){
            if(item===option){
                targetIndex=index;
                return false;
            }
        });
        this.options.splice(targetIndex,1);
        option.element.remove();
    }
    addOption(json){
        var checkbox=new Checkbox(this.optionsInline);
        if(json){
            checkbox.initFromJson(json);
        }
        this.options.push(checkbox);
        this.inputElement.append(checkbox.element);
        if(!this.optionsInline){
            checkbox.element.find("input").first().css("margin-left","auto");
        }
        return checkbox;
    }
    initFromJson(json){
        $.each(this.options,function(index,item){
            item.element.remove();
        });
        this.options.splice(0,this.options.length);
        super.fromJson(json);
        var options=json.options;
        for(var i=0;i<options.length;i++){
            this.addOption(options[i]);
        }
        if(json.optionsInline!==undefined){
            this.setOptionsInline(json.optionsInline);
        }
    }
    toJson(){
        const json={
            label:this.label,
            optionsInline:this.optionsInline,
            labelPosition:this.labelPosition,
            bindParameter:this.bindParameter,
            type:CheckboxInstance.TYPE,
            options:[]
        };
        for(let option of this.options){
            json.options.push(option.toJson());
        }
        return json;
    }
    toXml(){
        let xml=`<input-checkbox label="${this.label}" type="${CheckboxInstance.TYPE}" options-inline="${this.optionsInline === undefined ? false : this.optionsInline}" label-position="${this.labelPosition || 'top'}" bind-parameter="${this.bindParameter || ''}">`;
        for(let option of this.options){
            xml+=`<option label="${option.label}" value="${option.value}"></option>`;
        }
        xml+=`</input-checkbox>`;
        return xml;
    }
}
CheckboxInstance.TYPE="Checkbox";
CheckboxInstance.LABEL_POSITION=["checkbox","checkbox-inline"];
CheckboxInstance.ID="check_instance";