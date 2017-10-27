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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bstek.ureport.build.Context;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.expression.ExpressionUtils;
import com.bstek.ureport.expression.function.Function;
import com.bstek.ureport.expression.function.page.PageFunction;
import com.bstek.ureport.expression.model.Expression;
import com.bstek.ureport.expression.model.data.ExpressionData;
import com.bstek.ureport.expression.model.data.ObjectExpressionData;
import com.bstek.ureport.expression.model.data.ObjectListExpressionData;
import com.bstek.ureport.expression.model.expr.set.CellExpression;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2016年11月18日
 */
public class FunctionExpression extends BaseExpression {
	private static final long serialVersionUID = -6981657541024043558L;
	private String name;
	private List<BaseExpression> expressions;
	
	@Override
	public ExpressionData<?> compute(Cell cell,Cell currentCell,Context context) {
		Map<String, Function> functions=ExpressionUtils.getFunctions();
		Function targetFunction=functions.get(name);
		if(targetFunction==null){
			throw new ReportComputeException("Function ["+name+"] not exist.");
		}
		List<ExpressionData<?>> dataList=new ArrayList<ExpressionData<?>>();
		if(expressions!=null){
			for(BaseExpression expr:expressions){
				if(targetFunction instanceof PageFunction){
					ExpressionData<?> data=buildPageExpressionData(expr,cell,currentCell, context);
					dataList.add(data);
				}else{
					ExpressionData<?> data=expr.execute(cell,currentCell, context);
					dataList.add(data);					
				}
			}
		}
		Object obj=targetFunction.execute(dataList, context,currentCell);
		if(obj instanceof List){
			return new ObjectListExpressionData((List<?>)obj);
		}
		return new ObjectExpressionData(obj);
	}
	
	private ExpressionData<?> buildPageExpressionData(Expression expr,Cell cell,Cell currentCell,Context context){
		if(expr instanceof CellExpression){
			CellExpression cellExpr=(CellExpression)expr;
			if(cellExpr.supportPaging()){
				return cellExpr.computePageCells(cell, currentCell, context);
			}else{
				return cellExpr.execute(cell, currentCell, context);
			}
		}else{
			return expr.execute(cell, currentCell, context);
		}
	}
	

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setExpressions(List<BaseExpression> expressions) {
		this.expressions = expressions;
	}
	public List<BaseExpression> getExpressions() {
		return expressions;
	}
}
