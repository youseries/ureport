/**
 * Created by Jacky.Gao on 2017-06-23.
 */
import CategoryChartValueEditor from './CategoryChartValueEditor.js';

export default class BarChartValueEditor extends CategoryChartValueEditor{
    constructor(parentContainer,context,id){
        super();
        if(id){
            this.id=id;
        }else{
            this.id='bar';
        }
        this.context=context;
        this.container=$(`<div></div>`);
        parentContainer.append(this.container);
        const tabUL=$(`<ul class="nav nav-tabs"></ul>`);
        this.container.append(tabUL);
        const dsLI=$(`<li class="active"><a href="#chart_bind_dataset_${this.id}" data-toggle="tab">${window.i18n.chart.datasetBind}</a></li>`);
        tabUL.append(dsLI);
        const optionLI=$(`<li><a href="#chart_option_${this.id}" data-toggle="tab">${window.i18n.chart.option}</a></li>`);
        tabUL.append(optionLI);
        this.axisLI=$(`<li><a href="#chart_axis_${this.id}" data-toggle="tab">${window.i18n.chart.axisConfig}</a></li>`);
        tabUL.append(this.axisLI);
        const tabContent=$(`<div class="tab-content"></div>`);
        this.container.append(tabContent);

        const dsContent=$(`<div class="tab-pane fade in active" id="chart_bind_dataset_${this.id}"></div>`);
        tabContent.append(dsContent);
        this._initDatasetTab(dsContent);

        const optionContent=$(`<div class="tab-pane fade in" id="chart_option_${this.id}"></div>`);
        tabContent.append(optionContent);
        this._initOptionTab(optionContent);

        const axisContent=$(`<div class="tab-pane fade in" id="chart_axis_${this.id}"></div>`);
        tabContent.append(axisContent);
        this._initAxisTab(axisContent);
        this.container.hide();
    }
    _initDatasetTab(dsContent){
        this.initCategoryDataset(dsContent);
    }
    _initOptionTab(optionContent){
        const group=$(`<div></div>`);
        optionContent.append(group);
        this.initTitleOption(group);
        this.initLegendOption(group);
        this.initDataLabelsOption(group);
        this.initAnimationsOption(group);
    }
    _initAxisTab(axisContent){
        this.initXAxes(axisContent);
        this.initYAxes(axisContent);
    }
    show(cellDef,rowIndex,colIndex,row2Index,col2Index){
        this.cellDef=cellDef;
        this.rowIndex=rowIndex;
        this.colIndex=colIndex;
        this.container.show();
        const chart=cellDef.value.chart;
        this.datasetSelect.empty();
        this.datasources=this.context.reportDef.datasources;
        for(let ds of this.datasources){
            let datasets=ds.datasets || [];
            for(let dataset of datasets){
                this.datasetSelect.append(`<option>${dataset.name}</option>`);
            }
        }
        this.datasetSelect.append(`<option selected></option>`);
        const dataset=chart.dataset;
        this.datasetSelect.val(dataset.datasetName);
        this.datasetSelect.trigger('change');
        this.categoryPropertySelect.val(dataset.categoryProperty);
        this.seriesPropertySelect.val(dataset.seriesProperty);
        this.seriesTextEditor.val(dataset.seriesText);
        this.valuePropertySelect.val(dataset.valueProperty);
        this.aggregateSelect.val(dataset.collectType);
        if(dataset.seriesType==='property'){
            this.propertySeriesRadio.children('input').attr('checked',true);
            this.propertySeriesRadio.children('input').trigger('click');
        }else{
            this.textSeriesRadio.children('input').attr('checked',true);
            this.textSeriesRadio.children('input').trigger('click');
        }
        this.formatEditor.val(dataset.format);

        const xaxes=chart.xaxes || {rotation:0,xposition:'left'};
        this.xAxesRotationEditor.val(xaxes.rotation);
        const xScaleLabel=xaxes.scaleLabel || {};
        if(xScaleLabel.display){
            this.showXTitleRadio.trigger('click');
            this.xTitleEditor.val(xScaleLabel.labelString);
        }else{
            this.hideXTitleRadio.trigger('click');
        }

        const yaxes=chart.yaxes || {rotation:0,yposition:'bottom'};
        this.yAxesRotationEditor.val(yaxes.rotation);
        const yScaleLabel=yaxes.scaleLabel || {};
        if(yScaleLabel.display){
            this.showYTitleRadio.trigger('click');
            this.yTitleEditor.val(yScaleLabel.labelString);
        }else{
            this.hideYTitleRadio.trigger('click');
        }
        this.hideDataLabelsRadio.children('input').attr('checked',true);
        const plugins=chart.plugins || [];
        for(let plugin of plugins){
            if(plugin.name==='data-labels' && plugin.display){
                this.showDataLabelsRadio.children('input').attr('checked',true);
            }
        }
        const options=chart.options || [];
        for(let option of options){
            switch (option.type){
                case "animation":
                    this.durationEditor.val(option.duration);
                    this.easingSelect.val(option.easing);
                    break;
                case "title":
                    if(option.display){
                        this.showTitleRadio.children('input').attr('checked',true);
                        this.titlePositionSelect.val(option.position);
                        this.titleTextEditor.val(option.text);
                        this.titleTextGroup.show();
                        this.titlePositionGroup.show();
                    }else{
                        this.hideTitleRadio.children('input').attr('checked',true);
                        this.titleTextGroup.hide();
                        this.titlePositionGroup.hide();
                    }
                    break;
                case "layout":
                    const layout=option.layout || {left:0,right:0,top:0,bottom:0};
                    this.upPaddingEditor.val(layout.top);
                    this.downPaddingEditor.val(layout.bottom);
                    this.leftPaddingEditor.val(layout.left);
                    this.rightPaddingEditor.val(layout.right);
                    break;
                case "legend":
                    if(option.display){
                        this.showLegendRadio.children('input').attr('checked',true);
                        this.legendPositionGroup.show();
                        this.legendPositionSelect.val(option.position);
                    }else{
                        this.hideLegendRadio.children('input').attr('checked',true);
                        this.legendPositionGroup.hide();
                    }
                    break;
            }
        }
    }
    hide(){
        this.container.hide();
    }
}