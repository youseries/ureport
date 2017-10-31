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
public class SelectInputComponent extends InputComponent {
	private List<Option> options;
	@Override
	String inputHtml(RenderContext context) {
		StringBuilder sb=new StringBuilder();
		sb.append("<select style=\"padding:3px;height:28px\" id='"+context.buildComponentId(this)+"' name='"+getBindParameter()+"' class='form-control'>");			
		for(Option option:options){
			sb.append("<option value='"+option.getValue()+"'>"+option.getLabel()+"</option>");
		}
		sb.append("</select>");
		return sb.toString();
	}
	@Override
	public String initJs(RenderContext context) {
		return "";
	}
	public void setOptions(List<Option> options) {
		this.options = options;
	}
	public List<Option> getOptions() {
		return options;
	}
}
