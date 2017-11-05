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
