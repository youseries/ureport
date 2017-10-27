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
package com.bstek.ureport.console.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bstek.ureport.cache.ReportCache;
import com.bstek.ureport.console.RequestHolder;
import com.bstek.ureport.model.Report;

/**
 * @author Jacky.gao
 * @since 2017年3月8日
 */
public class HttpSessionReportCache implements ReportCache {
	private Map<String,ReportMapObject> sessionReportMap=new HashMap<String,ReportMapObject>();
	private boolean disabled;
	@Override
	public Report getReport(String file) {
		HttpServletRequest req=RequestHolder.getRequest();
		if(req==null){
			return null;
		}
		ReportMapObject reportMapObject = getReportMap(req);
		return reportMapObject.getReport(file);
	}

	@Override
	public void storeReport(String file, Report report) {
		HttpServletRequest req=RequestHolder.getRequest();
		if(req==null){
			return;
		}
		ReportMapObject reportMapObject = getReportMap(req);
		reportMapObject.put(file, report);
	}
	
	@Override
	public boolean disabled() {
		return disabled;
	}
	
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	private ReportMapObject getReportMap(HttpServletRequest req) {
		List<String> expiredList=new ArrayList<String>();
		for(String key:sessionReportMap.keySet()){
			ReportMapObject reportObj=sessionReportMap.get(key);
			if(reportObj.isExpired()){
				expiredList.add(key);
			}
		}
		for(String key:expiredList){
			sessionReportMap.remove(key);
		}
		String sessionId=req.getSession().getId();
		ReportMapObject reportObject=sessionReportMap.get(sessionId);
		if(reportObject!=null){
			return reportObject;
		}else{
			ReportMapObject reportMapObject=new ReportMapObject();
			sessionReportMap.put(sessionId, reportMapObject);
			return reportMapObject;
		}
	}
}
