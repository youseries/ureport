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
package com.bstek.ureport.console;

import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bstek.ureport.exception.ReportComputeException;


/**
 * @author Jacky.gao
 * @since 2016年6月3日
 */
public abstract class BaseServletAction implements ServletAction {
	
	protected String decode(String value){
		if(value==null){
			return value;
		}
		try{
			value=URLDecoder.decode(value, "utf-8");
			value=URLDecoder.decode(value, "utf-8");
			return value;
		}catch(Exception ex){
			throw new ReportComputeException(ex);
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
}
