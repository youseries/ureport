/**
 * Created by Jacky.Gao on 2017-10-12.
 */

export default class Component{
    constructor(options){
        this.entityList=[];
        this.tool=$("<div><i class='"+options.icon+"' style='margin-right:5px'>"+options.label+"</div>");
        this.tool.addClass("pb-component");
        this.tool.attr(Component.ID,this.getId());
        /*
        if(this instanceof TabControlComponent){
            this.tool.attr(Component.TAB,"1");
        }

        else if(this instanceof PanelComponent){
            this.tool.attr(Component.PANEL,"1");
        }else if(this instanceof NavbarComponent){
            this.tool.attr(Component.NAVBAR,"1");
        }else if(this instanceof AccordionComponent){
            this.tool.attr(Component.ACCORDION,"1");
        }else if(this instanceof CarouselComponent){
            this.tool.attr(Component.CAROUSEL,"1");
        }
        */
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
Component.TAB="component_tab";
Component.PANEL="component_panel";
Component.NAVBAR="component_navbar";
Component.ACCORDION="component_accordion";
Component.CAROUSEL="component_carousel";