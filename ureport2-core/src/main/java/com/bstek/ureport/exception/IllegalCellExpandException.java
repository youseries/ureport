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
package com.bstek.ureport.exception;

import com.bstek.ureport.model.ReportCell;

/**
 * @author Jacky.gao
 * @since 2016年11月1日
 */
public class IllegalCellExpandException extends ReportException{
	private static final long serialVersionUID = -2442986317129037490L;

	public IllegalCellExpandException(ReportCell cell) {
		super("Cell expand is "+cell.getExpand()+" is invalid.");
	}
}
