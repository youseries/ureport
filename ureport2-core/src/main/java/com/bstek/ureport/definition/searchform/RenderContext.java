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
package com.bstek.ureport.definition.searchform;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jacky.gao
 * @since 2017年10月23日
 */
public class RenderContext {
	private int id=0;
	private Map<Component,String> componentMap=new HashMap<Component,String>();
	private Map<String,Object> metadata=new HashMap<String,Object>();
	
	public String buildComponentId(Component component){
		if(componentMap.containsKey(component)){
			return componentMap.get(component);
		}
		String cid="__c_"+(id++);
		componentMap.put(component, cid);
		return cid;
	}
	public Object getMetadata(String key) {
		return metadata.get(key);
	}
	public void putMetadata(String key,Object value){
		metadata.put(key, value);
	}
	
}
