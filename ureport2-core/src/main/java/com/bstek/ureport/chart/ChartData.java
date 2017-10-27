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
