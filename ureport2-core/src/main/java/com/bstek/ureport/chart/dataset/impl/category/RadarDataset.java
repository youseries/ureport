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
package com.bstek.ureport.chart.dataset.impl.category;

import com.bstek.ureport.build.Context;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2017年6月8日
 */
public class RadarDataset extends CategoryDataset {
	private boolean fill=true;
	private double lineTension=0.1;
	
	@Override
	public String buildDataJson(Context context,Cell cell) {
		String props="\"fill\":"+fill+",\"lineTension\":"+lineTension;
		String datasetJson=buildDatasetJson(context, cell,props);
		StringBuilder sb=new StringBuilder();
		sb.append("{");
		String labels=getLabels();
		sb.append("\"labels\":"+labels+",");
		sb.append("\"datasets\":["+datasetJson+"]");
		sb.append("}");
		return sb.toString();
	}
	
	@Override
	public String getType() {
		return "radar";
	}

	public boolean isFill() {
		return fill;
	}

	public void setFill(boolean fill) {
		this.fill = fill;
	}

	public double getLineTension() {
		return lineTension;
	}

	public void setLineTension(double lineTension) {
		this.lineTension = lineTension;
	}
}
