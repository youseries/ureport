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
package com.bstek.ureport.parser.impl.value;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;

import com.bstek.ureport.definition.value.Source;
import com.bstek.ureport.definition.value.ImageValue;
import com.bstek.ureport.definition.value.Value;
import com.bstek.ureport.expression.ExpressionUtils;
import com.bstek.ureport.expression.model.Expression;

/**
 * @author Jacky.gao
 * @since 2017年3月6日
 */
public class ImageValueParser extends ValueParser {

	@Override
	public Value parse(Element element) {
		ImageValue value=new ImageValue();
		String width=element.attributeValue("width");
		if(StringUtils.isNotBlank(width)){
			value.setWidth(Integer.valueOf(width));
		}
		String height=element.attributeValue("height");
		if(StringUtils.isNotBlank(height)){
			value.setHeight(Integer.valueOf(height));
		}
		Source source=Source.valueOf(element.attributeValue("source"));
		value.setSource(source);
		for(Object obj:element.elements()){
			if(obj==null || !(obj instanceof Element)){
				continue;
			}
			Element ele=(Element)obj;
			if(ele.getName().equals("text")){
				if(source.equals(Source.text)){
					value.setPath(ele.getText());					
				}else{
					value.setExpr(ele.getText());					
				}
				break;
			}
		}
		if(source.equals(Source.expression)){
			String expr=value.getExpr();
			Expression expression=ExpressionUtils.parseExpression(expr);
			value.setExpression(expression);
		}
		return value;
	}
}
