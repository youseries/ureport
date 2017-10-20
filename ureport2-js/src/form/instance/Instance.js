/**
 * Created by Jacky.Gao on 2017-10-12.
 */
export default class Instance{
    constructor(){
        this.bindMaster=true;
        this.isRequired=false;
        this.isDigits=false;
        this.isNumeric=false;
        this.isEmail=false;
        this.isDate=false;
        this.isCheckbox=false;
        this.checkboxMin=1;
        this.checkboxMax=1;
        this.stringLength=false;
        this.minStringLength=1;
        this.maxStringLength=60;
        this.labelPosition=Instance.TOP;
        this.enable="true";
        this.visible="true";
    }
    newElement(label){
        this.element=$("<div class='form-group row' style='margin:0px'>");
        this.label=label;
        this.labelElement=$("<label class='control-label'>");
        this.element.append(this.labelElement);
        this.labelElement.text(label);
        return this.element;
    }
    setLabel(label){
        this.label=label;
        if(this.isRequired){
            this.labelElement.html(this.label+"<span style='color:red'>*</span>");
        }else{
            this.labelElement.html(this.label);
        }
    }
    setLabelPosition(position){
        if(this.labelPosition===position){
            return;
        }
        this.labelPosition=position;
        if(position===Instance.TOP){
            this.labelElement.removeClass(Instance.POS_CLASSES[0]);
            this.inputElement.removeClass(Instance.POS_CLASSES[1]);
        }else if(position===Instance.LEFT){
            this.labelElement.addClass(Instance.POS_CLASSES[0]);
            this.inputElement.addClass(Instance.POS_CLASSES[1]);
        }
    }
    setBindField(field,fieldLabel){
        this.bindField=field;
        if(fieldLabel && fieldLabel!==field){
            this.setLabel(fieldLabel);
        }
    }
    setRequired(required){
        if(this.isRequired===required){
            return;
        }
        this.isRequired=required;
        if(required){
            this.labelElement.html(this.label+"<span style='color:red'>*</span>");
        }else{
            this.labelElement.html(this.label);
        }
    }
    getElementId(){
        if(UPage.binding){
            if(!this.bindTableName){
                this.bindTableName=formBuilder.bindTable.name;
            }
            if(this.bindTableName && this.bindField){
                return this.bindTableName+"."+this.bindField;
            }
            return null;
        }else{
            return this.label;
        }
    }
    fromJson(){
        this.bindMaster=json.bindMaster;
        this.bindTableId=json.bindTableId;
        this.bindTableName=json.bindTableName;
        this.bindField=json.bindField;
        this.setLabel(json.label);
        this.setRequired(json.required);
        this.setLabelPosition(json.labelPosition);
        this.isDigits=json.digits;
        this.isNumeric=json.numeric;
        this.isEmail=json.email;
        this.isDate=json.date;
        this.isCheckbox=json.checkbox;
        this.checkboxMin=json.checkboxMin;
        this.checkboxMax=json.checkboxMax;
        this.stringLength=json.stringLength;
        this.minStringLength=json.minStringLength;
        this.maxStringLength=json.maxStringLength;
        this.primaryKey=json.primaryKey;
        this.defaultValue=json.defaultValue;
        this.enable=json.enable;
        this.visible=json.visible;
    }
    toJSON(){
        const json={
            id:this.id,
            bindMaster:this.bindMaster,
            bindTableId:this.bindTableId || formBuilder.bindTable.id,
            bindTableName:this.bindTableName || formBuilder.bindTable.name,
            bindField:this.bindField,
            required:this.isRequired || false,
            label:this.label,
            labelPosition:this.labelPosition,
            defaultValue:this.defaultValue,
            enable:this.enable,
            visible:this.visible
        };
        if(UPage.binding){
            json.bindField=this.bindField;
        }
        if(this.isRequired){
            json.required=true;
        }
        if(this.primaryKey){
            json.primaryKey=true;
        }
        if(this.stringLength){
            json.stringLength=true;
            json.minStringLength=this.minStringLength;
            json.maxStringLength=this.maxStringLength;
        }
        if(this.isDigits){
            json.digits=true;
        }
        if(this.isNumeric){
            json.numeric=true;
        }
        if(this.isEmail){
            json.email=true;
        }
        if(this.isDate){
            json.date=true;
        }
        if(this.isCheckbox){
            json.checkbox=true;
        }
        if(this.checkboxMin!==1){
            json.checkboxMin=this.checkboxMin;
        }
        if(this.checkboxMax!==1){
            json.checkboxMax=this.checkboxMax;
        }
        var validators=this.buildValidators();
        if(validators){
            json.validators=validators;
        }
        return json;
    }
    initFromJson(json){

    }
    buildValidators(){
        let validators="";
        if(this.isRequired){
            validators="notEmpty:{message:'值不能为空.'}";
        }
        if(this.stringLength){
            if(validators!==""){
                validators+=",";
            }
            validators+="stringLength:{min:"+this.minStringLength+",max:"+this.maxStringLength+",message:'只能输入"+this.minStringLength+"~"+this.maxStringLength+"个字符.'}";
        }
        if(this.primaryKey && (this instanceof TextInstance)){
            //unique validate
            if(validators!==""){
                validators+=",";
            }
            if(!this.bindTableName || this.bindTableName===""){
                this.bindTableName=formBuilder.bindTable.name;
            }
            var url=formBuilder.server+"/upage/form_data_unique_check?bindTableName="+this.bindTableName+"&bindField="+this.bindField+"";
            validators+="remote:{message:'数据库中已存在当前值',url:'"+url+"'}";
        }
        if(this.isDigits){
            if(validators!==""){
                validators+=",";
            }
            validators+="digits:{message:'整数不合法.'}";
        }
        if(this.isNumeric){
            if(validators!==""){
                validators+=",";
            }
            validators+="numeric:{message:'数字不合法.'}";
        }
        if(this.isEmail){
            if(validators!==""){
                validators+=",";
            }
            validators+="emailAddress:{message:'Email地址不合法.'}"
        }
        /*    if(this.isDate){
         if(validators!==""){
         validators+=",";
         }
         validators+="date:{message:'日期不合法.'";
         if(this.dateFormat){
         validators+=",format:'"+this.dateFormat+"'";
         }
         validators+="}";
         }*/
        if(this.isCheckbox){
            if(validators!==""){
                validators+=",";
            }
            var msg="";
            if(this.checkboxMin===this.checkboxMax){
                msg="请选择"+this.checkboxMin+"个选项.";
            }else{
                msg="请选择"+this.checkboxMin+"至"+this.checkboxMax+"个选项.";
            }
            validators+="chioce:{message:'"+msg+"',min:"+this.checkboxMin+",max:"+this.checkboxMax+"}";
        }
        var fieldName=this.bindField || this.label;
        if(validators!==""){
            fieldName=fieldName.replace(eval("/-/gi"),"_");
            var script=fieldName+":{validators: {"+validators+"}}";
            return script;
        }
        return null;
    }
}
Instance.LEFT="left";
Instance.TOP="top";
Instance.POS_CLASSES=["col-md-3","col-md-9"];