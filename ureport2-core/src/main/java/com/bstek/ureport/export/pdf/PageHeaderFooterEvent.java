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

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.bstek.ureport.build.paging.HeaderFooter;
import com.bstek.ureport.build.paging.Page;
import com.bstek.ureport.definition.Orientation;
import com.bstek.ureport.definition.Paper;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.export.pdf.font.FontBuilder;
import com.bstek.ureport.model.Report;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author Jacky.gao
 * @since 2014年4月22日
 */
public class PageHeaderFooterEvent extends PdfPageEventHelper {
	private Report report;
	public PageHeaderFooterEvent(Report report) {
		this.report=report;
	}
	@Override
	public void onEndPage(PdfWriter writer, Document document) {
		List<Page> pages=report.getPages();
		int pageNumber=writer.getPageNumber();
		if(pageNumber>pages.size()){
			return;
		}
		Page page=pages.get(pageNumber-1);
		HeaderFooter header=page.getHeader();
		HeaderFooter footer=page.getFooter();
		if(header!=null){
			buildTable(writer,header,true,report);			
		}
		if(footer!=null){
			buildTable(writer,footer,false,report);						
		}
	}
	private void buildTable(PdfWriter writer,HeaderFooter hf,boolean header,Report report) {
		Paper paper=report.getPaper();
		int width=paper.getWidth();
		if(paper.getOrientation().equals(Orientation.landscape)){
			width=paper.getHeight();
		}
		int leftMargin=paper.getLeftMargin();
		int rightMargin=paper.getRightMargin();
		int tableWidth=width-leftMargin-rightMargin;
		int height=paper.getHeight();
		if(paper.getOrientation().equals(Orientation.landscape)){
			height=paper.getWidth();
		}
		int margin=hf.getMargin();
		int hfHeight=hf.getHeight();
		String left=hf.getLeft();
		String center=hf.getCenter();
		String right=hf.getRight();
		try {
			PdfPTable table=null;
			if(StringUtils.isNotEmpty(left)){
				if(StringUtils.isNotEmpty(center) && StringUtils.isNotEmpty(right)){
					table = new PdfPTable(3);
					table.setWidths(new int[]{1, 1, 1});
					table.addCell(buildPdfPCell(hf,left,1));
					table.addCell(buildPdfPCell(hf,center,2));
					table.addCell(buildPdfPCell(hf,right,3));
				}else if(StringUtils.isNotEmpty(center)){
					table = new PdfPTable(3);
					table.setWidths(new int[]{1, 1, 1});
					table.addCell(buildPdfPCell(hf,left,1));
					table.addCell(buildPdfPCell(hf,center,2));
					table.addCell(buildPdfPCell(hf,"",3));
				}else if(StringUtils.isNotEmpty(right)){
					table = new PdfPTable(3);
					table.setWidths(new int[]{1, 1, 1});
					table.addCell(buildPdfPCell(hf,left,1));
					table.addCell(buildPdfPCell(hf,"",2));
					table.addCell(buildPdfPCell(hf,right,3));
				}else{
					table = new PdfPTable(1);
					table.setWidths(new int[]{1});
					table.addCell(buildPdfPCell(hf,left,1));
				}
			}else if(StringUtils.isNotEmpty(center)){
				if(StringUtils.isNotEmpty(right)){
					table = new PdfPTable(3);
					table.setWidths(new int[]{1, 1, 1});
					table.addCell(buildPdfPCell(hf,"",1));
					table.addCell(buildPdfPCell(hf,center,2));
					table.addCell(buildPdfPCell(hf,right,3));
				}else{
					table = new PdfPTable(1);
					table.setWidths(new int[]{1});
					table.addCell(buildPdfPCell(hf,center,2));
				}
			}else if(StringUtils.isNotEmpty(right)){
				table = new PdfPTable(1);
				table.setWidths(new int[]{1});
				table.addCell(buildPdfPCell(hf,right,3));
			}
			if(table==null){
				return;
			}
			table.getDefaultCell().setFixedHeight(hfHeight);
			table.setTotalWidth(tableWidth);
			table.setLockedWidth(true);
		    if(header){
		    	int y=height-margin;
		    	table.writeSelectedRows(0, -1, leftMargin,y, writer.getDirectContent());
		    }else{
		    	table.writeSelectedRows(0, -1, leftMargin,margin+hfHeight, writer.getDirectContent());            	 
		    }
		}catch(DocumentException de) {
		   throw new ReportComputeException(de);
		}
	}
	private PdfPCell buildPdfPCell(HeaderFooter phf,String text,int type){
		PdfPCell cell=new PdfPCell();
		cell.setPadding(0);
		cell.setBorder(Rectangle.NO_BORDER);
		Font font=FontBuilder.getFont(phf.getFontFamily(), phf.getFontSize(), phf.isBold(), phf.isItalic(),phf.isUnderline());
		String fontColor=phf.getForecolor();
		if(StringUtils.isNotEmpty(fontColor)){
			String[] color=fontColor.split(",");
			font.setColor(Integer.valueOf(color[0]), Integer.valueOf(color[1]), Integer.valueOf(color[2]));			
		}
		Paragraph graph=new Paragraph(text,font);
		cell.setPhrase(graph);
		switch(type){
		case 1:
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			break;
		case 2:
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			break;
		case 3:
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			break;
		}
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		return cell;
	}
}
