/**
 * Created by Jacky.Gao on 2017-10-15.
 */
import Container from './Container.js';
export default class ColContainer extends Container{
    constructor(colsize){
        super();
        this.colsize=colsize;
        this.container=$("<div style='min-height:80px;padding: 1px'>");
        this.container.addClass("col-md-"+colsize+"");
        this.container.addClass("pb-dropable-grid");
    }
    toJson(){
        const json={
            size:this.colsize,
            children:[]
        };
        for(let child of this.getChildren()){
            json.children.push(child.toJson());
        }
        return json;
    }
    toXml(){
        let xml=`<col size="${this.colsize}">`;
        for(let child of this.getChildren()){
            xml+=child.toXml();
        }
        xml+=`</col>`;
        return xml;
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
