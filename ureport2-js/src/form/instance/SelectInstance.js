/**
 * Created by Jacky.Gao on 2017-10-20.
 */
import Instance from './Instance.js';
import Option from './Option.js';
export default class SelectInstance extends Instance{
    constructor(seq){
        super();
        var label="单选列表"+seq;
        this.element=this.newElement(label);
        this.inputElement=$("<div>");
        this.select=$("<select class='form-control'>");
        this.inputElement.append(this.select);
        this.element.append(this.inputElement);
        this.options=[];
        this.optionNum=1;
        for(var i=1;i<5;i++){
            this.addOption();
        }
        this.element.uniqueId();
        this.id=this.element.prop("id");
    }
    addOption(json){
        var option=new Option("选项"+(this.optionNum++));
        if(json){
            option.initFromJson(json);
        }
        this.options.push(option);
        this.select.append(option.element);
        return option;
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
        option.remove();
    }
    initFromJson(json){
        $.each(this.options,function(index,item){
            item.element.remove();
        });
        this.options.splice(0,this.options.length);
        super.fromJson(json);
        if(json.searchOperator){
            this.searchOperator=json.searchOperator;
        }
        var options=json.options;
        for(var i=0;i<options.length;i++){
            this.addOption(options[i]);
        }
        this.useDataset=json.useDataset;
        this.dataset=json.dataset;
        this.labelField=json.labelField;
        this.valueField=json.valueField;
    }
    toJson(){
        const json={
            label:this.label,
            optionsInline:this.optionsInline,
            labelPosition:this.labelPosition,
            bindParameter:this.bindParameter,
            type:SelectInstance.TYPE,
            useDataset:this.useDataset,
            dataset:this.dataset,
            labelField:this.labelField,
            valueField:this.valueField,
            options:[]
        };
        for(let option of this.options){
            json.options.push(option.toJson());
        }
        return json;
    }
    toXml(){
        let xml=`<input-select label="${this.label}" type="${SelectInstance.TYPE}" label-position="${this.labelPosition || 'top'}" bind-parameter="${this.bindParameter || ''}"`;
        if(this.useDataset){
            xml+=` use-dataset="${this.useDataset}" dataset="${this.dataset}" label-field="${this.labelField}" value-field="${this.valueField}"`;
        }
        xml+='>';
        for(let option of this.options || []){
            xml+=`<option label="${option.label}" value="${option.value}"></option>`;
        }
        xml+=`</input-select>`;
        return xml;
    }
}
SelectInstance.TYPE="Select";