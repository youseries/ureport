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
package com.bstek.ureport.build;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bstek.ureport.build.compute.ChartValueCompute;
import com.bstek.ureport.build.compute.DatasetValueCompute;
import com.bstek.ureport.build.compute.ExpressionValueCompute;
import com.bstek.ureport.build.compute.ImageValueCompute;
import com.bstek.ureport.build.compute.SimpleValueCompute;
import com.bstek.ureport.build.compute.SlashValueCompute;
import com.bstek.ureport.build.compute.ValueCompute;
import com.bstek.ureport.build.compute.ZxingValueCompute;
import com.bstek.ureport.definition.value.Value;
import com.bstek.ureport.exception.ReportException;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2016年12月21日
 */
public class DataCompute {
	private static Map<String,ValueCompute> valueComputesMap = new HashMap<String,ValueCompute>();

	static{
		SimpleValueCompute simpleValueCompute=new SimpleValueCompute();
		valueComputesMap.put(simpleValueCompute.type().name(), simpleValueCompute);
		DatasetValueCompute datasetValueCompute=new DatasetValueCompute();
		valueComputesMap.put(datasetValueCompute.type().name(), datasetValueCompute);
		ExpressionValueCompute expressionValueCompute=new ExpressionValueCompute();
		valueComputesMap.put(expressionValueCompute.type().name(), expressionValueCompute);
		ImageValueCompute imageValueCompute=new ImageValueCompute();
		valueComputesMap.put(imageValueCompute.type().name(), imageValueCompute);
		SlashValueCompute slashValueCompute=new SlashValueCompute();
		valueComputesMap.put(slashValueCompute.type().name(), slashValueCompute);
		ZxingValueCompute zxingValueCompute=new ZxingValueCompute();
		valueComputesMap.put(zxingValueCompute.type().name(), zxingValueCompute);
		ChartValueCompute chartValueCompute=new ChartValueCompute();
		valueComputesMap.put(chartValueCompute.type().name(), chartValueCompute);
		
	}

	public static List<BindData> buildCellData(Cell cell,Context context) {
		Value value = cell.getValue();
		ValueCompute valueCompute=valueComputesMap.get(value.getType().name());
		if(valueCompute!=null){
			List<BindData> list= valueCompute.compute(cell, context);
			return list;
		}
		throw new ReportException("Unsupport value: "+value);
	}
}
