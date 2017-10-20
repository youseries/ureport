/**
 * Created by Jacky.Gao on 2017-01-25.
 */
import Tool from './Tool.js';
import {undoManager,setDirty} from '../Utils.js';

export default class ItalicTool extends Tool{
    execute(){
        if(!this.checkSelection()){
            return;
        }
        const _this=this;
        const hot=this.context.hot;
        const selected=hot.getSelected();
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
        let oldItalicStyle=updateCellsItalicStyle(_this.context,startRow,startCol,endRow,endCol);
        undoManager.add({
            redo:function(){
                oldItalicStyle=updateCellsItalicStyle(_this.context,startRow,startCol,endRow,endCol);
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
                        let italic=oldItalicStyle[i+","+j];
                        cellStyle.italic=italic;
                    }
                }
                hot.render();
                setDirty();
            }
        });
        setDirty();
    }
    getTitle(){
        return `${window.i18n.italic}`;
    }
    getIcon(){
        return `<i class="ureport ureport-italic" style="color: #0e90d2;"></i>`;
    }
}
function updateCellsItalicStyle(context,startRow,startCol,endRow,endCol){
    let hot=context.hot;
    const oldItalicStyle={};
    for(let i=startRow;i<=endRow;i++){
        for(let j=startCol;j<=endCol;j++){
            let cellDef=context.getCell(i,j);
            if(!cellDef){
                continue;
            }
            let cellStyle=cellDef.cellStyle;
            oldItalicStyle[i+","+j]=cellStyle.italic;
            if(cellStyle.italic){
                cellStyle.italic=false;
            }else{
                cellStyle.italic=true;
            }
        }
    }
    hot.render();
    return oldItalicStyle;
}