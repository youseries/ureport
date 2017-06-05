/**
 * Created by Jacky.Gao on 2017-01-25.
 */
import Tool from './Tool.js';
import {undoManager,setDirty} from '../Utils.js';
import {alert} from '../MsgBox.js';

export default class AlignLeftTool extends Tool{
    execute(){
        const cells=this.context.getSelectedCells();
        if(!cells){
            alert("请先选择目标单元格!");
            return;
        }
        for(const td of cells){
            let cellDef=td.cellDef.cellStyle;
            cellDef.align="left";
            $(td).css("text-align","left");
        }
    }

    buildButton(){
        const _this=this;
        const group=$(`<div class="btn-group"></div>`);
        const mainBtn=$(`<button type="button" class="btn btn-default dropdown-toggle" style="border:none;border-radius:0;background: #f8f8f8;padding: 6px 5px;" data-toggle="dropdown" title="左右对齐">
            <i class="ureport ureport-alignleft" style="color: #0e90d2;"></i>
            <span class="caret"></span>
        </button>`);
        const ul=$(`<ul class="dropdown-menu" role="menu"></ul>`);
        const left=$(`<li>
                <a href="###">
                    <i class="ureport ureport-alignleft" style="color: #0e90d2;"></i> 左对齐
                </a>
            </li>`);
        ul.append(left);
        left.click(function(){
            const selectedCells=_this.context.hot.getSelected();
            if(!selectedCells || selectedCells.length===0){
                alert("请先选择单元格！");
                return;
            }
            let oldAligns=_this._buildCellAlign(_this.context,"left");
            undoManager.add({
                undo:function(){
                    oldAligns=_this._buildCellAlign(_this.context,null,oldAligns);
                    setDirty();
                },
                redo:function(){
                    oldAligns=_this._buildCellAlign(_this.context,"left");
                    setDirty();
                }
            });
            setDirty();
        });
        const center=$(`<li>
                <a href="###">
                    <i class="ureport ureport-aligncenter" style="color: #0e90d2;"></i> 居中对齐
                </a>
            </li>`);
        center.click(function(){
            const selectedCells=_this.context.hot.getSelected();
            if(!selectedCells || selectedCells.length===0){
                alert("请先选择单元格！");
                return;
            }
            let oldAligns=_this._buildCellAlign(_this.context,"center");
            undoManager.add({
                undo:function(){
                    oldAligns=_this._buildCellAlign(_this.context,null,oldAligns);
                    setDirty();
                },
                redo:function(){
                    oldAligns=_this._buildCellAlign(_this.context,"center");
                    setDirty();
                }
            });
            setDirty();
        });
        ul.append(center);
        const right=$(`<li>
                <a href="###">
                    <i class="ureport ureport-alignright" style="color: #0e90d2;"></i> 右对齐
                </a>
            </li>`);
        right.click(function(){
            const selectedCells=_this.context.hot.getSelected();
            if(!selectedCells || selectedCells.length===0){
                alert("请先选择单元格！");
                return;
            }
            let oldAligns=_this._buildCellAlign(_this.context,"right");
            undoManager.add({
                undo:function(){
                    oldAligns=_this._buildCellAlign(_this.context,null,oldAligns);
                    setDirty();
                },
                redo:function(){
                    oldAligns=_this._buildCellAlign(_this.context,"right");
                    setDirty();
                }
            });
            setDirty();
        });
        ul.append(right);
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
                oldAligns[i+","+j]=cellStyle.align || "";
                if(prevAligns){
                    align=prevAligns[i+","+j];
                }
                $(td).css("text-align",align);
                cellStyle.align=align;
            }
        }
        return oldAligns;
    }

    getTitle(){
        return '左右对齐';
    }
    getIcon(){
        return `<i class="ureport ureport-alignleft" style="color: #0e90d2;"></i>`;
    }
}