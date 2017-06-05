/**
 * Created by Jacky.Gao on 2017-01-25.
 */
import Tool from './Tool.js';
import {undoManager,setDirty} from '../Utils.js';

export default class FontSizeTool extends Tool{
    execute(){
    }
    buildButton(){
        const _this=this;
        const group=$(`<div class="btn-group"></div>`);
        const mainBtn=$(`<button type="button" class="btn btn-default dropdown-toggle" style="border:none;border-radius:0;background: #f8f8f8;padding: 6px 5px;" data-toggle="dropdown" title="字体尺寸">
            <i class="ureport ureport-fontsize" style="color: #0e90d2;"></i>
            <span class="caret"></span>
        </button>`);
        const ul=$(`<ul class="dropdown-menu" role="menu" style="padding: 1px;"></ul>`);
        for(let i=1;i<=100;i++){
            let size=$(`<li data="${i}"><a href='###'>${i}</a></li>`);
            ul.append(size);
            size.click(function(e){
                if(!_this.checkSelection()){
                    return;
                }
                triggerFontSize.call(this,_this.context,size);
            });
        }
        group.append(mainBtn);
        group.append(ul);
        return group;
    }
}

function triggerFontSize(context,li){
    let hot=context.hot;
    let fontsize=li.attr("data");
    let selected=hot.getSelected();
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
    let oldFontSize=updateFontSize(context,startRow,startCol,endRow,endCol,fontsize);
    undoManager.add({
        redo:function(){
            oldFontSize=updateFontSize(context,startRow,startCol,endRow,endCol,fontsize);
            setDirty();
        },
        undo:function(){
            for(let i=startRow;i<=endRow;i++) {
                for (let j = startCol; j <= endCol; j++) {
                    let cellDef = context.getCell(i, j);
                    if (!cellDef) {
                        continue;
                    }
                    let cellStyle=cellDef.cellStyle;
                    cellStyle.fontSize=oldFontSize[i+','+j];
                }
            }
            hot.render();
            setDirty();
        }
    });
    setDirty();
}

function updateFontSize(context,startRow,startCol,endRow,endCol,size){
    let hot=context.hot;
    const oldFontSize={};
    for(let i=startRow;i<=endRow;i++) {
        for (let j = startCol; j <= endCol; j++) {
            let cellDef = context.getCell(i, j);
            if (!cellDef) {
                continue;
            }
            let cellStyle=cellDef.cellStyle;
            oldFontSize[i+','+j]=cellStyle.fontSize;
            cellStyle.fontSize=size;
        }
    }
    hot.render();
    return oldFontSize;
}
