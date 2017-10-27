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
		if(left==null || right==null){
			return false;
		}
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
				list.add(bd.getValue());
			}
			return list;
		}else if(data instanceof NoneExpressionData){
			return null;
		}
		return null;
	}
	
	public void setOp(Op op) {
		this.op = op;
		this.operation=op.toString();
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
