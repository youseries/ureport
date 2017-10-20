/**
 * Created by Jacky.Gao on 2017-01-25.
 */
import Tool from './Tool.js';
import {undoManager,setDirty} from '../Utils.js';

export default class UnderlineTool extends Tool{
    execute(){
        if(!this.checkSelection()){
            return;
        }
        const _this=this;
        const table=this.context.hot;
        const selected=table.getSelected();
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
        let oldUnderlineStyle=updateCellsUnderlineStyle(this.context,startRow,startCol,endRow,endCol);
        undoManager.add({
            redo:function(){
                oldUnderlineStyle=updateCellsUnderlineStyle(_this.context,startRow,startCol,endRow,endCol);
                setDirty();
            },
            undo:function(){
                oldUnderlineStyle=updateCellsUnderlineStyle(_this.context,startRow,startCol,endRow,endCol,oldUnderlineStyle);
                setDirty();
            }
        });
        setDirty();
    }
    getTitle(){
        return `${window.i18n.underline}`;
    }
    getIcon(){
        return `<i class="ureport ureport-underline" style="color: #0e90d2;"></i>`;
    }
}
function updateCellsUnderlineStyle(context,startRow,startCol,endRow,endCol,prevUnderLine){
    const oldUnderlineStyle={},hot=context.hot;
    for(let i=startRow;i<=endRow;i++){
        for(let j=startCol;j<=endCol;j++){
            let td=hot.getCell(i,j);
            let cellDef=context.getCell(i,j);
            if(!cellDef){
                continue;
            }
            let underline='underline';
            if(prevUnderLine){
                underline=prevUnderLine[i+","+j];
            }
            let cellStyle=cellDef.cellStyle;
            oldUnderlineStyle[i+","+j]=cellStyle.underline;
            if(cellStyle.underline){
                cellStyle.underline=false;
            }else{
                cellStyle.underline=true;
            }
        }
    }
    hot.render();
    return oldUnderlineStyle;
}