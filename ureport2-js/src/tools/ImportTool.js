/**
 * Created by Jacky.Gao on 2017-01-25.
 */
import Tool from './Tool.js';
import ImportDialog from '../dialog/ImportDialog.js';

export default class ImportTool extends Tool{
    constructor(){
        super();
        this.dialog=new ImportDialog();
    }
    execute(){
        this.dialog.show();
    }
    getTitle(){
        return `${window.i18n.importExcel}`;
    }
    getIcon(){
        return `<i class="ureport ureport-import" style="color: #0e90d2;"></i>`;
    }
}