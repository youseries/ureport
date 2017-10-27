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
package com.bstek.ureport.build.compute;

import java.util.ArrayList;
import java.util.List;

import com.bstek.ureport.build.BindData;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.chart.Chart;
import com.bstek.ureport.chart.ChartData;
import com.bstek.ureport.definition.value.ChartValue;
import com.bstek.ureport.definition.value.ValueType;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2017年6月9日
 */
public class ChartValueCompute implements ValueCompute {

	@Override
	public List<BindData> compute(Cell cell, Context context) {
		ChartValue chartValue=(ChartValue)cell.getValue();
		Chart chart=chartValue.getChart();
		ChartData data=chart.doCompute(cell, context);
		List<BindData> list=new ArrayList<BindData>();
		list.add(new BindData(data));
		return list;
	}

	@Override
	public ValueType type() {
		return ValueType.chart;
	}
}
