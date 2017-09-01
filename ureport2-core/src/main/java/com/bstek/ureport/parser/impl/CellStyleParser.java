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

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;

import com.bstek.ureport.Utils;
import com.bstek.ureport.definition.Alignment;
import com.bstek.ureport.definition.Border;
import com.bstek.ureport.definition.BorderStyle;
import com.bstek.ureport.definition.CellStyle;
import com.bstek.ureport.definition.ConditionCellStyle;
import com.bstek.ureport.definition.Scope;
import com.bstek.ureport.parser.Parser;

/**
 * @author Jacky.gao
 * @since 2017年1月19日
 */
public class CellStyleParser implements Parser<CellStyle> {
	@Override
	public CellStyle parse(Element element) {
		boolean forCondition=false;
		String forConditionText=element.attributeValue("for-condition");
		if(StringUtils.isNotBlank(forConditionText)){
			forCondition=Boolean.valueOf(forConditionText);
		}
		CellStyle style=null;
		if(forCondition){
			ConditionCellStyle s=new ConditionCellStyle();
			String bgcolorScope=element.attributeValue("bgcolor-scope");
			if(StringUtils.isNotBlank(bgcolorScope)){
				s.setBgcolorScope(Scope.valueOf(bgcolorScope));				
			}
			String forecolorScope=element.attributeValue("forecolor-scope");
			if(StringUtils.isNotBlank(forecolorScope)){
				s.setForecolorScope(Scope.valueOf(forecolorScope));
			}
			String fontSizeScope=element.attributeValue("font-size-scope");
			if(StringUtils.isNotBlank(fontSizeScope)){
				s.setFontSizeScope(Scope.valueOf(fontSizeScope));
			}
			String fontFamilyScope=element.attributeValue("font-family-scope");
			if(StringUtils.isNotBlank(fontFamilyScope)){
				s.setFontFamilyScope(Scope.valueOf(fontFamilyScope));
			}
			String boldScope=element.attributeValue("bold-scope");
			if(StringUtils.isNotBlank(boldScope)){
				s.setBoldScope(Scope.valueOf(boldScope));
			}
			String italicScope=element.attributeValue("italic-scope");
			if(StringUtils.isNotBlank(italicScope)){
				s.setItalicScope(Scope.valueOf(italicScope));
			}
			String underlineScope=element.attributeValue("underline-scope");
			if(StringUtils.isNotBlank(underlineScope)){
				s.setUnderlineScope(Scope.valueOf(underlineScope));
			}
			String alignScope=element.attributeValue("align-scope");
			if(StringUtils.isNotBlank(alignScope)){
				s.setAlignScope(Scope.valueOf(alignScope));
			}
			String valignScope=element.attributeValue("valign-scope");
			if(StringUtils.isNotBlank(valignScope)){
				s.setValignScope(Scope.valueOf(valignScope));
			}
			style=s;
		}else{
			style=new CellStyle();
		}
		style.setBgcolor(element.attributeValue("bgcolor"));
		String forecolor=element.attributeValue("forecolor");
		if(StringUtils.isNotBlank(forecolor)){
			style.setForecolor(forecolor);			
		}
		String fontFamily=element.attributeValue("font-family");
		if(StringUtils.isNotBlank(fontFamily)){			
			style.setFontFamily(fontFamily);			
		}
		String bold=element.attributeValue("bold");
		if(StringUtils.isNotBlank(bold)){
			style.setBold(Boolean.valueOf(bold));
		}
		String fontSize=element.attributeValue("font-size");
		if(StringUtils.isNotBlank(fontSize)){
			style.setFontSize(Integer.valueOf(fontSize));
		}
		style.setFormat(element.attributeValue("format"));
		String italic=element.attributeValue("italic");
		if(StringUtils.isNotBlank(italic)){
			style.setItalic(Boolean.valueOf(italic));
		}
		String underline=element.attributeValue("underline");
		if(StringUtils.isNotBlank(underline)){
			style.setUnderline(Boolean.valueOf(underline));
		}
		String valign=element.attributeValue("valign");
		if(StringUtils.isNotBlank(valign)){
			style.setValign(Alignment.valueOf(valign));
		}
		String align=element.attributeValue("align");
		if(StringUtils.isNotBlank(align)){
			style.setAlign(Alignment.valueOf(align));
		}
		String wrapCompute=element.attributeValue("wrap-compute");
		if(StringUtils.isNotBlank(wrapCompute)){
			style.setWrapCompute(Boolean.valueOf(wrapCompute));
		}
		String lineHeight=element.attributeValue("line-height");
		if(StringUtils.isNotBlank(lineHeight)){
			style.setLineHeight(Utils.toBigDecimal(lineHeight).floatValue());
		}
		for(Object obj:element.elements()){
			if(obj==null || !(obj instanceof Element)){
				continue;
			}
			Element ele=(Element)obj;
			String name=ele.getName();
			if(name.equals("left-border")){
				style.setLeftBorder(parseBorder(ele));
			}else if(name.equals("right-border")){
				style.setRightBorder(parseBorder(ele));
			}else if(name.equals("top-border")){
				style.setTopBorder(parseBorder(ele));
			}else if(name.equals("bottom-border")){
				style.setBottomBorder(parseBorder(ele));
			}
		}
		return style;
	}
	
	private Border parseBorder(Element element){
		Border border=new Border();
		border.setWidth(Integer.valueOf(element.attributeValue("width")));
		border.setStyle(BorderStyle.valueOf(element.attributeValue("style")));
		border.setColor(element.attributeValue("color"));
		return border;
	}
}
