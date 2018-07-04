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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.bstek.ureport.build.BindData;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.expression.ExpressionUtils;
import com.bstek.ureport.expression.model.Condition;
import com.bstek.ureport.expression.model.Op;
import com.bstek.ureport.expression.model.data.BindDataListExpressionData;
import com.bstek.ureport.expression.model.data.ExpressionData;
import com.bstek.ureport.expression.model.data.NoneExpressionData;
import com.bstek.ureport.expression.model.data.ObjectExpressionData;
import com.bstek.ureport.expression.model.data.ObjectListExpressionData;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2016年11月22日
 */
public abstract class BaseCondition implements Condition {
	protected Op op;
	private String operation; 
	private Join join;
	private Condition nextCondition;
	private String left;
	private String right;
	protected Logger log=Logger.getAnonymousLogger();
	@Override
	public final boolean filter(Cell cell,Cell currentCell,Object obj,Context context) {
		Object left=computeLeft(cell,currentCell,obj,context);
		Object right=computeRight(cell,currentCell,obj,context);
		boolean result=ExpressionUtils.conditionEval(op, left, right);		
		if(join!=null && nextCondition!=null){
			if(result){
				if(join.equals(Join.and)){
					return nextCondition.filter(cell,currentCell,obj,context);					
				}else{
					return result;
				}
			}else{
				if(join.equals(Join.and)){
					return result;
				}else{
					return nextCondition.filter(cell,currentCell,obj,context);
				}
			}
		}
		return result;
	}
	abstract Object computeLeft(Cell cell,Cell currentCell,Object obj,Context context);
	abstract Object computeRight(Cell cell,Cell currentCell,Object obj,Context context);
	public abstract ConditionType getType();
	
	protected Object extractExpressionData(ExpressionData<?> data){
		if(data instanceof ObjectExpressionData){
			return data.getData();
		}else if(data instanceof ObjectListExpressionData){
			ObjectListExpressionData listData=(ObjectListExpressionData)data;
			List<?> list=listData.getData();
			return list;
		}else if(data instanceof BindDataListExpressionData){
			BindDataListExpressionData bindData=(BindDataListExpressionData)data;
			List<BindData> bindDataList=bindData.getData();
			List<Object> list=new ArrayList<Object>();
			for(BindData bd:bindDataList){
				Object v=bd.getValue();
				list.add(v);
			}
			if(list.size()==1){
				return list.get(0);
			}else if(list.size()==0){
				return null;
			}
			return list;
		}else if(data instanceof NoneExpressionData){
			return null;
		}
		return null;
	}
	
	public void setOp(Op op) {
		this.op = op;
	}
	public Op getOp() {
		return op;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public void setNextCondition(Condition nextCondition) {
		this.nextCondition = nextCondition;
	}
	public void setJoin(Join join) {
		this.join = join;
	}
	public Join getJoin() {
		return join;
	}
	public String getLeft() {
		return left;
	}
	public void setLeft(String left) {
		this.left = left;
	}
	public String getRight() {
		return right;
	}
	public void setRight(String right) {
		this.right = right;
	}
}
