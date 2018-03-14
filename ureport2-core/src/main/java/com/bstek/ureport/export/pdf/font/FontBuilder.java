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
package com.bstek.ureport.export.pdf.font;

import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.exception.ReportException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.BaseFont;

/**
 * @author Jacky.gao
 * @since 2014年4月22日
 */
public class FontBuilder implements ApplicationContextAware{
	private static ApplicationContext applicationContext;
	private static final Map<String,BaseFont> fontMap=new HashMap<String,BaseFont>();
	public static final Map<String,String> fontPathMap=new HashMap<String,String>();
	private static List<String> systemFontNameList=new ArrayList<String>();
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
	
	public static java.awt.Font getAwtFont(String fontName,int fontStyle,float size){
		if(systemFontNameList.contains(fontName)){
			return new java.awt.Font(fontName,fontStyle,new Float(size).intValue());
		}
		String fontPath=fontPathMap.get(fontName);
		if(fontPath==null){
			fontName="宋体";
			fontPath=fontPathMap.get(fontName);
			if(fontPath==null){
				return null;				
			}
		}
		InputStream inputStream=null;
		try {
			inputStream=applicationContext.getResource(fontPath).getInputStream();
			java.awt.Font font=java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, inputStream);
			return font.deriveFont(fontStyle,size);
		} catch (Exception e) {
			throw new ReportException(e);
		}finally{
			IOUtils.closeQuietly(inputStream);
		}
	}
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		FontBuilder.applicationContext=applicationContext;
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fontNames=environment.getAvailableFontFamilyNames();
		for(String name:fontNames){
			systemFontNameList.add(name);
		}
		Collection<FontRegister> fontRegisters=applicationContext.getBeansOfType(FontRegister.class).values();
		for(FontRegister fontReg:fontRegisters){
			String fontName=fontReg.getFontName();
			String fontPath=fontReg.getFontPath();
			if(StringUtils.isEmpty(fontPath) || StringUtils.isEmpty(fontName)){
				continue;
			}
			try {
				BaseFont baseFont=getIdentityFont(fontName,fontPath,applicationContext);
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
	
	private BaseFont getIdentityFont(String fontFamily,String fontPath,ApplicationContext applicationContext) throws DocumentException,IOException {
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
			fontPathMap.put(fontFamily, fontPath);
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
