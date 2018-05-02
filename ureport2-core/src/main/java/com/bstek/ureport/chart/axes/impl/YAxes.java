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
package com.bstek.ureport.chart.axes.impl;

import com.bstek.ureport.chart.axes.BaseAxes;
import com.bstek.ureport.chart.axes.ScaleLabel;
import com.bstek.ureport.chart.axes.YPosition;

/**
 * @author Jacky.gao
 * @since 2017年6月14日
 */
public class YAxes extends BaseAxes {
	private YPosition yposition;
	@Override
	public String toJson() {
		StringBuilder sb=new StringBuilder();
		sb.append("{");
		sb.append("\"ticks\":{");
		sb.append("\"minRotation\":"+getRotation()+"");
		sb.append("}");
		ScaleLabel scaleLabel=getScaleLabel();
		if(scaleLabel!=null){
			
			sb.append(",\"scaleLabel\":"+scaleLabel.toJson());	
		}
		sb.append("}");
		return sb.toString();
	}
	public YPosition getYposition() {
		return yposition;
	}
	public void setYposition(YPosition yposition) {
		this.yposition = yposition;
	}
}
