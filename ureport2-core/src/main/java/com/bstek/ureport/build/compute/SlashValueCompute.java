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
package com.bstek.ureport.build.compute;

import java.util.ArrayList;
import java.util.List;

import com.bstek.ureport.build.BindData;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.definition.value.SlashValue;
import com.bstek.ureport.definition.value.ValueType;
import com.bstek.ureport.model.Cell;
import com.bstek.ureport.model.Image;

/**
 * @author Jacky.gao
 * @since 2017年3月14日
 */
public class SlashValueCompute implements ValueCompute {
	@Override
	public List<BindData> compute(Cell cell, Context context) {
		List<BindData> list=new ArrayList<BindData>();
		SlashValue v=(SlashValue)cell.getValue();
		Image img=new Image(v.getBase64Data(),"slash.png",0,0);
		BindData bindData=new BindData(img);
		list.add(bindData);
		/*String key=SlashBuilder.buildKey(context.getReport().getReportFullName(), cell.getName());
		Resource res=new Resource(key);
		BindData bindData=new BindData(res);
		list.add(bindData);*/
		return list;
	}


	@Override
	public ValueType type() {
		return ValueType.slash;
	}
}
