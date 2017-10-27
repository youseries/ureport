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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.bstek.ureport.Utils;
import com.bstek.ureport.build.BindData;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.definition.value.Value;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.expression.model.Condition;
import com.bstek.ureport.expression.model.expr.dataset.DatasetExpression;
import com.bstek.ureport.model.Cell;
import com.bstek.ureport.utils.DataUtils;

/**
 * @author Jacky.gao
 * @since 2017年1月20日
 */
public class AvgAggregate extends Aggregate {
	@Override
	public List<BindData> aggregate(DatasetExpression expr, Cell cell,Context context) {
		String datasetName=expr.getDatasetName();
		String property=expr.getProperty();
		Cell leftCell=DataUtils.fetchLeftCell(cell, context, datasetName);
		Cell topCell=DataUtils.fetchTopCell(cell, context, datasetName);
		List<Object> leftList=null,topList=null;
		if(leftCell!=null){
			leftList=leftCell.getBindData();
		}
		if(topCell!=null){
			topList=topCell.getBindData();
		}
		BigDecimal result=null;
		if(leftList==null && topList==null){
			List<?> data=context.getDatasetData(datasetName);
			result=buildAvg(data,property,cell,expr,context);
		}else if(leftList==null){
			result=buildAvg(topList,property,cell,expr,context);
		}else if(topList==null){
			result=buildAvg(leftList,property,cell,expr,context);
		}else{
			List<Object> list=null;
			Object data=null;
			String prop=null;
			if(leftList.size()>topList.size()){
				list=topList;
				data=leftCell.getData();
				Value value=leftCell.getValue();
				DatasetExpression de=DataUtils.fetchDatasetExpression(value);
				if(de==null){
					throw new ReportComputeException("Unsupport value : "+value);
				}
				prop=de.getProperty();
			}else{
				list=leftList;
				data=topCell.getData();
				Value value=topCell.getValue();
				DatasetExpression de=DataUtils.fetchDatasetExpression(value);
				if(de==null){
					throw new ReportComputeException("Unsupport value : "+value);
				}
				prop=de.getProperty();
			}
			result=new BigDecimal(0);
			int count=0;
			Condition condition=getCondition(cell);
			if(condition==null){
				condition=expr.getCondition();
			}
			for(Object obj:list){
				if(condition!=null && !condition.filter(cell, cell, obj, context)){
					continue;
				}
				Object o=Utils.getProperty(obj, prop);
				if((o!=null && data!=null && (o==data || o.equals(data))) || (o==null && data==null)){
					Object value=Utils.getProperty(obj, property);
					if(value==null || value.toString().equals("")){
						continue;
					}
					result=result.add(Utils.toBigDecimal(value));
					count++;
				}
			}
			if(count>0){
				result=result.divide(new BigDecimal(count),8,BigDecimal.ROUND_HALF_UP);				
			}
		}
		List<BindData> list=new ArrayList<BindData>();
		list.add(new BindData(result.doubleValue(),null));
		return list;
	}
	
	private BigDecimal buildAvg(List<?> list,String property,Cell cell,DatasetExpression expr,Context context){
		Condition condition=getCondition(cell);
		if(condition==null){
			condition=expr.getCondition();
		}
		BigDecimal result=new BigDecimal(0);
		for(Object obj:list){
			if(condition!=null){
				boolean ok=condition.filter(cell, cell, obj, context);
				if(!ok){
					continue;
				}
			}
			Object value=Utils.getProperty(obj, property);
			if(value==null || value.toString().equals("")){
				continue;
			}
			result=result.add(Utils.toBigDecimal(value));
		}
		int size=list.size();
		result=result.divide(new BigDecimal(size),8,BigDecimal.ROUND_HALF_UP);
		return result;
	}
}
