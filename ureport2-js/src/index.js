/**
 * Created by Jacky.Gao on 2017-01-25.
 */
import '../node_modules/jquery-contextmenu/dist/jquery.contextMenu.min.css';
import '../node_modules/completer/dist/completer.min.css';
import '../node_modules/bootstrap-colorpicker/dist/css/bootstrap-colorpicker.min.css';
import '../node_modules/codemirror/lib/codemirror.css';
import '../node_modules/codemirror/addon/hint/show-hint.css';
import '../node_modules/codemirror/addon/lint/lint.css';
import '../css/iconfont.css';
import '../css/designer.css';
import './tree/css/tree.css';
import './jquery.draggable.js';
import '../node_modules/bootstrap/dist/js/bootstrap.js';
import '../node_modules/codemirror/mode/javascript/javascript.js';
import UReportDesigner from './designer.js';
import buildLocal from  './i18n/i18n.js';

$(document).ready(function(){
    buildLocal();
    const designer=new UReportDesigner("container");
    let prevTop=0;
    $(window).scroll(function(){
        const top=$(window).scrollTop();
        const toolbar=$('.top-toolbar');
        if(top>0){
            toolbar.css({
                "position":"absolute",
                "top":top+"px",
                "z-index":5
            });
            $('.ht_clone_top').css({
                "top":'40px'
            });
        }else{
            toolbar.css({
                position:"relative",
                top:"auto"
            });
            $('.ht_clone_top').css({
                "top":0
            });
        }
        const propPanel=$('.ud-property-panel');
        let orgTop=parseInt(propPanel.css('top'));
        let newTop=parseInt(orgTop+(top-prevTop)+0.5)+"px";
        propPanel.css('top',newTop);
        const printLine=$('.ureport-right-hr-for-print');
        orgTop=parseInt(printLine.css('top'));
        newTop=parseInt(orgTop+(top-prevTop)+0.5)+"px";
        printLine.css('top',newTop);
        prevTop=top;
    });
});