/**
 * Created by Jacky.Gao on 2017-01-25.
 */
import Tool from './Tool.js';
import OpenDialog from '../dialog/OpenDialog.js';

export default class OpenTool extends Tool{
    constructor(context){
        super(context);
        this.openDialog=new OpenDialog(context);
    }
    execute(){
        this.openDialog.show()
    }
    getTitle(){
        return `${window.i18n.openFile}`;
    }
    getIcon(){
        return `<i class="ureport ureport-open" style="color: #0e90d2;"></i>`;
    }
}