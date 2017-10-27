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
