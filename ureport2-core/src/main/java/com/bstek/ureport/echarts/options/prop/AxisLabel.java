package com.bstek.ureport.echarts.options.prop;


/**
 * @author Jacky.gao
 * @since 2017年12月25日
 */
public class AxisLabel {
	private boolean show=true;
	private Integer interval;
	private Boolean inside;
	private Integer rotate;
	private Integer margin;
	private String formatter;
	private Boolean showMinLabel;
	private Boolean showMaxLabel;
	private String color;
	private FontStyle fontStyle;
	private FontWeight fontWeight;
	private String fontFamily;
	private Integer fontSize;
	private Align align;
	private VerticalAlign verticalAlign;
	private String backgroundColor;
	private String borderColor;
	private int borderWidth;
	private int borderRadius;
	private String padding;
	public boolean isShow() {
		return show;
	}
	public void setShow(boolean show) {
		this.show = show;
	}
	public Integer getInterval() {
		return interval;
	}
	public void setInterval(Integer interval) {
		this.interval = interval;
	}
	public boolean isInside() {
		return inside;
	}
	public void setInside(boolean inside) {
		this.inside = inside;
	}
	public Integer getRotate() {
		return rotate;
	}
	public void setRotate(Integer rotate) {
		this.rotate = rotate;
	}
	public Integer getMargin() {
		return margin;
	}
	public void setMargin(Integer margin) {
		this.margin = margin;
	}
	public String getFormatter() {
		return formatter;
	}
	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}
	public Boolean getShowMinLabel() {
		return showMinLabel;
	}
	public void setShowMinLabel(Boolean showMinLabel) {
		this.showMinLabel = showMinLabel;
	}
	public Boolean getShowMaxLabel() {
		return showMaxLabel;
	}
	public void setShowMaxLabel(Boolean showMaxLabel) {
		this.showMaxLabel = showMaxLabel;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public FontStyle getFontStyle() {
		return fontStyle;
	}
	public void setFontStyle(FontStyle fontStyle) {
		this.fontStyle = fontStyle;
	}
	public FontWeight getFontWeight() {
		return fontWeight;
	}
	public void setFontWeight(FontWeight fontWeight) {
		this.fontWeight = fontWeight;
	}
	public String getFontFamily() {
		return fontFamily;
	}
	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}
	public Integer getFontSize() {
		return fontSize;
	}
	public void setFontSize(Integer fontSize) {
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
	public int getBorderRadius() {
		return borderRadius;
	}
	public void setBorderRadius(int borderRadius) {
		this.borderRadius = borderRadius;
	}
	public String getPadding() {
		return padding;
	}
	public void setPadding(String padding) {
		this.padding = padding;
	}
}
