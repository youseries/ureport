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
package com.bstek.ureport.console.importexcel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.bstek.ureport.console.RenderPageServletAction;
import com.bstek.ureport.console.cache.TempObjectCache;
import com.bstek.ureport.definition.ReportDefinition;

/**
 * @author Jacky.gao
 * @since 2017年5月25日
 */
public class ImportExcelServletAction extends RenderPageServletAction {
	private List<ExcelParser> excelParsers=new ArrayList<ExcelParser>();
	public ImportExcelServletAction(){
		excelParsers.add(new HSSFExcelParser());
		excelParsers.add(new XSSFExcelParser());
	}
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String tempDir=System.getProperty("java.io.tmpdir");
		FileItemFactory factory=new DiskFileItemFactory(1000240,new File(tempDir));
		ServletFileUpload upload=new ServletFileUpload(factory);
		ReportDefinition report=null;
		String errorInfo=null;
		try {
			List<FileItem> items=upload.parseRequest(req);
			for(FileItem item:items){
				String fieldName=item.getFieldName();
				String name=item.getName().toLowerCase();
				if(fieldName.equals("_excel_file") && (name.endsWith(".xls") || name.endsWith(".xlsx"))){
					InputStream inputStream=item.getInputStream();
					for(ExcelParser parser:excelParsers){
						if(parser.support(name)){
							report=parser.parse(inputStream);
							break;
						}
					}
					inputStream.close();
					break;
				}
			}
			errorInfo="请选择一个合法的Excel导入";
		} catch (Exception e) {
			e.printStackTrace();
			errorInfo=e.getMessage();
		}
		Map<String,Object> result=new HashMap<String,Object>();
		if(report!=null){
			result.put("result", true);
			TempObjectCache.putObject("classpath:template/template.ureport.xml", report);
		}else{
			result.put("result", false);
			if(errorInfo!=null){
				result.put("errorInfo", errorInfo);
			}
		}
		writeObjectToJson(resp, result);
	}
	
	@Override
	public String url() {
		return "/import";
	}
}
