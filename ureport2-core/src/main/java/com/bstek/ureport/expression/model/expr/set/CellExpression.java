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
import java.util.Map;

import com.bstek.ureport.Utils;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.expression.model.data.ExpressionData;
import com.bstek.ureport.expression.model.data.NoneExpressionData;
import com.bstek.ureport.expression.model.data.ObjectExpressionData;
import com.bstek.ureport.expression.model.data.ObjectListExpressionData;
import com.bstek.ureport.expression.model.expr.BaseExpression;
import com.bstek.ureport.model.Cell;
import com.bstek.ureport.model.Column;
import com.bstek.ureport.model.Row;

/**
 * @author Jacky.gao
 * @since 2017年1月1日
 */
public class CellExpression extends BaseExpression {
	private static final long serialVersionUID = 8376298136905903019L;
	protected String cellName;
	public CellExpression(String cellName) {
		this.cellName=cellName;
	}
	public boolean supportPaging(){
		return true;
	}
	@Override
	protected ExpressionData<?> compute(Cell cell,Cell currentCell,Context context) {
		List<Cell> targetCells=Utils.fetchTargetCells(cell, context, cellName);
		if(targetCells.size()>1){
			List<Object> list=new ArrayList<Object>();
			for(Cell targetCell:targetCells){
				list.add(targetCell.getData()); 
			}
			return new ObjectListExpressionData(list);			
		}else if(targetCells.size()==1){
			return new ObjectExpressionData(targetCells.get(0).getData());
		}else{
			return new NoneExpressionData();
		}
	}
	
	public ExpressionData<?> computePageCells(Cell cell,Cell currentCell,Context context) {
		List<Row> pageRows=context.getCurrentPageRows();
		List<Object> list=new ArrayList<Object>();
		Map<Row, Map<Column, Cell>> cellMap=context.getReport().getRowColCellMap();
		List<Column> columns=context.getReport().getColumns();
		for(Row row:pageRows){
			Map<Column, Cell> map=cellMap.get(row);
			if(map==null){
				continue;
			}
			for(Column col:columns){
				Cell targetCell=map.get(col);
				if(targetCell==null || !targetCell.getName().equals(cellName)){
					continue;
				}
				list.add(targetCell.getData());
			}
		}
		return new ObjectListExpressionData(list);
	}
	
	protected List<Cell> fetchPageCells(Cell cell,Cell currentCell,Context context){
		List<Row> pageRows=context.getCurrentPageRows();
		Map<Row, Map<Column, Cell>> cellMap=context.getReport().getRowColCellMap();
		List<Column> columns=context.getReport().getColumns();
		List<Cell> list=new ArrayList<Cell>();
		for(Row row:pageRows){
			Map<Column, Cell> map=cellMap.get(row);
			if(map==null){
				continue;
			}
			for(Column col:columns){
				Cell targetCell=map.get(col);
				if(targetCell==null || !targetCell.getName().equals(cellName)){
					continue;
				}
				list.add(targetCell);
			}
		}
		return list;
	}
}
