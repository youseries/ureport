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
package com.bstek.ureport.expression.function.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.bstek.ureport.build.Context;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.expression.model.data.ExpressionData;
import com.bstek.ureport.expression.model.data.ObjectExpressionData;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2017年1月22日
 */
public class DateFunction extends CalendarFunction {
	private String pattern="yyyy-MM-dd HH:mm:ss";
	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context,Cell currentCell) {
		SimpleDateFormat sd=new SimpleDateFormat(pattern);
		Date date=new Date();
		if(dataList.size()==1){
			ExpressionData<?> data=dataList.get(0);
			sd=buildPattern(data);
		}
		if(dataList.size()==2){
			Calendar c = buildCalendar(dataList);
			date=c.getTime();
			ExpressionData<?> data=dataList.get(1);
			sd=buildPattern(data);
		}
		return sd.format(date);
	}

	private SimpleDateFormat buildPattern(ExpressionData<?> data) {
		SimpleDateFormat sd=null;
		if(data instanceof ObjectExpressionData){
			ObjectExpressionData objectData=(ObjectExpressionData)data;
			String newPattern=(String)objectData.getData();
			sd=new SimpleDateFormat(newPattern);
		}else{
			throw new ReportComputeException("Unknow date format pattern:"+data.getData());
		}
		return sd;
	}

	@Override
	public String name() {
		return "date";
	}
}
