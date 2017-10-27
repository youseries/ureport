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
import java.util.Collection;
import java.util.List;

import org.dom4j.Element;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.bstek.ureport.definition.searchform.Component;

/**
 * @author Jacky.gao
 * @since 2017年10月24日
 */
public class FormParserUtils implements ApplicationContextAware{
	@SuppressWarnings("rawtypes")
	private static Collection<FormParser> parsers=null;
	public static List<Component> parse(Element element){
		List<Component> list=new ArrayList<Component>();
		for(Object obj:element.elements()){
			if(obj==null || !(obj instanceof Element)){
				continue;
			}
			Element ele=(Element)obj;
			String name=ele.getName();
			FormParser<?> targetParser=null;
			for(FormParser<?> parser:parsers){
				if(parser.support(name)){
					targetParser=parser;
					break;
				}
			}
			if(targetParser==null){
				continue;				
			}
			list.add((Component)targetParser.parse(ele));
		}
		return list;
	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		FormParserUtils.parsers=applicationContext.getBeansOfType(FormParser.class).values();
	}
}
