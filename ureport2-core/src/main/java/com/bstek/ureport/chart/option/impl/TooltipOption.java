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

/**
 * @author Jacky.gao
 * @since 2017年6月8日
 */
public class TooltipOption implements Option {
	private boolean enabled=true;
	private int titleFontSize=12;
	private FontStyle titleFontStyle=FontStyle.bold;
	private String titleFontColor="#fff";
	private int bodyFontSize=12;
	private FontStyle bodyFontStyle=FontStyle.normal;
	private String bodyFontColor="#fff";
	
	@Override
	public String buildOptionJson() {
		StringBuilder sb=new StringBuilder();
		sb.append("\"tooltips\":{");
		sb.append("\"enabled\":"+enabled+",");
		sb.append("\"titleFontSize\":"+titleFontSize+",");
		sb.append("\"titleFontStyle\":\""+titleFontStyle+"\",");
		sb.append("\"titleFontColor\":\""+titleFontColor+"\",");
		sb.append("\"bodyFontSize\":"+bodyFontSize+",");
		sb.append("\"bodyFontStyle\":\""+bodyFontStyle+"\",");
		sb.append("\"bodyFontColor\":\""+bodyFontColor+"\",");
		sb.append("}");
		return sb.toString();
	}

	@Override
	public String getType() {
		return "tooltips";
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getTitleFontSize() {
		return titleFontSize;
	}

	public void setTitleFontSize(int titleFontSize) {
		this.titleFontSize = titleFontSize;
	}

	public FontStyle getTitleFontStyle() {
		return titleFontStyle;
	}

	public void setTitleFontStyle(FontStyle titleFontStyle) {
		this.titleFontStyle = titleFontStyle;
	}

	public String getTitleFontColor() {
		return titleFontColor;
	}

	public void setTitleFontColor(String titleFontColor) {
		this.titleFontColor = titleFontColor;
	}

	public int getBodyFontSize() {
		return bodyFontSize;
	}

	public void setBodyFontSize(int bodyFontSize) {
		this.bodyFontSize = bodyFontSize;
	}

	public FontStyle getBodyFontStyle() {
		return bodyFontStyle;
	}

	public void setBodyFontStyle(FontStyle bodyFontStyle) {
		this.bodyFontStyle = bodyFontStyle;
	}

	public String getBodyFontColor() {
		return bodyFontColor;
	}

	public void setBodyFontColor(String bodyFontColor) {
		this.bodyFontColor = bodyFontColor;
	}
	
}
