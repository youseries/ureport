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
package com.bstek.ureport.build.cell;

import java.util.List;

import com.bstek.ureport.build.BindData;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.definition.ConditionPropertyItem;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2016年11月1日
 */
public class NoneExpandBuilder implements CellBuilder {

	@Override
	public Cell buildCell(List<BindData> dataList, Cell cell, Context context) {
		if(dataList.size()==1){
			BindData bindData=dataList.get(0);
			cell.setData(bindData.getValue());
			cell.setFormatData(bindData.getLabel());
			cell.setBindData(bindData.getDataList());
		}else{
			Object obj=null;
			List<Object> bindData=null;
			for(BindData data:dataList){
				if(obj==null){
					if(data.getLabel()==null){
						obj=data.getValue();					
					}else{
						obj=data.getLabel();										
					}
				}else{
					if(data.getLabel()==null){
						obj=obj+","+data.getValue();					
					}else{
						obj=obj+","+data.getLabel();					
					}
				}
				bindData=data.getDataList();
			}
			cell.setData(obj);
			cell.setBindData(bindData);
		}
		List<ConditionPropertyItem> conditionPropertyItems=cell.getConditionPropertyItems();
		if(conditionPropertyItems!=null && conditionPropertyItems.size()>0){
			context.getReport().getLazyComputeCells().add(cell);
		}else{
			cell.doFormat();
			cell.doDataWrapCompute(context);
		}
		return cell;
	}
}
