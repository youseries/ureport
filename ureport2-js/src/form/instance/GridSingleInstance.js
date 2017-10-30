/**
 * Created by Jacky.Gao on 2017-10-16.
 */
import ContainerInstance from './ContainerInstance.js';
import ColContainer from '../container/ColContainer.js';
export default class GridSingleInstance extends ContainerInstance{
    constructor(){
        super();
        this.element=$("<div class=\"row\" style=\"margin: 0px;min-width:100px;\">");
        this.col1=new ColContainer(12);
        this.containers.push(this.col1);
        this.element.append(this.col1.getContainer());
        this.element.uniqueId();
        this.id=this.element.prop("id");
        this.showBorder=false;
        this.borderWidth=1;
        this.borderColor="#cccccc";
    }
    toJson(){
        const json={
            showBorder:this.showBorder,
            borderWidth:this.borderWidth,
            borderColor:this.borderColor,
            type:GridSingleInstance.TYPE,
            cols:[]
        };
        for(let container of this.containers){
            json.cols.push(container.toJson());
        }
        return json;
    }
    toXml(){
        let xml=`<grid show-border="${this.showBorder}" type="${GridSingleInstance.TYPE}" border-width="${this.borderWidth}" border-color="${this.borderColor}">`;
        for(let container of this.containers){
            xml+=container.toXml();
        }
        xml+=`</grid>`;
        return xml;
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