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
 * @since 2017年6月19日
 */
public class ConditionPaging {
	private PagingPosition position;
	/**
	 * 当position为after时，line用来指定当前行后多少行进行分页 
	 */
	private int line;
	
	public PagingPosition getPosition() {
		return position;
	}
	public void setPosition(PagingPosition position) {
		this.position = position;
	}
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
}
