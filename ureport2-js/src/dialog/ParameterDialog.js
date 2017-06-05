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
                            添加参数
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
        const nameRow=$(`<div class="row" style="margin-bottom: 10px;margin-right:6px;"><div class="col-md-3" style="padding: 0 10px 0 0px;text-align:right;margin-top:5px">参数名称：</div></div>`);
        const nameGroup=$(`<div class="col-md-9" style="padding: 0 10px 0 0px"></div>`);
        const nameEditor=$(`<input type="text" class="form-control">`);
        nameGroup.append(nameEditor);
        nameRow.append(nameGroup);
        body.append(nameRow);

        const typeRow=$(`<div class="row" style="margin-bottom: 10px;margin-right:6px;"><div class="col-md-3" style="padding: 0 10px 0 0px;text-align:right;margin-top:5px">数据类型：</div></div>`);
        const typeGroup=$(`<div class="col-md-9" style="padding: 0 10px 0 0px"></div>`);
        const typeEditor=$(`<select class="form-control">
            <option>String</option>
            <option>Integer</option>
            <option>Float</option>
            <option>Boolean</option>
            <option>Date</option>
            <option>List</option>
        </select>`);
        typeGroup.append(typeEditor);
        typeRow.append(typeGroup);
        body.append(typeRow);

        const defaultValueRow=$(`<div class="row" style="margin-bottom: 10px;margin-right:6px;"><div class="col-md-3" style="padding: 0 10px 0 0px;text-align:right;margin-top:5px">默认值：</div></div>`);
        const defaultValueGroup=$(`<div class="col-md-9" style="padding: 0 10px 0 0px"></div>`);
        const defaultValueEditor=$(`<input type="text" placeholder="Date类型默认值为一个格式为yyyy-MM-dd HH:mm:ss的日期值" class="form-control">`);
        defaultValueGroup.append(defaultValueEditor);
        defaultValueRow.append(defaultValueGroup);
        body.append(defaultValueRow);

        const _this=this;
        const addButton=$(`<button class="btn btn-primary">确定</button>`);
        footer.append(addButton);
        addButton.click(function(){
            const name=nameEditor.val(),type=typeEditor.val(),defaultValue=defaultValueEditor.val();
            if(name===''){
                alert("请输入参数名！");
                return;
            }
            if(type===''){
                alert("请选择数据类型！");
                return;
            }
            for(let param of _this.data){
                if(param.name===name){
                    alert("参数["+name+"]已存在!");
                    return;
                }
            }
            _this.onSave.call(this,name,type,defaultValue);
            _this.dialog.modal('hide');
        });
    }
    show(onSave){
        this.onSave=onSave;
        this.dialog.modal('show');
    }
}
