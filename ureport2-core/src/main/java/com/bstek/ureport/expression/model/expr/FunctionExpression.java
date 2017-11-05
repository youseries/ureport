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
