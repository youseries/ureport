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
package com.bstek.ureport.parser;

import com.bstek.ureport.definition.CellDefinition;

/**
 * @author Jacky.gao
 * @since 2017年2月27日
 */
public class BuildUtils {
	public static int buildRowNumberEnd(CellDefinition cell,int rowNumber){
		int rowSpan=cell.getRowSpan();
		rowSpan=rowSpan>0 ? rowSpan-1 : rowSpan;
		return rowNumber+rowSpan;
	}
	public static int buildColNumberEnd(CellDefinition cell,int colNumber){
		int colSpan=cell.getColSpan();
		colSpan=colSpan>0 ? colSpan-1 : colSpan;
		return colNumber+colSpan;
	}
}
