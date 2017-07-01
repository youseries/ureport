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
package com.bstek.ureport.chart.dataset;


/**
 * @author Jacky.gao
 * @since 2017年6月9日
 */
public abstract class BaseDataset implements Dataset {
	protected String getRgbColor(int index){
		String[] COLORS={"255, 99, 132","54, 162, 235","255, 205, 86","75, 192, 192",
				"255, 159, 64","153,102,255","53,202,25","201,203,207","205,92,92",
				"255,127,80","173,255,47","127,255,0","138,43,226","147,112,219",
				"198,226,255","rbg(154,50,205","193,205,205","238,238,224",
				"24,116,205","137,104,205","180,82,205","205,50,120","205,0,0","148,0,211"};
		if(index>=COLORS.length){
			index=index % COLORS.length;
		}
		return COLORS[index];
	}
}
