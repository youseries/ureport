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
 * @since 2016年1月11日
 */
public class DateInputComponent extends InputComponent {
	private String format;
	public void setFormat(String format) {
		this.format = format;
	}
	
	@Override
	public String initJs(RenderContext context) {
		StringBuffer sb=new StringBuffer();
		sb.append("$('#"+context.buildComponentId(this)+"').datetimepicker({");
		sb.append("format:'"+this.format+"'");
		sb.append("");
		sb.append("});");
		return sb.toString();
	}
	
	@Override
	public String inputHtml(RenderContext context) {
		StringBuffer sb=new StringBuffer();
		sb.append("<div id='"+context.buildComponentId(this)+"' class='input-group date'>");
		sb.append("<input type='text' name='"+getBindParameter()+"' class='form-control'>");			
		sb.append("<span class='input-group-addon'><span class='glyphicon glyphicon-calendar'></span></span>");
		sb.append("</div>");
		return sb.toString();
	}
}
