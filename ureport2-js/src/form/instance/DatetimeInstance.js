/**
 * Created by Jacky.Gao on 2017-10-23.
 */
import Instance from './Instance.js';
import Utils from '../Utils.js';
export default class DatetimeInstance extends Instance{
    constructor(){
        super();
        this.isDate=true;
        var seq=Utils.seq(DatetimeInstance.ID);
        var label="日期选择"+seq;
        this.element=this.newElement(label);
        this.dateFormat="YYYY-MM-DD";
        this.inputElement=$("<div>");
        this.element.append(this.inputElement);
        this.datePickerinputGroup=$("<div class='input-group date'>");
        this.inputElement.append(this.datePickerinputGroup);
        var text=$("<input type='text' class='form-control'>");
        this.datePickerinputGroup.append(text);
        var pickerIcon=$("<span class='input-group-addon'><span class='glyphicon glyphicon-calendar'></span></span>");
        this.datePickerinputGroup.append(pickerIcon);
        this.datePickerinputGroup.datetimepicker({
            format:this.dateFormat
        });
        this.element.uniqueId();
        this.id=this.element.prop("id");
    }
    setDateFormat(format){
        if(this.dateFormat===format || format===''){
            return;
        }
        this.dateFormat=format;
        this.datePickerinputGroup.datetimepicker('remove');
        this.datePickerinputGroup.datetimepicker({
            format:this.dateFormat
        });
    }
    initFromJson(json){
        Instance.prototype.fromJson.call(this,json);
        this.setDateFormat(json.format);
        if(json.searchOperator){
            this.searchOperator=json.searchOperator;
        }
    }
    toJson(){
        return {
            label:this.label,
            labelPosition:this.labelPosition,
            bindParameter:this.bindParameter,
            format:this.format,
            type:DatetimeInstance.TYPE
        };
    }
    toXml(){
        let xml=`<input-datetime label="${this.label}" label-position="${this.labelPosition}" bind-parameter="${this.bindParameter}" format="${this.format}"></input-datetime>`;
        return xml;
    }
}
DatetimeInstance.TYPE="Datetime";
DatetimeInstance.ID="datetime_instance";