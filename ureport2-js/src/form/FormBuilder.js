/**
 * Created by Jacky.Gao on 2017-10-12.
 */
import './css/iconfont.css';
import './css/form.css';
import './external/jquery-ui.css';
import '../../node_modules/bootstrap/dist/css/bootstrap.css';
import '../../node_modules/bootstrap/dist/js/bootstrap.js';
import Utils from './Utils.js';
import CanvasContainer from './container/CanvasContainer.js';
import Toolbar from './Toolbar.js';
import Palette from './Palette.js';
import PageProperty from './property/PageProperty.js';
import Component from './component/Component.js';

export default class FormBuilder{
    constructor(container,server){
        this.pageName="";
        this.pageTitle="";
        window.formBuilder=this;
        this.container=$(container);
        this.server=server || "http://localhost:8080/upage-test";
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
        this.registeredActions=[];
        /*
        this.registeredActions.push(new OpenUrlAction());
        this.registeredActions.push(new AlertAction());
        this.registeredActions.push(new PromptAction());
        this.registeredActions.push(new ConfirmAction());
        this.registeredActions.push(new SubmitFormAction());
        this.registeredActions.push(new ValidateFormAction());
        */
        this.initRootContainer();
        this.initPage();
    }
    initRootContainer(){
        const body=$("<div style='width:auto;margin-left:300px;margin-right:10px'>");
        this.container.append(body);
        const shadowContainer=$("<div class='pb-shadow'>");
        body.append(shadowContainer);
        const container=$("<div class='container pb-canvas-container form-horizontal' style='width: auto'>");
        shadowContainer.append(container);
        const row=$("<div class='row'>");
        const canvas=$("<div class='col-md-12 pb-dropable-grid' style='min-height: 100px'>");
        row.append(canvas);
        container.append(row);
        this.rootContainer=new CanvasContainer(canvas);
        this.containers.push(this.rootContainer);
        Utils.attachSortable(canvas);
    }
    initPage(){
        /*
        var page=_getParameter("p");
        if(!page || page.length<1){
            return;
        }
        var self=this;
        var loadPageJsonUrl=this.server + "/upage/page/"+page;
        $.ajax({
            url:loadPageJsonUrl,
            success:function(data){
                self.initPageData(data);
            },
            error:function(){
                bootbox.alert("加载页面信息失败");
            }
        });
        */
    }
    initPageData(data){
        /*
        if(data){
            this.name=data.name;
            this.pageTitle=data.title;
            this.bindTableId=data.bindTableId;
            var url=this.server+"/upage/loadmastertable?tableId="+this.bindTableId+"";
            var self=this;
            $.ajax({
                url:url,
                success:function(data){
                    self.bindTable=data;
                    self.pageProperty.refreshValue();
                    if(UPage.binding && !data){
                        var wizard=new BindTableWizard();
                        wizard.show();
                    }
                },
                error:function(code,xmlCode,errorInfo){
                    bootbox.alert("加载绑定表失败:"+errorInfo);
                }
            });
        }else{
            if(UPage.binding && !this.bindTable){
                var wizard=new BindTableWizard();
                wizard.show();
            }
        }
        if(data.content){
            var content= ($.parseJSON(data.content)).children;
            this.buildPageElements(content,this.rootContainer);
        }
        */
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
    getRegisteredAction(actionId){
        var action;
        $.each(this.registeredActions,function(index,actionDef){
            if(actionDef.getId()===actionId){
                action=actionDef;
                return false;
            }
        });
        return action;
    }
    getInstance(id){
        var target;
        $.each(this.instances,function(index,item){
            if(item.id===id){
                target=item.instance;
                return false;
            }
        });
        return target;
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
    registerAction(action){
        this.registeredActions.push(action);
    }
}
function _getParameter(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)return unescape(r[2]);
    return null;
};