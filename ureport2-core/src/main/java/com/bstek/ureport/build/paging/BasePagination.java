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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bstek.ureport.build.Context;
import com.bstek.ureport.definition.HeaderFooterDefinition;
import com.bstek.ureport.model.Cell;
import com.bstek.ureport.model.Column;
import com.bstek.ureport.model.Report;
import com.bstek.ureport.model.Row;


/**
 * @author Jacky.gao
 * @since 2017年1月17日
 */
public abstract class BasePagination {
	protected void buildSummaryRows(List<Row> summaryRows,List<Page> pages){
		Page lastPage=pages.get(pages.size()-1);
		List<Row> lastPageRows=lastPage.getRows();
		for(Row row:summaryRows){
			row.setPageIndex(pages.size());
			lastPageRows.add(row);
		}
	}
	protected Page buildPage(List<Row> rows,List<Row> headerRows,List<Row> footerRows,List<Row> titleRows,int pageIndex,Report report){
		int rowSize=rows.size();
		Row lastRow=rows.get(rowSize-1);
		int lastRowNumber=lastRow.getRowNumber();
		List<Column> columns=report.getColumns();
		Context context=report.getContext();
		context.setPageIndex(pageIndex);
		context.setCurrentPageRows(pageIndex,rows);
		Map<Row, Map<Column, Cell>> rowColCellsMap=report.getRowColCellMap();
		List<Row> reportRows=report.getRows();
		for(int i=0;i<rows.size();i++){
			Row row=rows.get(i);
			Map<Column,Cell> colMap=rowColCellsMap.get(row);
			if(colMap==null){
				continue;
			}
			for(Column col:columns){
				Cell cell=colMap.get(col);
				if(cell==null){
					continue;
				}
				int rowSpan=cell.getPageRowSpan();
				if(rowSpan==0){
					continue;
				}else{
					int span=rowSpan-1;
					int pageRowNumber=i+1;
					int maxRow=pageRowNumber+span;
					if(maxRow<=rowSize){
						continue;
					}else{
						Cell newCell=cell.newCell();
						newCell.setFormatData(cell.getFormatData());
						newCell.setForPaging(true);
						int leftSpan=rowSize-pageRowNumber;
						if(leftSpan>0){
							leftSpan++;							
							cell.setPageRowSpan(leftSpan);
						}else{
							cell.setPageRowSpan(0);
						}
						newCell.setData(cell.getData());
						int newSpan=maxRow-rowSize;
						if(newSpan>1){
							newCell.setPageRowSpan(newSpan);
							newCell.setRowSpan(newSpan);
						}else{
							newCell.setPageRowSpan(0);		
							newCell.setRowSpan(0);
						}
						
						int nextRowNumber=lastRowNumber+1;
						Row nextRow=fetchNextRow(reportRows, nextRowNumber-1);
						newCell.setRow(nextRow);
						Map<Column,Cell> cmap=null;
						if(rowColCellsMap.containsKey(nextRow)){
							cmap=rowColCellsMap.get(nextRow);
						}else{
							cmap=new HashMap<Column,Cell>();
							rowColCellsMap.put(nextRow, cmap);
						}
						cmap.put(newCell.getColumn(),newCell);
					}
				}
			}
		}
		int headerRowSize=headerRows.size()-1;
		for(int i=headerRowSize;i>-1;i--){
			Row row=headerRows.get(i);
			row.setPageIndex(pageIndex);
			Row newRow=duplicateRepeateRow(row, context);
			newRow.setPageIndex(pageIndex);
			rows.add(0,newRow);
			Map<Column,Cell> colMap=rowColCellsMap.get(newRow);
			if(colMap==null){
				continue;
			}
			for(Column col:columns){
				Cell cell=colMap.get(col);
				if(cell==null){
					continue;
				}
			}
		}
		if(pageIndex==1){
			int titleRowSize=titleRows.size()-1;
			for(int i=titleRowSize;i>-1;i--){
				Row row=titleRows.get(i);
				rows.add(0,row);
				Map<Column,Cell> colMap=rowColCellsMap.get(row);
				if(colMap==null){
					continue;
				}
				for(Column col:columns){
					Cell cell=colMap.get(col);
					if(cell==null){
						continue;
					}
				}
			}
		}
		for(Row row:footerRows){
			Row newRow=duplicateRepeateRow(row, context);
			newRow.setPageIndex(pageIndex);
			rows.add(newRow);
			Map<Column,Cell> colMap=rowColCellsMap.get(newRow);
			if(colMap==null){
				continue;
			}
			for(Column col:columns){
				Cell cell=colMap.get(col);
				if(cell==null){
					continue;
				}
			}
		}
		Page page=new Page(rows,columns);
		return page;
	}
	
	private Row fetchNextRow(List<Row> reportRows,int rowNumber){
		Row row=null;
		do{
			if(rowNumber>=reportRows.size()){
				break;
			}
			row=reportRows.get(rowNumber);
			rowNumber++;
		}while(row.getRealHeight()==0);
		return row;
	}

	private Row duplicateRepeateRow(Row row,Context context){
		Row newRow=row.newRow();
		newRow.setPageIndex(row.getPageIndex());
		Map<Row, Map<Column, Cell>> cellMap=context.getReport().getRowColCellMap();
		Map<Column, Cell> map=cellMap.get(row);
		if(map==null){
			return newRow;
		}
		Map<Column, Cell> newMap=new HashMap<Column,Cell>();
		cellMap.put(newRow, newMap);
		List<Column> columns=context.getReport().getColumns();
		for(Column col:columns){
			Cell cell=map.get(col);
			if(cell==null){
				continue;
			}
			Cell newCell=cell.newCell();
			newCell.setRow(newRow);
			newCell.setFormatData(cell.getFormatData());
			newCell.setData(cell.getData());
			newCell.setCustomCellStyle(cell.getCustomCellStyle());
			newCell.setFormatData(cell.getFormatData());
			newCell.setExistPageFunction(cell.isExistPageFunction());
			if(cell.isExistPageFunction()){
				context.addExistPageFunctionCells(newCell);				
			}
			newMap.put(col, newCell);
		}
		return newRow;
	}
	protected void buildPageHeaderFooter(List<Page> pages,Report report){
		int totalPages=pages.size();
		for(int i=0;i<totalPages;i++){
			Page page=pages.get(i);
			HeaderFooterDefinition headerDef=report.getHeader();
			int pageIndex=i+1;
			if(headerDef!=null){
				HeaderFooter hf=headerDef.buildHeaderFooter(pageIndex, report.getContext());
				page.setHeader(hf);
			}
			HeaderFooterDefinition footerDef=report.getFooter();
			if(footerDef!=null){
				HeaderFooter hf=footerDef.buildHeaderFooter(pageIndex, report.getContext());
				page.setFooter(hf);
			}
		}
	}
}
