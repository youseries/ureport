/*******************************************************************************
 * Copyright 2017 Bstek
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
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
