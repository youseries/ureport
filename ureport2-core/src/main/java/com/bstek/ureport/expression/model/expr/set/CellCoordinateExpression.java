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
package com.bstek.ureport.expression.model.expr.set;

import java.util.ArrayList;
import java.util.List;

import com.bstek.ureport.Utils;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.expression.model.Condition;
import com.bstek.ureport.expression.model.data.ExpressionData;
import com.bstek.ureport.expression.model.data.ObjectExpressionData;
import com.bstek.ureport.expression.model.data.ObjectListExpressionData;
import com.bstek.ureport.model.Cell;
import com.bstek.ureport.model.Column;
import com.bstek.ureport.model.Row;


/**
 * @author Jacky.gao
 * @since 2017年1月1日
 */
public class CellCoordinateExpression extends CellExpression {
	private static final long serialVersionUID = 4132183845260722859L;
	private Condition condition;
	private CellCoordinateSet leftCoordinate;
	private CellCoordinateSet topCoordinate;
	public CellCoordinateExpression(String cellName,CellCoordinateSet leftCoordinate) {
		super(cellName);
		this.leftCoordinate=leftCoordinate;
	}
	public CellCoordinateExpression(String cellName,CellCoordinateSet leftCoordinate,Condition condition) {
		super(cellName);
		this.leftCoordinate=leftCoordinate;
		this.condition=condition;
	}
	public CellCoordinateExpression(String cellName,CellCoordinateSet leftCoordinate,CellCoordinateSet topCoordinate) {
		super(cellName);
		this.leftCoordinate=leftCoordinate;
		this.topCoordinate=topCoordinate;
	}
	public CellCoordinateExpression(String cellName,CellCoordinateSet leftCoordinate,CellCoordinateSet topCoordinate,Condition condition) {
		super(cellName);
		this.leftCoordinate=leftCoordinate;
		this.topCoordinate=topCoordinate;
		this.condition=condition;
	}
	@Override
	public boolean supportPaging(){
		return false;
	}
	@Override
	protected ExpressionData<?> compute(Cell cell,Cell currentCell, Context context) {
		while(!context.isCellPocessed(cellName)){
			context.getReportBuilder().buildCell(context, null);
		}
		List<Cell> leftCellList = buildLeftCells(cell, context);
		List<Cell> topCellList=buildTopCells(cell, context);
		List<Object> list=new ArrayList<Object>();
		if(leftCellList==null){
			if(topCellList!=null){
				topCellList=filterCells(cell,context, condition, topCellList);
				for(Cell c:topCellList){
					list.add(c.getData());
				}
			}else{
				List<Cell> cells=context.getReport().getCellsMap().get(cellName);
				if(cells==null){
					throw new ReportComputeException("Cell ["+cellName+"] not exist.");
				}
				topCellList=filterCells(cell,context, condition, cells);
				for(Cell c:cells){
					list.add(c.getData());
				}
			}
		}else{
			if(topCellList!=null){
				leftCellList=filterCells(cell,context, condition, leftCellList);
				topCellList=filterCells(cell,context, condition, topCellList);
				for(Cell c:topCellList){
					if(leftCellList.contains(c)){
						list.add(c.getData());
					}
				}
			}else{
				leftCellList=filterCells(cell,context, condition, leftCellList);
				for(Cell c:leftCellList){
					list.add(c.getData());
				}
			}
		}
		if(list.size()==1){
			return new ObjectExpressionData(list.get(0));
		}else{
			return new ObjectListExpressionData(list);
		}
	}
	private List<Cell> buildLeftCells(Cell cell, Context context) {
		if(leftCoordinate==null){
			return null;
		}
		List<Cell> cellList=null;
		Cell targetLeftCell=null;
		Row row=cell.getRow();
		int rowNumber=row.getRowNumber();
		List<CellCoordinate> leftCoordinates=leftCoordinate.getCellCoordinates();
		for(CellCoordinate coordinate:leftCoordinates){
			String name=coordinate.getCellName();
			while(!context.isCellPocessed(name)){
				context.getReportBuilder().buildCell(context, null);
			}
			if(targetLeftCell==null){
				if(coordinate.getCoordinateType().equals(CoordinateType.relative)){
					cellList=Utils.fetchTargetCells(cell, context, name);
				}else{	
					cellList=context.getReport().getCellsMap().get(name);
				}
			}else{
				cellList=targetLeftCell.getRowChildrenCellsMap().get(name);
			}
			int position=coordinate.getPosition();
			if(position==0){
				for(Cell childCell:cellList){
					Row childRow=childCell.getRow();
					if(row==childRow){
						targetLeftCell=childCell;
						break;
					}
					int rowSpan=childCell.getRowSpan();
					if(rowSpan>0){
						int childRowNumberStart=childRow.getRowNumber();
						int childRowNumberEnd=childRowNumberStart+rowSpan-1;
						if(childRowNumberStart<=rowNumber && childRowNumberEnd>=rowNumber){
							targetLeftCell=childCell;
							break;
						}
					}
				}
			}else{
				if(position>0){
					targetLeftCell=cellList.get(position-1);
				}else if(position<0){
					boolean reverse=coordinate.isReverse();
					int cellSize=cellList.size();
					if(reverse){
						int newPosition=cellSize-position;
						if(newPosition>=cellSize){
							newPosition=cellSize-1;
						}
						targetLeftCell=cellList.get(newPosition);
					}else{
						int index=0;
						for(int i=0;i<cellSize;i++){
							Cell childCell=cellList.get(i);
							if(childCell.getRow()==cell.getRow()){
								index=i;
								break;
							}
							int rowSpan=childCell.getRowSpan();
							if(rowSpan>1){
								int rowNum=childCell.getRow().getRowNumber();
								int start=rowNum,end=rowNum+rowSpan-1;
								if(rowNumber>=start && rowNumber<=end){
									index=i;
									break;
								}								
							}
						}
						int newPosition=index+position;
						if(newPosition<0){
							newPosition=0;
						}
						if(newPosition>=cellSize){
							newPosition=cellSize-1;
						}
						targetLeftCell=cellList.get(newPosition);				
					}
				}
			}
		}
		List<Cell> leftCellList=targetLeftCell.getRowChildrenCellsMap().get(cellName);
		return leftCellList;
	}
	private List<Cell> buildTopCells(Cell cell, Context context) {
		if(topCoordinate==null){
			return null;
		}
		List<Cell> cellList=null;
		Cell targetTopCell=null;
		Column col=cell.getColumn();
		int colNumber=col.getColumnNumber();
		List<CellCoordinate> topCoordinates=topCoordinate.getCellCoordinates();
		for(CellCoordinate coordinate:topCoordinates){
			String name=coordinate.getCellName();
			while(!context.isCellPocessed(name)){
				context.getReportBuilder().buildCell(context, null);
			}
			if(cellList==null){
				if(coordinate.getCoordinateType().equals(CoordinateType.relative)){
					cellList=Utils.fetchTargetCells(cell, context, name);
				}else{	
					cellList=context.getReport().getCellsMap().get(name);
				}			
			}else{
				cellList=targetTopCell.getColumnChildrenCellsMap().get(name);
			}
			int position=coordinate.getPosition();
			if(position==0){
				for(Cell childCell:cellList){
					Column childCol=childCell.getColumn();
					if(col==childCol){
						targetTopCell=childCell;
						break;
					}
					int colSpan=childCell.getColSpan();
					if(colSpan>0){
						int childColNumberStart=childCol.getColumnNumber();
						int childColNumberEnd=childColNumberStart+colSpan-1;
						if(childColNumberStart<=colNumber && childColNumberEnd>=colNumber){
							targetTopCell=childCell;
							break;
						}
					}
				}
			}else{
				if(position>0){
					targetTopCell=cellList.get(position-1);
				}else if(position<0){
					boolean reverse=coordinate.isReverse();
					int cellSize=cellList.size();
					if(reverse){
						int newPosition=cellSize-position;
						if(newPosition>=cellSize){
							newPosition=cellSize-1;
						}
						targetTopCell=cellList.get(newPosition);
					}else{
						int index=0;
						for(int i=0;i<cellSize;i++){
							Cell childCell=cellList.get(i);
							if(childCell.getColumn()==cell.getColumn()){
								index=i;
								break;
							}
							int colSpan=childCell.getColSpan();
							if(colSpan>1){
								int colNum=childCell.getColumn().getColumnNumber();
								int start=colNum,end=colNum+colSpan-1;
								if(colNumber>=start && colNumber<=end){
									index=i;
									break;
								}								
							}
						}
						int newPosition=index+position;
						if(newPosition<0){
							newPosition=0;
						}
						if(newPosition>=cellSize){
							newPosition=cellSize-1;
						}
						targetTopCell=cellList.get(newPosition);
					}
				}
			}
		}
		List<Cell> topCellList=targetTopCell.getColumnChildrenCellsMap().get(cellName);
		return topCellList;
	}
}
