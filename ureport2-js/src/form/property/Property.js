/**
 * Created by Jacky.Gao on 2017-10-12.
 */
export default class Property{
    constructor(){
        this.propertyContainer=$("<div class='row'>");
        this.col=$("<div class='col-md-12'>");
        this.propertyContainer.append(this.col);
    }
    buildOptionsInlineGroup(){
        const inlineGroup=$("<div class='form-group'><label class='control-label'>选项换行显示</label></div>");
        this.optionsInlineSelect=$("<select class='form-control'>");
        this.optionsInlineSelect.append($("<option value='0'>是</option>"));
        this.optionsInlineSelect.append($("<option value='1'>否</option>"));
        inlineGroup.append(this.optionsInlineSelect);
        const self=this;
        this.optionsInlineSelect.change(function(){
            let value=false;
            if($(this).val()==="1"){
                value=true;
            }
            self.current.setOptionsInline(value);
        });
        return inlineGroup;
    }
    buildDefaultValueGroup(){
        const group=$("<div class='form-group'><label>默认值</label></div>");
        const exprLink=$("<a href='###'>(支持表达式)</a>");
        exprLink.click(function(){
            new ExpressionValueDialog();
        });
        group.append(exprLink);
        this.defaultValueText=$("<input type='text' placeholder='请输入默认值...' class='form-control'>");
        group.append(this.defaultValueText);
        const self=this;
        this.defaultValueText.change(function(){
            self.current.defaultValue=$(this).val();
        });
        return group;
    }
    buildVisibleGroup(){
        const group=$("<div class='form-group'><label>是否可见</label></div>");
        const exprLink=$("<a href='###'>(true是,false否,支持表达式)</a>");
        exprLink.click(function(){
            new ExpressionValueDialog();
        });
        group.append(exprLink);
        this.visibleText=$("<input type='text' class='form-control'>");
        group.append(this.visibleText);
        const self=this;
        this.visibleText.change(function(){
            if(self.currentButton){
                self.currentButton.visible=$(this).val();
            }else{
                self.current.visible=$(this).val();
            }
        });
        return group;
    }
    buildEnableGroup(){
        const group=$("<div class='form-group'><label>是否可操作</label></div>");
        const exprLink=$("<a href='###'>(true是,false否,支持表达式)</a>");
        exprLink.click(function(){
            new ExpressionValueDialog();
        });
        group.append(exprLink);
        this.enableText=$("<input type='text' class='form-control'>");
        group.append(this.enableText);
        const self=this;
        this.enableText.change(function(){
            if(self.currentButton){
                self.currentButton.enable=$(this).val();
            }else{
                self.current.enable=$(this).val();
            }
        });
        return group;
    }
    buildBindTableGroup(){
        const group=$("<div class='form-group'><label>要绑定的表</label></div>");
        const bindTableRadioName="bind_table_type";
        const masterOption=$("<label class='checkbox-inline'>");
        this.bindMasterRadio=$("<input type='radio' name='"+bindTableRadioName+"' checked>");
        masterOption.append(this.bindMasterRadio);
        masterOption.append("主表");
        group.append(masterOption);
        const self=this;
        this.bindMasterRadio.change(function(){
            const checked=$(this).prop("checked");
            if(checked){
                self.current.bindMaster=true;
                var table=formBuilder.bindTable;
                self.current.bindTableId=table.id;
                self.current.bindTableName=table.name;
                self.bindTable=table;
                self.bindSlaveTableGroup.hide();
                self.buildBindFieldSelect();
            }
        });
        const slaveOption=$("<label class='checkbox-inline'>")
        this.bindSlaveRadio=$("<input type='radio' name='"+bindTableRadioName+"'>");
        slaveOption.append(this.bindSlaveRadio);
        slaveOption.append("子表");
        group.append(slaveOption);
        this.bindSlaveRadio.change(function(){
            const checked=$(this).prop("checked");
            if(checked){
                self.current.bindMaster=false;
                self.bindSlaveTableGroup.show();
                self.buildBindTableSelect();
            }
        });
        return group;
    }
    buildBindSlaveTableSelectGroup(){
        const self=this;
        this.bindSlaveTableGroup=$("<div class='form-group'>");
        this.bindSlaveTableGroup.append($("<label>要绑定的子表</label>"));
        this.bindTableSelect=$("<select class='form-control'>");
        this.buildBindTableSelect();
        this.bindTableSelect.change(function(){
            const slaveTableId=$(this).val();
            if(slaveTableId===""){
                return;
            }
            self.current.bindTableId=slaveTableId;
            var slaveBindTables=formBuilder.bindTable.slaveBindTables || [];
            for(var i=0;i<slaveBindTables.length;i++){
                var slaveBindTable=slaveBindTables[i];
                if(slaveTableId===slaveBindTable.id){
                    self.current.bindTableName=slaveBindTable.name;
                    break;
                }
            }
            self.buildBindFieldSelect();
        });
        this.bindSlaveTableGroup.append(this.bindTableSelect);
        this.bindSlaveTableGroup.hide();
        if(tabEnable){
            return this.bindSlaveTableGroup;
        }else{
            this.col.append(this.bindSlaveTableGroup);
        }
    }
    buildBindTableSelect(){
        this.bindTableSelect.empty();
        const bindTable=formBuilder.bindTable || {};
        const slaveBindTables=bindTable.slaveBindTables || [];
        for(let i=0;i<slaveBindTables.length;i++){
            const slaveBindTable=slaveBindTables[i];
            const id=slaveBindTable.id;
            const desc=slaveBindTable.desc || slaveBindTable.name;
            this.bindTableSelect.append($("<option value='"+id+"'>"+desc+"</option>"));
        }
        this.bindTableSelect.append($("<option value=''></option>"));
        this.bindTableSelect.val("");
    }
    buildBindFieldGroup(){
        const group=$("<div class='form-group'><label>要绑定的字段</label></div>");
        this.bindFieldSelect=$("<select class='form-control'>");
        group.append(this.bindFieldSelect);
        const self=this;
        this.bindFieldSelect.change(function(){
            const select=$(this);
            const label=select.find("option:selected").text();
            const value=select.val();
            self.current.setBindField(value,label);
            self.textLabel.val(label);
            self.current.setLabel(label);
            if(value===self.bindFieldSelect.primaryKey){
                self.current.primaryKey=true;
            }else{
                self.current.primaryKey=false;
            }
        });
        return group;
    }
    buildBindFieldSelect(){
        this.bindFieldSelect.empty();
        const masterTable=formBuilder.bindTable;
        let table=masterTable;
        if(!this.current.bindMaster){
            const bindTableId=this.current.bindTableId;
            const slaveBindTables=masterTable.slaveBindTables || [];
            $.each(slaveBindTables,function(i,slaveTable){
                if(slaveTable.id===bindTableId){
                    table=slaveTable;
                    return false;
                }
            });
        }
        if(table && table.fields){
            const primaryKeyType=table.primaryKeyType;
            const fields=table.fields;
            for(var i=0;i<fields.length;i++){
                const field=fields[i];
                if((this.current instanceof DatetimeInstance) && field.fieldType!=="Date"){
                    continue;
                }
                const name=field.name;
                if(!(this.current instanceof DataLabelInstance) && field.primaryKey && primaryKeyType!=="Input"){
                    continue;
                }
                if(field.primaryKey){
                    this.bindFieldSelect.primaryKey=name;
                }
                let label=field.label || name;
                if(field.dummy){
                    label+="(dummy)";
                }
                this.bindFieldSelect.append($("<option value='"+name+"'>"+label+"</option>"));
            }
            this.bindFieldSelect.append($("<option value=''></option>"));
            this.bindFieldSelect.val("");
        }
    }
    buildLabelGroup(){
        const labelGroup=$("<div class='form-group'>");
        const labelLabel=$("<label>标题</label>");
        labelGroup.append(labelLabel);
        this.textLabel=$("<input type='text' class='form-control'>");
        const self=this;
        this.textLabel.change(function(){
            self.current.setLabel($(this).val());
        });
        labelGroup.append(this.textLabel);
        return labelGroup;
    }
    buildPositionLabelGroup(){
        const positionLabelGroup=$("<div class='form-group'>");
        const positionLabel=$("<label class='control-label'>标题位置</label>");
        positionLabelGroup.append(positionLabel);
        this.positionLabelSelect=$("<select class='form-control'>");
        positionLabelGroup.append(this.positionLabelSelect);
        this.positionLabelSelect.append("<option value='top' selected>上边</option>");
        this.positionLabelSelect.append("<option value='left'>左边</option>");
        const self=this;
        this.positionLabelSelect.change(function(){
            self.current.setLabelPosition($(this).val());
        });
        return positionLabelGroup;
    }
    buildStringLengthGroup(){
        const group=$("<div class='form-group'></div>");
        this.stringLengthCheckbox=$("<input type='checkbox'><span>输入长度限制</span>");
        group.append(this.stringLengthCheckbox);
        const self=this;
        this.stringLengthCheckbox.change(function(){
            const value=$(this).prop("checked");
            if(value){
                self.minLengthText.prop("disabled",false);
                self.maxLengthText.prop("disabled",false);
                self.current.stringLength=true;
            }else{
                self.minLengthText.prop("disabled",true);
                self.maxLengthText.prop("disabled",true);
                self.current.stringLength=false;
            }
        });
        const minLengthGroup=$("<div class='form-group' style='margin-left: 15px'><label>最少字符数</label></div>");
        this.minLengthText=$("<input type='number' class='form-control' title='输入一个数字'>");
        minLengthGroup.append(this.minLengthText);
        group.append(minLengthGroup);
        this.minLengthText.change(function(){
            self.current.minStringLength=$(this).val();
        });

        const maxLengthGroup=$("<div class='form-group'  style='margin-left: 15px'><label>最多字符数</label></div>");
        this.maxLengthText=$("<input type='number' class='form-control' title='输入一个数字'>");
        maxLengthGroup.append(this.maxLengthText);
        group.append(maxLengthGroup);
        this.maxLengthText.change(function(){
            self.current.maxStringLength=$(this).val();
        });
        return group;
    }
    buildRequiredGroup(){
        const requiredGroup=$("<div class='form-group'><label>是否必填</label></div>");
        this.requiredSelect=$("<select class='form-control'>");
        this.requiredSelect.append($("<option value='0'>否</option>"));
        this.requiredSelect.append($("<option value='1'>是</option>"));
        const self=this;
        this.requiredSelect.change(function(){
            let value=false;
            if($(this).val()==="1"){
                value=true;
            }
            self.current.setRequired(value);
        });
        requiredGroup.append(this.requiredSelect);
        return requiredGroup;
    }
    refreshValue(instance){
        this.current=instance;
        if(this.bindSlaveTableGroup){
            if(instance.bindMaster){
                this.bindSlaveTableGroup.hide();
                this.bindMasterRadio.prop("checked",true);
            }else{
                this.bindSlaveRadio.prop("checked",true);
                this.bindSlaveTableGroup.show();
                this.buildBindTableSelect();
                this.bindTableSelect.val(instance.bindTableId);
            }
        }
        if(this.optionsInlineSelect){
            if(instance.optionsInline){
                this.optionsInlineSelect.val("1");
            }else{
                this.optionsInlineSelect.val("0");
            }
        }
        if(this.defaultValueText){
            this.defaultValueText.val(instance.defaultValue);
        }

        if(this.requiredSelect){
            if(instance.isRequired){
                this.requiredSelect.val("1");
            }else{
                this.requiredSelect.val("0");
            }
        }
        if(this.stringLengthCheckbox){
            if(instance.stringLength){
                this.stringLengthCheckbox.prop("checked",true);
                this.minLengthText.prop("disabled",false);
                this.maxLengthText.prop("disabled",false);
                this.minLengthText.val(instance.minStringLength);
                this.maxLengthText.val(instance.maxStringLength);
            }else{
                this.stringLengthCheckbox.prop("checked",false);
                this.minLengthText.prop("disabled",true);
                this.maxLengthText.prop("disabled",true);
                this.minLengthText.val("");
                this.maxLengthText.val("");
            }
        }
        if(this.enableText){
            this.enableText.val(instance.enable);
        }
        if(this.visibleText){
            this.visibleText.val(instance.visible);
        }
        this.positionLabelSelect.val(instance.labelPosition);
        this.textLabel.val(instance.label);
        this.buildBindFieldSelect();
        if(this.bindFieldSelect){
            this.bindFieldSelect.val(instance.bindField || "");
        }
    }
}