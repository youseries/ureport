package com.bstek.ureport.echarts.options;

import java.util.List;

import com.bstek.ureport.echarts.options.prop.Align;
import com.bstek.ureport.echarts.options.prop.LegendType;
import com.bstek.ureport.echarts.options.prop.Orient;
import com.bstek.ureport.echarts.options.prop.TextStyle;

/**
 * @author Jacky.gao
 * @since 2017年12月25日
 */
public class LegendOption {
	private boolean show;
	private LegendType type;
	private String left;
	private String right;
	private String top;
	private String bottom;
	private Orient orient;
	private Align align;
	private String padding;
	private int itemGap;
	private String formatter;
	private String selectedMode;
	private String inactiveColor;
	private TextStyle textStyle;
	private List<String> data;
	private String backgroundColor;
	private String borderColor;
	private int borderWidth;
	private int borderRadius;
	private TextStyle pageTextStyle;
	public boolean isShow() {
		return show;
	}
	public void setShow(boolean show) {
		this.show = show;
	}
	public LegendType getType() {
		return type;
	}
	public void setType(LegendType type) {
		this.type = type;
	}
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
	public Orient getOrient() {
		return orient;
	}
	public void setOrient(Orient orient) {
		this.orient = orient;
	}
	public Align getAlign() {
		return align;
	}
	public void setAlign(Align align) {
		this.align = align;
	}
	public String getPadding() {
		return padding;
	}
	public void setPadding(String padding) {
		this.padding = padding;
	}
	public int getItemGap() {
		return itemGap;
	}
	public void setItemGap(int itemGap) {
		this.itemGap = itemGap;
	}
	public String getFormatter() {
		return formatter;
	}
	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}
	public String getSelectedMode() {
		return selectedMode;
	}
	public void setSelectedMode(String selectedMode) {
		this.selectedMode = selectedMode;
	}
	public String getInactiveColor() {
		return inactiveColor;
	}
	public void setInactiveColor(String inactiveColor) {
		this.inactiveColor = inactiveColor;
	}
	public TextStyle getTextStyle() {
		return textStyle;
	}
	public void setTextStyle(TextStyle textStyle) {
		this.textStyle = textStyle;
	}
	public List<String> getData() {
		return data;
	}
	public void setData(List<String> data) {
		this.data = data;
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
	public TextStyle getPageTextStyle() {
		return pageTextStyle;
	}
	public void setPageTextStyle(TextStyle pageTextStyle) {
		this.pageTextStyle = pageTextStyle;
	}
}
