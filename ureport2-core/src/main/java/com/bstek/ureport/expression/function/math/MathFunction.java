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
