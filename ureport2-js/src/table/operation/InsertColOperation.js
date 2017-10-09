/**
 * Created by Jacky.Gao on 2017-02-17.
 */
import {setDirty,resetTableData,buildNewCellDef,undoManager} from '../../Utils.js';

export function doInsertCol(left){
    const selected=this.getSelected();
    if(!selected){
        alert(`${window.i18n.table.colTip}`);
        return;
    }
    let startCol=selected[1],endCol=selected[3];
    let position=startCol;
    if(startCol>endCol){
        if(left){
            position=endCol;
        }else{
            position=startCol+1;
        }
    }else{
        if(left){
            position=startCol;
        }else{
            position=endCol+1;
        }
    }
    let colWidths=this.getSettings().colWidths;
    let newColWidths=colWidths.concat([]);
    newColWidths.splice(position,0,98);
    this.alter("insert_col",position);
    const context=this.context,cellsMap=this.context.cellsMap,changeCells=[];
    for(let cell of cellsMap.values()){
        let colIndex=cell.columnNumber-1;
        if(colIndex>=position){
            changeCells.push(cell);
        }
    }
    for(let cell of changeCells){
        context.removeCell(cell);
    }
    for(let cell of changeCells){
        cell.columnNumber=cell.columnNumber+1;
        context.addCell(cell);
    }
    let countRows=this.countRows();
    for(let i=0;i<countRows;i++){
        let newCellDef=buildNewCellDef(i+1,position+1);
        context.addCell(newCellDef);
    }
    this.updateSettings({
        colWidths:newColWidths,
        manualColumnResize:newColWidths
    });
    resetTableData(this);
    setDirty();

    const _this=this,removeCells=[];
    let removeColWidth=98;
    undoManager.add({
        redo:function(){
            colWidths=_this.getSettings().colWidths;
            newColWidths=colWidths.concat([]);
            newColWidths.splice(position,0,removeColWidth);
            _this.alter("insert_col",position);
            changeCells.splice(0,changeCells.length);
            for(let cell of cellsMap.values()){
                let colIndex=cell.columnNumber-1;
                if(colIndex>=position){
                    changeCells.push(cell);
                }
            }
            for(let cell of changeCells){
                context.removeCell(cell);
            }
            for(let cell of changeCells){
                cell.columnNumber=cell.columnNumber+1;
                context.addCell(cell);
            }
            for(let cell of removeCells){
                context.addCell(cell);
            }
            _this.updateSettings({
                colWidths:newColWidths,
                manualColumnResize:newColWidths
            });
            resetTableData(_this);
            setDirty();
        },
        undo:function(){
            removeCells.splice(0,removeCells.length);
            colWidths=_this.getSettings().colWidths;
            newColWidths=colWidths.concat([]);
            removeColWidth=newColWidths[position];
            newColWidths.splice(position,1);
            _this.alter('remove_col',position);
            _this.updateSettings({
                colWidths:newColWidths,
                manualColumnResize:newColWidths
            });
            let countRows=_this.countRows();
            for(let i=0;i<countRows;i++){
                let cell=context.getCell(i,position);
                if(cell){
                    context.removeCell(cell);
                    removeCells.push(cell);
                }
            }
            changeCells.splice(0,changeCells.length);
            for(let cell of cellsMap.values()) {
                let colIndex = cell.columnNumber - 1;
                if(colIndex>position){
                    changeCells.push(cell);
                }
            }
            for(let cell of changeCells){
                context.removeCell(cell);
            }
            for(let cell of changeCells){
                cell.columnNumber=cell.columnNumber-1;
                context.addCell(cell);
            }

            resetTableData(_this);
            setDirty();
        }
    });
};