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
package com.bstek.ureport.build.cell.down;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bstek.ureport.build.Context;
import com.bstek.ureport.model.Cell;
import com.bstek.ureport.model.Report;
import com.bstek.ureport.model.Row;

/**
 * @author Jacky.gao
 * @since 2016年11月10日
 */
public class DownDuplicate {
	private int index;
	private Cell mainCell;
	private int rowSize;
	private Context context;
	private int minRowNumber=-1;
	private Map<Row,Row> rowMap=new HashMap<Row,Row>();
	private List<Row> newRowList=new ArrayList<Row>();
	public DownDuplicate(Cell mainCell,int rowSize,Context context) {
		this.mainCell=mainCell;
		this.rowSize=rowSize;
		this.context=context;
	}
	
	public Row newRow(Row row,int currentRowNumber){
		if(rowMap.containsKey(row)){
			return rowMap.get(row);
		}else{
			int rowNumber=currentRowNumber;
			Row newRow=row.newRow();
			rowNumber=rowNumber+rowSize*(index-1)+rowSize;
			if(minRowNumber==-1 || minRowNumber>rowNumber){
				minRowNumber=rowNumber;
			}
			newRow.setTempRowNumber(rowNumber); 
			newRowList.add(newRow);
			rowMap.put(row, newRow);
			return newRow;
		}
	}
	
	public Cell getMainCell() {
		return mainCell;
	}
	
	public int getRowSize() {
		return rowSize;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public Context getContext() {
		return context;
	}
	public void complete(){
		if(minRowNumber<1){
			return;
		}
		Report report=context.getReport();
		/*		
		Collections.sort(newRowList, new Comparator<Row>() {
			@Override
			public int compare(Row o1, Row o2) {
				return o1.getTempRowNumber()-o2.getTempRowNumber();
			}
		});
		*/
		report.insertRows(minRowNumber, newRowList);
	}
	public void reset(){
		rowMap.clear();
	}
}
