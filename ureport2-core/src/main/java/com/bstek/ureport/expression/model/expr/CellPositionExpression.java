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
package com.bstek.ureport.expression.model.expr;

import java.util.List;
import java.util.Map;

import com.bstek.ureport.build.Context;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.expression.model.data.ExpressionData;
import com.bstek.ureport.expression.model.data.ObjectExpressionData;
import com.bstek.ureport.expression.model.expr.set.CellExpression;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2017年4月5日
 */
public class CellPositionExpression extends CellExpression {
	private static final long serialVersionUID = 6881039873078990276L;

	public CellPositionExpression(String cellName) {
		super(cellName);
	}
	@Override
	public boolean supportPaging(){
		return false;
	}
	@Override
	protected ExpressionData<?> compute(Cell cell,Cell currentCell, Context context) {
		List<Cell> targetCells=fetchCellsByLeftParent(context,cell,cellName);
		if(targetCells==null || targetCells.size()==0){
			targetCells=fetchCellsByTopParent(context,cell,cellName);
		}
		if(targetCells==null || targetCells.size()==0){
			targetCells=context.getReport().getCellsMap().get(cellName);
		}
		if(targetCells==null){
			throw new ReportComputeException("Cell ["+cellName+"] not exist.");
		}
		int index=-1;
		int rowNumber=cell.getRow().getRowNumber(),colNumber=cell.getColumn().getColumnNumber();
		for(int i=0;i<targetCells.size();i++){
			Cell target=targetCells.get(i);
			if(target.getRow()==cell.getRow()){
				index=i;
				break;
			}
			int rowSpan=target.getRowSpan();
			if(rowSpan>0){
				int targetRowStart=target.getRow().getRowNumber();
				int targetRowEnd=targetRowStart+rowSpan-1;
				if(targetRowStart<=rowNumber && targetRowEnd>=rowNumber){
					index=i;
					break;
				}
			}
		}
		if(index>-1){
			index++;
			return new ObjectExpressionData(index);			
		}
		for(int i=0;i<targetCells.size();i++){
			Cell target=targetCells.get(i);
			if(target.getColumn()==cell.getColumn()){
				index=i;
				break;
			}
			int colSpan=target.getColSpan();
			if(colSpan>0){
				int targetColStart=target.getColumn().getColumnNumber();
				int targetColEnd=targetColStart+colSpan-1;
				if(targetColStart<=colNumber && targetColEnd>=colNumber){
					index=i;
					break;
				}
			}
		}
		index++;
		return new ObjectExpressionData(index);
	}
	
	private List<Cell> fetchCellsByLeftParent(Context context,Cell cell,String cellName){
		Cell leftParentCell=cell.getLeftParentCell();
		if(leftParentCell==null){
			return null;
		}
		Map<String,List<Cell>> childrenCellsMap=leftParentCell.getRowChildrenCellsMap();
		List<Cell> targetCells=childrenCellsMap.get(cellName);
		if(targetCells!=null){
			return targetCells;
		}
		return fetchCellsByLeftParent(context,leftParentCell,cellName);
	}
	
	private List<Cell> fetchCellsByTopParent(Context context,Cell cell,String cellName){
		Cell topParentCell=cell.getTopParentCell();
		if(topParentCell==null){
			return null;
		}
		Map<String,List<Cell>> childrenCellsMap=topParentCell.getColumnChildrenCellsMap();
		List<Cell> targetCells=childrenCellsMap.get(cellName);
		if(targetCells!=null){
			return targetCells;
		}
		return fetchCellsByTopParent(context,topParentCell,cellName);
	}
}
