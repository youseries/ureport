/**
 * Created by Jacky.Gao on 2017-02-07.
 */
import CodeMirror from 'codemirror';
import '../../../node_modules/codemirror/addon/hint/show-hint.js';
import '../../../node_modules/codemirror/addon/lint/lint.js';
import {setDirty} from '../../Utils.js';
import BaseValueEditor from './BaseValueEditor.js';

export default class ImageValueEditor extends BaseValueEditor{
    constructor(parentContainer,context){
        super();
        this.context=context;
        this.container=$(`<div></div>`);
        parentContainer.append(this.container);
        this._initSizeGroup();
        this._initTypeSelect();
        this._buildExpand();
        this._initExpressionEditor();
        this._initPathEditor();
    }
    _initSizeGroup(){
        const _this=this;
        const widthGroup=$(`<div class="form-group"><label>宽：</label></div>`);
        this.widthEditor=$(`<input class="form-control" type="number" placeholder="为0或空表示采用图片默认宽度" style="display: inline-block;width:335px">`);
        widthGroup.append(this.widthEditor);
        this.widthEditor.change(function(){
            _this.cellDef.value.width=$(this).val();
        });
        this.container.append(widthGroup);
        const heightGroup=$(`<div class="form-group"><label>高：</label></div>`);
        this.heightEditor=$(`<input class="form-control" type="number" placeholder="为0或空表示采用图片默认高度" style="display: inline-block;width:335px">`);
        heightGroup.append(this.heightEditor);
        this.heightEditor.change(function(){
            _this.cellDef.value.height=$(this).val();
        });
        this.container.append(heightGroup);
    }
    _initTypeSelect(){
        const imageSourceGroup=$(`<div class="form-group"><label>${window.i18n.property.image.source}</label></div>`);
        this.sourceSelect=$(`<select class="form-control" style="display: inline-block;width:295px">
            <option value="text">${window.i18n.property.image.path}</option>
            <option value="expression">${window.i18n.property.image.expr}</option>
        </select>`);
        imageSourceGroup.append(this.sourceSelect);
        const _this=this;
        this.sourceSelect.change(function(){
            const value=$(this).val();
            _this.cellDef.value.source=value;
            if(value==='text'){
                _this.pathEditorGroup.show();
                _this.expressionEditorGroup.hide();
                _this.expandGroup.hide();
            }else{
                _this.expressionEditorGroup.show();
                _this.expandGroup.show();
                _this.pathEditorGroup.hide();
            }
            setDirty();
        });
        this.container.append(imageSourceGroup);
    }

    _initPathEditor(){
        const _this=this;
        this.pathEditorGroup=$(`<div><label>${window.i18n.property.image.p}</label></div>`);
        this.pathEditor=$(`<input class="form-control" title="${window.i18n.property.image.tip}" placeholder="${window.i18n.property.image.tip}" style="display: inline-block;width: 325px;padding: 5px">`);
        this.pathEditorGroup.append(this.pathEditor);
        this.pathEditor.change(function(){
            _this.cellDef.value.value=$(this).val();
            setDirty();
        });
        this.pathEditorGroup.hide();
        this.container.append(this.pathEditorGroup);
    }

    _initExpressionEditor(){
        this.expressionEditorGroup=$(`<div><label>${window.i18n.property.image.expr}</label></div>`);
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
                if(_this.initialize){
                    return;
                }
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
        this.expandGroup=$(`<div class="form-group" style="margin-bottom: 10px;"><label>${window.i18n.property.image.expand}</label></div>`);
        this.downExpandRadio=$(`<label class="checkbox-inline" style="padding-left: 2px"><input type="radio" name="__expand_radio" value="Down">${window.i18n.property.image.down}</label>`);
        this.expandGroup.append(this.downExpandRadio);
        this.downExpandRadio.children('input').click(function(){
            _this._setExpand('Down');
        });
        this.rightExpandRadio=$(`<label class="checkbox-inline" style="padding-left: 2px"><input type="radio" name="__expand_radio" value="Right">${window.i18n.property.image.right}</label>`);
        this.expandGroup.append(this.rightExpandRadio);
        this.rightExpandRadio.children('input').click(function(){
            _this._setExpand('Right');
        });
        this.noneExpandRadio=$(`<label class="checkbox-inline" style="padding-left: 2px"><input type="radio" name="__expand_radio" value="None">${window.i18n.property.image.noneExpand}</label>`);
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
                if(type==='dataset' || type==='expression' || type==='image'){
                    cellDef.expand=expand;
                }
            }
        }
        hot.render();
        setDirty();
    }

    show(cellDef,rowIndex,colIndex,row2Index,col2Index){
        this.initialize=true;
        this.cellDef=cellDef;
        this.rowIndex=rowIndex;
        this.colIndex=colIndex;
        this.row2Index=row2Index;
        this.col2Index=col2Index;
        this.container.show();
        this.widthEditor.val(cellDef.value.width);
        this.heightEditor.val(cellDef.value.height);
        const source=cellDef.value.source;
        this.sourceSelect.val(source);
        this.pathEditor.val('');
        this.codeMirror.setValue('');
        if(source==='text'){
            this.pathEditorGroup.show();
            this.expressionEditorGroup.hide();
            this.pathEditor.val(cellDef.value.value || '');
        }else{
            this.expressionEditorGroup.show();
            this.pathEditorGroup.hide();
            this.codeMirror.setValue(cellDef.value.value || '');
        }
        const expand=cellDef.expand;
        if(expand==='None'){
            this.noneExpandRadio.trigger('click');
        }else if(expand==='Down'){
            this.downExpandRadio.trigger('click');
        }else if(expand==='Right'){
            this.rightExpandRadio.trigger('click');
        }
        this.initialize=false;
    }
    hide(){
        this.container.hide();
    }
}