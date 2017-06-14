package com.bstek.ureport.chart.axes;

import com.bstek.ureport.chart.FontStyle;

/**
 * @author Jacky.gao
 * @since 2017年6月14日
 */
public class ScaleLabel {
	private boolean display=false;
	private String labelString="";
	private String fontColor="#666";
	private int fontSize=12;
	private FontStyle fontStyle=FontStyle.normal;
	
	public String toJson(){
		StringBuilder sb=new StringBuilder();
		sb.append("{");
		sb.append("display:"+display+",");
		sb.append("labelString:\""+labelString+"\",");
		sb.append("fontColor:\""+fontColor+"\",");
		sb.append("fontSize:"+fontSize+",");
		sb.append("fontStyle:\""+fontStyle+"\"");
		sb.append("}");
		return sb.toString();
	}
	
	public boolean isDisplay() {
		return display;
	}
	public void setDisplay(boolean display) {
		this.display = display;
	}
	public String getLabelString() {
		return labelString;
	}
	public void setLabelString(String labelString) {
		this.labelString = labelString;
	}
	public String getFontColor() {
		return fontColor;
	}
	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
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
}
