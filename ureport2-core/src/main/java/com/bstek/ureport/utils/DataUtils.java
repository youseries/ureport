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
package com.bstek.ureport.utils;

import java.util.ArrayList;
import java.util.List;

import com.bstek.ureport.Utils;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.definition.value.DatasetValue;
import com.bstek.ureport.definition.value.ExpressionValue;
import com.bstek.ureport.definition.value.Value;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.expression.model.Expression;
import com.bstek.ureport.expression.model.expr.BaseExpression;
import com.bstek.ureport.expression.model.expr.JoinExpression;
import com.bstek.ureport.expression.model.expr.ParenExpression;
import com.bstek.ureport.expression.model.expr.dataset.DatasetExpression;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2017年6月12日
 */
public class DataUtils {
	public static List<?> fetchData(Cell cell, Context context,String datasetName) {
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
				Value value=topCell.getValue();
				DatasetExpression de=fetchDatasetExpression(value);
				if(de==null){
					throw new ReportComputeException("Unsupport value : "+value);
				}
				prop=de.getProperty();
			}
			List<Object> result=new ArrayList<Object>();
			for(Object obj:list){
				Object o=Utils.getProperty(obj, prop);
				if((o==null && data==null)){
					result.add(obj);
				}else if(o!=null && o.equals(data)){
					result.add(obj);
				}else if(data!=null && data.equals(o)){
					result.add(obj);
				}
			}
			return result;
		}
	}
	public static Cell fetchLeftCell(Cell cell, Context context,String datasetName){
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
	public static Cell fetchTopCell(Cell cell, Context context,String datasetName){
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
	public static DatasetExpression fetchDatasetExpression(Value value){
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
	
	private static DatasetExpression buildDatasetExpression(JoinExpression joinExpr){
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
}
