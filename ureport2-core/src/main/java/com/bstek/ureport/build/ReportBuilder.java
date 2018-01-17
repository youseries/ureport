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
package com.bstek.ureport.build;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.bstek.ureport.Utils;
import com.bstek.ureport.build.cell.CellBuilder;
import com.bstek.ureport.build.cell.NoneExpandBuilder;
import com.bstek.ureport.build.cell.down.DownExpandBuilder;
import com.bstek.ureport.build.cell.right.RightExpandBuilder;
import com.bstek.ureport.build.paging.BasePagination;
import com.bstek.ureport.build.paging.Page;
import com.bstek.ureport.build.paging.PagingBuilder;
import com.bstek.ureport.definition.Band;
import com.bstek.ureport.definition.Expand;
import com.bstek.ureport.definition.Orientation;
import com.bstek.ureport.definition.PagingMode;
import com.bstek.ureport.definition.Paper;
import com.bstek.ureport.definition.ReportDefinition;
import com.bstek.ureport.definition.datasource.BuildinDatasource;
import com.bstek.ureport.definition.datasource.BuildinDatasourceDefinition;
import com.bstek.ureport.definition.datasource.DatasourceDefinition;
import com.bstek.ureport.definition.datasource.DatasourceProvider;
import com.bstek.ureport.definition.datasource.JdbcDatasourceDefinition;
import com.bstek.ureport.definition.datasource.SpringBeanDatasourceDefinition;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.exception.ReportException;
import com.bstek.ureport.model.Cell;
import com.bstek.ureport.model.Column;
import com.bstek.ureport.model.Report;
import com.bstek.ureport.model.Row;

/**
 * @author Jacky.gao
 * @since 2016年11月1日
 */
public class ReportBuilder extends BasePagination implements ApplicationContextAware{
	public static final String BEAN_ID="ureport.reportBuilder";
	private ApplicationContext applicationContext;
	private Map<String,DatasourceProvider> datasourceProviderMap=new HashMap<String,DatasourceProvider>();
	private Map<Expand,CellBuilder> cellBuildersMap=new HashMap<Expand,CellBuilder>();
	private NoneExpandBuilder noneExpandBuilder=new NoneExpandBuilder();
	private HideRowColumnBuilder hideRowColumnBuilder;
	public ReportBuilder() {
		cellBuildersMap.put(Expand.Right,new RightExpandBuilder());
		cellBuildersMap.put(Expand.Down,new DownExpandBuilder());
		cellBuildersMap.put(Expand.None,noneExpandBuilder);
	}
	public Report buildReport(ReportDefinition reportDefinition,Map<String,Object> parameters) {
		Report report = reportDefinition.newReport();
		Map<String,Dataset> datasetMap=buildDatasets(reportDefinition, parameters, applicationContext);
		Context context = new Context(this,report,datasetMap,applicationContext,parameters,hideRowColumnBuilder);
		long start=System.currentTimeMillis();
		List<Cell> cells=new ArrayList<Cell>();
		cells.add(report.getRootCell());
		do {			
			buildCell(context,cells);
			cells = context.nextUnprocessedCells();
		} while (cells != null);
		doFillBlankRows(report,context);
		recomputeCells(report,context);
		long end=System.currentTimeMillis();
		String msg="~~~ Report compute completed:"+(end-start)+"ms";
		Utils.logToConsole(msg);
		return report;
	}
	
	public void buildCell(Context context,List<Cell> cells){
		if(cells==null){
			cells=context.nextUnprocessedCells();			
		}
		if(cells==null){
			return;
		}
		for(Cell cell:cells){
			List<BindData> dataList=context.buildCellData(cell);
			cell.setProcessed(true);
			int size=dataList.size();
			Cell lastCell=cell;
			if(size==1){
				lastCell=noneExpandBuilder.buildCell(dataList, cell, context);
			}else if(size>1){
				CellBuilder cellBuilder=cellBuildersMap.get(cell.getExpand());
				lastCell=cellBuilder.buildCell(dataList,cell, context);				
			}
			if(lastCell.isFillBlankRows() && lastCell.getMultiple()>0){
				int result=size % lastCell.getMultiple();
				if(result>0){
					int value=lastCell.getMultiple()-result;
					context.addFillBlankRow(lastCell.getRow(), value);
				}
			}
		}
	}
	
	private Map<String,Dataset> buildDatasets(ReportDefinition reportDefinition,Map<String,Object> parameters,ApplicationContext applicationContext){
		Map<String,Dataset> datasetMap=new HashMap<String,Dataset>();
		List<DatasourceDefinition> datasources=reportDefinition.getDatasources();
		if(datasources==null){
			return datasetMap;
		}
		for(DatasourceDefinition dsDef:datasources){
			if(dsDef instanceof JdbcDatasourceDefinition){
				String dsName=dsDef.getName();
				Connection conn=null;
				try{
					if(datasourceProviderMap.containsKey(dsName)){
						conn=datasourceProviderMap.get(dsName).getConnection();
					}
					JdbcDatasourceDefinition ds=(JdbcDatasourceDefinition)dsDef;
					List<Dataset> ls=ds.buildDatasets(conn, parameters);
					if(ls!=null){
						for(Dataset dataset:ls){
							datasetMap.put(dataset.getName(), dataset);
						}					
					}
				}finally{
					if(conn!=null){
						try {
							conn.close();
						} catch (SQLException e) {}
					}
				}
			}else if(dsDef instanceof SpringBeanDatasourceDefinition){
				SpringBeanDatasourceDefinition ds=(SpringBeanDatasourceDefinition)dsDef;
				List<Dataset> ls=ds.getDatasets(applicationContext, parameters);
				if(ls!=null){
					for(Dataset dataset:ls){
						datasetMap.put(dataset.getName(), dataset);
					}
				}
			}else if(dsDef instanceof BuildinDatasourceDefinition){
				String dsName=dsDef.getName();
				Connection conn=null;
				try{
					if(datasourceProviderMap.containsKey(dsName)){
						conn=datasourceProviderMap.get(dsName).getConnection();
					}
					for(BuildinDatasource datasource:Utils.getBuildinDatasources()){
						if(datasource.name().equals(dsName)){
							conn=datasource.getConnection();
							break;
						}
					}
					if(conn==null){
						throw new ReportComputeException("Buildin datasource ["+dsName+"] not exist.");
					}
					BuildinDatasourceDefinition ds=(BuildinDatasourceDefinition)dsDef;
					List<Dataset> ls=ds.buildDatasets(conn, parameters);
					if(ls!=null){					
						for(Dataset dataset:ls){
							datasetMap.put(dataset.getName(), dataset);
						}
					}
				}finally{
					if(conn!=null){
						try {
							conn.close();
						} catch (SQLException e) {}
					}
				}
			}
		}
		return datasetMap;
	}
	
	private void doFillBlankRows(Report report,Context context){
		Map<Row, Integer> map=context.getFillBlankRowsMap();
		List<Row> newRowList=new ArrayList<Row>();
		for(Row row:map.keySet()){
			int size=map.get(row);
			Row lastRow=findLastRow(row, report);
			for(int i=0;i<size;i++){
				Row newRow=buildNewRow(lastRow, report);
				newRowList.add(newRow);
			}
			int rowNumber=lastRow.getRowNumber();
			if(newRowList.size()>0){
				report.insertRows(rowNumber+1, newRowList);
				newRowList.clear();
			}
		}
	}
	
	private Row buildNewRow(Row row,Report report){
		Row newRow=row.newRow();
		newRow.setBand(null);
		List<Row> rows=report.getRows();
		List<Column> columns=report.getColumns();
		int start=-1,colSize=columns.size();
		Map<Row, Map<Column, Cell>> rowMap=report.getRowColCellMap();
		Map<Column,Cell> newCellMap=new HashMap<Column,Cell>();
		rowMap.put(newRow, newCellMap);
		
		Map<Column, Cell> colMap=rowMap.get(row);
		for(int index=0;index<colSize;index++){
			Column column=columns.get(index);
			Cell currentCell=colMap.get(column);
			if(currentCell==null){
				if(start==-1){
					start=row.getRowNumber()-2;
				}
				for(int i=start;i>-1;i--){
					Row currentRow=rows.get(i);
					Map<Column, Cell> prevColMap=rowMap.get(currentRow);
					if(prevColMap==null){
						continue;
					}
					if(prevColMap.containsKey(column)){
						currentCell=prevColMap.get(column);
						break;
					}
				}
			}
			if(currentCell==null){
				throw new ReportException("Insert blank rows fail.");
			}
			int colSpan=currentCell.getColSpan();
			if(colSpan>0){
				colSpan--;
				index+=colSpan;
			}
			int rowSpan=currentCell.getRowSpan();
			if(rowSpan>1){
				currentCell.setRowSpan(rowSpan+1);
			}else{
				Cell newCell=newBlankCell(currentCell, column, report);
				newCell.setRow(newRow);
				newRow.getCells().add(newCell);
				newCellMap.put(newCell.getColumn(), newCell);
			}
		}
		return newRow;
	}
	
	private Row findLastRow(Row row,Report report){
		List<Row> rows=report.getRows();
		List<Cell> cells=row.getCells();
		Row lastRow=row;
		int span=0;
		for(Cell cell:cells){
			int rowSpan=cell.getRowSpan();
			if(rowSpan<2){
				continue;
			}
			if(span==0){
				span=rowSpan;
			}else if(rowSpan>span){
				span=rowSpan;
			}
		}
		if(span>1){
			int rowIndex=row.getRowNumber()-1+span-1;
			lastRow=rows.get(rowIndex);
		}
		return lastRow;
	}
	
	
	private Cell newBlankCell(Cell cell,Column column,Report report){
		Cell newCell=new Cell();
		newCell.setData("");
		newCell.setColSpan(cell.getColSpan());
		newCell.setConditionPropertyItems(cell.getConditionPropertyItems());
		report.addLazyCell(newCell);
		newCell.setCellStyle(cell.getCellStyle());
		newCell.setName(cell.getName());
		newCell.setColumn(column);
		column.getCells().add(newCell);
		Cell leftParent=cell.getLeftParentCell();
		if(leftParent!=null){
			newCell.setLeftParentCell(leftParent);
			leftParent.addRowChild(newCell);
		}
		Cell topParent=cell.getTopParentCell();
		if(topParent!=null){
			newCell.setTopParentCell(topParent);
			topParent.addColumnChild(newCell);					
		}
		return newCell;
	}
	
	private void recomputeCells(Report report,Context context){
		List<Cell> lazyCells=report.getLazyComputeCells();
		for(Cell cell:lazyCells){
			cell.doCompute(context);
		}
		context.setDoPaging(true);
		List<Row> rows=report.getRows();
		int rowSize=rows.size();
		Paper paper = report.getPaper();
		PagingMode pagingMode=paper.getPagingMode();
		List<Row> headerRows=report.getHeaderRepeatRows();
		List<Row> footerRows=report.getFooterRepeatRows();
		List<Row> titleRows=report.getTitleRows();
		List<Row> summaryRows=report.getSummaryRows();
		List<Page> pages=new ArrayList<Page>();
		List<Row> pageRows=new ArrayList<Row>();
		int pageIndex=1;
		List<Row> pageRepeatHeaders=new ArrayList<Row>();
		List<Row> pageRepeatFooters=new ArrayList<Row>();
		pageRepeatHeaders.addAll(headerRows);
		pageRepeatFooters.addAll(footerRows);
		if(pagingMode.equals(PagingMode.fitpage)){
			int height=paper.getHeight()-paper.getBottomMargin()-paper.getTopMargin()-5;
			if(paper.getOrientation().equals(Orientation.landscape)){
				height=paper.getWidth()-paper.getBottomMargin()-paper.getTopMargin()-5;
			}
			int repeatHeaderRowHeight=report.getRepeatHeaderRowHeight(),repeatFooterRowHeight=report.getRepeatFooterRowHeight();
			int titleRowHeight=report.getTitleRowsHeight();
			int rowHeight=titleRowHeight+repeatHeaderRowHeight+repeatFooterRowHeight;
			for(int i=0;i<rowSize;i++){
				Row row=rows.get(i);
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
				row.setPageIndex(pageIndex);
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
			}
			if(pageRows.size()>0){
				Page newPage=buildPage(pageRows,pageRepeatHeaders,pageRepeatFooters,titleRows,pageIndex,report);
				pages.add(newPage);
			}
			report.getContext().setTotalPages(pages.size());
			buildPageHeaderFooter(pages, report);
		}else{
			int fixRows=paper.getFixRows()-headerRows.size()-footerRows.size();
			if(fixRows<0){
				fixRows=1;
			}
			for(int i=0;i<rowSize;i++){
				Row row=rows.get(i);
				int height=row.getRealHeight();
				if(height==0){
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
				row.setPageIndex(pageIndex);
				pageRows.add(row);
				if(pageRows.size() >= fixRows){
					Page newPage=buildPage(pageRows,pageRepeatHeaders,pageRepeatFooters,titleRows,pageIndex,report);
					pageIndex++;
					pages.add(newPage);
					pageRows=new ArrayList<Row>();
				}
			}
			if(pageRows.size()>0){
				Page newPage=buildPage(pageRows,pageRepeatHeaders,pageRepeatFooters,titleRows,pageIndex,report);
				pages.add(newPage);
			}
			report.getContext().setTotalPages(pages.size());
			buildPageHeaderFooter(pages, report);
		}
		buildSummaryRows(summaryRows, pages);
		PagingBuilder.computeExistPageFunctionCells(report);
		report.setPages(pages);
	}
	
	public void setHideRowColumnBuilder(HideRowColumnBuilder hideRowColumnBuilder) {
		this.hideRowColumnBuilder = hideRowColumnBuilder;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
		Collection<DatasourceProvider> datasourceProviders=applicationContext.getBeansOfType(DatasourceProvider.class).values();
		for(DatasourceProvider dp: datasourceProviders){
			datasourceProviderMap.put(dp.getName(), dp);
		}
		new Splash().doPrint();
	}
}
