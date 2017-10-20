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
        var titleGroup=$("<div class='form-group'>");
        titleGroup.append($("<label>页面标题</label>"));
        this.titleText=$("<input type='text' class='form-control'>");
        titleGroup.append(this.titleText);
        var self=this;
        this.titleText.change(function(){
            formBuilder.pageTitle=$(this).val();
        });
        this.col.append(titleGroup);

        var bindGroup=$("<div class='form-group'><label>与当前页面绑定的表</label></div>");
        this.col.append(bindGroup);
        this.bindTableLabel=$("<div></div>");
        bindGroup.append(this.bindTableLabel);
    }
    refreshValue(current){
        this.titleText.val(formBuilder.pageTitle);
        if(formBuilder.bindTable){
            this.bindTableLabel.html(formBuilder.bindTable.desc+"("+formBuilder.bindTable.name+")");
        }
    }
}
