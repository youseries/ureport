/**
 * Created by Jacky.Gao on 2017-02-07.
 */
import {alert,confirm} from '../MsgBox.js';
import {setDirty} from '../Utils.js';

export default class ImportDialog{
    constructor(){
        this.dialog=$(`<div class="modal fade" role="dialog" aria-hidden="true" style="z-index: 10000">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title">
                            导入Excel模版文件
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
        const url=window._server+"/import";
        const form=$(`<form enctype="multipart/form-data" action="${url}" method="post" target="_import_excel_frame"></form>`);
        body.append(form);
        form.append(`<div style="margin-bottom: 10px;line-height: 2;color: #929191;">此操作将导入Excel的第一个Sheet页内容，如果选择是的xlsx格式的Excel文件，导入后发现背景色未正常解析，可将该文件另存为xls格式，再次上传这个xls格式Excel文件即可。</div>`);
        const fileGroup=$(`<div class="form-group"><label>请选择要导入的Excel文件：</label></div>`);
        form.append(fileGroup);
        const file=$(`<input name="_excel_file" class="form-control" type="file">`);
        fileGroup.append(file);
        const buttonGroup=$(`<div class="form-group"></div>`);
        const submit=$(`<button type="submit" class="btn btn-primary">上传文件</button>`);
        buttonGroup.append(submit);
        form.append(buttonGroup);
        const iframe=$(`<iframe height="0" width="0" src="" name="_import_excel_frame"></iframe>`);
        body.append(iframe);
        iframe.on('load',function(e){
            const text=iframe.contents().find("body").text();
            if(!text || text===""){
                return;
            }
            const json=JSON.parse(text);
            const result=json.result;
            if(result){
                const url=window._server+"/designer";
                window.open(url,"_self");
            }else{
                const errorInfo=json.errorInfo;
                if(errorInfo){
                    alert("导入失败："+errorInfo);
                }else{
                    alert("导入失败！");
                }
            }
        });
    }
    show(){
        this.dialog.modal('show');
    }
}