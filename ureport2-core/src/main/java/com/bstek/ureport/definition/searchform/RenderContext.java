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
package com.bstek.ureport.definition.searchform;

import java.util.HashMap;
import java.util.Map;

import com.bstek.ureport.build.Dataset;

/**
 * @author Jacky.gao
 * @since 2017年10月23日
 */
public class RenderContext {
	private int id=0;
	private Map<String,Dataset> datasetMap;
	private Map<String, Object> parameters;
	private Map<Component,String> componentMap=new HashMap<Component,String>();
	private Map<String,Object> metadata=new HashMap<String,Object>();
	public RenderContext(Map<String,Dataset> datasetMap,Map<String, Object> parameters) {
		this.datasetMap=datasetMap;
		this.parameters=parameters;
	}
	public Dataset getDataset(String datasetName) {
		return datasetMap.get(datasetName);
	}
	public Object getParameter(String name){
		return parameters.get(name);
	}
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
