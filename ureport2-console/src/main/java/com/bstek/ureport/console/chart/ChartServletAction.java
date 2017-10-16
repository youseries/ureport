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
