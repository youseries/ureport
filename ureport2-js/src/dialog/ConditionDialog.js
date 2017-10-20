/**
 * Created by Jacky.Gao on 2017-02-09.
 */
import {alert} from '../MsgBox.js';

export default class ConditionDialog{
    constructor(conditions){
        this.conditions=conditions;
        this.dialog=$(`<div class="modal fade" role="dialog" aria-hidden="true" style="z-index: 10000">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title">
                            ${window.i18n.dialog.condition.config}
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
        const _this=this;
        this.joinGroup=$(`<div class="form-group"><label>${window.i18n.dialog.condition.relationship}</label></div>`);
        this.joinSelect=$(`<select class="form-control" style="display: inline-block;width:430px;">
            <option value="and">${window.i18n.dialog.condition.and}</option>
            <option value="or">${window.i18n.dialog.condition.or}</option>
        </select>`);
        this.joinGroup.append(this.joinSelect);
        body.append(this.joinGroup);

        const propertyGroup=$(`<div class="form-group"><label>${window.i18n.dialog.condition.propertyName}</label></div>`);
        this.propertySelect=$(`<select class="form-control" style="display: inline-block;width:490px;"></select>`);
        propertyGroup.append(this.propertySelect);
        body.append(propertyGroup);
        const operatorGroup=$(`<div class="form-group"><label>${window.i18n.dialog.condition.op}</label></div>`);
        this.operatorSelect=$(`<select class="form-control" style="display: inline-block;width:490px;">
            <option value=">">${window.i18n.dialog.condition.greatThen}</option>
            <option value=">=">${window.i18n.dialog.condition.greatEquals}</option>
            <option value="<">${window.i18n.dialog.condition.lessThen}</option>
            <option value="<=">${window.i18n.dialog.condition.lessEquals}</option>
            <option value="==">${window.i18n.dialog.condition.equals}</option>
            <option value="!=">${window.i18n.dialog.condition.notEquals}</option>
            <option value="in">${window.i18n.dialog.condition.in}</option>
            <option value="like">${window.i18n.dialog.condition.like}</option>
        </select>`);
        operatorGroup.append(this.operatorSelect);
        body.append(operatorGroup);
        const valueGroup=$(`<div class="form-group"><label>${window.i18n.dialog.condition.valueExpr}</label></div>`);
        this.valueEditor=$(`<input type="text" class="form-control" style="display: inline-block;width:477px;">`);
        valueGroup.append(this.valueEditor);
        body.append(valueGroup);
        this.valueEditor.change(function(){
            const val=$(this).val();
            const url=window._server+'/designer/conditionScriptValidation';
            $.ajax({
                url,
                type:'POST',
                data:{content:val},
                success:function(errors){
                    if(errors.length>0){
                        alert(`${val} ${window.i18n.dialog.condition.exprError}`);
                    }
                }
            });
        });

        const button=$(`<button class="btn btn-default">${window.i18n.dialog.condition.ok}</button>`);
        button.click(function(){
            const property=_this.propertySelect.val(),op=_this.operatorSelect.val(),value=_this.valueEditor.val(),join=_this.joinSelect.val();
            if(property===''){
                alert(`${window.i18n.dialog.condition.selectProperty}`);
                return;
            }
            if(op===''){
                alert(`${window.i18n.dialog.condition.selectOp}`);
                return;
            }
            if(value===''){
                alert(`${window.i18n.dialog.condition.inputExpr}`);
                return;
            }
            if(_this.condition){
                if(_this.condition.join){
                    _this.callback.call(_this,property,op,value,join);
                }else{
                    _this.callback.call(_this,property,op,value);
                }
            }else if(_this.conditions.length>0){
                _this.callback.call(_this,property,op,value,join);
            }else{
                _this.callback.call(_this,property,op,value);
            }
            _this.dialog.modal('hide');
        });
        footer.append(button);
    }
    show(callback,fields,condition){
        this.callback=callback;
        this.condition=condition;
        if(condition){
            if(condition.join){
                this.joinGroup.show();
            }else{
                this.joinGroup.hide();
            }
        }else{
            if(this.conditions.length>0){
                this.joinGroup.show();
            }else{
                this.joinGroup.hide();
            }
        }
        this.propertySelect.empty();
        for(let field of fields){
            this.propertySelect.append(`<option>${field.name}</option>`);
        }
        if(condition){
            this.propertySelect.val(condition.left);
            this.operatorSelect.val(condition.operation || condition.op);
            this.valueEditor.val(condition.right);
            this.joinSelect.val(condition.join);
        }
        this.dialog.modal('show');
    }
}