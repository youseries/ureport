/**
 * Created by Jacky.Gao on 2017-01-25.
 */
import Tool from './Tool.js';
import SaveDialog from '../dialog/SaveDialog.js';
import {alert} from '../MsgBox.js';
import {setDirty,tableToXml,undoManager} from '../Utils.js';
import Handsontable from 'handsontable';

export default class ZxingTool extends Tool{
    execute(){
    }

    buildButton(){
        const group=$(`<div class="btn-group"></div>`);
        const mainBtn=$(`<button type="button" class="btn btn-default dropdown-toggle" style="border:none;border-radius:0;background: #f8f8f8;padding: 6px 5px;" data-toggle="dropdown" title="${window.i18n.tools.zxing.title}">
            <i class="ureport ureport-qrcode" style="color: #0e90d2;"></i>
            <span class="caret"></span>
        </button>`);
        const ul=$(`<ul class="dropdown-menu" role="menu"></ul>`);
        const qrcode=$(`<li>
                <a href="###">
                    <i class="ureport ureport-qrcode" style="color: #0e90d2;"></i> ${window.i18n.tools.zxing.qrcode}
                </a>
            </li>`);
        ul.append(qrcode);
        const saveDialog=new SaveDialog();
        const _this=this;
        qrcode.click(function(){
            if(!_this.checkSelection()){
                return;
            }
            const hot=_this.context.hot;
            const selected=hot.getSelected();
            const startRow=selected[0],startCol=selected[1],endRow=selected[2],endCol=selected[3];
            let cellDef=_this.context.getCell(startRow,startCol);
            let oldValue=cellDef.value,oldCellData=hot.getDataAtCell(startRow,startCol);
            hot.setDataAtCell(startRow,startCol,'');
            let td=hot.getCell(startRow,startCol);
            let width=_this._buildWidth(startCol,td.colSpan,hot),height=_this._buildHeight(startRow,td.rowSpan,hot);
            cellDef.value={
                width,
                height,
                type:'zxing',
                category:'qrcode',
                source:'text',
                data:''
            };
            hot.render();
            setDirty();
            Handsontable.hooks.run(hot, 'afterSelectionEnd',startRow,startCol,endRow,endCol);
            undoManager.add({
                redo:function(){
                    cellDef=_this.context.getCell(startRow,startCol);
                    oldValue=cellDef.value,oldCellData=hot.getDataAtCell(startRow,startCol);
                    hot.setDataAtCell(startRow,startCol,'');
                    td=hot.getCell(startRow,startCol);
                    width=_this._buildWidth(startCol,td.colSpan,hot),height=_this._buildHeight(startRow,td.rowSpan,hot);
                    cellDef.value={
                        width,
                        height,
                        type:'zxing',
                        category:'qrcode',
                        source:'text',
                        data:''
                    };
                    hot.render();
                    setDirty();
                    Handsontable.hooks.run(hot, 'afterSelectionEnd',startRow,startCol,endRow,endCol);
                },
                undo:function(){
                    cellDef=_this.context.getCell(startRow,startCol);
                    cellDef.value=oldValue;
                    hot.setDataAtCell(startRow,startCol,oldCellData);
                    hot.render();
                    setDirty();
                    Handsontable.hooks.run(hot, 'afterSelectionEnd',startRow,startCol,endRow,endCol);
                }
            })
        });
        const barcode=$(`<li>
                <a href="###">
                    <i class="ureport ureport-barcode" style="color: #0e90d2;font-size: 16px"></i> ${window.i18n.tools.zxing.barcode}
                </a>
            </li>`);
        ul.append(barcode);
        barcode.click(function(){
            if(!_this.checkSelection()){
                return;
            }
            const hot=_this.context.hot;
            const selected=hot.getSelected();
            const startRow=selected[0],startCol=selected[1],endRow=selected[2],endCol=selected[3];
            let cellDef=_this.context.getCell(startRow,startCol);
            let oldValue=cellDef.value,oldCellData=hot.getDataAtCell(startRow,startCol);
            hot.setDataAtCell(startRow,startCol,'');
            let td=hot.getCell(startRow,startCol);
            let width=_this._buildWidth(startCol,td.colSpan,hot),height=_this._buildHeight(startRow,td.rowSpan,hot);
            cellDef.value={
                width,
                height,
                type:'zxing',
                category:'barcode',
                source:'text',
                format:'CODE_128',
                data:''
            };
            hot.render();
            setDirty();
            Handsontable.hooks.run(hot, 'afterSelectionEnd',startRow,startCol,endRow,endCol);
            undoManager.add({
                redo:function(){
                    cellDef=_this.context.getCell(startRow,startCol);
                    oldValue=cellDef.value,oldCellData=hot.getDataAtCell(startRow,startCol);
                    hot.setDataAtCell(startRow,startCol,'');
                    td=hot.getCell(startRow,startCol);
                    width=_this._buildWidth(startCol,td.colSpan,hot),height=_this._buildHeight(startRow,td.rowSpan,hot);
                    cellDef.value={
                        width,
                        height,
                        type:'zxing',
                        category:'barcode',
                        source:'text',
                        format:'CODE_128',
                        data:''
                    };
                    hot.render();
                    setDirty();
                    Handsontable.hooks.run(hot, 'afterSelectionEnd',startRow,startCol,endRow,endCol);
                },
                undo:function(){
                    cellDef=_this.context.getCell(startRow,startCol);
                    cellDef.value=oldValue;
                    hot.setDataAtCell(startRow,startCol,oldCellData);
                    hot.render();
                    setDirty();
                    Handsontable.hooks.run(hot, 'afterSelectionEnd',startRow,startCol,endRow,endCol);
                }
            })
        });
        group.append(mainBtn);
        group.append(ul);
        return group;
    }


    _buildWidth(colIndex,colspan,hot){
        let width=hot.getColWidth(colIndex)-3;
        if(!colspan || colspan<2){
            return width;
        }
        let start=colIndex+1,end=colIndex+colspan;
        for(let i=start;i<end;i++){
            width+=hot.getColWidth(i);
        }
        return width;
    }

    _buildHeight(rowIndex,rowspan,hot){
        let height=hot.getRowHeight(rowIndex)-3;
        if(!rowspan || rowspan<2){
            return height;
        }
        let start=rowIndex+1,end=rowIndex+rowspan;
        for(let i=start;i<end;i++){
            height+=hot.getRowHeight(i);
        }
        return height;
    }
}