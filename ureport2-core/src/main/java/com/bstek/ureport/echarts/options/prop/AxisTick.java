package com.bstek.ureport.echarts.options.prop;


/**
 * @author Jacky.gao
 * @since 2017年12月25日
 */
public class AxisTick {
	private boolean show=true;
	private Boolean alignWithLabel;
	private Integer interval;
	private Boolean inside;
	private Integer length;
	private LineStyle lineStyle;
	public boolean isShow() {
		return show;
	}
	public void setShow(boolean show) {
		this.show = show;
	}
	public Boolean getAlignWithLabel() {
		return alignWithLabel;
	}
	public void setAlignWithLabel(Boolean alignWithLabel) {
		this.alignWithLabel = alignWithLabel;
	}
	public Integer getInterval() {
		return interval;
	}
	public void setInterval(Integer interval) {
		this.interval = interval;
	}
	public Boolean getInside() {
		return inside;
	}
	public void setInside(Boolean inside) {
		this.inside = inside;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public LineStyle getLineStyle() {
		return lineStyle;
	}
	public void setLineStyle(LineStyle lineStyle) {
		this.lineStyle = lineStyle;
	}
}
