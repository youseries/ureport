package com.bstek.ureport.chart.dataset.impl.category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bstek.ureport.Utils;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.chart.dataset.BaseDataset;
import com.bstek.ureport.chart.dataset.Source;
import com.bstek.ureport.definition.value.AggregateType;
import com.bstek.ureport.model.Cell;


/**
 * @author Jacky.gao
 * @since 2017年6月9日
 */
public abstract class CategoryDataset extends BaseDataset {
	private Source source=Source.dataset;
	private AggregateType aggregateType=AggregateType.select;
	
	private String datasetName;
	private String categoryProperty;
	private String seriesProperty;
	private String valueProperty;
	
	
	private String categoryExpression;
	private String seriesExpression;
	private String valueExpression;
	
	protected List<CategoryData> buildCategoryDatas(Context context,Cell cell){
		List<CategoryData> list=new ArrayList<CategoryData>();
		List<?> data=context.getDatasetData(datasetName);
		List<Object> categoryList=new ArrayList<Object>();
		Map<Object,List<Object>> seriesMap=new HashMap<Object,List<Object>>(); 
		for(Object obj:data){
			Object category=Utils.getProperty(obj, categoryProperty);
			if(category!=null){
				if(!categoryList.contains(category)){
					categoryList.add(category);
				}
			}
			Object series=Utils.getProperty(obj, seriesProperty);
			if(series!=null){
				List<Object> valueList;
				if(seriesMap.containsKey(series)){
					valueList=seriesMap.get(series);
				}else{
					valueList=new ArrayList<Object>();
					seriesMap.put(series, valueList);
				}
				Object value=Utils.getProperty(obj, valueProperty);
				valueList.add(value);
			}
		}
		return list;
	}
	
	public AggregateType getAggregateType() {
		return aggregateType;
	}

	public void setAggregateType(AggregateType aggregateType) {
		this.aggregateType = aggregateType;
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
}
