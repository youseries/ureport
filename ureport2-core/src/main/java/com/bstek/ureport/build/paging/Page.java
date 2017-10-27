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
package com.bstek.ureport.build.paging;

import java.util.List;

import com.bstek.ureport.model.Column;
import com.bstek.ureport.model.Row;

/**
 * @author Jacky.gao
 * @since 2017年1月17日
 */
public class Page {
	private List<Row> rows;
	private List<Column> columns;
	private HeaderFooter header;
	private HeaderFooter footer;
	
	public Page(List<Row> rows,List<Column> columns) {
		this.rows = rows;
		this.columns=columns;
	}
	public List<Row> getRows() {
		return rows;
	}
	public List<Column> getColumns() {
		return columns;
	}
	public HeaderFooter getHeader() {
		return header;
	}
	public void setHeader(HeaderFooter header) {
		this.header = header;
	}
	public HeaderFooter getFooter() {
		return footer;
	}
	public void setFooter(HeaderFooter footer) {
		this.footer = footer;
	}
}
