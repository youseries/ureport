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
package com.bstek.ureport.build.paging;

import java.util.List;

import com.bstek.ureport.model.Row;

/**
 * @author Jacky.gao
 * @since 2017年1月17日
 */
public class RepeatRows {
	private List<Row> headerRepeatRows;
	private List<Row> footerRepeatRows;
	
	public RepeatRows(List<Row> headerRepeatRows, List<Row> footerRepeatRows) {
		this.headerRepeatRows = headerRepeatRows;
		this.footerRepeatRows = footerRepeatRows;
	}
	public List<Row> getFooterRepeatRows() {
		return footerRepeatRows;
	}
	public List<Row> getHeaderRepeatRows() {
		return headerRepeatRows;
	}
}
