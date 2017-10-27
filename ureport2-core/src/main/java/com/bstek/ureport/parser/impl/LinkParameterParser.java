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

import org.dom4j.Element;

import com.bstek.ureport.definition.LinkParameter;
import com.bstek.ureport.expression.ExpressionUtils;
import com.bstek.ureport.expression.model.Expression;
import com.bstek.ureport.parser.Parser;

/**
 * @author Jacky.gao
 * @since 2017年3月31日
 */
public class LinkParameterParser implements Parser<LinkParameter> {
	@Override
	public LinkParameter parse(Element element) {
		LinkParameter param=new LinkParameter();
		param.setName(element.attributeValue("name"));
		for(Object obj:element.elements()){
			if(obj==null || !(obj instanceof Element)){
				continue;
			}
			Element ele=(Element)obj;
			if(ele.getName().equals("value")){
				param.setValue(ele.getText());
				Expression expr=ExpressionUtils.parseExpression(ele.getText());
				param.setValueExpression(expr);;
				break;
			}
		}
		return param;
	}
}
