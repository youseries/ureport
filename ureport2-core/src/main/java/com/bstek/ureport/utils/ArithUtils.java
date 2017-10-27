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
		return a.add(b).doubleValue();
	}
	/**
	 * @param first 第一个参数
	 * @param second 第二个参数
	 * @return 减法
	 */
	public static Object sub(Object first,Object second) {
		BigDecimal a=Utils.toBigDecimal(first);
		BigDecimal b=Utils.toBigDecimal(second);			
		return a.subtract(b).doubleValue();
	}
	/**
	 * @param first 第一个参数
	 * @param second 第二个参数
	 * @return 乘法
	 */
	public static Object mul(Object first,Object second) {
		BigDecimal a=Utils.toBigDecimal(first);
		BigDecimal b=Utils.toBigDecimal(second);
		return a.multiply(b).doubleValue();
	}
	/**
	 * @param first 第一个参数
	 * @param second 第二个参数
	 * @return 除法
	 */
	public static Object div(Object first,Object second) {
		BigDecimal a=Utils.toBigDecimal(first);
		BigDecimal b=Utils.toBigDecimal(second);
		return a.divide(b,8,BigDecimal.ROUND_HALF_UP).doubleValue();
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
