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


/**
 * @author Jacky.gao
 * @since 2017年1月24日
 */
public class Image {
	private String base64Data;
	private String path;
	private int width;
	private int height;
	public Image(String base64Data,int width,int height) {
		this.base64Data=base64Data;
		this.width=width;
		this.height=height;
	}
	public Image(String base64Data,String path,int width,int height) {
		this.base64Data=base64Data;
		this.path=path;
		this.width=width;
		this.height=height;
	}
	public String getBase64Data() {
		return base64Data;
	}
	public String getPath() {
		return path;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
}
