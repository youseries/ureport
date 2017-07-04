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
package com.bstek.ureport.export.word.high;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFldChar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STFldCharType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHdrFtr;

import com.bstek.ureport.build.paging.HeaderFooter;
import com.bstek.ureport.model.Report;

/**
 * @author Jacky.gao
 * @since 2017年4月17日
 */
public class HeaderFooterBuilder {
	public void build(XWPFDocument document,CTSectPr sectPr,Report report){
		//HeaderFooterDefinition headerDef=report.getHeader();
		//HeaderFooterDefinition footerDef=report.getFooter();
		
		HeaderFooter header=new HeaderFooter();
		HeaderFooter footer=new HeaderFooter();
		XWPFHeaderFooterPolicy headerFooterPolicy=null;
		if(header!=null){
			List<XWPFParagraph> list=buildXWPFParagraph(header, document);
			XWPFParagraph[] newparagraphs = new XWPFParagraph[list.size()];
			list.toArray(newparagraphs);
			headerFooterPolicy = new XWPFHeaderFooterPolicy(document, sectPr);									
			headerFooterPolicy.createHeader(STHdrFtr.DEFAULT, newparagraphs);
		}
		if(footer!=null){
			List<XWPFParagraph> list=buildXWPFParagraph(footer, document);
			XWPFParagraph[] newparagraphs = new XWPFParagraph[list.size()];
			list.toArray(newparagraphs);
			/*if(headerFooterPolicy==null){
				headerFooterPolicy = new XWPFHeaderFooterPolicy(document, sectPr);
			}*/
			headerFooterPolicy.createFooter(STHdrFtr.DEFAULT, newparagraphs);
		}
	}
	
	private List<XWPFParagraph> buildXWPFParagraph(HeaderFooter hf,XWPFDocument document){
		CTP ctp = null;
		List<XWPFParagraph> paras=new ArrayList<XWPFParagraph>();
		XWPFParagraph para=null;		
		XWPFRun r1 = null;
		String left=hf.getLeft();
		String center=hf.getCenter();
		String right=hf.getRight();
		SimpleDateFormat dateSD=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeSD=new SimpleDateFormat("HH:MM:ss");
		Date D=new Date();
		String date=dateSD.format(D);
		String time=timeSD.format(D);
		if(StringUtils.isNotBlank(left)){
			ctp = CTP.Factory.newInstance();				
			para=new XWPFParagraph(ctp, document);
			para.setAlignment(ParagraphAlignment.LEFT);
			paras.add(para);
			List<String> list=splitHeaderFooterContent(left);
			for(int i=0;i<list.size();i++){
				String text=list.get(i);
				if(text.equals("$[PAGE]")){
					r1 = para.createRun();
				    CTFldChar fldChar = r1.getCTR().addNewFldChar();
				    fldChar.setFldCharType(STFldCharType.Enum.forString("begin"));
					CTText ctText = r1.getCTR().addNewInstrText();
					ctText.setStringValue("PAGE  \\* MERGEFORMAT");
					ctText.setSpace(SpaceAttribute.Space.Enum.forString("preserve"));
					if(hf.getFontSize()>1){
						r1.setFontSize(hf.getFontSize());
					}
					CTRPr rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
					CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
					String fontName=hf.getFontFamily();
					if(fontName!=null){
						fonts.setAscii(fontName);
						fonts.setEastAsia(fontName);
						fonts.setHAnsi(fontName);
					}
					if(hf.isBold()){
						r1.setBold(true);
					}
					if(hf.isItalic()){
						r1.setItalic(true);
					}
					fldChar = r1.getCTR().addNewFldChar();
					fldChar.setFldCharType(STFldCharType.Enum.forString("end"));					
				}else if(text.equals("$[PAGES]")){
					r1 = para.createRun();
				    CTFldChar fldChar = r1.getCTR().addNewFldChar();
				    fldChar.setFldCharType(STFldCharType.Enum.forString("begin"));
					CTText ctText = r1.getCTR().addNewInstrText();
					ctText.setStringValue("NUMPAGES  \\* MERGEFORMAT ");
					ctText.setSpace(SpaceAttribute.Space.Enum.forString("preserve"));
					if(hf.getFontSize()>1){
						r1.setFontSize(hf.getFontSize());
					}
					CTRPr rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
					CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
					String fontName=hf.getFontFamily();
					if(fontName!=null){
						fonts.setAscii(fontName);
						fonts.setEastAsia(fontName);
						fonts.setHAnsi(fontName);
					}
					if(hf.isBold()){
						r1.setBold(true);
					}
					if(hf.isItalic()){
						r1.setItalic(true);
					}
					fldChar = r1.getCTR().addNewFldChar();
					fldChar.setFldCharType(STFldCharType.Enum.forString("end"));
				}else if(text.equals("$[DATE]")){
					r1 = para.createRun();
					r1.setText(date);
					if(hf.getFontSize()>1){
						r1.setFontSize(hf.getFontSize());
					}
					CTRPr rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
					CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
					String fontName=hf.getFontFamily();
					if(fontName!=null){
						fonts.setAscii(fontName);
						fonts.setEastAsia(fontName);
						fonts.setHAnsi(fontName);
					}
					if(hf.isBold()){
						r1.setBold(true);
					}
					if(hf.isItalic()){
						r1.setItalic(true);
					}
				}else if(text.equals("$[TIME]")){
					r1 = para.createRun();
					r1.setText(time);
					if(hf.getFontSize()>1){
						r1.setFontSize(hf.getFontSize());
					}
					CTRPr rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
					CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
					String fontName=hf.getFontFamily();
					if(fontName!=null){
						fonts.setAscii(fontName);
						fonts.setEastAsia(fontName);
						fonts.setHAnsi(fontName);
					}
					if(hf.isBold()){
						r1.setBold(true);
					}
					if(hf.isItalic()){
						r1.setItalic(true);
					}
				}else{
					r1 = para.createRun();
					r1.setText(text);
					if(hf.getFontSize()>1){
						r1.setFontSize(hf.getFontSize());
					}
					CTRPr rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
					CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
					String fontName=hf.getFontFamily();
					if(fontName!=null){
						fonts.setAscii(fontName);
						fonts.setEastAsia(fontName);
						fonts.setHAnsi(fontName);
					}
					if(hf.isBold()){
						r1.setBold(true);
					}
					if(hf.isItalic()){
						r1.setItalic(true);
					}
				}
			}
		}
		if(StringUtils.isNotBlank(center)){
			ctp = CTP.Factory.newInstance();				
			para=new XWPFParagraph(ctp, document);
			para.setAlignment(ParagraphAlignment.CENTER);
			paras.add(para);
			List<String> list=splitHeaderFooterContent(center);
			for(int i=0;i<list.size();i++){
				String text=list.get(i);
				if(text.equals("$[PAGE]")){
					r1 = para.createRun();
				    CTFldChar fldChar = r1.getCTR().addNewFldChar();
				    fldChar.setFldCharType(STFldCharType.Enum.forString("begin"));
					CTText ctText = r1.getCTR().addNewInstrText();
					ctText.setStringValue("PAGE  \\* MERGEFORMAT");
					ctText.setSpace(SpaceAttribute.Space.Enum.forString("preserve"));
					if(hf.getFontSize()>1){
						r1.setFontSize(hf.getFontSize());
					}
					CTRPr rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
					CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
					String fontName=hf.getFontFamily();
					if(fontName!=null){
						fonts.setAscii(fontName);
						fonts.setEastAsia(fontName);
						fonts.setHAnsi(fontName);
					}
					if(hf.isBold()){
						r1.setBold(true);
					}
					if(hf.isItalic()){
						r1.setItalic(true);
					}
					fldChar = r1.getCTR().addNewFldChar();
					fldChar.setFldCharType(STFldCharType.Enum.forString("end"));					
				}else if(text.equals("$[PAGES]")){
					r1 = para.createRun();
				    CTFldChar fldChar = r1.getCTR().addNewFldChar();
				    fldChar.setFldCharType(STFldCharType.Enum.forString("begin"));
					CTText ctText = r1.getCTR().addNewInstrText();
					ctText.setStringValue("NUMPAGES  \\* MERGEFORMAT ");
					ctText.setSpace(SpaceAttribute.Space.Enum.forString("preserve"));
					if(hf.getFontSize()>1){
						r1.setFontSize(hf.getFontSize());
					}
					CTRPr rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
					CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
					String fontName=hf.getFontFamily();
					if(fontName!=null){
						fonts.setAscii(fontName);
						fonts.setEastAsia(fontName);
						fonts.setHAnsi(fontName);
					}
					if(hf.isBold()){
						r1.setBold(true);
					}
					if(hf.isItalic()){
						r1.setItalic(true);
					}
					fldChar = r1.getCTR().addNewFldChar();
					fldChar.setFldCharType(STFldCharType.Enum.forString("end"));
				}else if(text.equals("$[DATE]")){
					r1 = para.createRun();
					r1.setText(date);
					if(hf.getFontSize()>1){
						r1.setFontSize(hf.getFontSize());
					}
					CTRPr rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
					CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
					String fontName=hf.getFontFamily();
					if(fontName!=null){
						fonts.setAscii(fontName);
						fonts.setEastAsia(fontName);
						fonts.setHAnsi(fontName);
					}
					if(hf.isBold()){
						r1.setBold(true);
					}
					if(hf.isItalic()){
						r1.setItalic(true);
					}
				}else if(text.equals("$[TIME]")){
					r1 = para.createRun();
					r1.setText(time);
					if(hf.getFontSize()>1){
						r1.setFontSize(hf.getFontSize());
					}
					CTRPr rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
					CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
					String fontName=hf.getFontFamily();
					if(fontName!=null){
						fonts.setAscii(fontName);
						fonts.setEastAsia(fontName);
						fonts.setHAnsi(fontName);
					}
					if(hf.isBold()){
						r1.setBold(true);
					}
					if(hf.isItalic()){
						r1.setItalic(true);
					}
				}else{
					r1 = para.createRun();
					r1.setText(text);
					if(hf.getFontSize()>1){
						r1.setFontSize(hf.getFontSize());
					}
					CTRPr rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
					CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
					String fontName=hf.getFontFamily();
					if(fontName!=null){
						fonts.setAscii(fontName);
						fonts.setEastAsia(fontName);
						fonts.setHAnsi(fontName);
					}
					if(hf.isBold()){
						r1.setBold(true);
					}
					if(hf.isItalic()){
						r1.setItalic(true);
					}
				}
			}
		}
		if(StringUtils.isNotBlank(right)){
			ctp = CTP.Factory.newInstance();				
			para=new XWPFParagraph(ctp, document);
			para.setAlignment(ParagraphAlignment.RIGHT);
			paras.add(para);
			List<String> list=splitHeaderFooterContent(right);
			for(int i=0;i<list.size();i++){
				String text=list.get(i);
				if(text.equals("$[PAGE]")){
					r1 = para.createRun();
				    CTFldChar fldChar = r1.getCTR().addNewFldChar();
				    fldChar.setFldCharType(STFldCharType.Enum.forString("begin"));
					CTText ctText = r1.getCTR().addNewInstrText();
					ctText.setStringValue("PAGE  \\* MERGEFORMAT");
					ctText.setSpace(SpaceAttribute.Space.Enum.forString("preserve"));
					if(hf.getFontSize()>1){
						r1.setFontSize(hf.getFontSize());
					}
					CTRPr rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
					CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
					String fontName=hf.getFontFamily();
					if(fontName!=null){
						fonts.setAscii(fontName);
						fonts.setEastAsia(fontName);
						fonts.setHAnsi(fontName);
					}
					if(hf.isBold()){
						r1.setBold(true);
					}
					if(hf.isItalic()){
						r1.setItalic(true);
					}
					fldChar = r1.getCTR().addNewFldChar();
					fldChar.setFldCharType(STFldCharType.Enum.forString("end"));					
				}else if(text.equals("$[PAGES]")){
					r1 = para.createRun();
				    CTFldChar fldChar = r1.getCTR().addNewFldChar();
				    fldChar.setFldCharType(STFldCharType.Enum.forString("begin"));
					CTText ctText = r1.getCTR().addNewInstrText();
					ctText.setStringValue("NUMPAGES  \\* MERGEFORMAT ");
					ctText.setSpace(SpaceAttribute.Space.Enum.forString("preserve"));
					if(hf.getFontSize()>1){
						r1.setFontSize(hf.getFontSize());
					}
					CTRPr rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
					CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
					String fontName=hf.getFontFamily();
					if(fontName!=null){
						fonts.setAscii(fontName);
						fonts.setEastAsia(fontName);
						fonts.setHAnsi(fontName);
					}
					if(hf.isBold()){
						r1.setBold(true);
					}
					if(hf.isItalic()){
						r1.setItalic(true);
					}
					fldChar = r1.getCTR().addNewFldChar();
					fldChar.setFldCharType(STFldCharType.Enum.forString("end"));
				}else if(text.equals("$[DATE]")){
					r1 = para.createRun();
					r1.setText(date);
					if(hf.getFontSize()>1){
						r1.setFontSize(hf.getFontSize());
					}
					CTRPr rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
					CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
					String fontName=hf.getFontFamily();
					if(fontName!=null){
						fonts.setAscii(fontName);
						fonts.setEastAsia(fontName);
						fonts.setHAnsi(fontName);
					}
					if(hf.isBold()){
						r1.setBold(true);
					}
					if(hf.isItalic()){
						r1.setItalic(true);
					}
				}else if(text.equals("$[TIME]")){
					r1 = para.createRun();
					r1.setText(time);
					if(hf.getFontSize()>1){
						r1.setFontSize(hf.getFontSize());
					}
					CTRPr rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
					CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
					String fontName=hf.getFontFamily();
					if(fontName!=null){
						fonts.setAscii(fontName);
						fonts.setEastAsia(fontName);
						fonts.setHAnsi(fontName);
					}
					if(hf.isBold()){
						r1.setBold(true);
					}
					if(hf.isItalic()){
						r1.setItalic(true);
					}
				}else{
					r1 = para.createRun();
					r1.setText(text);
					if(hf.getFontSize()>1){
						r1.setFontSize(hf.getFontSize());
					}
					CTRPr rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
					CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
					String fontName=hf.getFontFamily();
					if(fontName!=null){
						fonts.setAscii(fontName);
						fonts.setEastAsia(fontName);
						fonts.setHAnsi(fontName);
					}
					if(hf.isBold()){
						r1.setBold(true);
					}
					if(hf.isItalic()){
						r1.setItalic(true);
					}
				}
			}
		}
		return paras;
	}
	
	private List<String> splitHeaderFooterContent(String info){
		Pattern pattern = Pattern.compile("\\$\\[PAGE\\]|\\$\\[PAGES\\]|\\$\\[DATE\\]|\\$\\[TIME\\]");
		Matcher matcher = pattern.matcher(info);
		List<String> list=new ArrayList<String>();
		int start=0;
		while (matcher.find()) {
			String text=matcher.group();
			int pos=info.indexOf(text);
			String str=info.substring(start,pos);
			start=pos+text.length();
			list.add(str);
			list.add(text);
		}
		if(start<info.length()){
			list.add(info.substring(start,info.length()));
		}
		return list;
	}
	
}
