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
