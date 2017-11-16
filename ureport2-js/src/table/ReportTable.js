/**
 * Created by Jacky.Gao on 2017-01-26.
 */
import * as utils from '../Utils.js';
import {afterRenderer} from './CellRenderer.js';
import buildMenuConfigure from './ContextMenu.js';
import Handsontable from 'handsontable';

export default class ReportTable{
    constructor(container,callback){
        this.container=container;
        this.hot=new Handsontable(container,{
            startCols:1,
            startRows:1,
            fillHandle:{
                autoInsertRow:false
            },
            colHeaders:true,
            rowHeaders:true,
            autoColumnSize:false,
            autoRowSize:false,
            manualColumnResize:true,
            manualRowResize:true,
            maxColsNumber:700,
            outsideClickDeselects:false
        });
        this.buildMenu();
        this.hot.addHook("afterRenderer",afterRenderer);
        let file=utils.getParameter("_u");
        if(!file || file===null || file===''){
            file='classpath:template/template.ureport.xml';
        }else{
            window._reportFile=file;
        }
        this.cellsMap=new Map();
        this.loadFile(file,callback);
        this.hot.addHook('afterRowResize', function(currentRow,newSize) {
            let rowHeights=this.getSettings().rowHeights;
            let oldRowHeights=rowHeights.concat([]);
            let newRowHeights=rowHeights.concat([]);
            newRowHeights.splice(currentRow,1,newSize);
            this.updateSettings({
                rowHeights:newRowHeights,
                manualRowResize:newRowHeights
            });
            const _this=this;
            utils.undoManager.add({
                redo:function(){
                    rowHeights=_this.getSettings().rowHeights;
                    oldRowHeights=rowHeights.concat([]);
                    newRowHeights.splice(currentRow,1,newSize);
                    _this.updateSettings({
                        rowHeights:newRowHeights,
                        manualRowResize:newRowHeights
                    });
                    utils.setDirty();
                },
                undo:function(){
                    _this.updateSettings({
                        rowHeights:oldRowHeights,
                        manualRowResize:oldRowHeights
                    });
                    utils.setDirty();
                }
            });
            utils.setDirty();
        });
        this.hot.addHook('afterColumnResize',function(currentColumn,newSize){
            let colWidths=this.getSettings().colWidths;
            let newColWidths=colWidths.concat([]);
            let oldColWidths=colWidths.concat([]);
            newColWidths.splice(currentColumn,1,newSize);
            this.updateSettings({
                colWidths:newColWidths,
                manualColumnResize:newColWidths
            });
            const _this=this;
            utils.undoManager.add({
                redo:function(){
                    colWidths=_this.getSettings().colWidths;
                    newColWidths=colWidths.concat([]);
                    oldColWidths=colWidths.concat([]);
                    newColWidths.splice(currentColumn,1,newSize);
                    _this.updateSettings({
                        colWidths:newColWidths,
                        manualColumnResize:newColWidths
                    });
                    utils.setDirty();
                },
                undo:function(){
                    _this.updateSettings({
                        colWidths:oldColWidths,
                        manualColumnResize:oldColWidths
                    });
                    utils.setDirty();
                }
            });
            utils.setDirty();
        });
    }

    loadFile(file,callback){
        const _this=this;
        const url=window._server+"/designer/loadReport";
        $.ajax({
            url,
            type:'POST',
            data:{file},
            success:function(reportDef){
                _this.reportDef=reportDef;
                _this._buildReportData(reportDef);
                if(callback){
                    callback.call(_this,reportDef);
                }
                _this.hot.render();
                if(file!=='classpath:template/template.ureport.xml'){
                    _this.hot.context.fileInfo.setFile(file);
                }else{
                    _this.hot.context.fileInfo.setFile(`${window.i18n.table.report.tip}`);
                }
                if(reportDef.paper.bgImage){
                    $('.ht_master').css('background',`url(${reportDef.paper.bgImage}) 50px 26px no-repeat`);
                }else{
                    $('.ht_master').css('background','transparent');
                }
            },
            error:function(response){
                if(response && response.responseText){
                    alert("服务端错误："+response.responseText+"");
                }else{
                    alert(`${window.i18n.table.report.load}${file}${window.i18n.table.report.fail}`);
                }
            }
        });
    }

    _buildReportData(data){
        this.cellsMap.clear();
        const rows=data.rows;
        const rowHeights=[];
        for(let row of rows){
            const height=row.height;
            rowHeights.push(utils.pointToPixel(height));
        }
        const columns =data.columns;
        const colWidths=[];
        for(let col of columns){
            const width=col.width;
            colWidths.push(utils.pointToPixel(width));
        }
        const cellsMap=data.cellsMap;
        const dataArray=[],mergeCells=[];
        for(let row of rows){
            const rowData=[];
            for(let col of columns){
                let key=row.rowNumber+","+col.columnNumber;
                let cell=cellsMap[key];
                if(cell){
                    this.cellsMap.set(key,cell);
                    rowData.push(cell.value.value || "");
                    let rowspan=cell.rowSpan,colspan=cell.colSpan;
                    if(rowspan>0 || colspan>0){
                        if(rowspan===0)rowspan=1;
                        if(colspan===0)colspan=1;
                        mergeCells.push({
                            rowspan,
                            colspan,
                            row:row.rowNumber-1,
                            col:col.columnNumber-1
                        });
                    }
                }else{
                    rowData.push("");
                }
            }
            dataArray.push(rowData);
        }
        this.hot.loadData(dataArray);
        this.hot.updateSettings({
            colWidths,
            rowHeights,
            mergeCells,
            readOnly:true
        });
    }

    buildMenu(){
        this.hot.updateSettings({
            contextMenu: buildMenuConfigure()
        });
    }

    bindSelectionEvent(callback){
        const _this=this;
        Handsontable.hooks.add("afterSelectionEnd",function(rowIndex,colIndex,row2Index,col2Index){
            callback.call(_this,rowIndex,colIndex,row2Index,col2Index);
        },this.hot);
    }
};
