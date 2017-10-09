/**
 * Created by Jacky.Gao on 2017-01-25.
 */
import Tool from './Tool.js';
import {undoManager,setDirty} from '../Utils.js';

export default class ForecolorTool extends Tool{
    execute(){
    }
    buildButton(){
        this.forecolor="0,0,0";
        const group=$(`<div class="btn-group"></div>`);
        this.nameButton=$(`<button type="button" class="btn btn-default"
            style="border:none;border-radius:0;background: #f8f8f8;padding: 2px 1px 6px 5px;color: #0e90d2;" title="${window.i18n.tools.foreColor.color}">
                <i class="ureport ureport-forecolor" style="color: #0e90d2;font-size: 14px"></i>
                <span class="ud-select-color"></span>
            </button>`);
        group.append(this.nameButton);
        const mainBtn=$(`<button type="button" class="btn btn-default dropdown-toggle" style="border:none;border-radius:0;background: #f8f8f8;padding: 6px 5px;" data-toggle="dropdown" title="${window.i18n.tools.foreColor.color}">
            <span class="caret"></span>
            <span class="sr-only">${window.i18n.tools.foreColor.changeMenu}</span>
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
            const hot=_this.context.hot;
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
            let oldForecolorStyle=updateCellsForecolorStyle(_this.context,startRow,startCol,endRow,endCol,color,_this);
            undoManager.add({
                redo:function(){
                    oldForecolorStyle=updateCellsForecolorStyle(_this.context,startRow,startCol,endRow,endCol,color,_this);
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
                            let forecolor=oldForecolorStyle[i+","+j];
                            cellStyle.forecolor=forecolor;
                            $('.ud-select-color').css("background-color","rgb("+forecolor+")");
                            _this.forecolor=forecolor;
                        }
                    }
                    hot.render();
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
            updateCellsForecolorStyle(_this.context,startRow,startCol,endRow,endCol,_this.forecolor,_this);
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
                const forecolor=cellStyle.forecolor || "0,0,0";
                $('.ud-select-color').css("background-color","rgb("+forecolor+")");
                this.forecolor=forecolor;
                break;
            }
            break;
        }
    }
}

function updateCellsForecolorStyle(context,startRow,startCol,endRow,endCol,color,_this){
    let hot=context.hot;
    const oldForecolorStyle={};
    for(let i=startRow;i<=endRow;i++){
        for(let j=startCol;j<=endCol;j++){
            let cellDef=context.getCell(i,j);
            if(!cellDef){
                continue;
            }
            let cellStyle=cellDef.cellStyle;
            oldForecolorStyle[i+","+j]=cellStyle.forecolor;
            cellStyle.forecolor=color;
            $('.ud-select-color').css("background-color","rgb("+color+")");
            _this.forecolor=color;
        }
    }
    hot.render();
    return oldForecolorStyle;
}