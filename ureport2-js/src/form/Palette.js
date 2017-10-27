/**
 * Created by Jacky.Gao on 2017-10-12.
 */
import Grid2X2Component from './component/Grid2X2Component.js';
import GridSingleComponent from './component/GridSingleComponent.js';
import Grid3x3x3Component from './component/Grid3x3x3Component.js';
import Grid4x4x4x4Component from './component/Grid4x4x4x4Component.js';
import GridCustomComponent from './component/GridCustomComponent.js';
import TextComponent from './component/TextComponent.js';
import RadioComponent from './component/RadioComponent.js';
import CheckboxComponent from './component/CheckboxComponent.js';
import SelectComponent from './component/SelectComponent.js';
import SubmitButtonComponent from './component/SubmitButtonComponent.js';
import ResetButtonComponent from './component/ResetButtonComponent.js';
import DatetimeComponent from './component/DatetimeComponent.js';

export default class Palette{
    constructor(){
        this.components=[];
        this.initContainer();
        this.initComponents();
    }
    initComponents(){
        this.addComponent(new GridSingleComponent({
            icon:"form form-1col",
            label:"一列布局"
        }));
        this.addComponent(new Grid2X2Component({
            icon:"form form-2col",
            label:"两列布局"
        }));
        this.addComponent(new Grid3x3x3Component({
            icon:"form form-3col",
            label:"三列布局"
        }));
        this.addComponent(new Grid4x4x4x4Component({
            icon:"form form-4col",
            label:"四列布局"
        }));
        this.addComponent(new GridCustomComponent({
            icon:"form form-custom-col",
            label:"自定义列布局"
        }));
        this.addComponent(new TextComponent({
            icon:"form form-textbox",
            label:"文本框"
        }));
        this.addComponent(new DatetimeComponent({
            icon:"glyphicon glyphicon-calendar",
            label:"日期选择框"
        }));
        this.addComponent(new RadioComponent({
            icon:"form form-radio",
            label:"单选框"
        }));
        this.addComponent(new CheckboxComponent({
            icon:"form form-checkbox",
            label:"复选框"
        }));
        this.addComponent(new SelectComponent({
            icon:"form form-dropdown",
            label:"单选列表"
        }));
        this.addComponent(new SubmitButtonComponent({
            icon:"form form-submit",
            label:"提交按钮"
        }));
        this.addComponent(new ResetButtonComponent({
            icon:"form form-reset",
            label:"重置按钮"
        }));
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
Palette.componentId="pb_component_container_palette";
Palette.propertyId="pb_component_property_palette";