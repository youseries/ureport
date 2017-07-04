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
package com.bstek.ureport.export.excel.low;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFShape;

import com.bstek.ureport.Utils;
import com.bstek.ureport.build.paging.Page;
import com.bstek.ureport.chart.ChartData;
import com.bstek.ureport.definition.Orientation;
import com.bstek.ureport.definition.Paper;
import com.bstek.ureport.definition.PaperType;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.model.Column;
import com.bstek.ureport.model.Image;
import com.bstek.ureport.model.Report;
import com.bstek.ureport.model.Row;
import com.bstek.ureport.utils.ImageUtils;
import com.bstek.ureport.utils.UnitUtils;

/**
 * @author Jacky.gao
 * @since 2017年7月3日
 */
public class Excel97Producer {
	public void produceWithPaging(Report report, OutputStream outputStream) {
		doProduce(report, outputStream, true,false);
	}
	public void produce(Report report, OutputStream outputStream) {
		doProduce(report, outputStream, false,false);
	}
	public void produceWithSheet(Report report, OutputStream outputStream) {
		doProduce(report, outputStream, true,true);
	}
	
	private void doProduce(Report report, OutputStream outputStream,boolean withPaging,boolean withSheet) {
		CellStyleContext cellStyleContext=new CellStyleContext();
		HSSFWorkbook wb = new HSSFWorkbook();
		CreationHelper creationHelper=wb.getCreationHelper();
		Paper paper=report.getPaper();

		try{
			List<Column> columns=report.getColumns();
			Map<Row,Map<Column,com.bstek.ureport.model.Cell>> cellMap=report.getRowColCellMap();
			int columnSize=columns.size();
			if(withPaging){
				List<Page> pages=report.getPages();
				int rowNumber=0,pageIndex=1;
				Sheet sheet=null;
				for(Page page:pages){
					if(withSheet){
						sheet=createSheet(wb, paper, "第"+pageIndex+"页");
						rowNumber=0;
					}else if(sheet==null){
						sheet=createSheet(wb, paper, null);						
					}
					pageIndex++;
					Drawing<?> drawing=sheet.createDrawingPatriarch();
					List<Row> rows=page.getRows();
					for(Row r:rows){
						org.apache.poi.ss.usermodel.Row row = sheet.getRow(rowNumber);
			        	if(row==null){
			        		row=sheet.createRow(rowNumber);
			        	}
			        	Map<Column,com.bstek.ureport.model.Cell> colCell=cellMap.get(r);
			        	for(int i=0;i<columnSize;i++){
			        		Column col=columns.get(i);
			        		int w=col.getWidth();
			        		if(w<1){
			        			continue;
			        		}
			        		double colWidth=UnitUtils.pointToPixel(w)*37.5;
			        		sheet.setColumnWidth(i,(short)colWidth);
			        		org.apache.poi.ss.usermodel.Cell cell = row.getCell(i);
			        		if(cell!=null){
			        			continue;
			        		}
			        		cell=row.createCell(i);
			        		com.bstek.ureport.model.Cell cellInfo=colCell.get(col);
			        		if(cellInfo==null){
			        			continue;
			        		}
			        		HSSFCellStyle style = cellStyleContext.produceXSSFCellStyle(wb,cellInfo);
			        		int colSpan=cellInfo.getColSpan();
			        		int rowSpan=cellInfo.getPageRowSpan();
			        		int rowStart=rowNumber;
			        		int rowEnd=rowSpan;
			        		if(rowSpan==0){
			        			rowEnd++;
			        		}
			        		rowEnd+=rowNumber;
			        		int colStart=i;
			        		int colEnd=colSpan;
			        		if(colSpan==0){
			        			colEnd++;
			        		}
			        		colEnd+=i;
			        		for(int j=rowStart;j<rowEnd;j++){
			        			org.apache.poi.ss.usermodel.Row rr=sheet.getRow(j);
			        			if(rr==null){
			        				rr=sheet.createRow(j);
			        			}
			        			for(int c=colStart;c<colEnd;c++){
			        				Cell cc=rr.getCell(c);
			        				if(cc==null){
			        					cc=rr.createCell(c);
			        				}
			        				cc.setCellStyle(style);
			        			}
			        		}
			        		if(colSpan>0 || rowSpan>0){
			        			if(rowSpan>0){
			        				rowSpan--;
			        			}
			        			if(colSpan>0){
			        				colSpan--;
			        			}
			        			CellRangeAddress cellRegion=new CellRangeAddress(rowNumber,(rowNumber+rowSpan),i,(i+colSpan));
			        			sheet.addMergedRegion(cellRegion);
			        		}
			        		Object obj=cellInfo.getFormatData();
			        		if(obj!=null){
				        		if(obj instanceof String){
				        			cell.setCellValue((String)obj);     
				        			cell.setCellType(CellType.STRING);
				        		}else if(obj instanceof Number){
				        			BigDecimal bigDecimal=Utils.toBigDecimal(obj);
				        			cell.setCellValue(bigDecimal.floatValue());
				        			cell.setCellType(CellType.NUMERIC);
				        		}else if(obj instanceof Boolean){
				        			cell.setCellValue((Boolean)obj);
				        			cell.setCellType(CellType.BOOLEAN);
				        		}else if(obj instanceof Image){
				        			Image img=(Image)obj;
				        			InputStream inputStream=ImageUtils.base64DataToInputStream(img.getBase64Data());
				    				BufferedImage bufferedImage=ImageIO.read(inputStream);
				    				int width=bufferedImage.getWidth();
				    				int height=bufferedImage.getHeight();
				    				IOUtils.closeQuietly(inputStream);
				    				inputStream=ImageUtils.base64DataToInputStream(img.getBase64Data());
				        			try{
				        				XSSFClientAnchor anchor=(XSSFClientAnchor)creationHelper.createClientAnchor();
				        				byte[] bytes=IOUtils.toByteArray(inputStream);
				        				int pictureFormat=buildImageFormat(img);
				        				int pictureIndex=wb.addPicture(bytes, pictureFormat);
				        				anchor.setCol1(i);
				        				anchor.setCol2(i+colSpan);
				        				anchor.setRow1(rowNumber);
				        				anchor.setRow2(rowNumber+rowSpan);
				        				anchor.setDx1(0 * XSSFShape.EMU_PER_PIXEL);
				        				anchor.setDx2(width * XSSFShape.EMU_PER_PIXEL);
				        				anchor.setDy1(0 * XSSFShape.EMU_PER_PIXEL);
				        				anchor.setDy2(height * XSSFShape.EMU_PER_PIXEL);
				        				drawing.createPicture(anchor, pictureIndex);
				        			}finally{
				        				IOUtils.closeQuietly(inputStream);
				        			}
				        		}else if(obj instanceof ChartData){
				        			ChartData chartData=(ChartData)obj;
				        			String base64Data=chartData.retriveBase64Data();
				        			if(base64Data!=null){
				        				Image img=new Image(base64Data,chartData.getWidth(),chartData.getHeight());
				        				InputStream inputStream=ImageUtils.base64DataToInputStream(img.getBase64Data());
				        				BufferedImage bufferedImage=ImageIO.read(inputStream);
				        				int width=bufferedImage.getWidth();
				        				int height=bufferedImage.getHeight();
				        				IOUtils.closeQuietly(inputStream);
				        				inputStream=ImageUtils.base64DataToInputStream(img.getBase64Data());
				        				try{
				        					XSSFClientAnchor anchor=(XSSFClientAnchor)creationHelper.createClientAnchor();
				        					byte[] bytes=IOUtils.toByteArray(inputStream);
				        					int pictureFormat=buildImageFormat(img);
				        					int pictureIndex=wb.addPicture(bytes, pictureFormat);
				        					anchor.setCol1(i);
				        					anchor.setCol2(i+colSpan);
				        					anchor.setRow1(rowNumber);
				        					anchor.setRow2(rowNumber+rowSpan);
				        					anchor.setDx1(0 * XSSFShape.EMU_PER_PIXEL);
				        					anchor.setDx2(width * XSSFShape.EMU_PER_PIXEL);
				        					anchor.setDy1(0 * XSSFShape.EMU_PER_PIXEL);
				        					anchor.setDy2(height * XSSFShape.EMU_PER_PIXEL);
				        					drawing.createPicture(anchor, pictureIndex);
				        				}finally{
				        					IOUtils.closeQuietly(inputStream);
				        				}
				        			}
				        		}else if(obj instanceof Date){
				        			cell.setCellValue((Date)obj);
				        		}
			        		}
			        	}
			        	row.setHeight((short)UnitUtils.pointToTwip(r.getHeight()));
			        	rowNumber++;	        		
					}      		
					sheet.setRowBreak(rowNumber-1);
				}
			}else{
				Sheet sheet=createSheet(wb, paper, null);
				Drawing<?> drawing=sheet.createDrawingPatriarch();
				List<Row> rows=report.getRows();
				int rowNumber=0;
				for(Row r:rows){
					int realHeight=r.getRealHeight();
					if(realHeight<1){
						continue;
					}
					if(r.isForPaging()){
						return;
					}
					org.apache.poi.ss.usermodel.Row row = sheet.getRow(rowNumber);
		        	if(row==null){
		        		row=sheet.createRow(rowNumber);
		        	}
		        	Map<Column,com.bstek.ureport.model.Cell> colCell=cellMap.get(r);
		        	for(int i=0;i<columnSize;i++){
		        		Column col=columns.get(i);
		        		int w=col.getWidth();
		        		if(w<1){
		        			continue;
		        		}
		        		double colWidth=UnitUtils.pointToPixel(w)*37.5;
		        		sheet.setColumnWidth(i,(short)colWidth);
		        		org.apache.poi.ss.usermodel.Cell cell = row.getCell(i);
		        		if(cell!=null){
		        			continue;
		        		}
		        		cell=row.createCell(i);
		        		com.bstek.ureport.model.Cell cellInfo=colCell.get(col);
		        		if(cellInfo==null){
		        			continue;
		        		}
		        		if(cellInfo.isForPaging()){
		        			continue;
		        		}
		        		HSSFCellStyle style = cellStyleContext.produceXSSFCellStyle(wb,cellInfo);
		        		int colSpan=cellInfo.getColSpan();
		        		int rowSpan=cellInfo.getRowSpan();
		        		int rowStart=rowNumber;
		        		int rowEnd=rowSpan;
		        		if(rowSpan==0){
		        			rowEnd++;
		        		}
		        		rowEnd+=rowNumber;
		        		int colStart=i;
		        		int colEnd=colSpan;
		        		if(colSpan==0){
		        			colEnd++;
		        		}
		        		colEnd+=i;
		        		for(int j=rowStart;j<rowEnd;j++){
		        			org.apache.poi.ss.usermodel.Row rr=sheet.getRow(j);
		        			if(rr==null){
		        				rr=sheet.createRow(j);
		        			}
		        			for(int c=colStart;c<colEnd;c++){
		        				Cell cc=rr.getCell(c);
		        				if(cc==null){
		        					cc=rr.createCell(c);
		        				}
		        				cc.setCellStyle(style);
		        			}
		        		}
		        		if(colSpan>0 || rowSpan>0){
		        			if(rowSpan>0){
		        				rowSpan--;
		        			}
		        			if(colSpan>0){
		        				colSpan--;
		        			}
		        			CellRangeAddress cellRegion=new CellRangeAddress(rowNumber,(rowNumber+rowSpan),i,(i+colSpan));
		        			sheet.addMergedRegion(cellRegion);
		        		}
		        		Object obj=cellInfo.getFormatData();
		        		if(obj!=null){
			        		if(obj instanceof String){
			        			cell.setCellValue((String)obj);     
			        			cell.setCellType(CellType.STRING);
			        		}else if(obj instanceof Number){
			        			BigDecimal bigDecimal=Utils.toBigDecimal(obj);
			        			cell.setCellValue(bigDecimal.floatValue());
			        			cell.setCellType(CellType.NUMERIC);
			        		}else if(obj instanceof Boolean){
			        			cell.setCellValue((Boolean)obj);
			        			cell.setCellType(CellType.BOOLEAN);
			        		}else if(obj instanceof Image){
			        			Image img=(Image)obj;
			        			InputStream inputStream=ImageUtils.base64DataToInputStream(img.getBase64Data());
			    				BufferedImage bufferedImage=ImageIO.read(inputStream);
			    				int width=bufferedImage.getWidth();
			    				int height=bufferedImage.getHeight();
			    				IOUtils.closeQuietly(inputStream);
			    				inputStream=ImageUtils.base64DataToInputStream(img.getBase64Data());
			        			try{
			        				XSSFClientAnchor anchor=(XSSFClientAnchor)creationHelper.createClientAnchor();
			        				byte[] bytes=IOUtils.toByteArray(inputStream);
			        				int pictureFormat=buildImageFormat(img);
			        				int pictureIndex=wb.addPicture(bytes, pictureFormat);
			        				anchor.setCol1(i);
			        				anchor.setCol2(i+colSpan);
			        				anchor.setRow1(rowNumber);
			        				anchor.setRow2(rowNumber+rowSpan);
			        				anchor.setDx1(0 * XSSFShape.EMU_PER_PIXEL);
			        				anchor.setDx2(width * XSSFShape.EMU_PER_PIXEL);
			        				anchor.setDy1(0 * XSSFShape.EMU_PER_PIXEL);
			        				anchor.setDy2(height * XSSFShape.EMU_PER_PIXEL);
			        				drawing.createPicture(anchor, pictureIndex);
			        			}finally{
			        				IOUtils.closeQuietly(inputStream);
			        			}
			        		}else if(obj instanceof ChartData){
			        			ChartData chartData=(ChartData)obj;
			        			String base64Data=chartData.retriveBase64Data();
			        			if(base64Data!=null){
			        				Image img=new Image(base64Data,chartData.getWidth(),chartData.getHeight());
			        				InputStream inputStream=ImageUtils.base64DataToInputStream(img.getBase64Data());
			        				BufferedImage bufferedImage=ImageIO.read(inputStream);
			        				int width=bufferedImage.getWidth();
			        				int height=bufferedImage.getHeight();
			        				IOUtils.closeQuietly(inputStream);
			        				inputStream=ImageUtils.base64DataToInputStream(img.getBase64Data());
			        				try{
			        					XSSFClientAnchor anchor=(XSSFClientAnchor)creationHelper.createClientAnchor();
			        					byte[] bytes=IOUtils.toByteArray(inputStream);
			        					int pictureFormat=buildImageFormat(img);
			        					int pictureIndex=wb.addPicture(bytes, pictureFormat);
			        					anchor.setCol1(i);
			        					anchor.setCol2(i+colSpan);
			        					anchor.setRow1(rowNumber);
			        					anchor.setRow2(rowNumber+rowSpan);
			        					anchor.setDx1(0 * XSSFShape.EMU_PER_PIXEL);
			        					anchor.setDx2(width * XSSFShape.EMU_PER_PIXEL);
			        					anchor.setDy1(0 * XSSFShape.EMU_PER_PIXEL);
			        					anchor.setDy2(height * XSSFShape.EMU_PER_PIXEL);
			        					drawing.createPicture(anchor, pictureIndex);
			        				}finally{
			        					IOUtils.closeQuietly(inputStream);
			        				}
			        			}
			        		}else if(obj instanceof Date){
			        			cell.setCellValue((Date)obj);
			        		}
		        		}
		        	}
		        	row.setHeight((short)UnitUtils.pointToTwip(r.getRealHeight()));
		        	rowNumber++;	        		
				}      		
				sheet.setRowBreak(rowNumber-1);
			}
			wb.write(outputStream);			
		}catch(Exception ex){
			throw new ReportComputeException(ex);
		}finally{
			try {
				wb.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private Sheet createSheet(HSSFWorkbook wb,Paper paper,String name){
		Sheet sheet = null;
		if(name==null){
			sheet=wb.createSheet();
		}else{			
			sheet=wb.createSheet(name);
		}
		PaperType paperType=paper.getPaperType();
		HSSFPrintSetup printSetup=(HSSFPrintSetup)sheet.getPrintSetup();
		Orientation orientation=paper.getOrientation();
		if(orientation.equals(Orientation.landscape)){
			printSetup.setLandscape(true);
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

	private int buildImageFormat(Image img){
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

	private boolean setupPaper(PaperType paperType, HSSFPrintSetup printSetup) {
		boolean setup=false;
		switch(paperType){
			case A0:
				printSetup.setPaperSize(HSSFPrintSetup.A4_EXTRA_PAPERSIZE);
				break;
			case A1:
				printSetup.setPaperSize(HSSFPrintSetup.A4_EXTRA_PAPERSIZE);
				break;
			case A2:
				printSetup.setPaperSize(HSSFPrintSetup.A4_EXTRA_PAPERSIZE);
				break;
			case A3:				
				printSetup.setPaperSize(HSSFPrintSetup.A3_PAPERSIZE);
				setup=true;
				break;
			case A4:
				printSetup.setPaperSize(HSSFPrintSetup.A4_EXTRA_PAPERSIZE);
				setup=true;
				break;
			case A5:
				printSetup.setPaperSize(HSSFPrintSetup.A5_PAPERSIZE);
				setup=true;
				break;
			case A6:
				printSetup.setPaperSize(HSSFPrintSetup.A4_EXTRA_PAPERSIZE);
				break;
			case A7:
				printSetup.setPaperSize(HSSFPrintSetup.A4_EXTRA_PAPERSIZE);
				break;
			case A8:
				printSetup.setPaperSize(HSSFPrintSetup.A4_EXTRA_PAPERSIZE);
				break;
			case A9:
				printSetup.setPaperSize(HSSFPrintSetup.A4_EXTRA_PAPERSIZE);
				break;
			case A10:
				printSetup.setPaperSize(HSSFPrintSetup.A4_EXTRA_PAPERSIZE);
				break;
			case B0:
				printSetup.setPaperSize(HSSFPrintSetup.A4_EXTRA_PAPERSIZE);
				break;
			case B1:
				printSetup.setPaperSize(HSSFPrintSetup.A4_EXTRA_PAPERSIZE);
				break;
			case B2:
				printSetup.setPaperSize(HSSFPrintSetup.A4_EXTRA_PAPERSIZE);
				break;
			case B3:
				printSetup.setPaperSize(HSSFPrintSetup.A4_EXTRA_PAPERSIZE);
				break;
			case B4:
				printSetup.setPaperSize(HSSFPrintSetup.B4_PAPERSIZE);
				setup=true;
				break;
			case B5:
				printSetup.setPaperSize(HSSFPrintSetup.B5_PAPERSIZE);
				setup=true;
				break;
			case B6:
				printSetup.setPaperSize(HSSFPrintSetup.A4_EXTRA_PAPERSIZE);
				break;
			case B7:
				printSetup.setPaperSize(HSSFPrintSetup.A4_EXTRA_PAPERSIZE);
				break;
			case B8:
				printSetup.setPaperSize(HSSFPrintSetup.A4_EXTRA_PAPERSIZE);
				break;
			case B9:
				printSetup.setPaperSize(HSSFPrintSetup.A4_EXTRA_PAPERSIZE);
				break;
			case B10:
				printSetup.setPaperSize(HSSFPrintSetup.A4_EXTRA_PAPERSIZE);
				break;
			case CUSTOM:
				printSetup.setPaperSize(HSSFPrintSetup.A4_EXTRA_PAPERSIZE);
				break;
		}
		return setup;
	}
}
