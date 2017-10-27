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
package com.bstek.ureport.build;

import java.util.List;


/**
 * @author Jacky.gao
 * @since 2016年11月1日
 */
public class BindData {
	private Object value;
	private Object label;
	private List<Object> dataList;
	public BindData(Object value) {
		this.value=value;
	}
	public BindData(Object value,List<Object> dataList) {
		this.value=value;
		this.dataList=dataList;
	}
	public BindData(Object value,Object label,List<Object> dataList) {
		this.value=value;
		this.label=label;
		this.dataList=dataList;
	}
	public Object getValue() {
		return value;
	}
	public List<Object> getDataList() {
		return dataList;
	}
	public Object getLabel() {
		return label;
	}
}
