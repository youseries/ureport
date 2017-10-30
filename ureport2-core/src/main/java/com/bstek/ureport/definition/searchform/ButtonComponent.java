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

/**
 * @author Jacky.gao
 * @since 2017年10月24日
 */
public abstract class ButtonComponent implements Component{
	private String label;
	private String style;
	private String type;
	@Override
	public String toHtml(RenderContext context) {
		return "<button type=\"button\" id=\""+context.buildComponentId(this)+"\" class=\""+style+"\">"+label+"</button>";
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	@Override
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
