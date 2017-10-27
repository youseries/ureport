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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.bstek.ureport.Utils;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.chart.dataset.BaseDataset;
import com.bstek.ureport.chart.dataset.BubbleData;
import com.bstek.ureport.model.Cell;
import com.bstek.ureport.utils.DataUtils;

/**
 * @author Jacky.gao
 * @since 2017年6月8日
 */
public class BubbleDataset extends BaseDataset {
	private String datasetName;
	private String xProperty;
	private String yProperty;
	private String rProperty;
	private String categoryProperty;
	
	@Override
	public String buildDataJson(Context context,Cell cell) {
		Map<Object,List<BubbleData>> map=new LinkedHashMap<Object,List<BubbleData>>();
		List<?> dataList=DataUtils.fetchData(cell, context, datasetName);
		for(Object obj:dataList){
			if(obj==null){
				continue;
			}
			Object categoryValue=Utils.getProperty(obj, categoryProperty);
			if(categoryValue==null){
				continue;
			}
			Object xValue=Utils.getProperty(obj, xProperty);
			Object yValue=Utils.getProperty(obj, yProperty);
			Object rValue=Utils.getProperty(obj, rProperty);
			if(xValue==null || yValue==null || rValue==null){
				continue;
			}
			List<BubbleData> list=null; 
			if(map.containsKey(categoryValue)){
				list=map.get(categoryValue);
			}else{
				list=new ArrayList<BubbleData>();
				map.put(categoryValue, list);
			}
			double x=Utils.toBigDecimal(xValue).doubleValue();
			double y=Utils.toBigDecimal(yValue).doubleValue();
			double r=Utils.toBigDecimal(rValue).doubleValue();
			list.add(new BubbleData(x,y,r));
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
			sb.append("\"borderColor\":\"rgb("+getRgbColor(index)+")\",");
			sb.append("\"backgroundColor\":\"rgba("+getRgbColor(index)+",0.5)\",");
			sb.append("\"label\":\""+obj+"\",");
			sb.append("\"data\":[");
			List<BubbleData> list=map.get(obj);
			int i=0;
			for(BubbleData data:list){
				if(i>0){
					sb.append(",");
				}
				i++;
				sb.append("{");				
				sb.append("\"x\":"+data.getX()+",");				
				sb.append("\"y\":"+data.getY()+",");				
				sb.append("\"r\":"+data.getR());				
				sb.append("}");				
			}
			sb.append("]");
			sb.append("}");
			index++;
		}
		sb.append("]}");
		return sb.toString();
	}
	@Override
	public String getType() {
		return "bubble";
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
	public String getrProperty() {
		return rProperty;
	}
	public void setrProperty(String rProperty) {
		this.rProperty = rProperty;
	}
}
