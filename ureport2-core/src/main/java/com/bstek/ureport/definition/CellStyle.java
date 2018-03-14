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
package com.bstek.ureport.definition;

import java.awt.Font;
import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.bstek.ureport.export.pdf.font.FontBuilder;


/**
 * @author Jacky.gao
 * @since 2017年1月18日
 */
public class CellStyle implements Serializable{
	private static final long serialVersionUID = 8327688051735343849L;
	private String bgcolor;
	private String forecolor;
	private int fontSize;
	private String fontFamily;
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
			String fontName=fontFamily;
			if(StringUtils.isBlank(fontName)){
				fontName="宋体";
			}
			this.font=FontBuilder.getAwtFont(fontName, fontStyle, new Float(fontSize));
		}
		return this.font;
	}
}
