/**
 * Created by Jacky.Gao on 2017-02-09.
 */
import {alert} from '../MsgBox.js';

export default class EditPropertyConditionDialog{
    constructor(conditions){
        this.conditions=conditions;
        this.dialog=$(`<div class="modal fade" role="dialog" aria-hidden="true" style="z-index: 11001">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title">
                            ${window.i18n.dialog.editPropCondition.title}
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
        this.joinGroup=$(`<div class="form-group"><label>${window.i18n.dialog.editPropCondition.relation}</label></div>`);
        this.joinSelect=$(`<select class="form-control" style="display: inline-block;width:430px;">
            <option value="and">${window.i18n.dialog.editPropCondition.and}</option>
            <option value="or">${window.i18n.dialog.editPropCondition.or}</option>
        </select>`);
        this.joinGroup.append(this.joinSelect);
        body.append(this.joinGroup);

        const leftGroup=$(`<div class="form-group"><label>${window.i18n.dialog.editPropCondition.leftValue}</label></div>`);
        this.leftTypeSelect=$(`<select class="form-control" style="display: inline-block;width: inherit">
            <option value="current">${window.i18n.dialog.editPropCondition.currentValue}</option>
            <option value="property">${window.i18n.dialog.editPropCondition.property}</option>
            <option value="expression">${window.i18n.dialog.editPropCondition.expression}</option>
        </select>`);
        leftGroup.append(this.leftTypeSelect);

        this.propertyGroup=$(`<span style="margin-left: 10px"><label>${window.i18n.dialog.editPropCondition.propName}</label></span>`);
        this.propertySelect=$(`<select class="form-control" style="display: inline-block;width:320px;"></select>`);
        this.propertyGroup.append(this.propertySelect);
        leftGroup.append(this.propertyGroup);
        body.append(leftGroup);

        this.exprGroup=$(`<span style="margin-left: 10px"><label>${window.i18n.dialog.editPropCondition.expr}</label></span>`);
        this.exprEditor=$(`<input type="text" style="display: inline-block;width:320px;" class="form-control">`);
        this.exprGroup.append(this.exprEditor);
        leftGroup.append(this.exprGroup);
        this.exprEditor.change(function(){
            const val=$(this).val();
            const url=window._server+'/designer/conditionScriptValidation';
            $.ajax({
                url,
                type:'POST',
                data:{content:val},
                success:function(errors){
                    if(errors.length>0){
                        alert(`${val} ${window.i18n.dialog.editPropCondition.syntaxError}`);
                    }
                }
            });
        });

        this.leftTypeSelect.change(function(){
            const val=$(this).val();
            if(val==='current'){
                _this.exprGroup.hide();
                _this.propertyGroup.hide();
            }else if(val==='property'){
                _this.exprGroup.hide();
                _this.propertyGroup.show();
            }else{
                _this.propertyGroup.hide();
                _this.exprGroup.show();
            }
        });

        const operatorGroup=$(`<div class="form-group"><label>${window.i18n.dialog.editPropCondition.operator}</label></div>`);
        this.operatorSelect=$(`<select class="form-control" style="display: inline-block;width:490px;">
            <option value=">">${window.i18n.dialog.editPropCondition.greater}</option>
            <option value=">=">${window.i18n.dialog.editPropCondition.greaterEquals}</option>
            <option value="<">${window.i18n.dialog.editPropCondition.less}</option>
            <option value="<=">${window.i18n.dialog.editPropCondition.lessEquals}</option>
            <option value="==">${window.i18n.dialog.editPropCondition.equals}</option>
            <option value="!=">${window.i18n.dialog.editPropCondition.notEquals}</option>
            <option value="in">${window.i18n.dialog.editPropCondition.in}</option>
            <option value="like">${window.i18n.dialog.editPropCondition.like}</option>
        </select>`);
        operatorGroup.append(this.operatorSelect);
        body.append(operatorGroup);
        const valueGroup=$(`<div class="form-group"><label>${window.i18n.dialog.editPropCondition.valueExpr}</label></div>`);
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
                        alert(`${val} ${window.i18n.dialog.editPropCondition.syntaxError}`);
                    }
                }
            });
        });

        const button=$(`<button class="btn btn-default">${window.i18n.dialog.editPropCondition.ok}</button>`);
        button.click(function(){
            let property=_this.propertySelect.val(),op=_this.operatorSelect.val(),value=_this.valueEditor.val(),join=_this.joinSelect.val(),type=_this.leftTypeSelect.val(),expr=_this.exprEditor.val();
            if (type === 'property') {
                if (property === '') {
                    alert(`${window.i18n.dialog.editPropCondition.selectProp}`);
                    return;
                }
            } else if(type==='expression') {
                if(expr===''){
                    alert(`${window.i18n.dialog.editPropCondition.leftValueExpr}`);
                    return;
                }
                property=expr;
            }else{
                property = null;
            }
            if(type==='current'){
                type="property";
            }
            if (op === '') {
                alert(`${window.i18n.dialog.editPropCondition.selectOperator}`);
                return;
            }
            if (value === '') {
                alert(`${window.i18n.dialog.editPropCondition.inputExpr}`);
                return;
            }
            if (_this.condition) {
                if (_this.condition.join) {
                    _this.callback.call(_this,type, property, op, value, join);
                } else {
                    _this.callback.call(_this,type, property, op, value);
                }
            } else if (_this.conditions.length > 0) {
                _this.callback.call(_this,type, property, op, value, join);
            } else {
                _this.callback.call(_this,type, property, op, value);
            }
            _this.dialog.modal('hide');
        });
        footer.append(button);
    }
    show(callback,fields,condition){
        this.callback=callback;
        this.condition=condition;
        this.type='current';
        if(condition){
            this.type=condition.type;
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
            if(this.type==='expression'){
                this.leftTypeSelect.val("expression");
                this.exprEditor.val(condition.left);
                this.propertyGroup.hide();
                this.exprGroup.show();
            }else{
                if(condition.left && condition.left!==''){
                    this.propertySelect.val(condition.left);
                    this.leftTypeSelect.val("property");
                    this.propertyGroup.show();
                }else{
                    this.leftTypeSelect.val("current");
                    this.propertyGroup.hide();
                }
                this.exprGroup.hide();
            }
            this.operatorSelect.val(condition.operation || condition.op);
            this.valueEditor.val(condition.right);
            this.joinSelect.val(condition.join);
        }else{
            this.leftTypeSelect.val("current");
            this.propertyGroup.hide();
            this.exprGroup.hide();
        }
        this.dialog.modal('show');
    }
}