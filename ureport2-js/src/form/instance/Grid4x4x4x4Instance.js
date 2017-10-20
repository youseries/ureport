/**
 * Created by Jacky.Gao on 2017-10-16.
 */
import ContainerInstance from './ContainerInstance.js';
import ColContainer from '../container/ColContainer.js';
export default class Grid4x4x4x4Instance extends ContainerInstance{
    constructor(){
        super();
        this.element=$("<div class=\"row\" style=\"margin: 0px;min-width:100px;\">");
        var col1=new ColContainer(3);
        var col2=new ColContainer(3);
        var col3=new ColContainer(3);
        var col4=new ColContainer(3);
        this.containers.push(col1,col2,col3,col4);
        this.element.append(col1.getContainer());
        this.element.append(col2.getContainer());
        this.element.append(col3.getContainer());
        this.element.append(col4.getContainer());
        this.element.uniqueId();
        this.id=this.element.prop("id");
        this.showBorder=false;
        this.borderWidth=1;
        this.borderColor="#cccccc";
    }
    toJSON(){
        var json={type:Grid4x4x4x4Instance.TYPE, showBorder: this.showBorder,borderWidth:this.borderWidth,borderColor:this.borderColor};
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
            if(width){
                container.container.css("border","solid "+width+"px "+self.borderColor+"");
            }else{
                container.container.css("border","");
            }
        });
        if(width){
            this.borderWidth=width;
        }
    }
    setBorderColor(color){
        var self=this;
        $.each(this.containers,function(index,container){
            container.container.css("border","solid "+self.borderWidth+"px "+color+"");
        });
        this.borderColor=color;
    }
}
Grid4x4x4x4Instance.TYPE="Grid4x4x4x4";