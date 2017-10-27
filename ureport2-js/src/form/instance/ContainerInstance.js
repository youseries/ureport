/**
 * Created by Jacky.Gao on 2017-10-12.
 */
import Instance from './Instance.js';
export default class ContainerInstance extends Instance{
    constructor(){
        super();
        this.containers=[];
        this.visible="true";
    }
    initFromJson(json){
        var cols=json.cols;
        for(var i=0;i<cols.length;i++){
            var col=cols[i];
            var c=this.containers[i];
            c.initFromJson(col);
        }
        if(json.showBorder){
            this.showBorder=json.showBorder;
            this.borderWidth=json.borderWidth;
            this.borderColor=json.borderColor;
            this.setBorderWidth(this.borderWidth);
        }
    }
}