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
package com.bstek.ureport.export;

import java.util.List;
import java.util.Map;

import com.bstek.ureport.build.paging.Page;
import com.bstek.ureport.cache.CacheUtils;
import com.bstek.ureport.definition.ReportDefinition;
import com.bstek.ureport.export.excel.ExcelProducer;
import com.bstek.ureport.export.html.HtmlProducer;
import com.bstek.ureport.export.html.HtmlReport;
import com.bstek.ureport.export.pdf.PdfProducer;
import com.bstek.ureport.export.word.WordProducer;
import com.bstek.ureport.model.Report;

/**
 * @author Jacky.gao
 * @since 2016年12月4日
 */
public class ExportManagerImpl implements ExportManager {
	private ReportRender reportRender;
	private HtmlProducer htmlProducer=new HtmlProducer();
	private WordProducer wordProducer=new WordProducer();
	private ExcelProducer excelProducer=new ExcelProducer();
	private PdfProducer pdfProducer=new PdfProducer();
	@Override
	public HtmlReport exportHtml(String file,String contextPath,Map<String, Object> parameters) {
		ReportDefinition reportDefinition=reportRender.getReportDefinition(file);
		Report report=reportRender.render(reportDefinition, parameters);
		CacheUtils.storeReport(file, report);
		HtmlReport htmlReport=new HtmlReport();
		String content=htmlProducer.produce(report);
		htmlReport.setContent(content);
		htmlReport.setStyle(reportDefinition.getStyle());
		return htmlReport;
	}
	
	@Override
	public HtmlReport exportHtml(String file,String contextPath,Map<String, Object> parameters, int pageIndex) {
		ReportDefinition reportDefinition=reportRender.getReportDefinition(file);
		Report report=reportRender.render(reportDefinition, parameters);
		SinglePageData pageData=PageBuilder.buildSinglePageData(pageIndex, report);
		List<Page> pages=pageData.getPages();
		String content=null;
		if(pages.size()==1){
			content=htmlProducer.produce(report.getContext(),pages.get(0),false);
		}else{
			content=htmlProducer.produce(report.getContext(),pages,pageData.getColumnMargin(),false);			
		}
		HtmlReport htmlReport=new HtmlReport();
		htmlReport.setContent(content);
		htmlReport.setStyle(reportDefinition.getStyle());
		htmlReport.setPageIndex(pageIndex);
		htmlReport.setTotalPage(pageData.getTotalPages());
		return htmlReport;
	}
	@Override
	public void exportPdf(ExportConfigure config) {
		String file=config.getFile();
		ReportDefinition reportDefinition=reportRender.getReportDefinition(file);
		Report report=reportRender.render(reportDefinition, config.getParameters());
		pdfProducer.produce(report, config.getOutputStream());
	}
	@Override
	public void exportWord(ExportConfigure config) {
		String file=config.getFile();
		ReportDefinition reportDefinition=reportRender.getReportDefinition(file);
		Report report=reportRender.render(reportDefinition, config.getParameters());
		wordProducer.produce(report, config.getOutputStream());
	}
	@Override
	public void exportExcel(ExportConfigure config) {
		String file=config.getFile();
		ReportDefinition reportDefinition=reportRender.getReportDefinition(file);
		Report report=reportRender.render(reportDefinition, config.getParameters());
		excelProducer.produce(report, config.getOutputStream());
	}
	
	@Override
	public void exportExcelWithPaging(ExportConfigure config) {
		String file=config.getFile();
		ReportDefinition reportDefinition=reportRender.getReportDefinition(file);
		Report report=reportRender.render(reportDefinition, config.getParameters());
		excelProducer.produceWithPaging(report, config.getOutputStream());
	}
	
	@Override
	public void exportExcelWithPagingSheet(ExportConfigure config) {
		String file=config.getFile();
		ReportDefinition reportDefinition=reportRender.getReportDefinition(file);
		Report report=reportRender.render(reportDefinition, config.getParameters());
		excelProducer.produceWithSheet(report, config.getOutputStream());
	}
	
	public void setReportRender(ReportRender reportRender) {
		this.reportRender = reportRender;
	}
}
