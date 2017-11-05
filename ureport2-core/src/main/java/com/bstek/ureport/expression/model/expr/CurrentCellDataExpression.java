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
package com.bstek.ureport.expression.model.expr;

import java.util.List;

import com.bstek.ureport.Utils;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.expression.model.data.ExpressionData;
import com.bstek.ureport.expression.model.data.NoneExpressionData;
import com.bstek.ureport.expression.model.data.ObjectExpressionData;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2017年7月11日
 */
public class CurrentCellDataExpression extends BaseExpression {
	private static final long serialVersionUID = 7517926036810650110L;
	private String property;
	@Override
	protected ExpressionData<?> compute(Cell cell, Cell currentCell,Context context) {
		List<Object> bindDataList=cell.getBindData();
		if(bindDataList==null || bindDataList.size()==0){
			return new NoneExpressionData();
		}
		Object obj=bindDataList.get(0);
		Object data=Utils.getProperty(obj, property);
		return new ObjectExpressionData(data);
	}
	public void setProperty(String property) {
		this.property = property;
	}
}
