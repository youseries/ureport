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
import java.text.DecimalFormat;
import java.util.List;

import com.bstek.ureport.Utils;
import com.bstek.ureport.build.BindData;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.expression.model.data.BindDataListExpressionData;
import com.bstek.ureport.expression.model.data.ExpressionData;
import com.bstek.ureport.expression.model.data.ObjectExpressionData;
import com.bstek.ureport.expression.model.data.ObjectListExpressionData;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2017年5月23日
 */
public class FormatNumberFunction implements Function {
	private final String defaultPattern="#";
	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context,Cell currentCell) {
		if(dataList==null){
			return "";
		}
		Object obj=null;
		String pattern=defaultPattern;
		if(dataList.size()==1){
			obj = buildExpressionData(dataList.get(0));
		}else if(dataList.size()>1){
			obj = buildExpressionData(dataList.get(0));
			Object patternData=buildExpressionData(dataList.get(1));
			if(patternData!=null){
				pattern=patternData.toString();				
			}
		}
		
		if(obj==null){
			throw new ReportComputeException("Function [formatnumber] need a number parameter at least");
		}else{
			BigDecimal bigData=Utils.toBigDecimal(obj);
			DecimalFormat df=new DecimalFormat(pattern);
			return df.format(bigData.doubleValue());
		}
	}

	private Object buildExpressionData(ExpressionData<?> data) {
		if(data instanceof ObjectListExpressionData){
			ObjectListExpressionData listExpressionData=(ObjectListExpressionData)data;
			List<?> list=listExpressionData.getData();
			if(list.size()>0){
				return list.get(0);
			}
		}else if(data instanceof ObjectExpressionData){
			return ((ObjectExpressionData)data).getData();
		}else if(data instanceof BindDataListExpressionData){
			BindDataListExpressionData bindDataList=(BindDataListExpressionData)data;
			List<BindData> list=bindDataList.getData();
			if(list.size()>0){
				return list.get(0).getValue();
			}
		}
		return null;
	}

	@Override
	public String name() {
		return "formatnumber";
	}
}
