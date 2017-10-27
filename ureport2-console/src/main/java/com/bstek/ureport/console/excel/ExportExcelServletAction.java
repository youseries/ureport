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
package com.bstek.ureport.console.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.bstek.ureport.build.ReportBuilder;
import com.bstek.ureport.cache.CacheUtils;
import com.bstek.ureport.console.BaseServletAction;
import com.bstek.ureport.console.cache.TempObjectCache;
import com.bstek.ureport.console.exception.ReportDesignException;
import com.bstek.ureport.definition.ReportDefinition;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.export.ExportConfigure;
import com.bstek.ureport.export.ExportConfigureImpl;
import com.bstek.ureport.export.ExportManager;
import com.bstek.ureport.export.excel.high.ExcelProducer;
import com.bstek.ureport.model.Report;

/**
 * @author Jacky.gao
 * @since 2017年4月17日
 */
public class ExportExcelServletAction extends BaseServletAction {
	private ReportBuilder reportBuilder;
	private ExportManager exportManager;
	private ExcelProducer excelProducer=new ExcelProducer();
	
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method=retriveMethod(req);
		if(method!=null){
			invokeMethod(method, req, resp);
		}else{			
			buildExcel(req, resp,false,false);
		}
	}
	public void paging(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		buildExcel(req, resp, true, false);
	}
	
	public void sheet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		buildExcel(req, resp, false, true);
	}
	
	public void buildExcel(HttpServletRequest req, HttpServletResponse resp,boolean withPage,boolean withSheet) throws IOException {
		String file=req.getParameter("_u");
		file=decode(file);
		if(StringUtils.isBlank(file)){
			throw new ReportComputeException("Report file can not be null.");
		}
		String fileName=req.getParameter("_n");
		if(StringUtils.isNotBlank(fileName)){
			fileName=decode(fileName);
			if(!fileName.toLowerCase().endsWith(".xlsx")){
				fileName=fileName+".xlsx";
			}
		}else{
			fileName="ureport.xlsx";
		}
		resp.setContentType("application/octet-stream;charset=ISO8859-1");
		fileName=new String(fileName.getBytes("UTF-8"),"ISO8859-1");
		resp.setHeader("Content-Disposition","attachment;filename=\"" + fileName + "\"");
		Map<String, Object> parameters = buildParameters(req);
		String fullName=file+parameters.toString();
		OutputStream outputStream=resp.getOutputStream();
		if(file.equals(PREVIEW_KEY)){
			Report report=CacheUtils.getReport(fullName);
			if(report==null){
				ReportDefinition reportDefinition=(ReportDefinition)TempObjectCache.getObject(PREVIEW_KEY);
				if(reportDefinition==null){
					throw new ReportDesignException("Report data has expired,can not do export excel.");
				}
				report=reportBuilder.buildReport(reportDefinition, parameters);	
			}
			if(withPage){
				excelProducer.produceWithPaging(report, outputStream);
			}else if(withSheet){
				excelProducer.produceWithSheet(report, outputStream);
			}else{
				excelProducer.produce(report, outputStream);				
			}
		}else{
			ExportConfigure configure=new ExportConfigureImpl(file,parameters,outputStream);
			if(withPage){
				exportManager.exportExcelWithPaging(configure);
			}else if(withSheet){
				exportManager.exportExcelWithPagingSheet(configure);
			}else{
				exportManager.exportExcel(configure);
			}
		}
		outputStream.flush();
		outputStream.close();
	}
	
	public void setReportBuilder(ReportBuilder reportBuilder) {
		this.reportBuilder = reportBuilder;
	}
	public void setExportManager(ExportManager exportManager) {
		this.exportManager = exportManager;
	}

	@Override
	public String url() {
		return "/excel";
	}
}
