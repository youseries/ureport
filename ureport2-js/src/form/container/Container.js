/**
 * Created by Jacky.Gao on 2017-10-12.
 */
export default class Container{
    constructor(){
        this.children=[];
        this.orderArray=[];
    }
    buildChildrenHtml(html){
        var children=this.getChildren();
        $.each(children,function(index,child){
            html.append(child.toHtml());
        });
        return children;
    }
    getChildren(){
        for(var i=(this.orderArray.length-1);i>-1;i--){
            var id=this.orderArray[i];
            var target=Container.searchAndRemoveChild(id,this.children);
            if(target){
                this.children.unshift(target);
            }
        }
        return this.children;
    }
    addChild(child){
        if($.inArray(child,this.children)===-1){
            this.children.push(child);
        }
    }
    getContainer(){
        if(!this.id){
            this.id=this.container.prop("id");
            if(!this.id){
                this.container.uniqueId();
                this.id=this.container.prop("id");
            }
        }
        return this.container;
    }
    removeChild(child){
        var id=child.prop("id");
        if(!id || id==="")return;
        var pos=-1;
        $.each(this.children,function(index,item){
            if(item.id===id){
                pos=index;
                return false;
            }
        });
        if(pos>-1){
            this.children.splice(pos,1);
        }
    }
    newOrder(orderArray){
        this.orderArray=orderArray;
    }
    static searchAndRemoveChild(id,children){
        var target,pos=-1;
        $.each(children,function(index,instance){
            if(instance.id===id){
                target=instance;
                pos=index;
                return false;
            }
        });
        if(pos!=-1){
            children.splice(pos,1);
        }
        return target;
    }
}