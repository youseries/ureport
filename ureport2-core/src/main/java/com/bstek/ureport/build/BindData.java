/*******************************************************************************
 * Copyright 2017 Bstek
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
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
