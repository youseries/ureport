package com.bstek.ureport.echarts.options.prop;

/**
 * @author Jacky.gao
 * @since 2017年12月25日
 */
public class SplitLine {
	private boolean show;
	private String interval;
	private LineStyle lineStyle;
	public boolean isShow() {
		return show;
	}
	public void setShow(boolean show) {
		this.show = show;
	}
	public String getInterval() {
		return interval;
	}
	public void setInterval(String interval) {
		this.interval = interval;
	}
	public LineStyle getLineStyle() {
		return lineStyle;
	}
	public void setLineStyle(LineStyle lineStyle) {
		this.lineStyle = lineStyle;
	}
}
