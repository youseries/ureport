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
