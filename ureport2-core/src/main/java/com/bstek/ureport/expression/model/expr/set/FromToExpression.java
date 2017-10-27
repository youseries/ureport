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
package com.bstek.ureport.expression.model.expr.set;

import java.util.ArrayList;
import java.util.List;

import com.bstek.ureport.Utils;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.expression.model.data.ExpressionData;
import com.bstek.ureport.expression.model.data.ObjectExpressionData;
import com.bstek.ureport.expression.model.data.ObjectListExpressionData;
import com.bstek.ureport.expression.model.expr.BaseExpression;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2017年1月1日
 */
public class FromToExpression extends BaseExpression {
	private static final long serialVersionUID = -3250140935488901894L;
	private BaseExpression fromExpression;
	private BaseExpression toExpression;
	
	public FromToExpression(BaseExpression fromExpression,BaseExpression toExpression) {
		this.fromExpression = fromExpression;
		this.toExpression = toExpression;
	}

	@Override
	protected ExpressionData<?> compute(Cell cell,Cell currentCell, Context context) {
		Object fromData=fromExpression.execute(cell,currentCell, context);
		Object toData=toExpression.execute(cell,currentCell, context);
		int from=convertFloatData(fromData),to=convertFloatData(toData);
		List<Integer> list=new ArrayList<Integer>();
		for(int i=from;i<=to;i++){
			list.add(i);
		}
		return new ObjectListExpressionData(list);
	}
	
	private int convertFloatData(Object data){
		if(data instanceof ObjectExpressionData){
			Object obj=((ObjectExpressionData)data).getData();
			return Utils.toBigDecimal(obj).intValue();
		}else{
			throw new ReportComputeException("Can not convert ["+data+"] to integer.");
		}
	}
}
