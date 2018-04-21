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

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.bstek.ureport.chart.ChartData;
import com.bstek.ureport.definition.ReportDefinition;

/**
 * @author Jacky.gao
 * @since 2017年3月8日
 */
public class CacheUtils implements ApplicationContextAware{
	private static ReportCache reportCache;
	private static ReportDefinitionCache reportDefinitionCache;
	private static String CHART_DATA_key="_chart_data_";
	
	@SuppressWarnings("unchecked")
	public static ChartData getChartData(String chartId){
		String key=CHART_DATA_key;
		if(reportCache!=null){
			Map<String, ChartData> chartDataMap = (Map<String, ChartData>)reportCache.getObject(key);
			if(chartDataMap!=null){
				return chartDataMap.get(chartId);				
			}
		}
		return null;
	}
	
	public static void storeChartDataMap(Map<String, ChartData> map){
		String key=CHART_DATA_key;
		if(reportCache!=null){
		    reportCache.storeObject(key, map);
		}
	}
	
	public static Object getObject(String file){
		if(reportCache!=null){
			return reportCache.getObject(file);
		}
		return null;
	}
	public static void storeObject(String file,Object obj){
		if(reportCache!=null){
			reportCache.storeObject(file, obj);
		}
	}
	
	public static ReportDefinition getReportDefinition(String file){
		return reportDefinitionCache.getReportDefinition(file);
	}
	public static void cacheReportDefinition(String file,ReportDefinition reportDefinition){
		reportDefinitionCache.cacheReportDefinition(file, reportDefinition);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Collection<ReportCache> coll=applicationContext.getBeansOfType(ReportCache.class).values();
		for(ReportCache cache:coll){
			if(cache.disabled()){
				continue;
			}
			reportCache=cache;
			break;
		}
		Collection<ReportDefinitionCache> reportCaches=applicationContext.getBeansOfType(ReportDefinitionCache.class).values();
		if(reportCaches.size()==0){
			reportDefinitionCache=new DefaultMemoryReportDefinitionCache();
		}else{
			reportDefinitionCache=reportCaches.iterator().next();
		}
	}
}
