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
package com.bstek.ureport.expression.function.string;

import java.util.List;

import com.bstek.ureport.build.Context;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.expression.model.data.ExpressionData;
import com.bstek.ureport.expression.model.data.ObjectExpressionData;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2017年1月24日
 */
public class ReplaceFunction extends StringFunction {

	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context,Cell currentCell) {
		if(dataList.size()!=3){
			throw new ReportComputeException("Function ["+name()+"] need three parameters.");
		}
		String text=buildString(dataList);
		String targetText=null,replaceText=null;
		ExpressionData<?> exprData=dataList.get(1);
		if(exprData instanceof ObjectExpressionData){
			ObjectExpressionData objData=(ObjectExpressionData)exprData;
			Object obj=objData.getData();
			if(obj==null){
				throw new ReportComputeException("Function ["+name()+"] parameter can not be null.");
			}
			targetText=obj.toString();
		}
		exprData=dataList.get(2);
		replaceText=buildStart(exprData);
		return text.replaceAll(targetText, replaceText);
	}

	private String buildStart(ExpressionData<?> exprData) {
		if(exprData instanceof ObjectExpressionData){
			ObjectExpressionData objData=(ObjectExpressionData)exprData;
			Object obj=objData.getData();
			if(obj==null){
				throw new ReportComputeException("Function ["+name()+"] parameter can not be null.");
			}
			return obj.toString();
		}
		throw new ReportComputeException("Function ["+name()+"] start position data is invalid : "+exprData);
	}

	@Override
	public String name() {
		return "replace";
	}

}
