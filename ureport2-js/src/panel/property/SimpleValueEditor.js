/**
 * Created by Jacky.Gao on 2017-02-07.
 */
import {setDirty} from '../../Utils.js';

export default class SimpleValueEditor{
    constructor(parentContainer,context){
        this.context=context;
        this.container=$(`<div><label>文本内容</label></div>`);
        parentContainer.append(this.container);
        this.container.hide();
        this.init();
    }
    init(){
        const _this=this;
        this.editor=$(`<textarea rows="5" cols="10" class="form-control"></textarea>`);
        this.container.append(this.editor);
        this.editor.change(function(){
            const value=$(this).val();
            _this.cellDef.value.value=value;
            _this.context.hot.setDataAtCell(_this.rowIndex,_this.colIndex,value);
            setDirty();
        });
    }
    show(cellDef,rowIndex,colIndex,row2Index,col2Index){
        this.cellDef=cellDef;
        this.rowIndex=rowIndex;
        this.colIndex=colIndex;
        this.container.show();
        this.editor.val(cellDef.value.value);
    }
    hide(){
        this.container.hide();
    }
}