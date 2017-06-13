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
package com.bstek.ureport.console.html;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.bstek.ureport.build.Context;
import com.bstek.ureport.build.ReportBuilder;
import com.bstek.ureport.build.paging.Page;
import com.bstek.ureport.cache.CacheUtils;
import com.bstek.ureport.console.RenderPageServletAction;
import com.bstek.ureport.console.exception.ReportDesignException;
import com.bstek.ureport.definition.Paper;
import com.bstek.ureport.definition.ReportDefinition;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.exception.ReportException;
import com.bstek.ureport.export.ExportManager;
import com.bstek.ureport.export.FullPageData;
import com.bstek.ureport.export.PageBuilder;
import com.bstek.ureport.export.ReportRender;
import com.bstek.ureport.export.SinglePageData;
import com.bstek.ureport.export.html.HtmlProducer;
import com.bstek.ureport.export.html.HtmlReport;
import com.bstek.ureport.model.Report;

/**
 * @author Jacky.gao
 * @since 2017年2月15日
 */
public class HtmlPreviewServletAction extends RenderPageServletAction {
	private ExportManager exportManager;
	private ReportBuilder reportBuilder;
	private ReportRender reportRender;
	private HtmlProducer htmlProducer=new HtmlProducer();
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method=retriveMethod(req);
		if(method!=null){
			invokeMethod(method, req, resp);
		}else{
			VelocityContext context = new VelocityContext();
			HtmlReport htmlReport=null;
			String errorMsg=null;
			try{
				htmlReport=loadReport(req);
			}catch(Exception ex){
				if(!(ex instanceof ReportDesignException)){
					ex.printStackTrace();					
				}
				errorMsg=ex.toString();
			}
			if(htmlReport==null){
				context.put("content", "<div style='color:red'><strong>报表计算错误：</strong>"+errorMsg+"</div>");
				context.put("error", true);
			}else{
				context.put("content", htmlReport.getContent());
				context.put("style", htmlReport.getStyle());
				context.put("totalPage", htmlReport.getTotalPage()); 
				context.put("pageIndex", htmlReport.getPageIndex()); 
				context.put("error", false);
				context.put("file", req.getParameter("_u"));
				Tools tools=null;
				String toolsInfo=req.getParameter("_t");
				if(StringUtils.isNotBlank(toolsInfo)){
					tools=new Tools(false);
					String[] infos=toolsInfo.split(",");
					for(String name:infos){
						tools.doInit(name);
					}
					context.put("_t", toolsInfo);
					context.put("hasTools", true);
				}else{
					tools=new Tools(true);
				}
				context.put("tools", tools);
			}
			context.put("contextPath", req.getContextPath());
			resp.setContentType("text/html");
			resp.setCharacterEncoding("utf-8");
			Template template=ve.getTemplate("html/html-preview.html","utf-8");
			PrintWriter writer=resp.getWriter();
			template.merge(context, writer);
			writer.close();
		}
	}
	
	public void loadPrintPages(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String file=req.getParameter("_u");
		if(StringUtils.isBlank(file)){
			throw new ReportComputeException("Report file can not be null.");
		}
		Report report=CacheUtils.getReport(file);
		if(report==null){
			throw new ReportException("Report preview data has expired,can not do load pages for print.");
		}
		FullPageData pageData=PageBuilder.buildFullPageData(report);
		StringBuilder sb=new StringBuilder();
		List<List<Page>> list=pageData.getPageList();
		Context context=report.getContext();
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				List<Page> columnPages=list.get(i);
				if(i==0){
					String html=htmlProducer.produce(context,columnPages,pageData.getColumnMargin(),false);
					sb.append(html);											
				}else{
					String html=htmlProducer.produce(context,columnPages,pageData.getColumnMargin(),false);
					sb.append(html);											
				}
			}
		}else{
			List<Page> pages=report.getPages();
			for(int i=0;i<pages.size();i++){
				Page page=pages.get(i);
				if(i==0){
					String html=htmlProducer.produce(context,page, false);
					sb.append(html);
				}else{
					String html=htmlProducer.produce(context,page, true);
					sb.append(html);
				}
			}
		}
		Map<String,String> map=new HashMap<String,String>();
		map.put("html", sb.toString());
		writeObjectToJson(resp, map);
	}
	
	public void loadPagePaper(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String file=req.getParameter("_u");
		if(StringUtils.isBlank(file)){
			throw new ReportComputeException("Report file can not be null.");
		}
		ReportDefinition report=null;
		if(file.equals(PREVIEW_KEY)){
			report=(ReportDefinition)req.getSession().getAttribute(PREVIEW_KEY);	
			if(report==null){
				throw new ReportDesignException("Report data has expired.");
			}
		}else{
			report=reportRender.getReportDefinition(file);
		}
		Paper paper=report.getPaper();
		writeObjectToJson(resp, paper);
	}
	
	private HtmlReport loadReport(HttpServletRequest req) {
		Map<String, Object> parameters = buildParameters(req);
		HtmlReport htmlReport=null;
		String file=req.getParameter("_u");
		String pageIndex=req.getParameter("_i");
		String reload=req.getParameter("_r");
		if(StringUtils.isBlank(file)){
			throw new ReportComputeException("Report file can not be null.");
		}
		if(file.equals(PREVIEW_KEY)){
			Report report=null;
			if(StringUtils.isNotBlank(pageIndex) && StringUtils.isBlank(reload)){
				report=CacheUtils.getReport(file);
			}
			ReportDefinition reportDefinition=(ReportDefinition)req.getSession().getAttribute(PREVIEW_KEY);
			if(report==null){
				if(reportDefinition==null){
					throw new ReportDesignException("Report data has expired,can not do preview.");
				}
				report=reportBuilder.buildReport(reportDefinition, parameters);	
				CacheUtils.storeReport(file, report);
			}
			htmlReport=new HtmlReport();
			String html=null;
			if(StringUtils.isNotBlank(pageIndex)){
				Context context=report.getContext();
				int index=Integer.valueOf(pageIndex);
				SinglePageData pageData=PageBuilder.buildSinglePageData(index, report);
				List<Page> pages=pageData.getPages();
				if(pages.size()==1){
					Page page=pages.get(0);
					html=htmlProducer.produce(context,page,false);					
				}else{
					html=htmlProducer.produce(context,pages,pageData.getColumnMargin(),false);					
				}
				htmlReport.setTotalPage(pageData.getTotalPages());
				htmlReport.setPageIndex(index);
			}else{
				html=htmlProducer.produce(report);				
			}
			htmlReport.setContent(html);
			htmlReport.setStyle(reportDefinition.getStyle());
		}else{
			if(StringUtils.isNotBlank(pageIndex)){
				int index=Integer.valueOf(pageIndex);
				htmlReport=exportManager.exportHtml(file,req.getContextPath(),parameters,index);								
			}else{
				htmlReport=exportManager.exportHtml(file,req.getContextPath(),parameters);				
			}
		}
		return htmlReport;
	}

	public void setExportManager(ExportManager exportManager) {
		this.exportManager = exportManager;
	}
	
	public void setReportBuilder(ReportBuilder reportBuilder) {
		this.reportBuilder = reportBuilder;
	}
	public void setReportRender(ReportRender reportRender) {
		this.reportRender = reportRender;
	}

	@Override
	public String url() {
		return "/preview";
	}
}
