package com.bstek.ureport.echarts.options.prop;

/**
 * @author Jacky.gao
 * @since 2017年12月25日
 */
public class LineStyle {
	private String color="#333";
	private int width=1;
	private LineType type;
	private int opacity=1;
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public LineType getType() {
		return type;
	}
	public void setType(LineType type) {
		this.type = type;
	}
	public int getOpacity() {
		return opacity;
	}
	public void setOpacity(int opacity) {
		this.opacity = opacity;
	}
}
