/**
 * Created by Jacky.Gao on 2017-01-25.
 */
import Tool from './Tool.js';
import {undoManager,setDirty} from '../Utils.js';

export default class FontFamilyTool extends Tool{
    execute(){
    }
    buildButton(){
        const _this=this;
        this.fontFamily="宋体";
        const group=$(`<div class="btn-group"></div>`);
        this.nameButton=$(`<button type="button" class="btn btn-default"
            style="border:none;border-radius:0;background: #f8f8f8;padding: 6px 1px 6px 5px;color: #0e90d2;" title="${window.i18n.tools.font.font}">
        宋体
        </button>`);
        group.append(this.nameButton);
        const mainBtn=$(`<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" style="border:none;border-radius:0;background: #f8f8f8;padding: 6px 5px;" title="${window.i18n.tools.font.font}">
            <span class="caret"></span>
            <span class="sr-only">${window.i18n.tools.font.changeMenu}</span>
        </button>`);
        const ul=$(`<ul class="dropdown-menu" role="menu" style="padding: 1px;"></ul>`);
        const fonts=[];
        fonts.push("宋体");
        fonts.push("仿宋");
        fonts.push("黑体");
        fonts.push("楷体");
        fonts.push("微软雅黑");
        fonts.push("Arial");
        fonts.push("Impact");
        fonts.push("Times New Roman");
        fonts.push("Comic Sans MS");
        fonts.push("Courier New");

        for(let font of fonts){
            let family=$(`<li data="${font}"><a href='###'>${font}</a></li>`);
            ul.append(family);
            family.click(function(e){
                if(!_this.checkSelection()){
                    return;
                }
                triggerFontFamily.call(_this,_this.context,family.attr("data"),_this.nameButton);
                setDirty();
            });
        }
        this.nameButton.click(function(){
            if(!_this.checkSelection()){
                return;
            }
            triggerFontFamily.call(_this,_this.context,_this.fontFamily,_this.nameButton);
            setDirty();
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
                const fontFamily=cellStyle.fontFamily || "宋体";
                this.nameButton.html(fontFamily);
                this.fontFamily=fontFamily;
                break;
            }
            break;
        }
    }
}


function triggerFontFamily(context,fontfamily,nameButton){
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
    const _this=this;
    let oldFontFamily=updateFontFamily(context,startRow,startCol,endRow,endCol,fontfamily,nameButton,_this);
    undoManager.add({
        redo:function(){
            oldFontFamily=updateFontFamily(context,startRow,startCol,endRow,endCol,fontfamily,nameButton,_this);
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
                    cellStyle.fontFamily=oldFontFamily[i+','+j];
                    nameButton.html(cellStyle.fontFamily);
                    _this.fontFamily=cellStyle.fontFamily;
                }
            }
            hot.render();
            setDirty();
        }
    });
    setDirty();
}

function updateFontFamily(context,startRow,startCol,endRow,endCol,fontFamily,nameButton,_this){
    let hot=context.hot;
    const oldFontFamily={};
    for(let i=startRow;i<=endRow;i++) {
        for (let j = startCol; j <= endCol; j++) {
            let cellDef = context.getCell(i, j);
            if (!cellDef) {
                continue;
            }
            let cellStyle=cellDef.cellStyle;
            oldFontFamily[i+','+j]=cellStyle.fontFamily;
            cellStyle.fontFamily=fontFamily;
            nameButton.html(fontFamily);
            _this.fontFamily=fontFamily;
        }
    }
    hot.render();
    return oldFontFamily;
}