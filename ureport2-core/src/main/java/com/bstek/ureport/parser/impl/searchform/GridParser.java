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
package com.bstek.ureport.parser.impl.searchform;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import com.bstek.ureport.definition.searchform.ColComponent;
import com.bstek.ureport.definition.searchform.Component;
import com.bstek.ureport.definition.searchform.GridComponent;

/**
 * @author Jacky.gao
 * @since 2017年10月24日
 */
public class GridParser implements FormParser<GridComponent> {
	@Override
	public GridComponent parse(Element element) {
		GridComponent grid=new GridComponent();
		grid.setShowBorder(Boolean.valueOf(element.attributeValue("show-border")));
		if(grid.isShowBorder()){
			grid.setBorderColor(element.attributeValue("border-color"));
			grid.setBorderWidth(Integer.valueOf(element.attributeValue("border-width")));			
		}
		List<ColComponent> cols=new ArrayList<ColComponent>();
		for(Object obj:element.elements()){
			if(obj==null || !(obj instanceof Element)){
				continue;
			}
			Element ele=(Element)obj;
			if(!ele.getName().equals("col")){
				continue;
			}
			ColComponent col=new ColComponent();
			cols.add(col);
			col.setSize(Integer.valueOf(ele.attributeValue("size")));
			List<Component> components=FormParserUtils.parse(ele);
			col.setChildren(components);
		}
		grid.setCols(cols);
		return grid;
	}
	@Override
	public boolean support(String name) {
		return name.equals("grid");
	}
}
