/**
 * Created by Jacky.Gao on 2017-02-12.
 */
import {formatDate,resetDirty} from '../Utils.js';
import {alert,confirm} from '../MsgBox.js';

export default class OpenDialog{
    constructor(context){
        this.context=context;
        this.reportFilesData={};
        this.dialog=$(`<div class="modal fade" role="dialog" aria-hidden="true" style="z-index: 10000">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title">
                            ${window.i18n.dialog.open.title}
                        </h4>
                    </div>
                    <div class="modal-body"></div>
                    <div class="modal-footer"></div>
                </div>
            </div>
        </div>`);
        const body=this.dialog.find('.modal-body'),footer=this.dialog.find(".modal-footer");
        this.initBody(body);
    }
    initBody(body){
        const providerGroup=$(`<div class="form-group"><label>${window.i18n.dialog.open.source}</label></div>`);
        this.providerSelect=$(`<select class="form-control" style="display: inline-block;width:450px;">`);
        providerGroup.append(this.providerSelect);
        body.append(providerGroup);
        const tableContainer=$(`<div style="height:350px;overflow: auto"></div>`);
        body.append(tableContainer);
        const fileTable=$(`<table class="table table-bordered"><thead><tr style="background: #f4f4f4;height: 30px;">
            <td style="vertical-align: middle">${window.i18n.dialog.open.fileName}</td>
            <td style="width: 150px;vertical-align: middle">${window.i18n.dialog.open.modDate}</td>
            <td style="width:50px;vertical-align: middle">${window.i18n.dialog.open.open}</td>
            <td style="width:50px;vertical-align: middle">${window.i18n.dialog.open.del}</td></tr></thead></table>`);
        this.fileTableBody=$(`<tbody></tbody>`);
        fileTable.append(this.fileTableBody);
        tableContainer.append(fileTable);
        const _this=this;
        this.providerSelect.change(function(){
            let value=$(this).val();
            if(!value || value===''){
                return;
            }
            _this.fileTableBody.empty();
            let reportFiles=_this.reportFilesData[value];
            if(!reportFiles){
                return;
            }
            for(let file of reportFiles){
                let tr=$(`<tr style="height: 35px;"></tr>`);
                _this.fileTableBody.append(tr);
                tr.append(`<td style="vertical-align: middle;">${file.name}</td>`);
                tr.append(`<td style="vertical-align: middle;">${formatDate(file.updateDate)}</td>`);

                let openCol=$(`<td style="vertical-align: middle;"></td>`);
                tr.append(openCol);
                let openIcon=$(`<a href="###"><i class="glyphicon glyphicon-folder-open" style="color: #008ed3;font-size: 14pt"></i></a>`);
                openCol.append(openIcon);
                openIcon.click(function(){
                    confirm(`${window.i18n.dialog.open.openConfirm}[${file.name}]？`,function(){
                        let fullFile=value+encodeURI(encodeURI(file.name));
                        let path=window._server+"/designer?_u="+fullFile;
                        window.open(path,"_self");
                    });
                });

                let deleteCol=$(`<td style="vertical-align: middle;"></td>`);
                tr.append(deleteCol);
                let deleteIcon=$(`<a href="###"><i class="glyphicon glyphicon-trash" style="color: red;font-size: 14pt"></i></a>`);
                deleteCol.append(deleteIcon);

                deleteIcon.click(function(){
                    confirm(`${window.i18n.dialog.open.delConfirm}`+file.name,function(){
                        let fullFile=value+file.name;
                        $.ajax({
                            type:'POST',
                            data:{file:fullFile},
                            url:window._server+"/designer/deleteReportFile",
                            success:function(){
                                tr.remove();
                                let index=reportFiles.indexOf(file);
                                reportFiles.splice(index,1);
                            },
                            error:function(response){
                                if(response && response.responseText){
                                    alert("服务端错误："+response.responseText+"");
                                }else{
                                    alert(`${window.i18n.dialog.open.delFail}`);
                                }
                            }
                        });
                    });
                });
            }
            _this.currentProviderPrefix=value;
            _this.currentReportFiles=reportFiles;
        });
    }
    show(){
        this.providerSelect.empty();
        this.fileTableBody.empty();
        this.reportFilesData={};
        const _this=this;
        $.ajax({
            url:window._server+'/designer/loadReportProviders',
            success:function(providers){
                for(let provider of providers){
                    let {reportFiles,name,prefix}=provider;
                    _this.reportFilesData[prefix]=reportFiles;
                    _this.providerSelect.append(`<option value="${prefix}">${name}</option>`);
                }
                _this.providerSelect.trigger('change');
            },
            error:function(response){
                if(response && response.responseText){
                    alert("服务端错误："+response.responseText+"");
                }else{
                    alert(`${window.i18n.dialog.open.loadFail}`);
                }
            }
        });
        this.dialog.modal('show');
    }
}