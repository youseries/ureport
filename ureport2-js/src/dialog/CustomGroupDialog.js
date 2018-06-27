/**
 * Created by Jacky.Gao on 2017-02-07.
 */
import uuid from 'node-uuid';
import {alert,confirm} from '../MsgBox.js';
import GroupItemDialog from './GroupItemDialog.js';
import {setDirty} from '../Utils.js';
import ConditionDialog from './ConditionDialog.js';

export default class CustomGroupDialog{
    constructor(){
        this.dialog=$(`<div class="modal fade" role="dialog" aria-hidden="true" style="z-index: 10000">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title">
                            ${window.i18n.dialog.customGroup.title}
                        </h4>
                    </div>
                    <div class="modal-body"></div>
                    <div class="modal-footer"></div>
                </div>
            </div>
        </div>`);
        const body=this.dialog.find('.modal-body'),footer=this.dialog.find(".modal-footer");
        this.initBody(body,footer);
    }
    initBody(body,footer){
        const container=$(`<div class="form-group"></div>`);
        body.append(container);
        const itemGroup=$(`<span></span>`);
        container.append(itemGroup);
        this.initCondition(container);
        const buttonGroup=$(`<div style="margin-bottom: 5px"></div>`);
        itemGroup.append(buttonGroup);
        const addItemButton=$(`<button type="button" class="btn btn-default"><i class="glyphicon glyphicon-plus-sign" style="color: #00a8c6"></i></button>`);
        buttonGroup.append(addItemButton);
        const groupItemDialog=new GroupItemDialog();
        const _this=this;
        addItemButton.click(function(){
            const groupItems=_this.cellDef.value.groupItems;
            const newItem={name:'',conditions:[]};
            groupItemDialog.show(newItem,function(){
                groupItems.push(newItem);
                const option=$(`<option>${newItem.name}</option>`);
                option.data(newItem);
                _this.itemSelect.append(option);
            },'add');
        });
        const delItemButton=$(`<button type="button" class="btn btn-default"><i class="glyphicon glyphicon-minus-sign" style="color: #d30e00"></i></button>`);
        buttonGroup.append(delItemButton);
        delItemButton.click(function(){
            const selectionOption=_this.itemSelect.find("option:selected");
            const selection=selectionOption.text();
            if(selection===''){
                alert(`${window.i18n.dialog.customGroup.deleteTip}`);
                return;
            }
            const groupItems=_this.cellDef.value.groupItems;
            let index=-1;
            let i=0;
            for(let item of groupItems){
                if(item.name===selection){
                    index=i;
                    break;
                }
                i++;
            }
            if(index===-1){
                alert(`${window.i18n.dialog.customGroup.deleteTip}`);
                return;
            }
            confirm(`${window.i18n.dialog.customGroup.deleteConfirm}[${selection}]?`,function(){
                groupItems.splice(index,1);
                selectionOption.remove();
            });
        });
        const modItemButton=$(`<button type="button" class="btn btn-default"><i class="glyphicon glyphicon-pencil" style="color: #3344d3"></i></button>`);
        buttonGroup.append(modItemButton);
        modItemButton.click(function(){
            const selectionOption=_this.itemSelect.find("option:selected");
            const selection=selectionOption.text();
            let selectItem=null;
            const groupItems=_this.cellDef.value.groupItems;
            for(let item of groupItems){
                if(item.name===selection){
                    selectItem=item;
                    break;
                }
            }
            if(!selectItem){
                alert(`${window.i18n.dialog.customGroup.modTip}`);
                return;
            }
            groupItemDialog.show(selectItem,function(){
                selectionOption.text(selectItem.name);
                selectionOption.data(selectItem);
            },'edit');
        });
        this.itemSelect=$(`<select size="15" class="form-control" style="width:200px;height: 285px;display: inline-block"></select>`);
        itemGroup.append(this.itemSelect);
        this.itemSelect.change(function(){
            const selectionOption=$(this).find("option:selected");
            const selection=selectionOption.text();
            if(selection===""){
                return;
            }
            _this.conditionList.empty();
            const conditions=selectionOption.data().conditions;
            let index=0;
            for(let condition of conditions){
                const op=condition.operation || condition.op;
                let text=condition.left+" "+op+" "+condition.right;
                const join=condition.join;
                if(index>0 && join){
                    text=join+" "+text;
                }
                const option=$(`<option>${text}</option>`);
                option.data(condition);
                _this.conditionList.append(option);
                index++;
            }
            _this.conditionGroup.show();
        });
    }

    initCondition(container){
        this.conditionGroup=$(`<span></span>`);
        container.append(this.conditionGroup);
        this._buildConditionTable(this.conditionGroup);
        this.conditionGroup.hide();
    }

    _buildConditionTable(container){
        const _this=this;
        const group=$(`<div style="margin-left: 20px;width:330px;display: inline-block;vertical-align: top"><label style="margin-right: 10px;">${window.i18n.dialog.customGroup.groupCondition}</label></div>`);
        const conditionGroup=$(`<div style="float: right"></div>`);
        group.append(conditionGroup);
        const addButton=$(`<button type="button" class="btn btn-default" title="${window.i18n.dialog.customGroup.addCondition}"><i class="glyphicon glyphicon-plus-sign"></i></button>`);
        conditionGroup.append(addButton);
        this.conditionList=$(`<select class="form-control" size="13" style="height: 250px"></select>`);
        addButton.click(function(){
            const selectionItem=_this.itemSelect.find('option:selected');
            if(selectionItem.text()===""){
                alert(`${window.i18n.dialog.customGroup.selectTip}`);
                return;
            }
            const groupItem=selectionItem.data();
            const conditions=groupItem.conditions;
            const conditionDialog=new ConditionDialog(conditions);
            conditionDialog.show(function(leftProperty,op,rightExpression,join){
                const c={left:leftProperty,op,operation:op,right:rightExpression,join,id:uuid.v1()};
                conditions.push(c);
                let text=leftProperty+" "+op+" "+rightExpression;
                if(join){
                    text=join+" "+text;
                }
                const option=$(`<option>${text}</option>`);
                option.data(c);
                _this.conditionList.append(option);
            },_this.fields);
        });
        const editButton=$(`<button type="button" class="btn btn-default" style="margin-left: 1px;" title="${window.i18n.dialog.customGroup.editTip}"><i class="glyphicon glyphicon-edit"></i></button>`);
        conditionGroup.append(editButton);
        editButton.click(function(){
            const option=_this.conditionList.find('option:selected');
            if(option.length===0){
                alert(`${window.i18n.dialog.customGroup.editConditionTip}`);
                return;
            }
            const condition=option.data();
            const selectionItem=_this.itemSelect.find('option:selected');
            if(selectionItem.text()===""){
                alert(`${window.i18n.dialog.customGroup.selectTip}`);
                return;
            }
            const groupItem=selectionItem.data();
            const conditions=groupItem.conditions;
            const conditionDialog=new ConditionDialog(conditions);
            conditionDialog.show(function(leftProperty,op,rightExpression,join){
                let targetCondition=null;
                for(let i=0;i<conditions.length;i++){
                    let c=conditions[i];
                    if(c.id===condition.id){
                        targetCondition=c;
                        break;
                    }
                }
                targetCondition.left=leftProperty;
                targetCondition.op=op;
                targetCondition.operation=op;
                targetCondition.right=rightExpression;
                targetCondition.join=join;
                let text=leftProperty+" "+op+" "+rightExpression;
                if(join){
                    text=join+" "+text;
                }
                option.data(targetCondition);
                option.html(text);
                setDirty();
            },_this.fields,condition);
        });

        const delButton=$(`<button type="button" class="btn btn-default" style="margin-left: 1px;" title="${window.i18n.dialog.customGroup.delTitle}"><i class="glyphicon glyphicon-minus-sign"></i></button>`);
        conditionGroup.append(delButton);
        delButton.click(function(){
            const option=_this.conditionList.find('option:selected');
            if(option.length===0){
                alert(`${window.i18n.dialog.customGroup.delConditionTip}`);
                return;
            }
            const condition=option.data();
            const selectionItem=_this.itemSelect.find('option:selected');
            if(selectionItem.text()===""){
                alert(`${window.i18n.dialog.customGroup.selectTip}`);
                return;
            }
            const groupItem=selectionItem.data();
            const conditions=groupItem.conditions;
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

    show(cellDef,fields){
        this.cellDef=cellDef;
        this.fields=fields;
        this.dialog.modal('show');
        this.itemSelect.empty();
        this.conditionGroup.hide();
        const groupItems=cellDef.value.groupItems;
        for(let item of groupItems){
            const option=$(`<option>${item.name}</option>`);
            option.data(item);
            this.itemSelect.append(option);
        }
    }
}