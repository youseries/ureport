/**
 * Created by Jacky.Gao on 2017-06-23.
 */
import {setDirty} from '../../../Utils.js';

export default class ChartValueEditor{
    constructor(){
    }
    initAnimationsOption(container){
        const _this=this;
        const legendGroup=$(`<fieldset style="padding: 10px;border:solid 1px #dddddd;border-radius: 8px;margin-top: 10px">
        <legend style="width: auto;margin-bottom: 1px;border-bottom:none;font-size: inherit;color: #4b4b4b;">${window.i18n.chart.motionConfig}</legend></fieldset>`);
        container.append(legendGroup);
        const g=$(`<div class="form-group" style="margin-bottom: 0"><label>${window.i18n.chart.motionDelay}</label></div>`);
        legendGroup.append(g);
        this.durationEditor=$(`<input type="number" class="form-control" style="display: inline-block;width:100px;padding:2px;font-size: 12px;height: 25px" value="1000">`);
        g.append(this.durationEditor);
        this.durationEditor.change(function(){
            const targetOption=_this.getTargetOption('animation');
            targetOption.duration=$(this).val();
            setDirty();
        });
        const easingGroup=$(`<span style="margin-left: 20px">${window.i18n.chart.effect}</span>`);
        g.append(easingGroup);
        const easingType='linear,easeInQuad,easeOutQuad,easeInOutQuad,easeInCubic,easeOutCubic,easeInOutCubic,easeInQuart,easeOutQuart,easeInOutQuart,easeInQuint,easeOutQuint,easeInOutQuint,easeInSine,easeOutSine,easeInOutSine,easeInExpo,easeOutExpo,easeInOutExpo,easeInCirc,easeOutCirc,easeInOutCirc,easeInElastic,easeOutElastic,easeInOutElastic,easeInBack,easeOutBack,easeInOutBack,easeInBounce,easeOutBounce,easeInOutBounce';
        this.easingSelect=$(`<select class="form-control" style="display: inline-block;width:inherit;padding:2px;font-size: 12px;height: 25px"></select>`);
        for(let type of easingType.split(',')){
            if(type==='easeOutQuart'){
                this.easingSelect.append(`<option selected>${type}</option>`);
            }else{
                this.easingSelect.append(`<option>${type}</option>`);
            }
        }
        easingGroup.append(this.easingSelect);
        this.easingSelect.change(function(){
            const targetOption=_this.getTargetOption('animation');
            targetOption.easing=$(this).val();
            setDirty();
        });
    }
    initPaddingOption(container){
        const _this=this;
        const legendGroup=$(`<fieldset style="padding: 10px;border:solid 1px #dddddd;border-radius: 8px">
        <legend style="width: auto;margin-bottom: 1px;border-bottom:none;font-size: inherit;color: #4b4b4b;">${window.i18n.chart.margin}</legend></fieldset>`);
        container.append(legendGroup);
        const upGroup=$(`<span>上：</span>`);
        legendGroup.append(upGroup);
        this.upPaddingEditor=$(`<input type="number" value="0" class="form-control" style="display: inline-block;width:50px;padding:2px;font-size: 12px;height: 25px">`);
        upGroup.append(this.upPaddingEditor);
        this.upPaddingEditor.change(function(){
            const targetOption=_this.getTargetOption('layout');
            if(!targetOption.padding){
                targetOption.padding={left:0,right:0,top:0,bottom:0};
            }
            targetOption.padding.top=$(this).val();
            const chart=_this.cellDef.chartWidget.chart;
            chart.options.layout={
                padding:targetOption.padding
            };
            _this.cellDef.chartWidget.chart.update();
            setDirty();
        });

        const downGroup=$(`<span style="margin-left: 10px">${window.i18n.chart.down}：</span>`);
        legendGroup.append(downGroup);
        this.downPaddingEditor=$(`<input type="number" value="0" class="form-control" style="display: inline-block;width:50px;padding:2px;font-size: 12px;height: 25px">`);
        downGroup.append(this.downPaddingEditor);
        this.downPaddingEditor.change(function(){
            const targetOption=_this.getTargetOption('layout');
            if(!targetOption.padding){
                targetOption.padding={left:0,right:0,top:0,bottom:0};
            }
            targetOption.padding.bottom=$(this).val();
            const chart=_this.cellDef.chartWidget.chart;
            chart.options.layout={
                padding:targetOption.padding
            };
            _this.cellDef.chartWidget.chart.update();
            setDirty();
        });

        const leftGroup=$(`<span style="margin-left: 10px">${window.i18n.chart.left}：</span>`);
        legendGroup.append(leftGroup);
        this.leftPaddingEditor=$(`<input type="number" value="0" class="form-control" style="display: inline-block;width:50px;padding:2px;font-size: 12px;height: 25px">`);
        leftGroup.append(this.leftPaddingEditor);
        this.leftPaddingEditor.change(function(){
            const targetOption=_this.getTargetOption('layout');
            if(!targetOption.padding){
                targetOption.padding={left:0,right:0,top:0,bottom:0};
            }
            targetOption.padding.left=$(this).val();
            const chart=_this.cellDef.chartWidget.chart;
            chart.options.layout={
                padding:targetOption.padding
            };
            _this.cellDef.chartWidget.chart.update();
            setDirty();
        });

        const rightGroup=$(`<span style="margin-left: 10px">${window.i18n.chart.right}：</span>`);
        legendGroup.append(rightGroup);
        this.rightPaddingEditor=$(`<input type="number" value="0" class="form-control" style="display: inline-block;width:50px;padding:2px;font-size: 12px;height: 25px">`);
        rightGroup.append(this.rightPaddingEditor);
        this.rightPaddingEditor.change(function(){
            const targetOption=_this.getTargetOption('layout');
            if(!targetOption.padding){
                targetOption.padding={left:0,right:0,top:0,bottom:0};
            }
            targetOption.padding.right=$(this).val();
            const chart=_this.cellDef.chartWidget.chart;
            chart.options.layout={
                padding:targetOption.padding
            };
            _this.cellDef.chartWidget.chart.update();
            setDirty();
        });
    }
    initLegendOption(container){
        const _this=this;
        const legendGroup=$(`<fieldset style="padding: 10px;border:solid 1px #dddddd;border-radius: 8px;margin-bottom: 10px;">
        <legend style="width: auto;margin-bottom: 1px;border-bottom:none;font-size: inherit;color: #4b4b4b;">${window.i18n.chart.legendConfig}</legend></fieldset>`);
        container.append(legendGroup);
        const displayGroup=$(`<div class="form-group" style="margin-bottom: 0"><label>${window.i18n.chart.display}</label></div>`);
        legendGroup.append(displayGroup);
        this.showLegendRadio=$(`<label class="checkbox-inline" style="padding-left: 2px"><input type="radio" name="__show_legend_radio_${this.id}" value="asc" checked>${window.i18n.chart.yes}</label>`);
        displayGroup.append(this.showLegendRadio);
        this.hideLegendRadio=$(`<label class="checkbox-inline" style="padding-left: 2px"><input type="radio" name="__show_legend_radio_${this.id}" value="asc">${window.i18n.chart.no}</label>`);
        displayGroup.append(this.hideLegendRadio);

        this.showLegendRadio.children('input').change(function(){
            const targetOption=_this.getTargetOption('legend');
            targetOption.display=true;
            targetOption.position='top';
            _this.legendPositionGroup.show();
            _this.legendPositionSelect.val(targetOption.position);
            const chart=_this.cellDef.chartWidget.chart;
            chart.options.legend={
                display:true,
                position:targetOption.position
            };
            _this.cellDef.chartWidget.chart.update();
            setDirty();
        });
        this.hideLegendRadio.children('input').change(function(){
            const targetOption=_this.getTargetOption('legend');
            targetOption.display=false;
            _this.legendPositionGroup.hide();
            const chart=_this.cellDef.chartWidget.chart;
            chart.options.legend={
                display:false
            };
            _this.cellDef.chartWidget.chart.update();
            setDirty();
        });

        this.legendPositionGroup=$(`<span style="margin-left: 20px">${window.i18n.chart.position}</span>`);
        displayGroup.append(this.legendPositionGroup);
        this.legendPositionSelect=$(`<select class="form-control" style="display: inline-block;width:50px;padding:2px;font-size: 12px;height: 25px">
            <option value="top" selected>${window.i18n.chart.up}</option>
            <option value="bottom">${window.i18n.chart.down}</option>
            <option value="left">${window.i18n.chart.left}</option>
            <option value="right">${window.i18n.chart.right}</option>
        </select>`);
        this.legendPositionGroup.append(this.legendPositionSelect);
        this.legendPositionSelect.change(function(){
            const targetOption=_this.getTargetOption('legend');
            targetOption.position=$(this).val();
            const chart=_this.cellDef.chartWidget.chart;
            chart.options.legend={
                display:true,
                position:targetOption.position
            };
            _this.cellDef.chartWidget.chart.update();
            setDirty();
        });
    }

    initDataLabelsOption(container){
        const _this=this;
        const dataLabelsGroup=$(`<fieldset style="padding: 10px;border:solid 1px #dddddd;border-radius: 8px;margin-top:10px;margin-bottom: 15px">
        <legend style="width: auto;margin-bottom: 1px;border-bottom:none;font-size: inherit;color: #4b4b4b;">数据标签配置</legend></fieldset>`);
        container.append(dataLabelsGroup);
        const displayGroup=$(`<div class="form-group" style="margin-bottom: 0"><label style="margin-bottom: 15px">${window.i18n.chart.display}</label></div>`);
        dataLabelsGroup.append(displayGroup);
        this.showDataLabelsRadio=$(`<label class="checkbox-inline" style="padding-left: 2px"><input type="radio" name="__show_datalabels_radio_${this.id}" value="asc">${window.i18n.chart.yes}</label>`);
        displayGroup.append(this.showDataLabelsRadio);
        this.showDataLabelsRadio.children('input').click(function () {
            const dataLabels=_this.getTargetPlugin("data-labels");
            dataLabels.display=true;
        });
        this.hideDataLabelsRadio=$(`<label class="checkbox-inline" style="padding-left: 2px"><input type="radio" name="__show_datalabels_radio_${this.id}" value="asc" checked>${window.i18n.chart.no}</label>`);
        displayGroup.append(this.hideDataLabelsRadio);
        this.hideDataLabelsRadio.children('input').click(function () {
            const dataLabels=_this.getTargetPlugin("data-labels");
            dataLabels.display=false;
        });
    }

    initTitleOption(container){
        const _this=this;
        const legendGroup=$(`<fieldset style="padding: 10px;border:solid 1px #dddddd;border-radius: 8px;margin-top:10px;margin-bottom: 15px">
        <legend style="width: auto;margin-bottom: 1px;border-bottom:none;font-size: inherit;color: #4b4b4b;">${window.i18n.chart.titleConfig}</legend></fieldset>`);
        container.append(legendGroup);
        const displayGroup=$(`<div class="form-group" style="margin-bottom: 0"><label style="margin-bottom: 15px">${window.i18n.chart.display}</label></div>`);
        legendGroup.append(displayGroup);
        this.showTitleRadio=$(`<label class="checkbox-inline" style="padding-left: 2px"><input type="radio" name="__show_title_radio_${this.id}" value="asc">${window.i18n.chart.yes}</label>`);
        displayGroup.append(this.showTitleRadio);
        this.hideTitleRadio=$(`<label class="checkbox-inline" style="padding-left: 2px"><input type="radio" name="__show_title_radio_${this.id}" value="asc" checked>${window.i18n.chart.no}</label>`);
        displayGroup.append(this.hideTitleRadio);
        this.showTitleRadio.children('input').change(function(){
            const targetOption=_this.getTargetOption('title');
            targetOption.display=true;
            targetOption.text='';
            targetOption.position='top';
            _this.titleTextGroup.show();
            _this.titleTextEditor.val(targetOption.text);
            _this.titlePositionGroup.show();
            _this.titlePositionSelect.val(targetOption.position);
            const chart=_this.cellDef.chartWidget.chart;
            chart.options.title={
                display:true,
                text:'',
                fontSize:14,
                position:'top'
            };
            _this.cellDef.chartWidget.chart.update();
            setDirty();
        });
        this.hideTitleRadio.children('input').change(function(){
            const targetOption=_this.getTargetOption('title');
            targetOption.display=false;
            _this.titleTextGroup.hide();
            _this.titlePositionGroup.hide();
            const chart=_this.cellDef.chartWidget.chart;
            chart.options.title={
                display:false
            };
            _this.cellDef.chartWidget.chart.update();
            setDirty();
        });

        this.titlePositionGroup=$(`<span style="margin-left: 15px">${window.i18n.chart.position}</span>`);
        displayGroup.append(this.titlePositionGroup);
        this.titlePositionSelect=$(`<select class="form-control" style="display: inline-block;width:50px;padding:2px;font-size: 12px;height: 25px">
            <option value="top">${window.i18n.chart.up}</option>
            <option value="bottom">${window.i18n.chart.down}</option>
            <option value="left">${window.i18n.chart.left}</option>
            <option value="right">${window.i18n.chart.right}</option>
        </select>`);
        this.titlePositionGroup.append(this.titlePositionSelect);
        this.titlePositionSelect.change(function(){
            const targetOption=_this.getTargetOption('title');
            targetOption.position=$(this).val();
            const chart=_this.cellDef.chartWidget.chart;
            chart.options.title={
                display:true,
                text:targetOption.text,
                fontSize:14,
                position:targetOption.position
            };
            _this.cellDef.chartWidget.chart.update();
            setDirty();
        });

        this.titleTextGroup=$(`<div class="form-group" style="margin-bottom: 0"><label>${window.i18n.chart.titleContent}</label></div>`);
        displayGroup.append(this.titleTextGroup);
        this.titleTextEditor=$(`<input type="text" class="form-control" style="display: inline-block;width:280px;padding:2px;font-size: 12px;height: 25px">`);
        this.titleTextGroup.append(this.titleTextEditor);
        this.titleTextEditor.change(function(){
            const targetOption=_this.getTargetOption('title');
            targetOption.text=$(this).val();
            const chart=_this.cellDef.chartWidget.chart;
            chart.options.title={
                display:true,
                text:targetOption.text,
                position:targetOption.position
            };
            _this.cellDef.chartWidget.chart.update();
            setDirty();
        });
        this.titleTextGroup.hide();
        this.titlePositionGroup.hide();
    }

    getTargetPlugin(name){
        let plugins=this.cellDef.value.chart.plugins;
        if(!plugins){
            plugins=[];
            this.cellDef.value.chart.plugins=plugins;
        }
        let targetPlugin=null;
        for(let plugin of plugins){
            if(plugin.name===name){
                targetPlugin=plugin;
                break;
            }
        }
        if(!targetPlugin){
            targetPlugin={name:name,display:false};
            plugins.push(targetPlugin);
        }
        return targetPlugin;
    }

    getTargetOption(type){
        let options=this.cellDef.value.chart.options;
        if(!options){
            options=[];
            this.cellDef.value.chart.options=options;
        }
        let targetOption=null;
        for(let option of options){
            if(option.type===type){
                targetOption=option;
                break;
            }
        }
        if(!targetOption){
            targetOption={type};
            options.push(targetOption);
        }
        return targetOption;
    }
    getDatasetConfig(){
        let dataset=this.cellDef.value.chart.dataset;
        if(!dataset){
            dataset={};
            this.cellDef.value.chart.dataset=dataset;
        }
        return dataset;
    }
}