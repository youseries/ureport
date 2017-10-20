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
            alert(`${window.i18n.tools.redo.noRedo}`);
        }
    }
    getTitle(){
        return `${window.i18n.tools.redo.redo}`;
    }
    getIcon(){
        return `<i class="ureport ureport-redo" style="color: #0e90d2;"></i>`;
    }
}