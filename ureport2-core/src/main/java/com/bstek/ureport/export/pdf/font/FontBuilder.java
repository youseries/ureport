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
package com.bstek.ureport.export.pdf.font;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.bstek.ureport.exception.ReportComputeException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.BaseFont;

/**
 * @author Jacky.gao
 * @since 2014年4月22日
 */
public class FontBuilder implements ApplicationContextAware{
	private static final Map<String,BaseFont> fontMap=new HashMap<String,BaseFont>();
	public static Font getFont(String fontName,int fontSize,boolean fontBold,boolean fontItalic,boolean underLine){
		BaseFont baseFont=fontMap.get(fontName);
		Font font=null;
		if(baseFont!=null){
			font=new Font(baseFont);
		}else{
			font=FontFactory.getFont(fontName);
		}
		font.setSize(fontSize);
		int fontStyle=Font.NORMAL;
		if(fontBold && fontItalic && underLine){
			fontStyle=Font.BOLD|Font.ITALIC|Font.UNDERLINE;				
		}else if(fontBold){
			if(fontItalic){
				fontStyle=Font.BOLD|Font.ITALIC;
			}else if(underLine){
				fontStyle=Font.BOLD|Font.UNDERLINE;				
			}else{
				fontStyle=Font.BOLD;				
			}
		}else if(fontItalic){
			if(underLine){
				fontStyle=Font.ITALIC|Font.UNDERLINE;											
			}else if(fontBold){
				fontStyle=Font.ITALIC|Font.BOLD;															
			}else{
				fontStyle=Font.ITALIC;				
			}
		}else if(underLine){
			fontStyle=Font.UNDERLINE;
		}
		font.setStyle(fontStyle);
		return font;
	}
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Collection<FontRegister> fontRegisters=applicationContext.getBeansOfType(FontRegister.class).values();
		for(FontRegister fontReg:fontRegisters){
			String fontName=fontReg.getFontName();
			String fontPath=fontReg.getFontPath();
			if(StringUtils.isEmpty(fontPath) || StringUtils.isEmpty(fontName)){
				continue;
			}
			try {
				BaseFont baseFont=getIdentityFont(fontPath,applicationContext);
				if(baseFont==null){
					throw new ReportComputeException("Font " + fontPath + " does not exist");
				}
				fontMap.put(fontName, baseFont);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ReportComputeException(e);
			}
		}
	}
	
	private BaseFont getIdentityFont(String fontPath,ApplicationContext applicationContext) throws DocumentException,IOException {
		if(!fontPath.startsWith(ApplicationContext.CLASSPATH_URL_PREFIX)){
			fontPath=ApplicationContext.CLASSPATH_URL_PREFIX+fontPath;
		}
		String fontName = fontPath;
		int lastSlashPos=fontPath.lastIndexOf("/");
		if(lastSlashPos!=-1){
			fontName = fontPath.substring(lastSlashPos+1,fontPath.length());			
		}
		if (fontName.toLowerCase().endsWith(".ttc")) {
			fontName = fontName + ",0";
		}
		InputStream inputStream=null;
		try{
			inputStream=applicationContext.getResource(fontPath).getInputStream();
			byte[] bytes = IOUtils.toByteArray(inputStream);
			BaseFont baseFont = BaseFont.createFont(fontName, BaseFont.IDENTITY_H,BaseFont.EMBEDDED,true,bytes,null);
			baseFont.setSubset(true);
			return baseFont;			
		}finally{
			if(inputStream!=null)inputStream.close();
		}
	}
}
