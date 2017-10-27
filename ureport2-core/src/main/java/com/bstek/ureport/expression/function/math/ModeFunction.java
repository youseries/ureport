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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bstek.ureport.build.Context;
import com.bstek.ureport.expression.model.data.ExpressionData;
import com.bstek.ureport.model.Cell;

/**
 * 求众数
 * @author Jacky.gao
 * @since 2017年1月23日
 */
public class ModeFunction extends MathFunction {

	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context,Cell currentCell) {
		int max=0;
		BigDecimal result=null;
		List<BigDecimal> list=buildDataList(dataList);
		Map<Double,Integer> map=new HashMap<Double,Integer>();
		for(BigDecimal bigData:list){
			if(bigData==null)continue;
			double d=bigData.doubleValue();
			if(map.containsKey(d)){
				int count=map.get(d);
				count++;
				map.put(d, count);
				if(count>max){
					max=count;
					result=bigData;
				}
			}else{
				map.put(d, 1);
				if(result==null){
					max=1;
					result=bigData;					
				}
			}
		}
		return result;
	}
	
	@Override
	public String name() {
		return "mode";
	}
}
