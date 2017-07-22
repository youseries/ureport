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
package com.bstek.ureport.chart.dataset.impl.category;

import java.util.List;
import java.util.Map;

import com.bstek.ureport.build.Context;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2017年6月8日
 */
public class PieDataset extends CategoryDataset {
	@Override
	public String buildDataJson(Context context,Cell cell) {
		String datasetJson=buildDatasetJson(context, cell,null);
		StringBuilder sb=new StringBuilder();
		sb.append("{");
		String labels=getLabels();
		sb.append("\"labels\":"+labels+",");
		sb.append("\"datasets\":["+datasetJson+"]");
		sb.append("}");
		return sb.toString();
	}
	@Override
	protected String buildDatasets(Map<Object,Map<Object,List<Object>>> map,String props){
		StringBuilder sb=new StringBuilder();
		int i=0; 
		for(Object series:map.keySet()){
			if(i>0){
				sb.append(",");
			}
			sb.append("{");
			sb.append("\"label\":\""+series+"\",");
			Map<Object,List<Object>> categoryMap=map.get(series);
			sb.append("\"backgroundColor\":"+buildBackgroundColor(i, categoryMap.size())+",");
			sb.append("\"data\":"+buildData(categoryMap));
			if(props!=null){
				sb.append(","+props);
			}
			sb.append("}");
			i++;
		}
		return sb.toString();
	}
	
	private String buildBackgroundColor(int start,int size){
		StringBuilder sb=new StringBuilder();
		sb.append("[");
		for(int i=start;i<size;i++){
			String color=getRgbColor(i);
			if(sb.length()>1){
				sb.append(",");
			}
			sb.append("\"rgb("+color+")\"");
		}
		sb.append("]");
		return sb.toString();
	}
	
	@Override
	public String getType() {
		return "pie";
	}
}
