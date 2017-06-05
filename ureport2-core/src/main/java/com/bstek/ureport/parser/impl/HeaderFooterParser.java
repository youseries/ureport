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
