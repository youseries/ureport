/**
 * Created by Jacky.Gao on 2017-01-25.
 */
import '../node_modules/jquery-contextmenu/dist/jquery.contextMenu.min.css';
import '../node_modules/completer/dist/completer.min.css';
import '../node_modules/bootstrap-colorpicker/dist/css/bootstrap-colorpicker.min.css';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import '../node_modules/handsontable/dist/handsontable.full.min.css';
import '../node_modules/codemirror/lib/codemirror.css';
import '../node_modules/codemirror/addon/hint/show-hint.css';
import '../node_modules/codemirror/addon/lint/lint.css';
import '../css/iconfont.css';
import '../css/designer.css';
import './tree/css/tree.css';
import './jquery.draggable.js';
import '../node_modules/codemirror/mode/javascript/javascript.js';
import UReportDesigner from './designer.js';
import buildLocal from  './i18n/i18n.js';

$(document).ready(function(){
    buildLocal();
    const designer=new UReportDesigner("container");
});