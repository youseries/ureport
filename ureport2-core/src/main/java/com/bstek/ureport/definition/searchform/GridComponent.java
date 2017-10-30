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
		sb.append("<div class='row'>");
		for(ColComponent c:cols){
			sb.append(c.toHtml(context));
		}
		sb.append("</div>");
		return sb.toString();
	}
	@Override
	public String initJs(RenderContext context) {
		return "";
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
