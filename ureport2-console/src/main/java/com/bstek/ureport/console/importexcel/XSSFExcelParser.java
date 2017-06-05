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
package com.bstek.ureport.console.importexcel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bstek.ureport.definition.Alignment;
import com.bstek.ureport.definition.Border;
import com.bstek.ureport.definition.CellDefinition;
import com.bstek.ureport.definition.CellStyle;
import com.bstek.ureport.definition.ColumnDefinition;
import com.bstek.ureport.definition.Orientation;
import com.bstek.ureport.definition.PagingMode;
import com.bstek.ureport.definition.Paper;
import com.bstek.ureport.definition.PaperSize;
import com.bstek.ureport.definition.PaperType;
import com.bstek.ureport.definition.ReportDefinition;
import com.bstek.ureport.definition.RowDefinition;
import com.bstek.ureport.definition.value.SimpleValue;

/**
 * @author Jacky.gao
 * @since 2017年5月27日
 */
public class XSSFExcelParser extends ExcelParser {
	@Override
	public ReportDefinition parse(InputStream inputStream) throws Exception {
		ReportDefinition report=new ReportDefinition();
		List<RowDefinition> rowDefs=new ArrayList<RowDefinition>();
		report.setRows(rowDefs);
		List<ColumnDefinition> columnDefs=new ArrayList<ColumnDefinition>();
		report.setColumns(columnDefs);
		List<CellDefinition> cellDefs=new ArrayList<CellDefinition>();
		report.setCells(cellDefs);
		XSSFWorkbook book=new XSSFWorkbook(inputStream);
		XSSFSheet sheet=book.getSheetAt(0);
		int firstRow=0;
		int rowCount=sheet.getPhysicalNumberOfRows();
		int maxColumnCount=buildMaxColumn(sheet);
		for(int i=firstRow;i<rowCount;i++){
			XSSFRow row=sheet.getRow(i);
			if(row==null){
				RowDefinition rowDef=new RowDefinition();
				rowDef.setHeight(20);
				rowDef.setRowNumber(i+1);
				rowDefs.add(rowDef);
				addBlankCells(cellDefs, i+1, maxColumnCount);
				continue;
			}
			RowDefinition rowDef=new RowDefinition();
			int height=row.getHeight()/20;
			rowDef.setHeight(height);
			rowDef.setRowNumber(i+1);
			rowDefs.add(rowDef);
			for(int j=0;j<maxColumnCount;j++){
				boolean isMergeRegion=isMergedRegion(sheet, i, j);
				if(isMergeRegion){
					continue;
				}
				XSSFCell cell=row.getCell(j);
				if(cell==null){
					CellDefinition cellDef=new CellDefinition();
					cellDef.setValue(new SimpleValue(""));
					cellDef.setRowNumber(i+1);
					cellDef.setColumnNumber(j+1);
					cellDefs.add(cellDef);
					continue;
				}
				Span span=getSpan(sheet, i, j);
				
				Object value=null;
				CellType cellType=cell.getCellTypeEnum();
				switch(cellType){
				case STRING:
					value=cell.getStringCellValue();
					break;
				case BOOLEAN:
					value=cell.getBooleanCellValue();
					break;
				case NUMERIC:
					value=cell.getNumericCellValue();
					break;
				case FORMULA:
					value=cell.getCellFormula();
					break;
				default:
					value="";
				}
				CellDefinition cellDef=new CellDefinition();
				cellDef.setValue(new SimpleValue(value !=null ? value.toString() : ""));
				cellDef.setRowNumber(i+1);
				cellDef.setColumnNumber(j+1);
				cellDef.setRowSpan(span.getRowSpan());
				cellDef.setColSpan(span.getColSpan());
				cellDef.setCellStyle(buildCellStyle(cell,book));
				cellDefs.add(cellDef);
			}
		}
		for(int i=0;i<maxColumnCount;i++){
			ColumnDefinition col=new ColumnDefinition();
			int width=sheet.getColumnWidth(i);
			col.setWidth(width/37);
			col.setColumnNumber(i+1);
			columnDefs.add(col);
		}
		book.close();
		inputStream.close();
		Paper paper=new Paper();
		paper.setPaperType(PaperType.A4);
		paper.setOrientation(Orientation.portrait);
		paper.setPagingMode(PagingMode.fitpage);
		PaperSize pageSize=PaperType.A4.getPaperSize();
		paper.setWidth(pageSize.getWidth());
		paper.setHeight(paper.getHeight());
		report.setPaper(paper);
		return report;
	}

	private CellStyle buildCellStyle(XSSFCell cell,XSSFWorkbook book){
		CellStyle style=new CellStyle();
		XSSFCellStyle cellStyle=cell.getCellStyle();
		HorizontalAlignment align=cellStyle.getAlignmentEnum();
		if(align.equals(HorizontalAlignment.CENTER)){
			style.setAlign(Alignment.center);
		}else if(align.equals(HorizontalAlignment.RIGHT)){
			style.setAlign(Alignment.right);
		}else{
			style.setAlign(Alignment.left);
		}
		VerticalAlignment valign=cellStyle.getVerticalAlignmentEnum();
		if(valign.equals(VerticalAlignment.BOTTOM)){
			style.setValign(Alignment.bottom);
		}else if(valign.equals(VerticalAlignment.TOP)){
			style.setValign(Alignment.top);
		}else if(valign.equals(VerticalAlignment.CENTER)){
			style.setValign(Alignment.middle);
		}else{
			style.setValign(Alignment.middle);
		}
		XSSFFont font=cellStyle.getFont();
		if(font.getBold()){
			style.setBold(true);
		}
		if(font.getItalic()){
			style.setItalic(true);
		}
		if(font.getUnderline()!=Font.U_NONE){
			style.setUnderline(true);
		}
		XSSFColor color=font.getXSSFColor();
		if(color!=null){
			String rgb=color.getARGBHex();
			style.setForecolor(hex2Rgb(rgb));
		}else{
			style.setForecolor("0,0,0");			
		}
		FillPatternType pattern=cellStyle.getFillPatternEnum();
		if(pattern!=null && pattern.equals(FillPatternType.SOLID_FOREGROUND)){
			XSSFColor bgcolor=cellStyle.getFillForegroundColorColor();
			if(bgcolor!=null){
				String hex=bgcolor.getARGBHex();
				style.setBgcolor(hex2Rgb(hex));					
			}
		}
		int fontSize=font.getFontHeight()/20;
		style.setFontSize(fontSize);
		BorderStyle borderStyle=cellStyle.getBorderLeftEnum();
		if(!borderStyle.equals(BorderStyle.NONE)){
			Border border=new Border();
			border.setColor("0,0,0");
			border.setStyle(com.bstek.ureport.definition.BorderStyle.solid);
			border.setWidth(1);
			style.setLeftBorder(border);
		}
		borderStyle=cellStyle.getBorderRightEnum();
		if(!borderStyle.equals(BorderStyle.NONE)){
			Border border=new Border();
			border.setColor("0,0,0");
			border.setStyle(com.bstek.ureport.definition.BorderStyle.solid);
			border.setWidth(1);
			style.setRightBorder(border);
		}
		borderStyle=cellStyle.getBorderTopEnum();
		if(!borderStyle.equals(BorderStyle.NONE)){
			Border border=new Border();
			border.setColor("0,0,0");
			border.setStyle(com.bstek.ureport.definition.BorderStyle.solid);
			border.setWidth(1);
			style.setTopBorder(border);
		}
		borderStyle=cellStyle.getBorderBottomEnum();
		if(!borderStyle.equals(BorderStyle.NONE)){
			Border border=new Border();
			border.setColor("0,0,0");
			border.setStyle(com.bstek.ureport.definition.BorderStyle.solid);
			border.setWidth(1);
			style.setBottomBorder(border);
		}
		return style;
	}

	private  String hex2Rgb(String colorStr) {
		return Integer.valueOf(colorStr.substring( 2, 4 ),16 )+","+
				Integer.valueOf( colorStr.substring( 4, 6 ), 16 )+","+
	            Integer.valueOf( colorStr.substring( 6, 8 ), 16 );
	}
	
	private Span getSpan(XSSFSheet sheet,int row ,int column){
		int sheetMergeCount = sheet.getNumMergedRegions(); 
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			if(row == firstRow && column==firstColumn){  
				int lastRow = range.getLastRow();
				int rowSpan=lastRow-firstRow;
				if(rowSpan>0){
					rowSpan++;
				}
				int colSpan=lastColumn-firstColumn;
				if(colSpan>0){
					colSpan++;
				}
				return new Span(rowSpan,colSpan);
			}
		}
		return new Span(0,0);
	}

	private boolean isMergedRegion(XSSFSheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row > firstRow && row < lastRow) {
				if (column > firstColumn && column < lastColumn) {
					return true;
				}
			}
		}
		return false;
	}
	
	private int buildMaxColumn(XSSFSheet sheet){
		int rowCount=sheet.getPhysicalNumberOfRows();
		int maxColumnCount=0;
		for(int i=0;i<rowCount;i++){
			XSSFRow row=sheet.getRow(i);
			if(row==null){
				continue;
			}
			int columnCount=row.getPhysicalNumberOfCells();
			if(columnCount>maxColumnCount){
				maxColumnCount=columnCount;
			}
		}
		return maxColumnCount;
	}
	
	protected void addBlankCells(List<CellDefinition> cellDefs,int rowNumber,int totalColumn){
		for(int i=0;i<totalColumn;i++){
			CellDefinition cellDef=new CellDefinition();
			cellDef.setValue(new SimpleValue(""));
			cellDef.setRowNumber(rowNumber);
			cellDef.setColumnNumber(i+1);
			cellDefs.add(cellDef);
		}
	}
	
	@Override
	public boolean support(String name) {
		return name.toLowerCase().endsWith(".xlsx");
	}
}
