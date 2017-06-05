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
package com.bstek.ureport.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.util.Base64Utils;

import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.image.ChartImageProcessor;
import com.bstek.ureport.image.ImageProcessor;
import com.bstek.ureport.image.ImageType;
import com.bstek.ureport.image.StaticImageProcessor;

/**
 * @author Jacky.gao
 * @since 2017年3月20日
 */
public class ImageUtils {
	private static Map<ImageType,ImageProcessor<?>> imageProcessorMap=new HashMap<ImageType,ImageProcessor<?>>();
	static{
		StaticImageProcessor staticImageProcessor=new StaticImageProcessor();
		imageProcessorMap.put(ImageType.image, staticImageProcessor);
		ChartImageProcessor chartImageProcessor=new ChartImageProcessor();
		imageProcessorMap.put(ImageType.chart, chartImageProcessor);
	}
	@SuppressWarnings("unchecked")
	public static InputStream getImage(ImageType type,Object data){
		ImageProcessor<Object> targetProcessor=(ImageProcessor<Object>)imageProcessorMap.get(type);
		if(targetProcessor==null){
			throw new ReportComputeException("Unknow image type :"+type);
		}
		return targetProcessor.getImage(data);
	}
	
	public static InputStream base64DataToInputStream(String base64Data){
		byte[] bytes=Base64Utils.decodeFromString(base64Data);
		ByteArrayInputStream inputStream=new ByteArrayInputStream(bytes);
		return inputStream;
	}
	
	@SuppressWarnings("unchecked")
	public static String getImageBase64Data(ImageType type,Object data){
		ImageProcessor<Object> targetProcessor=(ImageProcessor<Object>)imageProcessorMap.get(type);
		if(targetProcessor==null){
			throw new ReportComputeException("Unknow image type :"+type);
		}
		InputStream inputStream = targetProcessor.getImage(data);
		try{
			byte[] bytes=IOUtils.toByteArray(inputStream);
			return Base64Utils.encodeToString(bytes);
		}catch(Exception ex){
			throw new ReportComputeException(ex);
		}finally{
			IOUtils.closeQuietly(inputStream);
		}
	}
}
