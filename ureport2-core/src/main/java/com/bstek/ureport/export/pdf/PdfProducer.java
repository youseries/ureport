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

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.bstek.ureport.build.paging.Page;
import com.bstek.ureport.chart.ChartData;
import com.bstek.ureport.definition.Alignment;
import com.bstek.ureport.definition.CellStyle;
import com.bstek.ureport.definition.Orientation;
import com.bstek.ureport.definition.Paper;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.export.FullPageData;
import com.bstek.ureport.export.PageBuilder;
import com.bstek.ureport.export.Producer;
import com.bstek.ureport.model.Cell;
import com.bstek.ureport.model.Column;
import com.bstek.ureport.model.Image;
import com.bstek.ureport.model.Report;
import com.bstek.ureport.model.Row;
import com.bstek.ureport.utils.ImageUtils;
import com.bstek.ureport.utils.UnitUtils;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.RectangleReadOnly;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author Jacky.gao
 * @since 2017年3月10日
 */
public class PdfProducer implements Producer {
	@Override
	public void produce(Report report,OutputStream outputStream) {
		Paper paper=report.getPaper();
		int width=paper.getWidth();
		int height=paper.getHeight();
		Rectangle pageSize=new RectangleReadOnly(width,height);
		if(paper.getOrientation().equals(Orientation.landscape)){
			pageSize=pageSize.rotate();			
		}
		int leftMargin=paper.getLeftMargin();
		int rightMargin=paper.getRightMargin();
		int topMargin=paper.getTopMargin();
		int bottomMargin=paper.getBottomMargin();
		Document document=new Document(pageSize,leftMargin,rightMargin,topMargin,bottomMargin);
		try{
			PdfWriter writer=PdfWriter.getInstance(document,outputStream);
			PageHeaderFooterEvent headerFooterEvent=new PageHeaderFooterEvent(report);
			writer.setPageEvent(headerFooterEvent);
			document.open();
			List<Column> columns=report.getColumns();
			List<Integer> columnsWidthList=new ArrayList<Integer>();
			int[] intArr=buildColumnSizeAndTotalWidth(columns,columnsWidthList);
			int colSize=intArr[0],totalWidth=intArr[1];
			int[] columnsWidth=new int[columnsWidthList.size()];
			for(int i=0;i<columnsWidthList.size();i++){
				columnsWidth[i]=columnsWidthList.get(i);
			}
			FullPageData pageData=PageBuilder.buildFullPageData(report);
			List<List<Page>> list=pageData.getPageList();
			if(list.size()>0){
				int columnCount=paper.getColumnCount();
				int w=columnCount*totalWidth+(columnCount-1)*paper.getColumnMargin();
				int size=columnCount+(columnCount-1);
				int[] widths=new int[size];
				for(int i=0;i<size;i++){
					int mode=(i+1)%2;
					if(mode==0){
						widths[i]=paper.getColumnMargin();						
					}else{
						widths[i]=totalWidth;					
					}
				}
				float tableHeight=pageSize.getHeight()-paper.getTopMargin()-paper.getBottomMargin();
				Map<Row, Map<Column, Cell>> cellMap=report.getRowColCellMap();
				for(List<Page> pages:list){
					PdfPTable table=new PdfPTable(size);
					table.setLockedWidth(true);
					table.setTotalWidth(w);
					table.setWidths(widths);
					table.setHorizontalAlignment(Element.ALIGN_LEFT);
					int ps=pages.size();
					for(int i=0;i<ps;i++){
						if(i>0){
							PdfPCell pdfMarginCell=new PdfPCell();
							pdfMarginCell.setBorder(Rectangle.NO_BORDER);
							table.addCell(pdfMarginCell);
						}
						Page page=pages.get(i);
						
						PdfPTable childTable=new PdfPTable(colSize);
						childTable.setLockedWidth(true);
						childTable.setTotalWidth(totalWidth);
						childTable.setWidths(columnsWidth);
						childTable.setHorizontalAlignment(Element.ALIGN_LEFT);
						List<Row> rows=page.getRows();
						for(Row row:rows){
							Map<Column,Cell> colMap=cellMap.get(row);
							if(colMap==null){
								continue;
							}
							for(Column col:columns){
								if(col.getWidth()<1){
									continue;
								}
								Cell cell=colMap.get(col);
								if(cell==null){
									continue;
								}
								int cellHeight=buildCellHeight(cell,rows);
								PdfPCell pdfcell=buildPdfPCell(cell,cellHeight);
								childTable.addCell(pdfcell);
							}
						}
						float childTableHeight=childTable.calculateHeights();
						if(tableHeight>childTableHeight){
							for(int j=0;j<columns.size();j++){
								PdfPCell lastCell=new PdfPCell();
								lastCell.setBorder(Rectangle.NO_BORDER);
								childTable.addCell(lastCell);
							}
						}
						PdfPCell pdfContainerCell=new PdfPCell(childTable);
						pdfContainerCell.setBorder(Rectangle.NO_BORDER);
						table.addCell(pdfContainerCell);
					}
					if(ps<columnCount){
						int left=columnCount-ps;
						for(int i=0;i<left;i++){
							PdfPCell pdfMarginCell=new PdfPCell();
							pdfMarginCell.setBorder(Rectangle.NO_BORDER);
							table.addCell(pdfMarginCell);
							pdfMarginCell=new PdfPCell();
							pdfMarginCell.setBorder(Rectangle.NO_BORDER);
							table.addCell(pdfMarginCell);
						}
					}
					document.add(table);
					document.newPage();
				}
				
			}else{
				List<Page> pages=report.getPages();
				Map<Row, Map<Column, Cell>> cellMap=report.getRowColCellMap();
				for(Page page:pages){
					PdfPTable table=new PdfPTable(colSize);
					table.setLockedWidth(true);
					table.setTotalWidth(totalWidth);
					table.setWidths(columnsWidth);
					table.setHorizontalAlignment(Element.ALIGN_LEFT);
					List<Row> rows=page.getRows();
					for(Row row:rows){
						Map<Column,Cell> colMap=cellMap.get(row);
						if(colMap==null){
							continue;
						}
						for(Column col:columns){
							if(col.getWidth()<1){
								continue;
							}
							Cell cell=colMap.get(col);
							if(cell==null){
								continue;
							}
							int cellHeight=buildCellHeight(cell,rows);
							PdfPCell pdfcell=buildPdfPCell(cell,cellHeight);
							table.addCell(pdfcell);
						}
					}
					document.add(table);
					document.newPage();
				}
			}
			document.close();
		}catch(Exception ex){
			throw new ReportComputeException(ex);
		}
	}

	
	private int buildCellHeight(Cell cell,List<Row> rows){
		int height=cell.getRow().getRealHeight();
		int rowSpan=cell.getPageRowSpan();
		if(rowSpan>0){
			int pos=rows.indexOf(cell.getRow());
			int start=pos+1,end=start+rowSpan-1;
			for(int i=start;i<end;i++){
				height+=rows.get(i).getRealHeight();
			}
		}
		return height;
	}
	
	private PdfPCell buildPdfPCell(Cell cellInfo,int cellHeight) throws Exception{
		CellStyle style=cellInfo.getCellStyle(); 
		CellStyle customStyle=cellInfo.getCustomCellStyle();
		CellStyle rowStyle=cellInfo.getRow().getCustomCellStyle();
		CellStyle colStyle=cellInfo.getColumn().getCustomCellStyle();
		PdfPCell cell=newPdfCell(cellInfo,cellHeight);
		cell.setPadding(0);
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.setCellEvent(new CellBorderEvent(style,customStyle));
		int rowSpan=cellInfo.getPageRowSpan();
		if(rowSpan>0){
			cell.setRowspan(rowSpan);
		}
		int colSpan=cellInfo.getColSpan();
		if(colSpan>0){
			cell.setColspan(colSpan);
		}
		Alignment align=style.getAlign();
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
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			}else if(align.equals(Alignment.center)){
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			}else if(align.equals(Alignment.right)){
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			}
		}
		Alignment valign=style.getValign();
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
				cell.setVerticalAlignment(Element.ALIGN_TOP);
			}else if(valign.equals(Alignment.middle)){
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			}else if(valign.equals(Alignment.bottom)){
				cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			}
		}
		String bgcolor=style.getBgcolor();
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
			String[] colors=bgcolor.split(",");
			cell.setBackgroundColor(new BaseColor(Integer.valueOf(colors[0]),Integer.valueOf(colors[1]),Integer.valueOf(colors[2])));
		}
		return cell;
	}
	
	private int[] buildColumnSizeAndTotalWidth(List<Column> columns,List<Integer> list){
		int count=0,totalWidth=0;
		for(int i=0;i<columns.size();i++){
			Column col=columns.get(i);
			int width=col.getWidth();
			if(width<1){
				continue;
			}
			count++;
			list.add(width);
			totalWidth+=width;
		}
		return new int[]{count,totalWidth};
	}
	
	private PdfPCell newPdfCell(Cell cellInfo,int cellHeight) throws Exception{
		PdfPCell cell=null;
		Object cellData=cellInfo.getFormatData();
		if(cellData instanceof Image){
			Image img=(Image)cellData;
			cell=new PdfPCell(buildPdfImage(img.getBase64Data(), 0, 0));
		}else if(cellData instanceof ChartData){
			ChartData chartData=(ChartData)cellData;
			String base64Data=chartData.retriveBase64Data();
			if(base64Data!=null){
				Image img=new Image(base64Data,chartData.getWidth(),chartData.getHeight());
				cell=new PdfPCell(buildPdfImage(img.getBase64Data(), 0, 0));
			}else{
				cell=new PdfPCell();
				CellPhrase pargraph=new CellPhrase(cellInfo,"");
				cell.setPhrase(pargraph);
				cell.setFixedHeight(cellHeight);
			}
		}else{
			cell=new PdfPCell();
			CellPhrase pargraph=new CellPhrase(cellInfo,cellData);
			cell.setPhrase(pargraph);
			cell.setFixedHeight(cellHeight);
		}
		CellStyle style=cellInfo.getCellStyle();
		if(style!=null && style.getLineHeight()>0){
			cell.setLeading(style.getLineHeight(), style.getLineHeight());			
		}
		return cell;
	}
	private com.itextpdf.text.Image buildPdfImage(String base64Data, int width,int height) throws Exception{
		com.itextpdf.text.Image pdfImg=null;
		InputStream input=ImageUtils.base64DataToInputStream(base64Data);
		try{
			byte[] bytes=IOUtils.toByteArray(input);
			pdfImg=com.itextpdf.text.Image.getInstance(bytes);
			float imgWidth=pdfImg.getWidth();
			float imgHeight=pdfImg.getHeight();
			if(width==0){
				width=Float.valueOf(imgWidth).intValue();
			}
			if(height==0){
				height=Float.valueOf(imgHeight).intValue();
			}
			width=UnitUtils.pixelToPoint(width-2);
			height=UnitUtils.pixelToPoint(height-2);
			pdfImg.scaleToFit(width,height);
		}finally{
			IOUtils.closeQuietly(input);
		}
		return pdfImg;
	}
}
