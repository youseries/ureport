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
 * @since 2017年9月15日
 */
public abstract class AbstractAssertor implements Assertor{
	protected Object buildObject(Object obj){
		if(obj==null){
			return obj;
		}
		if(obj instanceof List){
			List<?> list=(List<?>)obj;
			if(list.size()==1){
				return list.get(0);
			}
		}
		return obj;
	}
}
