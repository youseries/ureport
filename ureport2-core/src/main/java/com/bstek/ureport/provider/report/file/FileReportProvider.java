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
package com.bstek.ureport.provider.report.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.WebApplicationContext;

import com.bstek.ureport.exception.ReportException;
import com.bstek.ureport.provider.report.ReportFile;
import com.bstek.ureport.provider.report.ReportProvider;

/**
 * @author Jacky.gao
 * @since 2017年2月11日
 */
public class FileReportProvider implements ReportProvider,ApplicationContextAware{
	private String prefix="file:";
	private String fileStoreDir;
	private boolean disabled;
	@Override
	public InputStream loadReport(String file) {
		if(file.startsWith(prefix)){
			file=file.substring(prefix.length(),file.length());
		}
		String fullPath=fileStoreDir+"/"+file;
		try {
			return new FileInputStream(fullPath);
		} catch (FileNotFoundException e) {
			throw new ReportException(e);
		}
	}
	
	@Override
	public void deleteReport(String file) {
		if(file.startsWith(prefix)){
			file=file.substring(prefix.length(),file.length());
		}
		String fullPath=fileStoreDir+"/"+file;
		File f=new File(fullPath);
		if(f.exists()){
			f.delete();
		}
	}

	@Override
	public List<ReportFile> getReportFiles() {
		File file=new File(fileStoreDir);
		List<ReportFile> list=new ArrayList<ReportFile>();
		for(File f:file.listFiles()){
			Calendar calendar=Calendar.getInstance();
			calendar.setTimeInMillis(f.lastModified());
			list.add(new ReportFile(f.getName(),calendar.getTime()));
		}
		Collections.sort(list, new Comparator<ReportFile>(){
			@Override
			public int compare(ReportFile f1, ReportFile f2) {
				return f2.getUpdateDate().compareTo(f1.getUpdateDate());
			}
		});
		return list;
	}

	@Override
	public String getName() {
		return "服务器文件系统";
	}
	
	@Override
	public void saveReport(String file,String content) {
		if(file.startsWith(prefix)){
			file=file.substring(prefix.length(),file.length());
		}
		String fullPath=fileStoreDir+"/"+file;
		FileOutputStream outStream=null;
		try{
			outStream=new FileOutputStream(new File(fullPath));
			IOUtils.write(content, outStream,"utf-8");
		}catch(Exception ex){
			throw new ReportException(ex);
		}finally{
			if(outStream!=null){
				try {
					outStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public boolean disabled() {
		return disabled;
	}
	
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
	public void setFileStoreDir(String fileStoreDir) {
		this.fileStoreDir = fileStoreDir;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		File file=new File(fileStoreDir);
		if(file.exists()){
			return;
		}
		if(applicationContext instanceof WebApplicationContext){
			WebApplicationContext context=(WebApplicationContext)applicationContext;
			ServletContext servletContext=context.getServletContext();
			String basePath=servletContext.getRealPath("/");
			fileStoreDir=basePath+fileStoreDir;
			file=new File(fileStoreDir);
			if(!file.exists()){
				file.mkdirs();
			}
		}
	}

	@Override
	public String getPrefix() {
		return prefix;
	}
}
