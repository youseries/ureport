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
package com.bstek.ureport.export.excel.high.builder;

import java.util.List;

import org.apache.poi.ss.usermodel.PaperSize;
import org.apache.poi.ss.usermodel.PrintOrientation;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;

import com.bstek.ureport.definition.Orientation;
import com.bstek.ureport.definition.Paper;
import com.bstek.ureport.definition.PaperType;
import com.bstek.ureport.model.Column;
import com.bstek.ureport.model.Image;
import com.bstek.ureport.model.Row;
import com.bstek.ureport.utils.UnitUtils;

/**
 * @author Jacky.gao
 * @since 2017年8月10日
 */
public abstract class ExcelBuilder {
	
	protected int getWholeWidth(List<Column> columns,int colNumber,int colSpan){
		Column col=columns.get(colNumber);
		int start=colNumber+1,end=colNumber+colSpan;
		int w=col.getWidth();
		for(int i=start;i<end;i++){
			Column c=columns.get(i);
			w+=c.getWidth();
		}
		w=UnitUtils.pointToPixel(w);
		return w;
	}
	
	protected int getWholeHeight(List<Row> rows,int rowNumber,int rowSpan){
		Row row=rows.get(rowNumber);
		int start=rowNumber+1,end=rowNumber+rowSpan;
		int h=row.getRealHeight();
		for(int i=start;i<end;i++){
			Row r=rows.get(i);
			h+=r.getRealHeight();
		}
		h=UnitUtils.pointToPixel(h);
		return h;
	}
	
	protected Sheet createSheet(SXSSFWorkbook wb,Paper paper,String name){
		Sheet sheet = null;
		if(name==null){
			sheet=wb.createSheet();
		}else{			
			sheet=wb.createSheet(name);
		}
		PaperType paperType=paper.getPaperType();
		XSSFPrintSetup printSetup=(XSSFPrintSetup)sheet.getPrintSetup();
		Orientation orientation=paper.getOrientation();
		if(orientation.equals(Orientation.landscape)){
			printSetup.setOrientation(PrintOrientation.LANDSCAPE);			
		}
		setupPaper(paperType, printSetup);
		int leftMargin=paper.getLeftMargin();
		int rightMargin=paper.getRightMargin();
		int topMargin=paper.getTopMargin();
		int bottomMargin=paper.getBottomMargin();
		sheet.setMargin(Sheet.LeftMargin, UnitUtils.pointToInche(leftMargin));
		sheet.setMargin(Sheet.RightMargin, UnitUtils.pointToInche(rightMargin));
		sheet.setMargin(Sheet.TopMargin, UnitUtils.pointToInche(topMargin));
		sheet.setMargin(Sheet.BottomMargin, UnitUtils.pointToInche(bottomMargin));
		return sheet;
	}

	protected int buildImageFormat(Image img){
		int type=Workbook.PICTURE_TYPE_PNG;
		String path=img.getPath();
		if(path==null){
			return type;
		}
		path=path.toLowerCase();
		if(path.endsWith("jpg") || path.endsWith("jpeg")){
			type=Workbook.PICTURE_TYPE_JPEG;
		}
		return type;
	}

	protected boolean setupPaper(PaperType paperType, XSSFPrintSetup printSetup) {
		boolean setup=false;
		switch(paperType){
			case A0:
				printSetup.setPaperSize(PaperSize.A4_PAPER);
				break;
			case A1:
				printSetup.setPaperSize(PaperSize.A4_PAPER);
				break;
			case A2:
				printSetup.setPaperSize(PaperSize.A4_PAPER);
				break;
			case A3:				
				printSetup.setPaperSize(PaperSize.A3_PAPER);
				setup=true;
				break;
			case A4:
				printSetup.setPaperSize(PaperSize.A4_PAPER);
				setup=true;
				break;
			case A5:
				printSetup.setPaperSize(PaperSize.A5_PAPER);
				setup=true;
				break;
			case A6:
				printSetup.setPaperSize(PaperSize.A4_PAPER);
				break;
			case A7:
				printSetup.setPaperSize(PaperSize.A4_PAPER);
				break;
			case A8:
				printSetup.setPaperSize(PaperSize.A4_PAPER);
				break;
			case A9:
				printSetup.setPaperSize(PaperSize.A4_PAPER);
				break;
			case A10:
				printSetup.setPaperSize(PaperSize.A4_PAPER);
				break;
			case B0:
				printSetup.setPaperSize(PaperSize.A4_PAPER);
				break;
			case B1:
				printSetup.setPaperSize(PaperSize.A4_PAPER);
				break;
			case B2:
				printSetup.setPaperSize(PaperSize.A4_PAPER);
				break;
			case B3:
				printSetup.setPaperSize(PaperSize.A4_PAPER);
				break;
			case B4:
				printSetup.setPaperSize(PaperSize.B4_PAPER);
				setup=true;
				break;
			case B5:
				printSetup.setPaperSize(PaperSize.B4_PAPER);
				setup=true;
				break;
			case B6:
				printSetup.setPaperSize(PaperSize.A4_PAPER);
				break;
			case B7:
				printSetup.setPaperSize(PaperSize.A4_PAPER);
				break;
			case B8:
				printSetup.setPaperSize(PaperSize.A4_PAPER);
				break;
			case B9:
				printSetup.setPaperSize(PaperSize.A4_PAPER);
				break;
			case B10:
				printSetup.setPaperSize(PaperSize.A4_PAPER);
				break;
			case CUSTOM:
				printSetup.setPaperSize(PaperSize.A4_PAPER);
				break;
		}
		return setup;
	}
}
