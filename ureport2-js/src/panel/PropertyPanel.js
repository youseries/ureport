/**
 * Created by Jacky.Gao on 2017-02-04.
 */
import SimpleValueEditor from './property/SimpleValueEditor.js';
import ExpressionValueEditor from './property/ExpressionValueEditor.js';
import DatasetValueEditor from './property/DatasetValueEditor.js';
import ImageValueEditor from './property/ImageValueEditor.js';
import SlashValueEditor from './property/SlashValueEditor.js';
import ZxingValueEditor from './property/ZxingValueEditor.js';
import URLParameterDialog from '../dialog/URLParameterDialog.js';
import BarChartValueEditor from './property/chart/BarChartValueEditor.js';
import LineChartValueEditor from './property/chart/LineChartValueEditor.js';
import AreaChartValueEditor from './property/chart/AreaChartValueEditor.js';
import HorizontalBarChartValueEditor from './property/chart/HorizontalBarChartValueEditor.js';
import BubbleChartValueEditor from './property/chart/BubbleChartValueEditor.js';
import DoughnutChartValueEditor from './property/chart/DoughnutChartValueEditor.js';
import PieChartValueEditor from './property/chart/PieChartValueEditor.js';
import PolarChartValueEditor from './property/chart/PolarChartValueEditor.js';
import RadarChartValueEditor from './property/chart/RadarChartValueEditor.js';
import ScatterChartValueEditor from './property/chart/ScatterChartValueEditor.js';
import CrossTabWidget from '../widget/CrossTabWidget.js';
import {setDirty} from '../Utils.js';
import {alert} from '../MsgBox.js'

export default class PropertyPanel{
    constructor(context){
        this.context=context;
    }
    buildPanel(){
        this.panel=$(`<div style="margin: 8px"></div>`);
        this._buildParentCell();
        this._buildRenderer();
        this._buildLinkConfig();
        this._buildCellType();
        this.editorMap=new Map();
        const simpleValueEditor=new SimpleValueEditor(this.panel,this.context);
        this.editorMap.set('simple',simpleValueEditor);
        const expressionValueEditor=new ExpressionValueEditor(this.panel,this.context);
        this.editorMap.set('expression',expressionValueEditor);
        const datasetValueEditor=new DatasetValueEditor(this.panel,this.context);
        this.editorMap.set('dataset',datasetValueEditor);
        const imageValueEditor=new ImageValueEditor(this.panel,this.context);
        this.editorMap.set('image',imageValueEditor);
        const slashValueEditor=new SlashValueEditor(this.panel,this.context);
        this.editorMap.set('slash',slashValueEditor);
        const zxingValueEditor=new ZxingValueEditor(this.panel,this.context);
        this.editorMap.set('zxing',zxingValueEditor);

        this.chartEditorMap=new Map();
        this.chartEditorMap.set('bar',new BarChartValueEditor(this.panel,this.context));
        this.chartEditorMap.set('line',new LineChartValueEditor(this.panel,this.context));
        this.chartEditorMap.set('horizontalBar',new HorizontalBarChartValueEditor(this.panel,this.context));
        this.chartEditorMap.set('area',new AreaChartValueEditor(this.panel,this.context));
        this.chartEditorMap.set('radar',new RadarChartValueEditor(this.panel,this.context));
        this.chartEditorMap.set('polarArea',new PolarChartValueEditor(this.panel,this.context));
        this.chartEditorMap.set('scatter',new ScatterChartValueEditor(this.panel,this.context));
        this.chartEditorMap.set('bubble',new BubbleChartValueEditor(this.panel,this.context));
        this.chartEditorMap.set('doughnut',new DoughnutChartValueEditor(this.panel,this.context));
        this.chartEditorMap.set('pie',new PieChartValueEditor(this.panel,this.context));

        return this.panel;
    }

    _buildLinkConfig(){
        const _this=this;
        this.linkGroup=$(`<fieldset style="padding: 10px;border:solid 1px #dddddd;border-radius: 8px">
        <legend style="width: auto;margin-bottom: 1px;border-bottom:none;font-size: inherit;color: #4b4b4b;">${window.i18n.property.prop.linkConfig}</legend></fieldset>`);
        const urlGroup=$(`<div class="form-group" style="margin-bottom:8px"><label>URL(<span style="font-size: 12px;color: #747474" title="如:$\{# == '1' ? 'a.jsp' : 'b.jsp'}">支持表达式,表达式定义在$\{...}中</span>)：</label></div>`);
        this.linkGroup.append(urlGroup);
        this.linkEditor=$(`<input type="text" placeholder="如:$\{# == '1' ? 'a.jsp' : 'b.jsp'}" class="form-control" style="width: 360px;padding: 3px;font-size: 12px;height: 25px;">`);
        urlGroup.append(this.linkEditor);
        this.linkEditor.change(function(){
            _this.cellDef.linkUrl=$(this).val();
            setDirty();
        });
        const configGroup=$(`<div class="form-group" style="margin-bottom:0px"><label>${window.i18n.property.prop.target}</label></div>`);
        this.linkGroup.append(configGroup);
        this.targetSelect=$(`<select class="form-control" style="display: inline-block;width: 160px;font-size: 12px;height: 25px;padding: 3px;">
            <option value="_blank">${window.i18n.property.prop.newWindow}</option>
            <option value="_self">${window.i18n.property.prop.currentWindow}</option>
            <option value="_parent">${window.i18n.property.prop.parentWindow}</option>
            <option value="_top">${window.i18n.property.prop.topWindow}</option>
        </select>`);
        configGroup.append(this.targetSelect);
        this.targetSelect.change(function(){
            _this.cellDef.linkTargetWindow=$(this).val();
            setDirty();
        });
        const urlParameterDialog=new URLParameterDialog();
        const parameterButton=$(`<button type="button" class="btn btn-primary" style="margin-left: 10px;font-size: 12px;height: 25px;padding: 4px 10px;">${window.i18n.property.prop.urlParameterConfig}</button>`);
        configGroup.append(parameterButton);
        parameterButton.click(function(){
            if(!_this.cellDef.linkUrl || _this.cellDef.linkUrl===''){
                alert(`${window.i18n.property.prop.urlTip}`);
                return;
            }
            if(!_this.cellDef.linkParameters){
                _this.cellDef.linkParameters=[];
            }
            urlParameterDialog.show(_this.cellDef.linkParameters);
            setDirty();
        });
        this.panel.append(this.linkGroup);
        this.linkGroup.hide();
    }

    _buildCellType(){
        this.typeGruop=$(`<div class="form-group" style="margin-bottom:10px;margin-top: 10px;"><label>${window.i18n.property.prop.cellType}</label></div>`);
        const radioName="__cell_value_type";
        this.typeSelect=$(`<select class="form-control" style="display: inline-block;width:280px;padding: 3px;font-size: 12px;height: 25px;">
            <option value="simple">${window.i18n.property.prop.text}</option>
            <option value="expression">${window.i18n.property.prop.expr}</option>
            <option value="dataset">${window.i18n.property.prop.dataset}</option>
            <option value="image">${window.i18n.property.prop.image}</option>
            <option value="slash">${window.i18n.property.prop.slash}</option>
            <option value="qrcode">${window.i18n.property.prop.qrcode}</option>
            <option value="barcode">${window.i18n.property.prop.barcode}</option>
            <option value="chart">${window.i18n.property.prop.chart}</option>
        </select>`);
        this.typeGruop.append(this.typeSelect);
        this.panel.append(this.typeGruop);
        this.typeGruop.hide();
        const _this=this;
        this.typeSelect.change(function(){
            for(let editor of _this.editorMap.values()){
                editor.hide();
            }
            let cellDef=_this.cellDef;
            let value=$(this).val();
            if(value==='simple'){
                if(cellDef.value.type!=='simple'){
                    cellDef.value={type:'simple'};
                }
                cellDef.expand='None';
                _this.editorMap.get('simple').show(_this.cellDef,_this.rowIndex,_this.colIndex,_this.row2Index,_this.col2Index);
            }else if(value==='expression'){
                if(cellDef.value.type!=='expression'){
                    cellDef.value={type:'expression',value:''};
                }
                cellDef.expand='None';
                _this.editorMap.get('expression').show(_this.cellDef,_this.rowIndex,_this.colIndex,_this.row2Index,_this.col2Index);
            }else if(value==='dataset'){
                if(cellDef.value.type!=='dataset'){
                    cellDef.value={type:'dataset',datasetName:'',property:'',aggregate:'',conditions:[],order:'none'};
                }
                cellDef.expand='Down';
                _this.editorMap.get('dataset').show(_this.cellDef,_this.rowIndex,_this.colIndex,_this.row2Index,_this.col2Index);
            }else if(value==='image'){
                if(cellDef.value.type!=='image'){
                    cellDef.value={type:'image',source:'text'};
                }
                cellDef.expand='None';
                _this.editorMap.get('image').show(_this.cellDef,_this.rowIndex,_this.colIndex,_this.row2Index,_this.col2Index);
            }else if(value==='qrcode'){
                if(cellDef.value.type!=='zxing' || cellDef.value.category!=='qrcode'){
                    const rowIndex=_this.rowIndex,colIndex=this.colIndex;
                    const td=_this.context.hot.getCell(rowIndex,colIndex);
                    const width=_this._buildWidth(colIndex,td.colSpan,_this.context.hot);
                    const height=_this._buildHeight(rowIndex,td.rowSpan,_this.context.hot);
                    cellDef.value={width,height,type:'zxing',source:'text',category:'qrcode',data:''};
                    cellDef.expand='None';
                }
                _this.editorMap.get('zxing').show(_this.cellDef,_this.rowIndex,_this.colIndex,_this.row2Index,_this.col2Index);
            }else if(value==='barcode'){
                if(cellDef.value.type!=='zxing' || cellDef.value.category!=='barcode'){
                    const rowIndex=_this.rowIndex,colIndex=this.colIndex;
                    const td=_this.context.hot.getCell(rowIndex,colIndex);
                    const width=_this._buildWidth(colIndex,td.colSpan,_this.context.hot);
                    const height=_this._buildHeight(rowIndex,td.rowSpan,_this.context.hot);
                    cellDef.value={width,height,type:'zxing',source:'text',category:'barcode',data:'',format:'CODE_128'};
                    cellDef.expand='None';
                }
                _this.editorMap.get('zxing').show(_this.cellDef,_this.rowIndex,_this.colIndex,_this.row2Index,_this.col2Index);
            }else if(value==='slash'){
                cellDef.crossTabWidget=new CrossTabWidget(_this.context,_this.rowIndex,_this.colIndex);
                cellDef.expand='None';
                _this.editorMap.get('slash').show(_this.cellDef,_this.rowIndex,_this.colIndex,_this.row2Index,_this.col2Index);
            }else if(value==='chart'){
                const rowIndex=_this.rowIndex,colIndex=this.colIndex;
                const td=_this.context.hot.getCell(rowIndex,colIndex);
                const width=_this._buildWidth(colIndex,td.colSpan,_this.context.hot);
                const height=_this._buildHeight(rowIndex,td.rowSpan,_this.context.hot);
                cellDef.value={
                    width,
                    height,
                    type:'chart',
                    chart:{
                        dataset:{
                            type:'pie'
                        }
                    }
                };
            }
            _this.context.hot.setDataAtCell(_this.rowIndex,_this.colIndex,'');
            _this.context.hot.render();
            setDirty();
        });
    }

    _buildParentCell(){
        this.parentGroup=$(`<div></div>`);
        const leftParentGroup=$(`<div class="form-group" style="margin-bottom:6px"><label>${window.i18n.property.prop.leftParent}</label></div>`);
        this.parentGroup.append(leftParentGroup);
        this.defaultLeftRadio=$(`<label class="checkbox-inline" style="padding-left: 2px"><input type="radio" class="__left_p_radio" name="__left_p_radio" value="default">${window.i18n.property.prop.default}</label>`);
        leftParentGroup.append(this.defaultLeftRadio);
        this.customLeftRadio=$(`<label class="checkbox-inline" style="padding-left: 2px"><input type="radio" class="__left_p_radio" name="__left_p_radio" value="custom">${window.i18n.property.prop.custom}</label>`);
        leftParentGroup.append(this.customLeftRadio);

        this.leftParentCellNameSelect=$(`<select class="form-control" disabled style="width: 90px;display: inline-block;margin-left: 10px;padding: 3px;font-size: 12px;height: 25px"></select>`);
        leftParentGroup.append(this.leftParentCellNameSelect);
        this.leftParentRowNumberSelect=$(`<select class="form-control" disabled style="width: 66px;display: inline-block;margin-left: 10px;padding: 3px;font-size: 12px;height: 25px"></select>`);
        leftParentGroup.append(this.leftParentRowNumberSelect);
        const _this=this;
        this.leftParentCellNameSelect.change(function(){
            let name=$(this).val();
            if(name==='root'){
                _this.leftParentRowNumberSelect.prop('disabled',true);
                _this.leftParentRowNumberSelect.val('');
                _this._setParentCell('root',true);
            }else{
                _this.leftParentRowNumberSelect.prop('disabled',false);
                let num=_this.leftParentRowNumberSelect.val();
                if(name!=='' && num!==''){
                    _this._setParentCell(name+num,true);
                }
            }
        });
        this.leftParentRowNumberSelect.change(function(){
            let name=_this.leftParentCellNameSelect.val();
            if(name==='root'){
                _this._setParentCell('root',true);
            }else{
                let num=$(this).val();
                if(name!==''&& num!==''){
                    _this._setParentCell(name+num,true);
                }
            }
        });

        this.defaultLeftRadio.children('input').click(function(){
            _this.leftParentCellNameSelect.prop("disabled",true);
            _this.leftParentRowNumberSelect.prop("disabled",true);
            _this._setParentCell(null,true);
        });
        this.customLeftRadio.children('input').click(function(){
            _this.leftParentCellNameSelect.prop("disabled",false);
            _this.leftParentRowNumberSelect.prop("disabled",false);
            setDirty();
        });

        const topParentGroup=$(`<div class="form-group" style="margin-bottom:6px"><label>${window.i18n.property.prop.topParent}</label></div>`);
        this.parentGroup.append(topParentGroup);
        this.defaultTopRadio=$(`<label class="checkbox-inline" style="padding-left: 2px"><input type="radio" name="__top_p_radio" value="default">${window.i18n.property.prop.default}</label>`);
        topParentGroup.append(this.defaultTopRadio);
        this.customTopRadio=$(`<label class="checkbox-inline" style="padding-left: 2px"><input type="radio" name="__top_p_radio" value="custom">${window.i18n.property.prop.custom}</label>`);
        topParentGroup.append(this.customTopRadio);

        this.topParentCellNameSelect=$(`<select class="form-control" disabled style="width: 90px;display: inline-block;margin-left: 10px;padding: 3px;font-size: 12px;height: 25px;"></select>`);
        topParentGroup.append(this.topParentCellNameSelect);
        this.topParentRowNumberSelect=$(`<select class="form-control" disabled style="width: 66px;display: inline-block;margin-left: 10px;padding: 3px;font-size: 12px;height: 25px"></select>`);
        topParentGroup.append(this.topParentRowNumberSelect);

        this.topParentCellNameSelect.change(function(){
            let name=$(this).val();
            if(name==='root'){
                _this.topParentRowNumberSelect.prop('disabled',true);
                _this.topParentRowNumberSelect.val('');
                _this._setParentCell('root',false);
            }else{
                _this.topParentRowNumberSelect.prop('disabled',false);
                let num=_this.topParentRowNumberSelect.val();
                if(name!=='' && num!==''){
                    _this._setParentCell(name+num,false);
                }
            }
        });
        this.topParentRowNumberSelect.change(function(){
            let name=_this.topParentCellNameSelect.val();
            if(name==='root'){
                _this._setParentCell('root',false);
            }else{
                let num=$(this).val();
                if(name!=='' && num!==''){
                    _this._setParentCell(name+num,false);
                }
            }
        });


        this.defaultTopRadio.children('input').click(function(){
            _this.topParentCellNameSelect.prop("disabled",true);
            _this.topParentRowNumberSelect.prop("disabled",true);
            _this._setParentCell(null,false);
        });
        this.customTopRadio.children('input').click(function(){
            _this.topParentCellNameSelect.prop("disabled",false);
            _this.topParentRowNumberSelect.prop("disabled",false);
        });

        this.panel.append(this.parentGroup);
        this.parentGroup.hide();
    }

    _setParentCell(cellName,isLeft){
        if(this.initialized){
            return;
        }
        for(let i=this.rowIndex;i<=this.row2Index;i++){
            for(let j=this.colIndex;j<=this.col2Index;j++){
                const cellDef=this.context.getCell(i,j);
                if(!cellDef){
                    continue;
                }
                if(isLeft){
                    if(cellName){
                        cellDef.leftParentCellName=cellName;
                    }else{
                        cellDef.leftParentCellName=null;
                    }
                }else{
                    if(cellName){
                        cellDef.topParentCellName=cellName;
                    }else{
                        cellDef.topParentCellName=null;
                    }
                }
            }
        }
        setDirty();
    }

    _buildParentCellNameOptions(select){
        select.empty();
        const hot=this.context.hot;
        const countCols=hot.countCols();
        select.append(`<option value="root">${window.i18n.property.prop.none}</option>`);
        for(let j=0;j<countCols;j++){
            let name=this.context.getCellName(null,j);
            select.append(`<option value="${name}">${name}</option>`);
        }
    }
    _buildParentRowNumberOptions(select){
        select.empty();
        const hot=this.context.hot;
        const countRows=hot.countRows();
        select.append(`<option></option>`);
        for(let j=0;j<countRows;j++){
            select.append(`<option>${j+1}</option>`);
        }
    }
    _buildRenderer(){
        this.rendererGroup=$(`<div class="form-group" style="margin-bottom:6px"><label>${window.i18n.property.prop.renderBean}</label></div>`);
        const rendererBeanEditorGroup=$(`<div class="input-group" style="width: 290px;display: inline-block;height: 22px;"></div>`);
        this.rendererGroup.append(rendererBeanEditorGroup);
        this.rendererBeanEditor=$(`<input type="text" class="form-control" style="width: 204px">`);
        rendererBeanEditorGroup.append(this.rendererBeanEditor);
        const addon=$(`<span class="input-group-btn"></span>`);
        const selectButton=$(`<button type="button" class="btn btn-default">${window.i18n.property.prop.selectBean}</button>`);
        addon.append(selectButton);
        rendererBeanEditorGroup.append(addon);
        const _this=this;
        selectButton.click(function(){

        });
        this.panel.append(this.rendererGroup);
        this.rendererBeanEditor.change(function(){
            _this._setRenderer($(this).val());
        });
        this.rendererGroup.hide();
    }

    _setRenderer(renderer){
        if(this.initialized){
            return;
        }
        for(let i=this.rowIndex;i<=this.row2Index;i++){
            for(let j=this.colIndex;j<=this.col2Index;j++){
                const cellDef=this.context.getCell(i,j);
                if(!cellDef){
                    continue;
                }
                cellDef.renderer=renderer;
            }
        }
        setDirty();
    }

    refresh(rowIndex,colIndex,row2Index,col2Index){
        const cellDef=this.context.getCell(rowIndex,colIndex);
        if(!cellDef){
            return;
        }
        this.cellDef=cellDef;
        let currentCellName=this.context.getCellName(rowIndex,colIndex);
        $('#__prop_tab_link').html(`${window.i18n.property.prop.prop}[${currentCellName}]`);
        this.rowIndex=rowIndex;
        this.colIndex=colIndex;
        this.row2Index=row2Index;
        this.col2Index=col2Index;
        this.parentGroup.show();
        this.typeGruop.show();
        this.linkGroup.show();
        //this.rendererGroup.show();
        this.initialized=true;
        this.linkEditor.val(cellDef.linkUrl);
        this.targetSelect.val(cellDef.linkTargetWindow);

        this._buildParentCellNameOptions(this.leftParentCellNameSelect);
        this._buildParentRowNumberOptions(this.leftParentRowNumberSelect);
        this._buildParentCellNameOptions(this.topParentCellNameSelect);
        this._buildParentRowNumberOptions(this.topParentRowNumberSelect);
        const leftParentCellName=cellDef.leftParentCellName;
        if(leftParentCellName){
            this.customLeftRadio.trigger('click');
            if(leftParentCellName==='root'){
                this.leftParentCellNameSelect.val('root');
                this.leftParentRowNumberSelect.val('');
            }else{
                let data=this._parseCellName(leftParentCellName);
                this.leftParentCellNameSelect.val(data.name);
                this.leftParentRowNumberSelect.val(data.num);
            }
            this.leftParentCellNameSelect.prop('disabled',false);
            this.leftParentRowNumberSelect.prop('disabled',false);
            this.leftParentCellNameSelect.trigger('change');
        }else{
            this.defaultLeftRadio.trigger('click');
            if(colIndex===0){
                this.leftParentCellNameSelect.val('root');
                this.leftParentRowNumberSelect.val('');
            }else{
                let row=rowIndex,col=colIndex-1;
                let td=this.context.hot.getCell(row,col);
                if($(td).css('display')==='none'){
                    let mergeCells=this.context.hot.getSettings().mergeCells;
                    for(let item of mergeCells){
                        let rowStart=item.row,rowspan=item.rowspan,colStart=item.col,colspan=item.colspan;
                        let rowEnd=rowStart+rowspan-1,colEnd=colStart+colspan-1;
                        if(row>=rowStart && row<=rowEnd && col>=colStart && col<=colEnd){
                            row=rowStart,col=colStart;
                            break;
                        }
                    }
                }
                let cellName=this.context.getCellName(row,col);
                let data=this._parseCellName(cellName);
                this.leftParentCellNameSelect.val(data.name);
                this.leftParentRowNumberSelect.val(data.num);
            }
            this.leftParentCellNameSelect.prop('disabled',true);
            this.leftParentRowNumberSelect.prop('disabled',true);
        }
        const topParentCellName=cellDef.topParentCellName;
        if(topParentCellName){
            this.customTopRadio.trigger('click');
            if(topParentCellName==='root'){
                this.topParentCellNameSelect.val('root');
                this.topParentRowNumberSelect.val('');
            }else{
                let data=this._parseCellName(topParentCellName);
                this.topParentCellNameSelect.val(data.name);
                this.topParentRowNumberSelect.val(data.num);
            }
            this.topParentCellNameSelect.prop('disabled',false);
            this.topParentRowNumberSelect.prop('disabled',false);
            this.topParentCellNameSelect.trigger('change');
        }else{
            this.defaultTopRadio.trigger('click');
            if(rowIndex===0){
                this.topParentCellNameSelect.val('root');
                this.topParentRowNumberSelect.val('');
            }else{
                let row=rowIndex-1,col=colIndex;
                let td=this.context.hot.getCell(row,col);
                if($(td).css('display')==='none'){
                    let mergeCells=this.context.hot.getSettings().mergeCells;
                    for(let item of mergeCells){
                        let rowStart=item.row,rowspan=item.rowspan,colStart=item.col,colspan=item.colspan;
                        let rowEnd=rowStart+rowspan-1,colEnd=colStart+colspan-1;
                        if(row>=rowStart && row<=rowEnd && col>=colStart && col<=colEnd){
                            row=rowStart,col=colStart;
                            break;
                        }
                    }
                }

                let cellName=this.context.getCellName(row,col);
                let data=this._parseCellName(cellName);
                this.topParentCellNameSelect.val(data.name);
                this.topParentRowNumberSelect.val(data.num);
            }
            this.topParentCellNameSelect.prop('disabled',true);
            this.topParentRowNumberSelect.prop('disabled',true);
        }
        const cellStyle=cellDef.cellStyle;
        if(cellStyle.renderer){
            this.rendererBeanEditor.val(cellStyle.renderer);
        }else{
            this.rendererBeanEditor.val("");
        }
        let type=cellDef.value.type || 'simple';
        if(type==='zxing'){
            const category=cellDef.value.category;
            this.typeSelect.val(category);
        }else{
            this.typeSelect.val(type);
        }
        for(let editor of this.editorMap.values()){
            editor.hide();
        }
        for(let editor of this.chartEditorMap.values()){
            editor.hide();
        }
        if(type==='chart'){
            const chartType=cellDef.value.chart.dataset.type;
            this.chartEditorMap.get(chartType).show(cellDef,rowIndex,colIndex,row2Index,col2Index);
        }else{
            this.editorMap.get(type).show(cellDef,rowIndex,colIndex,row2Index,col2Index);
        }
        this.initialized=false;
    }

    _parseCellName(cellName){
        let pos=-1;
        for(let i=0;i<cellName.length;i++){
            let char=cellName.charAt(i);
            let num=parseInt(char);
            if(!isNaN(num)){
                pos=i;
                break;
            }
        }
        const name=cellName.substring(0,pos);
        const num=cellName.substring(pos,cellName.length);
        return {name,num};
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