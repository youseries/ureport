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
                            ${window.i18n.dialog.rowColWidthHeight.title}
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
        this.valueEditor=$(`<input type="number" class="form-control" placeholder="${window.i18n.dialog.rowColWidthHeight.tip}">`);
        group.append(this.valueEditor);
        body.append(group);

        const button=$(`<button type="button" class="btn btn-danger">${window.i18n.dialog.rowColWidthHeight.ok}</button>`);
        footer.append(button);
        const _this=this;
        button.click(function(){
            const value=parseInt(_this.valueEditor.val());
            if(!value){
                alert(`${window.i18n.dialog.rowColWidthHeight.numValidate}`);
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
            this.label.html(`${window.i18n.dialog.rowColWidthHeight.colWidth}:`);
            $(".row-col-wh").html(`${window.i18n.dialog.rowColWidthHeight.colWidth}`);
        }else{
            this.label.html(`${window.i18n.dialog.rowColWidthHeight.rowHeight}:`);
            $(".row-col-wh").html(`${window.i18n.dialog.rowColWidthHeight.rowHeight}`);
        }
        this.valueEditor.val(value);
    }
}