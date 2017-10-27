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
package com.bstek.ureport.chart.option.impl;

import com.bstek.ureport.chart.option.Labels;
import com.bstek.ureport.chart.option.Option;
import com.bstek.ureport.chart.option.Position;

/**
 * @author Jacky.gao
 * @since 2017年6月8日
 */
public class LegendOption implements Option {
	private boolean display=true;
	private Position position=Position.top;
	private Labels labels;
	@Override
	public String buildOptionJson() {
		StringBuilder sb=new StringBuilder();
		sb.append("\"legend\":{");
		sb.append("\"display\":"+display+",");
		sb.append("\"position\":\""+position+"\"");
		if(labels!=null){
			sb.append("\"labels\":"+labels.toJson());			
		}
		sb.append("}");
		return sb.toString();
	}
	@Override
	public String getType() {
		return "legend";
	}
	public boolean isDisplay() {
		return display;
	}
	public void setDisplay(boolean display) {
		this.display = display;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public Labels getLabels() {
		return labels;
	}
	public void setLabels(Labels labels) {
		this.labels = labels;
	}
}
