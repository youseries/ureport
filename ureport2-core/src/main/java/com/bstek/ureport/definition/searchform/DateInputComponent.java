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


/**
 * @author Jacky.gao
 * @since 2016年1月11日
 */
public class DateInputComponent extends InputComponent {
	private String format;
	public void setFormat(String format) {
		this.format = format;
	}
	public String getFormat() {
		return format;
	}
	@Override
	public String initJs(RenderContext context) {
		StringBuffer sb=new StringBuffer();
		sb.append("$('#"+context.buildComponentId(this)+"').datetimepicker({");
		sb.append("format:'"+this.format+"',");
		sb.append("autoclose:1");
		if(this.format.equals("yyyy-mm-dd")){		
			sb.append(",startView:2,");
			sb.append("minView:2");
		}
		sb.append("});");
		
		String name=getBindParameter();
		sb.append("formElements.push(");
		sb.append("function(){");
		sb.append("if(''==='"+name+"'){");
		sb.append("alert('日期输入框未绑定查询参数名，不能进行查询操作!');");
		sb.append("throw '日期输入框未绑定查询参数名，不能进行查询操作!'");
		sb.append("}");
		sb.append("return {");
		sb.append("\""+name+"\":");		
		sb.append("$(\"input[name='"+name+"']\").val()");
		sb.append("}");
		sb.append("}");
		sb.append(");");
		return sb.toString();
	}
	
	@Override
	public String inputHtml(RenderContext context) {
		String name=getBindParameter();
		Object value=context.getParameter(name)==null ? "" : context.getParameter(name);
		StringBuffer sb=new StringBuffer();
		sb.append("<div id='"+context.buildComponentId(this)+"' class='input-group date'>");
		sb.append("<input type='text' style=\"padding:3px;height:28px\" name='"+name+"' value=\""+value+"\" class='form-control'>");			
		sb.append("<span class='input-group-addon' style=\"font-size:12px\"><span class='glyphicon glyphicon-calendar'></span></span>");
		sb.append("</div>");
		return sb.toString();
	}
}
