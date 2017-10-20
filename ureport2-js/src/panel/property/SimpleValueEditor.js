/**
 * Created by Jacky.Gao on 2017-02-07.
 */
import {setDirty} from '../../Utils.js';

export default class SimpleValueEditor{
    constructor(parentContainer,context){
        this.context=context;
        this.container=$(`<div></div>`);
        parentContainer.append(this.container);
        this.container.hide();
        this.init();
    }
    init(){
        const _this=this;
        this.container.append(this.buildLineHeight());
        this.container.append(`<div><label>${window.i18n.property.simple.content}</label></div>`);
        this.editor=$(`<textarea rows="5" cols="10" class="form-control"></textarea>`);
        this.container.append(this.editor);
        this.editor.change(function(){
            const value=$(this).val();
            _this.cellDef.value.value=value;
            _this.context.hot.setDataAtCell(_this.rowIndex,_this.colIndex,value);
            setDirty();
        });
    }

    buildLineHeight(){
        const _this=this;
        const group=$(`<div class="form-group" style="margin-left: 8px;margin-top: 5px;margin-bottom: 5px;"><label>${window.i18n.property.simple.lineHeight}</label></div>`);
        this.lineHeightEditor=$(`<input type="number" class="form-control" placeholder="${window.i18n.property.simple.tip}" style="display: inline-block;width: 310px;padding: 3px;font-size: 12px;height: 25px;">`);
        group.append(this.lineHeightEditor);
        this.lineHeightEditor.change(function(){
            const value=$(this).val();
            _this.cellDef.cellStyle.lineHeight=value;
            let td=_this.context.hot.getCell(_this.rowIndex,_this.colIndex);
            if(value===''){
                $(td).css("line-height",'');
            }else{
                $(td).css("line-height",value);
            }
            _this.context.hot.render();
        });
        return group;
    }

    show(cellDef,rowIndex,colIndex,row2Index,col2Index){
        this.cellDef=cellDef;
        this.rowIndex=rowIndex;
        this.colIndex=colIndex;
        this.container.show();
        this.editor.val(cellDef.value.value);
        this.lineHeightEditor.val(cellDef.cellStyle.lineHeight);
    }
    hide(){
        this.container.hide();
    }
}