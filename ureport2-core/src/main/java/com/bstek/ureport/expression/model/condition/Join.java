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
package com.bstek.ureport.expression.model.condition;

/**
 * @author Jacky.gao
 * @since 2016年12月1日
 */
public enum Join {
	and,or;
	public static Join parse(String join){
		if(join.equals("and") || join.equals("&&")){
			return and;
		}
		if(join.equals("or") || join.equals("||")){
			return or;
		}
		throw new IllegalArgumentException("Unknow join : "+join);
	}
}
