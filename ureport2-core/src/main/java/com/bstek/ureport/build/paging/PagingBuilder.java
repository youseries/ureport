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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bstek.ureport.definition.PagingMode;
import com.bstek.ureport.definition.Paper;
import com.bstek.ureport.model.Report;

/**
 * @author Jacky.gao
 * @since 2017年1月17日
 */
public class PagingBuilder {
	private static Map<PagingMode,Pagination> paginationMap=new HashMap<PagingMode,Pagination>();
	static{
		paginationMap.put(PagingMode.fitpage, new FitPagePagination());
		paginationMap.put(PagingMode.fixrows, new FixRowsPagination());
	}
	public static List<Page> buildPages(Report report){
		Paper paper = report.getPaper();
		Pagination pagination=paginationMap.get(paper.getPagingMode());
		List<Page> pages=pagination.doPaging(report);
		return pages;
	}
}
