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
package com.bstek.ureport.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.bstek.ureport.definition.ReportDefinition;

/**
 * @author Jacky.gao
 * @since 2016年12月4日
 */
public class DefaultMemoryReportDefinitionCache implements ReportDefinitionCache {
	private Map<String,ReportDefinition> reportMap=new ConcurrentHashMap<String,ReportDefinition>();
	@Override
	public ReportDefinition getReportDefinition(String file) {
		return reportMap.get(file);
	}
	@Override
	public void cacheReportDefinition(String file,ReportDefinition reportDefinition) {
		if(reportMap.containsKey(file)){
			reportMap.remove(file);
		}
		reportMap.put(file, reportDefinition);
	}
}
