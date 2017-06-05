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
	public ReportBuilder() {
		cellBuildersMap.put(Expand.Right,new RightExpandBuilder());
		cellBuildersMap.put(Expand.Down,new DownExpandBuilder());
		cellBuildersMap.put(Expand.None,noneExpandBuilder);
	}
	public Report buildReport(ReportDefinition reportDefinition,Map<String,Object> parameters) {
		Report report = reportDefinition.newReport();
		Map<String,Dataset> datasetMap=buildDatasets(reportDefinition, parameters, applicationContext);
		Context context = new Context(this,report,datasetMap,applicationContext,parameters);
		long start=System.currentTimeMillis();
		List<Cell> cells=new ArrayList<Cell>();
		cells.add(report.getRootCell());
		do {			
			buildCell(context,cells);
			cells = context.nextUnprocessedCells();
		} while (cells != null);
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
			if(size==1){
				noneExpandBuilder.buildCell(dataList, cell, context);
			}else if(size>1){
				CellBuilder cellBuilder=cellBuildersMap.get(cell.getExpand());
				cellBuilder.buildCell(dataList,cell, context);				
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
			}
		}
		return datasetMap;
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
		List<Page> pages=new ArrayList<Page>();
		List<Row> pageRows=new ArrayList<Row>();
		int pageIndex=1;
		if(pagingMode.equals(PagingMode.fitpage)){
			int height=paper.getHeight()-paper.getBottomMargin()-paper.getTopMargin();
			if(paper.getOrientation().equals(Orientation.landscape)){
				height=paper.getWidth()-paper.getLeftMargin()-paper.getRightMargin();
			}
			int repeatRowHeight=0;
			for(Row row:headerRows){
				repeatRowHeight+=row.getRealHeight();
			}
			for(Row row:footerRows){
				repeatRowHeight+=row.getRealHeight();
			}
			int rowHeight=repeatRowHeight;
			for(int i=0;i<rowSize;i++){
				Row row=rows.get(i);
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
				processRowColumn(report, i, row);
			}
			if(rowHeight>0){
				Page newPage=buildPage(pageRows,headerRows,footerRows,pageIndex,report);
				pages.add(newPage);
			}
			buildPageHeaderFooter(pages, report);
		}else{
			int fixRows=paper.getFixRows();
			for(int i=0;i<rowSize;i++){
				Row row=rows.get(i);
				processRowColumn(report, i, row);
				
				int height=row.getRealHeight();
				if(height<1){
					continue;
				}
				pageRows.add(row);
				if((pageRows.size()+footerRows.size()) >= fixRows){
					pageRows.addAll(footerRows);
					Page newPage=buildPage(pageRows,headerRows,footerRows,pageIndex,report);
					pageIndex++;
					pages.add(newPage);
					pageRows=new ArrayList<Row>();
				}
			}
			if(pageRows.size()>headerRows.size()){
				pageRows.addAll(footerRows);
				Page newPage=buildPage(pageRows,headerRows,footerRows,pageIndex,report);
				pages.add(newPage);
			}
			buildPageHeaderFooter(pages, report);
		}
		report.setPages(pages);
	}
	private void processRowColumn(Report report, int i, Row row) {
		Map<Row, Map<Column, Cell>> cellMap=report.getRowColCellMap();
		Map<Column,Cell> map=cellMap.get(row);
		if(map==null){
			return;
		}
		List<Row> rows=report.getRows();
		List<Column> columns=report.getColumns();
		int colSize=columns.size();
		for(int j=0;j<colSize;j++){
			Column col=columns.get(j);
			if(col==null){
				continue;
			}
			Cell cell=map.get(col);
			if(cell==null){
				continue;
			}
			int colWidth=col.getWidth();
			int colSpan=cell.getColSpan();
			if(colWidth<1){
				if(colSpan>1){
					colSpan--;
					if(colSpan<2)colSpan=0;
					cell.setColSpan(colSpan);
					Column nextCol=columns.get(j+1);
					map.put(nextCol, cell);
				}
				map.remove(col);
			}else{
				if(colSpan>1){
					int start=j+1,end=j+colSpan;
					for(int num=start;num<end;num++){
						Column nextCol=columns.get(num);
						if(nextCol.getWidth()<1){
							colSpan--;
						}
					}
					if(colSpan<2){
						colSpan=0;
					}
					cell.setColSpan(colSpan);
				}
			}
			int rowHeight=row.getRealHeight();
			int rowSpan=cell.getRowSpan();
			if(rowHeight<1){
				if(rowSpan>1){
					rowSpan--;
					if(rowSpan<2)rowSpan=0;
					cell.setRowSpan(rowSpan);
					Row nextRow=rows.get(i+1);
					Map<Column,Cell> cmap=cellMap.get(nextRow);
					cmap.put(col, cell);
				}
			}else{
				if(rowSpan>1){
					int start=i+1,end=i+rowSpan;
					for(int num=start;num<end;num++){
						Row nextRow=rows.get(num);
						if(nextRow.getRealHeight()<1){
							rowSpan--;
						}
					}
					if(rowSpan<2){
						rowSpan=0;
					}
					cell.setRowSpan(rowSpan);
				}
			}
		}
		if(row.getRealHeight()<1){
			cellMap.remove(row);
		}
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
		Collection<DatasourceProvider> datasourceProviders=applicationContext.getBeansOfType(DatasourceProvider.class).values();
		for(DatasourceProvider dp: datasourceProviders){
			datasourceProviderMap.put(dp.getName(), dp);
		}
		StringBuilder sb=new StringBuilder();
		sb.append("\n");
		sb.append("`7MMF'   `7MF'`7MM\"\"\"Mq.  `7MM\"\"\"YMM  `7MM\"\"\"Mq.   .g8\"\"8q.   `7MM\"\"\"Mq.  MMP\"\"MM\"\"YMM          ");
		sb.append("\n");
		sb.append("  MM       M    MM   `MM.   MM    `7    MM   `MM..dP'    `YM.   MM   `MM. P'   MM   `7          ");
		sb.append("\n");
		sb.append("  MM       M    MM   ,M9    MM   d      MM   ,M9 dM'      `MM   MM   ,M9       MM       pd*\"*b. ");
		sb.append("\n");
		sb.append("  MM       M    MMmmdM9     MMmmMM      MMmmdM9  MM        MM   MMmmdM9        MM      (O)   j8 ");
		sb.append("\n");
		sb.append("  MM       M    MM  YM.     MM   Y  ,   MM       MM.      ,MP   MM  YM.        MM          ,;j9 ");
		sb.append("\n");
		sb.append("  YM.     ,M    MM   `Mb.   MM     ,M   MM       `Mb.    ,dP'   MM   `Mb.      MM       ,-='    ");
		sb.append("\n");
		sb.append("   `bmmmmd\"'  .JMML. .JMM..JMMmmmmMMM .JMML.       `\"bmmd\"'   .JMML. .JMM.   .JMML.    Ammmmmmm ");
		sb.append("\n");
		sb.append(".....................................................................................................");
		sb.append("\n");
		sb.append(".  uReport, is a Chinese style report engine");
		sb.append(" licensed under the Apache License 2.0,                 .");
		sb.append("\n");
		sb.append(".  which is opensource, free of charge, easy to use,");
		sb.append("high-performance, with browser-based-designer.  .");
		sb.append("\n");
		sb.append(".....................................................................................................");
		sb.append("\n");
		System.out.println(sb.toString());
	}
}
