package com.bstek.ureport.echarts.options.prop;


/**
 * @author Jacky.gao
 * @since 2017年12月25日
 */
public class AxisLine {
	private boolean show=true;
	private String axisLine;
	private LineStyle lineStyle;
	public boolean isShow() {
		return show;
	}
	public void setShow(boolean show) {
		this.show = show;
	}
	public String getAxisLine() {
		return axisLine;
	}
	public void setAxisLine(String axisLine) {
		this.axisLine = axisLine;
	}
	public LineStyle getLineStyle() {
		return lineStyle;
	}
	public void setLineStyle(LineStyle lineStyle) {
		this.lineStyle = lineStyle;
	}
}
