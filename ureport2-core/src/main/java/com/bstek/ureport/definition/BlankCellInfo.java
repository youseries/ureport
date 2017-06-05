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
package com.bstek.ureport.definition;

/**
 * @author Jacky.gao
 * @since 2017年2月26日
 */
public class BlankCellInfo {
	private int offset;
	private int span;
	private boolean parent;
	public BlankCellInfo() {
	}
	public BlankCellInfo(int offset, int span,boolean parent) {
		this.offset=offset;
		this.span = span;
		this.parent=parent;
	}
	public int getOffset() {
		return offset;
	}

	public int getSpan() {
		return span;
	}
	public boolean isParent() {
		return parent;
	}
}
