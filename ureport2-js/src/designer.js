/**
 * Created by Jacky.Gao on 2017-01-25.
 */
import Context from './Context.js';
import ReportTable from './table/ReportTable.js';
import SaveTool from './tools/SaveTool.js';
import OpenTool from './tools/OpenTool.js';
import AlignLeftTool from './tools/AlignLeftTool.js';
import AlignTopTool from './tools/AlignTopTool.js';
import RedoTool from './tools/RedoTool.js';
import UndoTool from './tools/UndoTool.js';
import BorderTool from './tools/BorderTool.js';
import BoldTool from './tools/BoldTool.js';
import ItalicTool from './tools/ItalicTool.js';
import UnderlineTool from './tools/UnderlineTool.js';
import BgcolorTool from './tools/BgcolorTool.js';
import ForecolorTool from './tools/ForecolorTool.js';
import ImageTool from './tools/ImageTool.js';
import ChartTool from './tools/ChartTool.js';
import CrosstabTool from './tools/CrosstabTool.js';
import MergeTool from './tools/MergeTool.js';
import ImportTool from './tools/ImportTool.js';
import PreviewTool from './tools/PreviewTool.js';
import FontFamilyTool from './tools/FontFamilyTool.js';
import FontSizeTool from './tools/FontSizeTool.js';
import ZxingTool from './tools/ZxingTool.js';
import SettingsTool from './tools/SettingsTool.js';
import SearchFormSwitchTool from './tools/SearchFormSwitchTool.js';
import DatasourcePanel from './panel/DatasourcePanel.js';
import PropertyPanel from './panel/PropertyPanel.js';

import {undoManager} from './Utils.js';
import PrintLine from './PrintLine.js';
import FileInfo from './FileInfo.js';
import {renderRowHeader} from './table/HeaderUtils.js';

export default class UReportDesigner{
    constructor(containerId,searchFormContainerId){
        undoManager.setLimit(100);
        const _this=this;
        this.container=$('#'+containerId);
        const tableContainer=$(`<div></div>`);
        this.container.append(tableContainer);
        const fileInfo=new FileInfo();
        const reportTable=new ReportTable(tableContainer.get(0),function(){
            _this.context=new Context(this);
            _this.context.fileInfo=fileInfo;
            _this.buildTools(_this.context);
            _this.datasourcePanel=new DatasourcePanel(_this.context);
            _this.propertyPanel=new PropertyPanel(_this.context);
            _this.buildPropertyPanel();
            this.bindSelectionEvent(function(rowIndex, colIndex, row2Index, col2Index){
                _this.propertyPanel.refresh(rowIndex,colIndex,row2Index,col2Index);
                for(let tool of _this.tools){
                    if(tool.refresh){
                        tool.refresh(rowIndex,colIndex,row2Index,col2Index);
                    }
                }
            });
            _this.printLine=new PrintLine(_this.context);
            const rows=_this.context.reportDef.rows;
            for(let row of rows){
                const band=row.band;
                if(!band){
                    continue;
                }
                _this.context.addRowHeader(row.rowNumber-1,band);
            }
            renderRowHeader(_this.context.hot,_this.context);
        });
    }
    buildPropertyPanel(){
        const propContainerId='_prop_container';
        const dsContainerId='_datasource_container';
        const propertyPanel=$('<div class="ud-property-panel"/>');
        this.container.prepend(propertyPanel);
        const propertyTab=$(`<ul class="nav nav-tabs">
            <li class="active">
                <a href="#${propContainerId}" data-toggle="tab" id="__prop_tab_link">${window.i18n.panel.property}</a>
            </li>
            <li>
                <a href="#${dsContainerId}" data-toggle="tab">${window.i18n.panel.datasource}</a>
            </li>
        </ul>`);
        const trigger=$(`
            <i class="glyphicon glyphicon-circle-arrow-down"
                style="color:#9E9E9E;font-size: 16px;vertical-align: middle;cursor: pointer;float: right;margin: 10px 10px 0px 0px;"
                title="${window.i18n.panel.tip}">
             </i>
            `);
        propertyTab.append(trigger);
        propertyPanel.append(propertyTab);
        propertyTab.mousedown(function (e) {
            e.preventDefault();
        });
        const tabContent=$(`<div class="tab-content" style="min-height: 300px"/>`);
        const propContainer=$(`<div id="${propContainerId}" class="tab-pane fade in active"></div>`);
        const dsContainer=$(`<div id="${dsContainerId}" class="tab-pane fade"></div>`);
        tabContent.append(propContainer);
        tabContent.append(dsContainer);
        propContainer.append(this.propertyPanel.buildPanel());
        dsContainer.append(this.datasourcePanel.buildPanel());
        propertyPanel.append(tabContent);
        propertyPanel.draggable();
        trigger.click(function(){
            tabContent.toggle();
            const display=tabContent.css("display");
            if(!display || display==='none'){
                trigger.removeClass("glyphicon-circle-arrow-down");
                trigger.addClass("glyphicon-circle-arrow-left");
            }else{
                trigger.removeClass("glyphicon-circle-arrow-left");
                trigger.addClass("glyphicon-circle-arrow-down");
            }
        });
    }

    buildTools(context){
        const toolbar=$(`<div class="btn-group ud-toolbar top-toolbar"></div>`);
        this.container.prepend(toolbar);
        this.tools=[];
        this.tools.push(new PreviewTool(context));
        this.tools.push(new SaveTool(context));
        this.tools.push(new OpenTool(context));
        this.tools.push(new ImportTool(context));
        this.tools.push(new RedoTool(context));
        this.tools.push(new UndoTool(context));
        this.tools.push(new MergeTool(context));
        this.tools.push(new AlignLeftTool(context));
        this.tools.push(new AlignTopTool(context));
        this.tools.push(new BorderTool(context));
        this.tools.push(new FontFamilyTool(context));
        this.tools.push(new FontSizeTool(context));
        this.tools.push(new BoldTool(context));
        this.tools.push(new ItalicTool(context));
        this.tools.push(new UnderlineTool(context));
        this.tools.push(new BgcolorTool(context));
        this.tools.push(new ForecolorTool(context));
        this.tools.push(new CrosstabTool(context));
        this.tools.push(new ImageTool(context));
        this.tools.push(new ZxingTool(context));
        this.tools.push(new ChartTool(context));
        this.tools.push(new SettingsTool(context));
        this.tools.push(new SearchFormSwitchTool(context));
        for(const tool of this.tools){
            toolbar.append(tool.buildButton());
        }
    }
}