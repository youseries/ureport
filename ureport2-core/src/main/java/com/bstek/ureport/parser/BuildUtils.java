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
