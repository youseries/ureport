/**
 * Created by Jacky.Gao on 2017-02-14.
 */
import {alert} from '../MsgBox.js';
import {setDirty,undoManager} from '../Utils.js';
import Handsontable from 'handsontable';
export default class BaseTree{
    _buildClickEvent(dataset,field,context){
        let hot=context.hot,cellsMap=context.cellsMap;
        let selected=hot.getSelected();
        if(!selected || selected.length===0){
            alert(`${window.i18n.tree.cellTip}`);
            return;
        }
        let rowIndex=selected[0],colIndex=selected[1];
        let cellDef=context.getCell(rowIndex,colIndex);

        let oldCellDef=Object.assign({},cellDef);

        if(cellDef.value.type!=='dataset'){
            context.removeCell(cellDef);
            cellDef={
                value: {type: 'dataset', conditions: []},
                rowNumber: cellDef.rowNumber,
                columnNumber: cellDef.columnNumber,
                cellStyle: cellDef.cellStyle
            };
            context.addCell(cellDef);
        }
        cellDef.expand="Down";
        let value=cellDef.value;
        value.aggregate="group";
        value.datasetName=dataset.name;
        value.property=field.name;
        value.order='none';

        let text=value.datasetName+"."+value.aggregate+"(";
        let prop=value.property;
        text+=prop+')';
        hot.setDataAtCell(rowIndex,colIndex,text);
        setDirty();
        hot.render();
        Handsontable.hooks.run(hot, 'afterSelectionEnd',selected[0],selected[1],selected[2],selected[3]);

        undoManager.add({
            redo:function(){
                cellDef=context.getCell(rowIndex,colIndex);
                oldCellDef=Object.assign({},cellDef);
                if(cellDef.value.type!=='dataset'){
                    context.removeCell(cellDef);
                    cellDef={
                        value: {type: 'dataset', conditions: []},
                        rowNumber: cellDef.rowNumber,
                        columnNumber: cellDef.columnNumber,
                        cellStyle: cellDef.cellStyle
                    };
                    context.addCell(cellDef);
                }
                cellDef.expand="Down";
                value=cellDef.value;
                value.aggregate="group";
                value.datasetName=dataset.name;
                value.property=field.name;
                value.order='none';

                text=value.datasetName+"."+value.aggregate+"(";
                prop=value.property;
                text+=prop+')';
                hot.setDataAtCell(rowIndex,colIndex,text);
                setDirty();
                hot.render();
                Handsontable.hooks.run(hot, 'afterSelectionEnd',selected[0],selected[1],selected[2],selected[3]);
            },
            undo:function(){
                cellDef=context.getCell(rowIndex,colIndex);
                context.removeCell(cellDef);
                context.addCell(oldCellDef);
                value=oldCellDef.value;
                let text=value.value || '';
                if(value.type==='dataset'){
                    text=value.datasetName+"."+value.aggregate+"(";
                    let prop=value.property;
                    text+=prop+')';
                }
                hot.setDataAtCell(rowIndex,colIndex,text);
                setDirty();
                hot.render();
                Handsontable.hooks.run(hot, 'afterSelectionEnd',selected[0],selected[1],selected[2],selected[3]);
            }
        })
    }
}