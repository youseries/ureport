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
package com.bstek.ureport.console.importexcel;

/**
 * @author Jacky.gao
 * @since 2017年5月26日
 */
public class Span {
	private int rowSpan;
	private int colSpan;
	public Span(int rowSpan,int colSpan) {
		this.rowSpan=rowSpan;
		this.colSpan=colSpan;
	}
	public int getRowSpan() {
		return rowSpan;
	}
	public int getColSpan() {
		return colSpan;
	}
}
