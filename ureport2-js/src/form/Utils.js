/**
 * Created by Jacky.Gao on 2017-10-12.
 */
import Component from './component/Component.js';
import TabControlInstance from './instance/TabControlInstance.js';
import ContainerInstance from './instance/ContainerInstance.js';
export default class Utils{
    static seq(id){
        var seqValue;
        $.each(Utils.SEQUENCE,function(name,value){
            if(name===id){
                value++;
                seqValue=value;
                Utils.SEQUENCE[id]=seqValue;
                return false;
            }
        });
        if(!seqValue){
            seqValue=1;
            Utils.SEQUENCE[id]=seqValue;
        }
        return seqValue;
    }
    static attachSortable(target){
        target.sortable({
            tolerance:"pointer",
            delay:200,
            dropOnEmpty:true,
            forcePlaceholderSize:true,
            forceHelperSize:true,
            placeholder:"pb-sortable-placeholder",
            connectWith:".pb-dropable-grid,.pb-tab-grid,.panel-body,.pb-carousel-container",
            start:function(e,ui){
                ui.item.css("display","block");
            },
            receive:function(e,ui){
                Utils.add=true;
            },
            remove:function(e,ui){
                var item=ui.item;
                var parent=$(this);
                var parentContainer=formBuilder.getContainer(parent.prop("id"));
                parentContainer.removeChild(item);
            },
            stop:function(e,ui){
                var item=ui.item;
                var parent=item.parent();
                var parentContainer=formBuilder.getContainer(parent.prop("id"));
                if(!parentContainer){
                    return;
                }
                if(item.hasClass("pb-component")){
                    //new component
                    var targetComponent=formBuilder.getComponent(item);
                    var newElement=Utils.attachComponent(targetComponent,parentContainer);
                    item.replaceWith(newElement);
                    item=newElement;
                }
                if(Utils.add){
                    var targetInstance=formBuilder.getInstance(item.prop("id"));
                    parentContainer.addChild(targetInstance);
                    Utils.add=false;
                }
                var newOrder=parent.sortable("toArray");
                if(newOrder.length>1){
                    parentContainer.newOrder(newOrder);
                }
            }
        });
    }
    static attachComponent(targetComponent,parentContainer,initJson){
        var newInstance;
        if(initJson){
            newInstance=targetComponent.newInstance(initJson.cols);
            newInstance.initFromJson(initJson);
        }else{
            newInstance=targetComponent.newInstance();
        }
        parentContainer.addChild(newInstance);
        if(newInstance instanceof ContainerInstance){
            $.each(newInstance.containers,function(i,container){
                formBuilder.containers.push(container);
            });
        }
        var newElement=newInstance.element;
        newElement.attr(Component.ID,targetComponent.id);
        formBuilder.addInstance(newInstance,newElement,targetComponent);
        if(initJson){
            parentContainer.addElement(newElement);
        }
        var childrenContainers;
        if(newElement.hasClass("row")){
            childrenContainers=newElement.children(".pb-dropable-grid");
        }else if(newElement.hasClass("tabcontainer")){
            childrenContainers=newElement.find(".pb-tab-grid");
        }else if(newElement.hasClass("panel-group") || newElement.hasClass("panel-default")){
            childrenContainers=newElement.find(".panel-body");
        }else if(newElement.hasClass("carousel")){
            childrenContainers=newElement.find(".pb-carousel-container");
        }else if(newElement.hasClass('btn')){
            childrenContainers=newElement;
        }
        if(childrenContainers){
            childrenContainers.each(function(index,child){
                Utils.attachSortable($(child));
            });
        }
        newElement.click(function(event){
            formBuilder.selectElement($(this));
            event.stopPropagation();
        });
        if(!newElement.hasClass("panel") && !newElement.hasClass("panel-default")){
            newElement.addClass("pb-element");
        }
        newElement.mouseover(function(e){
            newElement.addClass("pb-element-hover");
            e.stopPropagation();
        });
        newElement.mouseout(function(e){
            newElement.removeClass("pb-element-hover");
            e.stopPropagation();
        });
        return newElement;
    }
    static removeContainerInstanceChildren(ins){
        var childrenInstances=[];
        if(ins instanceof TabControlInstance){
            var tabs=ins.tabs;
            $.each(tabs,function(index,tab){
                var children=tab.container.children;
                childrenInstances=childrenInstances.concat(children);
            });
        }else if(ins instanceof ContainerInstance){
            $.each(ins.containers,function(index,container){
                var children=container.children;
                childrenInstances=childrenInstances.concat(children);
            });
        }
        if(childrenInstances.length===0)return;
        $.each(childrenInstances,function(index,child){
            var pos=-1,id=child.id;
            $.each(formBuilder.instances,function(i,item){
                if(item.id===id){
                    pos=i;
                    return false;
                }
            });
            if(pos>-1){
                formBuilder.instances.splice(pos,1);
            }else{
                bootbox.alert('删除元素未注册,不能被删除.');
            }
            Utils.removeContainerInstanceChildren(child);
        });
    }
}
Utils.SEQUENCE={};
Utils.binding=true;
Utils.add=false;