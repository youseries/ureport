/*******************************************************************************
 * Copyright 2017 Bstek
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.bstek.ureport.parser.impl.value;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

import com.bstek.ureport.chart.Chart;
import com.bstek.ureport.chart.axes.BaseAxes;
import com.bstek.ureport.chart.axes.ScaleLabel;
import com.bstek.ureport.chart.axes.impl.XAxes;
import com.bstek.ureport.chart.axes.impl.YAxes;
import com.bstek.ureport.chart.dataset.CollectType;
import com.bstek.ureport.chart.dataset.Dataset;
import com.bstek.ureport.chart.dataset.impl.BubbleDataset;
import com.bstek.ureport.chart.dataset.impl.ScatterDataset;
import com.bstek.ureport.chart.dataset.impl.category.AreaDataset;
import com.bstek.ureport.chart.dataset.impl.category.BarDataset;
import com.bstek.ureport.chart.dataset.impl.category.CategoryDataset;
import com.bstek.ureport.chart.dataset.impl.category.DoughnutDataset;
import com.bstek.ureport.chart.dataset.impl.category.HorizontalBarDataset;
import com.bstek.ureport.chart.dataset.impl.category.LineDataset;
import com.bstek.ureport.chart.dataset.impl.category.PieDataset;
import com.bstek.ureport.chart.dataset.impl.category.PolarDataset;
import com.bstek.ureport.chart.dataset.impl.category.RadarDataset;
import com.bstek.ureport.chart.dataset.impl.category.SeriesType;
import com.bstek.ureport.chart.option.Easing;
import com.bstek.ureport.chart.option.Option;
import com.bstek.ureport.chart.option.Position;
import com.bstek.ureport.chart.option.impl.AnimationsOption;
import com.bstek.ureport.chart.option.impl.LegendOption;
import com.bstek.ureport.chart.option.impl.TitleOption;
import com.bstek.ureport.chart.plugins.DataLabelsPlugin;
import com.bstek.ureport.definition.value.ChartValue;
import com.bstek.ureport.definition.value.Value;
import com.bstek.ureport.exception.ReportParseException;

/**
 * @author Jacky.gao
 * @since 2017年6月28日
 */
public class ChartValueParser extends ValueParser {

	@Override
	public Value parse(Element element) {
		ChartValue value=new ChartValue();
		Chart chart=new Chart();
		value.setChart(chart);
		for(Object obj:element.elements()){
			if(obj==null || !(obj instanceof Element)){
				continue;
			}
			Element ele=(Element)obj;
			String name=ele.getName();
			if(name.equals("dataset")){
				Dataset dataset=parseDataset(ele);
				chart.setDataset(dataset);
			}else if(name.equals("xaxes")){
				XAxes xaxes=new XAxes();
				parseAxes(ele, xaxes);
				chart.setXaxes(xaxes);
			}else if(name.equals("yaxes")){
				YAxes yaxes=new YAxes();
				parseAxes(ele, yaxes);
				chart.setYaxes(yaxes);
			}else if(name.equals("option")){
				chart.getOptions().add(parseOption(ele));
			}else if(name.equals("plugin")) {
				String pluginName=ele.attributeValue("name");
				if(pluginName.equals("data-labels")) {					
					DataLabelsPlugin plugin=new DataLabelsPlugin();
					plugin.setDisplay(Boolean.valueOf(ele.attributeValue("display")));
					chart.getPlugins().add(plugin);
				}
			}
		}
		return value;
	}
	
	private Option parseOption(Element element){
		String type=element.attributeValue("type");
		Option target=null;
		if(type.equals("title")){
			TitleOption option=new TitleOption();
			String display=element.attributeValue("display");
			if(StringUtils.isNotBlank(display)){
				option.setDisplay(Boolean.valueOf(display));
			}
			String position=element.attributeValue("position");
			if(StringUtils.isNotBlank(position)){
				option.setPosition(Position.valueOf(position));
			}
			String text=element.attributeValue("text");
			option.setText(text);
			target=option;
		}else if(type.equals("legend")){
			LegendOption option=new LegendOption();
			String display=element.attributeValue("display");
			if(StringUtils.isNotBlank(display)){
				option.setDisplay(Boolean.valueOf(display));
			}
			String position=element.attributeValue("position");
			if(StringUtils.isNotBlank(position)){
				option.setPosition(Position.valueOf(position));
			}
			target=option;
		}else if(type.equals("animation")){
			AnimationsOption option=new AnimationsOption();
			String duration=element.attributeValue("duration");
			if(StringUtils.isNotBlank(duration)){
				option.setDuration(Integer.valueOf(duration));
			}
			String easing=element.attributeValue("easing");
			if(StringUtils.isNotBlank(easing)){
				option.setEasing(Easing.valueOf(easing));
			}
			target=option;
		}
		if(target!=null){
			return target;			
		}
		throw new ReportParseException("Unknow option :"+type);
	}
	
	private void parseAxes(Element element,BaseAxes axes){
		String rotation=element.attributeValue("rotation");
		if(StringUtils.isNotBlank(rotation)){
			axes.setRotation(Integer.valueOf(rotation));
		}
		for(Object obj:element.elements()){
			if(obj==null || !(obj instanceof Element)){
				continue;
			}
			Element ele=(Element)obj;
			String name=ele.getName();
			if(name.equals("scale-label")){
				ScaleLabel label=new ScaleLabel();
				String display=ele.attributeValue("display");
				if(StringUtils.isNotBlank(display)){
					label.setDisplay(Boolean.valueOf(display));
				}
				String labelString=ele.attributeValue("label-string");
				label.setLabelString(labelString);
				axes.setScaleLabel(label);
				break;
			}
		}
	}
	
	private Dataset parseDataset(Element element){
		String type=element.attributeValue("type");
		Dataset dataset=null;
		if(type.equals("area")){
			AreaDataset ds=new AreaDataset();
			dataset=ds;
		}else if(type.equals("line")){
			LineDataset ds=new LineDataset();
			dataset=ds;
		}else if(type.equals("bar")){
			BarDataset ds=new BarDataset();
			dataset=ds;
		}else if(type.equals("doughnut")){
			DoughnutDataset ds=new DoughnutDataset();
			dataset=ds;
		}else if(type.equals("horizontalBar")){
			HorizontalBarDataset ds=new HorizontalBarDataset();
			dataset=ds;
		}else if(type.equals("pie")){
			PieDataset ds=new PieDataset();
			dataset=ds;
		}else if(type.equals("polarArea")){
			PolarDataset ds=new PolarDataset();
			dataset=ds;
		}else if(type.equals("radar")){
			RadarDataset ds=new RadarDataset();
			dataset=ds;
		}else if(type.equals("bubble")){
			BubbleDataset ds=new BubbleDataset();
			String datasetName=element.attributeValue("dataset-name");
			ds.setDatasetName(datasetName);
			ds.setCategoryProperty(element.attributeValue("category-property"));
			ds.setxProperty(element.attributeValue("x-property"));
			ds.setyProperty(element.attributeValue("y-property"));
			ds.setrProperty(element.attributeValue("r-property"));
			dataset=ds;
		}else if(type.equals("scatter")){
			ScatterDataset ds=new ScatterDataset();
			String datasetName=element.attributeValue("dataset-name");
			ds.setDatasetName(datasetName);
			ds.setCategoryProperty(element.attributeValue("category-property"));
			ds.setxProperty(element.attributeValue("x-property"));
			ds.setyProperty(element.attributeValue("y-property"));
			dataset=ds;
		}
		if(dataset!=null && dataset instanceof CategoryDataset){
			CategoryDataset ds=(CategoryDataset)dataset;
			String datasetName=element.attributeValue("dataset-name");
			ds.setDatasetName(datasetName);
			String format=element.attributeValue("format");
			ds.setFormat(format);
			String categoryProperty=element.attributeValue("category-property");
			ds.setCategoryProperty(categoryProperty);
			String valueProperty=element.attributeValue("value-property");
			ds.setValueProperty(valueProperty);
			String seriesProperty=element.attributeValue("series-property");
			ds.setSeriesProperty(seriesProperty);
			String collectType=element.attributeValue("collect-type");
			if(StringUtils.isNotBlank(collectType)){
				ds.setCollectType(CollectType.valueOf(collectType));
			}
			String seriesType=element.attributeValue("series-type");
			if(StringUtils.isNotBlank(seriesType)){
				ds.setSeriesType(SeriesType.valueOf(seriesType));
			}
			ds.setSeriesText(element.attributeValue("series-text"));
		}
		if(dataset!=null){
			return dataset;
		}
		throw new ReportParseException("Unknow chart type : "+type);
	}
}
