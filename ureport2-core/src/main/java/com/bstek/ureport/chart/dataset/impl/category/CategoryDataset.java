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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.bstek.ureport.Utils;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.chart.dataset.BaseDataset;
import com.bstek.ureport.chart.dataset.CollectType;
import com.bstek.ureport.chart.dataset.Source;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.model.Cell;
import com.bstek.ureport.utils.DataUtils;


/**
 * @author Jacky.gao
 * @since 2017年6月9日
 */
public abstract class CategoryDataset extends BaseDataset {
	private Source source=Source.dataset;
	private CollectType collectType=CollectType.select;
	
	private String datasetName;
	private String categoryProperty;
	private String seriesProperty;
	private String valueProperty;
	
	private String categoryExpression;
	private String seriesExpression;
	private String valueExpression;
	
	private String labels;
	private String format;
	
	protected String buildDatasetJson(Context context,Cell cell){
		List<?> dataList=DataUtils.fetchData(cell, context, datasetName);
		List<Object> categoryList=new ArrayList<Object>();
		Map<Object,Map<Object,List<Double>>> seriesDataMap=new HashMap<Object,Map<Object,List<Double>>>();
		for(Object obj:dataList){
			Object category=Utils.getProperty(obj, categoryProperty);
			if(category==null){
				continue; 
			}
			if(!categoryList.contains(category)){
				categoryList.add(category);
			}
			
			Object series=Utils.getProperty(obj, seriesProperty);
			if(series==null){
				continue;
			}
			Object value=Utils.getProperty(obj, valueProperty);
			double data=0;
			if(value!=null && StringUtils.isNotBlank(value.toString())){
				data=Utils.toBigDecimal(value).doubleValue();
			}
			if(seriesDataMap.containsKey(series)){
				Map<Object,List<Double>> categoryMap=seriesDataMap.get(series);
				List<Double> valueList=null;
				if(categoryMap.containsKey(category)){
					valueList=categoryMap.get(category);
				}else{
					valueList=new ArrayList<Double>();
					categoryMap.put(category, valueList);
				}
				valueList.add(data);
			}else{
				Map<Object,List<Double>> categoryMap=new HashMap<Object,List<Double>>();
				for(Object cg:categoryList){
					categoryMap.put(cg, new ArrayList<Double>());
				}
				List<Double> valueList=categoryMap.get(category);
				valueList.add(data);
			}
		}
		setLabels(toLabel(categoryList));
		return buildDatasets(seriesDataMap);
	}
	
	private String buildDatasets(Map<Object,Map<Object,List<Double>>> map){
		StringBuilder sb=new StringBuilder();
		sb.append("[");
		int i=0; 
		for(Object series:map.keySet()){
			if(i>0){
				sb.append(",");
			}
			i++;
			sb.append("{");
			sb.append("label:\""+series+"\",");
			String color=null;
			if(this instanceof LineDataset){
				color="rgb("+getRgbColor(i)+")";
			}else{
				color="rgba("+getRgbColor(i)+",0.5)";				
			}
			sb.append("backgroundColor:\""+color+"\",");
			sb.append("borderColor:\"rgb("+getRgbColor(i)+")\",");
			sb.append("data:"+buildData(map.get(series)));
			if(this instanceof LineDataset){
				sb.append(",");
				if(this instanceof AreaDataset){
					sb.append("fill:true");					
				}else{
					sb.append("fill:false");									
				}
			}
			sb.append("}");
		}
		sb.append("]");
		return sb.toString();
	}
	
	private String buildData(Map<Object,List<Double>> categoryMap){
		StringBuilder sb=new StringBuilder();
		sb.append("[");
		for(Object category:categoryMap.keySet()){
			List<Double> list=categoryMap.get(category);
			double data=collectData(list);
			if(sb.length()>1){
				sb.append(",");
			}
			sb.append(data);
		}
		sb.append("]");
		return sb.toString();
	}
	
	private double collectData(List<Double> list){
		double result=0;
		switch(collectType){
		case select:
			result = list.get(0);
			break;
		case avg:
			double total=0;
			for(double data:list){
				total+=data;
			}
			result=Utils.toBigDecimal(total).divide(Utils.toBigDecimal(list.size()),8,BigDecimal.ROUND_HALF_UP).doubleValue();
			break;
		case count:
			result=list.size();
			break;
		case max:
			Double max=null;
			for(double data:list){
				if(max==null){
					max=data;
				}else if(max<data){
					max=data;
				}
			}
			result=max;
			break;
		case min:
			Double min=null;
			for(double data:list){
				if(min==null){
					min=data;
				}else if(min>data){
					min=data;
				}
			}
			result=min;
			break;
		case sum:
			for(double data:list){
				result+=data;
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

	public CollectType getCollectType() {
		return collectType;
	}

	public void setCollectType(CollectType collectType) {
		this.collectType = collectType;
	}

	public Source getSource() {
		return source;
	}
	public void setSource(Source source) {
		this.source = source;
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
	public String getCategoryExpression() {
		return categoryExpression;
	}
	public void setCategoryExpression(String categoryExpression) {
		this.categoryExpression = categoryExpression;
	}
	public String getSeriesExpression() {
		return seriesExpression;
	}
	public void setSeriesExpression(String seriesExpression) {
		this.seriesExpression = seriesExpression;
	}
	public String getValueExpression() {
		return valueExpression;
	}
	public void setValueExpression(String valueExpression) {
		this.valueExpression = valueExpression;
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
