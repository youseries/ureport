/**
 * Created by Jacky.Gao on 2017-10-20.
 */
import Property from './Property.js';
export default class SelectProperty extends Property{
    constructor(report){
        super();
        this.col.append(this.buildBindParameter());
        this.positionLabelGroup=this.buildPositionLabelGroup();
        this.col.append(this.positionLabelGroup);
        this.col.append(this.buildLabelGroup());
        this.optionFormGroup=$("<div class='form-group'>");
        this.col.append(this.optionFormGroup);
    }
    refreshValue(editor){
        super.refreshValue(editor);
        this.optionFormGroup.empty();
        const group=$(`<div class="form-group"><label>数据来源</label></div>`);
        const datasourceSelect=$(`<select class="form-control">
            <option value="dataset">数据集</option>
            <option value="simple">固定值</option>
        </select>`);
        group.append(datasourceSelect);
        this.optionFormGroup.append(group);
        this.simpleOptionGroup=$(`<div class="form-group"></div>`);
        this.optionFormGroup.append(this.simpleOptionGroup);
        this.datasetGroup=$(`<div class="form-group"></div>`);
        this.optionFormGroup.append(this.datasetGroup);
        const _this=this;
        datasourceSelect.change(function(){
            if($(this).val()==='dataset'){
                editor.useDataset=true;
                _this.datasetGroup.show();
                _this.simpleOptionGroup.hide();
            }else{
                editor.useDataset=false;
                _this.datasetGroup.hide();
                _this.simpleOptionGroup.show();
            }
        });
        const datasetGroup=$(`<div class="form-group"><label>数据集</label></div>`);
        this.datasetGroup.append(datasetGroup);
        const datasetSelect=$(`<select class="form-control"></select>`);
        datasetGroup.append(datasetSelect);
        let dsName=null;
        for(let datasetName of formBuilder.datasetMap.keys()){
            datasetSelect.append(`<option>${datasetName}</option>`);
            dsName=datasetName;
        }
        if(editor.dataset){
            dsName=editor.dataset;
        }else{
            editor.dataset=dsName;
        }
        datasetSelect.val(dsName);
        let fields=formBuilder.datasetMap.get(dsName);
        if(!fields)fields=[];
        const labelGroup=$(`<div class="form-group"><label>显示值字段名</label></div>`);
        this.datasetGroup.append(labelGroup);
        const labelSelect=$(`<select class="form-control"></select>`);
        labelGroup.append(labelSelect);
        const valueGroup=$(`<div class="form-group"><label>实际值字段名</label></div>`);
        this.datasetGroup.append(valueGroup);
        const valueSelect=$(`<select class="form-control"></select>`);
        labelSelect.change(function(){
            editor.labelField=$(this).val();
        });
        valueSelect.change(function(){
            editor.valueField=$(this).val();
        });
        let targetField=null;
        for(let field of fields){
            labelSelect.append(`<option>${field.name}</option>`);
            valueSelect.append(`<option>${field.name}</option>`);
            targetField=field.name;
        }
        datasetSelect.change(function () {
            const dsName=$(this).val();
            if(!dsName){
                return;
            }
            editor.dataset=dsName;
            labelSelect.empty();
            valueSelect.empty();
            fields=formBuilder.datasetMap.get(dsName);
            if(!fields)fields=[];
            for(let field of fields){
                labelSelect.append(`<option>${field.name}</option>`);
                valueSelect.append(`<option>${field.name}</option>`);
                targetField=field.name;
            }
            editor.labelField=targetField;
            editor.valueField=targetField;
            labelSelect.val(targetField);
            valueSelect.val(targetField);
        });
        if(editor.labelField){
            targetField=editor.labelField;
        }else{
            editor.labelField=targetField;
        }
        labelSelect.val(targetField);
        if(editor.valueField){
            targetField=editor.valueField;
        }else{
            editor.valueField=targetField;
        }
        valueSelect.val(targetField);
        valueGroup.append(valueSelect);
        if(editor.useDataset){
            datasourceSelect.val('dataset');
            this.datasetGroup.show();
            this.simpleOptionGroup.hide();
        }else{
            this.datasetGroup.hide();
            this.simpleOptionGroup.show();
            datasourceSelect.val('simple');
        }
        this.simpleOptionGroup.append($("<label>固定值选项(若显示值与实际值不同，则用“,”分隔，如“是,true”等)</label>"));
        var self=this;
        $.each(editor.options,function(index,option){
            self.addOptionEditor(option);
        });
    }
    addOptionEditor(option){
        var inputGroup=$("<div class='input-group'>");
        var input=$("<input class='form-control' type='text'>");

        if(option.label===option.value){
            input.val(option.label);
        }else{
            input.val(option.label+","+option.value);
        }

        input.change(function(){
            var value=$(this).val();
            var json={value:value,label:value};
            var array=value.split(",");
            if(array.length==2){
                json.label=array[0];
                json.value=array[1];
            }
            option.setValue(json);
        });
        inputGroup.append(input);
        var addon=$("<span class='input-group-addon'>");
        inputGroup.append(addon);
        var self=this;
        var del=$("<span class='pb-icon-delete'><li class='glyphicon glyphicon-trash'></li></span>");
        del.click(function(){
            if(self.current.options.length===1){
                bootbox.alert("至少要保留一个列表选项!");
                return;
            }
            self.current.removeOption(option);
            inputGroup.remove();
        });
        addon.append(del);
        var add=$("<span class='pb-icon-add' style='margin-left: 10px'><li class='glyphicon glyphicon-plus'></span>");
        add.click(function(){
            var newOption=self.current.addOption();
            self.addOptionEditor(newOption);
        });
        addon.append(add);
        this.simpleOptionGroup.append(inputGroup);
    }
}