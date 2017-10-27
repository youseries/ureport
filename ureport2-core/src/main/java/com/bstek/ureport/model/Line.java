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

import java.util.ArrayList;
import java.util.List;

import com.bstek.ureport.definition.CellStyle;

/**
 * @author Jacky.gao
 * @since 2016年11月1日
 */
public abstract class Line{
	private CellStyle customCellStyle;
	private List<Cell> cells=new ArrayList<Cell>();

	public CellStyle getCustomCellStyle() {
		return customCellStyle;
	}

	public void setCustomCellStyle(CellStyle customCellStyle) {
		this.customCellStyle = customCellStyle;
	}

	public List<Cell> getCells() {
		return cells;
	}

	public void setCells(List<Cell> cells) {
		this.cells = cells;
	}
}
