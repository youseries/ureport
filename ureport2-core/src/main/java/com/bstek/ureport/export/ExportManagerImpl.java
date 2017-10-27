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
package com.bstek.ureport.export;

import java.util.List;
import java.util.Map;

import com.bstek.ureport.build.paging.Page;
import com.bstek.ureport.cache.CacheUtils;
import com.bstek.ureport.definition.ReportDefinition;
import com.bstek.ureport.export.excel.high.ExcelProducer;
import com.bstek.ureport.export.excel.low.Excel97Producer;
import com.bstek.ureport.export.html.HtmlProducer;
import com.bstek.ureport.export.html.HtmlReport;
import com.bstek.ureport.export.pdf.PdfProducer;
import com.bstek.ureport.export.word.high.WordProducer;
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
	private Excel97Producer excel97Producer=new Excel97Producer();
	private PdfProducer pdfProducer=new PdfProducer();
	@Override
	public HtmlReport exportHtml(String file,String contextPath,Map<String, Object> parameters) {
		ReportDefinition reportDefinition=reportRender.getReportDefinition(file);
		Report report=reportRender.render(reportDefinition, parameters);
		String fullName=file+parameters.toString();
		CacheUtils.storeReport(fullName, report);
		HtmlReport htmlReport=new HtmlReport();
		String content=htmlProducer.produce(report);
		htmlReport.setContent(content);
		htmlReport.setStyle(reportDefinition.getStyle());
		htmlReport.setReportAlign(report.getPaper().getHtmlReportAlign().name());
		htmlReport.setChartDatas(report.getContext().getChartDataMap().values());
		htmlReport.setHtmlIntervalRefreshValue(report.getPaper().getHtmlIntervalRefreshValue());
		return htmlReport;
	}
	
	@Override
	public HtmlReport exportHtml(String file,String contextPath,Map<String, Object> parameters, int pageIndex) {
		ReportDefinition reportDefinition=reportRender.getReportDefinition(file);
		String fullName=file+parameters.toString();
		Report report=CacheUtils.getReport(fullName);
		if (report == null) {
			report = reportRender.render(reportDefinition, parameters);
		}
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
		htmlReport.setReportAlign(report.getPaper().getHtmlReportAlign().name());
		htmlReport.setChartDatas(report.getContext().getChartDataMap().values());
		htmlReport.setHtmlIntervalRefreshValue(report.getPaper().getHtmlIntervalRefreshValue());
		return htmlReport;
	}
	@Override
	public void exportPdf(ExportConfigure config) {
		String file=config.getFile();
		Map<String, Object> parameters=config.getParameters();
		String fullName=file+parameters.toString();
		Report report=CacheUtils.getReport(fullName);
		if (report == null) {
			ReportDefinition reportDefinition=reportRender.getReportDefinition(file);
			report = reportRender.render(reportDefinition, parameters);
		}
		pdfProducer.produce(report, config.getOutputStream());
	}
	@Override
	public void exportWord(ExportConfigure config) {
		String file=config.getFile();
		Map<String, Object> parameters=config.getParameters();
		String fullName=file+parameters.toString();
		Report report=CacheUtils.getReport(fullName);
		if (report == null) {
			ReportDefinition reportDefinition=reportRender.getReportDefinition(file);
			report = reportRender.render(reportDefinition, parameters);
		}
		wordProducer.produce(report, config.getOutputStream());
	}
	@Override
	public void exportExcel(ExportConfigure config) {
		String file=config.getFile();
		Map<String, Object> parameters=config.getParameters();
		String fullName=file+parameters.toString();
		Report report=CacheUtils.getReport(fullName);
		if (report == null) {
			ReportDefinition reportDefinition=reportRender.getReportDefinition(file);
			report = reportRender.render(reportDefinition, parameters);
		}
		excelProducer.produce(report, config.getOutputStream());
	}
	
	@Override
	public void exportExcel97(ExportConfigure config) {
		String file=config.getFile();
		Map<String, Object> parameters=config.getParameters();
		String fullName=file+parameters.toString();
		Report report=CacheUtils.getReport(fullName);
		if (report == null) {
			ReportDefinition reportDefinition=reportRender.getReportDefinition(file);
			report = reportRender.render(reportDefinition, parameters);
		}
		excel97Producer.produce(report, config.getOutputStream());
	}
	
	@Override
	public void exportExcelWithPaging(ExportConfigure config) {
		String file=config.getFile();
		Map<String, Object> parameters=config.getParameters();
		String fullName=file+parameters.toString();
		Report report=CacheUtils.getReport(fullName);
		if (report == null) {
			ReportDefinition reportDefinition=reportRender.getReportDefinition(file);
			report = reportRender.render(reportDefinition, parameters);
		}
		excelProducer.produceWithPaging(report, config.getOutputStream());
	}
	@Override
	public void exportExcel97WithPaging(ExportConfigure config) {
		String file=config.getFile();
		Map<String, Object> parameters=config.getParameters();
		String fullName=file+parameters.toString();
		Report report=CacheUtils.getReport(fullName);
		if (report == null) {
			ReportDefinition reportDefinition=reportRender.getReportDefinition(file);
			report = reportRender.render(reportDefinition, parameters);
		}
		excel97Producer.produceWithPaging(report, config.getOutputStream());
	}
	
	@Override
	public void exportExcelWithPagingSheet(ExportConfigure config) {
		String file=config.getFile();
		Map<String, Object> parameters=config.getParameters();
		String fullName=file+parameters.toString();
		Report report=CacheUtils.getReport(fullName);
		if (report == null) {
			ReportDefinition reportDefinition=reportRender.getReportDefinition(file);
			report = reportRender.render(reportDefinition, parameters);
		}
		excelProducer.produceWithSheet(report, config.getOutputStream());
	}
	
	@Override
	public void exportExcel97WithPagingSheet(ExportConfigure config) {
		String file=config.getFile();
		Map<String, Object> parameters=config.getParameters();
		String fullName=file+parameters.toString();
		Report report=CacheUtils.getReport(fullName);
		if (report == null) {
			ReportDefinition reportDefinition=reportRender.getReportDefinition(file);
			report = reportRender.render(reportDefinition, parameters);
		}
		excel97Producer.produceWithSheet(report, config.getOutputStream());
	}
	
	public void setReportRender(ReportRender reportRender) {
		this.reportRender = reportRender;
	}
}
