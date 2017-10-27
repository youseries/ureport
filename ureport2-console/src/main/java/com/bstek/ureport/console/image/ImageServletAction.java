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
package com.bstek.ureport.console.image;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.bstek.ureport.cache.ResourceCache;
import com.bstek.ureport.console.ServletAction;

/**
 * @author Jacky.gao
 * @since 2016年6月6日
 */
public class ImageServletAction implements ServletAction{
	public static final String URL="/image";
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String key=req.getParameter("_key");
		if(StringUtils.isNotBlank(key)){
			byte[] bytes=(byte[])ResourceCache.getObject(key);
			InputStream input=new ByteArrayInputStream(bytes);
			OutputStream output=resp.getOutputStream();
			resp.setContentType("image/png");
			try{
				IOUtils.copy(input, output);			
			}finally{
				IOUtils.closeQuietly(input);
				IOUtils.closeQuietly(output);
			}
		}else{
			//processImage(req, resp);			
		}
	}

	@Override
	public String url() {
		return URL;
	}
}
