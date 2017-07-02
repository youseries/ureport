/**
 * Created by Jacky.Gao on 2017-02-07.
 */
import ConditionDialog from '../../dialog/ConditionDialog.js';
import {alert,confirm} from '../../MsgBox.js';
import uuid from 'node-uuid';
import {setDirty} from '../../Utils.js';
import BaseValueEditor from './BaseValueEditor.js';
import CustomGroupDialog from '../../dialog/CustomGroupDialog.js';
import MappingDialog from '../../dialog/MappingDialog.js';

export default class DatasetValueEditor extends BaseValueEditor{
    constructor(parentContainer,context){
        super();
        this.context=context;
        this.container=$(`<div style="margin: 5px;"></div>`);
        parentContainer.append(this.container);
        this.mappingDialog=new MappingDialog();
        this._init();
        this.container.hide();
    }
    _init(){
        const _this=this;

        const tab=$(`<ul class="nav nav-tabs">
            <li class="active"><a href="#__dataset_config" data-toggle="tab">数据集配置</a></li>
            <li><a href="#__filter_condition" data-toggle="tab">过滤条件</a></li>
            <li><a href="#__data_mapping" data-toggle="tab">数据映射</a></li>
        </ul>`);
        this.container.append(tab);
        const tabContainer=$(`<div class="tab-content"></div>`);
        this.container.append(tabContainer);

        const dsContainer=$(`<div id="__dataset_config" class="tab-pane fade in active"></div>`);
        tabContainer.append(dsContainer);
        const conditionContainer=$(`<div id="__filter_condition" class="tab-pane fade"></div>`);
        tabContainer.append(conditionContainer);
        this._buildConditionTable(conditionContainer);
        const mappingContainer=$(`<div id="__data_mapping" class="tab-pane fade"></div>`);
        tabContainer.append(mappingContainer);
        this._buildMappingTable(mappingContainer);

        const datasetGroup=$(`<div class="form-group" style="margin-bottom: 5px;margin-top: 10px;"><label>数据集：</label></div>`);
        this.datasetSelect=$(`<select class="form-control" style="display: inline-block;width:305px;padding:2px;font-size: 12px;height: 25px"></select>`);
        datasetGroup.append(this.datasetSelect);
        dsContainer.append(datasetGroup);

        const propertyGroup=$(`<div class="form-group" style="margin-left: 8px;margin-top: 5px;margin-bottom: 5px;"><label>属性：</label></div>`);
        this.propertySelect=$(`<select class="form-control" style="display: inline-block;width:310px;padding: 2px;font-size: 12px;height: 25px"></select>`);
        propertyGroup.append(this.propertySelect);
        dsContainer.append(propertyGroup);

        this.datasetSelect.change(function(){
            _this.propertySelect.empty();
            const dsName=$(this).val();
            let fields=[];
            for(let ds of _this.datasources){
                let datasets=ds.datasets || [];
                for(let dataset of datasets){
                    if(dataset.name===dsName){
                        fields=dataset.fields || [];
                        break;
                    }
                }
                if(fields.length>0){
                    break;
                }
            }
            for(let field of fields){
                _this.propertySelect.append(`<option>${field.name}</option>`);
            }
            _this._setDatasetName(dsName);
        });

        this.propertySelect.change(function(){
            const value=$(this).val();
            _this._setProperty(value);
        });

        const aggregateGroup=$(`<div class="form-group" style="margin-bottom: 10px;"><label>聚合方式：</label></div>`);
        this.aggregateSelect=$(`<select class="form-control" style="display: inline-block;width: 143px;font-size: 12px;height: 25px;padding: 3px;">
            <option value="select">列表</option>
            <option value="group">分组</option>
            <option value="customgroup">自定义分组</option>
            <option value="sum">汇总</option>
            <option value="count">统计数量</option>
            <option value="max">最大值</option>
            <option value="min">最小值</option>
            <option value="avg">平均值</option>
        </select>`);
        aggregateGroup.append(this.aggregateSelect);
        const customGroupButton=$(`<button type="button" class="btn btn-danger" style="margin-left: 10px;font-size: 12px;height: 25px;padding: 4px 10px;">自定义分组配置</button>`);
        this.aggregateSelect.change(function(){
            const value=$(this).val();
            _this.cellDef.value.aggregate=value;
            if(value==='customgroup'){
                customGroupButton.show();
            }else{
                customGroupButton.hide();
            }
            if(value==='group' || value==='select'){
                _this.mappingGroup.show();
            }else{
                _this.mappingGroup.hide();
            }
            if(value==='sum' || value==='count' || value==='max' || value==='min' || value==='avg'){
                sortGroup.hide();
                expandGroup.hide();
            }else{
                expandGroup.show();
                sortGroup.show();
            }
            _this._setAggregate(value);
        });
        const customGroupDialog=new CustomGroupDialog();
        customGroupButton.click(function(){
            if(!_this.cellDef.value.groupItems){
                _this.cellDef.value.groupItems=[];
            }
            let fields=_this._buildFields();
            customGroupDialog.show(_this.cellDef,fields);
            setDirty();
        });
        aggregateGroup.append(customGroupButton);
        dsContainer.append(aggregateGroup);

        const sortGroup=$(`<div class="form-group" style="margin-bottom: 10px;"><label>排序方式：</label></div>`);
        this.noneSortRadio=$(`<label class="checkbox-inline" style="padding-left: 2px"><input type="radio" name="__sort_radio" value="asc">不排序</label>`);
        sortGroup.append(this.noneSortRadio);
        this.noneSortRadio.children('input').click(function(){
            _this._setOrder('none');
        });
        this.ascSortRadio=$(`<label class="checkbox-inline" style="padding-left: 2px"><input type="radio" name="__sort_radio" value="asc">正序</label>`);
        sortGroup.append(this.ascSortRadio);
        this.ascSortRadio.children('input').click(function(){
            _this._setOrder('asc');
        });
        this.descSortRadio=$(`<label class="checkbox-inline" style="padding-left: 2px"><input type="radio" name="__sort_radio" value="asc">倒序</label>`);
        sortGroup.append(this.descSortRadio);
        this.descSortRadio.children('input').click(function(){
            _this._setOrder('desc');
        });
        dsContainer.append(sortGroup);

        const expandGroup=$(`<div class="form-group" style="margin-bottom: 10px;"><label>数据展开方向：</label></div>`);
        this.downExpandRadio=$(`<label class="checkbox-inline" style="padding-left: 2px"><input type="radio" name="__expand_radio" value="Down">向下</label>`);
        expandGroup.append(this.downExpandRadio);
        this.downExpandRadio.children('input').click(function(){
            _this._setExpand('Down');
        });
        this.rightExpandRadio=$(`<label class="checkbox-inline" style="padding-left: 2px"><input type="radio" name="__expand_radio" value="Right">向右</label>`);
        expandGroup.append(this.rightExpandRadio);
        this.rightExpandRadio.children('input').click(function(){
            _this._setExpand('Right');
        });
        this.noneExpandRadio=$(`<label class="checkbox-inline" style="padding-left: 2px"><input type="radio" name="__expand_radio" value="None">不展开</label>`);
        expandGroup.append(this.noneExpandRadio);
        this.noneExpandRadio.children('input').click(function(){
            _this._setExpand('None');
        });
        dsContainer.append(expandGroup);

        this._buildWrapCompute(dsContainer);
        this._buildFormat(dsContainer);
        this._buildFillBlankRows(dsContainer);
        this._buildConditionProperty(dsContainer);
    }

    _setDatasetName(datasetName){
        if(this.initialized){
            return;
        }
        const hot=this.context.hot;
        for(let i=this.rowIndex;i<=this.row2Index;i++){
            for(let j=this.colIndex;j<=this.col2Index;j++){
                const cellDef=hot.context.getCell(i,j);
                if(!cellDef){
                    continue;
                }
                const valueType=cellDef.value.type;
                if(valueType==='dataset'){
                    cellDef.value.datasetName=datasetName;
                }
            }
        }
        this._undateTableData();
        setDirty();
    }
    _setProperty(property){
        if(this.initialized){
            return;
        }
        const hot=this.context.hot;
        for(let i=this.rowIndex;i<=this.row2Index;i++){
            for(let j=this.colIndex;j<=this.col2Index;j++){
                const cellDef=hot.context.getCell(i,j);
                if(!cellDef){
                    continue;
                }
                const valueType=cellDef.value.type;
                if(valueType==='dataset'){
                    cellDef.value.property=property;
                }
            }
        }
        this._undateTableData();
        setDirty();
    }
    _setAggregate(aggregate){
        if(this.initialized){
            return;
        }
        const hot=this.context.hot;
        let none=false;
        for(let i=this.rowIndex;i<=this.row2Index;i++){
            for(let j=this.colIndex;j<=this.col2Index;j++){
                const cellDef=hot.context.getCell(i,j);
                if(!cellDef){
                    continue;
                }
                const valueType=cellDef.value.type;
                if(valueType==='dataset'){
                    cellDef.value.aggregate=aggregate;
                    if(aggregate==='sum' || aggregate==='count' || aggregate==='max' || aggregate==='min' || aggregate==='avg'){
                        cellDef.value.order='none';
                        cellDef.expand='None';
                        none=true;
                    }
                }
            }
        }
        if(none){
            this.noneSortRadio.children('input').trigger('click');
            this.noneExpandRadio.children('input').trigger('click');
        }
        this._undateTableData();
        hot.render();
        setDirty();
    }

    _setOrder(order){
        if(this.initialized){
            return;
        }
        const hot=this.context.hot;
        for(let i=this.rowIndex;i<=this.row2Index;i++){
            for(let j=this.colIndex;j<=this.col2Index;j++){
                const cellDef=hot.context.getCell(i,j);
                if(!cellDef){
                    continue;
                }
                const valueType=cellDef.value.type;
                if(valueType==='dataset'){
                    cellDef.value.order=order;
                }
            }
        }
        setDirty();
    }

    _setExpand(expand){
        if(this.initialized){
            return;
        }
        const hot=this.context.hot;
        for(let i=this.rowIndex;i<=this.row2Index;i++){
            for(let j=this.colIndex;j<=this.col2Index;j++){
                const cellDef=hot.context.getCell(i,j);
                if(!cellDef){
                    continue;
                }
                const valueType=cellDef.value.type;
                if(valueType==='dataset' || valueType==='expression'){
                    cellDef.expand=expand;
                }
            }
        }
        hot.render();
        setDirty();
    }

    _buildConditionTable(container){
        const _this=this;
        const group=$(`<div class="form-group" style="margin-bottom: 10px;"></div>`);
        const conditionGroup=$(`<span style="float: right"></span>`);
        group.append(conditionGroup);
        const addButton=$(`<button type="button" class="btn btn-default" title="添加过滤条件"><i class="glyphicon glyphicon-plus-sign"></i></button>`);
        conditionGroup.append(addButton);
        this.conditionList=$(`<select class="form-control" size="5" style="height: 100px;"></select>`);
        addButton.click(function(){
            let fields=_this._buildFields();
            if(!fields){
                return;
            }
            const conditions=_this.cellDef.value.conditions;
            const conditionDialog=new ConditionDialog(conditions);
            conditionDialog.show(function(left,op,right,join){
                const c={left,operation:op,right,join,id:uuid.v1()};
                conditions.push(c);
                let text=left+" "+op+" "+right;
                if(join){
                    text=join+" "+text;
                }
                const option=$(`<option>${text}</option>`);
                option.data(c);
                _this.conditionList.append(option);
            },fields);
        });
        const editButton=$(`<button type="button" class="btn btn-default" style="margin-left: 1px;" title="编辑选中的过滤条件"><i class="glyphicon glyphicon-edit"></i></button>`);
        conditionGroup.append(editButton);
        editButton.click(function(){
            const option=_this.conditionList.find('option:selected');
            if(option.length===0){
                alert('请先选中要编辑的条件！');
                return;
            }
            const condition=option.data();
            let fields=_this._buildFields();
            if(!fields){
                return;
            }
            const conditions=_this.cellDef.value.conditions;
            const conditionDialog=new ConditionDialog(conditions);
            conditionDialog.show(function(left,op,right,join){
                let targetCondition=null;
                for(let i=0;i<conditions.length;i++){
                    let c=conditions[i];
                    if(c.id===condition.id){
                        targetCondition=c;
                        break;
                    }
                }
                targetCondition.left=left;
                targetCondition.operation=op;
                targetCondition.right=right;
                targetCondition.join=join;
                let text=left+" "+op+" "+right;
                if(join){
                    text=join+" "+text;
                }
                option.data(targetCondition);
                option.html(text);
                setDirty();
            },fields,condition);
        });

        const delButton=$(`<button type="button" class="btn btn-default" style="margin-left: 1px;" title="删除选中的过滤条件"><i class="glyphicon glyphicon-minus-sign"></i></button>`);
        conditionGroup.append(delButton);
        delButton.click(function(){
            const option=_this.conditionList.find('option:selected');
            if(option.length===0){
                alert('请先选中要删除的条件！');
                return;
            }
            const condition=option.data();
            const conditions=_this.cellDef.value.conditions;
            let index=-1;
            for(let i=0;i<conditions.length;i++){
                let c=conditions[i];
                if(c.id===condition.id){
                    index=i;
                    break;
                }
            }
            conditions.splice(index,1);
            option.remove();
            setDirty();
        });
        group.append(this.conditionList);
        container.append(group);
    }

    _buildMappingTable(container){
        this.mappingGroup=$(`<div class="form-group" style="padding-top: 10px"></div>`);
        const addButton=$(`<button type="button" class="btn btn-default" style="float: right;" title="添加映射项"><i class="glyphicon glyphicon-plus-sign" style="color: #00746f;"></i></button>`);
        this.mappingGroup.append(addButton);
        const _this=this;
        addButton.click(function(){
            const newItem={value:'',label:''};
            _this.mappingDialog.show(function(){
                const datasetValue=_this.cellDef.value;
                if(!datasetValue.mappingItems){
                    datasetValue.mappingItems=[];
                }
                datasetValue.mappingItems.push(newItem);
                const tr=$(`<tr style="height: 30px"></tr>`);
                const valueTd=$(`<td style="vertical-align: middle">${newItem.value}</td>`);
                const labelTd=$(`<td style="vertical-align: middle">${newItem.label}</td>`);
                tr.append(valueTd);
                tr.append(labelTd);
                _this.mappingTbody.append(tr);
                const td=$(`<td style="vertical-align: middle"></td>`);
                tr.append(td);
                const delLink=$(`<a href="###"><i class="glyphicon glyphicon-trash" style="font-size: 16px;color: #d30e00;"></i></a>`);
                td.append(delLink);
                delLink.click(function(){
                    confirm('确定要删除？',function(){
                        const index=datasetValue.mappingItems.indexOf(newItem);
                        datasetValue.mappingItems.splice(index,1);
                        tr.remove();
                    });
                });

                const editLink=$(`<a href="###" style="margin-left: 10px"><i class="glyphicon glyphicon-pencil" style="font-size: 16px;color: #005fd3;"></i></a>`);
                td.append(editLink);
                editLink.click(function(){
                    _this.mappingDialog.show(function(){
                        valueTd.html(newItem.value);
                        labelTd.html(newItem.label);
                    },newItem,'edit');
                });
            },newItem,'add');
        });
        const mappingTable=$(`<table class="table table-bordered"><thead><tr style="background-color: #f5f5f5;height: 30px;"><td style="width: 130px;vertical-align: middle">实际值</td><td style="width: 170px;vertical-align: middle">显示值</td><td style="vertical-align: middle">操作</td></tr></thead></table>`);
        this.mappingTbody=$(`<tbody style="font-size: 12px"></tbody>`);
        mappingTable.append(this.mappingTbody);
        this.mappingGroup.append(mappingTable);
        container.append(this.mappingGroup);
    }

    _buildFields(){
        const _this=this;
        let fields=[],datasetName=_this.datasetSelect.val();
        if(datasetName===''){
            alert('请先选择当前单元格要绑定的数据集');
            return null;
        }
        for(let ds of _this.datasources){
            let datasets=ds.datasets || [];
            for(let dataset of datasets){
                if(dataset.name===datasetName){
                    fields=dataset.fields || [];
                    break;
                }
            }
            if(fields.length>0){
                break;
            }
        }
        return fields;
    }

    _undateTableData(){
        const hot=this.context.hot,cellList=this.context.cellList;
        for(let i=this.rowIndex;i<=this.row2Index;i++) {
            for (let j = this.colIndex; j <= this.col2Index; j++) {
                const cellDef = hot.context.getCell(i, j);
                if (!cellDef) {
                    continue;
                }
                const value=cellDef.value,valueType=cellDef.value.type;
                let data='';
                if(valueType==='simple'){
                    data=value.value;
                }else if(valueType==='dataset'){
                    data=value.datasetName+"."+value.aggregate+"("+value.property+")";
                }else if(valueType==='expression'){
                    data=value.value;
                }
                hot.setDataAtCell(cellDef.rowNumber-1,cellDef.columnNumber-1,data);
            }
        }
    }

    show(cellDef,rowIndex,colIndex,row2Index,col2Index){
        let cellStyle=cellDef.cellStyle;
        if(cellStyle.wrapCompute){
            this.enableWrapComput.children('input').prop('checked',true);
        }else{
            this.disableWrapComput.children('input').prop('checked',true);
        }
        if(cellStyle.format){
            this.formatEditor.val(cellStyle.format);
        }else{
            this.formatEditor.val('');
        }
        this.initialized=true;
        this.cellDef=cellDef;
        this.rowIndex=rowIndex;
        this.colIndex=colIndex;
        this.row2Index=row2Index;
        this.col2Index=col2Index;
        this.container.show();
        this.datasetSelect.empty();
        this.propertySelect.empty();
        this.datasources=this.context.reportDef.datasources;
        for(let ds of this.datasources){
            let datasets=ds.datasets || [];
            for(let dataset of datasets){
                this.datasetSelect.append(`<option>${dataset.name}</option>`);
            }
        }
        if(cellDef.fillBlankRows){
            this.enableFillRadio.trigger("click");
            this.multipleEditor.val(cellDef.multiple);
            this.multipleGroup.show();
        }else{
            this.disableFillRadio.trigger("click");
            this.multipleGroup.hide();
        }
        const expand=cellDef.expand;
        if(expand==='None'){
            this.noneExpandRadio.trigger('click');
        }else if(expand==='Down'){
            this.downExpandRadio.trigger('click');
        }else if(expand==='Right'){
            this.rightExpandRadio.trigger('click');
        }
        const value=cellDef.value;
        this.datasetSelect.val(value.datasetName);
        this.datasetSelect.trigger('change');
        this.propertySelect.val(value.property);
        this.aggregateSelect.val(value.aggregate);
        this.aggregateSelect.trigger('change');
        const order=value.order;
        if(order==='none'){
            this.noneSortRadio.trigger('click');
        }else if(order==='desc'){
            this.descSortRadio.trigger('click');
        }else if(order==='asc'){
            this.ascSortRadio.trigger('click');
        }
        this.initialized=false;
        this.conditionList.empty();
        const conditions=this.cellDef.value.conditions;
        for(let condition of conditions){
            if(!condition.id){
                condition.id=uuid.v1();
            }
            const op=condition.operation;
            let text=condition.left+' '+op+" "+condition.right;
            if(condition.join){
                text=condition.join+' '+text;
            }
            const option=$(`<option>${text}</option>`);
            option.data(condition);
            this.conditionList.append(option);
        }
        this.mappingTbody.empty();
        const datasetValue=this.cellDef.value;
        const mappingItems=datasetValue.mappingItems || [];
        const _this=this;
        for(let item of mappingItems){
            const tr=$(`<tr style="height: 30px"></tr>`);
            const valueTd=$(`<td style="vertical-align: middle">${item.value}</td>`);
            const labelTd=$(`<td style="vertical-align: middle">${item.label}</td>`);
            tr.append(valueTd);
            tr.append(labelTd);
            this.mappingTbody.append(tr);
            const td=$(`<td style="vertical-align: middle"></td>`);
            tr.append(td);
            const delLink=$(`<a href="###"><i class="glyphicon glyphicon-trash" style="font-size: 16px;color: #d30e00;"></i></a>`);
            td.append(delLink);
            delLink.click(function(){
                confirm('确定要删除？',function(){
                    const index=datasetValue.mappingItems.indexOf(item);
                    datasetValue.mappingItems.splice(index,1);
                    tr.remove();
                });
            });

            const editLink=$(`<a href="###" style="margin-left: 10px"><i class="glyphicon glyphicon-pencil" style="font-size: 16px;color: #005fd3;"></i></a>`);
            td.append(editLink);
            editLink.click(function(){
                _this.mappingDialog.show(function(){
                    valueTd.html(item.value);
                    labelTd.html(item.label);
                },item,'edit');
            });
        }
    }
    hide(){
        this.container.hide();
    }
}