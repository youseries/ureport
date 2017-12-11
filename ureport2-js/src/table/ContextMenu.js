/**
 * Created by Jacky.Gao on 2017-01-31.
 */
import {undoManager,resetTableData,setDirty} from '../Utils.js';
import {doInsertRow} from './operation/InsertRowOperation.js';
import {doInsertCol} from './operation/InsertColOperation.js';
import {doDeleteRow} from './operation/DeleteRowOperation.js';
import {doDeleteCol} from './operation/DeleteColOperation.js';
import {renderRowHeader} from './HeaderUtils.js';
import RowColWidthHeightDialog from '../dialog/RowColWidthHeightDialog.js';
import Handsontable from 'handsontable';

export default function buildMenuConfigure(){
    return {
        callback: function (key, options) {
            const _this=this;
            if (key === 'insert_row_above') {
                doInsertRow.call(this,true);
            }else if(key === 'insert_row_below'){
                doInsertRow.call(this);
            }else if(key==='insert_col_left'){
                doInsertCol.call(this,true);
            }else if(key==='insert_col_right'){
                doInsertCol.call(this);
            }else if(key==='del_row'){
                doDeleteRow.call(this);
            }else if(key==='del_col'){
                doDeleteCol.call(this);
            }else if(key==='clean_content'){
                const selected=this.getSelected();
                const startRow=selected[0],endRow=selected[2],startCol=selected[1],endCol=selected[3];
                let removeCellsMap=cleanCells(_this.context,startRow,endRow,startCol,endCol,'content');
                undoManager.add({
                    redo:function(){
                        removeCellsMap=cleanCells(_this.context,startRow,endRow,startCol,endCol,'content');
                    },
                    undo:function(){
                        undoCleanCells(_this.context,startRow,endRow,startCol,endCol,removeCellsMap,'content');
                    }
                })
            }else if(key==='clean_style'){
                const selected=this.getSelected();
                const startRow=selected[0],endRow=selected[2],startCol=selected[1],endCol=selected[3];
                let removeCellsMap=cleanCells(_this.context,startRow,endRow,startCol,endCol,'style');
                undoManager.add({
                    redo:function(){
                        removeCellsMap=cleanCells(_this.context,startRow,endRow,startCol,endCol,'style');
                    },
                    undo:function(){
                        undoCleanCells(_this.context,startRow,endRow,startCol,endCol,removeCellsMap,'style');
                    }
                })
            }else if(key==='clean'){
                const selected=this.getSelected();
                const startRow=selected[0],endRow=selected[2],startCol=selected[1],endCol=selected[3];
                let removeCellsMap=cleanCells(_this.context,startRow,endRow,startCol,endCol,'all');
                undoManager.add({
                    redo:function(){
                        removeCellsMap=cleanCells(_this.context,startRow,endRow,startCol,endCol,'all');
                    },
                    undo:function(){
                        undoCleanCells(_this.context,startRow,endRow,startCol,endCol,removeCellsMap,'all');
                    }
                });
            }else if(key==='repeat_row_header'){
                const selected=this.getSelected();
                const startRow=selected[0],endRow=selected[2];
                const context=this.context;
                for(let rowNumber=startRow;rowNumber<=endRow;rowNumber++){
                    context.addRowHeader(rowNumber,'headerrepeat');
                }
                renderRowHeader(this,context);
                setDirty();
            }else if(key==='title_row'){
                const selected=this.getSelected();
                const startRow=selected[0],endRow=selected[2];
                const context=this.context;
                for(let rowNumber=startRow;rowNumber<=endRow;rowNumber++){
                    context.addRowHeader(rowNumber,'title');
                }
                renderRowHeader(this,context);
                setDirty();
            }else if(key==='repeat_row_footer'){
                const selected=this.getSelected();
                const startRow=selected[0],endRow=selected[2];
                const context=this.context;
                for(let rowNumber=startRow;rowNumber<=endRow;rowNumber++){
                    context.addRowHeader(rowNumber,'footerrepeat');
                }
                renderRowHeader(this,context);
                setDirty();
            }else if(key==='summary_row'){
                const selected=this.getSelected();
                const startRow=selected[0],endRow=selected[2];
                const context=this.context;
                for(let rowNumber=startRow;rowNumber<=endRow;rowNumber++){
                    context.addRowHeader(rowNumber,'summary');
                }
                renderRowHeader(this,context);
                setDirty();
            }else if(key==='repeat_cancel'){
                const selected=this.getSelected();
                const startRow=selected[0],endRow=selected[2];
                const context=this.context;
                for(let rowNumber=startRow;rowNumber<=endRow;rowNumber++){
                    context.adjustDelRowHeaders(rowNumber);
                }
                renderRowHeader(this,context);
                setDirty();
            }else if(key==='row_height'){
                const selected=this.getSelected();
                const startRow=selected[0];
                const rowHeight=this.getRowHeight(startRow);
                const dialog=new RowColWidthHeightDialog();
                dialog.show(function(newHeight){
                    const rowHeights=_this.getSettings().rowHeights;
                    rowHeights.splice(startRow,1,newHeight);
                    _this.updateSettings({
                        rowHeights:rowHeights,
                        manualRowResize:rowHeights
                    });
                },rowHeight,false);
                setDirty();
            }else if(key==='col_width'){
                const selected=this.getSelected();
                const startCol=selected[1];
                const colWidth=this.getColWidth(startCol);
                const dialog=new RowColWidthHeightDialog();
                dialog.show(function(newColWidth){
                    const colWidths=_this.getSettings().colWidths;
                    colWidths.splice(startCol,1,newColWidth);
                    _this.updateSettings({
                        colWidths:colWidths,
                        manualColumnResize:colWidths
                    });
                },colWidth,true);
                setDirty();
            }else if(key==='copy_style'){
                const selected=this.getSelected();
                const startRow=selected[0],endRow=selected[2],startCol=selected[1],endCol=selected[3];
                let cell=_this.context.getCell(startRow,startCol);
                if(!cell){
                    alert("请先选中目标单元格！");
                    return;
                }
                window.__copy_cell_style__=cell.cellStyle;
            }else if(key==='paste_style'){
                if(!window.__copy_cell_style__){
                    alert('请先复制目标单元格样式');
                    return;
                }
                const selected=this.getSelected();
                const startRow=selected[0],endRow=selected[2],startCol=selected[1],endCol=selected[3];
                let oldCellsStyleMap=pasteStyle(_this.context,startRow,endRow,startCol,endCol);
                undoManager.add({
                    redo:function(){
                        oldCellsStyleMap=pasteStyle(_this.context,startRow,endRow,startCol,endCol);
                    },
                    undo:function(){
                        undoPasteStyle(_this.context,startRow,endRow,startCol,endCol,oldCellsStyleMap);
                    }
                });
            }
        },
        items: {
            "insert_row_above": {
                name: `<i class="ureport ureport-insertrow" style="color: #3344d3;font-size: 13px"></i> ${window.i18n.table.contextMenu.insertRowUp}`
            },
            "insert_row_below": {
                name: `<i class="ureport ureport-insertrow" style="color: #3344d3;font-size: 13px"></i> ${window.i18n.table.contextMenu.insertRowDown}`
            },
            "insert_col_left": {
                name: `<i class="ureport ureport-insert-column" style="color: #008ed3;font-size: 13px"></i> ${window.i18n.table.contextMenu.insertColBefore}`
            },
            "insert_col_right": {
                name: `<i class="ureport ureport-insert-column" style="color: #008ed3;font-size: 13px"></i> ${window.i18n.table.contextMenu.insertColAfter}`
            },
            "del_row": {
                name: `<i class="ureport ureport-deleterow" style="color: #d30a16;font-size: 13px"></i>  ${window.i18n.table.contextMenu.delRow}`,
                disabled:checkRowDeleteOperationDisabled
            },
            "del_col": {
                name: `<i class="ureport ureport-deletecolumn" style="color: #d30a16;font-size: 13px"></i>  ${window.i18n.table.contextMenu.delCol}`,
                disabled:checkColDeleteOperationDisabled
            },
            "row_height": {
                name: `<i class="ureport ureport-height" style="color: #d30a16;font-size: 13px;font-weight:bold"></i>  ${window.i18n.table.contextMenu.rowHeight}`,
                disabled:checkRowDeleteOperationDisabled
            },
            "col_width": {
                name: `<i class="ureport ureport-width" style="color: #d30a16;font-size: 13px;font-weight:bold"></i>  ${window.i18n.table.contextMenu.colWidth}`,
                disabled:checkColDeleteOperationDisabled
            },
            "title_row": {
                name: `<i class="ureport ureport-title" style="color: #9C27B0;font-size: 13px"></i>  ${window.i18n.table.contextMenu.title}`,
                disabled:checkRowDeleteOperationDisabled
            },
            "repeat_row_header": {
                name: `<i class="ureport ureport-header-repeat" style="color: #9C27B0;font-size: 13px"></i>  ${window.i18n.table.contextMenu.repeatHeader}`,
                disabled:checkRowDeleteOperationDisabled
            },
            "repeat_row_footer": {
                name: `<i class="ureport ureport-footer-repeat" style="color: #9C27B0;font-size: 13px"></i>  ${window.i18n.table.contextMenu.repeatFooter}`,
                disabled:checkRowDeleteOperationDisabled
            },
            "summary_row": {
                name: `<i class="ureport ureport-summary" style="color: #9C27B0;font-size: 13px"></i>  ${window.i18n.table.contextMenu.summary}`,
                disabled:checkRowDeleteOperationDisabled
            },
            "repeat_cancel": {
                name: `<i class="glyphicon glyphicon-remove-circle" style="color: #d30e00;font-size: 13px"></i>  ${window.i18n.table.contextMenu.cancel}`,
                disabled:checkRowDeleteOperationDisabled
            },
            "copy_style": {
                name: `<i class="ureport ureport-copy" style="color: #d30e00;font-size: 13px"></i>  ${window.i18n.table.contextMenu.copy}`,
                disabled:checkCopyOperationDisabled
            },
            "paste_style": {
                name: `<i class="ureport ureport-paste" style="color: #d30e00;font-size: 13px"></i>  ${window.i18n.table.contextMenu.paste}`,
                disabled:checkPasteOperationDisabled
            },
            "clean_content": {
                name: `<i class="ureport ureport-clean-content" style="color: #007471;font-size: 13px"></i>  ${window.i18n.table.contextMenu.clearContent}`,
                disabled:checkCleanOperationDisabled
            },
            "clean_style": {
                name: `<i class="ureport ureport-clean-style" style="color: #00746f;font-size: 13px"></i>  ${window.i18n.table.contextMenu.clearStyle}`,
                disabled:checkCleanOperationDisabled
            },
            "clean": {
                name: `<i class="ureport ureport-clean" style="color: #d30e00;font-size: 13px"></i>  ${window.i18n.table.contextMenu.clearAll}`,
                disabled:checkCleanOperationDisabled
            }
        }
    };

    function undoCleanCells(context,startRow,endRow,startCol,endCol,removeCellsMap,type){
        let cellsMap=context.cellsMap,hot=context.hot;
        for(let i=startRow;i<=endRow;i++) {
            for (let j = startCol; j <= endCol; j++) {
                let cell = context.getCell(i, j);
                if (!cell) {
                    continue;
                }
                let key=cell.rowNumber+","+cell.columnNumber;
                if(type==='content'){
                    let orgValue=removeCellsMap.get(key);
                    if(!orgValue){
                        alert(`${window.i18n.table.contextMenu.cancelConetntFail}`);
                        return;
                    }
                    cell.value=orgValue;
                    let value=cell.value;
                    let valueType=value.type;
                    let text=value.value;
                    if(valueType==='dataset'){
                        text=value.datasetName+"."+value.aggregate+"("+value.property+")";
                    }
                    hot.setDataAtCell(i,j,text);
                }else if(type==='style'){
                    let orgStyle=removeCellsMap.get(key);
                    if(!orgStyle){
                        alert(`${window.i18n.table.contextMenu.cancelStyleFail}`);
                        return;
                    }
                    cell.cellStyle=orgStyle;
                }else if(type==='all'){
                    context.removeCell(cell);
                    let orgCell=removeCellsMap.get(key);
                    if(!orgCell){
                        alert(`${window.i18n.table.contextMenu.cancelClearFail}`);
                        return;
                    }
                    context.addCell(orgCell);
                    let value=orgCell.value;
                    let valueType=value.type;
                    let text=value.value;
                    if(valueType==='dataset'){
                        text=value.datasetName+"."+value.aggregate+"("+value.property+")";
                    }
                    hot.setDataAtCell(i,j,text);
                }
            }
        }
        Handsontable.hooks.run(hot, 'afterSelectionEnd',startRow,startCol,endRow,endCol);
        hot.render();
    };


    function undoPasteStyle(context,startRow,endRow,startCol,endCol,oldStyleMap){
        const style=window.__copy_cell_style__;
        let cellsMap=new Map(),hot=context.hot;
        for(let i=startRow;i<=endRow;i++){
            for(let j=startCol;j<=endCol;j++){
                let cell=context.getCell(i,j);
                if(!cell){
                    continue;
                }
                let key=cell.rowNumber+","+cell.columnNumber;
                const oldStyle=oldStyleMap.get(key);
                if(oldStyle){
                    cell.cellStyle=oldStyle;
                }
            }
        }
        Handsontable.hooks.run(hot, 'afterSelectionEnd',startRow,startCol,endRow,endCol);
        hot.render();
        return cellsMap;
    };

    function pasteStyle(context,startRow,endRow,startCol,endCol){
        const style=window.__copy_cell_style__;
        let cellsMap=new Map(),hot=context.hot;
        for(let i=startRow;i<=endRow;i++){
            for(let j=startCol;j<=endCol;j++){
                let cell=context.getCell(i,j);
                if(!cell){
                    continue;
                }
                let key=cell.rowNumber+","+cell.columnNumber;
                if(!cell.cellStyle){
                    cell.cellStyle={};
                }
                const oldStyle=JSON.parse(JSON.stringify(cell.cellStyle));
                cellsMap.set(key,oldStyle);
                cell.cellStyle.fontSize=style.fontSize;
                cell.cellStyle.forecolor=style.forecolor;
                cell.cellStyle.fontFamily=style.fontFamily;
                cell.cellStyle.valign=style.valign;
                cell.cellStyle.align=style.align;
                cell.cellStyle.bgcolor=style.bgcolor;
                cell.cellStyle.bold=style.bold;
                cell.cellStyle.italic=style.italic;
                cell.cellStyle.underline=style.underline;
            }
        }
        Handsontable.hooks.run(hot, 'afterSelectionEnd',startRow,startCol,endRow,endCol);
        hot.render();
        return cellsMap;
    };

    function cleanCells(context,startRow,endRow,startCol,endCol,type){
        let removeCellsMap=new Map(),hot=context.hot;
        for(let i=startRow;i<=endRow;i++){
            for(let j=startCol;j<=endCol;j++){
                let cell=context.getCell(i,j);
                if(!cell){
                    continue;
                }
                cell.cellStyle.format=null;
                let key=cell.rowNumber+","+cell.columnNumber;
                if(type==='content'){
                    removeCellsMap.set(key,cell.value);
                    cell.value={
                        type:'simple',
                        value:''
                    };
                    cell.expand='None';
                    cell.conditionPropertyItems=null;
                    hot.setDataAtCell(i,j,'');
                }else if(type==='style'){
                    removeCellsMap.set(key,cell.cellStyle);
                    cell.cellStyle={fontSize:9,forecolor:'0,0,0',fontFamily:'宋体',align:'center',valign:'middle'};
                }else if(type==='all'){
                    context.removeCell(cell);
                    removeCellsMap.set(key,cell);
                    let newCell={
                        rowNumber:cell.rowNumber,
                        columnNumber:cell.columnNumber,
                        expand:'None',
                        value:{
                            type:'simple',
                            value:''
                        },
                        cellStyle:{fontSize:9,forecolor:'0,0,0',fontFamily:'宋体',align:'center',valign:'middle'}
                    };
                    context.addCell(newCell);
                    hot.setDataAtCell(i,j,'');
                }
            }
        }
        Handsontable.hooks.run(hot, 'afterSelectionEnd',startRow,startCol,endRow,endCol);
        hot.render();
        return removeCellsMap;
    };

    function checkCopyOperationDisabled(){
        const selected=this.getSelected();
        if(!selected){
            return true;
        }
        return false;
    };
    function checkPasteOperationDisabled(){
        const selected=this.getSelected();
        if(!selected){
            return true;
        }
        if(window.__copy_cell_style__){
            return false;
        }
        return true;
    };
    function checkRowDeleteOperationDisabled(){
        const selected=this.getSelected();
        if(!selected){
            return true;
        }
        const startRow=selected[0], endRow=selected[2];
        let dif=Math.abs(startRow-endRow)+1;
        const countRows=this.countRows();
        if(dif>=countRows){
            return true;
        }else{
            return false;
        }
    };

    function checkColDeleteOperationDisabled(){
        const selected=this.getSelected();
        if(!selected){
            return true;
        }
        const startCol=selected[1], endCol=selected[3];
        let dif=Math.abs(startCol-endCol)+1;
        const countCols=this.countCols();
        if(dif>=countCols){
            return true;
        }else{
            return false;
        }
    };
    function checkCleanOperationDisabled(){
        const selected=this.getSelected();
        if(!selected || selected.length===0){
            return true;
        }
        return false;
    };
}
