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
package com.bstek.ureport.definition.searchform;

import java.util.List;

/**
 * @author Jacky.gao
 * @since 2017年10月23日
 */
public class GridComponent implements Component{
	private boolean showBorder;
	private int borderWidth;
	private String borderColor;
	private String type;
	private List<ColComponent> cols;
	public static final String KEY="grid_component";
	@Override
	public String toHtml(RenderContext context) {
		StringBuffer sb=new StringBuffer();
		sb.append("<div class='row' style='margin:0'>");
		context.putMetadata(KEY, this);
		for(ColComponent c:cols){
			sb.append(c.toHtml(context));
		}
		sb.append("</div>");
		return sb.toString();
	}
	@Override
	public String initJs(RenderContext context) {
		StringBuffer sb=new StringBuffer();
		for(ColComponent c:cols){
			sb.append(c.initJs(context));
		}
		return sb.toString();
	}
	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}
	public void setBorderWidth(int borderWidth) {
		this.borderWidth = borderWidth;
	}
	public void setShowBorder(boolean showBorder) {
		this.showBorder = showBorder;
	}
	public boolean isShowBorder() {
		return showBorder;
	}
	public int getBorderWidth() {
		return borderWidth;
	}
	public String getBorderColor() {
		return borderColor;
	}
	public List<ColComponent> getCols() {
		return cols;
	}
	public void setCols(List<ColComponent> cols) {
		this.cols = cols;
	}
	@Override
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
