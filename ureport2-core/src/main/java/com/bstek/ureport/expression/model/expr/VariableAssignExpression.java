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

import com.bstek.ureport.build.BindData;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.expression.model.Expression;
import com.bstek.ureport.expression.model.data.BindDataListExpressionData;
import com.bstek.ureport.expression.model.data.ExpressionData;
import com.bstek.ureport.expression.model.data.ObjectExpressionData;
import com.bstek.ureport.expression.model.data.ObjectListExpressionData;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2018年7月13日
 */
public class VariableAssignExpression extends BaseExpression {
	private static final long serialVersionUID = 435511939569866187L;
	private String variable;
	private Expression expression;
	@Override
	protected ExpressionData<?> compute(Cell cell, Cell currentCell,Context context) {
		ExpressionData<?> data=expression.execute(cell, currentCell, context);
		Object obj=null;
		if(data instanceof ObjectExpressionData){
			ObjectExpressionData d=(ObjectExpressionData)data;
			obj=d.getData();
		}else if(data instanceof ObjectListExpressionData){
			ObjectListExpressionData d=(ObjectListExpressionData)data;
			obj=d.getData();
		}else if(data instanceof BindDataListExpressionData){
			BindDataListExpressionData dataList=(BindDataListExpressionData)data;
			List<BindData> bindList=dataList.getData();
			if(bindList.size()==1){
				BindData bindData=bindList.get(0);
				obj=bindData.getValue();
			}else{
				StringBuilder sb=new StringBuilder();
				for(BindData bd:bindList){
					if(sb.length()>0){
						sb.append(",");
					}
					sb.append(bd.getValue());
				}
				obj=sb.toString();
			}
		}
		if(obj!=null){
			context.putVariable(variable, obj);
		}
		return null;
	}
	public String getVariable() {
		return variable;
	}
	public void setVariable(String variable) {
		this.variable = variable;
	}
	public Expression getExpression() {
		return expression;
	}
	public void setExpression(Expression expression) {
		this.expression = expression;
	}
}
