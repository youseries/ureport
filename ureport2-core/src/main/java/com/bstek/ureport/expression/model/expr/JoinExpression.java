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

import com.bstek.ureport.build.BindData;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.expression.model.Operator;
import com.bstek.ureport.expression.model.data.BindDataListExpressionData;
import com.bstek.ureport.expression.model.data.ExpressionData;
import com.bstek.ureport.expression.model.data.ObjectExpressionData;
import com.bstek.ureport.expression.model.data.ObjectListExpressionData;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2017年1月15日
 */
public class JoinExpression extends BaseExpression {
	private static final long serialVersionUID = -9045259827109781135L;
	private List<Operator> operators;
	private List<BaseExpression> expressions;
	
	public JoinExpression(List<Operator> operators, List<BaseExpression> expressions) {
		this.operators = operators;
		this.expressions = expressions;
	}
	@Override
	protected ExpressionData<?> compute(Cell cell,Cell currentCell,Context context) {
		if(expressions.size()==1){
			return expressions.get(0).compute(cell, currentCell,context);
		}
		List<Object> list=new ArrayList<Object>();
		for(int i=0;i<expressions.size();i++){
			BaseExpression expression=expressions.get(i);
			ExpressionData<?> data=expression.execute(cell, currentCell,context);
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
			if(obj==null){
				obj="";
			}
			list.add(obj);
		}
		String str=null;
		for(int i=0;i<list.size();i++){
			Object data=list.get(i);
			if(str==null){
				if(data instanceof String){
					str="\""+data+"\"";
					//str=""+data+"";
				}else{
					str=""+data+"";					
				}
			}else{
				Operator op=operators.get(i-1);
				if(data instanceof String){
					str+=""+op+"\""+data+"\"";
					//str+=""+op+""+data+"";
				}else{
					str+=""+op+""+data+"";					
				}
			}
		}
		Object obj=context.evalExpr(str);
		return new ObjectExpressionData(obj);
	}
	public List<BaseExpression> getExpressions() {
		return expressions;
	}
}
