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
package com.bstek.ureport.export.pdf;

import org.apache.commons.lang.StringUtils;

import com.bstek.ureport.definition.CellStyle;
import com.bstek.ureport.export.pdf.font.FontBuilder;
import com.bstek.ureport.model.Cell;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;

/**
 * @author Jacky.gao
 * @since 2014年4月17日
 */
public class CellPhrase extends Phrase {
	private static final long serialVersionUID = 8712267867853876619L;
	public CellPhrase(Cell cell,Object cellData){
		String text="";
		if(cellData!=null){
			text=cellData.toString();
		}
		Font font=buildPdfFont(cell);
		setFont(font);
		add(text);			
	}
	
	private Font buildPdfFont(Cell cell){
		CellStyle style=cell.getCellStyle();
		CellStyle customStyle=cell.getCustomCellStyle();
		CellStyle rowStyle=cell.getRow().getCustomCellStyle();
		CellStyle colStyle=cell.getColumn().getCustomCellStyle();
		String fontName=style.getFontFamily();
		if(customStyle!=null && StringUtils.isNotBlank(customStyle.getFontFamily())){
			fontName=customStyle.getFontFamily();
		}
		if(rowStyle!=null && StringUtils.isNotBlank(rowStyle.getFontFamily())){
			fontName=rowStyle.getFontFamily();
		}
		if(colStyle!=null && StringUtils.isNotBlank(colStyle.getFontFamily())){
			fontName=colStyle.getFontFamily();
		}
		int fontSize=style.getFontSize();
		Boolean bold=style.getBold(),italic=style.getItalic(),underline=style.getUnderline();
		if(customStyle!=null){
			if(customStyle.getBold()!=null){
				bold=customStyle.getBold();
			}
			if(customStyle.getItalic()!=null){
				italic=customStyle.getItalic();
			}
			if(customStyle.getUnderline()!=null){
				underline=customStyle.getUnderline();
			}
			if(customStyle.getFontSize()>0){
				fontSize=customStyle.getFontSize();
			}
		}
		if(rowStyle!=null){
			if(rowStyle.getBold()!=null){
				bold=rowStyle.getBold();
			}
			if(rowStyle.getItalic()!=null){
				italic=rowStyle.getItalic();
			}
			if(rowStyle.getUnderline()!=null){
				underline=rowStyle.getUnderline();
			}
			if(rowStyle.getFontSize()>0){
				fontSize=rowStyle.getFontSize();
			}
		}
		if(colStyle!=null){
			if(colStyle.getBold()!=null){
				bold=colStyle.getBold();
			}
			if(colStyle.getItalic()!=null){
				italic=colStyle.getItalic();
			}
			if(colStyle.getUnderline()!=null){
				underline=colStyle.getUnderline();
			}
			if(colStyle.getFontSize()>0){
				fontSize=colStyle.getFontSize();
			}
		}
		if(bold==null)bold=false;
		if(italic==null)italic=false;
		if(underline==null)underline=false;
		if(StringUtils.isBlank(fontName)){
			fontName="宋体";
		}
		Font font=FontBuilder.getFont(fontName, fontSize,bold,italic,underline);
		String fontColor=style.getForecolor();
		if(customStyle!=null && StringUtils.isNotBlank(customStyle.getForecolor())){
			fontColor=customStyle.getForecolor();
		}
		if(rowStyle!=null && StringUtils.isNotBlank(rowStyle.getForecolor())){
			fontColor=rowStyle.getForecolor();
		}
		if(colStyle!=null && StringUtils.isNotBlank(colStyle.getForecolor())){
			fontColor=colStyle.getForecolor();
		}
		if(StringUtils.isNotEmpty(fontColor)){
			String[] color=fontColor.split(",");
			font.setColor(Integer.valueOf(color[0]), Integer.valueOf(color[1]), Integer.valueOf(color[2]));			
		}
		return font;
	}
}
