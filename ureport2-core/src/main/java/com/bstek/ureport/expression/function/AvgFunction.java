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

import java.math.BigDecimal;
import java.util.List;

import com.bstek.ureport.Utils;
import com.bstek.ureport.build.BindData;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.expression.model.data.BindDataListExpressionData;
import com.bstek.ureport.expression.model.data.ExpressionData;
import com.bstek.ureport.expression.model.data.ObjectExpressionData;
import com.bstek.ureport.expression.model.data.ObjectListExpressionData;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2017年1月20日
 */
public class AvgFunction implements Function {
	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context,Cell currentCell) {
		if(dataList==null || dataList.size()==0){
			return null;
		}
		int size=0;
		Object singleData=null;
		BigDecimal total=new BigDecimal(0);
		for(ExpressionData<?> exprData:dataList){
			if(exprData instanceof ObjectListExpressionData){
				ObjectListExpressionData listExpr=(ObjectListExpressionData)exprData;
				List<?> list=listExpr.getData();
				for(Object obj:list){
					if(obj==null){
						continue;
					}
					singleData=obj;
					BigDecimal bigData=Utils.toBigDecimal(obj);
					total=total.add(bigData);
					size++;
				}
			}else if(exprData instanceof ObjectExpressionData){
				ObjectExpressionData data=(ObjectExpressionData)exprData;
				BigDecimal bigData=Utils.toBigDecimal(data.getData());
				singleData=data.getData();
				total=total.add(bigData);
				size++;
			}else if(exprData instanceof BindDataListExpressionData){
				BindDataListExpressionData data=(BindDataListExpressionData)exprData;
				List<BindData> bindDataList=data.getData();
				for(BindData bindData:bindDataList){
					Object obj=bindData.getValue();
					if(obj==null){
						continue;
					}
					singleData=obj;
					BigDecimal bigData=Utils.toBigDecimal(obj);
					total=total.add(bigData);
					size++;
				}
			}
		}
		if(size==0){
			return 0;
		}else if(size==1){
			if(singleData==null || singleData.equals("")){
				return "";
			}
			return total;
		}else{
			return total.divide(new BigDecimal(size), 8, BigDecimal.ROUND_HALF_UP);			
		}
	}
	@Override
	public String name() {
		return "avg";
	}
}
