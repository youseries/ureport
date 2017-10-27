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
package com.bstek.ureport.console.res;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.bstek.ureport.console.ServletAction;
import com.bstek.ureport.console.UReportServlet;

/**
 * @author Jacky.gao
 * @since 2016年6月6日
 */
public class ResourceLoaderServletAction implements ServletAction,ApplicationContextAware{
	public static final String URL="/res";
	private ApplicationContext applicationContext;
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path=req.getContextPath()+UReportServlet.URL+URL;
		String uri=req.getRequestURI();
		String resPath=uri.substring(path.length()+1);
		String p="classpath:"+resPath;
		if(p.endsWith(".js")){
			resp.setContentType("text/javascript");	
		}else if(p.endsWith(".css")){
			resp.setContentType("text/css");
		}else if(p.endsWith(".png")){
			resp.setContentType("image/png");
		}else if(p.endsWith(".jpg")){
			resp.setContentType("image/jpeg");
		}else if(p.endsWith(".svg")){
			resp.setContentType("image/svg+xml");			
		}else{
			resp.setContentType("application/octet-stream");
		}
		InputStream input=applicationContext.getResource(p).getInputStream();
		OutputStream output=resp.getOutputStream();
		try{
			IOUtils.copy(input, output);			
		}finally{
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
	}
	
	@Override
	public String url() {
		return URL;
	}
}
