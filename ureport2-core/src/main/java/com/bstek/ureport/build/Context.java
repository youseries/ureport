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
package com.bstek.ureport.build;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import com.bstek.ureport.Utils;
import com.bstek.ureport.chart.ChartData;
import com.bstek.ureport.definition.mapping.MappingType;
import com.bstek.ureport.definition.value.SimpleValue;
import com.bstek.ureport.definition.value.Value;
import com.bstek.ureport.exception.CellDependencyException;
import com.bstek.ureport.exception.DatasetUndefinitionException;
import com.bstek.ureport.expression.model.expr.dataset.DatasetExpression;
import com.bstek.ureport.model.Cell;
import com.bstek.ureport.model.Column;
import com.bstek.ureport.model.Report;
import com.bstek.ureport.model.Row;

/**
 * @author Jacky.gao
 * @since 2016年11月1日
 */
public class Context {
	private Report report;
	private Cell rootCell;
	private int pageIndex;
	private int totalPages;
	private boolean doPaging;
	private List<Row> currentPageRows;
	private Map<String,Dataset> datasetMap;
	private ApplicationContext applicationContext;
	private ReportBuilder reportBuilder;
	private Map<String,Object> parameters;
	private HideRowColumnBuilder hideRowColumnBuilder;
	private Map<String,List<Cell>> unprocessedCellsMap = new HashMap<String,List<Cell>>();
	private Map<Row,Map<Column,Cell>> blankCellsMap=new HashMap<Row,Map<Column,Cell>>();
	private Map<Row,Integer> fillBlankRowsMap=new HashMap<Row,Integer>();
	private Map<String,ChartData> chartDataMap=new HashMap<String,ChartData>();
	public Context(ReportBuilder reportBuilder,Report report,Map<String,Dataset> datasetMap,ApplicationContext applicationContext,Map<String,Object> parameters,HideRowColumnBuilder hideRowColumnBuilder) {
		this.reportBuilder=reportBuilder;
		this.report = report;
		report.setContext(this);
		this.datasetMap=datasetMap;
		this.applicationContext=applicationContext;
		this.parameters=parameters;
		this.hideRowColumnBuilder=hideRowColumnBuilder;
		Map<String,List<Cell>> cellsMap=report.getCellsMap();
		for(String key:cellsMap.keySet()){
			if(key.equals(report.getRootCell().getName())){
				continue;
			}
			List<Cell> list=new ArrayList<Cell>();
			list.addAll(cellsMap.get(key));
			unprocessedCellsMap.put(key, list);
		}
		this.rootCell=new Cell();
		this.rootCell.setName("ROOT");
	}
	
	public Context(ApplicationContext applicationContext,Map<String,Object> parameters){
		this.applicationContext=applicationContext;
		this.parameters=parameters;
	}
	
	public Map<String,String> getMapping(DatasetExpression expr){
		if(expr.getMappingType().equals(MappingType.simple)){
			Map<String,String> mapping=expr.getMapping();
			return mapping;
		}else if(expr.getMappingType().equals(MappingType.dataset)){
			if(StringUtils.isNotBlank(expr.getMappingDataset()) && StringUtils.isNotBlank(expr.getMappingKeyProperty()) && StringUtils.isNotBlank(expr.getMappingValueProperty())){
				Map<String,String> mapping=new HashMap<String,String>();
				List<?> list=getDatasetData(expr.getMappingDataset());
				for(Object obj:list){
					Object key=Utils.getProperty(obj, expr.getMappingKeyProperty());
					Object value=Utils.getProperty(obj, expr.getMappingValueProperty());
					if(key!=null && value!=null){
						mapping.put(key.toString(), value.toString());
					}
				}
				return mapping;
			}
		}
		return null;
	}
	
	public void doHideProcessColumn(Column col) {
		hideRowColumnBuilder.doHideProcessColumn(report, col);
	}
	
	public void doHideProcessRow(Row row) {
		hideRowColumnBuilder.doHideProcessRow(report, row);
	}
	
	public void addFillBlankRow(Row row,int value){
		fillBlankRowsMap.put(row, value);
	}
	
	public Map<Row, Integer> getFillBlankRowsMap() {
		return fillBlankRowsMap;
	}
	
	public ReportBuilder getReportBuilder() {
		return reportBuilder;
	}
	
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public Map<String, Object> getParameters() {
		return parameters;
	}
	
	public boolean isDoPaging() {
		return doPaging;
	}
	public void setDoPaging(boolean doPaging) {
		this.doPaging = doPaging;
	}
	
	public List<BindData> buildCellData(Cell cell){
		return DataCompute.buildCellData(cell, this);
	}
	
	public Cell getBlankCell(Row row,Column column){
		Map<Column,Cell> colCellMap=blankCellsMap.get(row);
		if(colCellMap==null){
			return null;
		}
		Cell targetCell=colCellMap.get(column);
		return targetCell;
	}
	
	public void removeBlankCell(Cell blankCell){
		Row row=blankCell.getRow();
		Column col=blankCell.getColumn();
		Map<Column,Cell> colCellMap=blankCellsMap.get(row);
		colCellMap.remove(col);
	}
	
	public void addBlankCell(Cell cell){
		cell.setBlankCell(true);
		Row row=cell.getRow();
		Column column=cell.getColumn();
		Map<Column,Cell> cellMap=blankCellsMap.get(row);
		if(cellMap==null){
			cellMap=new HashMap<Column,Cell>();
			blankCellsMap.put(row, cellMap);
		}
		cellMap.put(column, cell);
		addReportCell(cell);
	}
	
	public void addCell(Cell newCell){
		addReportCell(newCell);
		addUnprocessedCell(newCell);
	}
	
	public void addUnprocessedCell(Cell cell){
		String cellName=cell.getName();
		List<Cell> cells=null;
		if(unprocessedCellsMap.containsKey(cellName)){
			cells=unprocessedCellsMap.get(cellName);
		}else{
			cells=new ArrayList<Cell>();
			unprocessedCellsMap.put(cellName, cells);
		}
		cells.add(cell);
	}
	
	public Map<Row, Map<Column, Cell>> getBlankCellsMap() {
		return blankCellsMap;
	}
	
	public void addReportCell(Cell newCell){
		boolean lazyAdd=report.addCell(newCell);
		if(lazyAdd){
			return;
		}
		newCell.doFormat();
		newCell.doDataWrapCompute(this);
	}
	public void addChartData(ChartData data){
		chartDataMap.put(data.getId(), data);
	}
	public Map<String, ChartData> getChartDataMap() {
		return chartDataMap;
	}
	
	public Row getRow(int rowNumber){
		return report.getRow(rowNumber);
	}
	
	
	public Column getColumn(int columnNumber){
		return report.getColumn(columnNumber);
	}
	
	public Report getReport() {
		return report;
	}

	public List<?> getDatasetData(String name){
		if(datasetMap.containsKey(name)){
			return datasetMap.get(name).getData();
		}
		throw new DatasetUndefinitionException(name);
	}
	
	public List<Cell> nextUnprocessedCells(){
		if(unprocessedCellsMap.size()==0){
			return null;
		}
		List<Cell> targetCellsList=null;
		String targetCellName=null;
		Set<String> keySet=unprocessedCellsMap.keySet();
		for(String cellName:keySet){
			List<Cell> cells=unprocessedCellsMap.get(cellName);
			Cell cell=cells.get(0);
			Value value=cell.getValue();
			Cell leftParent=cell.getLeftParentCell();
			Cell topParent=cell.getTopParentCell();
			if((leftParent==null || leftParent.isProcessed()) && (topParent==null || topParent.isProcessed())){
				targetCellsList=cells;
				targetCellName=cellName;
				break;
			}
			if(value instanceof SimpleValue){
				targetCellsList=cells;
				targetCellName=cellName;
				break;
			}
		}
		if(targetCellName==null){
			throw new CellDependencyException();
		}else{			
			unprocessedCellsMap.remove(targetCellName);
		}
		return targetCellsList;
	}
	
	public boolean isCellPocessed(String cellName){
		return !unprocessedCellsMap.containsKey(cellName);
	}
	
	public void addRow(Row row){
		this.report.getRows().add(row);
	}
	public void addColumn(Column column){
		this.report.getColumns().add(column);
	}
	
	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public void setCurrentPageRows(List<Row> currentPageRows) {
		this.currentPageRows = currentPageRows;
	}
	public List<Row> getCurrentPageRows() {
		return currentPageRows;
	}
	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	
	public Cell getRootCell() {
		return rootCell;
	}
}
