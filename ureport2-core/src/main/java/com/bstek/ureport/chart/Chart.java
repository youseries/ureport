/*******************************************************************************
 * Copyright (C) 2017 Bstek.com
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2017年6月8日
 */
public class Chart {
	private List<Option> options=new ArrayList<Option>();
	private Dataset dataset;
	private XAxes xaxes;
	private YAxes yaxes;
	public ChartData doCompute(Cell cell, Context context){
		StringBuilder sb=new StringBuilder();
		sb.append("{");
		sb.append("\"type\":\""+dataset.getType()+"\",");
		sb.append("\"data\":"+dataset.buildDataJson(context, cell)+",");
		sb.append("\"options\":{");
		if(options!=null && options.size()>0){
			for(int i=0;i<options.size();i++){
				Option option=options.get(i);
				if(i>0){
					sb.append(",");
				}
				sb.append(option.buildOptionJson());
			}
		}
		if(xaxes!=null || yaxes!=null){
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
			if(hasYAxes(dataset)){
				sb.append("\"scales\":{\"yAxes\":[{\"ticks\":{\"min\":0}}]}");				
			}
		}
		sb.append("}");
		sb.append("}");
		ChartData chartData=new ChartData(sb.toString());
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
