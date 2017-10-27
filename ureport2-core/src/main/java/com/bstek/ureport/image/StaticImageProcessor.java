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
package com.bstek.ureport.image;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.logging.Logger;

import org.springframework.context.ApplicationContext;

import com.bstek.ureport.Utils;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.provider.image.ImageProvider;

/**
 * @author Jacky.gao
 * @since 2017年3月20日
 */
public class StaticImageProcessor implements ImageProcessor<String> {
	private Logger log=Logger.getGlobal();
	@Override
	public InputStream getImage(String path) {
		Collection<ImageProvider> imageProviders=Utils.getImageProviders();
		ImageProvider targetImageProvider=null;
		for(ImageProvider provider:imageProviders){
			if(provider.support(path)){
				targetImageProvider=provider;
				break;
			}
		}
		if(targetImageProvider==null){
			throw new ReportComputeException("Unsupport image path :"+path);
		}
		try{
			InputStream inputStream=targetImageProvider.getImage(path);
			return inputStream;			
		}catch(Exception ex){
			ApplicationContext applicationContext=Utils.getApplicationContext();
			log.warning("Image ["+path+"] not exist,use default picture.");
			String imageNotExistPath="classpath:com/bstek/ureport/image/image-not-exist.jpg";
			try {
				return applicationContext.getResource(imageNotExistPath).getInputStream();
			} catch (IOException e1) {
				throw new ReportComputeException(e1);
			}
		}
	}
}
