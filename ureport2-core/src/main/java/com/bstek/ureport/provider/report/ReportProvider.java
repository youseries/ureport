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
package com.bstek.ureport.provider.report;

import java.io.InputStream;
import java.util.List;

/**
 * @author Jacky.gao
 * @since 2016年12月4日
 */
public interface ReportProvider {
	/**
	 * 根据报表名加载报表文件
	 * @param file 报表名称
	 * @return 返回的InputStream
	 */
	InputStream loadReport(String file);
	/**
	 * 根据报表名，删除指定的报表文件
	 * @param file 报表名称
	 */
	void deleteReport(String file);
	/**
	 * 获取所有的报表文件
	 * @return 返回报表文件列表
	 */
	List<ReportFile> getReportFiles();
	/**
	 * 保存报表文件
	 * @param file 报表名称
	 * @param content 报表的XML内容
	 */
	void saveReport(String file,String content);
	/**
	 * @return 返回存储器名称
	 */
	String getName();
	/**
	 * @return 返回是否禁用
	 */
	boolean disabled();
	/**
	 * @return 返回报表文件名前缀
	 */
	String getPrefix();
}
