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
