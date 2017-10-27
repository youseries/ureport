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
package com.bstek.ureport.expression.model.expr.set;

/**
 * @author Jacky.gao
 * @since 2017年4月2日
 */
public class CellCoordinate {
	private String cellName;
	private int position;
	private boolean reverse;
	private CoordinateType coordinateType;
	
	public CellCoordinate(String cellName,CoordinateType coordinateType) {
		this.cellName = cellName;
		this.coordinateType=coordinateType;
	}
	public String getCellName() {
		return cellName;
	}
	public void setCellName(String cellName) {
		this.cellName = cellName;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	
	public boolean isReverse() {
		return reverse;
	}
	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}
	public CoordinateType getCoordinateType() {
		return coordinateType;
	}
	
	@Override
	public String toString() {
		return cellName+":"+position;
	}
}
