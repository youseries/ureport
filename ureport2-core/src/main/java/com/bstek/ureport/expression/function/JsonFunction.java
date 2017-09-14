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
package com.bstek.ureport.expression.function;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.bstek.ureport.Utils;
import com.bstek.ureport.build.BindData;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.exception.ReportException;
import com.bstek.ureport.expression.model.data.BindDataListExpressionData;
import com.bstek.ureport.expression.model.data.ExpressionData;
import com.bstek.ureport.expression.model.data.ObjectExpressionData;
import com.bstek.ureport.expression.model.data.ObjectListExpressionData;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2017年1月3日
 */
public class JsonFunction implements Function {

	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context,Cell currentCell){
		if(dataList.size()!=2){
			return null;
		}
		String obj = buildData(dataList.get(0));
		String property=buildData(dataList.get(1));
		
		if(obj==null || property==null || obj.equals("") || property.equals("")){
			return null;
		}
		ObjectMapper mapper=new ObjectMapper();
		try{
			Map<?,?> map=mapper.readValue(obj, HashMap.class);
			return Utils.getProperty(map, property);
		}catch(Exception ex){
			throw new ReportException(ex);
		}
	}

	private String buildData(ExpressionData<?> exprData) {
		String obj=null;
		if(exprData instanceof ObjectExpressionData){
			ObjectExpressionData objData=(ObjectExpressionData)exprData;
			Object data=objData.getData();
			if(data!=null){
				obj=data.toString();
			}
		}else if(exprData instanceof ObjectListExpressionData){
			ObjectListExpressionData listData=(ObjectListExpressionData)exprData;
			List<?> list=listData.getData();
			if(list.size()==1){
				Object data=list.get(0);
				if(data!=null){
					obj=data.toString();
				}
			}
		}else if(exprData instanceof BindDataListExpressionData){
			BindDataListExpressionData listData=(BindDataListExpressionData)exprData;
			List<BindData> list=listData.getData();
			if(list.size()==1){
				Object data=list.get(0).getValue();
				if(data!=null){
					obj=data.toString();
				}
			}
		}
		return obj;
	}

	@Override
	public String name() {
		return "json";
	}
}
