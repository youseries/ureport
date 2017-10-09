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
        this.fontSize=10;
        const group=$(`<div class="btn-group"></div>`);
        this.nameButton=$(`<button type="button" class="btn btn-default"
            style="border:none;border-radius:0;background: #f8f8f8;padding: 6px 1px 6px 5px;color: #0e90d2;font-size: 12pt;" title="${window.i18n.tools.fontSize.size}">10</button>`);
        group.append(this.nameButton);
        const mainBtn=$(`<button type="button" class="btn btn-default dropdown-toggle" style="border:none;border-radius:0;background: #f8f8f8;padding: 6px 5px;" data-toggle="dropdown" title="${window.i18n.tools.fontSize.size}">
            <span class="caret"></span>
            <span class="sr-only">${window.i18n.tools.fontSize.changeMenu}</span>
        </button>`);
        const ul=$(`<ul class="dropdown-menu" role="menu" style="padding: 1px;"></ul>`);
        for(let i=1;i<=100;i++){
            let size=$(`<li data="${i}"><a href='###'>${i}</a></li>`);
            ul.append(size);
            size.click(function(e){
                if(!_this.checkSelection()){
                    return;
                }
                triggerFontSize.call(this,_this.context,size.attr("data"),_this.nameButton,_this);
            });
        }
        this.nameButton.click(function(){
            if(!_this.checkSelection()){
                return;
            }
            triggerFontSize.call(this,_this.context,this.fontSize,_this.nameButton,_this);
        });
        group.append(mainBtn);
        group.append(ul);
        return group;
    }

    refresh(startRow,startCol,endRow,endCol){
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
        for(let i=startRow;i<=endRow;i++) {
            for (let j = startCol; j <= endCol; j++) {
                let cellDef = this.context.getCell(i, j);
                if (!cellDef) {
                    continue;
                }
                let cellStyle=cellDef.cellStyle;
                const fontSize=cellStyle.fontSize || 10;
                this.nameButton.html(fontSize);
                this.fontSize=fontSize;
                break;
            }
            break;
        }
    }
}

function triggerFontSize(context,fontsize,nameButton,_this){
    let hot=context.hot;
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
    let oldFontSize=updateFontSize(context,startRow,startCol,endRow,endCol,fontsize,nameButton,_this);
    undoManager.add({
        redo:function(){
            oldFontSize=updateFontSize(context,startRow,startCol,endRow,endCol,fontsize,nameButton,_this);
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
                    nameButton.html(cellStyle.fontSize);
                    _this.fontSize=cellStyle.fontSize;
                }
            }
            hot.render();
            setDirty();
        }
    });
    setDirty();
}

function updateFontSize(context,startRow,startCol,endRow,endCol,size,nameButton,_this){
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
            nameButton.html(size);
            _this.fontSize=size;
        }
    }
    hot.render();
    return oldFontSize;
}
