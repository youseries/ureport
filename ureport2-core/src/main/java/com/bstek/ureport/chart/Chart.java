package com.bstek.ureport.chart;

import com.bstek.ureport.build.Context;
import com.bstek.ureport.chart.dataset.Dataset;
import com.bstek.ureport.chart.option.Option;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2017年6月8日
 */
public class Chart {
	private Option option;
	private Dataset dataset;
	public String doCompute(Cell cell, Context context){
		StringBuilder sb=new StringBuilder();
		
		return sb.toString();
	}
	public Option getOption() {
		return option;
	}
	public void setOption(Option option) {
		this.option = option;
	}
	public Dataset getDataset() {
		return dataset;
	}
	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}
}
