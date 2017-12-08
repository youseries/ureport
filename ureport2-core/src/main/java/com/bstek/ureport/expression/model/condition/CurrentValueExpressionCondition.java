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
package com.bstek.ureport.expression.model.condition;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.bstek.ureport.build.Context;
import com.bstek.ureport.expression.model.Expression;
import com.bstek.ureport.expression.model.data.ExpressionData;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2017年12月8日
 */
public class CurrentValueExpressionCondition extends BaseCondition {
	private ConditionType type=ConditionType.current;
	@JsonIgnore
	private Expression rightExpression;
	@Override
	Object computeLeft(Cell cell,Cell currentCell,Object obj,Context context) {
		return obj;
	}

	@Override
	Object computeRight(Cell cell,Cell currentCell,Object obj,Context context) {
		ExpressionData<?> exprData=rightExpression.execute(cell, currentCell,context);
		return extractExpressionData(exprData);
	}
	
	@Override
	public ConditionType getType() {
		return type;
	}
	public void setRightExpression(Expression rightExpression) {
		this.rightExpression = rightExpression;
	}
	public Expression getRightExpression() {
		return rightExpression;
	}
}
