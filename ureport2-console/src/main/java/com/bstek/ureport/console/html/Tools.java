package com.bstek.ureport.console.html;

/**
 * @author Jacky.gao
 * @since 2017年6月1日
 */
public class Tools {
	private boolean word=true;
	private boolean excel=true;
	private boolean pdf=true;
	private boolean pagingExcel=true;
	private boolean sheetPagingExcel=true;
	private boolean print=true;
	private boolean pdfPrint=true;
	private boolean pdfPreviewPrint=true;
	private boolean paging=true;
	public Tools(boolean init) {
		if(init){
			word=true;
			excel=true;
			pdf=true;
			pagingExcel=true;
			sheetPagingExcel=true;
			print=true;
			pdfPrint=true;
			pdfPreviewPrint=true;
			paging=true;
		}else{
			word=false;
			excel=false;
			pdf=false;
			pagingExcel=false;
			sheetPagingExcel=false;
			print=false;
			pdfPrint=false;
			pdfPreviewPrint=false;
			paging=false;
		}
	}
	public void doInit(String name){
		if(name.equals("5")){
			word=true;
		}else if(name.equals("6")){
			excel=true;
		}else if(name.equals("4")){
			pdf=true;
		}else if(name.equals("1")){
			print=true;
		}else if(name.equals("2")){
			pdfPrint=true;
		}else if(name.equals("3")){
			pdfPreviewPrint=true;
		}else if(name.equals("9")){
			paging=true;
		}else if(name.equals("7")){
			pagingExcel=true;
		}else if(name.equals("8")){
			sheetPagingExcel=true;
		}
	}
	public boolean isWord() {
		return word;
	}
	public void setWord(boolean word) {
		this.word = word;
	}
	public boolean isExcel() {
		return excel;
	}
	public void setExcel(boolean excel) {
		this.excel = excel;
	}
	public boolean isPdf() {
		return pdf;
	}
	public void setPdf(boolean pdf) {
		this.pdf = pdf;
	}
	public boolean isPagingExcel() {
		return pagingExcel;
	}
	public void setPagingExcel(boolean pagingExcel) {
		this.pagingExcel = pagingExcel;
	}
	public boolean isSheetPagingExcel() {
		return sheetPagingExcel;
	}
	public void setSheetPagingExcel(boolean sheetPagingExcel) {
		this.sheetPagingExcel = sheetPagingExcel;
	}
	public boolean isPrint() {
		return print;
	}
	public void setPrint(boolean print) {
		this.print = print;
	}
	public boolean isPdfPrint() {
		return pdfPrint;
	}
	public void setPdfPrint(boolean pdfPrint) {
		this.pdfPrint = pdfPrint;
	}
	public boolean isPdfPreviewPrint() {
		return pdfPreviewPrint;
	}
	public void setPdfPreviewPrint(boolean pdfPreviewPrint) {
		this.pdfPreviewPrint = pdfPreviewPrint;
	}
	public boolean isPaging() {
		return paging;
	}
	public void setPaging(boolean paging) {
		this.paging = paging;
	}
}
