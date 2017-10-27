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
package com.bstek.ureport.expression.function;

import java.util.ArrayList;
import java.util.List;

import com.bstek.ureport.build.Context;
import com.bstek.ureport.expression.model.data.ExpressionData;
import com.bstek.ureport.expression.model.data.ObjectExpressionData;
import com.bstek.ureport.expression.model.data.ObjectListExpressionData;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2017年1月3日
 */
public class ListFunction implements Function {

	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context,Cell currentCell) {
		List<Object> list= new ArrayList<Object>();
		for(ExpressionData<?> d:dataList){
			if(d instanceof ObjectExpressionData){
				ObjectExpressionData exprData=(ObjectExpressionData)d;
				list.add(exprData.getData());
			}else if(d instanceof ObjectListExpressionData){
				ObjectListExpressionData listData=(ObjectListExpressionData)d;
				list.addAll(listData.getData());
			}
		}
		return list;
	}

	@Override
	public String name() {
		return "list";
	}
}
