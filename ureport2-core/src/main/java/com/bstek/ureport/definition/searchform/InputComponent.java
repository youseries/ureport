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
public abstract class InputComponent implements Component{
	private String label;
	private String bindParameter;
	private String type;
	protected LabelPosition labelPosition=LabelPosition.top;
	
	abstract String inputHtml(RenderContext context);
	@Override
	public String toHtml(RenderContext context) {
		StringBuffer sb=new StringBuffer();
		if(this.labelPosition.equals(LabelPosition.top)){
			sb.append("<div>");			
		}else{
			sb.append("<div class='form-horizontal'>");			
		}
		sb.append("<div class='form-group' style='margin:0px 0px 10px 0px'>");
		if(this.labelPosition.equals(LabelPosition.top)){		
			sb.append("<label>"+this.label+"</label>");			
			sb.append(inputHtml(context));
		}else{					
			sb.append("<label class='col-md-3 control-label' style='text-align:right;padding-right:1px'>"+this.label+"</label>");			
			sb.append("<div class='col-md-9' style='padding-left:1px;'>");
			sb.append(inputHtml(context));
			sb.append("</div>");
		}
		sb.append("</div>");
		sb.append("</div>");
		return sb.toString();
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getLabel() {
		return label;
	}
	public void setLabelPosition(LabelPosition labelPosition) {
		this.labelPosition = labelPosition;
	}
	public String getBindParameter() {
		return bindParameter;
	}
	public void setBindParameter(String bindParameter) {
		this.bindParameter = bindParameter;
	}
	@Override
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
