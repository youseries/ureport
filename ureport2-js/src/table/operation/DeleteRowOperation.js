/**
 * Created by Jacky.Gao on 2017-02-17.
 */
import {setDirty,resetTableData,buildNewCellDef,undoManager} from '../../Utils.js';
import {renderRowHeader} from '../HeaderUtils.js';
import {alert} from '../../MsgBox.js';

export function doDeleteRow(){
    const selected=this.getSelected(),context=this.context;
    if(!selected){
        alert(`${window.i18n.table.rowTip}`);
        return;
    }
    let startRow=selected[0],endRow=selected[2];
    if(endRow<startRow){
        let tempStartRow=startRow;
        startRow=endRow;
        endRow=tempStartRow;
    }
    let rowHeights=this.getSettings().rowHeights,mergeCells=this.getSettings().mergeCells;
    let oldMergeCells=[];
    let newMergeCells=mergeCells.concat([]);
    for(let mergeItem of mergeCells){
        oldMergeCells.push(Object.assign({},mergeItem));
        let row=mergeItem.row,rowspan=mergeItem.rowspan;
        let rowEnd=row+rowspan-1;
        let index=newMergeCells.indexOf(mergeItem);
        if(row>=startRow && rowEnd<=endRow){
            newMergeCells.splice(index,1);
        }else if(row<=startRow && rowEnd>=endRow){
            let span=endRow-startRow+1;
            let leftSpan=rowspan-span;
            if(leftSpan===0){
                leftSpan=1;
            }
            if(leftSpan===1 && mergeItem.colspan===1){
                newMergeCells.splice(index,1);
            }else{
                newMergeCells[index]={
                    col:mergeItem.col,
                    row:row,
                    rowspan:leftSpan,
                    colspan:mergeItem.colspan
                };
            }
        }else if(row>endRow){
            let totalRows=endRow-startRow+1;
            newMergeCells[index]={
                col:mergeItem.col,
                row:row-totalRows,
                rowspan:mergeItem.rowspan,
                colspan:mergeItem.colspan
            };
        }
    }
    this.updateSettings({mergeCells:[]});
    let dif=endRow-startRow+1;
    let oldRowHeights=rowHeights.concat([]);
    let newRowHeights=rowHeights.concat([]);
    newRowHeights.splice(startRow,dif);
    let countCols=this.countCols(),removeCells=[];
    for(let i=endRow;i>=startRow;i--){
        for(let j=0;j<countCols;j++){
            let cell=context.getCell(i,j);
            if(cell){
                context.removeCell(cell);
                removeCells.push(cell);
            }
        }
        this.alter('remove_row',i);
        this.context.adjustDelRowHeaders(i);
    }
    renderRowHeader(this,this.context);
    let cellsMap=context.cellsMap,changeCells=[];
    for(let cell of cellsMap.values()){
        let rowIndex=cell.rowNumber-1;
        if(rowIndex>=endRow){
            changeCells.push(cell);
        }
    }
    for(let cell of changeCells){
        context.removeCell(cell);
    }
    for(let cell of changeCells){
        cell.rowNumber=cell.rowNumber-dif;
        context.addCell(cell);
    }
    this.updateSettings({rowHeights:newRowHeights,mergeCells:newMergeCells});
    resetTableData(this);
    setDirty();

    const _this=this;
    undoManager.add({
        redo:function(){
            rowHeights=_this.getSettings().rowHeights,mergeCells=_this.getSettings().mergeCells;
            oldMergeCells=[];
            newMergeCells=mergeCells.concat([]);
            for(let mergeItem of mergeCells){
                oldMergeCells.push(Object.assign({},mergeItem));
                let row=mergeItem.row,rowspan=mergeItem.rowspan;
                let rowEnd=row+rowspan-1;
                let index=newMergeCells.indexOf(mergeItem);
                if(row>=startRow && rowEnd<=endRow){
                    newMergeCells.splice(index,1);
                }else if(row<=startRow && rowEnd>=endRow){
                    let span=endRow-startRow+1;
                    let leftSpan=rowspan-span;
                    if(leftSpan===0){
                        leftSpan=1;
                    }
                    if(leftSpan===1 && mergeItem.colspan===1){
                        newMergeCells.splice(index,1);
                    }else{
                        newMergeCells[index]={
                            col:mergeItem.col,
                            row:row,
                            rowspan:leftSpan,
                            colspan:mergeItem.colspan
                        };
                    }
                }else if(row>endRow){
                    let totalRows=endRow-startRow+1;
                    newMergeCells[index]={
                        col:mergeItem.col,
                        row:row-totalRows,
                        rowspan:mergeItem.rowspan,
                        colspan:mergeItem.colspan
                    };
                }
            }
            _this.updateSettings({mergeCells:[]});
            oldRowHeights=rowHeights.concat([]);
            newRowHeights=rowHeights.concat([]);
            newRowHeights.splice(startRow,dif);
            countCols=_this.countCols();
            removeCells.splice(0,removeCells.length);
            for(let i=endRow;i>=startRow;i--){
                for(let j=0;j<countCols;j++){
                    let cell=context.getCell(i,j);
                    if(cell){
                        context.removeCell(cell);
                        removeCells.push(cell);
                    }
                }
                _this.alter('remove_row',i);
                _this.context.adjustDelRowHeaders(i);
            }
            renderRowHeader(_this,_this.context);
            changeCells.splice(0,changeCells.length);
            for(let cell of cellsMap.values()){
                let rowIndex=cell.rowNumber-1;
                if(rowIndex>=endRow){
                    changeCells.push(cell);
                }
            }
            for(let cell of changeCells){
                context.removeCell(cell);
            }
            for(let cell of changeCells){
                cell.rowNumber=cell.rowNumber-dif;
                context.addCell(cell);
            }
            _this.updateSettings({rowHeights:newRowHeights,mergeCells:newMergeCells});
            resetTableData(_this);
            setDirty();
        },
        undo:function(){
            for(let i=endRow;i>=startRow;i--){
                _this.alter('insert_row',i);
                _this.context.adjustInsertRowHeaders(i);
            }
            renderRowHeader(_this,_this.context);
            changeCells.splice(0,changeCells.length);
            for(let cell of cellsMap.values()){
                let rowIndex=cell.rowNumber-1;
                if(rowIndex>=startRow){
                    changeCells.push(cell);
                }
            }
            for(let cell of changeCells){
                context.removeCell(cell);
            }
            for(let cell of changeCells){
                cell.rowNumber=cell.rowNumber+dif;
                context.addCell(cell);
            }
            for(let cell of removeCells){
                context.addCell(cell);
            }
            _this.updateSettings({rowHeights:oldRowHeights,mergeCells:oldMergeCells});
            resetTableData(_this);
            setDirty();
        }
    })
}