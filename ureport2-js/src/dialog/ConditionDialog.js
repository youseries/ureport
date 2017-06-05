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
                            条件配置
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
        this.joinGroup=$(`<div class="form-group"><label>与上一条件关系：</label></div>`);
        this.joinSelect=$(`<select class="form-control" style="display: inline-block;width:430px;">
            <option value="and">与</option>
            <option value="or">或</option>
        </select>`);
        this.joinGroup.append(this.joinSelect);
        body.append(this.joinGroup);

        const propertyGroup=$(`<div class="form-group"><label>属性名：</label></div>`);
        this.propertySelect=$(`<select class="form-control" style="display: inline-block;width:490px;"></select>`);
        propertyGroup.append(this.propertySelect);
        body.append(propertyGroup);
        const operatorGroup=$(`<div class="form-group"><label>操作符：</label></div>`);
        this.operatorSelect=$(`<select class="form-control" style="display: inline-block;width:490px;">
            <option value=">">大于</option>
            <option value=">=">大于等于</option>
            <option value="<">小于</option>
            <option value="<=">小于等于</option>
            <option value="==">等于</option>
            <option value="!=">不等于</option>
            <option value="in">在集合中</option>
            <option value="like">相似</option>
        </select>`);
        operatorGroup.append(this.operatorSelect);
        body.append(operatorGroup);
        const valueGroup=$(`<div class="form-group"><label>值表达式：</label></div>`);
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
                        alert(`${val} 存在语法错误！`);
                    }
                }
            });
        });

        const button=$(`<button class="btn btn-default">确定</button>`);
        button.click(function(){
            const property=_this.propertySelect.val(),op=_this.operatorSelect.val(),value=_this.valueEditor.val(),join=_this.joinSelect.val();
            if(property===''){
                alert("请选择属性!");
                return;
            }
            if(op===''){
                alert("请选择操作符!");
                return;
            }
            if(value===''){
                alert("请输入表达式值!");
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