/**
 * Created by Jacky.Gao on 2017-01-25.
 */
import Tool from './Tool.js';
import {undoManager} from '../Utils.js';
import {alert} from '../MsgBox.js';

export default class UndoTool extends Tool{
    execute(){
        if(undoManager.hasUndo()){
            undoManager.undo();
        }else{
            alert("当前没有内容可以撤消！");
        }
    }
    getTitle(){
        return '撤消';
    }
    getIcon(){
        return `<i class="ureport ureport-undo" style="color: #0e90d2;"></i>`;
    }
}