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
package com.bstek.ureport.console.pdf;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.bstek.ureport.build.ReportBuilder;
import com.bstek.ureport.cache.CacheUtils;
import com.bstek.ureport.console.BaseServletAction;
import com.bstek.ureport.console.cache.TempObjectCache;
import com.bstek.ureport.console.exception.ReportDesignException;
import com.bstek.ureport.definition.Paper;
import com.bstek.ureport.definition.ReportDefinition;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.export.ExportConfigure;
import com.bstek.ureport.export.ExportConfigureImpl;
import com.bstek.ureport.export.ExportManager;
import com.bstek.ureport.export.ReportRender;
import com.bstek.ureport.export.pdf.PdfProducer;
import com.bstek.ureport.model.Report;

/**
 * @author Jacky.gao
 * @since 2017年3月20日
 */
public class ExportPdfServletAction extends BaseServletAction{
	private ReportBuilder reportBuilder;
	private ExportManager exportManager;
	private ReportRender reportRender;
	private PdfProducer pdfProducer=new PdfProducer();
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method=retriveMethod(req);
		if(method!=null){
			invokeMethod(method, req, resp);
		}else{			
			buildPdf(req, resp,false);
		}
	}
	public void show(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		buildPdf(req, resp,true);
	}

	public void buildPdf(HttpServletRequest req, HttpServletResponse resp,boolean forPrint) throws IOException {
		String file=req.getParameter("_u");
		file=decode(file);
		if(StringUtils.isBlank(file)){
			throw new ReportComputeException("Report file can not be null.");
		}
		String fileName=req.getParameter("_n");
		if(StringUtils.isNotBlank(fileName)){
			fileName=decode(fileName);
			if(!fileName.toLowerCase().endsWith(".pdf")){
				fileName=fileName+".pdf";
			}
		}else{
			fileName="ureport.pdf";
		}
		fileName=new String(fileName.getBytes("UTF-8"),"ISO8859-1");
		if(forPrint){
			resp.setContentType("application/pdf");
			resp.setHeader("Content-Disposition","inline;filename=\"" + fileName + "\"");
		}else{
			resp.setContentType("application/octet-stream;charset=ISO8859-1");
			resp.setHeader("Content-Disposition","attachment;filename=\"" + fileName + "\"");
		}
		Map<String, Object> parameters = buildParameters(req);
		String fullName=file+parameters.toString();
		OutputStream outputStream=resp.getOutputStream();
		if(file.equals(PREVIEW_KEY)){
			Report report=CacheUtils.getReport(fullName);
			if(report==null){
				ReportDefinition reportDefinition=(ReportDefinition)TempObjectCache.getObject(PREVIEW_KEY);
				if(reportDefinition==null){
					throw new ReportDesignException("Report data has expired,can not do export pdf.");
				}
				report=reportBuilder.buildReport(reportDefinition, parameters);	
			}
			pdfProducer.produce(report, outputStream);
		}else{
			ExportConfigure configure=new ExportConfigureImpl(file,parameters,outputStream);
			exportManager.exportPdf(configure);
		}
		outputStream.flush();
		outputStream.close();
	}
	
	public void newPaging(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String file=req.getParameter("_u");
		if(StringUtils.isBlank(file)){
			throw new ReportComputeException("Report file can not be null.");
		}
		Report report=null;
		Map<String, Object> parameters = buildParameters(req);
		String fullName=file+parameters.toString();
		if(file.equals(PREVIEW_KEY)){
			report=CacheUtils.getReport(fullName);
			ReportDefinition reportDefinition=(ReportDefinition)TempObjectCache.getObject(PREVIEW_KEY);
			if(report==null){
				if(reportDefinition==null){
					throw new ReportDesignException("Report data has expired,can not do export pdf.");
				}
				report=reportBuilder.buildReport(reportDefinition, parameters);	
			}
		}else{
			report=CacheUtils.getReport(fullName);
			if(report==null){
				ReportDefinition reportDefinition=reportRender.getReportDefinition(file);
				report=reportRender.render(reportDefinition, parameters);
			}
		}
		String paper=req.getParameter("_paper");
		ObjectMapper mapper=new ObjectMapper();
		Paper newPaper=mapper.readValue(paper, Paper.class);
		report.rePaging(newPaper);
	}
	
	public void setReportRender(ReportRender reportRender) {
		this.reportRender = reportRender;
	}
	
	public void setExportManager(ExportManager exportManager) {
		this.exportManager = exportManager;
	}
	
	public void setReportBuilder(ReportBuilder reportBuilder) {
		this.reportBuilder = reportBuilder;
	}

	@Override
	public String url() {
		return "/pdf";
	}
}
