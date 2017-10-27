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

/**
 * @author Jacky.gao
 * @since 2017年9月6日
 */
public class ObjectMap {
	private final int MAX_ITEM=3;
	private static final int MILLISECOND=1200000;//default expired time is 20 minutes.
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
