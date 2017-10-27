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
package com.bstek.ureport.expression.model.expr.ifelse;

import java.util.List;

import com.bstek.ureport.build.Context;
import com.bstek.ureport.expression.model.Expression;
import com.bstek.ureport.expression.model.data.ExpressionData;
import com.bstek.ureport.expression.model.data.ObjectExpressionData;
import com.bstek.ureport.expression.model.expr.BaseExpression;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2017年1月16日
 */
public class IfExpression extends BaseExpression {
	private static final long serialVersionUID = -514395376408127087L;
	private ExpressionConditionList conditionList;
	private Expression expression;
	private List<ElseIfExpression> elseIfExpressions;
	private ElseExpression elseExpression;
	@Override
	protected ExpressionData<?> compute(Cell cell,Cell currentCell, Context context) {
		if(conditionList!=null){
			boolean result=conditionList.eval(context, cell,currentCell);
			if(result){
				return expression.execute(cell, currentCell,context);
			}
		}
		if(elseIfExpressions!=null){				
			for(ElseIfExpression elseIfExpr:elseIfExpressions){
				if(elseIfExpr.conditionsEval(cell, currentCell,context)){
					return elseIfExpr.execute(cell,currentCell, context);
				}
			}
		}
		if(elseExpression!=null){
			return elseExpression.execute(cell,currentCell, context);
		}
		return new ObjectExpressionData(null);
	}
	public void setConditionList(ExpressionConditionList conditionList) {
		this.conditionList = conditionList;
	}
	public ExpressionConditionList getConditionList() {
		return conditionList;
	}
	public void setElseExpression(ElseExpression elseExpression) {
		this.elseExpression = elseExpression;
	}
	public void setElseIfExpressions(List<ElseIfExpression> elseIfExpressions) {
		this.elseIfExpressions = elseIfExpressions;
	}
	public void setExpression(Expression expression) {
		this.expression = expression;
	}
	public Expression getExpression() {
		return expression;
	}
	public ElseExpression getElseExpression() {
		return elseExpression;
	}
	public List<ElseIfExpression> getElseIfExpressions() {
		return elseIfExpressions;
	}
}
