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
