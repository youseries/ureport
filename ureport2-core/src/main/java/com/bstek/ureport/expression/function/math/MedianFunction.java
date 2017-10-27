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
import java.util.Collections;
import java.util.List;

import com.bstek.ureport.build.Context;
import com.bstek.ureport.expression.model.data.ExpressionData;
import com.bstek.ureport.model.Cell;

/**
 * 求中位数
 * @author Jacky.gao
 * @since 2017年1月23日
 */
public class MedianFunction extends MathFunction {

	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context,Cell currentCell) {
		List<BigDecimal> list=buildDataList(dataList);
		int size=list.size();
		if(size==1){
			return list.get(0);
		}else if(size==2){
			BigDecimal data=list.get(0).add(list.get(1));
			return data.divide(new BigDecimal(2),8,BigDecimal.ROUND_HALF_UP);
		}
		Collections.sort(list);
		int mode=size % 2;
		if(mode==0){
			int half = size / 2;
			int start=half-1;
			BigDecimal data=list.get(start).add(list.get(half));
			return data.divide(new BigDecimal(2),8,BigDecimal.ROUND_HALF_UP);
		}else{
			int half=size / 2;
			return list.get(half);
		}
	}
	
	@Override
	public String name() {
		return "median";
	}
}
