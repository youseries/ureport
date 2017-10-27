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

import com.bstek.ureport.console.RequestHolder;


/**
 * @author Jacky.gao
 * @since 2017年9月6日
 */
public class TempObjectCache{
	private static TempObjectCache tempObjectCache=new TempObjectCache();
	private Map<String,ObjectMap> sessionMap=new HashMap<String,ObjectMap>();
	public static Object getObject(String key){
		return tempObjectCache.get(key);
	}
	public static void putObject(String key,Object obj){
		tempObjectCache.store(key, obj);
	}
	
	public static void removeObject(String key){
		tempObjectCache.remove(key);
	}
	
	public void remove(String key){
		HttpServletRequest req=RequestHolder.getRequest();
		if(req==null){
			return;
		}
		ObjectMap mapObject = getReportMap(req);
		if(mapObject!=null){
			mapObject.remove(key);
		}
	}
	
	public Object get(String key) {
		HttpServletRequest req=RequestHolder.getRequest();
		if(req==null){
			return null;
		}
		ObjectMap mapObject = getReportMap(req);
		return mapObject.get(key);
	}

	public void store(String key, Object obj) {
		HttpServletRequest req=RequestHolder.getRequest();
		if(req==null){
			return;
		}
		ObjectMap mapObject = getReportMap(req);
		mapObject.put(key, obj);
	}

	private ObjectMap getReportMap(HttpServletRequest req) {
		List<String> expiredList=new ArrayList<String>();
		for(String key:sessionMap.keySet()){
			ObjectMap reportObj=sessionMap.get(key);
			if(reportObj.isExpired()){
				expiredList.add(key);
			}
		}
		for(String key:expiredList){
			sessionMap.remove(key);
		}
		String sessionId=req.getSession().getId();
		ObjectMap obj=sessionMap.get(sessionId);
		if(obj!=null){
			return obj;
		}else{
			ObjectMap mapObject=new ObjectMap();
			sessionMap.put(sessionId, mapObject);
			return mapObject;
		}
	}
}
