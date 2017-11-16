/**
 * Created by Jacky.Gao on 2017-02-04.
 */
import uuid from 'node-uuid';
import {alert,confirm,dialog} from '../MsgBox.js';
import SqlDatasetDialog from '../dialog/SqlDatasetDialog.js';
import BaseTree from './BaseTree.js';

export default class DatabaseTree extends BaseTree{
    constructor(container,datasources,ds,datasourceDialog,context){
        super();
        this.type='jdbc';
        this.datasources=datasources;
        this.ds=ds;
        this.datasets=ds.datasets || [];
        this.datasourceDialog=datasourceDialog;
        this.context=context;
        this.id=uuid.v1();
        this.name=ds.name;
        this.username=ds.username;
        this.password=ds.password;
        this.driver=ds.driver;
        this.url=ds.url;
        this.init(container);
    }
    init(container){
        this.treeContainer=$(`<div class="tree" style="margin-left: 10px"></div>`);
        container.append(this.treeContainer);
        this.ul=$(`<ul style="padding-left: 20px;"></ul>`);
        this.treeContainer.append(this.ul);
        this._buildDatasource();
        for(let dataset of this.datasets){
            const fieldsUL=this.addDataset(dataset);
            this.buildFileds(dataset,fieldsUL);
        }
    }
    _buildDatasource(){
        this.datasourceLi=$(`<li></li>`);
        const rootSpan=$(`
            <span id="${this.id}">
                <i class='ureport ureport-minus' style='margin-right:2px'></i>
                <i class="ureport ureport-database"></i> <a href='###' class="ds_name">${this.name}</a>
            </span>`);
        this.datasourceLi.append(rootSpan);
        this.ul.append(this.datasourceLi);
        this.attachEvent(rootSpan,this.datasourceLi);
        this.datasetUL=$(`<ul style="margin-left: -16px;"></ul>`);
        this.datasourceLi.append(this.datasetUL);
        const _this=this;
        const datasetDialog=new SqlDatasetDialog(this,{parameters:[]});
        $.contextMenu({
            selector:'#'+this.id,
            callback:function(key,options){
                if(key==='add'){
                    const span=$(options.selector);
                    datasetDialog.show(function(name,sql,parameters){
                        const dataset={name,sql,parameters};
                        _this.datasets.push(dataset);
                        const fieldsUL=_this.addDataset(dataset);
                        _this.buildFileds(dataset,fieldsUL);
                    },{parameters:[]});
                }else if(key==='delete'){
                    confirm(`${window.i18n.tree.delConfirm}[${_this.name}]？`,function(){
                        let index=-1;
                        const datasources=_this.datasources;
                        for(let i=0;i<datasources.length;i++){
                            let d=_this.datasources[i];
                            if(d.name===_this.name){
                                index=i;
                                break;
                            }
                        }
                        datasources.splice(index,1);
                        _this.treeContainer.remove();
                    });
                }else if(key==='edit'){
                    _this.datasourceDialog.show(function(name,username,password,driver,url){
                        _this.name=name;
                        _this.username=username;
                        _this.password=password;
                        _this.driver=driver;
                        _this.url=url;
                        _this.ds.name=name;
                        _this.ds.username=username;
                        _this.ds.password=password;
                        _this.ds.driver=driver;
                        _this.ds.url=url;
                        rootSpan.find(".ds_name").html(name);
                    }, {
                        name: _this.name,
                        username: _this.username,
                        password: _this.password,
                        driver: _this.driver,
                        url: _this.url
                    });
                }
            },
            items:{
                "add": {name:`${window.i18n.tree.addDataset}`, icon: "add"},
                "edit": {name: `${window.i18n.tree.edit}`, icon: "edit"},
                "delete": {name:`${window.i18n.tree.del}`, icon: "delete"}
            }
        });
    }

    addDataset(dataset){
        const li=$(`<li></li>`);
        const spanId=uuid.v1();
        const span=$(`<span id="${spanId}"><i class='ureport ureport-minus' style='margin-right:2px'></i> <i class="ureport ureport-sqlds"></i> <a href='###' class="dataset_name">${dataset.name}</a></span>`);
        li.append(span);
        this.datasetUL.append(li);
        this.attachEvent(span,li);
        const fieldsUL=$(`<ul style="padding-left: 22px;"></ul>`);
        li.append(fieldsUL);
        const _this=this;
        const datasetDialog=new SqlDatasetDialog(this,dataset);

        const newFiledGroup=$(`<div>${window.i18n.tree.inputTip}</div>`);
        const newFieldEditor=$(`<input type="text" class="form-control">`);
        newFiledGroup.append(newFieldEditor);

        $.contextMenu({
            selector:'#'+spanId,
            callback:function(key,options){
                if(key==='add'){
                    const span=$(options.selector);
                    dialog(`${window.i18n.tree.addField}`,newFiledGroup,function(){
                        const newFieldName=newFieldEditor.val();
                        for(let field of dataset.fields){
                            if(field.name===newFieldName){
                                alert(`${window.i18n.tree.fieldExist}`);
                                return;
                            }
                        }
                        let field={name:newFieldName};
                        dataset.fields.push(field);
                        _this.addField(dataset,dataset.fields,field,fieldsUL);
                    });
                }else if(key==='delete'){
                    confirm(`${window.i18n.tree.delDatasetConfirm}[${dataset.name}]?`,function(){
                        let index=-1;
                        for(let i=0;i< _this.datasets.length;i++){
                            const d=_this.datasets[i];
                            if(d.name===dataset.name){
                                index=i;
                                break;
                            }
                        }
                        _this.datasets.splice(index,1);
                        li.remove();
                    });
                }else if(key === 'edit'){
                    datasetDialog.show(function(name,sql,parameters){
                        dataset.name=name;
                        dataset.sql=sql;
                        dataset.parameters=parameters;
                        span.find(".dataset_name").html(name);
                        dataset.fields=null;
                        _this.buildFileds(dataset,fieldsUL);
                    });
                }else if(key==='refresh'){
                    dataset.fields=null;
                    _this.buildFileds(dataset,fieldsUL);
                }
            },
            items:{
                "add": {name: `${window.i18n.tree.addField}`, icon: "add"},
                "edit": {name: `${window.i18n.tree.edit}`, icon: "edit"},
                "delete": {name: `${window.i18n.tree.del}`, icon: "delete"},
                "refresh": {name: `${window.i18n.tree.refresh}`,icon:'loading'}
            }
        });
        return fieldsUL;
    }

    buildFileds(dataset,ul){
        const defaultFields=dataset.fields,_this=this;
        if(defaultFields){
            ul.empty();
            for(let field of defaultFields){
                _this.addField(dataset,defaultFields,field,ul);
            }
            return;
        }
        $.ajax({
            url:window._server+"/datasource/buildFields",
            data: {
                sql: dataset.sql,
                parameters: JSON.stringify(dataset.parameters),
                username: this.username,
                password: this.password,
                driver:this.driver,
                url:this.url,
                type:'jdbc'
            },
            success:function(fields){
                dataset.fields=fields;
                ul.empty();
                for(let field of fields){
                    _this.addField(dataset,fields,field,ul);
                }
            },
            error:function(response){
                if(response && response.responseText){
                    alert("服务端错误："+response.responseText+"");
                }else{
                    alert(`${window.i18n.tree.loadFieldFail}`);
                }
            }
        })
    }

    addField(dataset,fields,field,ul){
        const _this=this;
        const li=$(`<li></li>`);
        const spanId=uuid.v1();
        const span=$(`<span id="${spanId}" title="${window.i18n.tree.doubleClick}"><i class="ureport ureport-property"></i> <a href='###'>${field.name}</a></span>`);
        li.append(span);
        span.dblclick(function(){
            _this._buildClickEvent(dataset,field,_this.context)
        });
        ul.append(li);
        $.contextMenu({
            selector:'#'+spanId,
            callback:function(key,options){
                if(key==='delete'){
                    confirm(`${window.i18n.tree.delFieldConfirm}[${field.name}]?`,function(){
                        const index=fields.indexOf(field);
                        fields.splice(index,1);
                        li.remove();
                    });
                }
            },
            items:{
                "delete": {name: `${window.i18n.tree.del}`, icon: "delete"}
            }
        });
    }

    attachEvent(span,li){
        span.click(function (e) {
            let $liChildren = li.find(' > ul > li');
            if ($liChildren.is(":visible")) {
                $liChildren.hide('fast');
                span.children('i:first').addClass('ureport-plus').removeClass('ureport-minus');
            } else {
                $liChildren.show('fast');
                span.children('i:first').addClass('ureport-minus').removeClass('ureport-plus');
            }
            e.stopPropagation();
        });
    }
}