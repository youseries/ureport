/**
 * Created by Jacky.Gao on 2017-02-07.
 */
import CodeMirror from 'codemirror';
import '../../../node_modules/codemirror/addon/hint/show-hint.js';
import '../../../node_modules/codemirror/addon/lint/lint.js';
import {setDirty} from '../../Utils.js';
import BaseValueEditor from './BaseValueEditor.js';

export default class ZxingValueEditor extends BaseValueEditor{
    constructor(parentContainer,context){
        super();
        this.context=context;
        this.container=$(`<div></div>`);
        parentContainer.append(this.container);
        this._initSize();
        this._initFormat();
        //this._initCodeDisplay();
        this._initTypeSelect();
        this._buildExpand();
        this._initExpressionEditor();
        this._initTextEditor();
    }

    _initSize(){
        const _this=this;
        const sizeGroup=$(`<div class="form-group"><label>${window.i18n.property.zxing.width}</label></div>`);
        this.container.append(sizeGroup);
        this.widthEditor=$(`<input class="form-control" type="number" style="display: inline-block;width: 140px;">`);
        sizeGroup.append(this.widthEditor);
        this.widthEditor.change(function(){
            let value=$(this).val();
            if(!value || isNaN(value)){
                alert(`${window.i18n.property.zxing.numberTip}`);
                return;
            }
            _this.cellDef.value.width=value;
            _this.context.hot.render();
            setDirty();
        });
        sizeGroup.append(`<label style="margin-left: 20px">${window.i18n.property.zxing.height}</label>`);
        this.heightEditor=$(`<input class="form-control" type="number" style="display: inline-block;width: 148px">`);
        sizeGroup.append(this.heightEditor);
        this.heightEditor.change(function(){
            let value=$(this).val();
            if(!value || isNaN(value)){
                alert(`${window.i18n.property.zxing.numberTip}`);
                return;
            }
            _this.cellDef.value.height=value;
            _this.context.hot.render();
            setDirty();
        });
    }

    _initFormat(){
        this.formatGroup=$(`<div class="form-group"><label>${window.i18n.property.zxing.format}</label></div>`);
        this.container.append(this.formatGroup);
        this.formatSelect=$(`<select class="form-control" style="display: inline-block;width: 295px;">
            <option>AZTEC</option>
            <option>CODABAR</option>
            <option>CODE_39</option>
            <option>CODE_93</option>
            <option>CODE_128</option>
            <option>CODABAR</option>
            <option>DATA_MATRIX</option>
            <option>EAN_8</option>
            <option>EAN_13</option>
            <option>ITF</option>
            <option>PDF_417</option>
            <option>UPC_E</option>
            <option>UPC_A</option>
        </select>`);
        const _this=this;
        this.formatSelect.change(function(){
            _this.cellDef.value.format=$(this).val();
            setDirty();
        });
        this.formatGroup.append(this.formatSelect);
    }

    _initCodeDisplay(){
        this.codeDisplayGroup=$(`<div class="form-group"><label>${window.i18n.property.zxing.displayText}</label></div>`);
        this.container.append(this.codeDisplayGroup);
        this.enabledCodeDisplayRadio=$(`<label class="checkbox-inline" style="padding-left: 5px">
            <input type="radio" name="codeDisplay" value="true"> ${window.i18n.property.zxing.yes}
        </label>`);
        const _this=this;
        this.codeDisplayGroup.append(this.enabledCodeDisplayRadio);
        this.enabledCodeDisplayRadio.children('input').click(function(){
            _this.cellDef.value.codeDisplay=true;
            setDirty();
        });
        this.disabledCodeDisplayRadio=$(`<label class="checkbox-inline" style="padding-left: 5px">
            <input type="radio" name="codeDisplay" value="true"> ${window.i18n.property.zxing.no}
        </label>`);
        this.codeDisplayGroup.append(this.disabledCodeDisplayRadio);
        this.disabledCodeDisplayRadio.children('input').click(function(){
            _this.cellDef.value.codeDisplay=false;
            setDirty();
        });
    }

    _initTypeSelect(){
        const imageSourceGroup=$(`<div class="form-group"><label>${window.i18n.property.zxing.source}</label></div>`);
        this.sourceSelect=$(`<select class="form-control" style="display: inline-block;width:295px">
            <option value="text">${window.i18n.property.zxing.text}</option>
            <option value="expression">${window.i18n.property.zxing.expr}</option>
        </select>`);
        imageSourceGroup.append(this.sourceSelect);
        const _this=this;
        this.sourceSelect.change(function(){
            const value=$(this).val();
            _this.cellDef.value.source=value;
            if(value==='text'){
                _this.textEditorGroup.show();
                _this.expressionEditorGroup.hide();
                _this.expandGroup.hide();
            }else{
                _this.expressionEditorGroup.show();
                _this.expandGroup.show();
                _this.textEditorGroup.hide();
            }
            setDirty();
        });
        this.container.append(imageSourceGroup);
    }

    _initTextEditor(){
        const _this=this;
        this.textEditorGroup=$(`<div><label>${window.i18n.property.zxing.text1}</label></div>`);
        this.textEditor=$(`<input class="form-control" style="display: inline-block;width: 325px;padding: 5px">`);
        this.textEditorGroup.append(this.textEditor);
        this.textEditor.change(function(){
            _this.cellDef.value.value=$(this).val();
            setDirty();
        });
        this.textEditorGroup.hide();
        this.container.append(this.textEditorGroup);
    }

    _initExpressionEditor(){
        this.expressionEditorGroup=$(`<div><label>${window.i18n.property.zxing.expr}</label></div>`);
        this.container.append(this.expressionEditorGroup);
        const editorContainer=$(`<div style="border: solid 1px #eeeeee;"></div>`);
        const codeEditor=$(`<textarea></textarea>`);
        editorContainer.append(codeEditor);
        this.expressionEditorGroup.append(editorContainer);
        const _this=this;
        setTimeout(function(){
            _this.codeMirror=CodeMirror.fromTextArea(codeEditor.get(0),{
                mode:'javascript',
                lineNumbers:true,
                gutters: ["CodeMirror-linenumbers", "CodeMirror-lint-markers"],
                lint: {
                    getAnnotations:_this._buildScriptLintFunction(),
                    async:true
                }
            });
            _this.codeMirror.setSize('auto','120px');
            _this.codeMirror.on('change',function(cm,changes){
                let expr=cm.getValue();
                _this.cellDef.value.value=expr;
                setDirty();
            });
            _this.expressionEditorGroup.hide();
            _this.container.hide();
        },100);
    }
    _buildExpand(){
        const _this=this;
        this.expandGroup=$(`<div class="form-group" style="margin-bottom: 10px;"><label>${window.i18n.property.zxing.expand}</label></div>`);
        this.downExpandRadio=$(`<label class="checkbox-inline" style="padding-left: 2px"><input type="radio" name="__expand_radio" value="Down">${window.i18n.property.zxing.down}</label>`);
        this.expandGroup.append(this.downExpandRadio);
        this.downExpandRadio.children('input').click(function(){
            _this._setExpand('Down');
        });
        this.rightExpandRadio=$(`<label class="checkbox-inline" style="padding-left: 2px"><input type="radio" name="__expand_radio" value="Right">${window.i18n.property.zxing.right}</label>`);
        this.expandGroup.append(this.rightExpandRadio);
        this.rightExpandRadio.children('input').click(function(){
            _this._setExpand('Right');
        });
        this.noneExpandRadio=$(`<label class="checkbox-inline" style="padding-left: 2px"><input type="radio" name="__expand_radio" value="None">${window.i18n.property.zxing.noneExpand}</label>`);
        this.expandGroup.append(this.noneExpandRadio);
        this.noneExpandRadio.children('input').click(function(){
            _this._setExpand('None');
        });
        this.container.append(this.expandGroup);
        this.expandGroup.hide();
    }

    _setExpand(expand){
        const hot=this.context.hot;
        for(let i=this.rowIndex;i<=this.row2Index;i++){
            for(let j=this.colIndex;j<=this.col2Index;j++){
                const cellDef=hot.context.getCell(i,j);
                if(!cellDef){
                    continue;
                }
                const type=cellDef.value.type;
                if(type==='dataset' || type==='expression' || type==='image' || type==='zxing'){
                    cellDef.expand=expand;
                }
            }
        }
        hot.render();
        setDirty();
    }

    show(cellDef,rowIndex,colIndex,row2Index,col2Index){
        this.cellDef=cellDef;
        this.rowIndex=rowIndex;
        this.colIndex=colIndex;
        this.row2Index=row2Index;
        this.col2Index=col2Index;
        this.container.show();
        const source=cellDef.value.source;
        this.widthEditor.val(cellDef.value.width);
        this.heightEditor.val(cellDef.value.height);
        this.sourceSelect.val(source);
        this.textEditor.val('');
        if(source==='text'){
            this.textEditorGroup.show();
            this.expressionEditorGroup.hide();
            this.textEditor.val(cellDef.value.value || '');
            this.expandGroup.hide();
        }else{
            this.expressionEditorGroup.show();
            this.textEditorGroup.hide();
            this.codeMirror.setValue(cellDef.value.expression.expr || '');
            this.expandGroup.show();
        }
        if(cellDef.value.category==='qrcode'){
            this.formatGroup.hide();
            //this.codeDisplayGroup.hide();
        }else{
            this.formatGroup.show();
            this.formatSelect.val(cellDef.value.format);
            /*this.codeDisplayGroup.show();
            if(cellDef.value.codeDisplay){
                this.enabledCodeDisplayRadio.children('input').trigger('click');
            }else{
                this.disabledCodeDisplayRadio.children('input').trigger('click');
            }*/
        }
        const expand=cellDef.expand;
        if(expand==='None'){
            this.noneExpandRadio.trigger('click');
        }else if(expand==='Down'){
            this.downExpandRadio.trigger('click');
        }else if(expand==='Right'){
            this.rightExpandRadio.trigger('click');
        }
    }
    hide(){
        this.container.hide();
    }
}