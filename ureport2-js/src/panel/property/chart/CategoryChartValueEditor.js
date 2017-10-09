/**
 * Created by Jacky.Gao on 2017-06-25.
 */
import ChartValueEditor from './ChartValueEditor.js';
import {setDirty} from '../../../Utils.js';

export default class CategoryChartValueEditor extends ChartValueEditor{
    constructor(){
        super();
    }
    initCategoryDataset(dsContent){
        const _this=this;
        const legendGroup=$(`<fieldset style="padding: 10px;border:solid 1px #dddddd;border-radius: 8px;margin-bottom: 10px;margin-top: 10px">
        <legend style="width: auto;margin-bottom: 1px;border-bottom:none;font-size: inherit;color: #4b4b4b;">${window.i18n.chart.propBindConfig}</legend></fieldset>`);
        dsContent.append(legendGroup);

        const datasetGroup=$(`<div class="form-group" style="margin-top: 10px;margin-bottom: 5px"><label>${window.i18n.chart.dataset}</label></div>`);
        legendGroup.append(datasetGroup);
        this.datasetSelect=$(`<select class="form-control" style="display: inline-block;width:300px;padding:2px;font-size: 12px;height: 25px"></select>`);
        datasetGroup.append(this.datasetSelect);

        const categoryPropertyGroup=$(`<div class="form-group" style="margin-top: 5px;margin-bottom: 5px;"><label>${window.i18n.chart.categoryProperty}</label></div>`);
        this.categoryPropertySelect=$(`<select class="form-control" style="display: inline-block;width:285px;padding: 2px;font-size: 12px;height: 25px"></select>`);
        categoryPropertyGroup.append(this.categoryPropertySelect);
        datasetGroup.append(categoryPropertyGroup);
        this.categoryPropertySelect.change(function(){
            const dataset=_this.getDatasetConfig();
            dataset.categoryProperty=$(this).val();
            setDirty();
        });

        const valuePropertyGroup=$(`<div class="form-group"><label>${window.i18n.chart.valueProperty}</label></div>`);
        this.valuePropertySelect=$(`<select class="form-control" style="display: inline-block;width:300px;padding: 2px;font-size: 12px;height: 25px"></select>`);
        valuePropertyGroup.append(this.valuePropertySelect);
        legendGroup.append(valuePropertyGroup);
        this.valuePropertySelect.change(function(){
            const dataset=_this.getDatasetConfig();
            dataset.valueProperty=$(this).val();
            setDirty();
        });

        const seriesGroup=$(`<div class="form-group" style="margin-bottom: 0"><label>${window.i18n.chart.seriesProperty}</label></div>`);
        legendGroup.append(seriesGroup);
        this.propertySeriesRadio=$(`<label class="checkbox-inline" style="padding-left: 2px"><input type="radio" name="__chart_series_radio_${this.id}">${window.i18n.chart.property}</label>`);
        seriesGroup.append(this.propertySeriesRadio);
        this.textSeriesRadio=$(`<label class="checkbox-inline" style="padding-left: 2px"><input type="radio" name="__chart_series_radio_${this.id}" checked>${window.i18n.chart.static}</label>`);
        seriesGroup.append(this.textSeriesRadio);
        this.propertySeriesRadio.children('input').click(function(){
            _this.seriesPropertyGroup.show();
            _this.seriesTextGroup.hide();
            const dataset=_this.getDatasetConfig();
            dataset.seriesType='property';
            setDirty();
        });
        this.textSeriesRadio.children('input').click(function(){
            _this.seriesPropertyGroup.hide();
            _this.seriesTextGroup.show();
            const dataset=_this.getDatasetConfig();
            dataset.seriesType='text';
            setDirty();
        });

        this.seriesPropertyGroup=$(`<div class="form-group"  style="margin-left: 10px"><span>${window.i18n.chart.prop}</span></div>`);
        this.seriesPropertySelect=$(`<select class="form-control" style="display: inline-block;width:303px;padding: 2px;font-size: 12px;height: 25px"></select>`);
        this.seriesPropertyGroup.append(this.seriesPropertySelect);
        legendGroup.append(this.seriesPropertyGroup);
        this.seriesPropertySelect.change(function(){
            const dataset=_this.getDatasetConfig();
            dataset.seriesProperty=$(this).val();
            setDirty();
        });
        this.seriesPropertyGroup.hide();
        this.seriesTextGroup=$(`<div class="form-group" style="margin-left: 10px"><span>${window.i18n.chart.staticValue}</span></div>`);
        this.seriesTextEditor=$(`<input type="text" class="form-control" style="display: inline-block;width:288px;padding: 2px;font-size: 12px;height: 25px">`);
        this.seriesTextGroup.append(this.seriesTextEditor);
        legendGroup.append(this.seriesTextGroup);
        this.seriesTextEditor.change(function(){
            const dataset=_this.getDatasetConfig();
            dataset.seriesText=$(this).val();
            setDirty();
        });

        this.datasetSelect.change(function(){
            _this.categoryPropertySelect.empty();
            _this.seriesPropertySelect.empty();
            _this.valuePropertySelect.empty();
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
                _this.seriesPropertySelect.append(`<option>${field.name}</option>`);
                _this.valuePropertySelect.append(`<option>${field.name}</option>`);
            }
            _this.categoryPropertySelect.append(`<option selected></option>`);
            _this.seriesPropertySelect.append(`<option selected></option>`);
            _this.valuePropertySelect.append(`<option selected></option>`);
            const dataset=_this.getDatasetConfig();
            dataset.datasetName=dsName;
            setDirty();
        });

        const aggregateGroup=$(`<div class="form-group" style="margin-bottom: 5px"><label>${window.i18n.chart.aggregate}</label></div>`);
        legendGroup.append(aggregateGroup);
        this.aggregateSelect=$(`<select class="form-control" style="display: inline-block;width: 285px;font-size: 12px;height: 25px;padding: 3px;">
            <option value="select">${window.i18n.chart.select}</option>
            <option value="sum">${window.i18n.chart.sum}</option>
            <option value="count">${window.i18n.chart.count}</option>
            <option value="max">${window.i18n.chart.max}</option>
            <option value="min">${window.i18n.chart.min}</option>
            <option value="avg">${window.i18n.chart.avg}</option>
            <option selected></option>
        </select>`);
        aggregateGroup.append(this.aggregateSelect);
        this.aggregateSelect.change(function(){
            const dataset=_this.getDatasetConfig();
            dataset.collectType=$(this).val();
            setDirty();
        });
    }
    initXAxes(container){
        const legendGroup=$(`<fieldset style="padding: 10px;border:solid 1px #dddddd;border-radius: 8px;margin-bottom: 10px;margin-top: 10px">
        <legend style="width: auto;margin-bottom: 1px;border-bottom:none;font-size: inherit;color: #4b4b4b;">${window.i18n.chart.xAxis}</legend></fieldset>`);
        container.append(legendGroup);
        const _this=this;
        const rotationGroup=$(`<div class="form-group" style="margin-bottom: 10px;display:inline-block;margin-right: 20px"><label>${window.i18n.chart.titleRotation}</label></div>`);
        legendGroup.append(rotationGroup);
        this.xAxesRotationEditor=$(`<input type="number" class="form-control" value="0"  title="${window.i18n.chart.angleScope}" style="display: inline-block;width: 80px;font-size: 12px;height: 25px;padding: 3px;">`);
        rotationGroup.append(this.xAxesRotationEditor);
        this.xAxesRotationEditor.change(function(){
            const xaxes=_this.getXAxesConfig();
            xaxes.rotation=$(this).val();
            const targetTicks=_this.getXTicks();
            targetTicks.minRotation=xaxes.rotation;
            _this.cellDef.chartWidget.chart.update();
            setDirty();
        });

        const formatGroup=$(`<div class="form-group" style="margin-bottom: 10px"><label>${window.i18n.chart.titleFormat}</label></div>`);
        legendGroup.append(formatGroup);

        this.formatEditor=$(`<input type="text" class="form-control" style="display: inline-block;width: 260px;font-size: 12px;height: 25px;padding: 3px;">`);
        formatGroup.append(this.formatEditor);
        this.formatEditor.change(function(){
            const dataset=_this.getDatasetConfig();
            dataset.format=$(this).val();
            setDirty();
        });

        const displayGroup=$(`<div class="form-group" style="margin-bottom: 10px"><label>${window.i18n.chart.displayAxisTitle}</label></div>`);
        legendGroup.append(displayGroup);
        this.showXTitleRadio=$(`<label class="checkbox-inline" style="padding-left: 2px"><input type="radio" name="__show_xtitle_radio_${this.id}">${window.i18n.chart.yes}</label>`);
        displayGroup.append(this.showXTitleRadio);
        this.hideXTitleRadio=$(`<label class="checkbox-inline" style="padding-left: 2px"><input type="radio" name="__show_xtitle_radio_${this.id}" checked>${window.i18n.chart.no}</label>`);
        displayGroup.append(this.hideXTitleRadio);
        this.showXTitleRadio.children('input').change(function(){
            _this.xTitleGroup.show();
            const xaxes=_this.getXAxesConfig();
            let scaleLabel=xaxes.scaleLabel;
            if(!scaleLabel){
                scaleLabel={};
                xaxes.scaleLabel=scaleLabel;
            }
            scaleLabel.display=true;

            const targetScaleLabel=_this.getXScaleLabel();
            targetScaleLabel.display=true;
            _this.cellDef.chartWidget.chart.update();
            setDirty();
        });
        this.hideXTitleRadio.children('input').change(function(){
            _this.xTitleGroup.hide();
            const xaxes=_this.getXAxesConfig();
            let scaleLabel=xaxes.scaleLabel;
            if(!scaleLabel){
                scaleLabel={};
                xaxes.scaleLabel=scaleLabel;
            }
            scaleLabel.display=false;

            const targetScaleLabel=_this.getXScaleLabel();
            targetScaleLabel.display=false;
            _this.cellDef.chartWidget.chart.update();
            setDirty();
        });

        this.xTitleGroup=$(`<div class="form-group" style="margin-bottom: 0"><label>${window.i18n.chart.axisTitle}</label></div>`);
        legendGroup.append(this.xTitleGroup);
        this.xTitleEditor=$(`<input type="text" class="form-control" style="display: inline-block;width: 288px;font-size: 12px;height: 25px;padding: 3px;">`);
        this.xTitleGroup.append(this.xTitleEditor);
        this.xTitleEditor.change(function(){
            const xaxes=_this.getXAxesConfig();
            let scaleLabel=xaxes.scaleLabel;
            if(!scaleLabel){
                scaleLabel={};
                xaxes.scaleLabel=scaleLabel;
            }
            scaleLabel.labelString=$(this).val();

            const targetScaleLabel=_this.getXScaleLabel();
            targetScaleLabel.labelString=$(this).val();
            _this.cellDef.chartWidget.chart.update();
            setDirty();
        });
        this.xTitleGroup.hide();
    }
    initYAxes(container){
        const legendGroup=$(`<fieldset style="padding: 10px;border:solid 1px #dddddd;border-radius: 8px;">
        <legend style="width: auto;margin-bottom: 1px;border-bottom:none;font-size: inherit;color: #4b4b4b;">${window.i18n.chart.yAxisConfig}</legend></fieldset>`);
        container.append(legendGroup);
        const _this=this;
        const rotationGroup=$(`<div class="form-group" style="margin-bottom: 10px;display:inline-block;margin-right: 20px"><label>${window.i18n.chart.titleRotation}</label></div>`);
        legendGroup.append(rotationGroup);
        this.yAxesRotationEditor=$(`<input type="number" class="form-control" value="0" title="${window.i18n.chart.angleScope}" style="display: inline-block;width: 80px;font-size: 12px;height: 25px;padding: 3px;">`);
        rotationGroup.append(this.yAxesRotationEditor);
        this.yAxesRotationEditor.change(function(){
            const yaxes=_this.getYAxesConfig();
            yaxes.rotation=$(this).val();

            const targetTicks=_this.getYTicks();
            targetTicks.minRotation=yaxes.rotation;
            _this.cellDef.chartWidget.chart.update();
            setDirty();
        });


        const displayGroup=$(`<div class="form-group" style="margin-bottom: 10px"><label>${window.i18n.chart.displayAxisTitle}</label></div>`);
        legendGroup.append(displayGroup);
        this.showYTitleRadio=$(`<label class="checkbox-inline" style="padding-left: 2px"><input type="radio" name="__show_ytitle_radio_${this.id}">${window.i18n.chart.yes}</label>`);
        displayGroup.append(this.showYTitleRadio);
        this.hideYTitleRadio=$(`<label class="checkbox-inline" style="padding-left: 2px"><input type="radio" name="__show_ytitle_radio_${this.id}" checked>${window.i18n.chart.no}</label>`);
        displayGroup.append(this.hideYTitleRadio);
        this.showYTitleRadio.children('input').change(function(){
            _this.yTitleGroup.show();
            const yaxes=_this.getYAxesConfig();
            let scaleLabel=yaxes.scaleLabel;
            if(!scaleLabel){
                scaleLabel={};
                yaxes.scaleLabel=scaleLabel;
            }
            scaleLabel.display=true;

            const targetScaleLabel=_this.getYScaleLabel();
            targetScaleLabel.display=true;
            _this.cellDef.chartWidget.chart.update();
            setDirty();
        });
        this.hideYTitleRadio.children('input').change(function(){
            _this.yTitleGroup.hide();
            const yaxes=_this.getYAxesConfig();
            let scaleLabel=yaxes.scaleLabel;
            if(!scaleLabel){
                scaleLabel={};
                yaxes.scaleLabel=scaleLabel;
            }
            scaleLabel.display=false;

            const targetScaleLabel=_this.getYScaleLabel();
            targetScaleLabel.display=false;
            _this.cellDef.chartWidget.chart.update();
            setDirty();
        });

        this.yTitleGroup=$(`<div class="form-group" style="margin-bottom: 0"><label>${window.i18n.chart.axisTitle}</label></div>`);
        legendGroup.append(this.yTitleGroup);
        this.yTitleEditor=$(`<input type="text" class="form-control" style="display: inline-block;width: 288px;font-size: 12px;height: 25px;padding: 3px;">`);
        this.yTitleGroup.append(this.yTitleEditor);
        this.yTitleEditor.change(function(){
            const yaxes=_this.getYAxesConfig();
            let scaleLabel=yaxes.scaleLabel;
            if(!scaleLabel){
                scaleLabel={};
                yaxes.scaleLabel=scaleLabel;
            }
            scaleLabel.labelString=$(this).val();

            const targetScaleLabel=_this.getYScaleLabel();
            targetScaleLabel.labelString=$(this).val();
            _this.cellDef.chartWidget.chart.update();
            setDirty();
        });
        this.yTitleGroup.hide();
    }

    getXTicks(){
        const chart=this.cellDef.chartWidget.chart;
        let scales=chart.options.scales;
        if(!scales){
            scales={};
            chart.options.scales=scales;
        }
        let xAxes=scales.xAxes;
        if(!xAxes){
            xAxes=[];
            scales.xAxes=xAxes;
        }
        let targetTicks=null;
        for(let axes of xAxes){
            if(axes.ticks){
                targetTicks=axes.ticks;
                break;
            }
        }
        if(!targetTicks){
            targetTicks={};
            xAxes.push(targetTicks);
        }
        return targetTicks;
    }

    getYTicks(){
        const chart=this.cellDef.chartWidget.chart;
        let scales=chart.options.scales;
        if(!scales){
            scales={};
            chart.options.scales=scales;
        }
        let yAxes=scales.yAxes;
        if(!yAxes){
            yAxes=[];
            scales.yAxes=yAxes;
        }
        let targetTicks=null;
        for(let axes of yAxes){
            if(axes.ticks){
                targetTicks=axes.ticks;
                break;
            }
        }
        if(!targetTicks){
            targetTicks={};
            yAxes.push(targetTicks);
        }
        return targetTicks;
    }

    getXScaleLabel(){
        const chart=this.cellDef.chartWidget.chart;
        let scales=chart.options.scales;
        if(!scales){
            scales={};
            chart.options.scales=scales;
        }
        let xAxes=scales.xAxes;
        if(!xAxes){
            xAxes=[];
            scales.xAxes=xAxes;
        }
        let targetScaleLabel=null;
        for(let axes of xAxes){
            if(axes.scaleLabel){
                targetScaleLabel=axes.scaleLabel;
                break;
            }
        }
        if(!targetScaleLabel){
            targetScaleLabel={};
            xAxes.push(targetScaleLabel);
        }
        return targetScaleLabel;
    }

    getYScaleLabel(){
        const chart=this.cellDef.chartWidget.chart;
        let scales=chart.options.scales;
        if(!scales){
            scales={};
            chart.options.scales=scales;
        }
        let yAxes=scales.yAxes;
        if(!yAxes){
            yAxes=[];
            scales.yAxes=yAxes;
        }
        let targetScaleLabel=null;
        for(let axes of yAxes){
            if(axes.scaleLabel){
                targetScaleLabel=axes.scaleLabel;
                break;
            }
        }
        if(!targetScaleLabel){
            targetScaleLabel={};
            yAxes.push(targetScaleLabel);
        }
        return targetScaleLabel;
    }

    getXAxesConfig(){
        let xaxes=this.cellDef.value.chart.xaxes;
        if(!xaxes){
            xaxes={};
            this.cellDef.value.chart.xaxes=xaxes;
        }
        return xaxes;
    }
    getYAxesConfig(){
        let yaxes=this.cellDef.value.chart.yaxes;
        if(!yaxes){
            yaxes={};
            this.cellDef.value.chart.yaxes=yaxes;
        }
        return yaxes;
    }
}