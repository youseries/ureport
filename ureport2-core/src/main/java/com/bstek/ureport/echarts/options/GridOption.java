package com.bstek.ureport.echarts.options;

/**
 * @author Jacky.gao
 * @since 2017年12月25日
 */
public class GridOption {
	private String left;
	private String right;
	private String top;
	private String bottom;
	private boolean containLabel=true;
	private String backgroundColor;
	private String borderColor;
	private int borderWidth;
	public String getLeft() {
		return left;
	}
	public void setLeft(String left) {
		this.left = left;
	}
	public String getRight() {
		return right;
	}
	public void setRight(String right) {
		this.right = right;
	}
	public String getTop() {
		return top;
	}
	public void setTop(String top) {
		this.top = top;
	}
	public String getBottom() {
		return bottom;
	}
	public void setBottom(String bottom) {
		this.bottom = bottom;
	}
	public boolean isContainLabel() {
		return containLabel;
	}
	public void setContainLabel(boolean containLabel) {
		this.containLabel = containLabel;
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
}
