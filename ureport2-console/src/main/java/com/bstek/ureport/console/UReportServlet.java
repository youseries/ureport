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
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author Jacky.gao
 * @since 2017年1月25日
 */
public class UReportServlet extends HttpServlet {
	private static final long serialVersionUID = 533049461276487971L;
	public static final String URL = "/ureport";
	private Map<String, ServletAction> actionMap = new HashMap<String, ServletAction>();

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
		Collection<ServletAction> handlers = applicationContext.getBeansOfType(ServletAction.class).values();
		for (ServletAction handler : handlers) {
			String url = handler.url();
			if (actionMap.containsKey(url)) {
				throw new RuntimeException("Handler [" + url + "] already exist.");
			}
			actionMap.put(url, handler);
		}
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getContextPath() + URL;
		String uri = req.getRequestURI();
		String targetUrl = uri.substring(path.length());
		if (targetUrl.length() < 1) {
			outContent(resp, "Welcome to use ureport,please specify target url.");
			return;
		}
		int slashPos = targetUrl.indexOf("/", 1);
		if (slashPos > -1) {
			targetUrl = targetUrl.substring(0, slashPos);
		}
		ServletAction targetHandler = actionMap.get(targetUrl);
		if (targetHandler == null) {
			outContent(resp, "Handler [" + targetUrl + "] not exist.");
			return;
		}
		RequestHolder.setRequest(req);
		try{
			targetHandler.execute(req, resp);
		}finally{
			RequestHolder.clean();
		}
	}

	private void outContent(HttpServletResponse resp, String msg) throws IOException {
		resp.setContentType("text/html");
		PrintWriter pw = resp.getWriter();
		pw.write("<html>");
		pw.write("<header><title>UReport Console</title></header>");
		pw.write("<body>");
		pw.write(msg);
		pw.write("</body>");
		pw.write("</html>");
		pw.flush();
		pw.close();
	}
}
