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

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

import com.bstek.ureport.definition.HeaderFooterDefinition;
import com.bstek.ureport.expression.ExpressionUtils;
import com.bstek.ureport.expression.model.Expression;
import com.bstek.ureport.parser.Parser;

/**
 * @author Jacky.gao
 * @since 2017年4月14日
 */
public class HeaderFooterParser implements Parser<HeaderFooterDefinition> {
	@Override
	public HeaderFooterDefinition parse(Element element) {
		HeaderFooterDefinition hf=new HeaderFooterDefinition();
		String margin=element.attributeValue("margin");
		if(StringUtils.isNotBlank(margin)){
			hf.setMargin(Integer.valueOf(margin));
		}
		String bold=element.attributeValue("bold");
		if(StringUtils.isNotBlank(bold)){
			hf.setBold(Boolean.valueOf(bold));
		}
		String italic=element.attributeValue("italic");
		if(StringUtils.isNotBlank(italic)){
			hf.setItalic(Boolean.valueOf(italic));
		}
		String underline=element.attributeValue("underline");
		if(StringUtils.isNotBlank(underline)){
			hf.setUnderline(Boolean.valueOf(underline));
		}
		String fontFamily=element.attributeValue("font-family");
		if(StringUtils.isNotBlank(fontFamily)){
			hf.setFontFamily(fontFamily);			
		}
		String forecolor=element.attributeValue("forecolor");
		if(StringUtils.isNotBlank(forecolor)){
			hf.setForecolor(forecolor);			
		}
		String fontSize=element.attributeValue("font-size");
		if(StringUtils.isNotBlank(fontSize)){
			hf.setFontSize(Integer.valueOf(fontSize));			
		}
		for(Object obj:element.elements()){
			if(obj==null || !(obj instanceof Element)){
				continue;
			}
			Element ele=(Element)obj;
			String name=ele.getName();
			if(name.equals("left")){
				hf.setLeft(ele.getText());
				if(StringUtils.isNotBlank(hf.getLeft())){
					Expression expr=ExpressionUtils.parseExpression(hf.getLeft());
					hf.setLeftExpression(expr);
				}
			}else if(name.equals("center")){
				hf.setCenter(ele.getText());
				if(StringUtils.isNotBlank(hf.getCenter())){
					Expression expr=ExpressionUtils.parseExpression(hf.getCenter());
					hf.setCenterExpression(expr);
				}
			}else if(name.equals("right")){
				hf.setRight(ele.getText());
				if(StringUtils.isNotBlank(hf.getRight())){
					Expression expr=ExpressionUtils.parseExpression(hf.getRight());
					hf.setRightExpression(expr);
				}
			}
		}
		return hf;
	}
}
