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
package com.bstek.ureport.build.paging;

import java.util.ArrayList;
import java.util.List;

import com.bstek.ureport.definition.Orientation;
import com.bstek.ureport.definition.Paper;
import com.bstek.ureport.model.Report;
import com.bstek.ureport.model.Row;

/**
 * @author Jacky.gao
 * @since 2017年1月17日
 */
public class FitPagePagination extends BasePagination implements Pagination {
	@Override
	public List<Page> doPaging(Report report) {
		Paper paper=report.getPaper();
		int height=paper.getHeight()-paper.getBottomMargin()-paper.getTopMargin();
		if(paper.getOrientation().equals(Orientation.landscape)){
			height=paper.getWidth()-paper.getLeftMargin()-paper.getRightMargin();
		}
		List<Row> rows=report.getRows();
		List<Row> headerRows=report.getHeaderRepeatRows();
		List<Row> footerRows=report.getFooterRepeatRows();
		int repeatRowHeight=0;
		for(Row row:headerRows){
			repeatRowHeight+=row.getRealHeight();
		}
		for(Row row:footerRows){
			repeatRowHeight+=row.getRealHeight();
		}
		int rowHeight=repeatRowHeight;
		List<Page> pages=new ArrayList<Page>();
		List<Row> pageRows=new ArrayList<Row>();
		int i=0;
		int rowSize=rows.size();
		Row row=rows.get(i);
		int pageIndex=1;
		while(row!=null){
			int rowRealHeight=row.getRealHeight();
			if(rowRealHeight>1 && row.getBand()==null){
				rowHeight+=rowRealHeight;
				pageRows.add(row);
				boolean overflow=false;
				if((i+1)<rows.size()){
					Row nextRow=rows.get(i+1);
					if((rowHeight+nextRow.getRealHeight()) > height){
						overflow=true;
					}
				}
				if(overflow){
					Page newPage=buildPage(pageRows,headerRows,footerRows,pageIndex,report);
					pageIndex++;
					pages.add(newPage);
					rowHeight=repeatRowHeight;
					pageRows=new ArrayList<Row>();
				}
			}
			i++;
			if(i<rowSize){
				row=rows.get(i);
			}else{
				row=null;
			}
		}
		if(rowHeight>0){
			Page newPage=buildPage(pageRows,headerRows,footerRows,pageIndex,report);
			pages.add(newPage);
		}
		buildPageHeaderFooter(pages, report);
		return pages;
	}
}
