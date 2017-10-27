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
package com.bstek.ureport.build.cell.right;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bstek.ureport.build.Context;
import com.bstek.ureport.model.Cell;
import com.bstek.ureport.model.Column;
import com.bstek.ureport.model.Report;

/**
 * @author Jacky.gao
 * @since 2016年11月10日
 */
public class RightDuplicate {
	private int index;
	private int columnSize;
	private Context context;
	private Cell mainCell;
	private int minColNumber=-1;
	private Map<Column,Column> colMap=new HashMap<Column,Column>();
	private List<Column> newColList=new ArrayList<Column>();
	public RightDuplicate(Cell mainCell,int columnSize,Context context) {
		this.mainCell=mainCell;
		this.columnSize=columnSize;
		this.context=context;
	}
	
	public Column newColumn(Column col,int colNumber){
		if(colMap.containsKey(col)){
			return colMap.get(col);
		}else{
			Column newCol=col.newColumn();
			colNumber=colNumber+columnSize*(index-1)+columnSize;
			if(minColNumber==-1 || minColNumber>colNumber){
				minColNumber=colNumber;
			}
			newCol.setTempColumnNumber(colNumber);
			newColList.add(newCol);
			colMap.put(col, newCol);
			return newCol;
		}
	}
	
	public int getColSize() {
		return columnSize;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public Context getContext() {
		return context;
	}
	
	public Cell getMainCell() {
		return mainCell;
	}
	
	public void complete(){
		if(minColNumber<1){
			return;
		}
		Report report=context.getReport();
		Collections.sort(newColList,new Comparator<Column>(){
			@Override
			public int compare(Column o1, Column o2) {
				return o1.getTempColumnNumber()-o2.getTempColumnNumber();
			}
		});
		report.insertColumns(minColNumber, newColList);
	}
	public void reset(){
		colMap.clear();
	}
}
