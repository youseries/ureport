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
package com.bstek.ureport.provider.image;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

import com.bstek.ureport.exception.ReportComputeException;

/**
 * @author Jacky.gao
 * @since 2017年3月6日
 */
public class DefaultImageProvider implements ImageProvider,ApplicationContextAware {
	private ApplicationContext applicationContext;
	private String baseWebPath;
	@Override
	public InputStream getImage(String path) {
		try {
			if(path.startsWith(ResourceUtils.CLASSPATH_URL_PREFIX) || path.startsWith("/WEB-INF")){
				return applicationContext.getResource(path).getInputStream();				
			}else{
				path=baseWebPath+path;
				return new FileInputStream(path);
			}
		} catch (IOException e) {
			throw new ReportComputeException(e);
		}
	}

	@Override
	public boolean support(String path) {
		if(path.startsWith(ResourceUtils.CLASSPATH_URL_PREFIX)){
			return true;
		}else if(baseWebPath!=null && (path.startsWith("/") || path.startsWith("/WEB-INF"))){
			return true;
		}
		return false;
	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if(applicationContext instanceof WebApplicationContext){
			WebApplicationContext context=(WebApplicationContext)applicationContext;
			baseWebPath=context.getServletContext().getRealPath("/");
		}
		this.applicationContext=applicationContext;
	}
}
