/**
 * Created by Jacky.Gao on 2017-01-25.
 */
import Tool from './Tool.js';
import {undoManager} from '../Utils.js';
import {alert} from '../MsgBox.js';

export default class RedoTool extends Tool{
    execute(){
        if(undoManager.hasRedo()){
            undoManager.redo();
        }else{
            alert("当前没有内容可以重做！");
        }
    }
    getTitle(){
        return '重做';
    }
    getIcon(){
        return `<i class="ureport ureport-redo" style="color: #0e90d2;"></i>`;
    }
}