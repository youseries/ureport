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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bstek.ureport.model.Cell;
import com.bstek.ureport.model.Column;
import com.bstek.ureport.model.Report;
import com.bstek.ureport.model.Row;

/**
 * @author Jacky.gao
 * @since 2017年7月4日
 */
public class HideRowColumnBuilder {
	public void doHideProcessColumn(Report report, Column col) {
		int colWidth=col.getWidth();
		if(colWidth>0 || col.isHide()){
			return;
		}
		col.setHide(true);
		List<Column> columns=report.getColumns();
		int colNumber=col.getColumnNumber();
		Map<Row, Map<Column, Cell>> cellMap=report.getRowColCellMap();
		List<Row> rows=report.getRows();
		for(Row row:rows){
			if(row.getRealHeight()==0){
				continue;
			}
			Map<Column,Cell> rowMap=cellMap.get(row);
			if(rowMap==null){
				return;
			}
			Cell cell=rowMap.get(col);
			if(cell!=null){
				int colSpan=cell.getColSpan();
				if(colSpan>0){
					colSpan--;
					if(colSpan==1){
						colSpan=0;
					}
					cell.setColSpan(colSpan);
					Column nextCol=columns.get(colNumber);
					cell.setColumn(nextCol);
					rowMap.put(nextCol, cell);
				}
			}else{
				cell=fetchPrevColumnCell(report, colNumber-2, row);
				int colSpan=cell.getColSpan();
				if(colSpan>0){
					colSpan--;
					if(colSpan==1){
						colSpan=0;
					}
					cell.setColSpan(colSpan);
				}
			}
			rowMap.remove(col);
		}
	}
	
	public void doHideProcessRow(Report report, Row row) {
		int rowHeight=row.getRealHeight();
		if(rowHeight>0 || row.isHide()){
			return;
		}
		row.setHide(true);
		Map<Row, Map<Column, Cell>> cellMap=report.getRowColCellMap();
		Map<Column,Cell> map=cellMap.get(row);
		if(map==null){
			return;
		}
		List<Row> rows=report.getRows();
		List<Column> columns=report.getColumns();
		int rowNumber=row.getRowNumber();
		for(Column col:columns){
			if(col.getWidth()==0){
				continue;
			}
			Cell cell=map.get(col);
			if(cell!=null){
				int rowSpan=cell.getRowSpan();
				if(rowSpan>0){
					rowSpan--;
					if(rowSpan==1){
						rowSpan=0;
					}
					cell.setRowSpan(rowSpan);
					Row nextRow=rows.get(rowNumber);
					cell.setRow(nextRow);
					Map<Column,Cell> nextRowMap=cellMap.get(nextRow);
					if(nextRowMap==null){
						nextRowMap=new HashMap<Column,Cell>();
						cellMap.put(nextRow, nextRowMap);
					}
					nextRowMap.put(col, cell);
				}
			}else{
				Cell prevCell=fetchPrevRowCell(report, rowNumber-2, col);
				int rowSpan=prevCell.getRowSpan();
				if(rowSpan>0){
					rowSpan--;
					if(rowSpan==1){
						rowSpan=0;
					}
					prevCell.setRowSpan(rowSpan);
				}
			}
		}
		cellMap.remove(row);
	}
	
	private Cell fetchPrevColumnCell(Report report, int startColNumber, Row row) {
		Map<Row, Map<Column, Cell>> cellMap=report.getRowColCellMap();
		List<Column> columns=report.getColumns();
		Cell targetCell=null;
		Map<Column,Cell> colMap=cellMap.get(row);
		for(int i=startColNumber;i>-1;i--){
			Column prevCol=columns.get(i);
			if(colMap==null){
				continue;
			}
			targetCell=colMap.get(prevCol);
			if(targetCell!=null){
				break;
			}
		}
		return targetCell;
	}
	
	private Cell fetchPrevRowCell(Report report, int startRowNumber, Column col) {
		Map<Row, Map<Column, Cell>> cellMap=report.getRowColCellMap();
		List<Row> rows=report.getRows();
		Cell targetCell=null;
		for(int i=startRowNumber;i>-1;i--){
			Row prevRow=rows.get(i);
			Map<Column,Cell> colMap=cellMap.get(prevRow);
			if(colMap==null){
				continue;
			}
			targetCell=colMap.get(col);
			if(targetCell!=null){
				break;
			}
		}
		return targetCell;
	}
	
	/*
	public void processRowColumn(Report report, int i, Row row) {
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
	*/
}
