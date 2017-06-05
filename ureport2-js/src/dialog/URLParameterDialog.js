/**
 * Created by Jacky.Gao on 2017-02-07.
 */
import {alert,confirm} from '../MsgBox.js';
import {setDirty} from '../Utils.js';
import URLParameterItemDialog from './URLParameterItemDialog.js';

export default class URLParameterDialog{
    constructor(){
        this.urlParameterItemDialog=new URLParameterItemDialog();
        this.dialog=$(`<div class="modal fade" role="dialog" aria-hidden="true" style="z-index: 11001">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title">
                            URL参数配置
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
        const addButton=$(`<button type="button" class="btn btn-default" style="float: right">
            <i class="glyphicon glyphicon-plus-sign" style="font-size: 12pt;color: #0000C0" title="添加参数"></i>
        </button>`);
        const _this=this;
        addButton.click(function(){
            const param={name:'',value:''};
            _this.urlParameterItemDialog.show(function(){
                _this.parameters.push(param);
                const tr=$(`<tr></tr>`);
                const nameTD=$(`<td>${param.name}</td>`);
                const valueTD=$(`<td>${param.value}</td>`);
                tr.append(nameTD);
                tr.append(valueTD);
                _this.tbody.append(tr);
                const td=$(`<td></td>`);
                const edit=$(`<a href="###"><i class="glyphicon glyphicon-edit" style="font-size: 12pt;color: #0000C0"></i></a>`);
                td.append(edit);
                edit.click(function(){
                    _this.urlParameterItemDialog.show(function(){
                        nameTD.html(param.name);
                        valueTD.html(param.value);
                    },param,'edit');
                });
                const del=$(`<a href="###" style="margin-left: 10px"><i class="glyphicon glyphicon-trash" style="font-size: 12pt;color: #0000C0"></i></a>`);
                td.append(del);
                del.click(function(){
                    confirm('真的要删除？',function(){
                        const index=_this.parameters.indexOf(param);
                        _this.parameters.splice(index,1);
                        tr.remove();
                    });
                });
                tr.append(td);
            },param,'add');
        });
        body.append(addButton);
        const table=$(`<table class="table table-bordered">
            <thead><tr style="background-color: #eeeeee"><td style="width: 150px">参数名</td><td style="width: 350px">参数值表达式</td><td>操作</td></tr></thead>
        </table>`);
        this.tbody=$(`<tbody></tbody>`);
        table.append(this.tbody);
        body.append(table);
    }
    show(parameters){
        this.parameters=parameters;
        this.dialog.modal('show');
        this.tbody.empty();
        const _this=this;
        for(let param of parameters){
            const tr=$(`<tr></tr>`);
            const nameTD=$(`<td>${param.name}</td>`);
            const valueTD=$(`<td>${param.value}</td>`);
            tr.append(nameTD);
            tr.append(valueTD);
            _this.tbody.append(tr);
            const td=$(`<td></td>`);
            const edit=$(`<a href="###"><i class="glyphicon glyphicon-edit" style="font-size: 12pt;color: #0000C0"></i></a>`);
            td.append(edit);
            edit.click(function(){
                _this.urlParameterItemDialog.show(function(){
                    nameTD.html(param.name);
                    valueTD.html(param.value);
                },param,'edit');
            });
            const del=$(`<a href="###" style="margin-left: 10px"><i class="glyphicon glyphicon-trash" style="font-size: 12pt;color: #0000C0"></i></a>`);
            td.append(del);
            del.click(function(){
                confirm('真的要删除？',function(){
                    const index=_this.parameters.indexOf(param);
                    _this.parameters.splice(index,1);
                    tr.remove();
                });
            });
            tr.append(td);
        }
    }
}