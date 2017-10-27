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
package com.bstek.ureport.parser.impl;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;

import com.bstek.ureport.definition.HtmlReportAlign;
import com.bstek.ureport.definition.Orientation;
import com.bstek.ureport.definition.PagingMode;
import com.bstek.ureport.definition.Paper;
import com.bstek.ureport.definition.PaperSize;
import com.bstek.ureport.definition.PaperType;
import com.bstek.ureport.parser.Parser;

/**
 * @author Jacky.gao
 * @since 2017年1月19日
 */
public class PaperParser implements Parser<Paper> {
	@Override
	public Paper parse(Element element) {
		Paper paper=new Paper();
		String orientation=element.attributeValue("orientation");
		paper.setOrientation(Orientation.valueOf(orientation));
		paper.setPaperType(PaperType.valueOf(element.attributeValue("type")));
		if(paper.getPaperType().equals(PaperType.CUSTOM)){
			paper.setWidth(Integer.valueOf(element.attributeValue("width")));
			paper.setHeight(Integer.valueOf(element.attributeValue("height")));
		}else{
			PaperSize size=paper.getPaperType().getPaperSize();
			paper.setWidth(size.getWidth());
			paper.setHeight(size.getHeight());
		}
		String leftMargin=element.attributeValue("left-margin");
		if(StringUtils.isNotBlank(leftMargin)){
			paper.setLeftMargin(Integer.valueOf(leftMargin));			
		}
		String rightMargin=element.attributeValue("right-margin");
		if(StringUtils.isNotBlank(rightMargin)){
			paper.setRightMargin(Integer.valueOf(rightMargin));			
		}
		String topMargin=element.attributeValue("top-margin");
		if(StringUtils.isNotBlank(topMargin)){
			paper.setTopMargin(Integer.valueOf(topMargin));			
		}
		String bottomMargin=element.attributeValue("bottom-margin");
		if(StringUtils.isNotBlank(bottomMargin)){
			paper.setBottomMargin(Integer.valueOf(bottomMargin));			
		}
		paper.setPagingMode(PagingMode.valueOf(element.attributeValue("paging-mode")));
		if(paper.getPagingMode().equals(PagingMode.fixrows)){
			paper.setFixRows(Integer.valueOf(element.attributeValue("fixrows")));
		}
		String columnEnabled=element.attributeValue("column-enabled");
		if(StringUtils.isNotBlank(columnEnabled)){
			paper.setColumnEnabled(Boolean.valueOf(columnEnabled));
		}
		if(paper.isColumnEnabled()){
			paper.setColumnCount(Integer.valueOf(element.attributeValue("column-count")));
			paper.setColumnMargin(Integer.valueOf(element.attributeValue("column-margin")));
		}
		String htmlReportAlign=element.attributeValue("html-report-align");
		if(StringUtils.isNotBlank(htmlReportAlign)){
			paper.setHtmlReportAlign(HtmlReportAlign.valueOf(htmlReportAlign));
		}
		String htmlIntervalRefreshValue=element.attributeValue("html-interval-refresh-value");
		if(StringUtils.isNotBlank(htmlIntervalRefreshValue)){
			paper.setHtmlIntervalRefreshValue(Integer.valueOf(htmlIntervalRefreshValue));
		}
		paper.setBgImage(element.attributeValue("bg-image"));
		return paper;
	}
}
