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
