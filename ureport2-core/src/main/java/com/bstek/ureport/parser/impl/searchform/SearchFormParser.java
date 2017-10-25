package com.bstek.ureport.parser.impl.searchform;

import java.util.List;

import org.dom4j.Element;

import com.bstek.ureport.definition.searchform.Component;
import com.bstek.ureport.definition.searchform.FormPosition;
import com.bstek.ureport.definition.searchform.SearchForm;
import com.bstek.ureport.parser.Parser;

/**
 * @author Jacky.gao
 * @since 2017年10月24日
 */
public class SearchFormParser implements Parser<SearchForm> {
	@Override
	public SearchForm parse(Element element) {
		SearchForm form=new SearchForm();
		form.setFormPosition(FormPosition.valueOf(element.attributeValue("form-position")));
		List<Component> components=FormParserUtils.parse(element);
		form.setComponents(components);
		return form;
	}
}
