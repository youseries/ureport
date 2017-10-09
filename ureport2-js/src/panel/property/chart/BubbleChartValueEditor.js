/**
 * Created by Jacky.Gao on 2017-06-27.
 */
import CategoryChartValueEditor from './CategoryChartValueEditor.js';
import {setDirty} from '../../../Utils.js';

export default class ScatterChartValueEditor extends CategoryChartValueEditor{
    constructor(parentContainer,context){
        super();
        this.context=context;
        this.container=$(`<div></div>`);
        parentContainer.append(this.container);
        const tabUL=$(`<ul class="nav nav-tabs"></ul>`);
        this.container.append(tabUL);
        const dsLI=$(`<li class="active"><a href="#chart_bind_dataset_bubble" data-toggle="tab">${window.i18n.chart.datasetBind}</a></li>`);
        tabUL.append(dsLI);
        const optionLI=$(`<li><a href="#chart_option_bubble" data-toggle="tab">${window.i18n.chart.option}</a></li>`);
        tabUL.append(optionLI);
        this.axisLI=$(`<li><a href="#chart_axis_bubble" data-toggle="tab">${window.i18n.chart.axisConfig}</a></li>`);
        tabUL.append(this.axisLI);
        const tabContent=$(`<div class="tab-content"></div>`);
        this.container.append(tabContent);

        const dsContent=$(`<div class="tab-pane fade in active" id="chart_bind_dataset_bubble"></div>`);
        tabContent.append(dsContent);
        this._initDatasetTab(dsContent);

        const optionContent=$(`<div class="tab-pane fade in" id="chart_option_bubble"></div>`);
        tabContent.append(optionContent);
        this._initOptionTab(optionContent);

        const axisContent=$(`<div class="tab-pane fade in" id="chart_axis_bubble"></div>`);
        tabContent.append(axisContent);
        this._initAxisTab(axisContent);
        this.container.hide();
    }
    _initDatasetTab(dsContent){
        const _this=this;
        const legendGroup=$(`<fieldset style="padding: 10px;border:solid 1px #dddddd;border-radius: 8px;margin-bottom: 10px;margin-top: 10px">
        <legend style="width: auto;margin-bottom: 1px;border-bottom:none;font-size: inherit;color: #4b4b4b;">${window.i18n.chart.propBindConfig}</legend></fieldset>`);
        dsContent.append(legendGroup);

        const datasetGroup=$(`<div class="form-group" style="margin-top: 10px"><label>${window.i18n.chart.dataset}</label></div>`);
        legendGroup.append(datasetGroup);
        this.datasetSelect=$(`<select class="form-control" style="display: inline-block;width:295px;padding:2px;font-size: 12px;height: 25px"></select>`);
        datasetGroup.append(this.datasetSelect);

        const categoryPropertyGroup=$(`<div class="form-group" style="margin-top: 5px;margin-bottom: 5px;"><label>${window.i18n.chart.categoryProperty}</label></div>`);
        this.categoryPropertySelect=$(`<select class="form-control" style="display: inline-block;width:280px;padding: 2px;font-size: 12px;height: 25px"></select>`);
        categoryPropertyGroup.append(this.categoryPropertySelect);
        datasetGroup.append(categoryPropertyGroup);
        this.categoryPropertySelect.change(function(){
            const dataset=_this.getDatasetConfig();
            dataset.categoryProperty=$(this).val();
        });
        const xPropertyGroup=$(`<div class="form-group" style="margin-bottom: 5px;"><label>${window.i18n.chart.xProperty}</label></div>`);
        this.xPropertySelect=$(`<select class="form-control" style="display: inline-block;width:285px;padding: 2px;font-size: 12px;height: 25px"></select>`);
        xPropertyGroup.append(this.xPropertySelect);
        xPropertyGroup.append(xPropertyGroup);
        this.xPropertySelect.change(function(){
            const dataset=_this.getDatasetConfig();
            dataset.xProperty=$(this).val();
            setDirty();
        });
        legendGroup.append(xPropertyGroup);

        const yPropertyGroup=$(`<div class="form-group" style="margin-bottom: 5px;"><label>${window.i18n.chart.yProperty}</label></div>`);
        this.yPropertySelect=$(`<select class="form-control" style="display: inline-block;width:285px;padding: 2px;font-size: 12px;height: 25px"></select>`);
        yPropertyGroup.append(this.yPropertySelect);
        yPropertyGroup.append(yPropertyGroup);
        this.yPropertySelect.change(function(){
            const dataset=_this.getDatasetConfig();
            dataset.yProperty=$(this).val();
            setDirty();
        });
        legendGroup.append(yPropertyGroup);


        const rPropertyGroup=$(`<div class="form-group" style="margin-bottom: 5px;"><label>${window.i18n.chart.rProperty}</label></div>`);
        this.rPropertySelect=$(`<select class="form-control" style="display: inline-block;width:265px;padding: 2px;font-size: 12px;height: 25px"></select>`);
        rPropertyGroup.append(this.rPropertySelect);
        rPropertyGroup.append(rPropertyGroup);
        this.rPropertySelect.change(function(){
            const dataset=_this.getDatasetConfig();
            dataset.rProperty=$(this).val();
            setDirty();
        });
        legendGroup.append(rPropertyGroup);

        this.datasetSelect.change(function(){
            _this.categoryPropertySelect.empty();
            _this.xPropertySelect.empty();
            _this.yPropertySelect.empty();
            _this.rPropertySelect.empty();
            const dsName=$(this).val();
            let fields=[];
            for(let ds of _this.datasources){
                let datasets=ds.datasets || [];
                for(let dataset of datasets){
                    if(dataset.name===dsName){
                        fields=dataset.fields || [];
                        break;
                    }
                }
                if(fields.length>0){
                    break;
                }
            }
            for(let field of fields){
                _this.categoryPropertySelect.append(`<option>${field.name}</option>`);
                _this.xPropertySelect.append(`<option>${field.name}</option>`);
                _this.yPropertySelect.append(`<option>${field.name}</option>`);
                _this.rPropertySelect.append(`<option>${field.name}</option>`);
            }
            _this.categoryPropertySelect.append(`<option selected></option>`);
            _this.xPropertySelect.append(`<option selected></option>`);
            _this.yPropertySelect.append(`<option selected></option>`);
            _this.rPropertySelect.append(`<option selected></option>`);
            const dataset=_this.getDatasetConfig();
            dataset.datasetName=dsName;
        });
    }
    _initOptionTab(optionContent){
        const group=$(`<div></div>`);
        optionContent.append(group);
        this.initTitleOption(group);
        this.initLegendOption(group);
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
        this.xPropertySelect.val(dataset.xProperty);
        this.yPropertySelect.val(dataset.yProperty);
        this.rPropertySelect.val(dataset.rProperty);

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