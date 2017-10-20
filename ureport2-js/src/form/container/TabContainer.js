/**
 * Created by Jacky.Gao on 2017-10-12.
 */
import Container from './Container.js';

export default class TabContainer extends Container{
    constructor(id){
        super();
        this.id=id;
        this.container=$("<div class='tab-pane fade pb-tab-grid' id='"+this.id+"'>");
    }
    addElement(element){
        this.container.append(element);
    }
    initFromJson(json){
        formBuilder.buildPageElements(json,this);
    }
    toJSON(){
        var children=[];
        $.each(this.getChildren(),function(index,child){
            children.push(child.toJSON());
        });
        return children;
    }
    toHtml(){
        var div=$("<div class='tab-pane fade pb-tab-grid' id='"+this.id+"1'>");
        div.append(this.buildChildrenHtml(div));
        return div;
    }
}
