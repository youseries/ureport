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

import java.util.List;
import java.util.Map;

import com.bstek.ureport.build.Context;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.expression.model.data.ExpressionData;
import com.bstek.ureport.expression.model.data.ObjectExpressionData;
import com.bstek.ureport.expression.model.data.ObjectListExpressionData;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2017年5月21日
 */
public class ParameterFunction  implements Function{
	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context,Cell currentCell) {
		if(dataList==null || dataList.size()<1){
			throw new ReportComputeException("Function [param] need one parameter.");
		}
		Object obj=null;
		ExpressionData<?> data=dataList.get(0);
		if(data instanceof ObjectExpressionData){
			ObjectExpressionData objData=(ObjectExpressionData)data;
			obj=objData.getData();
		}else if(data instanceof ObjectListExpressionData){
			ObjectListExpressionData listData=(ObjectListExpressionData)data;
			List<?> list=listData.getData();
			if(list.size()>0){
				obj=list.get(0);
			}
		}
		if(obj==null){
			throw new ReportComputeException("Function [param] need one parameter.");
		}
		Map<String,Object> map=context.getParameters();
		if(map==null){
			return null;
		}
		return map.get(obj.toString());
	}
	@Override
	public String name() {
		return "param";
	}
}
