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
                            ${window.i18n.dialog.bean.beanDatasetConfig}
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
        const nameRow=$(`<div class="row" style="margin: 10px;"><div class="col-md-3" style="text-align: right;margin-top:8px">${window.i18n.dialog.bean.datasetName}</div></div>`);
        const col=$(`<div class="col-md-9" style="padding: 0px;"></div>`);
        nameRow.append(col);
        this.nameEditor=$(`<input type="text" class="form-control">`);
        col.append(this.nameEditor);
        body.append(nameRow);

        const _this=this;
        const methodRow=$(`<div class="row" style="margin: 10px;"><div class="col-md-3" style="text-align: right;margin-top:8px">${window.i18n.dialog.bean.methodName}</div></div>`);
        const methodCol=$(`<div class="col-md-9" style="padding: 0px;"></div>`);
        const methodGroup=$(`<div class="input-group"></div>`);
        methodCol.append(methodGroup);
        this.methodEditor=$(`<input type="text" placeholder="${window.i18n.dialog.bean.methodParameters}String,String,Map" class="form-control">`);
        methodGroup.append(this.methodEditor);
        const selectMethodButton=$(`<span class="input-group-btn"><button type="button" class="btn btn-default">${window.i18n.dialog.bean.selectMethod}</button></span>`);
        methodGroup.append(selectMethodButton);
        const methodSelectDialog=new MethodSelectDialog();
        selectMethodButton.click(function(){
            methodSelectDialog.show(function(method){
                _this.methodEditor.val(method);
            },_this.beanId);
        });
        methodRow.append(methodCol);
        body.append(methodRow);

        const helpRow=$(`<div class="row" style="margin: 10px;"><div class="col-md-3" style="text-align: right;margin-top:8px">${window.i18n.dialog.bean.returnObject}</div></div>`);
        const helpCol=$(`<div class="col-md-9" style="padding: 0px;"></div>`);
        helpRow.append(helpCol);
        this.helpEditor=$(`<input type="text" placeholder="${window.i18n.dialog.bean.className}" class="form-control">`);
        helpCol.append(this.helpEditor);
        body.append(helpRow);
    }

    initButton(footer){
        const button=$(`<button class="btn btn-primary">${window.i18n.dialog.bean.ok}</button>`);
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
                            alert(`${window.i18n.dialog.bean.dataset}["+name+"]${window.i18n.dialog.bean.datasetExist}`);
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