package com.bstek.ureport.parser.impl.searchform;

import org.dom4j.Element;

import com.bstek.ureport.definition.searchform.LabelPosition;
import com.bstek.ureport.definition.searchform.TextInputComponent;

/**
 * @author Jacky.gao
 * @since 2017年10月24日
 */
public class TextInputParser implements FormParser<TextInputComponent> {
	@Override
	public TextInputComponent parse(Element element) {
		TextInputComponent component=new TextInputComponent();
		component.setBindParameter(element.attributeValue("bind-parameter"));
		component.setLabel(element.attributeValue("label"));
		component.setLabelPosition(LabelPosition.valueOf(element.attributeValue("label-position")));
		return component;
	}
	@Override
	public boolean support(String name) {
		return name.equals("input-text");
	}
}
