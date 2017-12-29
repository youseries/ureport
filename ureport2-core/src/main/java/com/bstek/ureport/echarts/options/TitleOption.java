package com.bstek.ureport.echarts.options;

import com.bstek.ureport.echarts.options.prop.TextStyle;

/**
 * @author Jacky.gao
 * @since 2017年12月25日
 */
public class TitleOption {
	private boolean show=true;
	private String text;
	private String link;
	private String target="blank";
	private TextStyle textStyle;
	private String subtext;
	private String sublink;
	private String subtarget="blank";
	private TextStyle subtextStyle;
	private String padding;
	private int itemGap;
	private String backgroundColor;
	private String borderColor;
	private int borderWidth;
	private int borderRadius;
	public boolean isShow() {
		return show;
	}
	public void setShow(boolean show) {
		this.show = show;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public TextStyle getTextStyle() {
		return textStyle;
	}
	public void setTextStyle(TextStyle textStyle) {
		this.textStyle = textStyle;
	}
	public String getSubtext() {
		return subtext;
	}
	public void setSubtext(String subtext) {
		this.subtext = subtext;
	}
	public String getSublink() {
		return sublink;
	}
	public void setSublink(String sublink) {
		this.sublink = sublink;
	}
	public String getSubtarget() {
		return subtarget;
	}
	public void setSubtarget(String subtarget) {
		this.subtarget = subtarget;
	}
	public TextStyle getSubtextStyle() {
		return subtextStyle;
	}
	public void setSubtextStyle(TextStyle subtextStyle) {
		this.subtextStyle = subtextStyle;
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
}
