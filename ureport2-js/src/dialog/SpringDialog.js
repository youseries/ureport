/**
 * Created by Jacky.Gao on 2017-02-07.
 */
import {alert} from '../MsgBox.js';
import {setDirty} from '../Utils.js';

export default class SpringDialog{
    constructor(datasources){
        this.datasources=datasources;
        this.dialog=$(`<div class="modal fade" role="dialog" aria-hidden="true" style="z-index: 10000">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title">
                            ${window.i18n.dialog.springDS.title}
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
        const dsRow=$(`<div class="row" style="margin-bottom: 10px;margin-right:6px;"><div class="col-md-3" style="padding: 0 10px 0 0px;text-align:right;margin-top:5px">${window.i18n.dialog.springDS.name}</div></div>`);
        const dsNameGroup=$(`<div class="col-md-9" style="padding: 0 10px 0 0px"></div>`);
        this.dsNameEditor=$(`<input type="text" class="form-control">`);
        dsNameGroup.append(this.dsNameEditor);
        dsRow.append(dsNameGroup);
        body.append(dsRow);

        const usernameRow=$(`<div class="row" style="margin-bottom: 10px;margin-right:6px;"><div class="col-md-3" style="padding: 0 10px 0 0px;text-align:right;margin-top:5px">${window.i18n.dialog.springDS.bean}</div></div>`);
        const usernameGroup=$(`<div class="col-md-9" style="padding: 0 10px 0 0px"></div>`);
        this.beanIdEditor=$(`<input type="text" class="form-control">`);
        usernameGroup.append(this.beanIdEditor);
        usernameRow.append(usernameGroup);
        body.append(usernameRow);

        const _this=this;
        const saveButton=$(`<button type="button" class="btn btn-primary">${window.i18n.dialog.springDS.save}</button>`);
        footer.append(saveButton);
        saveButton.click(function(){
            const dsName=_this.dsNameEditor.val(),beanId=_this.beanIdEditor.val();
            if(dsName===''){
                alert(`${window.i18n.dialog.springDS.nameTip}`);
                return;
            }
            if(beanId===''){
                alert(`${window.i18n.dialog.springDS.beanTip}`);
                return;
            }
            let check=false;
            if(!_this.oldName || dsName!==_this.oldName){
                check=true;
            }
            if(check){
                for(let source of _this.datasources){
                    if(source.name===dsName){
                        alert(`${window.i18n.dialog.springDS.ds}["+dsName+"]${window.i18n.dialog.springDS.exist}`);
                        return;
                    }
                }
            }
            _this.onSave.call(this,dsName,beanId);
            _this.dialog.modal('hide');
            setDirty();
        });
    }
    show(onSave,ds){
        this.onSave=onSave;
        if(ds){
            this.oldName=ds.name;
            this.dsNameEditor.val(ds.name);
            this.beanIdEditor.val(ds.beanId);
        }
        this.dialog.modal('show');
    }
}