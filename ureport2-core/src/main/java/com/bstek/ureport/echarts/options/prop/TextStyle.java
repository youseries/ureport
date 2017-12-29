package com.bstek.ureport.echarts.options.prop;


/**
 * @author Jacky.gao
 * @since 2017年12月25日
 */
public class TextStyle {
	private String color="#333";
	private String fontStyle="normal";
	private String fontWeight="normal";
	private String fontFamily="sans-serif";
	private int fontSize=18;
	private Align align;
	private VerticalAlign verticalAlign;
	private int lineHeight;
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getFontStyle() {
		return fontStyle;
	}
	public void setFontStyle(String fontStyle) {
		this.fontStyle = fontStyle;
	}
	public String getFontWeight() {
		return fontWeight;
	}
	public void setFontWeight(String fontWeight) {
		this.fontWeight = fontWeight;
	}
	public String getFontFamily() {
		return fontFamily;
	}
	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}
	public int getFontSize() {
		return fontSize;
	}
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	public Align getAlign() {
		return align;
	}
	public void setAlign(Align align) {
		this.align = align;
	}
	public VerticalAlign getVerticalAlign() {
		return verticalAlign;
	}
	public void setVerticalAlign(VerticalAlign verticalAlign) {
		this.verticalAlign = verticalAlign;
	}
	public int getLineHeight() {
		return lineHeight;
	}
	public void setLineHeight(int lineHeight) {
		this.lineHeight = lineHeight;
	}
}
