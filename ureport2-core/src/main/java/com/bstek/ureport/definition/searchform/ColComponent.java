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
public class ColComponent extends ContainerComponent{
	private int size;
	@Override
	public String toHtml(RenderContext context) {
		StringBuffer sb=new StringBuffer();
		sb.append("<div class='col-md-"+size+"'");
		Object gridComponent=context.getMetadata(GridComponent.KEY);
		if(gridComponent!=null){
			GridComponent gc=(GridComponent)gridComponent;
			if(gc.isShowBorder()){
				String border="border:solid "+gc.getBorderWidth()+"px "+gc.getBorderColor()+"";
				sb.append(" style='"+border+";padding:10px'");
			}
		}
		sb.append(">");
		for(Component c:children){
			sb.append(c.toHtml(context));
		}
		sb.append("</div>");
		return sb.toString();
	}
	@Override
	public String initJs(RenderContext context) {
		return "";
	}
	public void setSize(int size) {
		this.size = size;
	}
	@Override
	public String getType() {
		return null;
	}
}
