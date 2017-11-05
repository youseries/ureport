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

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.bstek.ureport.definition.Border;
import com.bstek.ureport.definition.BorderStyle;
import com.bstek.ureport.definition.CellStyle;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;

/**
 * @author Jacky.gao
 * @since 2014年4月17日
 */
public class CellBorderEvent implements PdfPCellEvent {
	private CellStyle style;
	private CellStyle customStyle;
	public CellBorderEvent(CellStyle style,CellStyle customStyle){
		this.style=style;
		this.customStyle=customStyle;
	}
	public void cellLayout(PdfPCell cell, Rectangle position,PdfContentByte[] canvases) {
		Border leftBorder=style.getLeftBorder();
		if(customStyle!=null && customStyle.getLeftBorder()!=null){
			leftBorder=customStyle.getLeftBorder();
		}
		if(leftBorder!=null){
			PdfContentByte cb=bulidCellBorder(canvases,leftBorder);
			cb.moveTo(position.getLeft(), position.getTop());
			cb.lineTo(position.getLeft(), position.getBottom());
			cb.stroke();
			if(leftBorder.getStyle().equals(BorderStyle.doublesolid)){
				cb.moveTo(position.getLeft()+2, position.getTop()-2);
				cb.lineTo(position.getLeft()+2, position.getBottom()+2);
            	cb.stroke();
        	}
			cb.restoreState();
		}
        Border topBorder=style.getTopBorder();
        if(customStyle!=null && customStyle.getTopBorder()!=null){
        	topBorder=customStyle.getTopBorder();
		}
        if(topBorder!=null){
        	PdfContentByte cb=bulidCellBorder(canvases,topBorder);
			cb.moveTo(position.getLeft(), position.getTop());
			cb.lineTo(position.getRight(), position.getTop());
			cb.stroke();
			if(topBorder.getStyle().equals(BorderStyle.doublesolid)){
				cb.moveTo(position.getLeft()+2, position.getTop()-2);
				cb.lineTo(position.getRight()-2, position.getTop()-2);
            	cb.stroke();
        	}
			cb.restoreState();
        }
        Border rightBorder=style.getRightBorder();
        if(customStyle!=null && customStyle.getRightBorder()!=null){
        	rightBorder=customStyle.getRightBorder();
		}
        if(rightBorder!=null){
        	PdfContentByte cb=bulidCellBorder(canvases,rightBorder);
        	cb.moveTo(position.getRight(), position.getTop());
        	cb.lineTo(position.getRight(), position.getBottom());
        	cb.stroke();
        	if(rightBorder.getStyle().equals(BorderStyle.doublesolid)){
        		cb.moveTo(position.getRight()-2, position.getTop()-2);
            	cb.lineTo(position.getRight()-2, position.getBottom()+2);
            	cb.stroke();
        	}
        	cb.restoreState();
        }
        Border bottomBorder=style.getBottomBorder();
        if(customStyle!=null && customStyle.getBottomBorder()!=null){
        	bottomBorder=customStyle.getBottomBorder();
		}
        if(bottomBorder!=null){
        	PdfContentByte cb=bulidCellBorder(canvases,bottomBorder);
        	cb.moveTo(position.getLeft(), position.getBottom());
        	cb.lineTo(position.getRight(), position.getBottom());
        	cb.stroke();
        	if(bottomBorder.getStyle().equals(BorderStyle.doublesolid)){
        		cb.moveTo(position.getLeft()+2, position.getBottom()+2);
            	cb.lineTo(position.getRight()-2, position.getBottom()+2);
            	cb.stroke();
        	}
        	cb.restoreState();
        }
	}
	private PdfContentByte bulidCellBorder(PdfContentByte[] canvases,Border border){
		PdfContentByte cb=canvases[PdfPTable.LINECANVAS];
		cb.saveState();
		BigDecimal w=new BigDecimal(border.getWidth());
		cb.setLineWidth(w.divide(new BigDecimal(2),10,RoundingMode.HALF_UP).floatValue());
		if(border.getStyle().equals(BorderStyle.dashed)){
			cb.setLineDash(new float[]{2f,3f,1f},2);
		}
		String borderColor[]=border.getColor().split(",");
		cb.setColorStroke(new BaseColor(Integer.valueOf(borderColor[0]),Integer.valueOf(borderColor[1]),Integer.valueOf(borderColor[2])));
		return cb;
	}
}
