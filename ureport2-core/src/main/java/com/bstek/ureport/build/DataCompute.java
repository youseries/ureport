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
		context.resetVariableMap();
		Value value = cell.getValue();
		ValueCompute valueCompute=valueComputesMap.get(value.getType().name());
		if(valueCompute!=null){
			List<BindData> list= valueCompute.compute(cell, context);
			return list;
		}
		throw new ReportException("Unsupport value: "+value);
	}
}
