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

/**
 * @author Jacky.gao
 * @since 2017年9月6日
 */
public class ObjectMap {
	private final int MAX_ITEM=3;
	private static final int MILLISECOND=300000;//default expired time is 5 minutes.
	private Map<String, Object> map=new LinkedHashMap<String, Object>();
	private long start;
	public ObjectMap() {
		this.start=System.currentTimeMillis();
	}
	public void put(String key,Object obj){
		this.start=System.currentTimeMillis();
		if(map.containsKey(key)){
			map.remove(key);
		}else{
			if(map.size()>MAX_ITEM){
				String lastFile=null;
				for(Iterator<Entry<String,Object>> it=map.entrySet().iterator();it.hasNext();){
					Entry<String,Object> entry=it.next();
					lastFile=entry.getKey();
				}
				map.remove(lastFile);
			}
		}
		map.put(key, obj);
	}
	public Object get(String key){
		this.start=System.currentTimeMillis();
		return this.map.get(key);
	}
	public void remove(String key){
		this.map.remove(key);
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
