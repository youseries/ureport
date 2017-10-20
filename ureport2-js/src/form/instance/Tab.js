/**
 * Created by Jacky.Gao on 2017-10-12.
 */
import TabContainer from '../container/TabContainer.js';

export default class Tab{
    constructor(seq,tabnum){
        this.li=$("<li>");
        this.id="tabContent"+seq+""+tabnum;
        this.tabName="页签"+tabnum;
        this.link=$("<a href='#"+this.id+"' data-toggle='tab'>"+this.tabName+"</a>");
        this.link.click(function(e){
            $(this).tab('show');
            e.stopPropagation();
        });
        this.li.append(this.link);
        this.container=new TabContainer(this.id);
    }
    getTabName(){
        return this.tabName;
    }
    setTabName(tabName){
        this.tabName=tabName;
        this.link.text(tabName);
    }
    liToHtml(){
        var li=$("<li>");
        li.append($("<a href='#"+this.id+"1' data-toggle='tab'>"+this.tabName+"</a>"));
        return li;
    }
    getTabContent(){
        return this.container.getContainer();
    }
    remove(){
        this.li.remove();
        this.container.getContainer().remove();
    }
    initFromJson(json){
        this.setTabName(json.tabName);
        this.container.initFromJson(json.container);
    }
    toJSON(){
        return {
            id:this.id,
            tabName:this.tabName,
            type:this.getType(),
            container:this.container.toJSON()
        };
    }
    getType(){
        return "Tab";
    }
}