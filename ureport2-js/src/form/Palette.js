/**
 * Created by Jacky.Gao on 2017-10-12.
 */
export default class Palette{
    static componentId="pb_component_container_palette";
    static propertyId="pb_component_property_palette";
    constructor(){
        this.components=[];
        this.initContainer();
        this.initComponents();
    }
    initComponents(){
        this.addComponent(new ButtonGroupComponent({
            icon:"res/material/img/but.png",
            label:"按钮"
        }));
        /*
        this.addComponent(new TextComponent({
            icon:"res/material/img/textbox.png",
            label:"文本框"
        }));
        this.addComponent(new TableInputComponent({
            icon:"res/material/img/tableinput.png",
            label:"多行数据输入"
        }));
        this.addComponent(new DatetimeComponent({
            icon:"res/material/img/datetime.png",
            label:"日期选择框"
        }));
        this.addComponent(new SelectComponent({
            icon:"res/material/img/dropdown.png",
            label:"单选列表",
            multiple:false
        }));
        this.addComponent(new SelectComponent({
            icon:"res/material/img/dropdown.png",
            label:"多选列表",
            multiple:true
        }));
        this.addComponent(new DynamicSelectComponent({
            icon:"res/material/img/dynamic-select.png",
            label:"动态列表",
            multiple:true
        }));
        this.addComponent(new CheckboxComponent({
            icon:"res/material/img/checkbox.png",
            label:"复选框"
        }));
        this.addComponent(new RadioComponent({
            icon:"res/material/img/radio.png",
            label:"单选框"
        }));
        this.addComponent(new TextAreaComponent({
            icon:"res/material/img/textarea.png",
            label:"多行文本框"
        }));

        this.addComponent(new GridSingleComponent({
            icon:"res/material/img/1col.png",
            label:"一列布局"
        }));
        this.addComponent(new Grid2X2Component({
            icon:"res/material/img/2col.png",
            label:"两列布局"
        }));
        this.addComponent(new Grid3x3x3Component({
            icon:"res/material/img/3col.png",
            label:"三列布局"
        }));
        this.addComponent(new Grid4x4x4x4Component({
            icon:"res/material/img/4col.png",
            label:"四列布局"
        }));
        this.addComponent(new GridCustomComponent({
            icon:"res/material/img/custom-col.png",
            label:"自定义列布局"
        }));
        this.addComponent(new TabControlComponent({
            icon:"res/material/img/tab.png",
            label:"标签页"
        }));
        this.addComponent(new HeaderComponent({
            icon:"res/material/img/ico_header.png",
            label:"标题"
        }));
        this.addComponent(new ParagraphComponent({
            icon:"res/material/img/pgp.png",
            label:"一段文字"
        }));
        this.addComponent(new PanelComponent({
            icon:"res/material/img/panel.png",
            label:"面板"
        }));
        this.addComponent(new ListComponent({
            icon:"res/material/img/list.png",
            label:"列表"
        }));
        this.addComponent(new AccordionComponent({
            icon:"res/material/img/accordion.png",
            label:"折叠面板"
        }));
        this.addComponent(new ImageComponent({
            icon:"res/material/img/img.png",
            label:"图片"
        }));
        */
    }
    initContainer(){
        this.tabControl=$("<div class='pb-palette'>");
        var ul=$("<ul class='nav nav-tabs' style='margin: 15px;'>");
        var componentLi=$("<li class='active'><a href='#"+Palette.componentId+"' data-toggle='tab'>组件</a>");
        ul.append(componentLi);
        var propertyLi=$("<li><a href='#"+Palette.propertyId+"' data-toggle='tab'>属性</a></li>");
        ul.append(propertyLi);
        this.tabControl.append(ul);
        var tabContent=$("<div class='tab-content'>");
        this.componentPalette=$("<div class=\"tab-pane fade in active container\" id=\""+Palette.componentId+"\" style=\"width: 100%\">");
        this.propertyPalette=$("<div class=\"tab-pane fade container\" id=\""+Palette.propertyId+"\" style=\"width:auto\">");
        tabContent.append(this.componentPalette);
        tabContent.append(this.propertyPalette);
        this.tabControl.append(tabContent);
    }
    addComponent(component){
        if(this.row){
            var col=$("<div class=\"col-sm-6\">");
            col.append(component.tool);
            this.row.append(col);
            this.row=null;
        }else{
            this.row=$("<div class=\"row\">");
            var col=$("<div class=\"col-sm-6\">");
            col.append(component.tool);
            this.row.append(col);
            this.componentPalette.append(this.row);
        }
        var componentId=component.id;
        this.components.push({
            "id":componentId,
            "component":component
        });
        if(component.property){
            this.propertyPalette.append(component.property.propertyContainer);
            component.property.propertyContainer.hide();
        }
    }
}