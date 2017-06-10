package com.bstek.ureport.chart.option;

/**
 * @author Jacky.gao
 * @since 2017年6月8日
 */
public class Labels {
	private int boxWidth=40;
	private int fontSize=12;
	private FontStyle fontStyle=FontStyle.normal;
	private String fontColor="#666";
	private String fontFamily="'Helvetica Neue', 'Helvetica', 'Arial', sans-serif";
	private int padding=10;
	public int getBoxWidth() {
		return boxWidth;
	}
	public void setBoxWidth(int boxWidth) {
		this.boxWidth = boxWidth;
	}
	public int getFontSize() {
		return fontSize;
	}
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	public FontStyle getFontStyle() {
		return fontStyle;
	}
	public void setFontStyle(FontStyle fontStyle) {
		this.fontStyle = fontStyle;
	}
	public String getFontColor() {
		return fontColor;
	}
	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}
	public String getFontFamily() {
		return fontFamily;
	}
	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}
	public int getPadding() {
		return padding;
	}
	public void setPadding(int padding) {
		this.padding = padding;
	}
}
