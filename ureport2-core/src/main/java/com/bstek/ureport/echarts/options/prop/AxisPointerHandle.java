package com.bstek.ureport.echarts.options.prop;

/**
 * @author Jacky.gao
 * @since 2017年12月27日
 */
public class AxisPointerHandle {
	private boolean show;
	private int size=45;
	private String color;
	public boolean isShow() {
		return show;
	}
	public void setShow(boolean show) {
		this.show = show;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
}
