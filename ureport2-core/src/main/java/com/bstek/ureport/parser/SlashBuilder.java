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
package com.bstek.ureport.parser;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageOutputStream;

import org.springframework.util.Base64Utils;

import com.bstek.ureport.cache.ResourceCache;
import com.bstek.ureport.definition.CellDefinition;
import com.bstek.ureport.definition.CellStyle;
import com.bstek.ureport.definition.ColumnDefinition;
import com.bstek.ureport.definition.ReportDefinition;
import com.bstek.ureport.definition.RowDefinition;
import com.bstek.ureport.definition.value.Slash;
import com.bstek.ureport.definition.value.SlashValue;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.utils.UnitUtils;

/**
 * @author Jacky.gao
 * @since 2017年3月17日
 */
public class SlashBuilder {
	public void buildSlashImage(CellDefinition cell, ReportDefinition report){
		int rowNumber=cell.getRowNumber();
		int colNumber=cell.getColumnNumber();
		int rowSpan=cell.getRowSpan();
		int colSpan=cell.getColSpan();
		int verticalBorderWidth=0,horizontalBorderWidth=0;
		CellStyle cellStyle=cell.getCellStyle();
		if(cellStyle.getLeftBorder()!=null){
			verticalBorderWidth+=cellStyle.getLeftBorder().getWidth();
		}
		if(cellStyle.getRightBorder()!=null){
			verticalBorderWidth+=cellStyle.getRightBorder().getWidth();
		}
		if(cellStyle.getTopBorder()!=null){
			horizontalBorderWidth=cellStyle.getTopBorder().getWidth();
		}
		if(cellStyle.getBottomBorder()!=null){
			horizontalBorderWidth=cellStyle.getBottomBorder().getWidth();
		}
		int width=0;
		int height=0;
		if(rowSpan==0){
			rowSpan=1;
		}
		if(colSpan==0){
			colSpan=1;
		}
		List<ColumnDefinition> columns=report.getColumns();
		List<RowDefinition> rows=report.getRows();
		for(int i=colNumber;i<(colNumber+colSpan);i++){
			ColumnDefinition col=columns.get(i-1);
			width+=UnitUtils.pointToPixel(col.getWidth());
		}
		for(int i=rowNumber;i<(rowNumber+rowSpan);i++){
			RowDefinition row=rows.get(i-1);
			height+=UnitUtils.pointToPixel(row.getHeight());
		}
		width-=horizontalBorderWidth;
		height-=verticalBorderWidth;
		SlashValue content=(SlashValue)cell.getValue();
		BufferedImage image=new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		Graphics2D g=(Graphics2D)image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,	RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		Font font=cellStyle.getFont();
		g.setFont(font);
		g.setStroke(new BasicStroke(1f));
		String bgColor=cellStyle.getBgcolor();
		if(bgColor==null){
			bgColor="255,255,255";
		}
		g.setColor(getColor(bgColor));
		g.fillRect(0, 0, width, height);
		AffineTransform transform=g.getTransform();
		int allRowHeight=0;
		int index=0;
		String lc=cellStyle.getForecolor();
		if(lc==null){
			lc="0,0,0";
		}
		Color lineColor=getColor(lc);
		String fc=cellStyle.getForecolor();
		if(fc==null){
			fc="0,0,0";
		}
		Color fontColor=getColor(fc);
		for(int i=rowNumber;i<(rowNumber+rowSpan);i++){
			Slash slash=getSlash(content, index);
			if(slash==null){
				break;
			}
			String text=slash.getText();
			if(text==null){
				break;
			}
			RowDefinition row=rows.get(i-1);
			int rowHeight=UnitUtils.pointToPixel(row.getHeight());
			g.setColor(fontColor);
			int x=slash.getX();
			int y=slash.getY();
			g.rotate(Math.toRadians(slash.getDegree()), x, y);
			g.drawString(text, x, y);
			g.setTransform(transform);
			g.setColor(lineColor);
			int h=allRowHeight+rowHeight;
			if(i==(rowNumber+rowSpan-1)){
				h=allRowHeight+(rowHeight/3)*2;
			}
			g.drawLine(0, 0, width, h);
			allRowHeight+=rowHeight;
			index++;
		}
		Slash slash=getSlash(content, index);
		if(slash!=null){
			String text=slash.getText();
			if(text!=null){
				int x=slash.getX();
				int y=slash.getY();
				g.rotate(Math.toRadians(slash.getDegree()), x, y);
				g.setColor(fontColor);
				g.drawString(text, x, y);
				g.setTransform(transform);
				index++;
			}
		}
		if(colSpan>0){
			colSpan--;
		}
		int colNumberStart=colNumber+colSpan;
		for(int i=colNumberStart;i>(colNumber-1);i--){
			slash=getSlash(content, index);
			if(slash==null){
				break;
			}
			String text=slash.getText();
			if(text==null){
				break;
			}
			int x=slash.getX();
			int y=slash.getY();
			g.rotate(Math.toRadians(slash.getDegree()), x, y);
			g.setColor(fontColor);
			g.drawString(text, x, y);
			g.setTransform(transform);
			ColumnDefinition col=columns.get(i-1);
			int colWidth=UnitUtils.pointToPixel(col.getWidth());
			int w=width;
			if(i==colNumberStart){
				w=width-colWidth/3;
			}
			g.setColor(lineColor);
			g.drawLine(0, 0, w, height);
			width-=colWidth;
			index++;
		}
		byte[] imageBytes=null;
		ByteArrayOutputStream byteOutput=new ByteArrayOutputStream();
		MemoryCacheImageOutputStream memoryImage=new MemoryCacheImageOutputStream(byteOutput);
		try{
			ImageIO.write(image, "png", memoryImage);
			imageBytes=byteOutput.toByteArray();
			String base64Data=Base64Utils.encodeToString(imageBytes);
			content.setBase64Data(base64Data);
		}catch(Exception ex){
			throw new ReportComputeException(ex);
		}finally{
				try {
					if(memoryImage!=null){
						memoryImage.close();
					}
					if(byteOutput!=null){
						byteOutput.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		g.dispose();
		String imageByteKey=buildKey(report.getReportFullName(), cell.getName());
		ResourceCache.putObject(imageByteKey, imageBytes);
	}
	
	private Slash getSlash(SlashValue content,int index){
		List<Slash> slashes=content.getSlashes();
		if(index<slashes.size()){
			return slashes.get(index);
		}
		return null;
	}
	
	private Color getColor(String text){
		if(text==null){
			return null;
		}
		String[] str=text.split(",");
		return new Color(Integer.valueOf(str[0]),Integer.valueOf(str[1]),Integer.valueOf(str[2]));
	}
	public static String buildKey(String reportFullName,String cellName){
		return "slash-"+reportFullName+"-"+cellName;
	}
}
