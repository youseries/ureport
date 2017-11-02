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
 * @since 2017年10月23日
 */
public class TextInputComponent extends InputComponent {
	@Override
	String inputHtml(RenderContext context) {
		return "<input type='text' style=\"padding:3px;height:28px\" id='"+context.buildComponentId(this)+"' name='"+getBindParameter()+"' class='form-control'>";	
	}
	@Override
	public String initJs(RenderContext context) {
		String name=getBindParameter();
		StringBuilder sb=new StringBuilder();
		sb.append("formElements.push(");
		sb.append("function(){");
		sb.append("if(''==='"+name+"'){");
		sb.append("alert('文本框未绑定查询参数名，不能进行查询操作!');");
		sb.append("throw '文本框未绑定查询参数名，不能进行查询操作!'");
		sb.append("}");
		sb.append("return {");
		sb.append("\""+name+"\":");		
		sb.append("$('#"+context.buildComponentId(this)+"').val()");
		sb.append("}");
		sb.append("}");
		sb.append(");");
		return sb.toString();
	}
}
