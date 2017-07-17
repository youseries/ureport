/**
 * Created by Jacky.Gao on 2017-02-12.
 */
import {formatDate,resetDirty} from '../Utils.js';
import {alert,confirm} from '../MsgBox.js';

export default class SaveDialog{
    constructor(){
        this.reportFilesData={};
        this.dialog=$(`<div class="modal fade" role="dialog" aria-hidden="true" style="z-index: 10000">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title">
                            保存报表文件
                        </h4>
                    </div>
                    <div class="modal-body"></div>
                    <div class="modal-footer"></div>
                </div>
            </div>
        </div>`);
        const body=this.dialog.find('.modal-body'),footer=this.dialog.find(".modal-footer");
        this.initBody(body);
        this.initFooter(footer);
    }
    initBody(body){
        const fileGroup=$(`<div class="form-group"><label>文件名：</label></div>`);
        this.fileEditor=$(`<input type="text" class="form-control" style="display: inline-block;width: 480px">`);
        fileGroup.append(this.fileEditor);
        body.append(fileGroup);
        const providerGroup=$(`<div class="form-group"><label>存储目的地：</label></div>`);
        this.providerSelect=$(`<select class="form-control" style="display: inline-block;width:450px;">`);
        providerGroup.append(this.providerSelect);
        body.append(providerGroup);
        const tableContainer=$(`<div style="height:350px;overflow: auto"></div>`);
        body.append(tableContainer);
        const fileTable=$(`<table class="table table-bordered"><thead><tr style="background: #f4f4f4;height: 30px;"><td style="vertical-align: middle">文件名</td><td style="width: 150px;vertical-align: middle">修改日期</td><td style="width:50px;vertical-align: middle">删除</td></tr></thead></table>`);
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
                tr.append(`<td style="vertical-align: middle">${file.name}</td>`);
                tr.append(`<td style="vertical-align: middle">${formatDate(file.updateDate)}</td>`);
                let deleteCol=$(`<td style="vertical-align: middle"></td>`);
                tr.append(deleteCol);
                let deleteIcon=$(`<a href="###"><i class="glyphicon glyphicon-trash" style="color: red;font-size: 14pt"></i></a>`);
                deleteCol.append(deleteIcon);
                deleteIcon.click(function(){
                    confirm("真要删除文件："+file.name,function(){
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
                            error:function(){
                                alert("文件删除操作失败！");
                            }
                        });
                    });
                });
            }
            _this.currentProviderPrefix=value;
            _this.currentReportFiles=reportFiles;
        });
    }

    initFooter(footer){
        const saveButton=$(`<button type="button" class="btn btn-primary">保存</button>`);
        footer.append(saveButton);
        const _this=this;
        saveButton.click(function(){
            let fileName=_this.fileEditor.val();
            if(fileName===''){
                alert('请输入文件名！');
                return;
            }
            if(!_this.currentProviderPrefix || !_this.currentReportFiles){
                alert("请选择文件保存地！");
                return;
            }
            for(let file of _this.currentReportFiles){
                let fname=file.name;
                let pos=fname.indexOf(".");
                fname=fname.substring(0,pos);
                if(fname===fileName){
                    alert(`文件[${fileName}]已存在`);
                    return;
                }
            }
            fileName=_this.currentProviderPrefix+fileName+".ureport.xml";
            $.ajax({
                url:window._server+"/designer/saveReportFile",
                data:{file:fileName,content:_this.content},
                type:'POST',
                success:function(){
                    alert("保存成功！");
                    window._reportFile=fileName;
                    _this.context.fileInfo.setFile(fileName);
                    resetDirty();
                    _this.dialog.modal('hide');
                },
                error:function(){
                    alert("文件保存失败！");
                }
            });
        });
    }

    show(content,context){
        this.content=content;
        this.context=context;
        this.fileEditor.val('');
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
            error:function(){
                alert("加载报表文件列表失败!");
            }
        });
        this.dialog.modal('show');
    }
}