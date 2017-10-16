/**
 * Created by Jacky.Gao on 2017-01-25.
 */
import Tool from './Tool.js';
import {alert} from '../MsgBox.js';
import SettingsDialog from '../dialog/SettingsDialog.js';

export default class SettingsTool extends Tool{
    constructor(context){
        super(context);
        this.settingsDialog=new SettingsDialog();
    }
    execute(){
        this.settingsDialog.show(this.context);
    }
    getTitle(){
        return `${window.i18n.setting}`;
    }
    getIcon(){
        return `<i class="ureport ureport-setting" style="color: #0e90d2;"></i>`;
    }
}