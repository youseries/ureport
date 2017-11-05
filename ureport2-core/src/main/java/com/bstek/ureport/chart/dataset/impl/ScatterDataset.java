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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.bstek.ureport.Utils;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.chart.dataset.BaseDataset;
import com.bstek.ureport.chart.dataset.ScatterData;
import com.bstek.ureport.model.Cell;
import com.bstek.ureport.utils.DataUtils;

/**
 * @author Jacky.gao
 * @since 2017年6月8日
 */
public class ScatterDataset extends BaseDataset {
	private String datasetName;
	private String categoryProperty;
	private String xProperty;
	private String yProperty;
	
	private boolean fill=true;
	private double lineTension=0.2;
	
	@Override
	public String buildDataJson(Context context,Cell cell) {
		List<?> dataList=DataUtils.fetchData(cell, context, datasetName);
		Map<Object,List<ScatterData>> map=new LinkedHashMap<Object,List<ScatterData>>();
		for(Object obj:dataList){
			Object category=Utils.getProperty(obj, categoryProperty);
			if(category==null){
				continue;
			}
			Object xValue=Utils.getProperty(obj, xProperty);
			Object yValue=Utils.getProperty(obj, yProperty);
			if(xValue==null || yValue==null){
				continue;
			}
			double x=Utils.toBigDecimal(xValue).doubleValue();
			double y=Utils.toBigDecimal(yValue).doubleValue();
			List<ScatterData> list=null;
			if(map.containsKey(category)){
				list=map.get(category);
			}else{
				list=new ArrayList<ScatterData>();
				map.put(category, list);
			}
			list.add(new ScatterData(x,y));
		}
		StringBuilder sb=new StringBuilder();
		sb.append("{");
		sb.append("\"datasets\":[");
		int index=0;
		for(Object obj:map.keySet()){
			if(index>0){
				sb.append(",");
			}
			sb.append("{");
			sb.append("\"label\":\""+obj+"\",");
			sb.append("\"fill\":"+fill+",");
			sb.append("\"lineTension\":"+lineTension+",");
			sb.append("\"borderColor\":\"rgb("+getRgbColor(index)+")\",");
			sb.append("\"backgroundColor\":\"rgba("+getRgbColor(index)+",0.5)\",");
			sb.append("\"data\":[");
			List<ScatterData> list=map.get(obj);
			int i=0;
			for(ScatterData data:list){
				if(i>0){
					sb.append(",");
				}
				i++;
				sb.append("{");				
				sb.append("\"x\":"+data.getX()+",");				
				sb.append("\"y\":"+data.getY()+"");				
				sb.append("}");				
			}
			sb.append("]");
			sb.append("}");
			index++;
		}
		sb.append("]");
		sb.append("}");
		return sb.toString();
	}
	@Override
	public String getType() {
		return "scatter";
	}
	
	public String getDatasetName() {
		return datasetName;
	}
	public void setDatasetName(String datasetName) {
		this.datasetName = datasetName;
	}
	public String getCategoryProperty() {
		return categoryProperty;
	}
	public void setCategoryProperty(String categoryProperty) {
		this.categoryProperty = categoryProperty;
	}
	public String getxProperty() {
		return xProperty;
	}
	public void setxProperty(String xProperty) {
		this.xProperty = xProperty;
	}
	public String getyProperty() {
		return yProperty;
	}
	public void setyProperty(String yProperty) {
		this.yProperty = yProperty;
	}
	public boolean isFill() {
		return fill;
	}
	public void setFill(boolean fill) {
		this.fill = fill;
	}
	public double getLineTension() {
		return lineTension;
	}
	public void setLineTension(double lineTension) {
		this.lineTension = lineTension;
	}
}
