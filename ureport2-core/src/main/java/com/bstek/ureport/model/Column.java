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
