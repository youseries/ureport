/**
 * Created by Jacky.Gao on 2017-01-25.
 */
import Tool from './Tool.js';
import {undoManager,setDirty} from '../Utils.js';
import {dialog,alert} from '../MsgBox.js';

export default class BorderTool extends Tool{
    execute(){
    }

    buildButton(){
        const _this=this;
        const group=$(`<div class="btn-group"></div>`);
        const mainBtn=$(`<button type="button" class="btn btn-default dropdown-toggle" style="border:none;border-radius:0;background: #f8f8f8;padding: 6px 5px;" data-toggle="dropdown" title="${window.i18n.tools.border.borderLine}">
            <i class="ureport ureport-no-border" style="color: #0e90d2;"></i>
            <span class="caret"></span>
        </button>`);
        const ul=$(`<ul class="dropdown-menu" role="menu"></ul>`);
        const fullBorder=$(`<li>
                <a href="###">
                    <i class="ureport ureport-full-border" style="color: #0e90d2;"></i> ${window.i18n.tools.border.allLine}
                </a>
            </li>`);
        fullBorder.click(function(){
            if(!_this.checkSelection()){
                return;
            }
            const table=_this.context.hot;
            const selected=table.getSelected(),oldStyle={};
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
            let newBorder={
                width:1,
                color:'0,0,0',
                style:'solid'
            };
            let oldBorderStyle=updateBorderStyles(_this.context,startRow,startCol,endRow,endCol,newBorder);
            table.render();
            undoManager.add({
                redo:function(){
                    oldBorderStyle=updateBorderStyles(_this.context,startRow,startCol,endRow,endCol,newBorder);
                    table.render();
                    setDirty();
                },
                undo:function(){
                    updateOldBorderStyles(_this.context,startRow,startCol,endRow,endCol,oldBorderStyle);
                    setDirty();
                }
            });
            setDirty();
        });
        ul.append(fullBorder);
        const noBorder=$(`<li>
                <a href="###">
                    <i class="ureport ureport-no-border" style="color: #0e90d2;"></i> ${window.i18n.tools.border.noBorder}
                </a>
            </li>`);
        noBorder.click(function(){
            if(!_this.checkSelection()){
                return;
            }
            const table=_this.context.hot;
            const selected=table.getSelected(),oldStyle={};
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
            let newBorder='';
            let oldBorderStyle=updateBorderStyles(_this.context,startRow,startCol,endRow,endCol,newBorder);
            table.render();
            undoManager.add({
                redo:function(){
                    oldBorderStyle=updateBorderStyles(_this.context,startRow,startCol,endRow,endCol,newBorder);
                    table.render();
                    setDirty();
                },
                undo:function(){
                    updateOldBorderStyles(_this.context,startRow,startCol,endRow,endCol,oldBorderStyle);
                    setDirty();
                }
            });
            setDirty();
        });
        ul.append(noBorder);
        const leftBorder=$(`<li>
                <a href="###">
                    <i class="ureport ureport-left-border" style="color: #0e90d2;"></i> ${window.i18n.tools.border.leftBorder}
                </a>
            </li>`);
        leftBorder.click(function(){
            if(!_this.checkSelection()){
                return;
            }
            const table=_this.context.hot;
            const selected=table.getSelected(),oldStyle={};
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
            let newBorder={
                width:1,
                color:'0,0,0',
                style:'solid'
            };
            let oldBorderStyle=updateBorderStyles(_this.context,startRow,startCol,endRow,endCol,newBorder,'left');
            table.render();
            undoManager.add({
                redo:function(){
                    oldBorderStyle=updateBorderStyles(_this.context,startRow,startCol,endRow,endCol,newBorder,'left');
                    table.render();
                    setDirty();
                },
                undo:function(){
                    updateOldBorderStyles(_this.context,startRow,startCol,endRow,endCol,oldBorderStyle);
                    setDirty();
                }
            });
            setDirty();
        });
        ul.append(leftBorder);
        const rightBorder=$(`<li>
                <a href="###">
                    <i class="ureport ureport-right-border" style="color: #0e90d2;"></i> ${window.i18n.tools.border.rightBorder}
                </a>
            </li>`);
        rightBorder.click(function(){
            if(!_this.checkSelection()){
                return;
            }
            const table=_this.context.hot;
            const selected=table.getSelected(),oldStyle={};
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
            let newBorder={
                width:1,
                color:'0,0,0',
                style:'solid'
            };
            let oldBorderStyle=updateBorderStyles(_this.context,startRow,startCol,endRow,endCol,newBorder,'right');
            table.render();
            undoManager.add({
                redo:function(){
                    oldBorderStyle=updateBorderStyles(_this.context,startRow,startCol,endRow,endCol,newBorder,'right');
                    table.render();
                    setDirty();
                },
                undo:function(){
                    updateOldBorderStyles(_this.context,startRow,startCol,endRow,endCol,oldBorderStyle);
                    setDirty();
                }
            });
            setDirty();
        });
        ul.append(rightBorder);
        const topBorder=$(`<li>
                <a href="###">
                    <i class="ureport ureport-top-border" style="color: #0e90d2;"></i> ${window.i18n.tools.border.topBorder}
                </a>
            </li>`);
        topBorder.click(function(){
            if(!_this.checkSelection()){
                return;
            }
            const table=_this.context.hot;
            const selected=table.getSelected(),oldStyle={};
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
            let newBorder={
                width:1,
                color:'0,0,0',
                style:'solid'
            };
            let oldBorderStyle=updateBorderStyles(_this.context,startRow,startCol,endRow,endCol,newBorder,'top');
            table.render();
            undoManager.add({
                redo:function(){
                    oldBorderStyle=updateBorderStyles(_this.context,startRow,startCol,endRow,endCol,newBorder,'top');
                    table.render();
                    setDirty();
                },
                undo:function(){
                    updateOldBorderStyles(_this.context,startRow,startCol,endRow,endCol,oldBorderStyle);
                    setDirty();
                }
            });
            setDirty();
        });
        ul.append(topBorder);
        const bottomBorder=$(`<li>
                <a href="###">
                    <i class="ureport ureport-bottom-border" style="color: #0e90d2;"></i> ${window.i18n.tools.border.bottomBorder}
                </a>
            </li>`);
        bottomBorder.click(function(){
            if(!_this.checkSelection()){
                return;
            }
            const table=_this.context.hot;
            const selected=table.getSelected(),oldStyle={};
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
            let newBorder={
                width:1,
                color:'0,0,0',
                style:'solid'
            };
            let oldBorderStyle=updateBorderStyles(_this.context,startRow,startCol,endRow,endCol,newBorder,'bottom');
            table.render();
            undoManager.add({
                redo:function(){
                    oldBorderStyle=updateBorderStyles(_this.context,startRow,startCol,endRow,endCol,newBorder,'bottom');
                    table.render();
                    setDirty();
                },
                undo:function(){
                    updateOldBorderStyles(_this.context,startRow,startCol,endRow,endCol,oldBorderStyle);
                    setDirty();
                }
            });
            setDirty();
        });
        ul.append(bottomBorder);
        ul.append(`<li class="divider"></li>`);
        const customBorder=$(`<li>
                <a href="###">
                    <i class="ureport ureport-full-border" style="color: #0e90d2;"></i> ${window.i18n.tools.border.customBorder}
                </a>
            </li>`);
        let content=null;
        const topBorderStyle={width:1,style:'solid',color:'0,0,0'};
        const bottomBorderStyle={width:1,style:'solid',color:'0,0,0'};
        const leftBorderStyle={width:1,style:'solid',color:'0,0,0'};
        const rightBorderStyle={width:1,style:'solid',color:'0,0,0'};
        customBorder.click(function(){
            if(!_this.checkSelection()){
                return;
            }
            content=showCustomBorderDialog(_this.context,content,topBorderStyle,bottomBorderStyle,leftBorderStyle,rightBorderStyle);
        });
        ul.append(customBorder);
        group.append(mainBtn);
        group.append(ul);
        return group;
    }
}

function showCustomBorderDialog(context,content,topBorderStyle,bottomBorderStyle,leftBorderStyle,rightBorderStyle){
    if(!content){
        content=$('<div></div>');
        const ul=$("<ul class='nav nav-tabs'></ul>");
        content.append(ul);
        const topLi=$(`<li class='active'><a data-toggle='tab' href='#topBorderConfig'>${window.i18n.tools.border.up}</a></li>`);
        ul.append(topLi);
        const bottomLi=$(`<li><a data-toggle='tab' href='#bottomBorderConfig'>${window.i18n.tools.border.down}</a></li>`);
        ul.append(bottomLi);
        const leftLi=$(`<li><a data-toggle='tab' href='#leftBorderConfig'>${window.i18n.tools.border.left}</a></li>`);
        ul.append(leftLi);
        const rightLi=$(`<li><a data-toggle='tab' href='#rightBorderConfig'>${window.i18n.tools.border.right}</a></li>`);
        ul.append(rightLi);

        const tabContent=$("<div class='tab-content'></div>");
        const topBorderConfig=$(`<div class="tab-pane fade in active" id="topBorderConfig"></div>`);
        tabContent.append(topBorderConfig);
        buildBorderStyleContent(topBorderConfig,topBorderStyle);

        const bottomBorderConfig=$(`<div class="tab-pane fade" id="bottomBorderConfig"></div>`);
        tabContent.append(bottomBorderConfig);
        buildBorderStyleContent(bottomBorderConfig,bottomBorderStyle);

        const leftBorderConfig=$(`<div class="tab-pane fade" id="leftBorderConfig"></div>`);
        tabContent.append(leftBorderConfig);
        buildBorderStyleContent(leftBorderConfig,leftBorderStyle);

        const rightBorderConfig=$(`<div class="tab-pane fade" id="rightBorderConfig"></div>`);
        tabContent.append(rightBorderConfig);
        buildBorderStyleContent(rightBorderConfig,rightBorderStyle);
        content.append(tabContent);
    }

    dialog(`${window.i18n.tools.border.customBorderLine}`,content,function(){
        const selected=context.hot.getSelected();
        const startRow=selected[0],startCol=selected[1],endRow=selected[2],endCol=selected[3];
        let oldBorderStyle=updateCustomBorderStyle(context,startRow,startCol,endRow,endCol,leftBorderStyle,rightBorderStyle,topBorderStyle,bottomBorderStyle);
        undoManager.add({
            redo:function(){
                oldBorderStyle=updateCustomBorderStyle(context,startRow,startCol,endRow,endCol,leftBorderStyle,rightBorderStyle,topBorderStyle,bottomBorderStyle);
            },
            undo:function(){
                updateOldBorderStyles(context,startRow,startCol,endRow,endCol,oldBorderStyle);
            }
        });
        setDirty();
    });
    content.parents('.modal-content').css({width:'280px'});
    return content;
}

function updateCustomBorderStyle(context,startRow,startCol,endRow,endCol,leftBorderStyle,rightBorderStyle,topBorderStyle,bottomBorderStyle){
    let hot=context.hot;
    let left=leftBorderStyle,right=rightBorderStyle,top=topBorderStyle,bottom=bottomBorderStyle;
    if(leftBorderStyle.style==='none'){
        left="";
    }
    if(rightBorderStyle.style==='none'){
        right="";
    }
    if(topBorderStyle.style==='none'){
        top="";
    }
    if(bottomBorderStyle.style==='none'){
        bottom="";
    }
    const oldBorderStyle={};
    for(let i=startRow;i<=endRow;i++){
        for(let j=startCol;j<=endCol;j++){
            let td=hot.getCell(i,j);
            let cellDef=context.getCell(i,j);
            if(!cellDef){
                continue;
            }
            const cellStyle=cellDef.cellStyle;
            oldBorderStyle[i+","+j]={
                leftBorder:cellStyle.leftBorder,
                rightBorder:cellStyle.rightBorder,
                topBorder:cellStyle.topBorder,
                bottomBorder:cellStyle.bottomBorder
            };
            cellStyle.leftBorder=cloneBorder(left);
            cellStyle.rightBorder=cloneBorder(right);
            cellStyle.topBorder=cloneBorder(top);
            cellStyle.bottomBorder=cloneBorder(bottom);
        }
    }
    hot.render();
    return oldBorderStyle;
}

function cloneBorder(border){
    if(border && border!==""){
        const text=JSON.stringify(border);
        const newJson=JSON.parse(text);
        return newJson;
    }else{
        return border;
    }
}

function buildBorderStyleContent(borderConfig,borderStyle){
    const borderLineContainer=$(`<div style="margin: 20px 0 10px 0;"><span>${window.i18n.tools.border.lineStyle}：</span></div>`);
    borderConfig.append(borderLineContainer);
    const borderLineList=$(`
                <select class="form-control" style="display: inline-block;width:120px">
                    <option value="solid" ${borderStyle.style==='solid' ? 'selected' : ''}>${window.i18n.tools.border.solidLine}</option>
                    <option value="dashed" ${borderStyle.style==='dashed' ? 'selected' : ''}>${window.i18n.tools.border.dashed}</option>
                    <option value="none" ${!borderStyle.style ? 'selected' : ''}>${window.i18n.tools.border.none}</option>
                </select>
            `);
    borderLineContainer.append(borderLineList);
    borderLineList.change(function(){
        const value=$(this).val();
        borderStyle.style=value;
    });

    const borderSizeContainer=$(`<div style="margin: 20px 0 10px 0;"><span>${window.i18n.tools.border.size}：</span></div>`);
    borderConfig.append(borderSizeContainer);
    const borderSizeList=$(`
                <select class="form-control" style="display: inline-block;width:120px">
                </select>
            `);
    for(let i=1;i<=10;i++){
        if(borderStyle.width===i){
            borderSizeList.append(`<option value="${i}" selected>${i}</option>`);
        }else{
            borderSizeList.append(`<option value="${i}">${i}</option>`);
        }
    }
    borderSizeContainer.append(borderSizeList);
    borderSizeList.change(function(){
        borderStyle.width=$(this).val();
    });

    const borderColor=$(`<div></div>`);
    borderConfig.append(borderColor);
    const topBorderColorContainer=$(`<div><span>${window.i18n.tools.border.color}：</span></div>`);
    borderColor.append(topBorderColorContainer);
    topBorderColorContainer.colorpicker({
        color: borderStyle.color,
        container: true,
        inline: true,
        colorSelectors: {
            'black': '#000000',
            'red': '#FF0000',
            'default': '#777777',
            'primary': '#337ab7',
            'success': '#5cb85c',
            'info': '#5bc0de',
            'warning': '#f0ad4e',
            'danger': '#d9534f'
        }
    });
    topBorderColorContainer.colorpicker().on("changeColor",function(e){
        let rgb=e.color.toRGB();
        let color=rgb.r+","+rgb.g+","+rgb.b;
        borderStyle.color=color;
    });
}

function updateOldBorderStyles(context,startRow,startCol,endRow,endCol,oldBorderStyle){
    let hot=context.hot;
    for(let i=startRow;i<=endRow;i++){
        for(let j=startCol;j<=endCol;j++){
            let td=hot.getCell(i,j);
            let cellDef=context.getCell(i,j);
            if(!cellDef){
                continue;
            }
            let oldBorder=oldBorderStyle[i + "," + j];
            let cellStyle=cellDef.cellStyle;
            cellStyle.leftBorder=oldBorder.leftBorder || "";
            cellStyle.rightBorder=oldBorder.rightBorder || "";
            cellStyle.topBorder=oldBorder.topBorder || "";
            cellStyle.bottomBorder=oldBorder.bottomBorder || "";
        }
    }
    hot.render();
}

function updateBorderStyles(context,startRow,startCol,endRow,endCol,newBorder,target){
    const oldStyle={},hot=context.hot;
    for(let i=startRow;i<=endRow;i++){
        for(let j=startCol;j<=endCol;j++){
            let td=hot.getCell(i,j);
            let cellDef=context.getCell(i,j);
            if(!cellDef){
                continue;
            }
            let cellStyle=cellDef.cellStyle;
            oldStyle[i + "," + j] = {
                leftBorder: cellStyle.leftBorder,
                rightBorder: cellStyle.rightBorder,
                topBorder: cellStyle.topBorder,
                bottomBorder: cellStyle.bottomBorder
            };
            if(!target){
                cellStyle.leftBorder=newBorder;
                cellStyle.rightBorder=newBorder;
                cellStyle.topBorder=newBorder;
                cellStyle.bottomBorder=newBorder;
            }else if(target==='left'){
                cellStyle.leftBorder=newBorder;
                cellStyle.rightBorder='';
                cellStyle.topBorder='';
                cellStyle.bottomBorder='';
            }else if(target==='right'){
                cellStyle.rightBorder=newBorder;
                cellStyle.leftBorder='';
                cellStyle.topBorder='';
                cellStyle.bottomBorder='';
            }else if(target==='top'){
                cellStyle.topBorder=newBorder;
                cellStyle.leftBorder='';
                cellStyle.rightBorder='';
                cellStyle.bottomBorder='';
            }else if(target==='bottom'){
                cellStyle.bottomBorder=newBorder;
                cellStyle.leftBorder='';
                cellStyle.rightBorder='';
                cellStyle.topBorder='';
            }
        }
    }
    return oldStyle;
};