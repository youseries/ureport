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

import com.bstek.ureport.model.Report;

/**
 * @author Jacky.gao
 * @since 2017年8月19日
 */
public class ReportMapObject {
	private final int MAX_ITEM=3;
	private static final int MILLISECOND=1800000;//default expired time is 30 minutes.
	private Map<String, Report> reportMap=new LinkedHashMap<String, Report>();
	private long start;
	public ReportMapObject() {
		this.start=System.currentTimeMillis();
	}
	public void put(String file,Report report){
		this.start=System.currentTimeMillis();
		if(reportMap.containsKey(file)){
			reportMap.remove(file);
		}else{
			if(reportMap.size()>MAX_ITEM){
				String lastFile=null;
				for(Iterator<Entry<String,Report>> it=reportMap.entrySet().iterator();it.hasNext();){
					Entry<String,Report> entry=it.next();
					lastFile=entry.getKey();
				}
				reportMap.remove(lastFile);
			}
		}
		this.reportMap.put(file, report);
	}
	public Report getReport(String file){
		return this.reportMap.get(file);
	}
	public boolean isExpired(){
		long end=System.currentTimeMillis();
		long value=end-start;
		if(value>=MILLISECOND){
			return true;
		}
		return false;
	}
}
