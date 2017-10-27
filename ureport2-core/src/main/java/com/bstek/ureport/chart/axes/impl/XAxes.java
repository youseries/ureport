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
package com.bstek.ureport.chart.axes.impl;

import com.bstek.ureport.chart.axes.BaseAxes;
import com.bstek.ureport.chart.axes.ScaleLabel;
import com.bstek.ureport.chart.axes.XPosition;

/**
 * @author Jacky.gao
 * @since 2017年6月14日
 */
public class XAxes extends BaseAxes {
	private XPosition xposition=XPosition.bottom;
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
	public XPosition getXposition() {
		return xposition;
	}
	public void setXposition(XPosition xposition) {
		this.xposition = xposition;
	}
}
