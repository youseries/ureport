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
package com.bstek.ureport.chart.dataset.impl;

import java.util.ArrayList;
import java.util.List;

import com.bstek.ureport.build.Context;
import com.bstek.ureport.chart.dataset.Dataset;
import com.bstek.ureport.chart.dataset.impl.category.BarDataset;
import com.bstek.ureport.chart.dataset.impl.category.LineDataset;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2017年6月8日
 */
public class MixDataset implements Dataset {
	private List<BarDataset> barDatasets=new ArrayList<BarDataset>();
	private List<LineDataset> lineDatasets=new ArrayList<LineDataset>();
	
	@Override
	public String buildDataJson(Context context,Cell cell) {
		StringBuilder sb=new StringBuilder();
		sb.append("{");
		sb.append("\"datasets\":[");
		int index=0;
		for(BarDataset ds:barDatasets){
			if(index>0){				
				sb.append(",");
			}
			sb.append(ds.toMixJson(context, cell, index));
		}
		for(LineDataset ds:lineDatasets){
			if(index>0){				
				sb.append(",");
			}
			sb.append(ds.toMixJson(context, cell, index));
		}
		sb.append("],");
		String labels=null;
		if(barDatasets.size()>0){
			labels=barDatasets.get(0).getLabels();
		}else if(lineDatasets.size()>0){
			labels=lineDatasets.get(0).getLabels();
		}else{
			throw new ReportComputeException("Mix chart need one dataset at least.");
		}
		sb.append("labels:"+labels);
		sb.append("}");
		return sb.toString();
	}

	
	@Override
	public String getType() {
		return "bar";
	}

	public List<BarDataset> getBarDatasets() {
		return barDatasets;
	}

	public void setBarDatasets(List<BarDataset> barDatasets) {
		this.barDatasets = barDatasets;
	}

	public List<LineDataset> getLineDatasets() {
		return lineDatasets;
	}

	public void setLineDatasets(List<LineDataset> lineDatasets) {
		this.lineDatasets = lineDatasets;
	}
}
