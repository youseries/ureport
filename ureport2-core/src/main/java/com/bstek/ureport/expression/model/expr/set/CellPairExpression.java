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
package com.bstek.ureport.expression.model.expr.set;

import java.util.ArrayList;
import java.util.List;

import com.bstek.ureport.Utils;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.expression.ExpressionUtils;
import com.bstek.ureport.expression.model.data.ExpressionData;
import com.bstek.ureport.expression.model.data.ObjectExpressionData;
import com.bstek.ureport.expression.model.data.ObjectListExpressionData;
import com.bstek.ureport.expression.model.expr.BaseExpression;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2017年1月1日
 */
public class CellPairExpression extends BaseExpression {
	private static final long serialVersionUID = 775139518725235246L;
	private String startCellName;
	private String endCellName;
	public CellPairExpression(String startCellName,String endCellName) {
		this.startCellName=startCellName;
		this.endCellName=endCellName;
	}
	@Override
	protected ExpressionData<?> compute(Cell cell,Cell currentCell, Context context) {
		List<Cell> cellList = buildCells(cell, context);
		if(cellList.size()>1){
			List<Object> list=new ArrayList<Object>();
			for(Cell targetCell:cellList){
				list.add(targetCell.getData()); 
			}
			return new ObjectListExpressionData(list);			
		}else if(cellList.size()==1){
			return new ObjectExpressionData(cellList.get(0).getData());
		}else{
			return new ObjectExpressionData(null);
		}
	}
	private List<Cell> buildCells(Cell cell, Context context) {
		List<String> cellNameList=ExpressionUtils.getCellNameList();
		CellName startName=parseCellName(startCellName);
		int startPos=cellNameList.indexOf(startName.getName());
		int rowStart=startName.getNumber();
		
		CellName endName=parseCellName(endCellName);
		int endPos=cellNameList.indexOf(endName.getName());
		int rowEnd=endName.getNumber();
		
		if(startPos>endPos){
			int tmp=startPos;
			startPos=endPos;
			endPos=tmp;
		}
		if(rowStart>rowEnd){
			int tmp=rowStart;
			rowStart=rowEnd;
			rowEnd=tmp;
		}
		
		List<String> names=new ArrayList<String>();
		for(int i=startPos;i<=endPos;i++){
			names.add(cellNameList.get(i));
		}
		
		List<Cell> cellList=new ArrayList<Cell>();
		for(String name:names){
			for(int i=rowStart;i<=rowEnd;i++){
				String cellName=name+i;
				List<Cell> cells=Utils.fetchTargetCells(cell, context, cellName);
				cellList.addAll(cells);
			}			
		}
		return cellList;
	}
	private CellName parseCellName(String name){
		StringBuilder sb=new StringBuilder();
		StringBuilder sb1=new StringBuilder();
		for(int i=0;i<name.length();i++){
			char c=name.charAt(i);
			if(Character.isDigit(c)){
				sb1.append(c);
			}else{
				sb.append(c);
			}
		}
		String cellName=sb.toString();
		int number=Integer.valueOf(sb1.toString());
		return new CellName(cellName,number);
	}
}

class CellName{
	private String name;
	private int number;
	public CellName(String name, int number) {
		this.name = name;
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public int getNumber() {
		return number;
	}
}
