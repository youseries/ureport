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

import com.bstek.ureport.build.BindData;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.definition.Order;
import com.bstek.ureport.definition.value.GroupItem;
import com.bstek.ureport.expression.model.Condition;
import com.bstek.ureport.expression.model.expr.dataset.DatasetExpression;
import com.bstek.ureport.model.Cell;
import com.bstek.ureport.utils.DataUtils;

/**
 * @author Jacky.gao
 * @since 2017年3月29日
 */
public class CustomGroupAggregate extends Aggregate {

	@Override
	public List<BindData> aggregate(DatasetExpression expr, Cell cell,Context context) {
		List<?> objList=DataUtils.fetchData(cell, context, expr.getDatasetName());
		List<BindData> list = doAggregate(expr, cell, context, objList);
		return list;
	}
	protected List<BindData> doAggregate(DatasetExpression expr, Cell cell,Context context, List<?> objList) {
		List<BindData> list=new ArrayList<BindData>();
		List<GroupItem> groupItems=expr.getGroupItems();
		if(objList.size()==0){
			list.add(new BindData(""));
			return list;
		}else if(objList.size()==1){
			Object o=objList.get(0);
			boolean conditionResult=doCondition(expr.getCondition(),cell,o,context);
			if(!conditionResult){
				list.add(new BindData(""));
				return list;
			}
			String itemName=groupData(groupItems, cell, context, o);
			if(itemName==null){
				list.add(new BindData(""));
				return list;
			}
			List<Object> rowList=new ArrayList<Object>();
			rowList.add(o);
			list.add(new BindData(itemName,rowList));
			return list;
		}
		Map<Object,List<Object>> map=new HashMap<Object,List<Object>>();
		for(Object o:objList){
			boolean conditionResult=doCondition(expr.getCondition(),cell,o,context);
			if(!conditionResult){
				continue;
			}
			String itemName=groupData(groupItems, cell, context, o);
			if(itemName==null){
				continue;
			}
			List<Object> rowList=null;
			if(map.containsKey(itemName)){
				rowList=map.get(itemName);
			}else{
				rowList=new ArrayList<Object>();
				map.put(itemName, rowList);
				list.add(new BindData(itemName,rowList));
			}
			rowList.add(o);				
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
	private String groupData(List<GroupItem> groupItems, Cell cell,Context context,Object o){
		for(GroupItem item:groupItems){
			Condition condition=item.getCondition();
			boolean doCondition=doCondition(condition, cell, o, context);
			if(doCondition){
				return item.getName();
			}
		}
		return null;
	}
}
