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

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bstek.ureport.cache.ReportCache;
import com.bstek.ureport.console.RequestHolder;
import com.bstek.ureport.model.Report;

/**
 * @author Jacky.gao
 * @since 2017年3月8日
 */
public class HttpSessionReportCache implements ReportCache {
	private final String KEY="__ureport_map";
	private final int MAX_ITEM=3;
	
	@Override
	public Report getReport(String file) {
		HttpServletRequest req=RequestHolder.getRequest();
		if(req==null){
			return null;
		}
		Map<String, Report> map = getReportMap(req);
		return map.get(file);
	}

	@Override
	public void storeReport(String file, Report report) {
		HttpServletRequest req=RequestHolder.getRequest();
		if(req==null){
			return;
		}
		Map<String, Report> map = getReportMap(req);
		if(map.containsKey(file)){
			map.remove(file);
		}else{
			if(map.size()>MAX_ITEM){
				String lastFile=null;
				for(Iterator<Entry<String,Report>> it=map.entrySet().iterator();it.hasNext();){
					Entry<String,Report> entry=it.next();
					lastFile=entry.getKey();
				}
				map.remove(lastFile);
			}
		}
		map.put(file, report);
	}
	

	@SuppressWarnings("unchecked")
	private Map<String, Report> getReportMap(HttpServletRequest req) {
		HttpSession session=req.getSession();
		Map<String,Report> map=(Map<String,Report>)session.getAttribute(KEY);
		if(map==null){
			map=new LinkedHashMap<String,Report>();
			session.setAttribute(KEY, map);
		}
		return map;
	}
}
