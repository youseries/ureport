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
        Instance.prototype.fromJson.call(this,json);
        var options=json.options;
        for(var i=0;i<options.length;i++){
            this.addOption(options[i]);
        }
        this.setOptionsInline(json.optionsInline);
    }
    toJSON(){
        var json=Instance.prototype.toJSON.call(this);
        json.type=CheckboxInstance.TYPE;
        json.optionsInline=this.optionsInline;
        var options=[];
        $.each(this.options,function(index,option){
            options.push(option.toJSON());
        });
        json.options=options;
        return json;
    }
}
CheckboxInstance.TYPE="Checkbox";
CheckboxInstance.LABEL_POSITION=["checkbox","checkbox-inline"];
CheckboxInstance.ID="check_instance";