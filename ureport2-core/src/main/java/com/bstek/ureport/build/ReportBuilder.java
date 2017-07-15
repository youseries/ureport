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
import java.util.logging.Logger;

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
import com.bstek.ureport.definition.Band;
import com.bstek.ureport.definition.CellStyle;
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
	private static final Logger log=Logger.getGlobal();
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
		log.info("Report compute completed:"+(end-start)+"ms");
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
		List<Column> columns=report.getColumns();
		Map<Row, Map<Column, Cell>> rowMap=report.getRowColCellMap();
		Map<Row, Integer> map=context.getFillBlankRowsMap();
		List<Row> newRowList=new ArrayList<Row>();
		for(Row row:map.keySet()){
			Map<Column,Cell> cellMap=rowMap.get(row);
			int size=map.get(row);
			for(int i=0;i<size;i++){
				Row newRow=row.newRow();
				newRow.setBand(null);
				newRowList.add(newRow);
				Map<Column,Cell> newCellMap=new HashMap<Column,Cell>();
				rowMap.put(newRow, newCellMap);
				for(Column col:columns){
					Cell newCell=newBlankCell(cellMap, col,report);
					newCell.setRow(newRow);
					newRow.getCells().add(newCell);
					newCellMap.put(col, newCell);
				}
			}
			if(newRowList.size()>0){
				int rowNumber=row.getRowNumber();
				report.insertRows(rowNumber+1, newRowList);
				newRowList.clear();
			}
		}
	}
	
	private Cell newBlankCell(Map<Column,Cell> cellMap,Column column,Report report){
		if(cellMap!=null){
			Cell cell=cellMap.get(column);
			if(cell!=null){
				Cell newCell=new Cell();
				newCell.setData("");
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
		}
		Cell newCell=new Cell();
		newCell.setData("");
		newCell.setCellStyle(new CellStyle());
		newCell.setName("");
		newCell.setColumn(column);
		column.getCells().add(newCell);
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
			int height=paper.getHeight()-paper.getBottomMargin()-paper.getTopMargin();
			if(paper.getOrientation().equals(Orientation.landscape)){
				height=paper.getWidth()-paper.getBottomMargin()-paper.getTopMargin();
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
				rowHeight+=rowRealHeight;
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
			}
			if(pageRows.size()>0){
				Page newPage=buildPage(pageRows,pageRepeatHeaders,pageRepeatFooters,titleRows,pageIndex,report);
				pages.add(newPage);
			}
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
					if(index==-1){
						throw new ReportComputeException("Invalid row["+band+"] with key "+rowKey+".");
					}
				}
				if(band!=null){
					continue;
				}
				pageRows.add(row);
				if((pageRows.size()+footerRows.size()) >= fixRows){
					pageRows.addAll(footerRows);
					Page newPage=buildPage(pageRows,pageRepeatHeaders,pageRepeatFooters,titleRows,pageIndex,report);
					pageIndex++;
					pages.add(newPage);
					pageRows=new ArrayList<Row>();
				}
			}
			if(pageRows.size()>0){
				pageRows.addAll(footerRows);
				Page newPage=buildPage(pageRows,pageRepeatHeaders,pageRepeatFooters,titleRows,pageIndex,report);
				pages.add(newPage);
			}
			buildPageHeaderFooter(pages, report);
		}
		buildSummaryRows(summaryRows, pages);
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
