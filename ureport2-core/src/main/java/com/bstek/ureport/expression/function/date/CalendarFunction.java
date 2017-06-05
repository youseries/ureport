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
package com.bstek.ureport.expression.function.date;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.bstek.ureport.Utils;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.expression.function.Function;
import com.bstek.ureport.expression.model.data.ExpressionData;
import com.bstek.ureport.expression.model.data.ObjectExpressionData;
import com.bstek.ureport.expression.model.data.ObjectListExpressionData;

/**
 * @author Jacky.gao
 * @since 2017年1月22日
 */
public abstract class CalendarFunction implements Function{
	protected Calendar buildCalendar(List<ExpressionData<?>> dataList) {
		Date date=new Date();
		if(dataList!=null && dataList.size()>0){
			ExpressionData<?> data=dataList.get(0);
			if(data instanceof ObjectListExpressionData){
				ObjectListExpressionData listData=(ObjectListExpressionData)data;
				List<?> list=listData.getData();
				if(list==null || list.size()!=1){
					throw new ReportComputeException("Function [day] first parameter need a data of Date.");
				}
				Object obj=list.get(0);
				if(obj==null){
					throw new ReportComputeException("Function [day] first parameter can not be null.");
				}
				date=Utils.toDate(obj);
			}else if(data instanceof ObjectExpressionData){
				ObjectExpressionData objData=(ObjectExpressionData)data;
				Object obj=objData.getData();
				if(obj==null){
					throw new ReportComputeException("Function [day] first parameter can not be null.");
				}
				date=Utils.toDate(obj);
			}else{
				throw new ReportComputeException("Function [day] first parameter need a data of Date.");
			}
		}
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		return c;
	}
}
