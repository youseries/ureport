/**
 * Created by Jacky.Gao on 2017-02-07.
 */
import uuid from 'node-uuid';
import {alert,confirm} from '../MsgBox.js';
import {setDirty} from '../Utils.js';
import PropertyConditionItemDialog from './PropertyConditionItemDialog.js';
import EditPropertyConditionDialog from './EditPropertyConditionDialog.js';
import URLParameterDialog from './URLParameterDialog.js';
import ConditionParameterCustomBorderDialog from './ConditionParameterCustomBorderDialog.js';
export default class PropertyConditionDialog{
    constructor(datasources){
        this.datasources=datasources;
        this.dialog=$(`<div class="modal fade" role="dialog" aria-hidden="true" style="z-index: 1100">
            <div class="modal-dialog modal-lg" style="width: 1000px;">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title">
                            ${window.i18n.dialog.propCondition.title}
                        </h4>
                    </div>
                    <div class="modal-body"></div>
                    <div class="modal-footer">
                    </div>
                </div>
            </div>
        </div>`);
        const body=this.dialog.find('.modal-body'),footer=this.dialog.find(".modal-footer");
        this.initBody(body,footer);
    }
    initBody(body,footer){
        const _this=this;
        const container=$(`<fieldset style="padding: 10px;border:solid 1px #dddddd;border-radius: 8px;width: 160px;display: inline-block;">
        <legend style="width: auto;margin-bottom: 1px;border-bottom:none;font-size: inherit;color: #4b4b4b;">${window.i18n.dialog.propCondition.config}</legend></fieldset>`);
        body.append(container);
        const itemGroup=$(`<span></span>`);
        container.append(itemGroup);
        const propertyConditionItemDialog=new PropertyConditionItemDialog();
        const addItemButton=$(`<button type="button" class="btn btn-default">
        <i class="glyphicon glyphicon-plus-sign" style="color: #49a700;" title="${window.i18n.dialog.propCondition.addItem}"></i></button>`);
        itemGroup.append(addItemButton);
        addItemButton.click(function(){
            const newItem={name:'',id:uuid.v1()};
            propertyConditionItemDialog.show(newItem,function(){
                _this.propertyConditions.push(newItem);
                const newOption=$(`<option>${newItem.name}</option>`);
                newOption.data(newItem);
                _this.itemSelect.append(newOption);
                setDirty();
            },'add');
        });
        const editItemButton=$(`<button type="button" class="btn btn-default">
        <i class="glyphicon glyphicon-edit" style="color: #005fd3;" title="${window.i18n.dialog.propCondition.editItem}"></i></button>`);
        itemGroup.append(editItemButton);
        editItemButton.click(function(){
            const option=_this.itemSelect.find('option:selected');
            if(option.length===0){
                alert(`${window.i18n.dialog.propCondition.editTip}`);
                return;
            }
            const item=option.data();
            propertyConditionItemDialog.show(item,function(){
                option.html(`${item.name}`);
                let targetItem;
                for(let target of _this.propertyConditions){
                    if(target.id===item.id){
                        targetItem=target;
                        break;
                    }
                }
                targetItem=item;
                option.data(item);
                setDirty();
            },'edit');
        });
        const delItemButton=$(`<button type="button" class="btn btn-default">
        <i class="glyphicon glyphicon-minus-sign" style="color: #d30e00;" title="${window.i18n.dialog.propCondition.delItem}"></i></button>`);
        itemGroup.append(delItemButton);
        delItemButton.click(function(){
            const option=_this.itemSelect.find('option:selected');
            if(option.length===0){
                alert(`${window.i18n.dialog.propCondition.delTip}`);
                return;
            }
            const item=option.data();
            confirm(`${window.i18n.dialog.propCondition.delConfirm}[${item.name}]?`,function(){
                let targetItem;
                for(let target of _this.propertyConditions){
                    if(target.id===item.id){
                        targetItem=target;
                        break;
                    }
                }
                const index=_this.propertyConditions.indexOf(targetItem);
                _this.propertyConditions.splice(index,1);
                option.remove();
                _this.conditionList.empty();
                _this.propGroup.hide();
                setDirty();
            });
        });
        this.itemSelect=$(`<select size="10" class="form-control" style="height: 500px;"></select>`);
        itemGroup.append(this.itemSelect);
        const conditionGroup=$(`<fieldset style="padding: 10px;border:solid 1px #dddddd;border-radius: 8px;width: 325px;display: inline-block;height:590px;vertical-align: top;margin-left: 10px">
        <legend style="width: auto;margin-bottom: 1px;border-bottom:none;font-size: inherit;color: #4b4b4b;">${window.i18n.dialog.propCondition.conditionConfig}</legend></fieldset>`);
        body.append(conditionGroup);
        this._initCondition(conditionGroup);
        this.propGroup=$(`<fieldset style="padding: 10px;border:solid 1px #dddddd;border-radius: 8px;width: 455px;display: inline-block;height:590px;vertical-align: top;margin-left: 10px">
        <legend style="width: auto;margin-bottom: 1px;border-bottom:none;font-size: inherit;color: #4b4b4b;">${window.i18n.dialog.propCondition.propConfig}</legend></fieldset>`);
        body.append(this.propGroup);
        this._initProperty(this.propGroup);
        this.propGroup.hide();
        this.itemSelect.change(function(){
            const option=_this.itemSelect.find('option:selected');
            if(option.length===0){
                return;
            }
            _this.conditionList.empty();
            _this.propGroup.show();
            const item=option.data();
            _this._refreshProperties(item);
            if(!item.conditions){
                item.conditions=[];
            }
            const conditions=item.conditions;
            let i=0;
            for(let condition of conditions){
                if(!condition.id){
                    condition.id=uuid.v1();
                }
                const op=condition.operation;
                let text=condition.left+' '+op+" "+condition.right;
                if(!condition.left || condition.left===''){
                    text=`${window.i18n.dialog.propCondition.currentValue} `+op+" "+(condition.right || condition.expr);
                }
                if(condition.join && i>0){
                    text=condition.join+' '+text;
                }
                const newOption=$(`<option>${text}</option>`);
                newOption.data(condition);
                _this.conditionList.append(newOption);
                i++;
                setDirty();
            }
        });
    }
    _initCondition(container){
        const _this=this;
        const group=$(`<div class="form-group" style="margin-bottom: 10px;"></div>`);
        const conditionGroup=$(`<span style="float: right"></span>`);
        group.append(conditionGroup);
        const addButton=$(`<button type="button" class="btn btn-default" title="${window.i18n.dialog.propCondition.addValue}"><i class="glyphicon glyphicon-plus-sign"></i></button>`);
        conditionGroup.append(addButton);
        this.conditionList=$(`<select class="form-control" size="100" style="height: 500px;padding: 3px;"></select>`);
        addButton.click(function(){
            let fields=_this._buildFields();
            const option=_this.itemSelect.find('option:selected');
            if(option.length===0){
                alert(`${window.i18n.dialog.propCondition.selectItem}`);
                return;
            }
            const item=option.data();
            let targetItem;
            for(let target of _this.propertyConditions){
                if(target.id===item.id){
                    targetItem=target;
                    break;
                }
            }
            const conditions=item.conditions;
            targetItem.conditions=conditions;
            const conditionDialog=new EditPropertyConditionDialog(conditions);
            conditionDialog.show(function(type,left,op,right,join){
                const c={type,left,operation:op,right,join,id:uuid.v1()};
                conditions.push(c);
                let text=left+" "+op+" "+right;
                if(type === 'property' && (!left || left==='')){
                    text=`${window.i18n.dialog.propCondition.currentValue} `+op+" "+right;
                }
                if(join){
                    text=join+" "+text;
                }
                const newOption=$(`<option>${text}</option>`);
                newOption.data(c);
                _this.conditionList.append(newOption);
                setDirty();
            },fields);
        });
        const editButton=$(`<button type="button" class="btn btn-default" style="margin-left: 1px;" title="${window.i18n.dialog.propCondition.editConditionItem}"><i class="glyphicon glyphicon-edit"></i></button>`);
        conditionGroup.append(editButton);
        editButton.click(function(){
            let fields=_this._buildFields();
            const conditionOption=_this.conditionList.find('option:selected');
            if(conditionOption.length===0){
                alert(`${window.i18n.dialog.propCondition.editConditionTip}`);
                return;
            }
            const condition=conditionOption.data();
            const option=_this.itemSelect.find('option:selected');
            if(option.length===0){
                alert(`${window.i18n.dialog.propCondition.selectConditionItem}`);
                return;
            }
            const conditions=option.data().conditions;
            const conditionDialog=new EditPropertyConditionDialog(conditions);
            conditionDialog.show(function(type,left,op,right,join){
                const conditionOption=_this.conditionList.find('option:selected');
                let targetCondition=conditionOption.data();
                targetCondition.type=type;
                targetCondition.left=left;
                targetCondition.operation=op;
                targetCondition.right=right;
                targetCondition.join=join;
                let text=left+" "+op+" "+right;
                if(type === 'property' && (!left || left==='')){
                    text=`${window.i18n.dialog.propCondition.currentValue} `+op+" "+right;
                }
                if(join){
                    text=join+" "+text;
                }
                conditionOption.data(targetCondition);
                conditionOption.html(text);
                let c;
                for(let target of conditions){
                    if(target.id===targetCondition.id){
                        c=target;
                        break;
                    }
                }
                const index=conditions.indexOf(c);
                conditions.splice(index,1,targetCondition);
                setDirty();
            },fields,condition);
        });

        const delButton=$(`<button type="button" class="btn btn-default" style="margin-left: 1px;" title="${window.i18n.dialog.propCondition.delCondition}"><i class="glyphicon glyphicon-minus-sign"></i></button>`);
        conditionGroup.append(delButton);
        delButton.click(function(){
            const conditionOption=_this.conditionList.find('option:selected');
            if(conditionOption.length===0){
                alert(`${window.i18n.dialog.propCondition.delConditionTip}`);
                return;
            }
            const condition=conditionOption.data();
            const option=_this.itemSelect.find('option:selected');
            if(option.length===0){
                alert(`${window.i18n.dialog.propCondition.selectDelCondition}`);
                return;
            }
            const conditions=option.data().conditions;
            let index=conditions.indexOf(condition);
            conditions.splice(index,1);
            conditionOption.remove();
            setDirty();
        });
        group.append(this.conditionList);
        container.append(group);
    }

    _buildFields(){
        let fields=[];
        if(!this.datasetName || this.datasetName===''){
            return fields;
        }
        for(let ds of this.datasources){
            let datasets=ds.datasets || [];
            for(let dataset of datasets){
                if(dataset.name===this.datasetName){
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

    _initProperty(container){
        const _this=this;
        const forceGroupContainer=$(`<div class="form-group" style="margin-bottom: 10px;"></div>`);
        container.append(forceGroupContainer);
        this.forceCheckbox=$(`<label><input type="checkbox">${window.i18n.dialog.propCondition.forecolor}</label>`);
        forceGroupContainer.append(this.forceCheckbox);
        this.forceGroup=$(`<span></span>`);
        forceGroupContainer.append(this.forceGroup);
        this.forceColorEditor=$(`<div class="input-group colorpicker-component" style="width: 170px;height: 26px;display: inline-block;margin-left: 10px;vertical-align: bottom">
            <input type="text" value="#00AABB" class="form-control" style="width: 120px;padding: 2px;height: 29px;vertical-align: top"/>
            <span class="input-group-addon" style="width: 22px"><i></i></span>
        </div>`);
        this.forceColorEditor.colorpicker({
            color: '#000',
            container: true,
            format:'rgb',
            colorSelectors: {
                'black': '#000000',
                'white': '#FFFFFF',
                'red': '#FF0000',
                'default': '#777777',
                'primary': '#337ab7',
                'success': '#5cb85c',
                'info': '#5bc0de',
                'warning': '#f0ad4e',
                'danger': '#d9534f'
            }
        });
        this.forceGroup.append(this.forceColorEditor);
        this.forceColorEditor.children('input').change(function(){
            let val=$(this).val();
            if(val.length>5){
                val=val.substring(4,val.length-1);
            }
            _this.item.cellStyle.forecolor=val;
            setDirty();
        });
        const forceScopeGroup=$(`<span style="margin-left: 5px;">${window.i18n.dialog.propCondition.scope}</span>`);
        this.forceScopeSelect=$(`<select class="form-control" style="height: 26px;padding: 3px;width: 120px;display: inline-block;vertical-align: top">
            <option value="cell">${window.i18n.dialog.propCondition.currentCell}</option>
            <option value="row">${window.i18n.dialog.propCondition.currentRow}</option>
            <option value="column">${window.i18n.dialog.propCondition.currentCol}</option>
        </select>`);
        forceScopeGroup.append(this.forceScopeSelect);
        this.forceScopeSelect.change(function(){
            let val=$(this).val();
            _this.item.cellStyle.forecolorScope=val;
            setDirty();
        });
        this.forceGroup.append(forceScopeGroup);
        this.forceCheckbox.children('input').click(function(){
            const checked=$(this).prop('checked');
            if(checked){
                _this.item.forecolorScope='cell';
                _this.forceScopeSelect.val('cell');
                _this.forceGroup.show();
            }else{
                _this.forceGroup.hide();
                _this.forceColorEditor.val('');
                _this.item.cellStyle.forecolor=null;
                _this.item.forecolorScope=null;
            }
            setDirty();
        });
        this.forceGroup.hide();

        const bgcolorGroupContainer=$(`<div class="form-group" style="margin-bottom: 10px;"></div>`);
        container.append(bgcolorGroupContainer);
        this.bgcolorCheckbox=$(`<label><input type="checkbox">${window.i18n.dialog.propCondition.bgcolor}</label>`);
        bgcolorGroupContainer.append(this.bgcolorCheckbox);
        this.bgcolorGroup=$(`<span></span>`);
        this.bgColorEditor=$(`<div class="input-group colorpicker-component" style="width: 170px;height: 26px;display: inline-block;margin-left: 10px;vertical-align: bottom">
            <input type="text" value="#00AABB" class="form-control" style="width: 120px;padding: 3px;height: 29px;vertical-align: top"/>
            <span class="input-group-addon" style="width: 22px"><i></i></span>
        </div>`);
        this.bgColorEditor.colorpicker({
            color: '#fff',
            container: true,
            format:'rgb',
            colorSelectors: {
                'black': '#000000',
                'white': '#FFFFFF',
                'red': '#FF0000',
                'default': '#777777',
                'primary': '#337ab7',
                'success': '#5cb85c',
                'info': '#5bc0de',
                'warning': '#f0ad4e',
                'danger': '#d9534f'
            }
        });
        this.bgcolorGroup.append(this.bgColorEditor);
        this.bgColorEditor.children('input').change(function(){
            let val=$(this).val();
            if(val.length>5){
                val=val.substring(4,val.length-1);
            }
            _this.item.cellStyle.bgcolor=val;
            setDirty();
        });
        const bgcolorScopeGroup=$(`<span style="margin-left: 5px;">${window.i18n.dialog.propCondition.scope}</span>`);
        this.bgcolorScopeSelect=$(`<select class="form-control" style="height: 26px;padding: 3px;width: inherit;display: inline-block;vertical-align: top">
            <option value="cell">${window.i18n.dialog.propCondition.currentCell}</option>
            <option value="row">${window.i18n.dialog.propCondition.currentRow}</option>
            <option value="column">${window.i18n.dialog.propCondition.currentCol}</option>
        </select>`);
        bgcolorScopeGroup.append(this.bgcolorScopeSelect);
        this.bgcolorScopeSelect.change(function(){
            let val=$(this).val();
            _this.item.cellStyle.bgcolorScope=val;
            setDirty();
        });
        this.bgcolorGroup.append(bgcolorScopeGroup);
        bgcolorGroupContainer.append(this.bgcolorGroup);
        this.bgcolorCheckbox.children('input').click(function(){
            const checked=$(this).prop('checked');
            if(checked){
                _this.item.bgcolorScope='cell';
                _this.bgcolorScopeSelect.val('cell');
                _this.bgcolorGroup.show();
            }else{
                _this.bgcolorGroup.hide();
                _this.bgColorEditor.val('');
                _this.item.cellStyle.bgcolor=null;
                _this.item.bgcolorScope=null;
            }
            setDirty();
        });
        this.bgcolorGroup.hide();

        const fontGroupContainer=$(`<div class="form-group" style="margin-bottom: 5px;"></div>`);
        container.append(fontGroupContainer);
        this.fontCheckbox=$(`<label><input type="checkbox">${window.i18n.dialog.propCondition.font}</label>`);
        fontGroupContainer.append(this.fontCheckbox);
        this.fontGroup=$(`<span style="margin-left: 10px"></span>`);
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
        fonts.push("");
        this.fontSelect=$(`<select class="form-control" style="height: 25px;display: inline-block;padding: 3px;width: inherit;vertical-align: top"></select>`);
        for(let font of fonts){
            this.fontSelect.append(`<option>${font}</option>`);
        }
        this.fontGroup.append(this.fontSelect);
        this.fontSelect.change(function(){
            _this.item.cellStyle.fontFamily=$(this).val();
            setDirty();
        });
        fontGroupContainer.append(this.fontGroup);
        this.fontCheckbox.children('input').click(function(){
            const checked=$(this).prop('checked');
            if(checked){
                _this.fontScopeSelect.val('cell');
                _this.item.fontFamilyScope='cell';
                _this.fontGroup.show();
            }else{
                _this.fontGroup.hide();
                _this.fontSelect.val('');
                _this.item.cellStyle.fontFamily='0';
                _this.item.fontFamilyScope=null;
            }
            setDirty();
        });
        this.fontGroup.hide();
        const fontScopeGroup=$(`<span style="margin-left: 15px;">${window.i18n.dialog.propCondition.scope}</span>`);
        this.fontScopeSelect=$(`<select class="form-control" style="height: 26px;padding: 3px;width: inherit;display: inline-block;vertical-align: top">
            <option value="cell">${window.i18n.dialog.propCondition.currentCell}</option>
            <option value="row">${window.i18n.dialog.propCondition.currentRow}</option>
            <option value="column">${window.i18n.dialog.propCondition.currentCol}</option>
        </select>`);
        fontScopeGroup.append(this.fontScopeSelect);
        this.fontScopeSelect.change(function(){
            let val=$(this).val();
            _this.item.cellStyle.fontFamilyScope=val;
            setDirty();
        });
        this.fontGroup.append(fontScopeGroup);

        const fontSizeGroupContainer=$(`<div class="form-group" style="margin-bottom: 5px;"></div>`);
        container.append(fontSizeGroupContainer);
        this.fontSizeCheckbox=$(`<label><input type="checkbox">${window.i18n.dialog.propCondition.fontSize}</label>`);
        fontSizeGroupContainer.append(this.fontSizeCheckbox);
        this.fontSizeGroup=$(`<span style="padding-left: 10px;;"></span>`);
        this.fontSizeSelect=$(`<select class="form-control" style="height: 25px;padding: 3px;width: inherit;display: inline-block;vertical-align: top"><option></option></select>`);
        for(let i=1;i<=100;i++){
            this.fontSizeSelect.append(`<option>${i}</option>`);
        }
        this.fontSizeGroup.append(this.fontSizeSelect);
        this.fontSizeSelect.change(function(){
            _this.item.cellStyle.fontSize=$(this).val();
            setDirty();
        });
        fontSizeGroupContainer.append(this.fontSizeGroup);
        this.fontSizeCheckbox.children('input').click(function(){
            const checked=$(this).prop('checked');
            if(checked){
                _this.fontSizeScopeSelect.val('cell');
                _this.item.fontSizeScope='cell';
                _this.fontSizeGroup.show();
            }else{
                _this.fontSizeGroup.hide();
                _this.fontSizeSelect.val('');
                _this.item.cellStyle.fontSize='0';
                _this.item.fontSizeScope=null;
            }
            setDirty();
        });
        this.fontSizeGroup.hide();
        const fontSizeScopeGroup=$(`<span style="margin-left: 15px;">${window.i18n.dialog.propCondition.scope}</span>`);
        this.fontSizeScopeSelect=$(`<select class="form-control" style="height: 26px;padding: 3px;width: inherit;display: inline-block;vertical-align: top">
            <option value="cell">${window.i18n.dialog.propCondition.currentCell}</option>
            <option value="row">${window.i18n.dialog.propCondition.currentRow}</option>
            <option value="column">${window.i18n.dialog.propCondition.currentCol}</option>
        </select>`);
        fontSizeScopeGroup.append(this.fontSizeScopeSelect);
        this.fontSizeScopeSelect.change(function(){
            let val=$(this).val();
            _this.item.cellStyle.fontSizeScope=val;
            setDirty();
        });
        this.fontSizeGroup.append(fontSizeScopeGroup);

        const fontBoldGroupContainer=$(`<div class="form-group" style="margin-bottom: 5px;"></div>`);
        container.append(fontBoldGroupContainer);
        this.fontBoldCheckbox=$(`<label><input type="checkbox">${window.i18n.dialog.propCondition.bold}</label>`);
        fontBoldGroupContainer.append(this.fontBoldCheckbox);
        this.fontBoldGroup=$(`<span style="padding-left: 10px"></span>`);
        this.fontBoldSelect=$(`<select class="form-control" style="height: 25px;padding: 3px;width: inherit;display: inline-block;vertical-align: top">
            <option selected></option>
            <option value="true">${window.i18n.dialog.propCondition.yes}</option>
            <option value="false">${window.i18n.dialog.propCondition.no}</option>
        </select>`);
        this.fontBoldGroup.append(this.fontBoldSelect);
        this.fontBoldSelect.change(function(){
            _this.item.cellStyle.bold=$(this).val();
            setDirty();
        });
        fontBoldGroupContainer.append(this.fontBoldGroup);
        this.fontBoldCheckbox.children('input').click(function(){
            const checked=$(this).prop('checked');
            if(checked){
                _this.fontBoldScopeSelect.val('cell');
                _this.item.boldScope='cell';
                _this.fontBoldGroup.show();
            }else{
                _this.fontBoldGroup.hide();
                _this.fontBoldSelect.val('');
                _this.item.cellStyle.bold=null;
                _this.item.boldScope=null;
            }
            setDirty();
        });
        this.fontBoldGroup.hide();
        const fontBoldScopeGroup=$(`<span style="margin-left: 15px;">${window.i18n.dialog.propCondition.scope}</span>`);
        this.fontBoldScopeSelect=$(`<select class="form-control" style="height: 26px;padding: 3px;width: inherit;display: inline-block;vertical-align: top">
            <option value="cell">${window.i18n.dialog.propCondition.currentCell}</option>
            <option value="row">${window.i18n.dialog.propCondition.currentRow}</option>
            <option value="column">${window.i18n.dialog.propCondition.currentCol}</option>
        </select>`);
        fontBoldScopeGroup.append(this.fontBoldScopeSelect);
        this.fontBoldScopeSelect.change(function(){
            let val=$(this).val();
            _this.item.cellStyle.boldScope=val;
            setDirty();
        });
        this.fontBoldGroup.append(fontBoldScopeGroup);

        const fontItalicGroupContainer=$(`<div class="form-group" style="margin-bottom: 5px;"></div>`);
        container.append(fontItalicGroupContainer);
        this.fontItalicCheckbox=$(`<label><input type="checkbox">${window.i18n.dialog.propCondition.italic}</label>`);
        fontItalicGroupContainer.append(this.fontItalicCheckbox);
        this.fontItalicGroup=$(`<span style="padding-left: 10px"></span>`);
        this.fontItalicSelect=$(`<select class="form-control" style="height: 25px;padding: 3px;width: inherit;display: inline-block;vertical-align: top">
            <option selected></option>
            <option value="true">${window.i18n.dialog.propCondition.yes}</option>
            <option value="false">${window.i18n.dialog.propCondition.no}</option>
        </select>`);
        this.fontItalicGroup.append(this.fontItalicSelect);
        this.fontItalicSelect.change(function(){
            _this.item.cellStyle.italic=$(this).val();
            setDirty();
        });
        fontItalicGroupContainer.append(this.fontItalicGroup);
        this.fontItalicCheckbox.children('input').click(function(){
            const checked=$(this).prop('checked');
            if(checked){
                _this.fontItalicScopeSelect.val('cell');
                _this.item.italicScope='cell';
                _this.fontItalicGroup.show();
            }else{
                _this.fontItalicGroup.hide();
                _this.fontItalicSelect.val('');
                _this.item.cellStyle.italic=null;
                _this.item.italicScope=null;
            }
            setDirty();
        });
        this.fontItalicGroup.hide();
        const fontItalicScopeGroup=$(`<span style="margin-left: 15px;">${window.i18n.dialog.propCondition.scope}</span>`);
        this.fontItalicScopeSelect=$(`<select class="form-control" style="height: 26px;padding: 3px;width: inherit;display: inline-block;vertical-align: top">
            <option value="cell">${window.i18n.dialog.propCondition.currentCell}</option>
            <option value="row">${window.i18n.dialog.propCondition.currentRow}</option>
            <option value="column">${window.i18n.dialog.propCondition.currentCol}</option>
        </select>`);
        fontItalicScopeGroup.append(this.fontItalicScopeSelect);
        this.fontItalicScopeSelect.change(function(){
            let val=$(this).val();
            _this.item.cellStyle.italicScope=val;
            setDirty();
        });
        this.fontItalicGroup.append(fontItalicScopeGroup);

        const fontUnderlineGroupContainer=$(`<div class="form-group" style="margin-bottom: 5px;"></div>`);
        container.append(fontUnderlineGroupContainer);
        this.fontUnderlineCheckbox=$(`<label><input type="checkbox">${window.i18n.dialog.propCondition.underline}</label>`);
        fontUnderlineGroupContainer.append(this.fontUnderlineCheckbox);
        this.fontUnderlineGroup=$(`<span style="padding-left: 10px"></span>`);
        this.fontUnderlineSelect=$(`<select class="form-control" style="height: 25px;padding: 3px;width: inherit;display: inline-block;vertical-align: top">
            <option selected></option>
            <option value="true">${window.i18n.dialog.propCondition.yes}</option>
            <option value="false">${window.i18n.dialog.propCondition.no}</option>
        </select>`);
        this.fontUnderlineGroup.append(this.fontUnderlineSelect);
        this.fontUnderlineSelect.change(function(){
            _this.item.cellStyle.underline=$(this).val();
            setDirty();
        });
        fontUnderlineGroupContainer.append(this.fontUnderlineGroup);
        this.fontUnderlineCheckbox.children('input').click(function(){
            const checked=$(this).prop('checked');
            if(checked){
                _this.fontUnderlineScopeSelect.val('cell');
                _this.item.underlineScope='cell';
                _this.fontUnderlineGroup.show();
            }else{
                _this.fontUnderlineGroup.hide();
                _this.fontUnderlineSelect.val('');
                _this.item.cellStyle.underline=null;
                _this.item.underlineScope=null;
            }
            setDirty();
        });
        this.fontUnderlineGroup.hide();
        const fontUnderlineScopeGroup=$(`<span style="margin-left: 15px;">${window.i18n.dialog.propCondition.scope}</span>`);
        this.fontUnderlineScopeSelect=$(`<select class="form-control" style="height: 26px;padding: 3px;width: inherit;display: inline-block;vertical-align: top">
            <option value="cell">${window.i18n.dialog.propCondition.currentCell}</option>
            <option value="row">${window.i18n.dialog.propCondition.currentRow}</option>
            <option value="column">${window.i18n.dialog.propCondition.currentCol}</option>
        </select>`);
        fontUnderlineScopeGroup.append(this.fontUnderlineScopeSelect);
        this.fontUnderlineScopeSelect.change(function(){
            let val=$(this).val();
            _this.item.cellStyle.underlineScope=val;
            setDirty();
        });
        this.fontUnderlineGroup.append(fontUnderlineScopeGroup);

        const alignGroupContainer=$(`<div class="form-group" style="margin-bottom: 5px;"></div>`);
        container.append(alignGroupContainer);
        this.alignCheckbox=$(`<label><input type="checkbox">${window.i18n.dialog.propCondition.align}</label>`);
        alignGroupContainer.append(this.alignCheckbox);
        this.alignGroup=$(`<span style="margin-left: 10px"></span>`);
        this.alignSelect=$(`<select class="form-control" style="height: 25px;padding: 3px;width: inherit;display: inline-block;vertical-align: top">
            <option selected></option>
            <option value="left">${window.i18n.dialog.propCondition.left}</option>
            <option value="center">${window.i18n.dialog.propCondition.center}</option>
            <option value="right">${window.i18n.dialog.propCondition.right}</option>
        </select>`);
        this.alignGroup.append(this.alignSelect);
        this.alignSelect.change(function(){
            _this.item.cellStyle.align=$(this).val();
            setDirty();
        });
        alignGroupContainer.append(this.alignGroup);
        this.alignCheckbox.children('input').click(function(){
            const checked=$(this).prop('checked');
            if(checked){
                _this.alignScopeSelect.val('cell');
                _this.item.alignScope='cell';
                _this.alignGroup.show();
            }else{
                _this.alignGroup.hide();
                _this.alignSelect.val('');
                _this.item.cellStyle.align=null;
                _this.item.alignScope=null;
            }
            setDirty();
        });
        this.alignGroup.hide();
        const alignScopeGroup=$(`<span style="margin-left: 15px;">${window.i18n.dialog.propCondition.scope}</span>`);
        this.alignScopeSelect=$(`<select class="form-control" style="height: 26px;padding: 3px;width: inherit;display: inline-block;vertical-align: top">
            <option value="cell">${window.i18n.dialog.propCondition.currentCell}</option>
            <option value="row">${window.i18n.dialog.propCondition.currentRow}</option>
            <option value="column">${window.i18n.dialog.propCondition.currentCol}</option>
        </select>`);
        alignScopeGroup.append(this.alignScopeSelect);
        this.alignScopeSelect.change(function(){
            let val=$(this).val();
            _this.item.cellStyle.alignScope=val;
            setDirty();
        });
        this.alignGroup.append(alignScopeGroup);

        const valignGroupContainer=$(`<div class="form-group" style="margin-bottom: 5px;"></div>`);
        container.append(valignGroupContainer);
        this.valignCheckbox=$(`<label><input type="checkbox">${window.i18n.dialog.propCondition.valign}</label>`);
        valignGroupContainer.append(this.valignCheckbox);
        this.valignGroup=$(`<span style="margin-left: 10px"></span>`);
        this.valignSelect=$(`<select class="form-control" style="height: 25px;padding: 3px;width: inherit;display: inline-block;vertical-align: top">
            <option selected></option>
            <option value="top">${window.i18n.dialog.propCondition.top}</option>
            <option value="middle">${window.i18n.dialog.propCondition.mid}</option>
            <option value="bottom">${window.i18n.dialog.propCondition.bottom}</option>
        </select>`);
        this.valignGroup.append(this.valignSelect);
        this.valignSelect.change(function(){
            _this.item.cellStyle.valign=$(this).val();
            setDirty();
        });
        valignGroupContainer.append(this.valignGroup);
        this.valignCheckbox.children('input').click(function(){
            const checked=$(this).prop('checked');
            if(checked){
                _this.valignScopeSelect.val('cell');
                _this.item.valignScope='cell';
                _this.valignGroup.show();
            }else{
                _this.valignGroup.hide();
                _this.valignSelect.val('');
                _this.item.cellStyle.valign=null;
                _this.item.valignScope=null;
            }
            setDirty();
        });
        this.valignGroup.hide();
        const valignScopeGroup=$(`<span style="margin-left: 15px;">${window.i18n.dialog.propCondition.scope}</span>`);
        this.valignScopeSelect=$(`<select class="form-control" style="height: 26px;padding: 3px;width: inherit;display: inline-block;vertical-align: top">
            <option value="cell">${window.i18n.dialog.propCondition.currentCell}</option>
            <option value="row">${window.i18n.dialog.propCondition.currentRow}</option>
            <option value="column">${window.i18n.dialog.propCondition.currentCol}</option>
        </select>`);
        valignScopeGroup.append(this.valignScopeSelect);
        this.valignScopeSelect.change(function(){
            let val=$(this).val();
            _this.item.cellStyle.valignScope=val;
            setDirty();
        });
        this.valignGroup.append(valignScopeGroup);

        const borderGroupContainer=$(`<div class="form-group" style="margin-bottom: 5px;"></div>`);
        container.append(borderGroupContainer);
        this.borderCheckbox=$(`<label><input type="checkbox">${window.i18n.dialog.propCondition.border}</label>`);
        borderGroupContainer.append(this.borderCheckbox);
        this.borderGroup=$(`<span style="margin-left: 10px;"></span>`);
        borderGroupContainer.append(this.borderGroup);
        const configBorderButton=$(`<button type="button" class="btn btn-default" style="padding: 1px 8px;"><i class="ureport ureport-setting"></i> ${window.i18n.dialog.propCondition.borderConfig}</button>`);
        const conditionParameterCustomBorderDialog=new ConditionParameterCustomBorderDialog();
        configBorderButton.click(function(){
            const cellStyle=_this.item.cellStyle;
            if(!cellStyle.leftBorder){
                cellStyle.leftBorder={color:'0,0,0',width:"1",style:'solid'};
            }
            if(!cellStyle.rightBorder){
                cellStyle.rightBorder={color:'0,0,0',width:"1",style:'solid'};
            }
            if(!cellStyle.topBorder){
                cellStyle.topBorder={color:'0,0,0',width:"1",style:'solid'};
            }
            if(!cellStyle.bottomBorder){
                cellStyle.bottomBorder={color:'0,0,0',width:"1",style:'solid'};
            }
            conditionParameterCustomBorderDialog.show(cellStyle);
            setDirty();
        });
        this.borderGroup.append(configBorderButton);
        this.borderCheckbox.children('input').click(function(){
            const checked=$(this).prop('checked');
            if(checked){
                _this.borderGroup.show();
            }else{
                _this.borderGroup.hide();
                _this.alignSelect.val('');
                const cellStyle=_this.item.cellStyle;
                cellStyle.leftBorder=null;
                cellStyle.rightBorder=null;
                cellStyle.topBorder=null;
                cellStyle.bottomBorder=null;
            }
            setDirty();
        });
        this.borderGroup.hide();

        const newValueGroupContainer=$(`<div class="form-group" style="margin-bottom: 5px;"></div>`);
        container.append(newValueGroupContainer);
        this.newValueCheckbox=$(`<label><input type="checkbox">${window.i18n.dialog.propCondition.newValue}</label>`);
        newValueGroupContainer.append(this.newValueCheckbox);
        this.newValueGroup=$(`<span style="margin-left: 10px;"></span>`);
        newValueGroupContainer.append(this.newValueGroup);
        this.newValueEditor=$(`<input type="text" class="form-control" style="display: inline-block;height: 25px;padding: 3px;width: 268px;">`);
        this.newValueGroup.append(this.newValueEditor);
        this.newValueEditor.change(function(){
            _this.item.newValue=$(this).val();
            setDirty();
        });
        this.newValueCheckbox.children('input').click(function(){
            const checked=$(this).prop('checked');
            if(checked){
                _this.newValueGroup.show();
            }else{
                _this.newValueGroup.hide();
                _this.newValueEditor.val('');
                _this.item.newValue=null;
            }
            setDirty();
        });
        this.newValueGroup.hide();

        const formatGroupContainer=$(`<div class="form-group" style="margin-bottom: 5px;"></div>`);
        container.append(formatGroupContainer);
        this.formatCheckbox=$(`<label><input type="checkbox">${window.i18n.dialog.propCondition.format}</label>`);
        formatGroupContainer.append(this.formatCheckbox);
        this.formatGroup=$(`<span style="margin-left: 10px;"></span>`);
        formatGroupContainer.append(this.formatGroup);
        this.formatEditor=$(`<input type="text" class="form-control" style="display: inline-block;height: 25px;padding: 3px;width: 268px;">`);
        this.formatGroup.append(this.formatEditor);
        this.formatEditor.change(function(){
            _this.item.cellStyle.format=$(this).val();
        });
        this.formatCheckbox.children('input').click(function(){
            const checked=$(this).prop('checked');
            if(checked){
                _this.formatGroup.show();
            }else{
                _this.formatGroup.hide();
                _this.formatEditor.val('');
                _this.item.cellStyle.format=null;
            }
            setDirty();
        });
        this.formatGroup.hide();
        this.formatEditor.completer({
            source: [
                "yyyy/MM/dd",
                "yyyy/MM",
                "yyyy-MM",
                "yyyy",
                "yyyy-MM-dd HH:mm:ss",
                "yyyy年MM月dd日 HH:mm:ss",
                "yyyy-MM-dd",
                "yyyy年MM月dd日",
                "HH:mm",
                "HH:mm:ss",
                "#.##",
                "#.00",
                "##.##%",
                "##.00%",
                "##,###.##",
                "￥##,###.##",
                "$##,###.##",
                "0.00E00",
                "##0.0E0"
            ],
            complete:function(){
                let value=_this.formatEditor.val();
                _this.item.cellStyle.format=value;
            },
            suggest: true,
            zIndex:1301
        });

        const rowHeightGroupContainer=$(`<div class="form-group" style="margin-bottom: 5px;"></div>`);
        container.append(rowHeightGroupContainer);
        this.rowHeightCheckbox=$(`<label><input type="checkbox">${window.i18n.dialog.propCondition.rowHeight}</label>`);
        rowHeightGroupContainer.append(this.rowHeightCheckbox);
        this.rowHeightGroup=$(`<span style="margin-left: 10px;"></span>`);
        rowHeightGroupContainer.append(this.rowHeightGroup);
        this.rowHeightEditor=$(`<input type="number" class="form-control" style="display: inline-block;height: 25px;padding: 3px;width: 88px;">`);
        this.rowHeightGroup.append(this.rowHeightEditor);
        this.rowHeightEditor.change(function(){
            _this.item.rowHeight=$(this).val();
        });
        this.rowHeightCheckbox.children('input').click(function(){
            const checked=$(this).prop('checked');
            if(checked){
                _this.rowHeightGroup.show();
                _this.item.rowHeight=0;
                _this.rowHeightEditor.val('0');
            }else{
                _this.rowHeightGroup.hide();
                _this.rowHeightEditor.val('');
                _this.item.rowHeight=null;
            }
            setDirty();
        });
        this.rowHeightGroup.hide();


        const colWidthGroupContainer=$(`<div class="form-group" style="margin-bottom: 5px;"></div>`);
        container.append(colWidthGroupContainer);
        this.colWidthCheckbox=$(`<label><input type="checkbox">${window.i18n.dialog.propCondition.colWidth}</label>`);
        colWidthGroupContainer.append(this.colWidthCheckbox);
        this.colWidthGroup=$(`<span style="margin-left: 10px;"></span>`);
        colWidthGroupContainer.append(this.colWidthGroup);
        this.colWidthEditor=$(`<input type="number" class="form-control" style="display: inline-block;height: 25px;padding: 3px;width: 88px;">`);
        this.colWidthGroup.append(this.colWidthEditor);
        this.colWidthEditor.change(function(){
            _this.item.colWidth=$(this).val();
        });
        this.colWidthCheckbox.children('input').click(function(){
            const checked=$(this).prop('checked');
            if(checked){
                _this.colWidthGroup.show();
                _this.item.colWidth=0;
                _this.colWidthEditor.val('0');
            }else{
                _this.colWidthGroup.hide();
                _this.colWidthEditor.val('');
                _this.item.colWidth=null;
            }
            setDirty();
        });
        this.colWidthGroup.hide();

        const pagingBreakContainer=$(`<div class="form-group" style="margin-bottom: 5px;"></div>`);
        container.append(pagingBreakContainer);
        this.pagingBreakCheckbox=$(`<label><input type="checkbox">${window.i18n.dialog.propCondition.paging}</label>`);
        pagingBreakContainer.append(this.pagingBreakCheckbox);
        this.pagingBreakGroup=$(`<span style="margin-left: 10px;"></span>`);
        pagingBreakContainer.append(this.pagingBreakGroup);
        this.pagingBreakSelect=$(`<select class="form-control" style="display: inline-block;height: 25px;padding: 1px;width: 90px;vertical-align: top">
            <option value="before">${window.i18n.dialog.propCondition.rowBefore}</option>
            <option value="after" selected>${window.i18n.dialog.propCondition.rowAfter}</option>
        </select>`);
        this.pagingBreakGroup.append(this.pagingBreakSelect);
        this.pagingBreakSelect.change(function(){
            _this.item.paging.position=$(this).val();
        });
        this.pagingLineEditor=$(`<input type="number" class="form-control" value="0" style="display: inline-block;height: 25px;padding: 3px;width: 60px;vertical-align: top;margin-left: 5px">`);
        this.pagingBreakGroup.append(this.pagingLineEditor);
        this.pagingLineEditor.hide();
        this.pagingBreakGroup.append(`<span style='margin-left: 5px'>${window.i18n.dialog.propCondition.paging}</span>`);
        this.pagingLineEditor.change(function(){
            _this.item.paging.line=$(this).val();
        });
        this.pagingBreakCheckbox.children('input').click(function(){
            const checked=$(this).prop('checked');
            if(checked){
                _this.pagingBreakGroup.show();
                _this.item.paging={position:"after",line:0};
                _this.pagingLineEditor.val('0');
            }else{
                _this.pagingBreakGroup.hide();
                _this.item.paging=null;
                _this.pagingLineEditor.val('0');
            }
            setDirty();
        });
        this.pagingBreakGroup.hide();

        const linkGroupContainer=$(`<div class="form-group" style="margin-bottom: 5px;"></div>`);
        container.append(linkGroupContainer);
        this.linkCheckbox=$(`<label><input type="checkbox">${window.i18n.dialog.propCondition.link}</label>`);
        linkGroupContainer.append(this.linkCheckbox);
        this.linkGroup=$(`<span style="margin-left: 10px"></span>`);
        this.linkEditor=$(`<span>URL：<input type="text" class="form-control" style="height: 25px;padding: 3px;display: inline-block;width:270px;"></span>`);
        this.linkGroup.append(this.linkEditor);
        this.linkEditor.children('input').change(function(){
            _this.item.linkUrl=$(this).val();
            setDirty();
        });
        this.linkTargetSelect=$(`<span style="margin-left: 60px;">${window.i18n.dialog.propCondition.target}<select class="form-control" style="height: 25px;display: inline-block;padding: 3px;width: 136px">
            <option></option>
            <option value="_blank">${window.i18n.dialog.propCondition.newWindow}</option>
            <option value="_self">${window.i18n.dialog.propCondition.currentWindow}</option>
            <option value="_parent">${window.i18n.dialog.propCondition.parentWindow}</option>
            <option value="_top">${window.i18n.dialog.propCondition.topWindow}</option>
        </select></span>`);
        this.linkGroup.append(this.linkTargetSelect);
        this.linkTargetSelect.children('select').change(function(){
            _this.item.linkTargetWindow=$(this).val();
            setDirty();
        });
        const linkParameterButton=$(`<button type="button" class="btn btn-default" style="margin-left: 5px;padding: 1px 8px;">${window.i18n.dialog.propCondition.urlParameter}</button>`);
        this.linkGroup.append(linkParameterButton);
        const urlParameterDialog=new URLParameterDialog();
        linkParameterButton.click(function(){
            if(!_this.item.linkUrl){
                alert(`${window.i18n.dialog.propCondition.linkUrl}`);
                return;
            }
            if(!_this.item.linkParameters){
                _this.item.linkParameters=[];
            }
            urlParameterDialog.show(_this.item.linkParameters);
            setDirty();
        });

        linkGroupContainer.append(this.linkGroup);
        this.linkCheckbox.children('input').click(function(){
            const checked=$(this).prop('checked');
            if(checked){
                _this.linkGroup.show();
            }else{
                _this.linkGroup.hide();
                _this.linkEditor.children('input').val('');
                _this.linkTargetSelect.children('select').val('');
                _this.item.linkUrl=null;
                _this.item.linkTargetWindow=null;
                _this.item.linkParameters=null;
            }
            setDirty();
        });
        this.linkGroup.hide();
    }

    _refreshProperties(item){
        if(!item.cellStyle){
            item.cellStyle={fontSize:'0',fontFamily:'0'};
        }
        let targetItem;
        for(let target of this.propertyConditions){
            if(target.id===item.id){
                targetItem=target;
                break;
            }
        }
        const index=this.propertyConditions.indexOf(targetItem);
        this.propertyConditions.splice(index,1,item);
        this.item=item;
        this.propGroup.show();
        this.rowHeightEditor.val('');
        const rowHeight=item.rowHeight;
        if(rowHeight!==null && rowHeight!==undefined && rowHeight!==-1){
            this.rowHeightGroup.show();
            this.rowHeightEditor.val(rowHeight);
            this.rowHeightCheckbox.children('input').prop('checked',true);
        }else{
            this.rowHeightGroup.hide();
            this.rowHeightCheckbox.children('input').prop('checked',false);
        }
        this.colWidthEditor.val('');
        const colWidth=item.colWidth;
        if(colWidth!==null && colWidth!==undefined && colWidth!==-1){
            this.colWidthGroup.show();
            this.colWidthEditor.val(colWidth);
            this.colWidthCheckbox.children('input').prop('checked',true);
        }else{
            this.colWidthGroup.hide();
            this.colWidthCheckbox.children('input').prop('checked',false);
        }
        this.newValueEditor.val('');
        const newValue=item.newValue;
        if(newValue && newValue!==''){
            this.newValueEditor.val(newValue);
            this.newValueGroup.show();
            this.newValueCheckbox.children('input').prop('checked',true);
        }else{
            this.newValueGroup.hide();
            this.newValueCheckbox.children('input').prop('checked',false);
        }
        this.linkEditor.children('input').val('');
        this.linkTargetSelect.children('select').val('');
        const linkUrl=item.linkUrl;
        if(linkUrl){
            this.linkEditor.children('input').val(linkUrl);
            this.linkTargetSelect.children('select').val(item.linkTargetWindow);
            this.linkGroup.show();
            this.linkCheckbox.children('input').prop('checked',true);
        }else{
            this.linkGroup.hide();
            this.linkCheckbox.children('input').prop('checked',false);
        }
        this.pagingBreakSelect.val('');
        this.pagingLineEditor.val('');
        const paging=item.paging;
        if(paging){
            this.pagingBreakGroup.show();
            this.pagingBreakCheckbox.children('input').prop('checked',true);
            this.pagingBreakSelect.val(paging.position);
            this.pagingLineEditor.val(paging.line);
        }else{
            this.pagingBreakCheckbox.children('input').prop('checked',false);
            this.pagingBreakGroup.hide();
        }

        this.forceColorEditor.children('input').val('');
        this.forceScopeSelect.val('');
        const cellStyle=item.cellStyle || {};
        const forecolor=cellStyle.forecolor;
        if(forecolor && forecolor!==''){
            this.forceGroup.show();
            this.forceColorEditor.children('input').val("rgb("+forecolor+")");
            this.forceColorEditor.children('input').trigger("change");
            this.forceCheckbox.children('input').prop('checked',true);
            this.forceScopeSelect.val(cellStyle.forecolorScope);
        }else{
            this.forceGroup.hide();
            this.forceCheckbox.children('input').prop('checked',false);
        }
        this.bgColorEditor.children('input').val('');
        this.bgcolorScopeSelect.val('');
        const bgcolor=cellStyle.bgcolor;
        if(bgcolor && bgcolor!==''){
            this.bgcolorGroup.show();
            this.bgColorEditor.children('input').val("rgb("+bgcolor+")");
            this.bgColorEditor.children('input').trigger("change");
            this.bgcolorCheckbox.children('input').prop('checked',true);
            this.bgcolorScopeSelect.val(cellStyle.bgcolorScope);
        }else{
            this.bgcolorGroup.hide();
            this.bgcolorCheckbox.children('input').prop('checked',false);
        }
        this.fontSelect.val('');
        this.fontScopeSelect.val('');
        const fontFamily=cellStyle.fontFamily;
        if(fontFamily && fontFamily!=='0'){
            this.fontSelect.val(fontFamily);
            this.fontGroup.show();
            this.fontCheckbox.children('input').prop('checked',true);
            this.fontScopeSelect.val(cellStyle.fontFamilyScope);
        }else{
            this.fontGroup.hide();
            this.fontCheckbox.children('input').prop('checked',false);
        }
        this.fontSizeSelect.val('');
        this.fontSizeScopeSelect.val('');
        const fontSize=cellStyle.fontSize;
        if(fontSize && fontSize!=='0'){
            this.fontSizeSelect.val(fontSize);
            this.fontSizeCheckbox.children('input').prop('checked',true);
            this.fontSizeGroup.show();
            this.fontSizeScopeSelect.val(cellStyle.fontSizeScope);
        }else{
            this.fontSizeCheckbox.children('input').prop('checked',false);
            this.fontSizeGroup.hide();
        }
        this.fontBoldSelect.val("false");
        this.fontBoldScopeSelect.val('');
        const bold=cellStyle.bold;
        if(bold!==null && bold!==undefined && bold!==''){
            if(bold==="true" || bold===true){
                this.fontBoldSelect.val("true");
            }else{
                this.fontBoldSelect.val("false");
            }
            this.fontBoldCheckbox.children('input').prop('checked',true);
            this.fontBoldGroup.show();
            this.fontBoldScopeSelect.val(cellStyle.boldScope);
        }else{
            this.fontBoldCheckbox.children('input').prop('checked',false);
            this.fontBoldGroup.hide();
        }
        this.fontItalicSelect.val("false");
        this.fontItalicScopeSelect.val('');
        const italic=cellStyle.italic;
        if(italic!==null && italic!==undefined && italic!==''){
            if(italic===true || italic==="true"){
                this.fontItalicSelect.val("true");
            }else{
                this.fontItalicSelect.val("false");
            }
            this.fontItalicCheckbox.children('input').prop('checked',true);
            this.fontItalicGroup.show();
            this.fontItalicScopeSelect.val(cellStyle.italicScope);
        }else{
            this.fontItalicCheckbox.children('input').prop('checked',false);
            this.fontItalicGroup.hide();
        }
        this.fontUnderlineSelect.val("false");
        this.fontUnderlineScopeSelect.val('');
        const underline=cellStyle.underline;
        if(underline!==null && underline!==undefined && underline!==''){
            if(underline===true || underline==="true"){
                this.fontUnderlineSelect.val("true");
            }else{
                this.fontUnderlineSelect.val("false");
            }
            this.fontUnderlineCheckbox.children('input').prop('checked',true);
            this.fontUnderlineGroup.show();
            this.fontUnderlineScopeSelect.val(cellStyle.underlineScope);
        }else{
            this.fontUnderlineCheckbox.children('input').prop('checked',false);
            this.fontUnderlineGroup.hide();
        }
        this.alignSelect.val('');
        this.alignScopeSelect.val('');
        const align=cellStyle.align;
        if(align && align!==''){
            this.alignSelect.val(align);
            this.alignCheckbox.children('input').prop('checked',true);
            this.alignGroup.show();
            this.alignScopeSelect.val(cellStyle.alignScope);
        }else{
            this.alignCheckbox.children('input').prop('checked',false);
            this.alignGroup.hide();
        }
        this.valignSelect.val('');
        this.valignScopeSelect.val('');
        const valign=cellStyle.valign;
        if(valign && valign!==''){
            this.valignSelect.val(valign);
            this.valignCheckbox.children('input').prop('checked',true);
            this.valignGroup.show();
            this.valignScopeSelect.val(cellStyle.valignScope);
        }else{
            this.valignCheckbox.children('input').prop('checked',false);
            this.valignGroup.hide();
        }
        if(cellStyle.leftBorder || cellStyle.rightBorder || cellStyle.topBorder || cellStyle.bottomBorder){
            this.borderCheckbox.children('input').prop('checked',true);
            this.borderGroup.show();
        }else{
            this.borderCheckbox.children('input').prop('checked',false);
            this.borderGroup.hide();
        }
        this.formatEditor.val('');
        const format=cellStyle.format;
        if(format && format!==''){
            this.formatEditor.val(format);
            this.formatCheckbox.children('input').prop('checked',true);
            this.formatGroup.show();
        }else{
            this.formatCheckbox.children('input').prop('checked',false);
            this.formatGroup.hide();
        }
    }

    show(datasources,datasetName,propertyConditions){
        this.datasources=datasources;
        this.datasetName=datasetName;
        this.propertyConditions=propertyConditions;
        this.dialog.modal('show');
        this.itemSelect.empty();
        for(let item of propertyConditions){
            if(!item.id){
                item.id=uuid.v1();
            }
            const newOption=$(`<option>${item.name}</option>`);
            newOption.data(item);
            this.itemSelect.append(newOption);
        }
        this.conditionList.empty();
        this.propGroup.hide();
    }
}