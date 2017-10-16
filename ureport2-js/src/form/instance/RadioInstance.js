/**
 * Created by Jacky.Gao on 2017-10-16.
 */
import Instance from './Instance.js';
import Utils from '../Utils.js';
import Radio from './Radio.js';
export default class RadioInstance extends Instance{
    constructor(seq){
        super();
        this.seq=Utils.seq(RadioInstance.ID);
        this.label="单选框"+this.seq;
        this.element=this.newElement(this.label);
        this.inputElement=$("<div>");
        this.element.append(this.inputElement);
        this.options=[];
        this.element.uniqueId();
        this.id=this.element.prop("id");
        this.optionsInline=false;
        this.addOption();
        this.addOption();
        this.addOption();
    }
    setOptionsInline(optionsInline){
        if(optionsInline===this.optionsInline){
            return;
        }
        this.optionsInline=optionsInline;
        $.each(this.options,function(index,radio){
            var element=radio.element;
            element.removeClass();
            if(optionsInline){
                element.addClass(RadioInstance.LABEL_POSITION[1]);
                element.css("padding-left","0px");
            }else{
                element.addClass(RadioInstance.LABEL_POSITION[0]);
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
        var radio=new Radio(this.optionsInline);
        if(json){
            radio.initFromJson(json);
        }
        this.options.push(radio);
        this.inputElement.append(radio.element);
        var input=radio.element.find("input").first();
        if(!this.optionsInline){
            input.css("margin-left","auto");
        }
        input.prop("name","radiooption"+this.seq);
        return radio;
    }
    initFromJson(json){
        $.each(this.options,function(index,item){
            item.element.remove();
        });
        this.options.splice(0,this.options.length);
        Instance.prototype.fromJson.call(this,json);
        var radios=json.radios;
        for(var i=0;i<radios.length;i++){
            this.addOption(radios[i]);
        }
        this.setOptionsInline(json.optionsInline);
    }
    toJSON(){
        var json=Instance.prototype.toJSON.call(this);
        json.type=RadioInstance.TYPE;
        json.optionsInline=this.optionsInline;
        var radios=[];
        $.each(this.options,function(index,item){
            radios.push(item.toJSON());
        });
        json.radios=radios;
        return json;
    }
}
RadioInstance.TYPE="Radio";
RadioInstance.LABEL_POSITION=["checkbox","checkbox-inline"];
RadioInstance.ID="radio_instance";