/**
 * Created by Jacky.Gao on 2017-01-25.
 */
import Tool from './Tool.js';
import {undoManager,setDirty} from '../Utils.js';

export default class BgcolorTool extends Tool{
    execute(){
    }
    buildButton(){
        this.bgcolor="255,255,255";
        const group=$(`<div class="btn-group"></div>`);
        this.nameButton=$(`<button type="button" class="btn btn-default"
            style="border:none;border-radius:0;background: #f8f8f8;padding: 2px 1px 6px 5px;color: #0e90d2;" title="${window.i18n.tools.bgColor.bgColor}">
                <span class="ureport ureport-bgcolor" style="color: #0e90d2;font-size: 13px"></span>
                <span class="ud-select-bgcolor"></span>
            </button>`);
        group.append(this.nameButton);
        const mainBtn=$(`<button type="button" class="btn btn-default dropdown-toggle" style="border:none;border-radius:0;background: #f8f8f8;padding: 6px 5px;" data-toggle="dropdown" title="${window.i18n.tools.bgColor.bgColor}">
            <span class="caret"></span>
            <span class="sr-only">${window.i18n.tools.bgColor.changeMenu}</span>
        </button>`);
        const ul=$(`<ul class="dropdown-menu" role="menu" style="padding: 1px;"></ul>`);
        const colorContainer=$(`<li></li>`);
        ul.append(colorContainer);
        colorContainer.colorpicker({
            color: '#000',
            container: true,
            inline: true,
            colorSelectors: {
                'black': '#000000',
                'white': '#FFFFFF',
                'gray1': '#efefef',
                'gray': '#CCCCCC',
                'red': '#FF0000',
                'default': '#777777',
                'primary': '#337ab7',
                'success': '#5cb85c',
                'info': '#5bc0de',
                'warning': '#f0ad4e',
                'danger': '#d9534f'
            }
        });
        const _this=this;
        colorContainer.colorpicker().on("changeColor",function(e){
            if(!_this.checkSelection()){
                return;
            }
            let rgb=e.color.toRGB();
            let color=rgb.r+","+rgb.g+","+rgb.b;
            const table=_this.context.hot;
            const selected=table.getSelected();
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
            let oldForecolorStyle=updateCellsBgcolorStyle(_this.context,startRow,startCol,endRow,endCol,color,_this);
            undoManager.add({
                redo:function(){
                    oldForecolorStyle=updateCellsBgcolorStyle(_this.context,startRow,startCol,endRow,endCol,color,_this);
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
                            let bgcolor=oldForecolorStyle[i+","+j];
                            cellStyle.bgcolor=bgcolor;
                            $('.ud-select-bgcolor').css("background-color","rgb("+bgcolor+")");
                            _this.bgcolor=bgcolor;
                        }
                    }
                    table.render();
                    setDirty();
                }
            });
            setDirty();
        });
        this.nameButton.click(function(){
            if(!_this.checkSelection()){
                return;
            }
            const table=_this.context.hot;
            const selected=table.getSelected();
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
            updateCellsBgcolorStyle(_this.context,startRow,startCol,endRow,endCol,_this.bgcolor,_this);
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
                const bgcolor=cellStyle.bgcolor || "255,255,255";
                $('.ud-select-bgcolor').css("background-color","rgb("+bgcolor+")");
                this.bgcolor=bgcolor;
                break;
            }
            break;
        }
    }
}

function updateCellsBgcolorStyle(context,startRow,startCol,endRow,endCol,color,_this){
    let hot=context.hot;
    const oldForecolorStyle={};
    for(let i=startRow;i<=endRow;i++){
        for(let j=startCol;j<=endCol;j++){
            let td=hot.getCell(i,j);
            let cellDef=context.getCell(i,j);
            if(!cellDef){
                continue;
            }
            let cellStyle=cellDef.cellStyle;
            oldForecolorStyle[i+","+j]=cellStyle.bgcolor;
            cellStyle.bgcolor=color;
            $('.ud-select-bgcolor').css("background-color","rgb("+color+")");
            _this.bgcolor=color;
        }
    }
    hot.render();
    return oldForecolorStyle;
}