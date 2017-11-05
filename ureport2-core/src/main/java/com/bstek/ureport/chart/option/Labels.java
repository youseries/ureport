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
