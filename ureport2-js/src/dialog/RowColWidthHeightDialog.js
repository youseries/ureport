/**
 * Created by Jacky.Gao on 2017-02-07.
 */
import {alert} from '../MsgBox.js';
import {setDirty} from '../Utils.js';

export default class RowColWidthHeightDialog{
    constructor(){
        this.dialog=$(`<div class="modal fade" role="dialog" aria-hidden="true" style="z-index: 11001">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title row-col-wh">
                            行列宽高设置
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
        const group=$(`<div class="form-group"></div>`);
        this.label=$(`<label></label>`);
        group.append(this.label);
        this.valueEditor=$(`<input type="number" class="form-control" placeholder="请输入一个大于0的数字">`);
        group.append(this.valueEditor);
        body.append(group);

        const button=$(`<button type="button" class="btn btn-danger">确定</button>`);
        footer.append(button);
        const _this=this;
        button.click(function(){
            const value=parseInt(_this.valueEditor.val());
            if(!value){
                alert("请输入一个合法的数字!");
                return;
            }
            _this.callback.call(this,value);
            _this.dialog.modal('hide');
        });
    }
    show(callback,value,iscol){
        this.dialog.modal('show');
        this.callback=callback;
        if(iscol){
            this.label.html("设置列宽：");
            $(".row-col-wh").html("设置列宽");
        }else{
            this.label.html("设置行高：");
            $(".row-col-wh").html("设置行高");
        }
        this.valueEditor.val(value);
    }
}