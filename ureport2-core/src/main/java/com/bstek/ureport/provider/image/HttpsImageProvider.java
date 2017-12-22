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
package com.bstek.ureport.provider.image;

import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.bstek.ureport.exception.ReportException;

/**
 * @author Jacky.gao
 * @since 2017年12月22日
 */
public class HttpsImageProvider implements ImageProvider {

	@Override
	public InputStream getImage(String path) {
		try{
			URL url=new URL(path);
			HttpsURLConnection connection=(HttpsURLConnection)url.openConnection();
			connection.connect();
			InputStream inputStream=connection.getInputStream();
			return inputStream;
		}catch(Exception ex){
			throw new ReportException(ex);
		}
	}

	@Override
	public boolean support(String path) {
		return path.startsWith("https:");
	}

}
