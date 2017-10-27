/**
 * Created by Jacky.Gao on 2017-10-12.
 */
import Utils from './Utils.js';
import Instance from './instance/Instance.js';

export default class Toolbar{
    constructor(){
        this.toolbar=$("<nav class=\"navbar navbar-default pb-toolbar\" style='background: #ffffff;min-height:40px' role=\"navigation\">");
        var ul=$("<ul class=\"nav navbar-nav\">");
        this.toolbar.append(ul);

        this.tip=$("<div class='alert alert-success alert-dismissable'  style='position: absolute;top:50px;width:100%;z-index: 100'> <button type='button' class='close' data-dismiss='alert' aria-hidden='true'> &times; </button> 保存成功!  </div>");
        this.toolbar.append(this.tip);
        this.tip.hide();

        //ul.append(this.buildSave());
        ul.append(this.buildRemove());
    }
    buildSave(){
        this.save=$("<i class='glyphicon glyphicon-floppy-save' style='color:#2196F3;font-size: 22px;margin: 10px;' title='保存'></i>");
        return this.save;
    }
    buildRemove(){
        this.remove=$("<button type='button' style='margin: 5px' class='btn btn-default btn-small'><i style='color: red' class='glyphicon glyphicon-remove'></i> 删除选中的元素</button>");
        var self=this;
        this.remove.click(function(){
            self.deleteElement();
        });
        $(document).keydown(function(e){
            if(e.which === 46 && e.target && e.target===document.body){
                self.deleteElement();
            }
        });
        return this.remove;
    }
    deleteElement(){
        var select=formBuilder.select;
        if(!select){
            bootbox.alert("请先选择一个组件.");
            return;
        }
        var parent=select.parent();
        var parentContainer=formBuilder.getContainer(parent.prop("id"));
        parentContainer.removeChild(select);
        var id=select.prop("id");
        var pos=-1, targetIns=null;
        $.each(formBuilder.instances,function(i,item){
            if(item.instance.id===id){
                pos=i;
                targetIns=item.instance;
                return false;
            }
        });
        if(pos>-1){
            formBuilder.instances.splice(pos,1);
        }else{
            bootbox.alert('删除元素未注册,不能被删除.');
            return;
        }
        Utils.removeContainerInstanceChildren(targetIns);
        select.remove();
        formBuilder.selectElement();
    }
}