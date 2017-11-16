/**
 * Created by Jacky.Gao on 2017-02-06.
 */
import CodeMirror from 'codemirror';
import '../../node_modules/codemirror/addon/hint/show-hint.js';
import '../../node_modules/codemirror/addon/lint/lint.js';
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
            <div class="modal-dialog modal-lg" style="width: 980px">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title">
                            ${window.i18n.dialog.sql.title}
                        </h4>
                    </div>
                    <div class="modal-body"></div>
                    <div class="modal-footer"></div>
                </div>
            </div>
        </div>`);
        const body=this.dialog.find('.modal-body'),footer=this.dialog.find(".modal-footer");
        const container=$(`<div></div>`);
        body.append(container);
        const leftContainer=$(`<div style="width: 250px;display: inline-block;vertical-align: top;height: 450px;overflow: auto;"></div>`);
        const rightContainer=$(`<div style="display: inline-block"></div>`);
        container.append(leftContainer);
        container.append(rightContainer);
        this.initTables(leftContainer);

        this.initSqlEditor(rightContainer);
        this.initParameterEditor(rightContainer);
        this.initButton(footer);
    }
    initTables(container){
        const searchGroup=$(`<div class="form-group" style="margin-bottom: 5px;"></div>`);
        container.append(searchGroup);
        const searchEdior=$(`<input class="form-control" placeholder="${window.i18n.dialog.sql.search}" style="display: inline-block;width: 150px;">`);
        searchGroup.append(searchEdior);
        const searchButton=$(`<button class="btn btn-default"><i class="glyphicon glyphicon-search"></i></button>`);
        searchGroup.append(searchButton);
        const _this=this;
        searchButton.click(function(){
            const name=searchEdior.val();
            const rows=_this.tableBody.children('tr');
            for(let row of rows){
                const $row=$(row);
                if(!name || name===""){
                    $row.show();
                    continue;
                }
                const nameTD=$row.find('a');
                const tableName=$(nameTD).text();
                if(tableName.indexOf(name)>-1){
                    $row.show();
                }else{
                    $row.hide();
                }
            }
        });

        const table=$(`<table class="table table-bordered" style="font-size: 12px"><thead><tr style="height: 30px;background: #fafafa"><td style="width: 135px;vertical-align: middle">${window.i18n.dialog.sql.tableName}</td><td style="width: 35px;vertical-align: middle">${window.i18n.dialog.sql.type}</td></tr></thead></table>`);
        this.tableBody=$(`<tbody></tbody>`);
        table.append(this.tableBody);
        container.append(table);
    }
    initSqlEditor(body){
        const nameRow=$(`<div class="row" style="margin: 10px;">${window.i18n.dialog.sql.datasetName}</div>`);
        this.nameEditor=$(`<input type="text" class="form-control" style="font-size: 13px;width:570px;display: inline-block">`);
        nameRow.append(this.nameEditor);
        body.append(nameRow);

        const sqlRow=$(`<div class="row" style="margin:10px;">SQL(<span style="color: #999999;font-size: 12px;">${window.i18n.dialog.sql.desc}`);
        this.sqlEditor=$(`<textarea placeholder="select username,dept_id from employee where dept_id=:deptId" class="form-control" rows="8" cols="30" style="width: 660px"></textarea>`);
        sqlRow.append(this.sqlEditor);
        body.append(sqlRow);
    }

    _buildScriptLintFunction(){
        return function (text, updateLinting, options, editor){
            if(text===''){
                updateLinting(editor,[]);
                return;
            }
            if(!text || text===''){
                return;
            }
            const prefix=text.substring(0,2),suffix=text.substring(text.length-1,text.length);
            if(prefix==='${' && suffix==='}'){
                text=text.substring(2,text.length-1);
            }else{
                return;
            }
            const url=window._server+'/designer/scriptValidation';
            $.ajax({
                url,
                data:{content:text},
                type:'POST',
                success:function(result){
                    if(result){
                        for(let item of result){
                            item.from={line:item.line-1};
                            item.to={line:item.line-1};
                        }
                        updateLinting(editor,result);
                    }else{
                        updateLinting(editor,[]);
                    }
                },
                error:function(response){
                    if(response && response.responseText){
                        alert("服务端错误："+response.responseText+"");
                    }else{
                        alert(`${window.i18n.dialog.sql.syntaxCheckError}`);
                    }
                }
            });
        };
    }

    initParameterEditor(body){
        const row=$(`<div class="row" style="margin:10px;">${window.i18n.dialog.sql.fiterParam}<span class="text-info">${window.i18n.dialog.sql.paramDesc}</span>:</div>`);
        body.append(row);
        const tableRow=$(`<div class="row" style="margin:10px;"></div>`);
        body.append(tableRow);
        this.parameterTable=new ParameterTable(tableRow,this.data.parameters);
    }

    initButton(footer){
        const _this=this;
        const previewButton=$(`<button class="btn btn-primary">${window.i18n.dialog.sql.preview}</button>`);
        footer.append(previewButton);
        previewButton.click(function(){
            const sql=_this.codeMirror.getValue();
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
                error:function(response){
                    if(response && response.responseText){
                        alert("服务端错误："+response.responseText+"");
                    }else{
                        previewDialog.showError(`<div style='color: #d30e00;'>${window.i18n.dialog.sql.previewFail}</div>`);
                    }
                }
            });
        });

        const confirmButton=$(`<button class="btn btn-primary">${window.i18n.dialog.sql.ok}</button>`);
        footer.append(confirmButton);
        confirmButton.click(function(){
            const name=_this.nameEditor.val(),sql=_this.codeMirror.getValue();
            if(!name || name===""){
                alert(`${window.i18n.dialog.sql.nameTip}`);
                return;
            }
            if(!sql || sql===""){
                alert(`${window.i18n.dialog.sql.sqlTip}`);
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
                            alert(`${window.i18n.dialog.sql.ds}[${name}]${window.i18n.dialog.sql.exist}`);
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

    show(onSave,params){
        this.onSave=onSave;
        if(params){
            this.data=params;
            this.parameterTable.data=this.data.parameters;
        }
        this.dialog.modal('show');
        this.oldName=this.data.name;
        this.nameEditor.val(this.data.name);
        this.parameterTable.refreshData();
        setTimeout(()=>{
            if(!this.codeMirror){
                this.codeMirror=CodeMirror.fromTextArea(this.sqlEditor.get(0),{
                    mode:'javascript',
                    lineNumbers:true,
                    gutters: ["CodeMirror-linenumbers", "CodeMirror-lint-markers"],
                    lint: {
                        getAnnotations:this._buildScriptLintFunction(),
                        async:true
                    }
                });
                this.codeMirror.setSize('660px','160px');
            }
            this.codeMirror.setValue(this.data.sql);
        },500);
        const type=this.db.type;
        const parameters={type};
        if(type==='jdbc'){
            parameters.username=this.db.username;
            parameters.password=this.db.password;
            parameters.driver=this.db.driver;
            parameters.url=this.db.url;
        }else if(type==='buildin'){
            parameters.name=this.db.name;
        }
        const _this=this;
        const url=window._server+"/datasource/buildDatabaseTables";
        $.ajax({
            type:"POST",
            data:parameters,
            url,
            success:function(tables){
                _this.tableBody.empty();
                for(let table of tables){
                    const tr=$(`<tr style="height: 30px"></tr>`);
                    const nameTD=$(`<td style="vertical-align: middle"><a href="###" title="${window.i18n.dialog.sql.addSql}">${table.name}</a></td>`);
                    tr.append(nameTD);
                    nameTD.dblclick(function(){
                        const sql="select * from "+table.name+"";
                        _this.codeMirror.setValue(sql);
                    });
                    const typeTD=$(`<td style="vertical-align: middle"></td>`);
                    tr.append(typeTD);
                    const type=table.type;
                    if(type==="TABLE"){
                        typeTD.append(`<span style="color: #49a700">${window.i18n.dialog.sql.table}</span>`)
                    }else{
                        typeTD.append(`<span style="color: #8B2252">${window.i18n.dialog.sql.view}</span>`)
                    }
                    _this.tableBody.append(tr);
                }
            },
            error:function(response){
                if(response && response.responseText){
                    alert("服务端错误："+response.responseText+"");
                }else{
                    alert(`${window.i18n.dialog.sql.loadFail}`);
                }
            }
        })
    }
}