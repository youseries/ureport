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
package com.bstek.ureport.parser.impl;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;

import com.bstek.ureport.definition.Band;
import com.bstek.ureport.definition.RowDefinition;
import com.bstek.ureport.parser.Parser;

/**
 * @author Jacky.gao
 * @since 2016年12月5日
 */
public class RowParser implements Parser<RowDefinition> {
	@Override
	public RowDefinition parse(Element element) {
		RowDefinition row=new RowDefinition();
		row.setRowNumber(Integer.valueOf(element.attributeValue("row-number")));
		String height=element.attributeValue("height");
		if(StringUtils.isNotBlank(height)){
			row.setHeight(Integer.valueOf(height));
		}
		String band=element.attributeValue("band");
		if(StringUtils.isNotBlank(band)){
			row.setBand(Band.valueOf(band));
		}
		return row;
	}
}
