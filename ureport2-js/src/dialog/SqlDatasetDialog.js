/**
 * Created by Jacky.Gao on 2017-02-06.
 */
import ParameterTable from './ParameterTable.js';
import {alert} from '../MsgBox.js';
import {setDirty} from '../Utils.js';
import PreviewDataDialog from './PreviewDataDialog.js'
export default class SqlDatasetDialog{
    constructor(db,data){
        this.db=db;
        this.datasources=db.datasources;
        this.data=data;
        this.dialog=$(`<div class="modal fade" role="dialog" aria-hidden="true" style="z-index: 10000;overflow: auto">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title">
                            数据集配置
                        </h4>
                    </div>
                    <div class="modal-body"></div>
                    <div class="modal-footer"></div>
                </div>
            </div>
        </div>`);
        const body=this.dialog.find('.modal-body'),footer=this.dialog.find(".modal-footer");
        this.initSqlEditor(body);
        this.initParameterEditor(body);
        this.initButton(footer);
    }
    initSqlEditor(body){
        const nameRow=$(`<div class="row" style="margin: 10px;">数据集名称：</div>`);
        this.nameEditor=$(`<input type="text" class="form-control" style="font-size: 13px;width:762px;display: inline-block">`);
        nameRow.append(this.nameEditor);
        body.append(nameRow);

        const sqlRow=$(`<div class="row" style="margin:10px;">SQL:</div>`);
        this.sqlEditor=$(`<textarea placeholder="如:select username,dept_id from employee where dept_id=:deptId" class="form-control" rows="8" cols="30"></textarea>`);
        sqlRow.append(this.sqlEditor);
        body.append(sqlRow);
    }

    initParameterEditor(body){
        const row=$(`<div class="row" style="margin:10px;">查询参数<span class="text-info">(请将上述SQL中用到的查询参数名定义在下面的表格中)</span>:</div>`);
        body.append(row);
        const tableRow=$(`<div class="row" style="margin:10px;"></div>`);
        body.append(tableRow);
        this.parameterTable=new ParameterTable(tableRow,this.data.parameters);
    }

    initButton(footer){
        const _this=this;
        const previewButton=$(`<button class="btn btn-primary">预览数据</button>`);
        footer.append(previewButton);
        previewButton.click(function(){
            const sql=_this.sqlEditor.val();
            const type=_this.db.type;
            const parameters={
                sql,
                type,
                parameters:JSON.stringify(_this.data.parameters)
            };
            if(type==='jdbc'){
                parameters.username=_this.db.username;
                parameters.password=_this.db.password;
                parameters.driver=_this.db.driver;
                parameters.url=_this.db.url;
            }else if(type==='buildin'){
                parameters.name=_this.db.name;
            }
            const previewDialog=new PreviewDataDialog();
            previewDialog.show();
            const url=window._server+"/datasource/previewData";
            $.ajax({
                type:'POST',
                url,
                data:parameters,
                success:function(data){
                    previewDialog.showData(data);
                },
                error:function(){
                    previewDialog.showError("<div style='color: #d30e00;'>数据预览失败，请检查配置是否正确.</div>");
                }
            });
        });

        const confirmButton=$(`<button class="btn btn-primary">确定</button>`);
        footer.append(confirmButton);
        confirmButton.click(function(){
            const name=_this.nameEditor.val(),sql=_this.sqlEditor.val();
            if(!name || name===""){
                alert("数据集名称不能为空!");
                return;
            }
            if(!sql || sql!==""){
                alert("数据集SQL不能为空！");
                return;
            }
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
            _this.onSave.call(this,name,sql,_this.data.parameters);
            setDirty();
            _this.dialog.modal('hide');
        });
    }

    show(onSave,parameters){
        this.onSave=onSave;
        if(parameters){
            this.data=parameters;
            this.parameterTable.data=this.data.parameters;
        }
        this.dialog.modal('show');
        this.oldName=this.data.name;
        this.nameEditor.val(this.data.name);
        this.sqlEditor.val(this.data.sql);
        this.parameterTable.refreshData();
    }
}