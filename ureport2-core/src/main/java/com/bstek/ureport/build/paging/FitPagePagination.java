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

import java.util.ArrayList;
import java.util.List;

import com.bstek.ureport.definition.Band;
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
		int height=paper.getHeight()-paper.getBottomMargin()-paper.getTopMargin()-5;
		if(paper.getOrientation().equals(Orientation.landscape)){
			height=paper.getWidth()-paper.getBottomMargin()-paper.getTopMargin()-5;
		}
		List<Row> rows=report.getRows();
		List<Row> headerRows=report.getHeaderRepeatRows();
		List<Row> footerRows=report.getFooterRepeatRows();
		List<Row> titleRows=report.getTitleRows();
		List<Row> summaryRows=report.getSummaryRows();
		int repeatHeaderRowHeight=report.getRepeatHeaderRowHeight(),repeatFooterRowHeight=report.getRepeatFooterRowHeight();
		int titleRowHeight=report.getTitleRowsHeight();
		int rowHeight=titleRowHeight+repeatHeaderRowHeight+repeatFooterRowHeight;
		List<Page> pages=new ArrayList<Page>();
		List<Row> pageRows=new ArrayList<Row>();
		List<Row> pageRepeatHeaders=new ArrayList<Row>();
		List<Row> pageRepeatFooters=new ArrayList<Row>();
		pageRepeatHeaders.addAll(headerRows);
		pageRepeatFooters.addAll(footerRows);
		int i=0;
		int rowSize=rows.size();
		Row row=rows.get(i);
		int pageIndex=1;
		while(row!=null){
			int rowRealHeight=row.getRealHeight();
			if(rowRealHeight==0){
				continue;
			}
			Band band=row.getBand();
			if(band!=null){
				String rowKey=row.getRowKey();
				int index=-1;
				if(band.equals(Band.headerrepeat)){
					for(int j=0;j<pageRepeatHeaders.size();j++){
						Row headerRow=pageRepeatHeaders.get(j);
						if(headerRow.getRowKey().equals(rowKey)){
							index=j;
							break;
						}
					}
					pageRepeatHeaders.remove(index);
					pageRepeatHeaders.add(index,row);
				}else if(band.equals(Band.footerrepeat)){
					for(int j=0;j<pageRepeatFooters.size();j++){
						Row footerRow=pageRepeatFooters.get(j);
						if(footerRow.getRowKey().equals(rowKey)){
							index=j;
							break;
						}
					}
					pageRepeatFooters.remove(index);
					pageRepeatFooters.add(index,row);
				}
				continue;
			}
			rowHeight+=rowRealHeight+1;
			pageRows.add(row);
			boolean overflow=false;
			if((i+1)<rows.size()){
				Row nextRow=rows.get(i+1);
				if((rowHeight+nextRow.getRealHeight()) > height){
					overflow=true;
				}
			}
			if(!overflow && row.isPageBreak()){
				overflow=true;
			}
			if(overflow){
				Page newPage=buildPage(pageRows,pageRepeatHeaders,pageRepeatFooters,titleRows,pageIndex,report);
				pageIndex++;
				pages.add(newPage);
				rowHeight=repeatHeaderRowHeight+repeatFooterRowHeight;
				pageRows=new ArrayList<Row>();
			}
			i++;
			if(i<rowSize){
				row=rows.get(i);
			}else{
				row=null;
			}
		}
		if(pageRows.size()>0){
			Page newPage=buildPage(pageRows,pageRepeatHeaders,pageRepeatFooters,titleRows,pageIndex,report);
			pages.add(newPage);
		}
		buildPageHeaderFooter(pages, report);
		buildSummaryRows(summaryRows, pages);
		return pages;
	}
}
