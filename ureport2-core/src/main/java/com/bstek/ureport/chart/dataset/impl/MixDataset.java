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
