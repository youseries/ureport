package com.bstek.ureport.parser.impl.searchform;

import org.dom4j.Element;

import com.bstek.ureport.definition.searchform.SubmitButtonComponent;

/**
 * @author Jacky.gao
 * @since 2017年10月24日
 */
public class SubmitButtonParser implements FormParser<SubmitButtonComponent> {
	@Override
	public SubmitButtonComponent parse(Element element) {
		SubmitButtonComponent btn=new SubmitButtonComponent();
		btn.setLabel(element.attributeValue("label"));
		btn.setStyle(element.attributeValue("style"));
		return btn;
	}
	@Override
	public boolean support(String name) {
		return name.equals("button-submit");
	}
}
