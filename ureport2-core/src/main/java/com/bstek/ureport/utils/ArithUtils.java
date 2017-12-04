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
package com.bstek.ureport.utils;

import java.math.BigDecimal;

import com.bstek.ureport.Utils;

/**
 * @author Jacky.gao
 * @since 2017年4月25日
 */
public class ArithUtils {
	
	public static void main(String[] args) {
		BigDecimal a=new BigDecimal(-3960);
		BigDecimal b=new BigDecimal(-3080);
		System.out.println(a.subtract(b));
	}
	
	/**
	 * @param first 第一个参数
	 * @param second 第二个参数
	 * @return 加法
	 */
	public static Object add(Object first,Object second) {
		BigDecimal a=Utils.toBigDecimal(first);
		BigDecimal b=Utils.toBigDecimal(second);
		return a.add(b);
	}
	/**
	 * @param first 第一个参数
	 * @param second 第二个参数
	 * @return 减法
	 */
	public static Object sub(Object first,Object second) {
		BigDecimal a=Utils.toBigDecimal(first);
		BigDecimal b=Utils.toBigDecimal(second);			
		return a.subtract(b);
	}
	/**
	 * @param first 第一个参数
	 * @param second 第二个参数
	 * @return 乘法
	 */
	public static Object mul(Object first,Object second) {
		BigDecimal a=Utils.toBigDecimal(first);
		BigDecimal b=Utils.toBigDecimal(second);
		BigDecimal c=a.multiply(b);
		return c.doubleValue();
	}
	/**
	 * @param first 第一个参数
	 * @param second 第二个参数
	 * @return 除法
	 */
	public static Object div(Object first,Object second) {
		BigDecimal a=Utils.toBigDecimal(first);
		BigDecimal b=Utils.toBigDecimal(second);
		BigDecimal c=a.divide(b,8,BigDecimal.ROUND_HALF_UP);
		return c.doubleValue();
	}
	/**
	 * @param first 第一个参数
	 * @param second 第二个参数
	 * @return complementation 求余
	 */
	public static Object com(Object first,Object second) {
		BigDecimal a=Utils.toBigDecimal(first);
		BigDecimal b=Utils.toBigDecimal(second);
		return a.doubleValue() % b.doubleValue();
	}
}
