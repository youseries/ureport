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
package com.bstek.ureport.console.designer;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.bstek.ureport.console.RenderPageServletAction;

/**
 * @author Jacky.gao
 * @since 2017年10月24日
 */
public class SearchFormDesignerAction extends RenderPageServletAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		VelocityContext context = new VelocityContext();
		context.put("contextPath", req.getContextPath());
		resp.setContentType("text/html");
		resp.setCharacterEncoding("utf-8");
		Template template=ve.getTemplate("ureport-html/searchform.html","utf-8");
		PrintWriter writer=resp.getWriter();
		template.merge(context, writer);
		writer.close();
	}

	@Override
	public String url() {
		return "/searchFormDesigner";
	}
}
