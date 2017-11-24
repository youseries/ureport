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
package com.bstek.ureport.expression.model.expr.ifelse;

import java.util.List;

import com.bstek.ureport.build.BindData;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.expression.ExpressionUtils;
import com.bstek.ureport.expression.model.Expression;
import com.bstek.ureport.expression.model.Op;
import com.bstek.ureport.expression.model.data.BindDataListExpressionData;
import com.bstek.ureport.expression.model.data.ExpressionData;
import com.bstek.ureport.expression.model.data.NoneExpressionData;
import com.bstek.ureport.expression.model.data.ObjectExpressionData;
import com.bstek.ureport.expression.model.data.ObjectListExpressionData;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2017年1月16日
 */
public class ExpressionCondition {
	private Expression left;
	private Op op;
	private Expression right;
	public ExpressionCondition(Expression left, Op op, Expression right) {
		this.left = left;
		this.op = op;
		this.right = right;
	}
	public boolean eval(Context context,Cell cell,Cell currentCell){
		ExpressionData<?> leftData=left.execute(cell,currentCell, context);
		ExpressionData<?> rightData=right.execute(cell,currentCell,context);
		Object leftObj=getData(leftData);
		Object rightObj=getData(rightData);
		return ExpressionUtils.conditionEval(op, leftObj, rightObj);
	}
	
	private Object getData(ExpressionData<?> data){
		if(data instanceof ObjectExpressionData){
			ObjectExpressionData objData=(ObjectExpressionData)data;
			return objData.getData();
		}else if(data instanceof ObjectListExpressionData){
			ObjectListExpressionData exprData=(ObjectListExpressionData)data;
			List<?> list=exprData.getData();
			StringBuffer sb=new StringBuffer();
			for(Object obj:list){
				if(sb.length()>0){
					sb.append(",");
				}
				sb.append(obj);
			}
			return sb.toString();
		}else if(data instanceof NoneExpressionData){
			return null;
		}else if(data instanceof BindDataListExpressionData){
			BindDataListExpressionData bindDataList=(BindDataListExpressionData)data;
			List<BindData> list=bindDataList.getData();
			if(list.size()==1){
				return list.get(0).getValue();
			}else{
				StringBuffer sb=new StringBuffer();
				for(BindData bindData:list){
					if(sb.length()>0){
						sb.append(",");
					}
					sb.append(bindData.getValue());
				}
				return sb.toString();
			}
		}else{
			throw new ReportComputeException("Unknow data : "+data);
		}
	}
	public Expression getLeft() {
		return left;
	}
	public Expression getRight() {
		return right;
	}
}
