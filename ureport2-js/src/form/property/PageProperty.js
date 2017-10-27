/**
 * Created by Jacky.Gao on 2017-10-12.
 */
import Property from './Property.js';
export default class PageProperty extends Property{
    constructor(){
        super();
        this.init();
    }
    init(){
        var positionGroup=$("<div class='form-group'>");
        positionGroup.append($("<label>查询表单位置</label>"));
        this.positionSelect=$(`<select class='form-control'>
            <option value="up">预览工具栏之上</option>
            <option value="down">预览工具栏之下</option>
        </select>`);
        positionGroup.append(this.positionSelect);
        var self=this;
        this.positionSelect.change(function(){
            window.formBuilder.formPosition=$(this).val();
        });
        this.col.append(positionGroup);
    }
    refreshValue(current){
        this.positionSelect.val(window.formBuilder.formPosition);
    }
}
