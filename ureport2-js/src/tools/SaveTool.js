/**
 * Created by Jacky.Gao on 2017-01-25.
 */
import Tool from './Tool.js';
import SaveDialog from '../dialog/SaveDialog.js';
import {alert} from '../MsgBox.js';
import {resetDirty,tableToXml} from '../Utils.js';

export default class SaveTool extends Tool{
    execute(){
    }

    buildButton(){
        const group=$(`<div class="btn-group"></div>`);
        const mainBtn=$(`<button type="button" class="btn btn-default dropdown-toggle" style="border:none;border-radius:0;background: #f8f8f8;padding: 6px 8px;" data-toggle="dropdown" title="保存">
            <i class="ureport ureport-save" style="color: #0e90d2;"></i>
            <span class="caret"></span>
        </button>`);
        const ul=$(`<ul class="dropdown-menu" role="menu"></ul>`);
        const save=$(`<li id="__save_btn" class="disabled">
                <a href="###">
                    <i class="ureport ureport-save" style="color: #0e90d2;"></i> 保存
                </a>
            </li>`);
        ul.append(save);
        const saveDialog=new SaveDialog();
        const _this=this;
        save.click(function(){
            const content=tableToXml(_this.context);
            if(window._reportFile){
                $.ajax({
                    url:window._server+"/designer/saveReportFile",
                    data:{content,file:window._reportFile},
                    type:'POST',
                    success:function(){
                        alert('保存成功！');
                        resetDirty();
                    },
                    error:function(){
                        alert('文件保存错误！');
                    }
                });
            }else{
                saveDialog.show(content,_this.context);
            }
        });
        const saveAs=$(`<li>
                <a href="###">
                    <i class="glyphicon glyphicon-floppy-disk" style="color: #0e90d2;font-size: 16px"></i> 另存为
                </a>
            </li>`);
        ul.append(saveAs);
        saveAs.click(function(){
            const content=tableToXml(_this.context);
            saveDialog.show(content,_this.context);
        });

        group.append(mainBtn);
        group.append(ul);
        return group;
    }


    getTitle(){
        return '保存';
    }
    getIcon(){
        return `<i class="ureport ureport-save" style="color: #0e90d2"></i>`;
    }
}