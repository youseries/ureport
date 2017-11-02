/**
 * Created by Jacky.Gao on 2017-10-23.
 */
import Property from './Property.js';
export default class DatetimeProperty extends Property{
    constructor(){
        super();
        this.init();
    }
    init(){
        this.positionLabelGroup=this.buildPositionLabelGroup();
        this.col.append(this.positionLabelGroup);
        this.col.append(this.buildBindParameter());
        this.col.append(this.buildLabelGroup());
        var formatGroup=$("<div class='form-group'><label class='control-label'>日期格式</label></div>");
        this.col.append(formatGroup);
        this.formatSelect=$("<select class='form-control'>");
        this.formatSelect.append($("<option>yyyy-mm-dd</option>"));
        this.formatSelect.append($("<option>yyyy-mm-dd hh:ii:ss</option>"));
        var self=this;
        this.formatSelect.change(function(){
            self.current.setDateFormat($(this).val());
        });
        formatGroup.append(this.formatSelect);
    }
    refreshValue(current){
        super.refreshValue(current);
        this.formatSelect.val(current.dateFormat);
    }
}