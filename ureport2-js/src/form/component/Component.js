/**
 * Created by Jacky.Gao on 2017-10-12.
 */

export default class Component{
    constructor(options){
        this.options=options;
        this.entityList=[];
        this.tool=$("<div><i class='"+options.icon+"' style='margin-right:5px'>"+options.label+"</div>");
        this.tool.addClass("pb-component");
        this.tool.attr(Component.ID,this.getId());
        this.tool.draggable({
            revert: false,
            connectToSortable:".pb-dropable-grid",
            helper:"clone"
        });
    }
    support(type){
        if(type===this.getType()){
            return true;
        }
        return false;
    }
    getId(){
        return '';
    }
}
Component.ID="component_id";
Component.GRID="component_grid";