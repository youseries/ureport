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
package com.bstek.ureport.build.cell.down;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
		Collections.sort(newRowList, new Comparator<Row>() {
			@Override
			public int compare(Row o1, Row o2) {
				return o1.getTempRowNumber()-o2.getTempRowNumber();
			}
		});
		report.insertRows(minRowNumber, newRowList);
	}
	public void reset(){
		rowMap.clear();
	}
}
