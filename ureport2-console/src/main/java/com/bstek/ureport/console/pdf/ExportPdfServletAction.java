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
import com.bstek.ureport.console.BaseServletAction;
import com.bstek.ureport.console.cache.TempObjectCache;
import com.bstek.ureport.console.exception.ReportDesignException;
import com.bstek.ureport.definition.Paper;
import com.bstek.ureport.definition.ReportDefinition;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.exception.ReportException;
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
		OutputStream outputStream=null;
		try {
			String fileName=req.getParameter("_n");
			fileName=buildDownloadFileName(file, fileName, ".pdf");
			fileName=new String(fileName.getBytes("UTF-8"),"ISO8859-1");
			if(forPrint){
				resp.setContentType("application/pdf");
				resp.setHeader("Content-Disposition","inline;filename=\"" + fileName + "\"");
			}else{
				resp.setContentType("application/octet-stream;charset=ISO8859-1");
				resp.setHeader("Content-Disposition","attachment;filename=\"" + fileName + "\"");
			}
			outputStream=resp.getOutputStream();
			Map<String, Object> parameters = buildParameters(req);
			if(file.equals(PREVIEW_KEY)){
				ReportDefinition reportDefinition=(ReportDefinition)TempObjectCache.getObject(PREVIEW_KEY);
				if(reportDefinition==null){
					throw new ReportDesignException("Report data has expired,can not do export pdf.");
				}
				Report report=reportBuilder.buildReport(reportDefinition, parameters);	
				pdfProducer.produce(report, outputStream);
			}else{
				ExportConfigure configure=new ExportConfigureImpl(file,parameters,outputStream);
				exportManager.exportPdf(configure);
			}			
		}catch(Exception ex) {
			throw new ReportException(ex);
		}finally {			
			outputStream.flush();
			outputStream.close();
		}
	}
	
	public void newPaging(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String file=req.getParameter("_u");
		if(StringUtils.isBlank(file)){
			throw new ReportComputeException("Report file can not be null.");
		}
		Report report=null;
		Map<String, Object> parameters = buildParameters(req);
		if(file.equals(PREVIEW_KEY)){
			ReportDefinition reportDefinition=(ReportDefinition)TempObjectCache.getObject(PREVIEW_KEY);
			if(reportDefinition==null){
				throw new ReportDesignException("Report data has expired,can not do export pdf.");
			}
			report=reportBuilder.buildReport(reportDefinition, parameters);	
		}else{
			ReportDefinition reportDefinition=reportRender.getReportDefinition(file);
			report=reportRender.render(reportDefinition, parameters);
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
