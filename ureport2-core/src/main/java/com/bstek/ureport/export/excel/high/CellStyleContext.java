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
package com.bstek.ureport.export.excel.high;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;

import com.bstek.ureport.definition.Alignment;
import com.bstek.ureport.definition.Border;
import com.bstek.ureport.definition.CellStyle;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2014年11月3日
 */
public class CellStyleContext {
	private Map<String,XSSFCellStyle> styleMap=new HashMap<String,XSSFCellStyle>();
	public XSSFCellStyle produceXSSFCellStyle(SXSSFWorkbook wb,Cell cell){
		String name=cell.getName();
		if(cell.getCustomCellStyle()==null && cell.getRow().getCustomCellStyle()==null){
			if(styleMap.containsKey(name)){
				return styleMap.get(name);
			}else{
				XSSFCellStyle cellStyle = createNewCellStyle(wb,cell);
				styleMap.put(name, cellStyle);
				return cellStyle;
			}
		}else{
			String styleId=name+"=>"+buildStyleId(cell);
			if(styleMap.containsKey(styleId)){
				return styleMap.get(styleId);
			}else{
				XSSFCellStyle cellStyle = createNewCellStyle(wb,cell);
				styleMap.put(styleId, cellStyle);
				return cellStyle;
			}
		}
	}
	
	private XSSFCellStyle createNewCellStyle(SXSSFWorkbook wb,Cell cell) {
		CellStyle cellStyle=cell.getCellStyle();
		CellStyle customStyle=cell.getCustomCellStyle();
		CellStyle rowStyle=cell.getRow().getCustomCellStyle();
		CellStyle colStyle=cell.getColumn().getCustomCellStyle();
		XSSFCellStyle style=(XSSFCellStyle)wb.createCellStyle();
		style.setWrapText(true);
		String bgcolor=cellStyle.getBgcolor();
		if(customStyle!=null && StringUtils.isNotBlank(customStyle.getBgcolor())){
			bgcolor=customStyle.getBgcolor();
		}
		if(rowStyle!=null && StringUtils.isNotBlank(rowStyle.getBgcolor())){
			bgcolor=rowStyle.getBgcolor();
		}
		if(colStyle!=null && StringUtils.isNotBlank(colStyle.getBgcolor())){
			bgcolor=colStyle.getBgcolor();
		}
		if(StringUtils.isNotEmpty(bgcolor)){
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			style.setFillForegroundColor(buildXSSFColor(bgcolor));
		}
		Border topBorder=cellStyle.getTopBorder();
		if(customStyle!=null && customStyle.getTopBorder()!=null){
			topBorder=customStyle.getTopBorder();
		}
		if(topBorder!=null){
			BorderStyle borderStyle=getBorderStyle(topBorder);
			XSSFColor borderColor=buildXSSFColor(topBorder.getColor());
			style.setTopBorderColor(borderColor);
			style.setBorderTop(borderStyle);        				
		}
		Border bottomBorder=cellStyle.getBottomBorder();
		if(customStyle!=null && customStyle.getBottomBorder()!=null){
			bottomBorder=customStyle.getBottomBorder();
		}
		if(bottomBorder!=null){
			BorderStyle borderStyle=getBorderStyle(bottomBorder);
			XSSFColor borderColor=buildXSSFColor(bottomBorder.getColor());
			style.setBottomBorderColor(borderColor);
			style.setBorderBottom(borderStyle);        				
		}
		Border leftBorder=cellStyle.getLeftBorder();
		if(customStyle!=null && customStyle.getLeftBorder()!=null){
			leftBorder=customStyle.getLeftBorder();
		}
		if(leftBorder!=null){
			BorderStyle borderStyle=getBorderStyle(leftBorder);
			XSSFColor borderColor=buildXSSFColor(leftBorder.getColor());
			style.setLeftBorderColor(borderColor);
			style.setBorderLeft(borderStyle);        				
		}
		Border rightBorder=cellStyle.getRightBorder();
		if(customStyle!=null && customStyle.getRightBorder()!=null){
			rightBorder=customStyle.getRightBorder();
		}
		if(rightBorder!=null){
			BorderStyle borderStyle=getBorderStyle(rightBorder);
			XSSFColor borderColor=buildXSSFColor(rightBorder.getColor());
			style.setRightBorderColor(borderColor);
			style.setBorderRight(borderStyle);        				
		}
		Alignment align=cellStyle.getAlign();
		if(customStyle!=null && customStyle.getAlign()!=null){
			align=customStyle.getAlign();
		}
		if(rowStyle!=null && rowStyle.getAlign()!=null){
			align=rowStyle.getAlign();
		}
		if(colStyle!=null && colStyle.getAlign()!=null){
			align=colStyle.getAlign();
		}
		if(align!=null){
			if(align.equals(Alignment.left)){
				style.setAlignment(HorizontalAlignment.LEFT);
			}else if(align.equals(Alignment.center)){
				style.setAlignment(HorizontalAlignment.CENTER);
			}else if(align.equals(Alignment.right)){
				style.setAlignment(HorizontalAlignment.RIGHT);        				
			}
		}
		Alignment valign=cellStyle.getValign();
		if(customStyle!=null && customStyle.getValign()!=null){
			valign=customStyle.getValign();
		}
		if(rowStyle!=null && rowStyle.getValign()!=null){
			valign=rowStyle.getValign();
		}
		if(colStyle!=null && colStyle.getValign()!=null){
			valign=colStyle.getValign();
		}
		if(valign!=null){
			if(valign.equals(Alignment.top)){
				style.setVerticalAlignment(VerticalAlignment.TOP);
			}else if(valign.equals(Alignment.middle)){
				style.setVerticalAlignment(VerticalAlignment.CENTER);
			}else if(valign.equals(Alignment.bottom)){
				style.setVerticalAlignment(VerticalAlignment.BOTTOM);
			}
		}
		XSSFFont font=(XSSFFont)wb.createFont();
		int fontSize=cellStyle.getFontSize();
		if(customStyle!=null && customStyle.getFontSize()>0){
			fontSize=customStyle.getFontSize();
		}
		if(rowStyle!=null && rowStyle.getFontSize()>0){
			fontSize=rowStyle.getFontSize();
		}
		if(colStyle!=null && colStyle.getFontSize()>0){
			fontSize=colStyle.getFontSize();
		}
		font.setFontHeight(fontSize);
		Boolean bold=cellStyle.getBold();
		if(customStyle!=null && customStyle.getBold()!=null){
			bold=customStyle.getBold();
		}
		if(rowStyle!=null && rowStyle.getBold()!=null){
			bold=rowStyle.getBold();
		}
		if(colStyle!=null && colStyle.getBold()!=null){
			bold=colStyle.getBold();
		}
		if(bold!=null){
			font.setBold(bold);			
		}
		Boolean italic=cellStyle.getItalic();
		if(customStyle!=null && customStyle.getItalic()!=null){
			italic=customStyle.getItalic();
		}
		if(rowStyle!=null && rowStyle.getItalic()!=null){
			italic=rowStyle.getItalic();
		}
		if(colStyle!=null && colStyle.getItalic()!=null){
			italic=colStyle.getItalic();
		}
		if(italic!=null){
			font.setItalic(italic);			
		}
		Boolean underline=cellStyle.getUnderline();
		if(customStyle!=null && customStyle.getUnderline()!=null){
			underline=customStyle.getUnderline();
		}
		if(rowStyle!=null && rowStyle.getUnderline()!=null){
			underline=rowStyle.getUnderline();
		}
		if(colStyle!=null && colStyle.getUnderline()!=null){
			underline=colStyle.getUnderline();
		}
		if(underline!=null && underline){
			font.setUnderline((byte)1);						
		}
		String forecolor=cellStyle.getForecolor();
		if(customStyle!=null && customStyle.getForecolor()!=null){
			forecolor=customStyle.getForecolor();
		}
		if(rowStyle!=null && rowStyle.getForecolor()!=null){
			forecolor=rowStyle.getForecolor();
		}
		if(colStyle!=null && colStyle.getForecolor()!=null){
			forecolor=colStyle.getForecolor();
		}
		if(StringUtils.isEmpty(forecolor)){
			forecolor="0,0,0";
		}
		font.setColor(buildXSSFColor(forecolor));
		String fontFamily=cellStyle.getFontFamily();
		if(customStyle!=null && customStyle.getFontFamily()!=null){
			fontFamily=customStyle.getFontFamily();
		}
		if(rowStyle!=null && rowStyle.getFontFamily()!=null){
			fontFamily=rowStyle.getFontFamily();
		}
		if(colStyle!=null && colStyle.getFontFamily()!=null){
			fontFamily=colStyle.getFontFamily();
		}
		if(fontFamily!=null){
			font.setFontName(fontFamily);			
		}
		style.setFont(font);
		return style;
	}
	
	private BorderStyle getBorderStyle(Border border){
		if(border.getStyle().equals(com.bstek.ureport.definition.BorderStyle.solid)){
			return BorderStyle.THIN;
		}else if(border.getStyle().equals(com.bstek.ureport.definition.BorderStyle.dashed)){
			return BorderStyle.DASHED;
		}else if(border.getStyle().equals(com.bstek.ureport.definition.BorderStyle.doublesolid)){
			return BorderStyle.DOUBLE;
		}
		return null;
	}
	
	private XSSFColor buildXSSFColor(String colorStr){
		String[] color=colorStr.split(",");
		Color c=new Color(Integer.valueOf(color[0]),Integer.valueOf(color[1]),Integer.valueOf(color[2]));
		XSSFColor xssfColor=new XSSFColor(c);
		return xssfColor;
	}
	
	private String buildStyleId(Cell cell){
		CellStyle customStyle=cell.getCustomCellStyle();
		CellStyle rowStyle=cell.getRow().getCustomCellStyle();
		CellStyle colStyle=cell.getColumn().getCustomCellStyle();
		StringBuffer sb=new StringBuffer();
		String bgcolor=customStyle!=null ? customStyle.getBgcolor() : null;
		if(rowStyle!=null && StringUtils.isNotBlank(rowStyle.getBgcolor())){
			bgcolor=rowStyle.getBgcolor();
		}
		if(colStyle!=null && StringUtils.isNotBlank(colStyle.getBgcolor())){
			bgcolor=colStyle.getBgcolor();
		}
		if(StringUtils.isNotEmpty(bgcolor)){
			sb.append("bgcolor:"+bgcolor);
		}
		Border topBorder=customStyle!=null ? customStyle.getTopBorder() : null;
		if(topBorder!=null){
			sb.append("topborder:"+buildBorderStyleId(topBorder));
		}
		Border bottomBorder=customStyle!=null ? customStyle.getBottomBorder() : null;
		if(bottomBorder!=null){
			sb.append("bottomborder:"+buildBorderStyleId(bottomBorder));
		}
		Border leftBorder=customStyle!=null ? customStyle.getLeftBorder() : null;
		if(leftBorder!=null){
			sb.append("leftborder:"+buildBorderStyleId(leftBorder));  				
		}
		Border rightBorder=customStyle!=null ? customStyle.getRightBorder() : null;
		if(rightBorder!=null){
			sb.append("rightborder:"+buildBorderStyleId(rightBorder));
		}
		Alignment align=customStyle!=null ? customStyle.getAlign() : null;
		if(rowStyle!=null && rowStyle.getAlign()!=null){
			align=rowStyle.getAlign();
		}
		if(colStyle!=null && colStyle.getAlign()!=null){
			align=colStyle.getAlign();
		}
		if(align!=null){
			if(align.equals(Alignment.left)){
				sb.append("align:"+HorizontalAlignment.LEFT.name());
			}else if(align.equals(Alignment.center)){
				sb.append("align:"+HorizontalAlignment.CENTER.name());
			}else if(align.equals(Alignment.right)){
				sb.append("align:"+HorizontalAlignment.RIGHT.name());
			}
		}
		Alignment valign=customStyle!=null ? customStyle.getValign() : null;
		if(rowStyle!=null && rowStyle.getValign()!=null){
			valign=rowStyle.getValign();
		}
		if(colStyle!=null && colStyle.getValign()!=null){
			valign=colStyle.getValign();
		}
		if(valign!=null){
			if(valign.equals(Alignment.top)){
				sb.append("valign:"+VerticalAlignment.TOP.name());
			}else if(valign.equals(Alignment.middle)){
				sb.append("valign:"+VerticalAlignment.CENTER.name());
			}else if(valign.equals(Alignment.bottom)){
				sb.append("valign:"+VerticalAlignment.BOTTOM.name());
			}
		}
		Boolean underline=customStyle!=null ? customStyle.getUnderline() : null;
		if(rowStyle!=null && rowStyle.getUnderline()!=null){
			underline=rowStyle.getUnderline();
		}
		if(colStyle!=null && colStyle.getUnderline()!=null){
			underline=colStyle.getUnderline();
		}
		sb.append("underline:"+underline);
		Boolean bold=customStyle!=null ? customStyle.getBold() : null;
		if(rowStyle!=null && rowStyle.getBold()!=null){
			bold=rowStyle.getBold();
		}
		if(colStyle!=null && colStyle.getBold()!=null){
			bold=colStyle.getBold();
		}
		sb.append("bold:"+bold);
		Boolean italic=customStyle!=null ? customStyle.getItalic() : null;
		if(rowStyle!=null && rowStyle.getItalic()!=null){
			italic=rowStyle.getItalic();
		}
		if(colStyle!=null && colStyle.getItalic()!=null){
			italic=colStyle.getItalic();
		}
		sb.append("italic:"+italic);	
		String forecolor=customStyle!=null ? customStyle.getForecolor() : null;
		if(rowStyle!=null && rowStyle.getForecolor()!=null){
			forecolor=rowStyle.getForecolor();
		}
		if(colStyle!=null && colStyle.getForecolor()!=null){
			forecolor=colStyle.getForecolor();
		}
		sb.append("forecolor:"+forecolor);
		String font=customStyle!=null ? customStyle.getFontFamily() : null;
		if(rowStyle!=null && rowStyle.getFontFamily()!=null){
			font=rowStyle.getFontFamily();
		}
		if(colStyle!=null && colStyle.getFontFamily()!=null){
			font=colStyle.getFontFamily();
		}
		sb.append("font:"+font);
		int fontSize=customStyle!=null ? customStyle.getFontSize() : 0;
		if(rowStyle!=null && rowStyle.getFontSize()>0){
			fontSize=rowStyle.getFontSize();
		}
		if(colStyle!=null && colStyle.getFontSize()>0){
			fontSize=colStyle.getFontSize();
		}
		sb.append("font-size:"+fontSize);
		return sb.toString();
	}
	
	private String buildBorderStyleId(Border border){
		StringBuilder sb=new StringBuilder();
		if(border.getStyle()!=null){
			sb.append("border-style:"+border.getStyle().name());
		}
		sb.append("border-color:"+border.getColor());
		sb.append("border-width:"+border.getWidth());
		return sb.toString();
	}
}
