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

import com.bstek.ureport.definition.searchform.LabelPosition;
import com.bstek.ureport.definition.searchform.Option;
import com.bstek.ureport.definition.searchform.RadioInputComponent;

/**
 * @author Jacky.gao
 * @since 2017年10月24日
 */
public class RadioInputParser implements FormParser<RadioInputComponent> {
	@Override
	public RadioInputComponent parse(Element element) {
		RadioInputComponent radio=new RadioInputComponent();
		radio.setBindParameter(element.attributeValue("bind-parameter"));
		radio.setOptionsInline(Boolean.valueOf(element.attributeValue("options-inline")));
		radio.setLabel(element.attributeValue("label"));
		radio.setLabelPosition(LabelPosition.valueOf(element.attributeValue("label-position")));
		List<Option> options=new ArrayList<Option>();
		for(Object obj:element.elements()){
			if(obj==null || !(obj instanceof Element)){
				continue;
			}
			Element ele=(Element)obj;
			if(!ele.getName().equals("option")){
				continue;
			}
			Option option=new Option();
			options.add(option);
			option.setLabel(ele.attributeValue("label"));
			option.setValue(ele.attributeValue("value"));
		}
		return radio;
	}
	@Override
	public boolean support(String name) {
		return name.equals("input-radio");
	}
}
