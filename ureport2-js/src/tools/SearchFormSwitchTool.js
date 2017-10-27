/**
 * Created by Jacky.Gao on 2017-01-25.
 */
import Tool from './Tool.js';
import SearchFormDialog from '../dialog/SearchFormDialog.js';

export default class SearchFormSwitchTool extends Tool{
    constructor(context){
        super(context);
        this.dialog=new SearchFormDialog(context.reportDef);
    }
    execute(){
        this.dialog.show();
    }
    getTitle(){
        return `${window.i18n.tools.searchFormSwitch}`;
    }
    getIcon(){
        return `<i class="glyphicon glyphicon-list-alt" style="color: #0e90d2;font-size: 22px;"></i>`;
    }
}