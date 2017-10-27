/*******************************************************************************
 * Copyright (C) 2017 Bstek.com
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.bstek.ureport.chart.option;

import com.bstek.ureport.chart.FontStyle;

/**
 * @author Jacky.gao
 * @since 2017年6月8日
 */
public class Labels {
	private int boxWidth=40;
	private int fontSize=12;
	private FontStyle fontStyle=FontStyle.normal;
	private String fontColor="#666";
	private int padding=10;
	public String toJson(){
		StringBuilder sb=new StringBuilder();
		sb.append("{");
		sb.append("boxWidth:"+boxWidth+",");
		sb.append("fontSize:"+fontSize+",");
		sb.append("fontStyle:\""+fontStyle+"\",");
		sb.append("fontColor:\""+fontColor+"\"");
		sb.append("}");
		return sb.toString();
	}
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
	public int getPadding() {
		return padding;
	}
	public void setPadding(int padding) {
		this.padding = padding;
	}
}
