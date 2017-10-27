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

import java.awt.Font;
import java.io.Serializable;
import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.bstek.ureport.Utils;


/**
 * @author Jacky.gao
 * @since 2017年1月18日
 */
public class CellStyle implements Serializable{
	private static final long serialVersionUID = 8327688051735343849L;
	private String bgcolor;
	private String forecolor="0,0,0";
	private int fontSize=10;
	private String fontFamily="宋体";
	private String format;
	private float lineHeight;
	private Alignment align;
	private Alignment valign;
	private Boolean bold;
	private Boolean italic;
	private Boolean underline;
	private Boolean wrapCompute;
	private Border leftBorder;
	private Border rightBorder;
	private Border topBorder;
	private Border bottomBorder;
	
	private Font font;

	public Border getLeftBorder() {
		return leftBorder;
	}

	public void setLeftBorder(Border leftBorder) {
		this.leftBorder = leftBorder;
	}

	public Border getRightBorder() {
		return rightBorder;
	}

	public void setRightBorder(Border rightBorder) {
		this.rightBorder = rightBorder;
	}

	public Border getTopBorder() {
		return topBorder;
	}

	public void setTopBorder(Border topBorder) {
		this.topBorder = topBorder;
	}

	public Border getBottomBorder() {
		return bottomBorder;
	}

	public void setBottomBorder(Border bottomBorder) {
		this.bottomBorder = bottomBorder;
	}

	public String getBgcolor() {
		return bgcolor;
	}

	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}

	public String getForecolor() {
		return forecolor;
	}

	public void setForecolor(String forecolor) {
		this.forecolor = forecolor;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public String getFontFamily() {
		return fontFamily;
	}

	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Alignment getAlign() {
		return align;
	}

	public void setAlign(Alignment align) {
		this.align = align;
	}

	public Alignment getValign() {
		return valign;
	}

	public void setValign(Alignment valign) {
		this.valign = valign;
	}

	public Boolean getBold() {
		return bold;
	}

	public void setBold(Boolean bold) {
		this.bold = bold;
	}

	public Boolean getItalic() {
		return italic;
	}

	public void setItalic(Boolean italic) {
		this.italic = italic;
	}

	public Boolean getUnderline() {
		return underline;
	}

	public void setUnderline(Boolean underline) {
		this.underline = underline;
	}

	public Boolean getWrapCompute() {
		return wrapCompute;
	}

	public void setWrapCompute(Boolean wrapCompute) {
		this.wrapCompute = wrapCompute;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public float getLineHeight() {
		return lineHeight;
	}

	public void setLineHeight(float lineHeight) {
		this.lineHeight = lineHeight;
	}

	@JsonIgnore
	public Font getFont(){
		if(this.font==null){
			int fontStyle=Font.PLAIN;
			if((bold!=null && bold) && (italic!=null && italic)){
				fontStyle=Font.BOLD|Font.ITALIC;				
			}else if(bold!=null && bold){
				fontStyle=Font.BOLD;							
			}else if(italic!=null && italic){
				fontStyle=Font.ITALIC;							
			}
			double size=fontSize * 1.1;
			BigDecimal bigData=Utils.toBigDecimal(size);
			int s=bigData.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
			this.font=new Font(fontFamily,fontStyle,s);
		}
		return this.font;
	}
}
