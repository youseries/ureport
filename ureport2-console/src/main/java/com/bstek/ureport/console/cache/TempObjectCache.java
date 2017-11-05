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
