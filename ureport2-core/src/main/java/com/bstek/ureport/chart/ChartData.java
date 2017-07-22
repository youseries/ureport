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
package com.bstek.ureport.chart;

import java.util.UUID;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author Jacky.gao
 * @since 2017年6月16日
 */
public class ChartData {
	private String id;
	private String json;
	@JsonIgnore
	private String base64Data;
	@JsonIgnore
	private int width;
	@JsonIgnore
	private int height;
	public ChartData(String json) {
		this.json=json;
		this.id=UUID.randomUUID().toString();
	}
	public String getJson() {
		return json;
	}
	public void setBase64Data(String base64Data) {
		this.base64Data = base64Data;
	}
	
	public String retriveBase64Data(){
		return base64Data;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getId() {
		return id;
	}
}
