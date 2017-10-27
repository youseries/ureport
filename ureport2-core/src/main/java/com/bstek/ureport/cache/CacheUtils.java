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

import java.util.Collection;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.bstek.ureport.definition.ReportDefinition;
import com.bstek.ureport.model.Report;

/**
 * @author Jacky.gao
 * @since 2017年3月8日
 */
public class CacheUtils implements ApplicationContextAware{
	private static ReportCache reportCache;
	private static ReportDefinitionCache reportDefinitionCache;
	
	public static Report getReport(String file){
		if(reportCache!=null){
			return reportCache.getReport(file);
		}
		return null;
	}
	public static void storeReport(String file,Report report){
		if(reportCache!=null){
			reportCache.storeReport(file, report);
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
