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
 * @since 2017年10月23日
 */
public class ColComponent extends ContainerComponent{
	private int size;
	@Override
	public String toHtml(RenderContext context) {
		StringBuffer sb=new StringBuffer();
		sb.append("<div class='col-md-"+size+"' style='padding-left:2px;padding-right:2px'");
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
		StringBuilder sb=new StringBuilder();
		for(Component c:children){
			sb.append(c.initJs(context));
		}
		return sb.toString();
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getSize() {
		return size;
	}
	@Override
	public String getType() {
		return null;
	}
}
