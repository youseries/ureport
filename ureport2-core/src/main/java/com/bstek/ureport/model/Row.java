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

import com.bstek.ureport.definition.Band;


/**
 * @author Jacky.gao
 * @since 2016年11月1日
 */
public class Row extends Line{
	private int height;
	private int realHeight=-1;
	private String rowKey;
	/**
	 * 一个用来临时存放当前行号的属性，只在构建报表时创建新行时使用
	 */
	private int tempRowNumber;
	private Band band;
	private boolean forPaging;
	private boolean pageBreak;
	private boolean hide;
	
	private List<Row> rows;
	
	public Row(List<Row> rows) {
		this.rows=rows;
	}
	
	public Row newRow(){
		Row row=new Row(rows);
		row.setHeight(height);
		row.setRealHeight(realHeight);
		row.setBand(band);
		row.setRowKey(rowKey);
		row.setCustomCellStyle(getCustomCellStyle());
		return row;
	}

	public String getRowKey() {
		return rowKey;
	}

	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}

	public int getRowNumber() {
		return rows.indexOf(this)+1;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public Band getBand() {
		return band;
	}
	public void setBand(Band band) {
		this.band = band;
	}

	public boolean isForPaging() {
		return forPaging;
	}

	public void setForPaging(boolean forPaging) {
		this.forPaging = forPaging;
	}

	public boolean isPageBreak() {
		return pageBreak;
	}

	public void setPageBreak(boolean pageBreak) {
		this.pageBreak = pageBreak;
	}

	public int getTempRowNumber() {
		return tempRowNumber;
	}

	public void setTempRowNumber(int tempRowNumber) {
		this.tempRowNumber = tempRowNumber;
	}

	public int getRealHeight() {
		if(realHeight==-1){
			return height;
		}
		return realHeight;
	}

	public void setRealHeight(int realHeight) {
		this.realHeight = realHeight;
	}

	public boolean isHide() {
		return hide;
	}

	public void setHide(boolean hide) {
		this.hide = hide;
	}
}
