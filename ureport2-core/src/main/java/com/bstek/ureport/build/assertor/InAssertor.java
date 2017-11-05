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
