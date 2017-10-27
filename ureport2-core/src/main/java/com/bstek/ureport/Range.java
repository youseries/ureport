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
package com.bstek.ureport;

import java.io.Serializable;

/**
 * @author Jacky.gao
 * @since 2017年2月24日
 */
public class Range implements Serializable{
	private static final long serialVersionUID = -4547468301777433024L;
	private int start=-1;
	private int end;
	public Range() {
	}
	public Range(int start, int end) {
		this.start = start;
		this.end = end;
	}
	
	public void setEnd(int end) {
		this.end = end;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getStart() {
		return start;
	}
	public int getEnd() {
		return end;
	}
}
