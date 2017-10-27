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

import java.util.ArrayList;
import java.util.List;

import com.bstek.ureport.build.paging.Page;
import com.bstek.ureport.definition.Paper;
import com.bstek.ureport.model.Report;

/**
 * @author Jacky.gao
 * @since 2017年3月23日
 */
public class PageBuilder {
	public static FullPageData buildFullPageData(Report report){
		List<Page> pages=report.getPages();
		int pageSize=pages.size();
		int totalPages=pageSize;
		Paper paper=report.getPaper();
		List<List<Page>> list=new ArrayList<List<Page>>();
		if(paper.isColumnEnabled()){
			int columnCount=paper.getColumnCount();
			totalPages=totalPages/columnCount;
			int mode=totalPages%columnCount;
			if(mode>0){
				totalPages++;
			}
			for(int i=0;i<pageSize;i++){
				List<Page> columnPages=new ArrayList<Page>();
				int end=i+columnCount;
				for(int j=i;j<end && j<pageSize;j++){
					columnPages.add(pages.get(j));
					i=j;
				}
				list.add(columnPages);
			}
		}
		return new FullPageData(totalPages,paper.getColumnMargin(),list);
	}
	public static SinglePageData buildSinglePageData(int pageIndex,Report report){
		List<Page> pages=report.getPages();
		int pageSize=pages.size();
		int totalPages=pageSize;
		Paper paper=report.getPaper();
		List<Page> columnPages=new ArrayList<Page>();
		if(paper.isColumnEnabled()){
			int columnCount=paper.getColumnCount();
			totalPages=pageSize/columnCount;
			int mode=pageSize%columnCount;
			if(mode>0){
				totalPages++;
			}
			int pageStart=(pageIndex-1)*columnCount,pageEnd=pageStart+columnCount;
			if(pageStart+1>pageSize){
				pageStart=pageSize-1;
			}
			if(pageEnd<=pageStart){
				pageEnd=pageStart+1;
			}
			for(int i=pageStart;i<pageEnd;i++){
				if(i>=pageSize){
					break;
				}
				columnPages.add(pages.get(i));
			}
		}else{
			if(pageIndex>pageSize){
				pageIndex=pageSize;
			}
			columnPages.add(pages.get(pageIndex-1));
		}
		return new SinglePageData(totalPages,pageIndex,paper.getColumnMargin(),columnPages);
	}
	public static void main(String[] args) {
		int columnCount=3;
		int totalPages=30;
		int total=totalPages/columnCount;
		int mode=totalPages%columnCount;
		if(mode>0){
			total++;
		}
		System.out.println(total);
	}
}
