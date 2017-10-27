/**
 * Created by Jacky.Gao on 2017-10-12.
 */
import ContainerInstance from './ContainerInstance.js';
import Tab from './Tab.js';
export default class TabControlInstance extends ContainerInstance{
    constructor(seq){
        super();
        this.seq=seq;
        this.tabs=[];
        this.tabNum=1;
        this.element=$("<div style='min-height: 100px;' class='tabcontainer'>");
        this.ul=$("<ul class='nav nav-tabs'>");
        this.element.append(this.ul);
        this.tabContent=$("<div class='tab-content'>");
        this.element.append(this.tabContent);
        this.addTab(true);
        this.addTab();
        this.addTab();
        this.element.uniqueId();
        this.id=this.element.prop("id");
        this.visible="true";
    }
    addTab(active,json){
        let tabnum=this.tabNum++;
        const tab=new Tab(this.seq,tabnum);
        if(json){
            tab.initFromJson(json);
        }
        this.containers.push(tab.container);
        formBuilder.containers.push(tab.container);
        var li=tab.li;
        if(active){
            li.addClass("active");
        }
        this.ul.append(li);
        var tabContent=tab.getTabContent();
        if(active){
            tabContent.addClass("in active");
        }
        this.tabContent.append(tabContent);
        this.tabs.push(tab);
        return tab;
    }
    getTab(id){
        let targetTab=null;
        $.each(this.tabs,function(index,tab){
            if(tab.getId()===id){
                targetTab=tab;
                return false;
            }
        });
        return targetTab;
    }
    initFromJson(json){
        $.each(this.tabs,function(index,tab){
            tab.remove();
        });
        this.tabs.splice(0,this.tabs.length);
        this.visible=json.visible;
        var tabs=json.tabs;
        for(var i=0;i<tabs.length;i++){
            var tab=tabs[i];
            if(i===0){
                this.addTab(true,tab);
            }else{
                this.addTab(false,tab);
            }
        }
    }
    toJSON(){
        var json={id:this.id,type:TabControlInstance.TYPE,visible:this.visible};
        var tabs=[];
        $.each(this.tabs,function(index,tab){
            tabs.push(tab.toJSON());
        });
        json.tabs=tabs;
        return json;
    }
}
TabControlInstance.TYPE="TabControl";