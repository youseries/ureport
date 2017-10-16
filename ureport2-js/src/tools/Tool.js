/**
 * Created by Jacky.Gao on 2017-01-25.
 */
import {alert} from '../MsgBox.js';

export default class Tool{
    constructor(context){
        this.context=context;
    }
    buildButton(){
        const btn=$(`<button type="button" class="btn btn-default" style="border:none;border-radius:0;background: #f8f8f8;padding: 6px 5px;" title="${this.getTitle()}">
             ${this.getIcon()}
        </button>`);
        const _this=this;
        btn.click(function(){
            _this.execute();
        });
        return btn;
    }
    checkSelection(){
        const selected=this.context.hot.getSelected();
        if(!selected || selected.length===0){
            alert(`${window.i18n.selectTargetCellFirst}`);
            return false;
        }else{
            return true;
        }
    }
};