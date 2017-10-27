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

import java.io.Serializable;
import java.util.List;

import com.bstek.ureport.model.Row;

/**
 * @author Jacky.gao
 * @since 2016年11月1日
 */
public class RowDefinition implements Comparable<RowDefinition>,Serializable{
	private static final long serialVersionUID = 8326047944994933822L;
	private int rowNumber;
	private int height;
	private Band band;
	protected Row newRow(List<Row> rows){
		Row row=new Row(rows);
		row.setHeight(height);
		row.setBand(band);
		row.setRowKey("r"+rowNumber);
		return row;
	}
	
	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
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

	@Override
	public int compareTo(RowDefinition o) {
		return rowNumber-o.getRowNumber();
	}
}
