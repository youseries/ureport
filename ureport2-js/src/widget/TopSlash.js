/**
 * Created by Jacky.Gao on 2017-03-16.
 */
import Slash from './Slash.js';

export default class TopSlash extends Slash{
    constructor(width,height,fontSize,lastHeight){
        super(width,height,fontSize);
        this.init(lastHeight);
    }
    init(lastHeight){
        this.degree=this._computeDegree(this.width,this.height+lastHeight/2);
        this.angle=this._computeAngle(this.width,this.height+lastHeight);
        const angle=this.angle-this.degree;
        const sin=Math.sin((angle/180)*Math.PI);
        const w=this.fontSize/2;
        const length=w/sin;

        const newAngle=90-this.angle;
        const cos=Math.cos((newAngle/180)*Math.PI);
        this.y=parseInt(length/cos);

        const topAngle=this.angle-angle*2;
        const topCos=Math.cos((topAngle/180)*Math.PI);
        const x=parseInt(length/topCos);
    }
}
