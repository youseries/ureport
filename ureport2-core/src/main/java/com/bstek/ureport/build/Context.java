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
import com.bstek.ureport.utils.ElCompute;

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
	private Map<String,Object> variableMap=new HashMap<String,Object>();
	private Map<Integer,List<Row>> currentPageRowsMap=new HashMap<Integer,List<Row>>();
	private Map<String,Dataset> datasetMap;
	private ApplicationContext applicationContext;
	private ReportBuilder reportBuilder;
	private Map<String,Object> parameters;
	private HideRowColumnBuilder hideRowColumnBuilder;
	private List<Cell> existPageFunctionCells=new ArrayList<Cell>();
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
	
	public Map<String, Dataset> getDatasetMap() {
		return datasetMap;
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
	
	public Object evalExpr(String expression){
		return new ElCompute().doCompute(expression);
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
	public void setCurrentPageRows(int pageIndex,List<Row> currentPageRows) {
		currentPageRowsMap.put(pageIndex, currentPageRows);
	}
	public List<Row> getCurrentPageRows(int pageIndex) {
		return currentPageRowsMap.get(pageIndex);
	}
	
	public void addExistPageFunctionCells(Cell cell) {
		existPageFunctionCells.add(cell);
	}
	
	public List<Cell> getExistPageFunctionCells() {
		return existPageFunctionCells;
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
	
	public void putVariable(String key,Object value){
		variableMap.put(key, value);
	}
	
	public void resetVariableMap(){
		variableMap.clear();
	}
	public Object getVariable(String key){
		return variableMap.get(key);
	}
}
