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

import java.util.List;

/**
 * @author Jacky.gao
 * @since 2017年4月2日
 */
public class CellCoordinateSet {
	private List<CellCoordinate> cellCoordinates;
	
	public CellCoordinateSet(List<CellCoordinate> cellCoordinates) {
		this.cellCoordinates = cellCoordinates;
	}
	public List<CellCoordinate> getCellCoordinates() {
		return cellCoordinates;
	}
}
