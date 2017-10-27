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

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;


/**
 * @author Jacky.gao
 * @since 2016年5月23日
 */
public abstract class WriteJsonServletAction extends BaseServletAction{
	protected void writeObjectToJson(HttpServletResponse resp,Object obj) throws ServletException, IOException{
		resp.setContentType("text/json");
		resp.setCharacterEncoding("UTF-8");
		ObjectMapper mapper=new ObjectMapper();
		mapper.setSerializationInclusion(Inclusion.NON_NULL);
		mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,false);
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		OutputStream out = resp.getOutputStream();
		try {
			mapper.writeValue(out, obj);
		} finally {
			out.flush();
			out.close();
		}
	}
}
