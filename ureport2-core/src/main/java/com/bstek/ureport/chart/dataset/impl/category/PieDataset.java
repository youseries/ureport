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
