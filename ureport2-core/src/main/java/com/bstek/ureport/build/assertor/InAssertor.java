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
package com.bstek.ureport.build.assertor;

import java.util.List;

/**
 * @author Jacky.gao
 * @since 2017年1月12日
 */
public class InAssertor implements Assertor {

	@Override
	public boolean eval(Object left, Object right) {
		if(left == null || right == null){
			return false;
		}
		if(right instanceof List){
			List<?> list=(List<?>)right;
			for(Object obj:list){
				if(left.equals(obj)){
					return true;
				}
			}
			return false;
		}else if(right instanceof Object[]){
			Object[] objs=(Object[])right;
			for(Object obj:objs){
				if(left.equals(obj)){
					return true;
				}
			}
			return false;
		}else if(right instanceof String){
			String[] array=right.toString().split(",");
			for(String str:array){
				if(left.equals(str)){
					return true;
				}
			}
			return false;
		}
		return left.equals(right);
	}
}
