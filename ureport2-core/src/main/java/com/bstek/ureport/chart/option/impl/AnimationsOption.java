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

import com.bstek.ureport.chart.option.Easing;
import com.bstek.ureport.chart.option.Option;

/**
 * @author Jacky.gao
 * @since 2017年6月8日
 */
public class AnimationsOption implements Option {
	private int duration=1000;
	private Easing easing=Easing.easeOutQuart;
	@Override
	public String buildOptionJson() {
		StringBuilder sb=new StringBuilder();
		sb.append("\"animation\":{");
		sb.append("\"duration\":"+duration+",");
		sb.append("\"easing\":\""+easing+"\"");
		sb.append("}");
		return sb.toString();
	}
	@Override
	public String getType() {
		return "animation";
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public Easing getEasing() {
		return easing;
	}
	public void setEasing(Easing easing) {
		this.easing = easing;
	}
}
