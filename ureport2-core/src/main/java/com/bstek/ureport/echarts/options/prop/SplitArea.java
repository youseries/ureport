package com.bstek.ureport.echarts.options.prop;

/**
 * @author Jacky.gao
 * @since 2017年12月27日
 */
public class SplitArea {
	private boolean show;
	private String interval;
	private String color;
	private int opacity=1;
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
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getOpacity() {
		return opacity;
	}
	public void setOpacity(int opacity) {
		this.opacity = opacity;
	}
}
