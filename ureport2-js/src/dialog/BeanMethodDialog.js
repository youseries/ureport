/**
 * Created by Jacky.Gao on 2017-02-07.
 */
import MethodSelectDialog from './MethodSelectDialog.js';
import {alert} from '../MsgBox.js';
import {setDirty} from '../Utils.js';

export default class BeanMethodDialog{
    constructor(datasources,beanId){
        this.datasources=datasources;
        this.beanId=beanId;
        this.dialog=$(`<div class="modal fade" role="dialog" aria-hidden="true" style="z-index: 10000">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title">
                            SpringBean数据集配置
                        </h4>
                    </div>
                    <div class="modal-body"></div>
                    <div class="modal-footer">
                    </div>
                </div>
            </div>
        </div>`);
        const body=this.dialog.find('.modal-body'),footer=this.dialog.find(".modal-footer");
        this.initBody(body);
        this.initButton(footer);
    }
    initBody(body){
        const nameRow=$(`<div class="row" style="margin: 10px;"><div class="col-md-3" style="text-align: right;margin-top:8px">数据集名称：</div></div>`);
        const col=$(`<div class="col-md-9" style="padding: 0px;"></div>`);
        nameRow.append(col);
        this.nameEditor=$(`<input type="text" class="form-control">`);
        col.append(this.nameEditor);
        body.append(nameRow);

        const _this=this;
        const methodRow=$(`<div class="row" style="margin: 10px;"><div class="col-md-3" style="text-align: right;margin-top:8px">方法名：</div></div>`);
        const methodCol=$(`<div class="col-md-9" style="padding: 0px;"></div>`);
        const methodGroup=$(`<div class="input-group"></div>`);
        methodCol.append(methodGroup);
        this.methodEditor=$(`<input type="text" placeholder="方法必须包含三个参数：String,String,Map" class="form-control">`);
        methodGroup.append(this.methodEditor);
        const selectMethodButton=$(`<span class="input-group-btn"><button type="button" class="btn btn-default">选择方法</button></span>`);
        methodGroup.append(selectMethodButton);
        const methodSelectDialog=new MethodSelectDialog();
        selectMethodButton.click(function(){
            methodSelectDialog.show(function(method){
                _this.methodEditor.val(method);
            },_this.beanId);
        });
        methodRow.append(methodCol);
        body.append(methodRow);

        const helpRow=$(`<div class="row" style="margin: 10px;"><div class="col-md-3" style="text-align: right;margin-top:8px">返回对象：</div></div>`);
        const helpCol=$(`<div class="col-md-9" style="padding: 0px;"></div>`);
        helpRow.append(helpCol);
        this.helpEditor=$(`<input type="text" placeholder="指定该方法返回类全名,用于生成字段,如不指定需手工添加字段" class="form-control">`);
        helpCol.append(this.helpEditor);
        body.append(helpRow);

    }

    initButton(footer){
        const button=$(`<button class="btn btn-primary">确定</button>`);
        footer.append(button);
        const _this=this;
        button.click(function(){
            const name=_this.nameEditor.val(),method=_this.methodEditor.val(),clazz=_this.helpEditor.val();
            let check=false;
            if(!_this.oldName || name!==_this.oldName){
                check=true;
            }
            if(check){
                for(let datasource of _this.datasources){
                    let datasets=datasource.datasets;
                    for(let dataset of datasets){
                        if(dataset.name===name){
                            alert("数据集["+name+"]已存在，请换一个数据集名称.");
                            return;
                        }
                    }
                }
            }
            _this.onSave.call(this,name,method,clazz);
            setDirty();
            _this.dialog.modal('hide');
        });
    }

    show(onSave,dataset){
        this.onSave=onSave;
        this.dialog.modal('show');
        if(dataset){
            this.oldName=dataset.name;
            this.nameEditor.val(dataset.name);
            this.methodEditor.val(dataset.method);
            this.helpEditor.val(dataset.clazz);
        }
    }
}