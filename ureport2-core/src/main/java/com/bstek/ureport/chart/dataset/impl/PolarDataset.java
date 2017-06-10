package com.bstek.ureport.chart.dataset.impl;

import com.bstek.ureport.build.Context;
import com.bstek.ureport.chart.dataset.Dataset;
import com.bstek.ureport.chart.dataset.DatasetType;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2017年6月8日
 */
public class PolarDataset implements Dataset {
	private double[] data;
	private String label;
	private String backgroundColor;
	private String borderColor;
	private int borderWidth;
	private String hoverBackgroundColor;
	private String hoverBorderColor;
	private int hoverBorderWidth;
	@Override
	public String buildConfiguration(Context context,Cell cell) {
		return null;
	}

	@Override
	public DatasetType getDatasetType() {
		return DatasetType.Polar;
	}

	@Override
	public String type() {
		return "polarArea";
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

	public int getHoverBorderWidth() {
		return hoverBorderWidth;
	}

	public void setHoverBorderWidth(int hoverBorderWidth) {
		this.hoverBorderWidth = hoverBorderWidth;
	}
}
