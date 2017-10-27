/**
 * Created by Jacky.Gao on 2017-10-12.
 */
import Utils from '../Utils.js';
import Property from './Property.js';

export default class TabProperty extends Property{
    constructor(){
        super();
    }
    refreshValue(editor){
        this.current=editor;
        this.tabs=editor.tabs;
        this.col.empty();
        this.formGroup=$("<div class='form-group'>");
        this.col.append(this.formGroup);
        this.formGroup.append("<label>标签页</label>");
        const self=this;
        $.each(this.tabs,function(i,item){
            self.formGroup.append(self.addTabEditor(item));
        });
    }
    addTabEditor(tab){
        const inputGroup=$("<div class='input-group'>");
        const input=$("<input type='text' class='form-control'>");
        inputGroup.append(input);
        input.val(tab.getTabName());
        input.change(function(){
            tab.setTabName($(this).val());
        });
        const inputGroupAddon=$("<span class='input-group-addon'>");
        inputGroup.append(inputGroupAddon);
        const del=$("<span class='pb-icon-delete'><li class='glyphicon glyphicon-trash'></li></span>");
        const self=this;
        del.click(function(){
            if(self.tabs.length===1){
                bootbox.alert("标签页至少要保留一个.");
                return;
            }
            let index=-1;
            $.each(self.tabs,function(i,item){
                if(item===tab){
                    index=i;
                    return false;
                }
            });
            if(index==-1)return;
            self.tabs.splice(index,1);
            inputGroup.remove();
            tab.remove();
            self.tabs[0].link.trigger("click");
        });
        inputGroupAddon.append(del);
        const add=$("<span class='pb-icon-add' style='margin-left: 10px'><li class='glyphicon glyphicon-plus'></span>");
        add.click(function(){
            const seq=Utils.seq("tab_control_component");
            const newTab=self.current.addTab();
            self.formGroup.append(self.addTabEditor(newTab));
            Utils.attachSortable(newTab.container.container);
        });
        inputGroupAddon.append(add);
        return inputGroup;
    }
}