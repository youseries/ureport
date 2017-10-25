package com.bstek.ureport.parser.impl.searchform;

import org.dom4j.Element;

import com.bstek.ureport.definition.searchform.DateInputComponent;
import com.bstek.ureport.definition.searchform.LabelPosition;

/**
 * @author Jacky.gao
 * @since 2017年10月24日
 */
public class DatetimeInputParser implements FormParser<DateInputComponent> {
	@Override
	public DateInputComponent parse(Element element) {
		DateInputComponent component=new DateInputComponent();
		component.setBindParameter(element.attributeValue("bind-parameter"));
		component.setLabel(element.attributeValue("label"));
		component.setLabelPosition(LabelPosition.valueOf(element.attributeValue("label-position")));
		component.setFormat(element.attributeValue("format"));
		return component;
	}
	@Override
	public boolean support(String name) {
		return name.equals("input-text");
	}
}
