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

import java.util.Arrays;
import java.util.List;

/**
 * @author Jacky.gao
 * @since 2017年10月23日
 */
public class CheckboxInputComponent extends InputComponent {
	private boolean optionsInline;
	private List<Option> options;
	@Override
	String inputHtml(RenderContext context) {
		StringBuilder sb=new StringBuilder();
		String name=getBindParameter();
		Object pvalue=context.getParameter(name)==null ? "" : context.getParameter(name);
		String[] data=pvalue.toString().split(",");
		List<String> list=Arrays.asList(data);
		for(Option option:options){
			String value=option.getValue();
			String label=option.getLabel();
			String checked=list.contains(value) ? "checked" : "";
			if(this.optionsInline){
				sb.append("<span class='checkbox-inline' style='padding-top:0px'><input value='"+value+"' type='checkbox' "+checked+" name='"+name+"'>"+label+"</span>");
			}else{
				sb.append("<span class='checkbox'><input type='checkbox' value='"+value+"' name='"+name+"' "+checked+" style='margin-left: auto'><span style=\"margin-left:15px\">"+label+"</span></span>");
			}				
		}
		return sb.toString();
	}
	@Override
	public String initJs(RenderContext context) {
		String name=getBindParameter();
		StringBuilder sb=new StringBuilder();
		sb.append("formElements.push(");
		sb.append("function(){");
		sb.append("if(''==='"+name+"'){");
		sb.append("alert('复选框未绑定查询参数名，不能进行查询操作!');");
		sb.append("throw '复选框未绑定查询参数名，不能进行查询操作!'");
		sb.append("}");
		sb.append("var names='';");
		sb.append("$(\"input[name='"+getBindParameter()+"']:checked\").each(function(index,item){");
		sb.append("if(names===''){names+=$(item).val();}else{names+=','+$(item).val();}");
		sb.append("});");
		sb.append("return {");
		sb.append("\""+name+"\":names");
		sb.append("}");
		sb.append("}");
		sb.append(");");
		return sb.toString();
	}
	public void setOptionsInline(boolean optionsInline) {
		this.optionsInline = optionsInline;
	}
	public boolean isOptionsInline() {
		return optionsInline;
	}
	public void setOptions(List<Option> options) {
		this.options = options;
	}
	public List<Option> getOptions() {
		return options;
	}
}
