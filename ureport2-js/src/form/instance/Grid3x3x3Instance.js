/**
 * Created by Jacky.Gao on 2017-10-16.
 */
import ContainerInstance from './ContainerInstance.js';
import ColContainer from '../container/ColContainer.js';
export default class Grid3x3x3Instance extends ContainerInstance{
    constructor(){
        super();
        this.element=$("<div class=\"row\" style=\"margin: 0px;min-width:100px;\">");
        var col1=new ColContainer(4);
        var col2=new ColContainer(4);
        var col3=new ColContainer(4);
        this.containers.push(col1,col2,col3);
        this.element.append(col1.getContainer());
        this.element.append(col2.getContainer());
        this.element.append(col3.getContainer());
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
            type:Grid3x3x3Instance.TYPE,
            cols:[]
        };
        for(let container of this.containers){
            json.cols.push(container.toJson());
        }
        return json;
    }
    toXml(){
        let xml=`<grid show-border="${this.showBorder}" type="${Grid3x3x3Instance.TYPE}" border-width="${this.borderWidth}" border-color="${this.borderColor}">`;
        for(let container of this.containers){
            xml+=container.toXml();
        }
        xml+=`</grid>`;
        return xml;
    }
    setBorderWidth(){
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
Grid3x3x3Instance.TYPE="Grid3x3x3";