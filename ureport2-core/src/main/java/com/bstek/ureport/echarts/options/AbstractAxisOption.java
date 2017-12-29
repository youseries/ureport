package com.bstek.ureport.echarts.options;

import java.util.List;

import com.bstek.ureport.echarts.options.prop.AxisData;
import com.bstek.ureport.echarts.options.prop.AxisLabel;
import com.bstek.ureport.echarts.options.prop.AxisLine;
import com.bstek.ureport.echarts.options.prop.AxisPointer;
import com.bstek.ureport.echarts.options.prop.AxisTick;
import com.bstek.ureport.echarts.options.prop.NameLocation;
import com.bstek.ureport.echarts.options.prop.SplitArea;
import com.bstek.ureport.echarts.options.prop.SplitLine;
import com.bstek.ureport.echarts.options.prop.TextStyle;
import com.bstek.ureport.echarts.options.prop.XAxisType;

/**
 * @author Jacky.gao
 * @since 2017年12月25日
 */
public abstract class AbstractAxisOption {
	private boolean show=true;
	private int offset;
	private XAxisType type;
	private String name;
	private NameLocation nameLocation;
	private TextStyle nameTextStyle;
	private int nameGap=15;
	private Integer nameRotate;
	private String boundaryGap;
	private String min;
	private String max;
	private AxisLine axisLine;
	private AxisTick axisTick;
	private AxisLabel axisLabel;
	private SplitLine splitLine;
	private SplitArea splitArea;
	private List<AxisData> data;
	private AxisPointer axisPointer;
	public boolean isShow() {
		return show;
	}
	public void setShow(boolean show) {
		this.show = show;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public XAxisType getType() {
		return type;
	}
	public void setType(XAxisType type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public NameLocation getNameLocation() {
		return nameLocation;
	}
	public void setNameLocation(NameLocation nameLocation) {
		this.nameLocation = nameLocation;
	}
	public TextStyle getNameTextStyle() {
		return nameTextStyle;
	}
	public void setNameTextStyle(TextStyle nameTextStyle) {
		this.nameTextStyle = nameTextStyle;
	}
	public int getNameGap() {
		return nameGap;
	}
	public void setNameGap(int nameGap) {
		this.nameGap = nameGap;
	}
	public Integer getNameRotate() {
		return nameRotate;
	}
	public void setNameRotate(Integer nameRotate) {
		this.nameRotate = nameRotate;
	}
	public String getBoundaryGap() {
		return boundaryGap;
	}
	public void setBoundaryGap(String boundaryGap) {
		this.boundaryGap = boundaryGap;
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
