/**
 * Created by Jacky.Gao on 2017-01-25.
 */
import Tool from './Tool.js';
import {undoManager,setDirty} from '../Utils.js';
import {alert} from '../MsgBox.js';

export default class AlignTopTool extends Tool{
    execute(){
    }

    buildButton(){
        const _this=this;
        const group=$(`<div class="btn-group"></div>`);
        const mainBtn=$(`<button type="button" class="btn btn-default dropdown-toggle" style="border:none;border-radius:0;background: #f8f8f8;padding: 6px 5px" data-toggle="dropdown" title="上下对齐">
            <i class="ureport ureport-aligntop" style="color: #0e90d2;"></i>
            <span class="caret"></span>
        </button>`);
        const ul=$(`<ul class="dropdown-menu" role="menu"></ul>`);
        const top=$(`<li>
                <a href="###">
                    <i class="ureport ureport-aligntop" style="color: #0e90d2;"></i> 上部对齐
                </a>
            </li>`);
        top.click(function(){
            const selectedCells=_this.context.hot.getSelected();
            if(!selectedCells || selectedCells.length===0){
                alert("请先选择单元格！");
                return;
            }
            let oldAligns=_this._buildCellAlign(_this.context,"top");
            undoManager.add({
                undo:function(){
                    oldAligns=_this._buildCellAlign(_this.context,null,oldAligns);
                    setDirty();
                },
                redo:function(){
                    oldAligns=_this._buildCellAlign(_this.context,"top");
                    setDirty();
                }
            });
            setDirty();
        });
        ul.append(top);
        const middle=$(`<li>
                <a href="###">
                    <i class="ureport ureport-alignmiddle" style="color: #0e90d2;"></i> 中部对齐
                </a>
            </li>`);
        middle.click(function(){
            const selectedCells=_this.context.hot.getSelected();
            if(!selectedCells || selectedCells.length===0){
                alert("请先选择单元格！");
                return;
            }
            let oldAligns=_this._buildCellAlign(_this.context,"middle");
            undoManager.add({
                undo:function(){
                    oldAligns=_this._buildCellAlign(_this.context,null,oldAligns);
                    setDirty();
                },
                redo:function(){
                    oldAligns=_this._buildCellAlign(_this.context,"middle");
                    setDirty();
                }
            });
            setDirty();
        });
        ul.append(middle);
        const bottom=$(`<li>
                <a href="###">
                    <i class="ureport ureport-alignbottom" style="color: #0e90d2;"></i> 下部对齐
                </a>
            </li>`);
        bottom.click(function(){
            const selectedCells=_this.context.hot.getSelected();
            if(!selectedCells || selectedCells.length===0){
                alert("请先选择单元格！");
                return;
            }
            let oldAligns=_this._buildCellAlign(_this.context,"bottom");
            undoManager.add({
                undo:function(){
                    oldAligns=_this._buildCellAlign(_this.context,null,oldAligns);
                    setDirty();
                },
                redo:function(){
                    oldAligns=_this._buildCellAlign(_this.context,"bottom");
                    setDirty();
                }
            });
            setDirty();
        });
        ul.append(bottom);
        group.append(mainBtn);
        group.append(ul);
        return group;
    }

    _buildCellAlign(context,align,prevAligns){
        const oldAligns={},selected=context.hot.getSelected();
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
        for(let i=startRow;i<=endRow;i++){
            for(let j=startCol;j<=endCol;j++){
                let cellDef=context.getCell(i,j);
                let td=context.hot.getCell(i,j);
                if(!cellDef){
                    continue;
                }
                const cellStyle=cellDef.cellStyle;
                oldAligns[i+","+j]=cellStyle.valign || "";
                if(prevAligns){
                    align=prevAligns[i+","+j];
                }
                $(td).css("vertical-align",align);
                cellStyle.valign=align;
            }
        }
        return oldAligns;
    }

    getTitle(){
        return '顶部对齐';
    }
    getIcon(){
        return `<i class="ureport ureport-aligntop" style="color: #0e90d2;"></i>`;
    }
}