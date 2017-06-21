package com.bstek.ureport.parser.impl;

import org.dom4j.Element;

import com.bstek.ureport.definition.ConditionPaging;
import com.bstek.ureport.definition.PagingPosition;
import com.bstek.ureport.parser.Parser;

/**
 * @author Jacky.gao
 * @since 2017年6月21日
 */
public class ConditionPagingParser implements Parser<ConditionPaging> {
	@Override
	public ConditionPaging parse(Element element) {
		ConditionPaging paging=new ConditionPaging();
		String position=element.attributeValue("position");
		paging.setPosition(PagingPosition.valueOf(position));
		String line=element.attributeValue("line");
		paging.setLine(Integer.valueOf(line));
		return paging;
	}
}
