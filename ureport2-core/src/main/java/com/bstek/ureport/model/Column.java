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

import java.util.List;


/**
 * @author Jacky.gao
 * @since 2016年11月1日
 */
public class Column extends Line{
	private int width;
	private boolean hide;
	/**
	 * 一个用来临时存放当前列号的属性，只在构建报表时创建新列时使用
	 */
	private int tempColumnNumber;
	private List<Column> columns;
	
	public Column(List<Column> columns) {
		this.columns=columns;
	}
	
	public Column newColumn(){
		Column col=new Column(columns);
		col.setWidth(width);
		return col;
	}
	
	public int getColumnNumber() {
		return this.columns.indexOf(this)+1;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public boolean isHide() {
		return hide;
	}

	public void setHide(boolean hide) {
		this.hide = hide;
	}

	public int getTempColumnNumber() {
		return tempColumnNumber;
	}

	public void setTempColumnNumber(int tempColumnNumber) {
		this.tempColumnNumber = tempColumnNumber;
	}
}
