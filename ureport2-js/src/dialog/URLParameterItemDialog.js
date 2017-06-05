/**
 * Created by Jacky.Gao on 2017-02-07.
 */
import {alert} from '../MsgBox.js';

export default class URLParameterItemDialog{
    constructor(){
        this.dialog=$(`<div class="modal fade" role="dialog" aria-hidden="true" style="z-index: 11003">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title">
                            添加参数
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
        const valueGroup=$(`<div class="form-group"><label>参数名：</label></div>`);
        body.append(valueGroup);
        this.nameEditor=$(`<input type="text" class="form-control" style="display: inline-block;width:500px;">`);
        valueGroup.append(this.nameEditor);
        const labelGroup=$(`<div class="form-group"><label>值表达式：</label></div>`);
        this.valueEditor=$(`<input type="text" class="form-control" style="display: inline-block;width:485px;">`);
        labelGroup.append(this.valueEditor);
        body.append(labelGroup);

        const saveButton=$(`<button type="button" class="btn btn-primary">保存</button>`);
        footer.append(saveButton);
        const _this=this;
        saveButton.click(function(){
            const name=_this.nameEditor.val(),value=_this.valueEditor.val();
            if(name==='' || value===''){
                alert("参数项请输入完整！");
                return;
            }
            _this.paramItem.name=name;
            _this.paramItem.value=value;
            _this.callback.call(this);
            _this.dialog.modal('hide');
        });
    }
    show(callback,paramItem,op){
        this.callback=callback;
        this.paramItem=paramItem;
        this.dialog.modal('show');
        if(op==='add'){
            this.dialog.find('.modal-title').html('添加参数');
        }else{
            this.dialog.find('.modal-title').html('编辑参数');
        }
        this.nameEditor.val(paramItem.name);
        this.valueEditor.val(paramItem.value);
    }
}