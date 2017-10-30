/**
 * Created by Jacky.Gao on 2017-10-17.
 */
import {alert} from '../MsgBox.js';

export default class SearchFormDialog{
    constructor(){
        this.dialog=$(`<div class="modal fade" role="dialog" aria-hidden="true" style="z-index: 11003">
            <div class="modal-dialog modal-lg" style="width: 1200px">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title">
                            查询表单设计器
                        </h4>
                    </div>
                    <div class="modal-body"></div>
                    <div class="modal-footer"></div>
                </div>
            </div>
        </div>`);
        const body=this.dialog.find('.modal-body'),footer=this.dialog.find(".modal-footer");
        this.initBody(body,footer);
        this.dialog.on("hide.bs.modal",function(){
            window.__current_report_def._formBuilder.buildData();
        });
        this.index=0;
    }
    initBody(body,footer){
        this.iframe=$(`<iframe frameborder="0" width="100%" height="500px"></iframe>`);
        body.append(this.iframe);
    }
    show(reportDef){
        window.__current_report_def=reportDef;
        this.dialog.modal('show');
        const url=window._server+"/searchFormDesigner?_i"+(this.index++);
        this.iframe.prop('src',url);
    }
}