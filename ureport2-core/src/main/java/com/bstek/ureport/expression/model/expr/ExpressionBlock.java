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

import java.util.List;

import com.bstek.ureport.build.Context;
import com.bstek.ureport.expression.model.Expression;
import com.bstek.ureport.expression.model.data.ExpressionData;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2018年7月13日
 */
public class ExpressionBlock extends BaseExpression{
	private static final long serialVersionUID = -400528304334443664L;
	private List<Expression> expressionList;
	private Expression returnExpression;
	@Override
	protected ExpressionData<?> compute(Cell cell, Cell currentCell,Context context) {
		ExpressionData<?> data=null;
		if(expressionList!=null){
			for(Expression expr:expressionList){
				data=expr.execute(cell, currentCell, context);
			}
		}
		if(returnExpression!=null){
			data=returnExpression.execute(cell, currentCell, context);
		}
		return data;
	}
	public List<Expression> getExpressionList() {
		return expressionList;
	}
	public void setExpressionList(List<Expression> expressionList) {
		this.expressionList = expressionList;
	}
	public Expression getReturnExpression() {
		return returnExpression;
	}
	public void setReturnExpression(Expression returnExpression) {
		this.returnExpression = returnExpression;
	}
}
