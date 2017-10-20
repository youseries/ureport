/**
 * Created by Jacky.Gao on 2017-02-07.
 */
import {alert} from '../MsgBox.js';
import {setDirty} from '../Utils.js';

export default class CrosstabDialog{
    constructor(){
        this.dialog=$(`<div class="modal fade" role="dialog" aria-hidden="true" style="z-index: 10001">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title">
                            ${window.i18n.dialog.crosstab.title}
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
        const group=$(`<div class="form-group"><label>${window.i18n.dialog.crosstab.crosstab}(<span style="font-size: 12px;color: #4e4e4e">${window.i18n.dialog.crosstab.tip}</span>)：</label></div>`);
        this.nameEditor=$(`<input type="text" class="form-control">`);
        group.append(this.nameEditor);
        body.append(group);
        const button=$(`<button type="button" class="btn btn-default">${window.i18n.dialog.crosstab.ok}</button>`);
        footer.append(button);
        const _this=this;
        button.click(function(){
            const value=_this.nameEditor.val();
            _this.callback.call(this,value);
            _this.dialog.modal('hide');
        });
    }
    show(callback){
        this.callback=callback;
        this.dialog.modal('show');
    }
}