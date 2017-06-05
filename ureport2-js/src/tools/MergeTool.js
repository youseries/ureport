import {undoManager,setDirty} from '../Utils.js';
import {alert} from '../MsgBox.js';

/**
 * Created by Jacky.Gao on 2017-01-25.
 */
import Tool from './Tool.js';
export default class MergeTool extends Tool{
    execute(){
        const table=this.context.hot;
        const selected=table.getSelected();
        if(!selected){
            alert("请选择目标单元格.");
            return;
        }
        let mergeCells=table.getSettings().mergeCells || [];
        let oldMergeCells=mergeCells.concat([]);
        let startRow=selected[0],startCol=selected[1],endRow=selected[2],endCol=selected[3];
        let tmp=endRow;
        if(startRow>endRow){
            endRow=startRow;
            startRow=tmp;
        }
        tmp=endCol;
        if(startCol>endCol){
            endCol=startCol;
            startCol=tmp;
        }
        doMergeCells(startRow,startCol,endRow,endCol,table);
        undoManager.add({
            redo:function(){
                mergeCells=table.getSettings().mergeCells || [];
                oldMergeCells=mergeCells.concat([]);
                doMergeCells(startRow,startCol,endRow,endCol,table);
                setDirty();
            },
            undo:function(){
                table.updateSettings({mergeCells:oldMergeCells});
                setDirty();
            }
        });
        setDirty();
    }
    getTitle(){
        return '合并/拆分单元格';
    }
    getIcon(){
        return `<i class="ureport ureport-merge" style="color: #0e90d2;"></i>`;
    }
}
function doMergeCells(startRow,startCol,endRow,endCol,table){
    let doMerge=true,doSplit=false;
    const mergeCells=table.getSettings().mergeCells || [];
    for(let i=startRow;i<=endRow;i++){
        for(let j=startCol;j<=endCol;j++){
            let td=table.getCell(i,j);
            if(!td){
                continue;
            }
            let $td=$(td);
            let colSpan=$td.prop("colspan") || "1",rowSpan=$td.prop("rowspan") || "1";
            colSpan=parseInt(colSpan),rowSpan=parseInt(rowSpan);
            if(colSpan>1 || rowSpan>1){
                let index=0;
                doSplit=true;
                doMerge=false;
                while(index<mergeCells.length){
                    let mergeItem=mergeCells[index];
                    let row=mergeItem.row,col=mergeItem.col;
                    if(row===i && col===j){
                        mergeCells.splice(index,1);
                        break;
                    }
                    index++;
                }
            }
        }
    }
    if(doMerge){
        if(endRow<startRow){
            let tmp=startRow;
            startRow=endRow;
            endRow=tmp;
        }
        if(endCol<startCol){
            let tmp=startCol;
            startCol=endCol;
            endCol=tmp;
        }
        let rowSpan=endRow-startRow,colSpan=endCol-startCol;
        if(rowSpan===0){
            rowSpan=1;
        }else{
            rowSpan++;
        }
        if(colSpan===0){
            colSpan=1;
        }else{
            colSpan++;
        }
        const newMergeItem={row:startRow,col:startCol,rowspan:rowSpan,colspan:colSpan};
        mergeCells.push(newMergeItem);
    }else{
        if(!doSplit){
            alert("请选择多个单元格后再进行此操作！");
        }
    }
    table.updateSettings({mergeCells});
};