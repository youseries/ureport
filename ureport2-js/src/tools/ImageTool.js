/**
 * Created by Jacky.Gao on 2017-01-25.
 */
import Tool from './Tool.js';
import {buildNewCellDef,setDirty,undoManager} from '../Utils.js';
import Handsontable from 'handsontable';

export default class ImageTool extends Tool{
    execute(){
        if(!this.checkSelection()){
            return;
        }
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
        let oldCellDef=this.context.getCell(startRow,startCol);
        let oldCellData=hot.getDataAtCell(startRow,startCol);
        let newCellDef=buildNewCellDef(startRow+1,startCol+1);
        newCellDef.value={
            type:'image',
            source:'text',
            value:''
        };
        this.context.addCell(newCellDef);
        const imagePath=window._server+'/res/ureport-asserts/icons/image.svg';
        const image=$(`<img src="${imagePath}" width="20px">`);
        let $td=$(hot.getCell(startRow,startCol));
        $td.empty();
        $td.append(image);
        setDirty();
        Handsontable.hooks.run(hot, 'afterSelectionEnd',startRow,startCol,endRow,endCol);

        const _this=this;
        undoManager.add({
            redo:function(){
                oldCellDef=_this.context.getCell(startRow,startCol);
                oldCellData=hot.getDataAtCell(startRow,startCol);
                newCellDef=buildNewCellDef(startRow+1,startCol+1);
                newCellDef.value={
                    type:'image',
                    source:'text',
                    value:''
                };
                _this.context.addCell(newCellDef);
                hot.setDataAtCell(startRow,startCol,'');
                hot.render();
                setDirty();
                Handsontable.hooks.run(hot, 'afterSelectionEnd',startRow,startCol,endRow,endCol);
            },
            undo:function(){
                _this.context.addCell(oldCellDef);
                hot.setDataAtCell(startRow,startCol,oldCellData);
                hot.render();
                setDirty();
                Handsontable.hooks.run(hot, 'afterSelectionEnd',startRow,startCol,endRow,endCol);
            }
        });
    }
    getTitle(){
        return `${window.i18n.image}`;
    }
    getIcon(){
        return `<i class="ureport ureport-image" style="color: #0e90d2;"></i>`;
    }
}