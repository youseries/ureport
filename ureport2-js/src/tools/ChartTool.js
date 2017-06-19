/**
 * Created by Jacky.Gao on 2017-01-25.
 */
import undoManager from 'undo-manager';
import Tool from './Tool.js';
import {setDirty} from '../Utils.js';

export default class ChartTool extends Tool{
    execute(){

    }
    buildButton(){
        const group=$(`<div class="btn-group"></div>`);
        const mainBtn=$(`<button type="button" class="btn btn-default dropdown-toggle" style="border:none;border-radius:0;background: #f8f8f8;padding: 6px 5px;" data-toggle="dropdown" title="图表">
            <i class="ureport ureport-pie" style="color: #0e90d2;"></i>
            <span class="caret"></span>
        </button>`);
        const ul=$(`<ul class="dropdown-menu" role="menu"></ul>`);
        const pie=$(`<li>
                <a href="###">
                    <i class="ureport ureport-pie" style="color: #0e90d2;"></i> 饼状图
                </a>
            </li>`);
        ul.append(pie);
        pie.click(function(){
        });
        const doughnut=$(`<li>
                <a href="###">
                    <i class="ureport ureport-doughnut" style="color: #0e90d2;"></i> 圆环图
                </a>
            </li>`);
        ul.append(doughnut);
        const line=$(`<li>
                <a href="###">
                    <i class="ureport ureport-line" style="color: #0e90d2;"></i> 曲线图
                </a>
            </li>`);
        ul.append(line);
        const bar=$(`<li>
                <a href="###">
                    <i class="ureport ureport-bar" style="color: #0e90d2;"></i> 柱状图
                </a>
            </li>`);
        ul.append(bar);
        const horBar=$(`<li>
                <a href="###">
                    <i class="ureport ureport-horizontal-bar" style="color: #0e90d2;"></i> 水平柱状图
                </a>
            </li>`);
        ul.append(horBar);
        const area=$(`<li>
                <a href="###">
                    <i class="ureport ureport-area" style="color: #0e90d2;"></i> 面积图
                </a>
            </li>`);
        ul.append(area);
        const radar=$(`<li>
                <a href="###">
                    <i class="ureport ureport-radar" style="color: #0e90d2;"></i> 雷达图
                </a>
            </li>`);
        ul.append(radar);
        const polar=$(`<li>
                <a href="###">
                    <i class="ureport ureport-polar" style="color: #0e90d2;"></i> 极坐标图
                </a>
            </li>`);
        ul.append(polar);
        const scatter=$(`<li>
                <a href="###">
                    <i class="ureport ureport-scatter" style="color: #0e90d2;"></i> 散点图
                </a>
            </li>`);
        ul.append(scatter);
        const bubble=$(`<li>
                <a href="###">
                    <i class="ureport ureport-bubble" style="color: #0e90d2;"></i> 气泡图
                </a>
            </li>`);
        ul.append(bubble);
        const mix=$(`<li>
                <a href="###">
                    <i class="ureport ureport-mixchart" style="color: #0e90d2;"></i> 组合图
                </a>
            </li>`);
        ul.append(mix);

        group.append(mainBtn);
        group.append(ul);
        return group;
    }
    _doClick(category){
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
            type:'chart',
            category:this._newChart(category),
            data:''
        };
        hot.render();
        setDirty();
        Handsontable.hooks.run(hot, 'afterSelectionEnd',startRow,startCol,endRow,endCol);
        const _this=this;
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
                    type:'chart',
                    category:_this._newChart(category),
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
    }
    _newChart(category){
        return {
            dataset:{
                type:category
            }
        };
        /*
        switch (category){
            case "pie":
                return {
                    dataset:{
                        type:"pie"
                    }
                };
            case "doughnut":
                return {
                    dataset:{
                        type:"doughnut"
                    }
                };
            case "line":
                return {
                    dataset:{
                        type:"line"
                    }
                };
            case "area":
                return {type:"area"};
            case "polar":
                return {type:"polar"};
            case "scatter":
                return {type:"scatter"};
            case "bar":
                return {type:"bar"};
            case "horizontalBar":
                return {type:"horizontalBar"};
            case "mix":
                return {type:"mix"};
            case "bubble":
                return {type:"bubble"};
            case "radar":
                return {type:"radar"};
        }
        */
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