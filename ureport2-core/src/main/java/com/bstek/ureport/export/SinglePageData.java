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
package com.bstek.ureport.export;

import java.util.List;

import com.bstek.ureport.build.paging.Page;

/**
 * @author Jacky.gao
 * @since 2017年3月23日
 */
public class SinglePageData {
	private int totalPages;
	private int pageIndex;
	private List<Page> pages;
	private int columnMargin;
	
	public SinglePageData(int totalPages, int columnMargin,List<Page> pages) {
		this.totalPages = totalPages;
		this.columnMargin=columnMargin;
		this.pages = pages;
	}
	public SinglePageData(int totalPages, int pageIndex, int columnMargin,List<Page> pages) {
		this.totalPages = totalPages;
		this.pageIndex = pageIndex;
		this.columnMargin=columnMargin;
		this.pages = pages;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public List<Page> getPages() {
		return pages;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public int getColumnMargin() {
		return columnMargin;
	}
}
