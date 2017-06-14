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

import com.bstek.ureport.build.Context;
import com.bstek.ureport.chart.dataset.Dataset;
import com.bstek.ureport.chart.dataset.DatasetType;
import com.bstek.ureport.chart.dataset.PointStyle;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2017年6月8日
 */
public class RadarDataset implements Dataset {
	private double[] data;
	private String label;
	private String backgroundColor;
	private String borderColor;
	private int borderWidth;
	private boolean fill;
	private double lineTension=0.1;
	private String pointBackgroundColor;
	private String pointBorderColor;
	private int pointBorderWidth;
	private int pointRadius;
	private PointStyle pointStyle;
	private int pointHitRadius;
	private String pointHoverBackgroundColor;
	private String pointHoverBorderColor;
	private int pointHoverBorderWidth;
	private int pointHoverRadius;
	
	@Override
	public String buildDataJson(Context context,Cell cell) {
		return null;
	}

	@Override
	public DatasetType getDatasetType() {
		return DatasetType.Radar;
	}
	
	@Override
	public String type() {
		return "radar";
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

	public String getPointBackgroundColor() {
		return pointBackgroundColor;
	}

	public void setPointBackgroundColor(String pointBackgroundColor) {
		this.pointBackgroundColor = pointBackgroundColor;
	}

	public String getPointBorderColor() {
		return pointBorderColor;
	}

	public void setPointBorderColor(String pointBorderColor) {
		this.pointBorderColor = pointBorderColor;
	}

	public int getPointBorderWidth() {
		return pointBorderWidth;
	}

	public void setPointBorderWidth(int pointBorderWidth) {
		this.pointBorderWidth = pointBorderWidth;
	}

	public int getPointRadius() {
		return pointRadius;
	}

	public void setPointRadius(int pointRadius) {
		this.pointRadius = pointRadius;
	}

	public PointStyle getPointStyle() {
		return pointStyle;
	}

	public void setPointStyle(PointStyle pointStyle) {
		this.pointStyle = pointStyle;
	}

	public int getPointHitRadius() {
		return pointHitRadius;
	}

	public void setPointHitRadius(int pointHitRadius) {
		this.pointHitRadius = pointHitRadius;
	}

	public String getPointHoverBackgroundColor() {
		return pointHoverBackgroundColor;
	}

	public void setPointHoverBackgroundColor(String pointHoverBackgroundColor) {
		this.pointHoverBackgroundColor = pointHoverBackgroundColor;
	}

	public String getPointHoverBorderColor() {
		return pointHoverBorderColor;
	}

	public void setPointHoverBorderColor(String pointHoverBorderColor) {
		this.pointHoverBorderColor = pointHoverBorderColor;
	}

	public int getPointHoverBorderWidth() {
		return pointHoverBorderWidth;
	}

	public void setPointHoverBorderWidth(int pointHoverBorderWidth) {
		this.pointHoverBorderWidth = pointHoverBorderWidth;
	}

	public int getPointHoverRadius() {
		return pointHoverRadius;
	}

	public void setPointHoverRadius(int pointHoverRadius) {
		this.pointHoverRadius = pointHoverRadius;
	}
}
