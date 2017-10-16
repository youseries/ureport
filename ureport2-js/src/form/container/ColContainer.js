/**
 * Created by Jacky.Gao on 2017-10-15.
 */
import Container from './Container.js';
export default class ColContainer extends Container{
    constructor(colsize){
        super();
        this.colsize=colsize;
        this.container=$("<div style='min-height:80px'>");
        this.container.addClass("col-md-"+colsize+"");
        this.container.addClass("pb-dropable-grid");
    }
    toJSON(){
        var json={type:this.getType(),size:this.colsize};
        var children=[];
        $.each(this.getChildren(),function(index,child){
            children.push(child.toJSON());
        });
        json.children=children;
        return json;
    }
    addElement(element){
        this.container.append(element);
    }
    initFromJson(json){
        var children=json.children;
        formBuilder.buildPageElements(children,this);
    }
    getType(){
        return "Col";
    }
    toHtml(){
        var col=$("<div class='col-md-"+this.colsize+"'>");
        this.buildChildrenHtml(col);
        return col;
    }
}
