package com.bstek.ureport.chart.dataset.impl.category;

import java.util.List;

/**
 * @author Jacky.gao
 * @since 2017年6月9日
 */
public class CategoryData {
	private String label;
	private List<Double> data;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public List<Double> getData() {
		return data;
	}
	public void setData(List<Double> data) {
		this.data = data;
	}
}
