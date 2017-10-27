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
package com.bstek.ureport.expression.function;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.bstek.ureport.Utils;
import com.bstek.ureport.build.BindData;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.expression.model.data.BindDataListExpressionData;
import com.bstek.ureport.expression.model.data.ExpressionData;
import com.bstek.ureport.expression.model.data.ObjectExpressionData;
import com.bstek.ureport.expression.model.data.ObjectListExpressionData;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2017年9月5日
 */
public class GetFunction implements Function {

	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context,Cell currentCell) {
		int index=1;
		String propertyName=null;
		List<Object> list= new ArrayList<Object>();
		if(dataList.size()==1){
			buildList(dataList.get(0),list);
		}else if(dataList.size()==2){
			buildList(dataList.get(0),list);
			index = buildIndex(dataList.get(1));
		}else if(dataList.size()==3){
			buildList(dataList.get(0),list);
			index = buildIndex(dataList.get(1));
			ExpressionData<?> d=dataList.get(2);
			if(d instanceof ObjectExpressionData){
				ObjectExpressionData exprData=(ObjectExpressionData)d;
				Object obj=exprData.getData();
				if(obj!=null){
					propertyName=obj.toString();
				}
			}
		}
		Object obj=null;
		if(list.size()>0){
			if(index<=list.size()){
				obj = list.get(index-1);
			}else{
				obj = list.get(list.size()-1);
			}
		}
		if(StringUtils.isNotBlank(propertyName)){
			obj=Utils.getProperty(obj, propertyName);
		}
		return obj;
	}

	private int buildIndex(ExpressionData<?> d) {
		int index=1;
		if(d instanceof ObjectExpressionData){
			ObjectExpressionData exprData=(ObjectExpressionData)d;
			Object obj=exprData.getData();
			if(obj!=null){
				index=Utils.toBigDecimal(obj).intValue();
			}
		}
		return index;
	}

	private void buildList(ExpressionData<?> d, List<Object> list) {
		if(d instanceof ObjectExpressionData){
			ObjectExpressionData exprData=(ObjectExpressionData)d;
			list.add(exprData.getData());
		}else if(d instanceof ObjectListExpressionData){
			ObjectListExpressionData listData=(ObjectListExpressionData)d;
			list.addAll(listData.getData());
		}else if(d instanceof BindDataListExpressionData){
			BindDataListExpressionData listData=(BindDataListExpressionData)d;
			for(BindData bindData:listData.getData()){
				list.addAll(bindData.getDataList());
			}
		}
	}

	@Override
	public String name() {
		return "get";
	}
}
