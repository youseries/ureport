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
package com.bstek.ureport.provider.report.classpath;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import com.bstek.ureport.exception.ReportException;
import com.bstek.ureport.provider.report.ReportFile;
import com.bstek.ureport.provider.report.ReportProvider;

/**
 * @author Jacky.gao
 * @since 2016年12月4日
 */
public class ClasspathReportProvider implements ReportProvider,ApplicationContextAware{
	private ApplicationContext applicationContext;
	@Override
	public InputStream loadReport(String file) {
		Resource resource=applicationContext.getResource(file);
		try {
			return resource.getInputStream();
		} catch (IOException e) {
			String newFileName=null;
			if(file.startsWith("classpath:")){
				newFileName="classpath*:"+file.substring(10,file.length());
			}else if(file.startsWith("classpath*:")){
				newFileName="classpath:"+file.substring(11,file.length());
			}
			if(newFileName!=null){
				try{
					return applicationContext.getResource(file).getInputStream();					
				}catch(IOException ex){
					throw new ReportException(e);
				}				
			}
			throw new ReportException(e);
		}
	}
	
	@Override
	public String getPrefix() {
		return "classpath";
	}
	
	@Override
	public void deleteReport(String file) {
	}
	
	@Override
	public void saveReport(String file,String content) {
	}
	@Override
	public List<ReportFile> getReportFiles() {
		return null;
	}
	
	@Override
	public boolean disabled() {
		return false;
	}
	
	@Override
	public String getName() {
		return null;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
	}
}
