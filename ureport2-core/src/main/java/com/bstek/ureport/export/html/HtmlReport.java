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
package com.bstek.ureport.export.html;

import java.util.Collection;

import com.bstek.ureport.chart.ChartData;

/**
 * @author Jacky.gao
 * @since 2017年2月16日
 */
public class HtmlReport {
	private String content;
	private String style;
	private int totalPage;
	private int pageIndex;
	private String reportAlign;
	private Collection<ChartData> chartDatas;
	private int htmlIntervalRefreshValue;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public String getReportAlign() {
		return reportAlign;
	}
	public void setReportAlign(String reportAlign) {
		this.reportAlign = reportAlign;
	}
	public Collection<ChartData> getChartDatas() {
		return chartDatas;
	}
	public void setChartDatas(Collection<ChartData> chartDatas) {
		this.chartDatas = chartDatas;
	}
	public int getHtmlIntervalRefreshValue() {
		return htmlIntervalRefreshValue;
	}
	public void setHtmlIntervalRefreshValue(int htmlIntervalRefreshValue) {
		this.htmlIntervalRefreshValue = htmlIntervalRefreshValue;
	}
}
