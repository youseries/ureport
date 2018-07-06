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
package com.bstek.ureport.chart;

import java.util.ArrayList;
import java.util.List;

import com.bstek.ureport.build.Context;
import com.bstek.ureport.chart.axes.impl.XAxes;
import com.bstek.ureport.chart.axes.impl.YAxes;
import com.bstek.ureport.chart.dataset.Dataset;
import com.bstek.ureport.chart.dataset.impl.BubbleDataset;
import com.bstek.ureport.chart.dataset.impl.ScatterDataset;
import com.bstek.ureport.chart.dataset.impl.category.BarDataset;
import com.bstek.ureport.chart.dataset.impl.category.LineDataset;
import com.bstek.ureport.chart.option.Option;
import com.bstek.ureport.chart.plugins.Plugin;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2017年6月8日
 */
public class Chart {
	private Dataset dataset;
	private XAxes xaxes;
	private YAxes yaxes;
	private List<Option> options=new ArrayList<Option>();
	private List<Plugin> plugins=new ArrayList<Plugin>();
	public ChartData doCompute(Cell cell, Context context){
		StringBuilder sb=new StringBuilder();
		sb.append("{");
		sb.append("\"type\":\""+dataset.getType()+"\",");
		sb.append("\"data\":"+dataset.buildDataJson(context, cell)+",");
		sb.append("\"options\":{");
		boolean withoption=false;
		if(options!=null && options.size()>0){
			for(int i=0;i<options.size();i++){
				Option option=options.get(i);
				if(i>0){
					sb.append(",");
				}
				sb.append(option.buildOptionJson());
				withoption=true;
			}
		}
		if(plugins!=null && plugins.size()>0) {
			if(withoption){				
				sb.append(",");
			}
			withoption=true;
			sb.append("\"plugins\": {");
			for(Plugin plugin:plugins) {
				String pluginJson=plugin.toJson(dataset.getType());
				if(pluginJson!=null) {
					sb.append(pluginJson);
				}
			}
			sb.append("}");
		}else {
			withoption=true;
			sb.append("\"plugins\": {");
			sb.append("\"datalabels\":{\"display\":false}");
			sb.append("}");
		}
		if(xaxes!=null || yaxes!=null){
			if(withoption){
				sb.append(",");
			}
			withoption=true;
			sb.append("\"scales\":{");
			if(xaxes!=null){
				sb.append("\"xAxes\":[");
				sb.append(xaxes.toJson());
				sb.append("]");
			}
			if(yaxes!=null){
				if(xaxes!=null){
					sb.append(",\"yAxes\":[");					
				}else{
					sb.append("\"yAxes\":[");										
				}
				sb.append(yaxes.toJson());
				sb.append("]");
			}else{
				if(hasYAxes(dataset)){
					sb.append(",\"yAxes\":[{\"ticks\":{\"min\":0}}]");					
				}
			}
			sb.append("}");
		}else{
			if(withoption && hasYAxes(dataset)){
				sb.append(",");
				sb.append("\"scales\":{\"yAxes\":[]}");				
			}
		}
		sb.append("}");
		sb.append("}");
		ChartData chartData=new ChartData(sb.toString(),cell);
		context.addChartData(chartData);
		return chartData;
	}

	private boolean hasYAxes(Dataset dataset){
		if(dataset instanceof BarDataset){
			return true;
		}
		if(dataset instanceof LineDataset){
			return true;
		}
		if(dataset instanceof BubbleDataset){
			return true;
		}
		if(dataset instanceof ScatterDataset){
			return true;
		}
		return false;
	}
	
	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	public List<Plugin> getPlugins() {
		return plugins;
	}

	public void setPlugins(List<Plugin> plugins) {
		this.plugins = plugins;
	}

	public Dataset getDataset() {
		return dataset;
	}
	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}

	public XAxes getXaxes() {
		return xaxes;
	}

	public void setXaxes(XAxes xaxes) {
		this.xaxes = xaxes;
	}

	public YAxes getYaxes() {
		return yaxes;
	}

	public void setYaxes(YAxes yaxes) {
		this.yaxes = yaxes;
	}
}
