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
package com.bstek.ureport.expression.function.math;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.bstek.ureport.Utils;
import com.bstek.ureport.build.BindData;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.expression.function.Function;
import com.bstek.ureport.expression.model.data.BindDataListExpressionData;
import com.bstek.ureport.expression.model.data.ExpressionData;
import com.bstek.ureport.expression.model.data.ObjectExpressionData;
import com.bstek.ureport.expression.model.data.ObjectListExpressionData;

/**
 * @author Jacky.gao
 * @since 2017年1月22日
 */
public abstract class MathFunction implements Function {
	protected List<BigDecimal> buildDataList(List<ExpressionData<?>> dataList){
		if(dataList==null || dataList.size()==0){
			throw new ReportComputeException("Function ["+name()+"] need a lot of data parameter.");
		}
		List<BigDecimal> list=new ArrayList<BigDecimal>();
		for(ExpressionData<?> data:dataList){
			if(data instanceof ObjectListExpressionData){
				ObjectListExpressionData objList=(ObjectListExpressionData)data;
				for(Object obj:objList.getData()){
					BigDecimal bigData=Utils.toBigDecimal(obj);
					if(bigData!=null){
						list.add(bigData);						
					}
				}
			}else if(data instanceof ObjectExpressionData){
				ObjectExpressionData objData=(ObjectExpressionData)data;
				BigDecimal bigData=Utils.toBigDecimal(objData.getData());
				if(bigData!=null){
					list.add(bigData);
				}
			}else if(data instanceof BindDataListExpressionData){
				BindDataListExpressionData bindDataListData=(BindDataListExpressionData)data;
				for(BindData bindData:bindDataListData.getData()){
					BigDecimal bigData=Utils.toBigDecimal(bindData.getValue());
					if(bigData!=null){
						list.add(bigData);
					}
				}
			}
		}
		return list;
	}
	
	protected BigDecimal buildBigDecimal(List<ExpressionData<?>> dataList) {
		if(dataList==null || dataList.size()==0){
			throw new ReportComputeException("Function ["+name()+"] need a data of number parameter.");
		}
		BigDecimal number=null;
		ExpressionData<?> data=dataList.get(0);
		if(data instanceof ObjectListExpressionData){
			ObjectListExpressionData listData=(ObjectListExpressionData)data;
			List<?> list=listData.getData();
			if(list==null || list.size()!=1){
				throw new ReportComputeException("Function ["+name()+"] need a data of number parameter.");
			}
			Object obj=list.get(0);
			if(obj==null){
				throw new ReportComputeException("Function ["+name()+"] parameter can not be null.");
			}
			number=Utils.toBigDecimal(obj);
		}else if(data instanceof ObjectExpressionData){
			ObjectExpressionData objData=(ObjectExpressionData)data;
			Object obj=objData.getData();
			if(obj==null){
				throw new ReportComputeException("Function ["+name()+"] parameter can not be null.");
			}
			number=Utils.toBigDecimal(obj);
		}else{
			throw new ReportComputeException("Function ["+name()+"] need a data of number parameter.");
		}
		return number;
	}
}
