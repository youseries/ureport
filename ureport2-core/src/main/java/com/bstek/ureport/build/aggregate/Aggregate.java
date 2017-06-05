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
package com.bstek.ureport.build.aggregate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bstek.ureport.Utils;
import com.bstek.ureport.build.BindData;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.definition.Order;
import com.bstek.ureport.definition.value.DatasetValue;
import com.bstek.ureport.definition.value.ExpressionValue;
import com.bstek.ureport.definition.value.Value;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.expression.model.Condition;
import com.bstek.ureport.expression.model.Expression;
import com.bstek.ureport.expression.model.expr.BaseExpression;
import com.bstek.ureport.expression.model.expr.JoinExpression;
import com.bstek.ureport.expression.model.expr.ParenExpression;
import com.bstek.ureport.expression.model.expr.dataset.DatasetExpression;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2016年12月21日
 */
public abstract class Aggregate {
	public abstract List<BindData> aggregate(DatasetExpression expr,Cell cell,Context context);
	
	protected Condition getCondition(Cell cell){
		Value value=cell.getValue();
		Condition condition=null;
		if(value instanceof DatasetExpression){
			DatasetExpression dsValue=(DatasetExpression)value;
			condition=dsValue.getCondition();
		}
		return condition;
	}
	
	protected Object mappingData(Map<String,String> mappingMap,Object data){
		if(mappingMap==null || data==null){
			return data;
		}
		String label=mappingMap.get(data.toString());
		if(label==null){
			return data;
		}
		return label;
	}
	
	protected Cell fetchLeftCell(Cell cell, Context context,String datasetName){
		Cell targetCell=null;
		Cell leftCell=cell.getLeftParentCell();
		if(leftCell!=null){
			Value leftCellValue=leftCell.getValue();
			DatasetExpression leftDSValue=fetchDatasetExpression(leftCellValue);
			if(leftDSValue!=null){
				String leftDatasetName=leftDSValue.getDatasetName();
				if(leftDatasetName.equals(datasetName)){
					if(leftCell.getBindData()!=null){
						targetCell=leftCell;					
					}
				}
			}
		}
		return targetCell;
	}
	protected Cell fetchTopCell(Cell cell, Context context,String datasetName){
		Cell targetCell=null;
		Cell topCell=cell.getTopParentCell();
		if(topCell!=null){
			Value topCellValue=topCell.getValue();
			DatasetExpression leftDSValue=fetchDatasetExpression(topCellValue);
			if(leftDSValue!=null){				
				String leftDatasetName=leftDSValue.getDatasetName();
				if(leftDatasetName.equals(datasetName)){
					if(topCell.getBindData()!=null){
						targetCell=topCell;			
					}
				}
			}
		}
		return targetCell;
	}
	
	protected DatasetExpression fetchDatasetExpression(Value value){
		if(value instanceof ExpressionValue){
			ExpressionValue exprValue=(ExpressionValue)value;
			Expression expr=exprValue.getExpression();
			if(expr instanceof DatasetExpression){
				return (DatasetExpression)expr;
			}else if(expr instanceof ParenExpression){
				ParenExpression parenExpr=(ParenExpression)expr;
				DatasetExpression targetExpr=buildDatasetExpression(parenExpr);
				return targetExpr;
			}else{				
				return null;
			}
		}else if(value instanceof DatasetValue){
			return (DatasetValue)value;
		}
		return null;
	}
	
	private DatasetExpression buildDatasetExpression(JoinExpression joinExpr){
		List<BaseExpression> expressions=joinExpr.getExpressions();
		for(BaseExpression baseExpr:expressions){
			if(baseExpr instanceof DatasetExpression){
				return (DatasetExpression)baseExpr;
			}else if(baseExpr instanceof JoinExpression){
				JoinExpression childExpr=(JoinExpression)baseExpr;
				return buildDatasetExpression(childExpr);
			}
		}
		return null;
	}
	
	protected List<?> fetchData(Cell cell, Context context,String datasetName) {
		Cell leftCell=fetchLeftCell(cell, context, datasetName);
		Cell topCell=fetchTopCell(cell, context, datasetName);
		List<Object> leftList=null,topList=null;
		if(leftCell!=null){
			leftList=leftCell.getBindData();
		}
		if(topCell!=null){
			topList=topCell.getBindData();
		}
		if(leftList==null && topList==null){
			List<?> data=context.getDatasetData(datasetName);
			return data;
		}else if(leftList==null){
			return topList;
		}else if(topList==null){
			return leftList;
		}else{
			List<Object> list=null;
			Object data=null;
			String prop=null;
			if(leftList.size()>topList.size()){
				list=topList;
				data=leftCell.getData();
				Value value=leftCell.getValue();
				DatasetExpression de=fetchDatasetExpression(value);
				if(de==null){
					throw new ReportComputeException("Unsupport value : "+value);
				}
				prop=de.getProperty();
			}else{
				list=leftList;
				data=topCell.getData();
				Value value=leftCell.getValue();
				DatasetExpression de=fetchDatasetExpression(value);
				if(de==null){
					throw new ReportComputeException("Unsupport value : "+value);
				}
				prop=de.getProperty();
			}
			List<Object> result=new ArrayList<Object>();
			for(Object obj:list){
				Object o=Utils.getProperty(obj, prop);
				if(o==data){
					result.add(obj);
				}
			}
			return result;
		}
	}
	
	protected boolean doCondition(Condition condition,Cell cell,Object obj,Context context){
		if(condition==null){
			return true;
		}
		return condition.filter(cell,cell, obj, context);
	}
	
	protected void orderBindDataList(List<BindData> list, final Order order) {
		if(order==null || order.equals(Order.none)){
			return;
		}
		Collections.sort(list, new Comparator<BindData>() {
			@Override
			public int compare(BindData o1, BindData o2) {
				Object data1=o1.getValue();
				Object data2=o2.getValue();
				if(data1==null || data2==null){
					return 1;
				}
				if(data1 instanceof Date){
					Date d1=(Date)data1;
					Date d2=(Date)data2;
					if(order.equals(Order.asc)){
						return d1.compareTo(d2);
					}else{
						return d2.compareTo(d1);								
					}
				}else if(data1 instanceof Number){
					BigDecimal n1=Utils.toBigDecimal(data1);
					BigDecimal n2=Utils.toBigDecimal(data2);
					if(order.equals(Order.asc)){
						return n1.compareTo(n2);
					}else{
						return n2.compareTo(n1);
					}
				}else{
					String str1=data1.toString();
					String str2=data2.toString();
					if(order.equals(Order.asc)){
						return str1.compareTo(str2);
					}else{
						return str2.compareTo(str1);								
					}
				}
			}
		});
	}
}
