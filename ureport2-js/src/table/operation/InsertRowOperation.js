/**
 * Created by Jacky.Gao on 2017-02-17.
 */
import {setDirty,resetTableData,buildNewCellDef,undoManager} from '../../Utils.js';
import {renderRowHeader} from '../HeaderUtils.js';
import {alert} from '../../MsgBox.js';

export function doInsertRow(above){
    const selected=this.getSelected();
    if(!selected){
        alert(`${window.i18n.table.rowTip}`);
        return;
    }
    let startRow=selected[0],endRow=selected[2];
    let position=startRow;
    if(startRow>endRow){
        if(above){
            position=endRow;
        }else{
            position=startRow+1;
        }
    }else{
        if(above){
            position=startRow;
        }else{
            position=endRow+1;
        }
    }
    let rowHeights=this.getSettings().rowHeights;
    let newRowHeights=rowHeights.concat([]);
    newRowHeights.splice(position,0,25);
    this.alter("insert_row",position);
    this.context.adjustInsertRowHeaders(position);
    renderRowHeader(this,this.context);

    buildNewRowCells(this,position);
    this.updateSettings({
        rowHeights:newRowHeights,
        manualRowResize:newRowHeights
    });
    resetTableData(this);
    setDirty();

    const _this=this,context=this.context,cellsMap=this.context.cellsMap,removeCells=[];
    let removeRowHeight=25;
    undoManager.add({
        redo:function(){
            rowHeights=_this.getSettings().rowHeights;
            newRowHeights=rowHeights.concat([]);
            newRowHeights.splice(position,0,removeRowHeight);
            _this.alter("insert_row",position);
            _this.context.adjustInsertRowHeaders(position);
            renderRowHeader(_this,_this.context);
            let changeCells=[];
            for(let cell of cellsMap.values()){
                let rowIndex=cell.rowNumber-1;
                if(rowIndex>=position){
                    changeCells.push(cell);
                }
            }
            for(let cell of changeCells){
                context.removeCell(cell);
            }
            for(let cell of changeCells){
                cell.rowNumber=cell.rowNumber+1;
                context.addCell(cell);
            }
            for(let cell of removeCells){
                context.addCell(cell);
            }
            _this.updateSettings({
                rowHeights:newRowHeights,
                manualRowResize:newRowHeights
            });
            resetTableData(_this);
            setDirty();
        },
        undo:function(){
            removeCells.splice(0,removeCells.length);
            rowHeights=_this.getSettings().rowHeights;
            newRowHeights=rowHeights.concat([]);
            removeRowHeight=newRowHeights[position];
            newRowHeights.splice(position,1);
            _this.alter('remove_row',position);
            _this.context.adjustDelRowHeaders(position);
            renderRowHeader(_this,_this.context);
            _this.updateSettings({
                rowHeights:newRowHeights,
                manualRowResize:newRowHeights
            });
            let countCols=_this.countCols();
            for(let i=0;i<countCols;i++){
                let cell=context.getCell(position,i);
                if(cell){
                    removeCells.push(cell);
                    context.removeCell(cell);
                }
            }
            let changeCells=[];
            for(let cell of cellsMap.values()){
                let rowIndex=cell.rowNumber-1;
                if(rowIndex>position){
                    changeCells.push(cell);
                }
            }
            for(let cell of changeCells){
                context.removeCell(cell);
            }
            for(let cell of changeCells){
                cell.rowNumber=cell.rowNumber-1;
                context.addCell(cell);
            }
            resetTableData(_this);
            setDirty();
        }
    });
};


function buildNewRowCells(hot,position){
    const countCols=hot.countCols(),countRows=hot.countRows(),context=hot.context;
    const cellsMap=context.cellsMap,changeCells=[];
    for(let cell of cellsMap.values()){
        let rowIndex=cell.rowNumber-1;
        if(rowIndex>=position){
            changeCells.push(cell);
        }
    }
    for(let cell of changeCells){
        context.removeCell(cell);
    }
    for(let cell of changeCells){
        cell.rowNumber=cell.rowNumber+1;
        context.addCell(cell);
    }
    for(let i=0;i<countCols;i++){
        let newCellDef=buildNewCellDef(position+1,(i+1));
        context.addCell(newCellDef);
    }
};
