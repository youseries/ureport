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
	private static final int MILLISECOND=1200000;//default expired time is 20 minutes.
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
		this.start=System.currentTimeMillis();
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
