/**
 * Created by Jacky.Gao on 2017-02-06.
 */
import {alert} from '../MsgBox.js';

export default class ParameterDialog{
    constructor(data){
        this.data=data;
        this.dialog=$(`<div class="modal fade" role="dialog" aria-hidden="true" style="z-index: 10000">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title">
                            ${window.i18n.dialog.sqlParam.title}
                        </h4>
                    </div>
                    <div class="modal-body"></div>
                    <div class="modal-footer"></div>
                </div>
            </div>
        </div>`);
        const body=this.dialog.find('.modal-body'),footer=this.dialog.find(".modal-footer");
        this.init(body,footer);
    }
    init(body,footer){
        const nameRow=$(`<div class="row" style="margin-bottom: 10px;margin-right:6px;"><div class="col-md-3" style="padding: 0 10px 0 0px;text-align:right;margin-top:5px">${window.i18n.dialog.sqlParam.name}</div></div>`);
        const nameGroup=$(`<div class="col-md-9" style="padding: 0 10px 0 0px"></div>`);
        this.nameEditor=$(`<input type="text" class="form-control">`);
        nameGroup.append(this.nameEditor);
        nameRow.append(nameGroup);
        body.append(nameRow);

        const typeRow=$(`<div class="row" style="margin-bottom: 10px;margin-right:6px;"><div class="col-md-3" style="padding: 0 10px 0 0px;text-align:right;margin-top:5px">${window.i18n.dialog.sqlParam.datatype}</div></div>`);
        const typeGroup=$(`<div class="col-md-9" style="padding: 0 10px 0 0px"></div>`);
        this.typeEditor=$(`<select class="form-control">
            <option>String</option>
            <option>Integer</option>
            <option>Float</option>
            <option>Boolean</option>
            <option>Date</option>
            <option>List</option>
        </select>`);
        typeGroup.append(this.typeEditor);
        typeRow.append(typeGroup);
        body.append(typeRow);

        const defaultValueRow=$(`<div class="row" style="margin-bottom: 10px;margin-right:6px;"><div class="col-md-3" style="padding: 0 10px 0 0px;text-align:right;margin-top:5px">${window.i18n.dialog.sqlParam.defaultValue}</div></div>`);
        const defaultValueGroup=$(`<div class="col-md-9" style="padding: 0 10px 0 0px"></div>`);
        this.defaultValueEditor=$(`<input type="text" placeholder="${window.i18n.dialog.sqlParam.tip}" class="form-control">`);
        defaultValueGroup.append(this.defaultValueEditor);
        defaultValueRow.append(defaultValueGroup);
        body.append(defaultValueRow);

        const _this=this;
        const addButton=$(`<button class="btn btn-primary">${window.i18n.dialog.sqlParam.ok}</button>`);
        footer.append(addButton);
        addButton.click(function(){
            const name=_this.nameEditor.val(),type=_this.typeEditor.val(),defaultValue=_this.defaultValueEditor.val();
            if(name===''){
                alert(`${window.i18n.dialog.sqlParam.nameTip}`);
                return;
            }
            if(type===''){
                alert(`${window.i18n.dialog.sqlParam.datatypeTip}`);
                return;
            }
            if(!_this.editData || name!==_this.editData.name){
                for(let param of _this.data){
                    if(param.name===name){
                        alert(`${window.i18n.dialog.sqlParam.param}[${name}]${window.i18n.dialog.sqlParam.exist}`);
                        return;
                    }
                }
            }
            _this.onSave.call(this,name,type,defaultValue);
            _this.dialog.modal('hide');
        });
    }
    show(onSave,data){
        this.onSave=onSave;
        this.dialog.modal('show');
        if(data){
            this.editData=data;
            this.nameEditor.val(data.name);
            this.typeEditor.val(data.type);
            this.defaultValueEditor.val(data.defaultValue);
        }
    }
}
