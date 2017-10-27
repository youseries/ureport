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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.bstek.ureport.console.exception.ReportDesignException;

/**
 * @author Jacky.gao
 * @since 2017年1月26日
 */
public class ReportUtils {
	public static String decodeFileName(String fileName){
		if(fileName==null){
			return fileName;
		}
		try {
			fileName=URLDecoder.decode(fileName, "utf-8");
			fileName=URLDecoder.decode(fileName, "utf-8");
			return fileName;
		} catch (UnsupportedEncodingException e) {
			throw new ReportDesignException(e);
		}
	}
}
