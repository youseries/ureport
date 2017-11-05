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
