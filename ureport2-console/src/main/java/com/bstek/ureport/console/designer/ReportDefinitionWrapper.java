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
package com.bstek.ureport.console.designer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bstek.ureport.definition.CellDefinition;
import com.bstek.ureport.definition.ColumnDefinition;
import com.bstek.ureport.definition.HeaderFooterDefinition;
import com.bstek.ureport.definition.Paper;
import com.bstek.ureport.definition.ReportDefinition;
import com.bstek.ureport.definition.RowDefinition;
import com.bstek.ureport.definition.datasource.DatasourceDefinition;
import com.bstek.ureport.definition.searchform.SearchForm;

/**
 * @author Jacky.gao
 * @since 2017年1月29日
 */
public class ReportDefinitionWrapper {
	private Paper paper;
	private HeaderFooterDefinition header;
	private HeaderFooterDefinition footer;
	private SearchForm searchForm;
	private String searchFormXml;
	private List<RowDefinition> rows;
	private List<ColumnDefinition> columns;
	private List<DatasourceDefinition> datasources;
	private Map<String,CellDefinition> cellsMap=new HashMap<String,CellDefinition>();
	public ReportDefinitionWrapper(ReportDefinition report) {
		this.paper=report.getPaper();
		this.header=report.getHeader();
		this.footer=report.getFooter();
		this.searchForm=report.getSearchForm();
		this.searchFormXml=report.getSearchFormXml();
		this.rows=report.getRows();
		this.columns=report.getColumns();
		this.datasources=report.getDatasources();
		for(CellDefinition cell:report.getCells()){
			cellsMap.put(cell.getRowNumber()+","+cell.getColumnNumber(), cell);
		}
	}
	public List<ColumnDefinition> getColumns() {
		return columns;
	}
	public List<DatasourceDefinition> getDatasources() {
		return datasources;
	}
	public HeaderFooterDefinition getFooter() {
		return footer;
	}
	public HeaderFooterDefinition getHeader() {
		return header;
	}
	public Paper getPaper() {
		return paper;
	}
	public SearchForm getSearchForm() {
		return searchForm;
	}
	public void setSearchForm(SearchForm searchForm) {
		this.searchForm = searchForm;
	}
	public Map<String, CellDefinition> getCellsMap() {
		return cellsMap;
	}
	public List<RowDefinition> getRows() {
		return rows;
	}
	public String getSearchFormXml() {
		return searchFormXml;
	}
	public void setSearchFormXml(String searchFormXml) {
		this.searchFormXml = searchFormXml;
	}
}
