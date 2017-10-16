/**
 * Created by Jacky.Gao on 2017-10-16.
 */
import ContainerInstance from './ContainerInstance.js';
import ColContainer from '../container/ColContainer.js';
export default class GridSingleInstance extends ContainerInstance{
    constructor(){
        super();
        this.element=$("<div class=\"row\" style=\"margin: 0px;min-width:100px;\">");
        var col1=new ColContainer(12);
        this.containers.push(col1);
        this.element.append(col1.getContainer());
        this.element.uniqueId();
        this.id=this.element.prop("id");
        this.showBorder=false;
        this.borderWidth=1;
        this.borderColor="#cccccc";
    }
    toJSON(){
        var json = {type: GridSingleInstance.TYPE, showBorder: this.showBorder,borderWidth:this.borderWidth,borderColor:this.borderColor};
        json.visible=this.visible;
        var cols=[];
        $.each(this.containers,function(index,col){
            cols.push(col.toJSON());
        });
        json.cols=cols;
        return json;
    }
    setBorderWidth(width){
        var self=this;
        $.each(this.containers,function(index,container){
            container.container.css("border","solid "+width+"px "+self.borderColor+"");
        });
        this.borderWidth=width;
    }
    setBorderColor(color){
        var self=this;
        $.each(this.containers,function(index,container){
            container.container.css("border","solid "+self.borderWidth+"px "+color+"");
        });
        this.borderColor=color;
    }
}
GridSingleInstance.TYPE="GridSingle";