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
