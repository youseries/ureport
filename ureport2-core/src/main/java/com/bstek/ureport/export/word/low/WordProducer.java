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
package com.bstek.ureport.export.word.low;



/**
 * @author Jacky.gao
 * @since 2015年5月20日
 */
public class WordProducer{/* implements Producer{
	public static final String BEAN_ID="ureport.wordExporter";
	
	@Override
	public void produce(Report report,OutputStream outputStream) {
		
		String tempDir=System.getProperty("java.io.tmpdir");
		String tempWordFile=tempDir+"/"+UUID.randomUUID().toString()+".doc";
		File file=new File(tempWordFile);
		POIFSFileSystem fileSystem=new POIFSFileSystem(file);
		HWPFDocument document = new HWPFDocument(fileSystem);
		try{
			Range range=document.getRange();
			CTSectPr sectpr = null;//document.getDocument().getBody().addNewSectPr();
			if(!sectpr.isSetPgSz()) {
				sectpr.addNewPgSz();
			}
			CTPageSz pageSize = sectpr.getPgSz();
			Paper paper=report.getPaper();
			Orientation orientation=paper.getOrientation();
			if(orientation.equals(Orientation.landscape)){
				pageSize.setOrient(STPageOrientation.LANDSCAPE);
				pageSize.setH(BigInteger.valueOf(DxaUtils.points2dxa(paper.getWidth())));
				pageSize.setW(BigInteger.valueOf(DxaUtils.points2dxa(paper.getHeight())));
			}else{
				pageSize.setOrient(STPageOrientation.PORTRAIT);
				pageSize.setW(BigInteger.valueOf(DxaUtils.points2dxa(paper.getWidth())));
				pageSize.setH(BigInteger.valueOf(DxaUtils.points2dxa(paper.getHeight())));
			}
			int columnCount=paper.getColumnCount();
			if(paper.isColumnEnabled() && columnCount>0){
				CTColumns cols=CTColumns.Factory.newInstance();
				cols.setNum(new BigInteger(String.valueOf(columnCount)));
				int columnMargin=paper.getColumnMargin();
				cols.setSpace(new BigInteger(String.valueOf(DxaUtils.points2dxa(columnMargin))));
				sectpr.setCols(cols);
			}
			CTPageMar pageMar = sectpr.addNewPgMar();
			pageMar.setLeft(BigInteger.valueOf(DxaUtils.points2dxa(paper.getLeftMargin())));
			pageMar.setRight(BigInteger.valueOf(DxaUtils.points2dxa(paper.getRightMargin())));
			pageMar.setTop(BigInteger.valueOf(DxaUtils.points2dxa(paper.getTopMargin())));
			pageMar.setBottom(BigInteger.valueOf(DxaUtils.points2dxa(paper.getBottomMargin())));
			List<Column> columns=report.getColumns();
			int totalColumn=buildColumnSize(columns);
			int tableWidth=buildTablePCTWidth(columns);
			List<Page> pages=report.getPages();
			Map<Row,Map<Column,Cell>> cellMap=report.getRowColCellMap();
			int totalPages=pages.size();
			int pageIndex=1;
			for(Page page:pages){
				List<Row> rows=page.getRows();
				HWPFTable table = document.createTable(rows.size(), totalColumn);
				table.getCTTbl().getTblPr().unsetTblBorders();
				table.getCTTbl().addNewTblPr().addNewTblW().setW(BigInteger.valueOf(DxaUtils.points2dxa(tableWidth)));
				for(Row row:rows){
					int height=row.getRealHeight();
					int rowNumber=rows.indexOf(row);
					HWPFTableRow tableRow=table.getRow(rowNumber);
					tableRow.setHeight(DxaUtils.points2dxa(height));
					Map<Column,Cell> colCell=cellMap.get(row);
					for(Column col:columns){
						int width=col.getWidth();
						if(width<1){
							continue;
						}
						int colNumber=col.getColumnNumber()-1;
						Cell cell=colCell.get(col);
						if(cell==null){
							continue;
						}
						HWPFTableCell tableCell=tableRow.getCell(colNumber);
						if(tableCell==null){
							continue;
						}
						tableCell.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(DxaUtils.points2dxa(width)));
						buildTableCellStyle(table,tableCell,cell,rowNumber,colNumber);
					}
				}
				if(pageIndex<totalPages){					
					HWPFParagraph paragraph = document.createParagraph();
					HWPFRun run = paragraph.createRun();
					run.setFontSize(0);
					run.addBreak(BreakType.PAGE);
				}
				pageIndex++;
			}
			document.write(outputStream);			
		}catch(Exception ex){
			throw new ReportComputeException(ex);
		}finally{
			try{
				document.close();				
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	
	private int buildColumnSize(List<Column> columns){
		int count=0;
		for(int i=0;i<columns.size();i++){
			Column col=columns.get(i);
			int width=col.getWidth();
			if(width<1){
				continue;
			}
			count++;
		}
		return count;
	}
	
	private void buildTableCellStyle(HWPFTable table,HWPFTableCell tableCell,Cell cell,int rowNumber,int columnNumber){
		CellStyle style=cell.getCellStyle();
		CellStyle customStyle=cell.getCustomCellStyle();
		CellStyle rowStyle=cell.getRow().getCustomCellStyle();
		CellStyle colStyle=cell.getColumn().getCustomCellStyle();
		CTTcPr cellProperties = tableCell.getCTTc().addNewTcPr();
		Border leftBorder=style.getLeftBorder();
		Border rightBorder=style.getRightBorder();
		Border topBorder=style.getTopBorder();
		Border bottomBorder=style.getBottomBorder();
		if(customStyle!=null){
			if(customStyle.getLeftBorder()!=null){
				leftBorder=customStyle.getLeftBorder();
			}
			if(customStyle.getRightBorder()!=null){
				rightBorder=customStyle.getRightBorder();
			}
			if(customStyle.getTopBorder()!=null){
				topBorder=customStyle.getTopBorder();
			}
			if(customStyle.getBottomBorder()!=null){
				bottomBorder=customStyle.getBottomBorder();
			}
		}
		int rowSpan=cell.getPageRowSpan();
		int colSpan=cell.getColSpan();
		if(style.getLeftBorder()!=null){
			if(rowSpan>0){
				int start=rowNumber;
				int end=start+rowSpan;
				for(int i=start;i<end;i++){
					HWPFTableCell c=table.getRow(i).getCell(columnNumber);
					buildCellBorder(leftBorder, c, 1);
				}
			}else{
				buildCellBorder(leftBorder, tableCell,1);
			}
		}
		if(rightBorder!=null){
			int lastCol=columnNumber;
			if(colSpan>0){
				lastCol+=colSpan-1;
			}
			if(rowSpan>0){
				int start=rowNumber;
				int end=start+rowSpan;
				for(int i=start;i<end;i++){
					HWPFTableCell c=table.getRow(i).getCell(lastCol);
					buildCellBorder(style.getRightBorder(), c, 2);
				}
			}else{
				HWPFTableCell c=table.getRow(rowNumber).getCell(lastCol);
				buildCellBorder(rightBorder, c, 2);
			}
		}
		if(topBorder!=null){
			if(colSpan>0){
				int start=columnNumber;
				int end=start+colSpan;
				for(int i=start;i<end;i++){
					HWPFTableCell c=table.getRow(rowNumber).getCell(i);
					buildCellBorder(topBorder, c, 3);
				}
			}else{
				buildCellBorder(topBorder, tableCell, 3);
			}
		}
		if(bottomBorder!=null){
			int lastRow=rowNumber;
			if(rowSpan>0){
				lastRow+=rowSpan-1;
			}
			if(colSpan>0){
				int start=columnNumber;
				int end=start+colSpan;
				for(int i=start;i<end;i++){
					HWPFTableCell c=table.getRow(lastRow).getCell(i);
					buildCellBorder(bottomBorder, c, 4);
				}
			}else{
				HWPFTableCell c=table.getRow(lastRow).getCell(columnNumber);
				buildCellBorder(bottomBorder, c, 4);
			}
		}
		List<HWPFParagraph> paras=tableCell.getParagraphs();
		HWPFParagraph para=null;
		if(paras!=null && paras.size()>0){
			para=paras.get(0);			
		}else{
			para=tableCell.addParagraph();
		}
		List<HWPFRun> runs=para.getRuns();
		HWPFRun run=null;
		if(runs!=null && runs.size()>0){
			run=runs.get(0);
		}else{
			run=para.createRun();
		}
		Object value=cell.getData();
		if(value instanceof String){
			String text=value.toString();
			if(text.contains("\n")){
				String[] line=text.split("\n");
				run.setText(line[0], 0);
				for(int i=1;i<line.length;i++){
					run.addBreak();
					run.setText(line[i], i);
				}
			}else{
				run.setText(text);
			}
		}else if(value instanceof Number){
			run.setText(String.valueOf(value));
		}else if(value instanceof Boolean){
			run.setText(value.toString());
		}else if((value instanceof Image) || (value instanceof ChartData)){
			Image img=null;
			if(value instanceof Image){
				img=(Image)value;
			}else{
				ChartData chartData=(ChartData)value;
				String base64Data=chartData.retriveBase64Data();
				if(base64Data!=null){
					img=new Image(base64Data,chartData.getWidth(),chartData.getHeight());
				}else{
					img=new Image("",chartData.getWidth(),chartData.getHeight());					
				}
			}
			String path=img.getPath();
			String imageType="png";
			if(StringUtils.isNotBlank(path)){
				path=path.toLowerCase();
				if(path.endsWith(".jpg") || path.endsWith(".jpeg")){
					imageType="jpeg";
				}else if(path.endsWith(".gif")){
					imageType="gif";
				}
			}
			String base64Data=img.getBase64Data();
			InputStream inputStream=null;
			try{
				inputStream=ImageUtils.base64DataToInputStream(base64Data);
				BufferedImage bufferedImage=ImageIO.read(inputStream);
				int width=bufferedImage.getWidth();
				int height=bufferedImage.getHeight();
				IOUtils.closeQuietly(inputStream);
				inputStream=ImageUtils.base64DataToInputStream(base64Data);
				if(imageType.equals("jpeg")){
					run.addPicture(inputStream, HWPFDocument.PICTURE_TYPE_JPEG, "ureport-"+rowNumber+"-"+columnNumber+".jpg", Units.toEMU(width), Units.toEMU(height));					
				}else if(imageType.equals("png")){
					run.addPicture(inputStream, HWPFDocument.PICTURE_TYPE_PNG, "ureport-"+rowNumber+"-"+columnNumber+".png", Units.toEMU(width), Units.toEMU(height));					
				}else if(imageType.equals("gif")){
					run.addPicture(inputStream, HWPFDocument.PICTURE_TYPE_GIF, "ureport-"+rowNumber+"-"+columnNumber+".gif", Units.toEMU(width), Units.toEMU(height));					
				}
			}catch(Exception ex){
				throw new ReportComputeException(ex);
			}finally{
				IOUtils.closeQuietly(inputStream);
			}
		}else if(value instanceof Date){
			Date date=(Date)value;
			SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			run.setText(sd.format(date));
		}
		String fontFamily=style.getFontFamily();
		if(customStyle!=null && StringUtils.isNotBlank(customStyle.getFontFamily())){
			fontFamily=customStyle.getFontFamily();
		}
		if(rowStyle!=null && StringUtils.isNotBlank(rowStyle.getFontFamily())){
			fontFamily=rowStyle.getFontFamily();
		}
		if(colStyle!=null && StringUtils.isNotBlank(colStyle.getFontFamily())){
			fontFamily=colStyle.getFontFamily();
		}
		if(StringUtils.isNotBlank(fontFamily)){
			run.setFontFamily(fontFamily);
		}
		int fontSize=style.getFontSize();
		if(customStyle!=null && customStyle.getFontSize()>0){
			fontSize=customStyle.getFontSize();
		}
		if(rowStyle!=null && rowStyle.getFontSize()>0){
			fontSize=rowStyle.getFontSize();
		}
		if(colStyle!=null && colStyle.getFontSize()>0){
			fontSize=colStyle.getFontSize();
		}
		if(fontSize>0){
			run.setFontSize(fontSize);
		}
		boolean bold=style.getBold()==null ? false : style.getBold();
		if(customStyle!=null && customStyle.getBold()!=null){
			bold=customStyle.getBold();
		}
		if(rowStyle!=null && rowStyle.getBold()!=null){
			bold=rowStyle.getBold();
		}
		if(colStyle!=null && colStyle.getBold()!=null){
			bold=colStyle.getBold();
		}
		if(bold){
			run.setBold(true);
		}
		boolean italic=style.getItalic()==null ? false : style.getItalic();
		if(customStyle!=null && customStyle.getItalic()!=null){
			italic=customStyle.getItalic();
		}
		if(rowStyle!=null && rowStyle.getItalic()!=null){
			italic=rowStyle.getItalic();
		}
		if(colStyle!=null && colStyle.getItalic()!=null){
			italic=colStyle.getItalic();
		}
		if(italic){
			run.setItalic(true);
		}
		boolean underline=style.getUnderline()==null ? false : style.getUnderline();
		if(customStyle!=null && customStyle.getUnderline()!=null){
			underline=customStyle.getUnderline();
		}
		if(rowStyle!=null && rowStyle.getUnderline()!=null){
			underline=rowStyle.getUnderline();
		}
		if(colStyle!=null && colStyle.getUnderline()!=null){
			underline=colStyle.getUnderline();
		}
		if(underline){
			run.setUnderline(UnderlinePatterns.SINGLE);
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
		if(bgcolor!=null){
			CTShd ctshd = cellProperties.addNewShd();  
	        ctshd.setFill(toHex(bgcolor.split(",")));  
		}
		String forecolor=style.getForecolor();
		if(customStyle!=null && StringUtils.isNotBlank(customStyle.getForecolor())){
			forecolor=customStyle.getForecolor();
		}
		if(rowStyle!=null && StringUtils.isNotBlank(rowStyle.getForecolor())){
			forecolor=rowStyle.getForecolor();
		}
		if(colStyle!=null && StringUtils.isNotBlank(colStyle.getForecolor())){
			forecolor=colStyle.getForecolor();
		}
		if(forecolor!=null){
			run.setColor(toHex(forecolor.split(",")));
		}
		Alignment align=style.getAlign();
		if(customStyle!=null && customStyle.getAlign()!=null){
			align=customStyle.getAlign();
		}
		if(rowStyle!=null && rowStyle.getAlign()!=null){
			align=rowStyle.getAlign();
		}
		if(align!=null){
			if(align.equals(Alignment.left)){
				para.setAlignment(ParagraphAlignment.LEFT);
			}else if(align.equals(Alignment.right)){
				para.setAlignment(ParagraphAlignment.RIGHT);
			}else if(align.equals(Alignment.center)){
				para.setAlignment(ParagraphAlignment.CENTER);
			}
		}
		align=style.getValign();
		if(customStyle!=null && customStyle.getValign()!=null){
			align=customStyle.getValign();
		}
		if(rowStyle!=null && rowStyle.getValign()!=null){
			align=rowStyle.getValign();
		}
		if(colStyle!=null && colStyle.getValign()!=null){
			align=colStyle.getValign();
		}
		if(align!=null){
			CTVerticalJc verticalAlign = cellProperties.addNewVAlign();
			if(align.equals(Alignment.top)){
				verticalAlign.setVal(STVerticalJc.TOP);
			}else if(align.equals(Alignment.middle)){
				verticalAlign.setVal(STVerticalJc.CENTER);
			}else if(align.equals(Alignment.bottom)){
				verticalAlign.setVal(STVerticalJc.BOTTOM);
			}
		}
		int startCol=columnNumber;
		int startRow=rowNumber;
		int endRow=startRow,endCol=startCol;
		if(colSpan>0){
			endCol=startCol+colSpan-1;
		}
		if(rowSpan>0){
			endRow=startRow+rowSpan-1;
		}
		if(startCol!=endCol){
			if(rowSpan>0){
				for(int i=startRow;i<=endRow;i++){
					mergeCellsHorizontal(table,i,startCol,endCol);								
				}
			}else{
					mergeCellsHorizontal(table,startRow,startCol,endCol);								
			}
		}
		if(startRow!=endRow){
			if(colSpan>0){
				for(int i=startCol;i<=endCol;i++){
					mergeCellsVertically(table, i, startRow, endRow);				
				}
			}else{
				mergeCellsVertically(table, startCol, startRow, endRow);				
			}
		}
	}
	
	private void mergeCellsHorizontal(HWPFTable table, int row, int startCol,int endCol) {
		for (int cellIndex = startCol; cellIndex <= endCol; cellIndex++) {
			HWPFTableCell cell = table.getRow(row).getCell(cellIndex);
			if (cellIndex == startCol) {
				cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
			} else {
				cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
			}
		}
	}

	private void mergeCellsVertically(HWPFTable table, int col, int fromRow,int toRow) {
		for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
			HWPFTableCell cell = table.getRow(rowIndex).getCell(col);
			if (rowIndex == fromRow) {
				cell.getCTTc().addNewTcPr().addNewVMerge()
						.setVal(STMerge.RESTART);
			} else {
				cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
			}
		}
	}	
	
	private void buildCellBorder(Border border, HWPFTableCell tableCell,int type) {
		CTTcPr cellPropertie = tableCell.getCTTc().getTcPr();
		if(cellPropertie==null){
			cellPropertie=tableCell.getCTTc().addNewTcPr();
		}
		CTTcBorders borders=cellPropertie.getTcBorders();
		if(borders==null){
			borders=cellPropertie.addNewTcBorders();;
		}
		BorderStyle borderStyle=border.getStyle();
		CTBorder ctborder=null;
		if(type==1){
			ctborder=borders.addNewLeft();
		}else if(type==2){
			ctborder=borders.addNewRight();
		}else if(type==3){
			ctborder=borders.addNewTop();
		}else if(type==4){
			ctborder=borders.addNewBottom();
		}
		if(borderStyle.equals(BorderStyle.dashed)){
			ctborder.setVal(STBorder.DASHED);
		}else if(borderStyle.equals(BorderStyle.doublesolid)){
			ctborder.setVal(STBorder.DOUBLE);
		}else{
			ctborder.setVal(STBorder.SINGLE);				
		}
		int borderWidth=border.getWidth();
		if(borderWidth>1){
			ctborder.setSz(BigInteger.valueOf(DxaUtils.points2dxa(borderWidth)));				
		}
		String color=border.getColor();
		if(StringUtils.isNotBlank(color)){
			ctborder.setColor(toHex(color.split(",")));
		}
	}

	private int buildTablePCTWidth(List<Column> columns){
		int width=0;
		for(Column col:columns){
			width+=col.getWidth();
		}
		return width;
	}
	
	
	private String toHex(String rgb[]) {
        StringBuffer sb = new StringBuffer();
        String R = Integer.toHexString(Integer.valueOf(rgb[0]));
        String G = Integer.toHexString(Integer.valueOf(rgb[1]));
        String B = Integer.toHexString(Integer.valueOf(rgb[2]));
        R = R.length() == 1 ? "0" + R : R;
        G = G.length() == 1 ? "0" + G : G;
        B = B.length() == 1 ? "0" + B : B;
        sb.append(R);
        sb.append(G);
        sb.append(B);
        return sb.toString();
    }*/
}
