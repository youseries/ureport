/**
 * Created by Jacky.Gao on 2017-10-16.
 */
import ContainerInstance from './ContainerInstance.js';
import ColContainer from '../container/ColContainer.js';
export default class GridCustomInstance extends ContainerInstance{
    constructor(colsJson){
        super();
        this.element=$("<div class=\"row\" style=\"margin: 0px;min-width:100px;\">");
        var value;
        if(!colsJson){
            while(!value){
                value=prompt("请输入列信息,列之间用“,”分隔,列数之和为12，如“2,8,2”，表示有三列，比重为2,8,2","2,8,2");
            }
        }else{
            value="";
            for(var i=0;i<colsJson.length;i++){
                var size=colsJson[i].size;
                if(value.length>0){
                    value+=",";
                }
                value+=size;
            }
        }
        var cols=value.split(",");
        for(var i=0;i<cols.length;i++){
            var colNum=parseInt(cols[i]);
            if(!colNum){
                colNum=1;
            }
            var col=new ColContainer(colNum);
            this.containers.push(col);
            this.element.append(col.getContainer());
        }
        this.element.uniqueId();
        this.id=this.element.prop("id");
        this.showBorder=false;
        this.borderWidth=1;
        this.borderColor="#cccccc";
    }
    getElement(){
        return this.element;
    }
    toJson(){
        const json={
            showBorder:this.showBorder,
            borderWidth:this.borderWidth,
            borderColor:this.borderColor,
            type:GridCustomInstance.TYPE,
            cols:[]
        };
        for(let container of this.containers){
            json.cols.push(container.toJson());
        }
        return json;
    }
    toXml(){
        let xml=`<grid show-border="${this.showBorder}" type="${GridCustomInstance.TYPE}" border-width="${this.borderWidth}" border-color="${this.borderColor}">`;
        for(let container of this.containers){
            xml+=container.toXml();
        }
        xml+=`</grid>`;
        return xml;
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
GridCustomInstance.TYPE="GridCustom";