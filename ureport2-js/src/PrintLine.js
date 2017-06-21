/**
 * Created by Jacky.Gao on 2017-02-18.
 */
import {mmToPoint} from './Utils.js';

export default class PrintLine{
    constructor(context){
        context.printLine=this;
        this.context=context;
        const height=$(window).height()-90;
        const rightHr=$(`<hr title="打印线" class="ureport-right-hr-for-print" style="height: ${height}px;width:0px;border-left:dashed 1px #999999;position: absolute;left: 300pt;top: 35px;z-index: 10">`);
        $(document.body).append(rightHr);
        this.refresh();
    }
    refresh(){
        const paper=this.context.reportDef.paper;
        const orientation=paper.orientation;
        let width=paper.width;
        if(orientation==='landscape'){
            width=paper.height;
        }
        width=width-paper.leftMargin-paper.rightMargin+38;
        $('.ureport-right-hr-for-print').css('left',width+"pt");
    }
};