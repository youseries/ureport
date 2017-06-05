/**
 * Created by Jacky.Gao on 2017-03-16.
 */
export default class Slash{
    constructor(width,height,fontSize){
        this.width=width;
        this.height=height;
        this.fontSize=fontSize;
        this.text='未命名';
    }
    _computeDegree(a,b){
        const c=Math.sqrt(a*a+b*b);
        const sin=Math.sin(b/c);
        const degree=(180/Math.PI)*Math.asin(sin);
        return parseInt(degree);
    }
    _computeAngle(a,b){
        const c=Math.sqrt(a*a+b*b);
        const cosB=(a*a+c*c-b*b)/2*a*c;
        const angle=Math.acos(cosB)*(180/Math.PI);
        return parseInt(angle);
    }
}