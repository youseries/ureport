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
package com.bstek.ureport.definition;

import java.io.Serializable;

/**
 * @author Jacky.gao
 * @since 2014年4月29日
 */
public class Paper implements Serializable{
	private static final long serialVersionUID = -6153150083492704136L;
	private int leftMargin=90;
	private int rightMargin=90;
	private int topMargin=72;
	private int bottomMargin=72;
	private PaperType paperType;
	private PagingMode pagingMode;
	private int fixRows;
	private int width;
	private int height;
	private Orientation orientation;
	private HtmlReportAlign htmlReportAlign=HtmlReportAlign.left;
	private String bgImage;
	private boolean columnEnabled;
	private int columnCount=2;
	private int columnMargin=5;
	private int htmlIntervalRefreshValue=0;
	public int getLeftMargin() {
		return leftMargin;
	}

	public void setLeftMargin(int leftMargin) {
		this.leftMargin = leftMargin;
	}

	public int getRightMargin() {
		return rightMargin;
	}

	public void setRightMargin(int rightMargin) {
		this.rightMargin = rightMargin;
	}

	public int getTopMargin() {
		return topMargin;
	}

	public void setTopMargin(int topMargin) {
		this.topMargin = topMargin;
	}

	public int getBottomMargin() {
		return bottomMargin;
	}

	public void setBottomMargin(int bottomMargin) {
		this.bottomMargin = bottomMargin;
	}
	
	public PaperType getPaperType() {
		return paperType;
	}
	public void setPaperType(PaperType paperType) {
		this.paperType = paperType;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	public PagingMode getPagingMode() {
		return pagingMode;
	}
	public void setPagingMode(PagingMode pagingMode) {
		this.pagingMode = pagingMode;
	}
	public int getFixRows() {
		return fixRows;
	}
	public void setFixRows(int fixRows) {
		this.fixRows = fixRows;
	}

	public boolean isColumnEnabled() {
		return columnEnabled;
	}

	public void setColumnEnabled(boolean columnEnabled) {
		this.columnEnabled = columnEnabled;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	public int getColumnMargin() {
		return columnMargin;
	}

	public void setColumnMargin(int columnMargin) {
		this.columnMargin = columnMargin;
	}

	public HtmlReportAlign getHtmlReportAlign() {
		return htmlReportAlign;
	}

	public void setHtmlReportAlign(HtmlReportAlign htmlReportAlign) {
		this.htmlReportAlign = htmlReportAlign;
	}

	public String getBgImage() {
		return bgImage;
	}

	public void setBgImage(String bgImage) {
		this.bgImage = bgImage;
	}

	public int getHtmlIntervalRefreshValue() {
		return htmlIntervalRefreshValue;
	}

	public void setHtmlIntervalRefreshValue(int htmlIntervalRefreshValue) {
		this.htmlIntervalRefreshValue = htmlIntervalRefreshValue;
	}
}
