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
