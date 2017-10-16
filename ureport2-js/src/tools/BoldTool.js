/**
 * Created by Jacky.Gao on 2017-01-25.
 */
import Tool from './Tool.js';
import {undoManager,setDirty} from '../Utils.js';

export default class BoldTool extends Tool{
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
        let oldBoldStyle=updateCellsBoldStyle(_this.context,startRow,startCol,endRow,endCol);
        undoManager.add({
            redo:function(){
                oldBoldStyle=updateCellsBoldStyle(_this.context,startRow,startCol,endRow,endCol);
                setDirty();
            },
            undo:function(){
                for(let i=startRow;i<=endRow;i++){
                    for(let j=startCol;j<=endCol;j++){
                        let cellDef=_this.context.getCell(i,j);
                        if(!cellDef){
                            continue;
                        }
                        let cellStyle=cellDef.cellStyle;
                        let bold=oldBoldStyle[i+","+j];
                        cellStyle.bold=bold;
                    }
                }
                table.render();
                setDirty();
            }
        });
        setDirty();
    }

    getTitle(){
        return `${window.i18n.tools.bold.bold}`;
    }
    getIcon(){
        return `<i class="ureport ureport-bold" style="color: #0e90d2;"></i>`;
    }
}

function updateCellsBoldStyle(context,startRow,startCol,endRow,endCol){
    let hot=context.hot;
    const oldBoldStyle={};
    for(let i=startRow;i<=endRow;i++){
        for(let j=startCol;j<=endCol;j++){
            let cellDef=context.getCell(i,j);
            if(!cellDef){
                continue;
            }
            let cellStyle=cellDef.cellStyle;
            oldBoldStyle[i+","+j]=cellStyle.bold;
            if(cellStyle.bold){
                cellStyle.bold=false;
            }else{
                cellStyle.bold=true;
            }
        }
    }
    hot.render();
    return oldBoldStyle;
}