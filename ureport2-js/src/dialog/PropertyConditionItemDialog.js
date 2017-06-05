/**
 * Created by Jacky.Gao on 2017-02-07.
 */
import {alert} from '../MsgBox.js';
import {setDirty} from '../Utils.js';

export default class PropertyConditionItemDialog{
    constructor(){
        this.dialog=$(`<div class="modal fade" role="dialog" aria-hidden="true" style="z-index: 11001">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title">
                            新增条件项
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
        const group=$(`<div class="form-group"><label>条件项名称：</label></div>`);
        this.nameEditor=$(`<input type="text" class="form-control">`);
        group.append(this.nameEditor);
        body.append(group);
        const button=$(`<button type="button" class="btn btn-default">确定</button>`);
        footer.append(button);
        const _this=this;
        button.click(function(){
            const value=_this.nameEditor.val();
            if(value===''){
                alert("请输入条件项名称！");
                return;
            }
            _this.conditionItem.name=value;
            _this.callback.call(this);
            _this.dialog.modal('hide');
        });
    }
    show(conditionItem,callback,op){
        this.conditionItem=conditionItem;
        this.callback=callback;
        this.dialog.modal('show');
        this.nameEditor.val(conditionItem.name);
        const title=this.dialog.find(".modal-title");
        if(op==='add'){
            title.html("添加条件项");
        }else if(op==='edit'){
            title.html("修改条件项");
        }
    }
}