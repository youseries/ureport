/**
 * Created by Jacky.Gao on 2017-10-12.
 */
import './css/iconfont.css';
import './css/form.css';
import './external/jquery-ui.css';
import './external/bootstrap-datetimepicker.css';
import '../../node_modules/bootstrap/dist/js/bootstrap.js';
import Utils from './Utils.js';
import CanvasContainer from './container/CanvasContainer.js';
import Toolbar from './Toolbar.js';
import Palette from './Palette.js';
import PageProperty from './property/PageProperty.js';
import Component from './component/Component.js';

export default class FormBuilder{
    constructor(container){
        window.formBuilder=this;
        this.container=container;
        this.formPosition="up";
        this.toolbar=new Toolbar();
        this.container.append(this.toolbar.toolbar);

        var palette=new Palette();
        this.propertyPalette=palette.propertyPalette;
        this.components=palette.components;
        this.pageProperty=new PageProperty();
        this.propertyPalette.append(this.pageProperty.propertyContainer);
        this.pageProperty.propertyContainer.show();

        this.container.append(palette.tabControl);
        this.containers=[];
        this.instances=[];
        this.initRootContainer();
    }
    initRootContainer(){
        const body=$("<div style='width:auto;margin-left:300px;margin-right:10px'>");
        this.container.append(body);
        const shadowContainer=$("<div class='pb-shadow'>");
        body.append(shadowContainer);
        const container=$("<div class='container pb-canvas-container form-horizontal' style='width: auto;padding: 0;'>");
        shadowContainer.append(container);
        const row=$("<div class='row'>");
        const canvas=$("<div class='col-md-12 pb-dropable-grid' style='min-height: 100px;border: none;padding: 0;;'>");
        row.append(canvas);
        container.append(row);
        this.rootContainer=new CanvasContainer(canvas);
        this.containers.push(this.rootContainer);
        Utils.attachSortable(canvas);
    }
    initData(reportDef){
        this.reportDef=reportDef;
        reportDef._formBuilder=this;
        let datasources=reportDef.datasources;
        if(!datasources){
            datasources=[];
        }
        let params=[];
        let datasetMap=new Map();
        for(let ds of datasources){
            const datasets=ds.datasets || [];
            for(let dataset of datasets){
                const parameters=dataset.parameters || [];
                params=params.concat(parameters);
                datasetMap.set(dataset.name,dataset.fields);
            }
        }
        this.reportParameters=params;
        this.datasetMap=datasetMap;
        const form=reportDef.searchForm || {};
        if(form){
            this.formPosition=form.formPosition;
            const components= form.components;
            this.buildPageElements(components,this.rootContainer);
        }
        this.pageProperty.refreshValue();
    }

    buildData(){
        this.reportDef.searchFormXml=this.toXml();
        this.reportDef.searchForm=this.toJson();
    }

    buildPageElements(elements,parentContainer){
        if(!elements || elements.length===0){
            return;
        }
        for(var i=0;i<elements.length;i++){
            var element=elements[i];
            var type=element.type;
            var targetComponent;
            $.each(this.components,function(index,c){
                if(c.component.support(type)){
                    targetComponent=c.component;
                    return false;
                }
            });
            if(!targetComponent){
                throw "Unknow component : "+type+"";
            }
            Utils.attachComponent(targetComponent,parentContainer,element);
        }
    }
    getInstance(id){
        let target;
        $.each(this.instances,function(index,item){
            if(item.id===id){
                target=item.instance;
                return false;
            }
        });
        return target;
    }
    toJson(){
        const json={formPosition:this.formPosition};
        json.components=this.rootContainer.toJson();
        return json;
    }
    toXml(){
        let xml=`<search-form form-position="${this.formPosition || 'up'}">`;
        xml+=this.rootContainer.toXml();
        xml+='</search-form>';
        return xml;
    }
    getContainer(containerId){
        var targetContainer;
        $.each(this.containers,function(index,container){
            if(container.id===containerId){
                targetContainer=container;
                return false;
            }
        });
        return targetContainer;
    }
    selectElement(instance){
        var children=this.propertyPalette.children();
        children.each(function(i,item){
            $(item).hide();
        });
        if(!instance){
            this.select=null;
            this.pageProperty.refreshValue();
            this.pageProperty.propertyContainer.show();
            return;
        }
        if(this.select){
            var sameInstance=false;
            if(this.select.prop("id")===instance.prop("id")){
                sameInstance=true;
            }
            this.select.removeClass("pb-hasFocus");
            this.select=null;
            if(sameInstance){
                this.pageProperty.refreshValue();
                this.pageProperty.propertyContainer.show();
                return;
            }
        }
        if(!this.select){
            this.select=instance;
            this.select.addClass("pb-hasFocus");
        }else{
            this.select.removeClass("pb-hasFocus");
            if(this.select!=instance){
                this.select=instance;
                this.select.addClass("pb-hasFocus");
            }
        }
        var instanceId=instance.prop("id");
        $.each(this.instances,function(index,item){
            if(item.id===instanceId){
                var instance=item.instance;
                var property=item.property;
                if(!property){
                    return false;
                }
                property.refreshValue(instance);
                property.propertyContainer.show();
                return false;
            }
        });
    }
    addInstance(newInstance,newElement,component){
        this.instances.push({
            id:newElement.prop("id"),
            instance:newInstance,
            property:component.property
        });
    }
    getComponent(item){
        var componentId=item.attr(Component.ID);
        var target=null;
        $(this.components).each(function(i,item){
            var id=item.id;
            if(id===componentId){
                target=item.component;
                return false;
            }
        });
        return target;
    }
}