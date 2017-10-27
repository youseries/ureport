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

import java.util.List;

import com.bstek.ureport.Utils;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.expression.model.data.ExpressionData;
import com.bstek.ureport.expression.model.data.NoneExpressionData;
import com.bstek.ureport.expression.model.data.ObjectExpressionData;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2017年7月11日
 */
public class CurrentCellDataExpression extends BaseExpression {
	private static final long serialVersionUID = 7517926036810650110L;
	private String property;
	@Override
	protected ExpressionData<?> compute(Cell cell, Cell currentCell,Context context) {
		List<Object> bindDataList=cell.getBindData();
		if(bindDataList==null || bindDataList.size()==0){
			return new NoneExpressionData();
		}
		Object obj=bindDataList.get(0);
		Object data=Utils.getProperty(obj, property);
		return new ObjectExpressionData(data);
	}
	public void setProperty(String property) {
		this.property = property;
	}
}
