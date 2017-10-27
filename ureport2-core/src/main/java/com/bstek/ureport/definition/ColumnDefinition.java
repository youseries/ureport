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
package com.bstek.ureport.definition;

import java.util.List;

import com.bstek.ureport.model.Column;

/**
 * @author Jacky.gao
 * @since 2016年11月1日
 */
public class ColumnDefinition implements Comparable<ColumnDefinition>{
	private int columnNumber;
	private int width;
	private boolean hide;
	
	protected Column newColumn(List<Column> columns){
		Column col=new Column(columns);
		col.setWidth(width);
		return col;
	}

	public int getColumnNumber() {
		return columnNumber;
	}

	public void setColumnNumber(int columnNumber) {
		this.columnNumber = columnNumber;
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

	@Override
	public int compareTo(ColumnDefinition o) {
		return columnNumber-o.getColumnNumber();
	}
}
