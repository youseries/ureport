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
        const group=$(`<div class="btn-group"></div>`);
        const mainBtn=$(`<button type="button" class="btn btn-default" style="border:none;border-radius:0;background: #f8f8f8;padding: 6px 5px;" data-toggle="dropdown" title="字体">
            <i class="ureport ureport-fontfamily" style="color: #0e90d2;"></i>
            <span class="caret dropdown-toggle"></span>
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
                triggerFontFamily.call(this,_this.context,family);
                setDirty();
            });
        }
        group.append(mainBtn);
        group.append(ul);
        return group;
    }
}


function triggerFontFamily(context,li){
    let hot=context.hot;
    let fontfamily=li.attr("data");
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
    let oldFontFamily=updateFontFamily(context,startRow,startCol,endRow,endCol,fontfamily);
    undoManager.add({
        redo:function(){
            oldFontFamily=updateFontFamily(context,startRow,startCol,endRow,endCol,fontfamily);
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
                }
            }
            hot.render();
            setDirty();
        }
    });
    setDirty();
}

function updateFontFamily(context,startRow,startCol,endRow,endCol,fontfamily){
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
            cellStyle.fontFamily=fontfamily;
        }
    }
    hot.render();
    return oldFontFamily;
}