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
package com.bstek.ureport.console.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.bstek.ureport.build.ReportBuilder;
import com.bstek.ureport.console.BaseServletAction;
import com.bstek.ureport.console.cache.TempObjectCache;
import com.bstek.ureport.console.exception.ReportDesignException;
import com.bstek.ureport.definition.ReportDefinition;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.exception.ReportException;
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
		OutputStream outputStream=resp.getOutputStream();
		try {
			String fileName=req.getParameter("_n");
			fileName=buildDownloadFileName(file, fileName, ".xlsx");
			resp.setContentType("application/octet-stream;charset=ISO8859-1");
			fileName=new String(fileName.getBytes("UTF-8"),"ISO8859-1");
			resp.setHeader("Content-Disposition","attachment;filename=\"" + fileName + "\"");
			Map<String, Object> parameters = buildParameters(req);
			if(file.equals(PREVIEW_KEY)){
				ReportDefinition reportDefinition=(ReportDefinition)TempObjectCache.getObject(PREVIEW_KEY);
				if(reportDefinition==null){
					throw new ReportDesignException("Report data has expired,can not do export excel.");
				}
				Report report=reportBuilder.buildReport(reportDefinition, parameters);	
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
		}catch(Exception ex) {
			throw new ReportException(ex);
		}finally {
			outputStream.flush();
			outputStream.close();			
		}
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
