/**
 * Created by Jacky.Gao on 2017-02-04.
 */
import uuid from 'node-uuid';
import {alert,confirm,dialog} from '../MsgBox.js';
import BeanMethodDialog from '../dialog/BeanMethodDialog.js';
import BaseTree from './BaseTree.js';

export default class DatabaseTree extends BaseTree{
    constructor(container,datasources,ds,springDialog,context){
        super();
        this.type='spring';
        this.datasources=datasources;
        this.datasets=ds.datasets || [];
        this.springDialog=springDialog;
        this.context=context;
        this.id=uuid.v1();
        this.name=ds.name;
        this.beanId=ds.beanId;
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
                <i class="ureport ureport-leaf"></i> <a href='###' class="ds_name">${this.name}</a>
            </span>`);
        this.datasourceLi.append(rootSpan);
        this.ul.append(this.datasourceLi);
        this.attachEvent(rootSpan,this.datasourceLi);
        this.datasetUL=$(`<ul style="margin-left: -16px;"></ul>`);
        this.datasourceLi.append(this.datasetUL);
        const _this=this;
        const beanMethodDialog=new BeanMethodDialog(this.datasources,this.beanId);
        $.contextMenu({
            selector:'#'+this.id,
            callback:function(key,options){
                if(key==='add'){
                    const span=$(options.selector);
                    beanMethodDialog.show(function(name,method,clazz){
                        const dataset={name,method,clazz,fields:[]};
                        _this.datasets.push(dataset);
                        const fieldsUL=_this.addDataset(dataset);
                        if(clazz && clazz!==''){
                            _this.buildFileds(dataset,fieldsUL);
                        }
                    });
                }else if(key==='delete'){
                    confirm(`${window.i18n.tree.delConfirm}[${_this.name}]？`,function(){
                        const datasources=_this.datasources;
                        const index=datasources.indexOf(_this);
                        datasources.splice(index,1);
                        _this.treeContainer.remove();
                    });
                }else if(key==='edit'){
                    _this.springDialog.show(function(name,beanId){
                        _this.name=name;
                        _this.beanId=beanId;
                        rootSpan.find(".ds_name").html(name);
                    }, {
                        name: _this.name,
                        beanId: _this.beanId
                    });
                }
            },
            items:{
                "add": {name: `${window.i18n.tree.addDataset}`, icon: "add"},
                "edit": {name: `${window.i18n.tree.edit}`, icon: "edit"},
                "delete": {name: `${window.i18n.tree.del}`, icon: "delete"}
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
        const beanMethodDialog=new BeanMethodDialog(this.datasources,this.beanId);

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
                                alert(`${window.i18n.tree.addField}`);
                                return;
                            }
                        }
                        let field={name:newFieldName};
                        dataset.fields.push(field);
                        _this.addField(dataset,dataset.fields,field,fieldsUL);
                    });
                }else if(key==='delete'){
                    confirm(`${window.i18n.tree.delDatasetConfirm}[${dataset.name}]?`,function(){
                        const index=_this.datasets.indexOf(dataset);
                        _this.datasets.splice(index,1);
                        li.remove();
                    });
                }else if(key === 'edit'){
                    beanMethodDialog.show(function(name,method,clazz){
                        dataset.name=name;
                        dataset.method=method;
                        dataset.clazz=clazz;
                        span.find(".dataset_name").html(name);
                        if(clazz && clazz!==''){
                            _this.buildFileds(dataset,fieldsUL);
                        }
                    },dataset);
                }else if(key==='refresh'){
                    _this.buildFileds(dataset,fieldsUL,true);
                }
            },
            items:{
                "add": {name:`${window.i18n.tree.addField}`, icon: "add"},
                "edit": {name: `${window.i18n.tree.edit}`, icon: "edit"},
                "delete": {name: `${window.i18n.tree.del}`, icon: "delete"},
                "refresh": {name: `${window.i18n.tree.refresh}`,icon:'loading'}
            }
        });
        return fieldsUL;
    }

    buildFileds(dataset,ul,refresh){
        const defaultFields=dataset.fields,_this=this;
        if(!refresh && defaultFields){
            for(let field of defaultFields){
                _this.addField(dataset,defaultFields,field,ul);
            }
            return;
        }
        $.ajax({
            url:window._server+"/datasource/buildClass",
            data: {
                clazz:dataset.clazz
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