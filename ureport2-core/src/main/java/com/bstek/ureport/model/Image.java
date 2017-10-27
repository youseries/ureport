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
