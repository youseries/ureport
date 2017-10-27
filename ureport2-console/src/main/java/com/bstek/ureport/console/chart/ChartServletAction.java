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
package com.bstek.ureport.console.chart;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bstek.ureport.cache.CacheUtils;
import com.bstek.ureport.chart.ChartData;
import com.bstek.ureport.console.RenderPageServletAction;
import com.bstek.ureport.model.Report;
import com.bstek.ureport.utils.UnitUtils;

/**
 * @author Jacky.gao
 * @since 2017年6月30日
 */
public class ChartServletAction extends RenderPageServletAction {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method=retriveMethod(req);
		if(method!=null){
			invokeMethod(method, req, resp);
		}
	}
	
	public void storeData(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String file=req.getParameter("_u");
		file=decode(file);
		Map<String, Object> parameters = buildParameters(req);
		String fullName=file+parameters.toString();
		Report report=CacheUtils.getReport(fullName);
		if(report==null){
			return;
		}
		String chartId=req.getParameter("_chartId");
		String base64Data=req.getParameter("_base64Data");
		String prefix="data:image/png;base64,";
		if(base64Data!=null){
			if(base64Data.startsWith(prefix)){
				base64Data=base64Data.substring(prefix.length(),base64Data.length());
			}
		}
		Map<String, ChartData> map=report.getContext().getChartDataMap();
		ChartData chartData=map.get(chartId);
		if(chartData==null)return;
		chartData.setBase64Data(base64Data);
		String width=req.getParameter("_width");
		String height=req.getParameter("_height");
		chartData.setHeight(UnitUtils.pixelToPoint(Integer.valueOf(height)));
		chartData.setWidth(UnitUtils.pixelToPoint(Integer.valueOf(width)));
	}

	@Override
	public String url() {
		return "/chart";
	}
}
