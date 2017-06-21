/**
 * Created by Jacky.Gao on 2017-03-01.
 */
import {setDirty} from '../../Utils.js';
import PropertyConditionDialog from '../../dialog/PropertyConditionDialog.js';

export default class BaseValueEditor{
    _buildFillBlankRows(container){
        this.fillGroup=$(`<div class="form-group" style="margin-bottom: 10px;height: 25px;"><label>补充空白行：</label></div>`);
        this.enableFillRadio=$(`<label class="checkbox-inline" style="padding-left: 2px"><input type="radio" name="__fill_blank_row_radio" value="default">打开</label>`);
        this.fillGroup.append(this.enableFillRadio);
        this.disableFillRadio=$(`<label class="checkbox-inline" style="padding-left: 2px"><input type="radio" name="__fill_blank_row_radio" value="custom">关闭</label>`);
        this.fillGroup.append(this.disableFillRadio);
        if(container){
            container.append(this.fillGroup);
        }else{
            this.container.append(this.fillGroup);
        }
        const _this=this;
        this.enableFillRadio.children('input').click(function(){
            _this._setFillBlankRows(true);
            _this.multipleGroup.show();
        });
        this.disableFillRadio.children('input').click(function(){
            _this._setFillBlankRows(false);
            _this.multipleGroup.hide();
        });
        this.multipleGroup=$(`<span style="margin-left: 10px">数据行倍数：</span>`);
        this.fillGroup.append(this.multipleGroup);
        this.multipleEditor=$(`<input type="number" class="form-control" style="display: inline-block;width: 77px;height: 25px;padding: 3px;font-size: 12px">`);
        this.multipleGroup.append(this.multipleEditor);
        this.multipleEditor.change(function(){
            const value=$(this).val();
            for(let i=_this.rowIndex;i<=_this.row2Index;i++){
                for(let j=_this.colIndex;j<=_this.col2Index;j++){
                    const cellDef=_this.context.getCell(i,j);
                    if(!cellDef){
                        continue;
                    }
                    cellDef.multiple=value;
                }
            }
            setDirty();
        });
    }

    _setFillBlankRows(value){
        if(this.initialized){
            return;
        }
        for(let i=this.rowIndex;i<=this.row2Index;i++){
            for(let j=this.colIndex;j<=this.col2Index;j++){
                const cellDef=this.context.getCell(i,j);
                if(!cellDef){
                    continue;
                }
                cellDef.fillBlankRows=value;
                if(!cellDef.multiple){
                    cellDef.multiple=0;
                }
            }
        }
        setDirty();
    }

    _buildWrapCompute(container){
        this.wrapGroup=$(`<div class="form-group" style="margin-bottom: 10px"><label>换行计算：</label></div>`);
        this.enableWrapComput=$(`<label class="checkbox-inline" style="padding-left: 2px"><input type="radio" name="__wrap_compute_radio" value="default" title="打开换行计算将耗费更多报表计算时间">打开</label>`);
        this.wrapGroup.append(this.enableWrapComput);
        this.disableWrapComput=$(`<label class="checkbox-inline" style="padding-left: 2px"><input type="radio" name="__wrap_compute_radio" value="custom">关闭</label>`);
        this.wrapGroup.append(this.disableWrapComput);
        if(container){
            container.append(this.wrapGroup);
        }else{
            this.container.append(this.wrapGroup);
        }
        const _this=this;
        this.enableWrapComput.children('input').click(function(){
            _this._setWrapCompute(true);
        });
        this.disableWrapComput.children('input').click(function(){
            _this._setWrapCompute(false);
        });
    }

    _setWrapCompute(wrapCompute){
        if(this.initialized){
            return;
        }
        for(let i=this.rowIndex;i<=this.row2Index;i++){
            for(let j=this.colIndex;j<=this.col2Index;j++){
                const cellDef=this.context.getCell(i,j);
                if(!cellDef){
                    continue;
                }
                cellDef.cellStyle.wrapCompute=wrapCompute;
            }
        }
        setDirty();
    }

    _buildFormat(container){
        const _this=this;
        this.formatGroup=$(`<div class="form-group" style="margin-bottom:10px;"><label>格式化：</label></div>`);
        this.formatEditor=$(`<input type="text" class="form-control" style="display: inline-block;width: 302px;padding: 3px;font-size: 12px;height: 25px;" placeholder="对单元格内日期或数字格式化">`);
        this.formatGroup.append(this.formatEditor);
        this.formatEditor.completer({
            source: [
                "yyyy/MM/dd",
                "yyyy/MM",
                "yyyy-MM",
                "yyyy",
                "yyyy-MM-dd HH:mm:ss",
                "yyyy年MM月dd日 HH:mm:ss",
                "yyyy-MM-dd",
                "yyyy年MM月dd日",
                "HH:mm",
                "HH:mm:ss",
                "#.##",
                "#.00",
                "##.##%",
                "##.00%",
                "##,###.##",
                "￥##,###.##",
                "$##,###.##",
                "0.00E00",
                "##0.0E0"
            ],
            complete:function(){
                let value=_this.formatEditor.val();
                _this._setFormat(value);
            },
            suggest: true,
            zIndex:1200
        });
        if(container){
            container.append(this.formatGroup);
        }else{
            this.container.append(this.formatGroup);
        }
        this.formatEditor.change(function(){
            _this._setFormat($(this).val());
        });
    }

    _buildConditionProperty(container){
        const _this=this;
        const group=$(`<div class="form-group" style="margin-bottom: 10px"><label>条件属性：</label></div>`);
        if(container){
            container.append(group);
        }else{
            this.container.append(group);
        }
        const configButton=$(`<button type="button" class="btn btn-default" style="font-size: 12px;height: 25px;padding: 4px;10px;"><i class="glyphicon glyphicon-filter"></i> 配置条件</button>`);
        group.append(configButton);
        const propertyConditionDialog=new PropertyConditionDialog();
        configButton.click(function(){
            const conditionPropertyItems=_this.cellDef.conditionPropertyItems || [];
            if(!_this.cellDef.conditionPropertyItems){
                _this.cellDef.conditionPropertyItems=conditionPropertyItems;
            }
            let datasetName='';
            if(_this.datasetSelect){
                datasetName=_this.datasetSelect.val();
                propertyConditionDialog.show(_this.datasources,datasetName,conditionPropertyItems);
            }else{
                const expr=_this.codeMirror.getValue();
                if(expr && expr!==''){
                    const url=window._server+'/designer/parseDatasetName';
                    $.ajax({
                        url,
                        type:'POST',
                        data:{expr},
                        success:function(result){
                            datasetName=result.datasetName;
                            propertyConditionDialog.show(_this.datasources,datasetName,conditionPropertyItems);
                        },
                        error:function(){
                            propertyConditionDialog.show(_this.datasources,datasetName,conditionPropertyItems);
                        }
                    });
                }
            }
        });
    }

    _setFormat(format){
        if(this.initialized){
            return;
        }
        for(let i=this.rowIndex;i<=this.row2Index;i++){
            for(let j=this.colIndex;j<=this.col2Index;j++){
                const cellDef=this.context.getCell(i,j);
                if(!cellDef){
                    continue;
                }
                cellDef.cellStyle.format=format;
            }
        }
        setDirty();
    }

    _buildScriptLintFunction(){
        return function (text, updateLinting, options, editor){
            if(text===''){
                updateLinting(editor,[]);
                return;
            }
            if(!text || text===''){
                return;
            }
            const url=window._server+'/designer/scriptValidation';
            $.ajax({
                url,
                data:{content:text},
                type:'POST',
                success:function(result){
                    if(result){
                        for(let item of result){
                            item.from={line:item.line-1};
                            item.to={line:item.line-1};
                        }
                        updateLinting(editor,result);
                    }else{
                        updateLinting(editor,[]);
                    }
                },
                error:function(){
                    alert('语法检查操作失败！');
                }
            });
        };
    }
}