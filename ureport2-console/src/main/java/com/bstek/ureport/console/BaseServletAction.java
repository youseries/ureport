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
package com.bstek.ureport.console;

import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;


/**
 * @author Jacky.gao
 * @since 2016年6月3日
 */
public abstract class BaseServletAction implements ServletAction {
	protected Throwable buildRootException(Throwable throwable){
		if(throwable.getCause()==null){
			return throwable;
		}
		return buildRootException(throwable.getCause());
	}

	protected String decode(String value){
		if(value==null){
			return value;
		}
		try{
			value=URLDecoder.decode(value, "utf-8");
			value=URLDecoder.decode(value, "utf-8");
			return value;
		}catch(Exception ex){
			return value;
		}
	}
	protected String decodeContent(String content){
		if(content==null){
			return content;
		}
		try{
			content=URLDecoder.decode(content, "utf-8");
			return content;
		}catch(Exception ex){
			return content;
		}
	}
	
	protected Map<String, Object> buildParameters(HttpServletRequest req) {
		Map<String,Object> parameters=new HashMap<String,Object>();
		Enumeration<?> enumeration=req.getParameterNames();
		while(enumeration.hasMoreElements()){
			Object obj=enumeration.nextElement();
			if(obj==null){
				continue;
			}
			String name=obj.toString();
			String value=req.getParameter(name);
			if(name==null || value==null || name.startsWith("_")){
				continue;
			}
			parameters.put(name, decode(value));
		}
		return parameters;
	}
	
	protected void invokeMethod(String methodName,HttpServletRequest req,HttpServletResponse resp) throws ServletException{
		try{
			Method method=this.getClass().getMethod(methodName, new Class<?>[]{HttpServletRequest.class,HttpServletResponse.class});			
			method.invoke(this, new Object[]{req,resp});
		}catch(Exception ex){
			throw new ServletException(ex);
		}
	}
	
	protected String retriveMethod(HttpServletRequest req) throws ServletException{
		String path=req.getContextPath()+UReportServlet.URL;
		String uri=req.getRequestURI();
		String targetUrl=uri.substring(path.length());
		int slashPos=targetUrl.indexOf("/",1);
		if(slashPos>-1){
			String methodName=targetUrl.substring(slashPos+1).trim();
			return methodName.length()>0 ? methodName : null;
		}
		return null;
	}
	
	protected String buildDownloadFileName(String reportFileName,String fileName,String extName){
		if(StringUtils.isNotBlank(fileName)){
			fileName=decode(fileName);
			if(!fileName.toLowerCase().endsWith(extName)){
				fileName=fileName+extName;
			}
			return fileName;
		}else{
			int pos=reportFileName.indexOf(":");
			if(pos>0){
				reportFileName=reportFileName.substring(pos+1,reportFileName.length());
			}
			pos=reportFileName.toLowerCase().indexOf(".ureport.xml");
			if(pos>0){
				reportFileName=reportFileName.substring(0,pos);
			}
			return "ureport-"+reportFileName+extName;
		}
	}
}
