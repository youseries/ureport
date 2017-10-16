/**
 * Created by Jacky.Gao on 2017-01-25.
 */
import Tool from './Tool.js';
import {buildNewCellDef,setDirty,undoManager} from '../Utils.js';
import CrossTabWidget from '../widget/CrossTabWidget.js';
import Handsontable from 'handsontable';
import CrosstabDialog from '../dialog/CrosstabDialog.js';

export default class CrosstabTool extends Tool{
    execute(){
        if(!this.checkSelection()){
            return;
        }
        const hot=this.context.hot;
        const selected=hot.getSelected();
        const rowIndex=selected[0],colIndex=selected[1];
        const cellDef=this.context.getCell(rowIndex,colIndex);
        let oldCellData=hot.getDataAtCell(rowIndex,colIndex);
        let oldCellDataValue=cellDef.value;
        const $td=$(hot.getCell(rowIndex,colIndex));
        const _this=this;
        const dialog=new CrosstabDialog();
        dialog.show(function(value){
            cellDef.crossTabWidget=new CrossTabWidget(_this.context,rowIndex,colIndex,null,value);
            hot.render();
            setDirty();
            Handsontable.hooks.run(hot, 'afterSelectionEnd',rowIndex,colIndex,selected[2],selected[3]);

            undoManager.add({
                redo:function(){
                    oldCellData=hot.getDataAtCell(rowIndex,colIndex);
                    oldCellDataValue=cellDef.value;
                    cellDef.crossTabWidget=new CrossTabWidget(_this.context,rowIndex,colIndex,null,value);
                    hot.render();
                    setDirty();
                    Handsontable.hooks.run(hot, 'afterSelectionEnd',rowIndex,colIndex,selected[2],selected[3]);
                },
                undo:function(){
                    cellDef.value=oldCellDataValue;
                    cellDef.crossTabWidget=null;
                    hot.setDataAtCell(rowIndex,colIndex,oldCellData);
                    hot.render();
                    setDirty();
                    Handsontable.hooks.run(hot, 'afterSelectionEnd',rowIndex,colIndex,selected[2],selected[3]);
                }
            });
        });
    }

    getTitle(){
        return `${window.i18n.tools.crosstab.title}`;
    }
    getIcon(){
        return `<i class="ureport ureport-crosstab" style="color: #0e90d2;"></i>`;
    }
}