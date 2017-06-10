package com.bstek.ureport.chart.dataset.impl.category;

import com.bstek.ureport.build.Context;
import com.bstek.ureport.chart.dataset.Dataset;
import com.bstek.ureport.chart.dataset.DatasetType;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2017年6月8日
 */
public class BarDataset implements Dataset{
	private double[] data;
	private String label;
	private String backgroundColor;
	private String borderColor;
	private int borderWidth;
	private String hoverBackgroundColor;
	private String hoverBorderColor;
	private String hoverBorderWidth;

	@Override
	public String buildConfiguration(Context context,Cell cell) {
		return null;
	}
	@Override
	public DatasetType getDatasetType() {
		return DatasetType.Bar;
	}
	@Override
	public String type() {
		return "bar";
	}
	public double[] getData() {
		return data;
	}
	public void setData(double[] data) {
		this.data = data;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public String getBorderColor() {
		return borderColor;
	}
	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}
	public int getBorderWidth() {
		return borderWidth;
	}
	public void setBorderWidth(int borderWidth) {
		this.borderWidth = borderWidth;
	}
	public String getHoverBackgroundColor() {
		return hoverBackgroundColor;
	}
	public void setHoverBackgroundColor(String hoverBackgroundColor) {
		this.hoverBackgroundColor = hoverBackgroundColor;
	}
	public String getHoverBorderColor() {
		return hoverBorderColor;
	}
	public void setHoverBorderColor(String hoverBorderColor) {
		this.hoverBorderColor = hoverBorderColor;
	}
	public String getHoverBorderWidth() {
		return hoverBorderWidth;
	}
	public void setHoverBorderWidth(String hoverBorderWidth) {
		this.hoverBorderWidth = hoverBorderWidth;
	}
}
