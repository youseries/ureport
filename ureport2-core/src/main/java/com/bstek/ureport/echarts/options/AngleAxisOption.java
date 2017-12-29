package com.bstek.ureport.echarts.options;

import java.util.List;

import com.bstek.ureport.echarts.options.prop.AxisData;
import com.bstek.ureport.echarts.options.prop.AxisLabel;
import com.bstek.ureport.echarts.options.prop.AxisLine;
import com.bstek.ureport.echarts.options.prop.AxisPointer;
import com.bstek.ureport.echarts.options.prop.AxisTick;
import com.bstek.ureport.echarts.options.prop.AxisType;
import com.bstek.ureport.echarts.options.prop.SplitArea;
import com.bstek.ureport.echarts.options.prop.SplitLine;

/**
 * @author Jacky.gao
 * @since 2017年12月27日
 */
public class AngleAxisOption {
	private int startAngle=90;
	private boolean clockwise=true;
	private AxisType type;
	private String min;
	private String max;
	private boolean scale;
	private int splitNumber=5;
	private int minInterval;
	private String maxInterval;
	private String interval;
	private int logBase=10;
	private AxisLine axisLine;
	private AxisTick axisTick;
	private AxisLabel axisLabel;
	private SplitLine splitLine;
	private SplitArea splitArea;
	private List<AxisData> data;
	private AxisPointer axisPointer;
	public int getStartAngle() {
		return startAngle;
	}
	public void setStartAngle(int startAngle) {
		this.startAngle = startAngle;
	}
	public boolean isClockwise() {
		return clockwise;
	}
	public void setClockwise(boolean clockwise) {
		this.clockwise = clockwise;
	}
	public AxisType getType() {
		return type;
	}
	public void setType(AxisType type) {
		this.type = type;
	}
	public String getMin() {
		return min;
	}
	public void setMin(String min) {
		this.min = min;
	}
	public String getMax() {
		return max;
	}
	public void setMax(String max) {
		this.max = max;
	}
	public boolean isScale() {
		return scale;
	}
	public void setScale(boolean scale) {
		this.scale = scale;
	}
	public int getSplitNumber() {
		return splitNumber;
	}
	public void setSplitNumber(int splitNumber) {
		this.splitNumber = splitNumber;
	}
	public int getMinInterval() {
		return minInterval;
	}
	public void setMinInterval(int minInterval) {
		this.minInterval = minInterval;
	}
	public String getMaxInterval() {
		return maxInterval;
	}
	public void setMaxInterval(String maxInterval) {
		this.maxInterval = maxInterval;
	}
	public String getInterval() {
		return interval;
	}
	public void setInterval(String interval) {
		this.interval = interval;
	}
	public int getLogBase() {
		return logBase;
	}
	public void setLogBase(int logBase) {
		this.logBase = logBase;
	}
	public AxisLine getAxisLine() {
		return axisLine;
	}
	public void setAxisLine(AxisLine axisLine) {
		this.axisLine = axisLine;
	}
	public AxisTick getAxisTick() {
		return axisTick;
	}
	public void setAxisTick(AxisTick axisTick) {
		this.axisTick = axisTick;
	}
	public AxisLabel getAxisLabel() {
		return axisLabel;
	}
	public void setAxisLabel(AxisLabel axisLabel) {
		this.axisLabel = axisLabel;
	}
	public SplitLine getSplitLine() {
		return splitLine;
	}
	public void setSplitLine(SplitLine splitLine) {
		this.splitLine = splitLine;
	}
	public SplitArea getSplitArea() {
		return splitArea;
	}
	public void setSplitArea(SplitArea splitArea) {
		this.splitArea = splitArea;
	}
	public List<AxisData> getData() {
		return data;
	}
	public void setData(List<AxisData> data) {
		this.data = data;
	}
	public AxisPointer getAxisPointer() {
		return axisPointer;
	}
	public void setAxisPointer(AxisPointer axisPointer) {
		this.axisPointer = axisPointer;
	}
}
