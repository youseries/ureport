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
package com.bstek.ureport.chart.option.impl;

import com.bstek.ureport.chart.FontStyle;
import com.bstek.ureport.chart.option.Option;
import com.bstek.ureport.chart.option.Position;

/**
 * @author Jacky.gao
 * @since 2017年6月8日
 */
public class TitleOption implements Option {
	private boolean display;
	private Position position=Position.top;
	private int fontSize=14;
	private String fontColor="#666";
	private FontStyle fontStyle=FontStyle.bold;
	private int padding=10;
	private String text;
	@Override
	public String buildOptionJson() {
		StringBuilder sb=new StringBuilder();
		sb.append("\"title\":{");
		sb.append("\"display\":"+display+",");
		sb.append("\"text\":\""+text+"\",");
		sb.append("\"position\":\""+position+"\",");
		sb.append("\"fontSize\":"+fontSize+",");
		sb.append("\"fontColor\":\""+fontColor+"\",");
		sb.append("\"fontStyle\":\""+fontStyle+"\",");
		sb.append("\"padding\":\""+padding+"\"");
		sb.append("}");
		return sb.toString();
	}
	@Override
	public String getType() {
		return "title";
	}
	public boolean isDisplay() {
		return display;
	}
	public void setDisplay(boolean display) {
		this.display = display;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public int getFontSize() {
		return fontSize;
	}
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	public String getFontColor() {
		return fontColor;
	}
	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}
	public FontStyle getFontStyle() {
		return fontStyle;
	}
	public void setFontStyle(FontStyle fontStyle) {
		this.fontStyle = fontStyle;
	}
	public int getPadding() {
		return padding;
	}
	public void setPadding(int padding) {
		this.padding = padding;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
