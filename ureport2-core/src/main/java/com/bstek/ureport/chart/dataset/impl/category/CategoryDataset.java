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

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.bstek.ureport.Utils;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.chart.dataset.BaseDataset;
import com.bstek.ureport.chart.dataset.CollectType;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.model.Cell;
import com.bstek.ureport.utils.DataUtils;


/**
 * @author Jacky.gao
 * @since 2017年6月9日
 */
public abstract class CategoryDataset extends BaseDataset {
	private CollectType collectType=CollectType.select;
	
	private String datasetName;
	private String categoryProperty;
	private String seriesProperty;
	private String valueProperty;
	private String seriesText;
	private SeriesType seriesType=SeriesType.text;
	
	private String labels;
	private String format;
	
	protected String buildDatasetJson(Context context,Cell cell,String props){
		List<?> dataList=DataUtils.fetchData(cell, context, datasetName);
		List<Object> categoryList=new ArrayList<Object>();
		Map<Object,Map<Object,List<Object>>> seriesDataMap=new LinkedHashMap<Object,Map<Object,List<Object>>>();
		for(Object obj:dataList){
			Object category=Utils.getProperty(obj, categoryProperty);
			if(category==null){
				continue; 
			}
			if(!categoryList.contains(category)){
				categoryList.add(category);
			}
			Object series=null;
			if(seriesType.equals(SeriesType.property)){
				series=Utils.getProperty(obj, seriesProperty);				
			}else{
				series=seriesText;
			}
			if(series==null){
				continue;
			}
			Object value=Utils.getProperty(obj, valueProperty);
			if(value==null){
				continue;
			}
			if(seriesDataMap.containsKey(series)){
				Map<Object,List<Object>> categoryMap=seriesDataMap.get(series);
				List<Object> valueList=null;
				if(categoryMap.containsKey(category)){
					valueList=categoryMap.get(category);
				}else{
					valueList=new ArrayList<Object>();
					categoryMap.put(category, valueList);
				}
				valueList.add(value);
			}else{
				Map<Object,List<Object>> categoryMap=new LinkedHashMap<Object,List<Object>>();
				seriesDataMap.put(series, categoryMap);
				for(Object cg:categoryList){
					categoryMap.put(cg, new ArrayList<Object>());
				}
				List<Object> valueList=categoryMap.get(category);
				valueList.add(value);
			}
		}
		setLabels(toLabel(categoryList));
		return buildDatasets(seriesDataMap,props);
	}
	
	
	protected String buildDatasets(Map<Object,Map<Object,List<Object>>> map,String props){
		StringBuilder sb=new StringBuilder();
		int i=0; 
		for(Object series:map.keySet()){
			if(i>0){
				sb.append(",");
			}
			sb.append("{");
			sb.append("\"label\":\""+series+"\",");
			String color=null;
			if(this instanceof LineDataset){
				color="rgb("+getRgbColor(i)+")";
			}else{
				color="rgba("+getRgbColor(i)+",0.3)";				
			}
			sb.append("\"backgroundColor\":\""+color+"\",");
			sb.append("\"borderColor\":\"rgb("+getRgbColor(i)+")\",");
			sb.append("\"borderWidth\": 1,");
			sb.append("\"data\":"+buildData(map.get(series)));
			if(this instanceof LineDataset){
				sb.append(",");
				if(this instanceof AreaDataset){
					sb.append("\"fill\":true");					
				}else{
					sb.append("\"fill\":false");									
				}
			}
			if(props!=null){
				sb.append(","+props);
			}
			sb.append("}");
			i++;
		}
		sb.append("");
		return sb.toString();
	}
	
	protected String buildData(Map<Object,List<Object>> categoryMap){
		StringBuilder sb=new StringBuilder();
		sb.append("[");
		for(Object category:categoryMap.keySet()){
			List<Object> list=categoryMap.get(category);
			double data=collectData(list);
			if(sb.length()>1){
				sb.append(",");
			}
			sb.append(data);
		}
		sb.append("]");
		return sb.toString();
	}
	
	protected double collectData(List<Object> list){
		double result=0;
		if(list.size()==0){
			return result;
		}
		switch(collectType){
		case select:
			result = Utils.toBigDecimal(list.get(0)).doubleValue();
			break;
		case avg:
			double total=0;
			for(Object data:list){
				total+=Utils.toBigDecimal(data).doubleValue();
			}
			result=Utils.toBigDecimal(total).divide(Utils.toBigDecimal(list.size()),8,BigDecimal.ROUND_HALF_UP).doubleValue();
			break;
		case count:
			result=list.size();
			break;
		case max:
			Double max=null;
			for(Object data:list){
				double value=Utils.toBigDecimal(data).doubleValue();
				if(max==null){
					max=value;
				}else if(max<value){
					max=value;
				}
			}
			result=max;
			break;
		case min:
			Double min=null;
			for(Object data:list){
				double value=Utils.toBigDecimal(data).doubleValue();
				if(min==null){
					min=value;
				}else if(min>value){
					min=value;
				}
			}
			result=min;
			break;
		case sum:
			for(Object data:list){
				double value=Utils.toBigDecimal(data).doubleValue();
				result+=value;
			}
			break;
		}
		return result;
	}
	private String toLabel(List<Object> categoryList){
		StringBuilder sb=new StringBuilder();
		for(Object obj:categoryList){
			if(sb.length()>0){
				sb.append(",");
			}else{
				sb.append("[");
			}
			if(StringUtils.isNotBlank(format)){
				if(obj instanceof Date){
					Date date=(Date)obj;
					SimpleDateFormat sd=new SimpleDateFormat(format);
					obj=sd.format(date);
				}else{					
					try{
						BigDecimal data=Utils.toBigDecimal(obj);
						DecimalFormat df=new DecimalFormat(format);
						obj=df.format(data.doubleValue());
					}catch(Exception ex){
						throw new ReportComputeException("Can not format data ["+obj+"] use pattern ["+format+"]");
					}
				}
			}
			sb.append("\""+obj+"\"");
		}
		if(sb.length()==0){
			sb.append("[");
		}
		sb.append("]");
		return sb.toString();
	}

	public String getSeriesText() {
		return seriesText;
	}
	
	public void setSeriesText(String seriesText) {
		this.seriesText = seriesText;
	}


	public SeriesType getSeriesType() {
		return seriesType;
	}

	public void setSeriesType(SeriesType seriesType) {
		this.seriesType = seriesType;
	}

	public CollectType getCollectType() {
		return collectType;
	}

	public void setCollectType(CollectType collectType) {
		this.collectType = collectType;
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
	public String getSeriesProperty() {
		return seriesProperty;
	}
	public void setSeriesProperty(String seriesProperty) {
		this.seriesProperty = seriesProperty;
	}
	public String getValueProperty() {
		return valueProperty;
	}
	public void setValueProperty(String valueProperty) {
		this.valueProperty = valueProperty;
	}
	
	public void setLabels(String labels) {
		this.labels = labels;
	}
	public String getLabels() {
		return labels;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
}
