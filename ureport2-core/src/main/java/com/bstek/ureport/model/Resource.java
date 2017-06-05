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
package com.bstek.ureport.model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.bstek.ureport.cache.ResourceCache;

/**
 * @author Jacky.gao
 * @since 2017年3月17日
 */
public class Resource {
	private String key;
	
	public Resource(String path) {
		this.key = path;
	}
	
	public InputStream getResourceData(){
		byte[] imageBytes = (byte[])ResourceCache.getObject(key);
		InputStream inputStream=new ByteArrayInputStream(imageBytes);
		return inputStream;
	}

	public String getKey() {
		return key;
	}
}
