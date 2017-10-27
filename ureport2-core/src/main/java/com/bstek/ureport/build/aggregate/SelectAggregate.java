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
package com.bstek.ureport.build.aggregate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bstek.ureport.Utils;
import com.bstek.ureport.build.BindData;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.definition.Order;
import com.bstek.ureport.expression.model.expr.dataset.DatasetExpression;
import com.bstek.ureport.model.Cell;
import com.bstek.ureport.utils.DataUtils;

/**
 * @author Jacky.gao
 * @since 2016年12月22日
 */
public class SelectAggregate extends Aggregate {
	@Override
	public List<BindData> aggregate(DatasetExpression expr,Cell cell, Context context) {
		List<?> objList=DataUtils.fetchData(cell, context, expr.getDatasetName());
		return doAggregate(expr, cell, context, objList);
	}

	protected List<BindData> doAggregate(DatasetExpression expr, Cell cell,Context context, List<?> objList) {
		List<BindData> list=new ArrayList<BindData>();
		Map<String,String> mappingMap=context.getMapping(expr);
		String property=expr.getProperty();
		for(Object o:objList){
			boolean conditionResult=doCondition(expr.getCondition(),cell,o,context);
			if(!conditionResult){
				continue;
			}
			List<Object> bindList=new ArrayList<Object>();
			bindList.add(o);
			Object data=Utils.getProperty(o, property);
			Object mappingData=mappingData(mappingMap,data);
			if(mappingData==null){
				list.add(new BindData(data,bindList));				
			}else{
				list.add(new BindData(data,mappingData,bindList));								
			}
		}
		if(list.size()==0){
			List<Object> rowList=new ArrayList<Object>();
			rowList.add(new HashMap<String,Object>());
			list.add(new BindData("",rowList));
		}
		if(list.size()>1){
			Order order=expr.getOrder();
			orderBindDataList(list, order);			
		}
		return list;
	}
}
