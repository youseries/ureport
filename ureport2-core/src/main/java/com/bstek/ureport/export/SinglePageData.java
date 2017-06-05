/*******************************************************************************
 * Copyright 2017 Bstek
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
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
