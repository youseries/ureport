package com.bstek.ureport.parser.impl.searchform;

import org.dom4j.Element;

import com.bstek.ureport.definition.searchform.ResetButtonComponent;

/**
 * @author Jacky.gao
 * @since 2017年10月24日
 */
public class ResetButtonParser implements FormParser<ResetButtonComponent>{
	@Override
	public ResetButtonComponent parse(Element element) {
		ResetButtonComponent btn=new ResetButtonComponent();
		btn.setLabel(element.attributeValue("label"));
		btn.setStyle(element.attributeValue("style"));
		return btn;
	}
	@Override
	public boolean support(String name) {
		return name.equals("button-reset");
	}
}
