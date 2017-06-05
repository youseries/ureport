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
package com.bstek.ureport.expression.function.math;

import java.math.BigDecimal;
import java.util.List;

import com.bstek.ureport.Utils;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.expression.model.data.ExpressionData;
import com.bstek.ureport.expression.model.data.ObjectExpressionData;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2017年1月23日
 */
public class ChnFunction extends MathFunction {
	private static final char[] cnNumbers = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' };
	private static final String[] series = { "" , "拾", "佰", "仟", "万", "拾", "佰","仟", "亿" , "拾", "佰", "仟" , "万" , "拾", "佰" , "仟"};
	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context,Cell currentCell) {
		BigDecimal data = buildBigDecimal(dataList);
		int type=0;
		if(dataList.size()==2){
			ExpressionData<?> exprData=dataList.get(1);
			if(exprData instanceof ObjectExpressionData){
				ObjectExpressionData objData=(ObjectExpressionData)exprData;
				Object obj=objData.getData();
				if(obj==null){
					throw new ReportComputeException("Pow Function second parameter can not be null.");
				}
				type=Utils.toBigDecimal(obj).intValue();
			}
		}
		return buildChnString(data.toString(), type);
	}

	@Override
	public String name() {
		return "chn";
	}
	
	public String buildChnString(String original,int type) {
		String integerPart = "";
		String floatPart = "";
		if (original.contains(".")) {
			int dotIndex = original.indexOf(".");
			integerPart = original.substring(0, dotIndex);
			floatPart = original.substring(dotIndex + 1);
		} else {
			integerPart = original;
		}
		if(integerPart.length()>16){
			throw new ReportComputeException("Chn function max support 16 bit integer,current is "+integerPart+"");
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < integerPart.length(); i++) {
			int number = getNumber(integerPart.charAt(i));
			char num=cnNumbers[number];
			sb.append(num);
			if(type==0){
				String unit=series[integerPart.length() -1 - i];
				sb.append(unit);				
			}
		}
		if (floatPart.length() > 0) {
			sb.append("点");
			for (int i = 0; i < floatPart.length(); i++) {
				int number = getNumber(floatPart.charAt(i));
				sb.append(cnNumbers[number]);
			}
		}
		return sb.toString();
	}

	private int getNumber(char c) {
		String str = String.valueOf(c);
		return Integer.parseInt(str);
	}
}
