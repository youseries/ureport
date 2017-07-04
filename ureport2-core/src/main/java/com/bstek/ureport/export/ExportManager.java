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

import java.util.Map;

import com.bstek.ureport.export.html.HtmlReport;

/**
 * @author Jacky.gao
 * @since 2016年12月4日
 */
public interface ExportManager {
	public static final String BEAN_ID="ureport.exportManager";
	/**
	 * 导出Html报表
	 * @param file 报表模版文件名
	 * @param contextPath 当前项目的context path
	 * @param parameters 参数
	 * @return 返回一个HtmlReport对象，里面有报表产生的HTML及相关CSS
	 */
	HtmlReport exportHtml(String file,String contextPath,Map<String, Object> parameters);
	/**
	 * 导出指定页码的Html报表
	 * @param file 报表模版文件名
	 * @param contextPath 当前项目的context path
	 * @param parameters 参数
	 * @param pageIndex 页码
	 * @return 返回一个HtmlReport对象，里面有报表产生的HTML及相关CSS
	 */
	HtmlReport exportHtml(String file,String contextPath,Map<String, Object> parameters,int pageIndex);
	/**
	 * 导出PDF报表
	 * @param config 包含报表模版文件名、参数等信息的配置对象
	 */
	void exportPdf(ExportConfigure config);
	/**
	 * 不分页导出Excel
	 * @param config 包含报表模版文件名、参数等信息的配置对象
	 */
	void exportExcel(ExportConfigure config);
	/**
	 * 不分页导出Excel97格式文件
	 * @param config 包含报表模版文件名、参数等信息的配置对象
	 */
	void exportExcel97(ExportConfigure config);
	/**
	 * 分页导出Excel
	 * @param config 包含报表模版文件名、参数等信息的配置对象
	 */
	void exportExcelWithPaging(ExportConfigure config);
	/**
	 * 分页导出Excel
	 * @param config 包含报表模版文件名、参数等信息的配置对象
	 */
	void exportExcel97WithPaging(ExportConfigure config);
	/**
	 * 分页分Sheet导出Excel
	 * @param config 包含报表模版文件名、参数等信息的配置对象
	 */
	void exportExcelWithPagingSheet(ExportConfigure config);
	/**
	 * 分页分Sheet导出Excel
	 * @param config 包含报表模版文件名、参数等信息的配置对象
	 */
	void exportExcel97WithPagingSheet(ExportConfigure config);
	/**
	 * 导出Word
	 * @param config 包含报表模版文件名、参数等信息的配置对象
	 */
	void exportWord(ExportConfigure config);
}
