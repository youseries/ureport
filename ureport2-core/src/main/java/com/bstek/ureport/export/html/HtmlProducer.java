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
package com.bstek.ureport.export.html;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.bstek.ureport.build.Context;
import com.bstek.ureport.build.paging.Page;
import com.bstek.ureport.chart.ChartData;
import com.bstek.ureport.definition.Alignment;
import com.bstek.ureport.definition.Border;
import com.bstek.ureport.definition.CellStyle;
import com.bstek.ureport.model.Cell;
import com.bstek.ureport.model.Column;
import com.bstek.ureport.model.Image;
import com.bstek.ureport.model.Report;
import com.bstek.ureport.model.Row;

/**
 * @author Jacky.gao
 * @since 2016年12月30日
 */
public class HtmlProducer{
	public String produce(Report report) {
		List<Row> rows=report.getRows();
		List<Column> columns=report.getColumns();
		Map<Row, Map<Column, Cell>> cellMap=report.getRowColCellMap();
		StringBuilder sb = buildTable(report.getContext(),rows, columns, cellMap,false,false);
		return sb.toString();
	}
	
	public String produce(Context context,List<Page> pages,int columnMargin,boolean breakPage){
		int pageSize=pages.size();
		int singleTableWidth=buildTableWidth(pages.get(0).getColumns());
		int tableWidth=singleTableWidth*pageSize+columnMargin*(pageSize-1);
		String bgStyle="";
		String bgImage=context.getReport().getPaper().getBgImage();
		if(StringUtils.isNotBlank(bgImage)){
			bgStyle=";background:url("+bgImage+") no-repeat";
		}
		StringBuilder sb=new StringBuilder();
		if(breakPage){
			sb.append("<table border='0' class='page-break' style='margin:auto;border-collapse:collapse;width:"+tableWidth+"pt"+bgStyle+"'>");			
		}else{
			sb.append("<table border='0' class='page-break' style='margin:auto;border-collapse:collapse;width:"+tableWidth+"pt"+bgStyle+"'>");			
		}
		sb.append("<tr>");
		for(int i=0;i<pageSize;i++){
			if(i>0){
				sb.append("<td style='width:"+columnMargin+"pt'></td>");
			}
			Page page=pages.get(i);
			String table=produce(context,page,false);
			sb.append("<td style='width:"+singleTableWidth+"pt;vertical-align:top'>");
			sb.append(table);
			sb.append("</td>");
		}
		sb.append("</tr>");
		sb.append("</table>");
		return sb.toString();
	}
	
	public String produce(Context context,Page page,boolean breakPage) {
		List<Row> rows=page.getRows();
		List<Column> columns=page.getColumns();
		Map<Row, Map<Column, Cell>> cellMap=context.getReport().getRowColCellMap();
		StringBuilder sb = buildTable(context,rows, columns, cellMap,breakPage,true);
		return sb.toString();
	}
	
	private StringBuilder buildTable(Context context,List<Row> rows, List<Column> columns,Map<Row, Map<Column, Cell>> cellMap,boolean breakPage,boolean forPage) {
		StringBuilder sb=new StringBuilder();
		int tableWidth=buildTableWidth(columns);
		String bgStyle="";
		String bgImage=context.getReport().getPaper().getBgImage();
		if(StringUtils.isNotBlank(bgImage)){
			bgStyle=";background:url("+bgImage+") no-repeat";
		}
		if(breakPage){
			sb.append("<table class='page-break' border='0' style='margin:auto;border-collapse:collapse;width:"+tableWidth+"pt"+bgStyle+"'>");						
		}else{
			sb.append("<table border='0' style='margin:auto;border-collapse:collapse;width:"+tableWidth+"pt"+bgStyle+"'>");						
		}
		int colSize=columns.size();
		int rowSize=rows.size();
		for(int i=0;i<rowSize;i++){
			Row row=rows.get(i);
			if(!forPage && row.isForPaging()){
				continue;
			}
			int height=row.getRealHeight();
			if(height<1){
				continue;
			}
			sb.append("<tr style=\"height:"+height+"pt\">");
			for(int j=0;j<colSize;j++){
				Column col=columns.get(j);
				Cell cell=null;
				if(cellMap.containsKey(row)){
					Map<Column,Cell> colMap=cellMap.get(row);
					if(colMap.containsKey(col)){
						cell=colMap.get(col);
					}
				}
				if(cell==null || (!forPage && cell.isForPaging())){
					continue;
				}
				int colSpan=cell.getColSpan();
				int rowSpan=cell.getRowSpan();
				if(forPage){
					rowSpan=cell.getPageRowSpan();
				}
				if(rowSpan>0){
					if(colSpan>0){
						sb.append("<td rowspan=\""+rowSpan+"\" colspan=\""+colSpan+"\"");						
					}else{
						sb.append("<td rowspan=\""+rowSpan+"\"");						
					}
				}else{
					if(colSpan>0){
						sb.append("<td colspan=\""+colSpan+"\"");						
					}else{
						sb.append("<td");
					}
				}
				sb.append(" class='_"+cell.getName()+"' ");
				String style=buildCustomStyle(cell);
				sb.append(" "+style+"");
				sb.append(">");
				boolean hasLink=false;
				String linkURL=cell.getLinkUrl();
				if(StringUtils.isNotBlank(linkURL)){
					hasLink=true;
					String urlParameter=cell.buildLinkParameters(context);
					if(linkURL.indexOf("?")==-1){
						linkURL+="?"+urlParameter;
					}else{
						linkURL+="&"+urlParameter;
					}
					String target=cell.getLinkTargetWindow();
					if(StringUtils.isBlank(target))target="_self";
					sb.append("<a href=\""+linkURL+"\" target=\""+target+"\">");
				}
				Object obj=(cell.getFormatData()== null) ? "&nbsp;" : cell.getFormatData();
				if(obj instanceof Image){
					Image img=(Image)obj;
					String path=img.getPath();
					String imageType="image/png";
					if(StringUtils.isNotBlank(path)){
						path=path.toLowerCase();
						if(path.endsWith(".jpg") || path.endsWith(".jpeg")){
							imageType="image/jpeg";
						}else if(path.endsWith(".gif")){
							imageType="image/gif";
						}
					}
					sb.append("<img src=\"data:"+imageType+";base64,"+img.getBase64Data()+"\"");
					sb.append(">");
				}else if(obj instanceof ChartData){
					ChartData chartData=(ChartData)obj;
					String canvasId=chartData.getId();
					int width=col.getWidth()-2;
					if(colSpan>0){
						width=buildWidth(columns,j,colSpan)-2;
					}
					if(rowSpan>0){
						height=buildHeight(rows,i,rowSpan)-2;
					}else{
						height-=2;
					}
					sb.append("<div style=\"position: relative;width:"+width+"pt;height:"+height+"pt\">");
					sb.append("<canvas id=\""+canvasId+"\" style=\"width:"+width+"px !important;height:"+height+"px !important\"></canvas>");
					sb.append("</div>");
				}else{
					String text=obj.toString();
					text=text.replaceAll("\r\n", "<br>");
					text=text.replaceAll("\n", "<br>");
					text=text.replaceAll(" ", "&nbsp;");
					sb.append(text);					
				}
				if(hasLink){
					sb.append("</a>");
				}
				sb.append("</td>");
			}
			sb.append("</tr>");
		}
		sb.append("</table>");
		return sb;
	}
	
	private int buildWidth(List<Column> columns,int colIndex,int colSpan){
		int width=0;
		int start=colIndex,end=colIndex+colSpan;
		for(int i=start;i<end;i++){
			Column col=columns.get(i);
			width+=col.getWidth();
		}
		return width;
	}
	
	private int buildHeight(List<Row> rows,int rowIndex,int rowSpan){
		int height=0;
		int start=rowIndex,end=rowIndex+rowSpan;
		for(int i=start;i<end;i++){
			Row row=rows.get(i);
			height+=row.getRealHeight();
		}
		return height;
	}
	
	private String buildCustomStyle(Cell cell){
		CellStyle style=cell.getCustomCellStyle();
		CellStyle rowStyle=cell.getRow().getCustomCellStyle();
		CellStyle colStyle=cell.getColumn().getCustomCellStyle();
		if(style==null && rowStyle==null && colStyle==null)return "";
		StringBuilder sb=new StringBuilder();
		String forecolor=null;
		if(style!=null){
			forecolor=style.getForecolor();
		}
		if(rowStyle!=null){
			forecolor=rowStyle.getForecolor();
		}
		if(colStyle!=null){
			forecolor=colStyle.getForecolor();
		}
		if(StringUtils.isNotBlank(forecolor)){
			sb.append("color:rgb("+forecolor+");");
		}
		String bgcolor=null;
		if(style!=null){
			bgcolor=style.getBgcolor();
		}
		if(rowStyle!=null){
			bgcolor=rowStyle.getBgcolor();
		}
		if(colStyle!=null){
			bgcolor=colStyle.getBgcolor();
		}
		if(StringUtils.isNotBlank(bgcolor)){
			sb.append("background-color:rgb("+bgcolor+");");
		}
		String fontFamily=null;
		if(style!=null){
			fontFamily=style.getFontFamily();
		}
		if(rowStyle!=null){
			fontFamily=rowStyle.getFontFamily();
		}
		if(colStyle!=null){
			fontFamily=colStyle.getFontFamily();
		}
		if(StringUtils.isNotBlank(fontFamily)){
			sb.append("font-family:"+fontFamily+";");
		}
		int fontSize=0;
		if(style!=null){
			fontSize=style.getFontSize();
		}
		if(rowStyle!=null){
			fontSize=rowStyle.getFontSize();
		}
		if(colStyle!=null){
			fontSize=colStyle.getFontSize();
		}
		if(fontSize>0){
			sb.append("font-size:"+fontSize+"pt;");
		}
		Boolean bold=null;
		if(style!=null){
			bold=style.getBold();
		}
		if(rowStyle!=null){
			bold=rowStyle.getBold();
		}
		if(colStyle!=null){
			bold=colStyle.getBold();
		}
		if(bold!=null){
			if(bold){
				sb.append("font-weight:bold;");
			}else{
				sb.append("font-weight:normal;");				
			}
		}
		Boolean italic=null;
		if(style!=null){
			italic=style.getItalic();
		}
		if(rowStyle!=null){
			italic=rowStyle.getItalic();
		}
		if(colStyle!=null){
			italic=colStyle.getItalic();
		}
		if(italic!=null){
			if(italic){
				sb.append("font-style:italic;");
			}else{
				sb.append("font-style:normal;");
				
			}
		}
		Boolean underline=null;
		if(style!=null){
			underline=style.getUnderline();
		}
		if(rowStyle!=null){
			underline=rowStyle.getUnderline();
		}
		if(colStyle!=null){
			underline=colStyle.getUnderline();
		}
		if(underline!=null){
			if(underline){
				sb.append("text-decoration:underline;");
			}else{
				sb.append("text-decoration:none;");
			}
		}
		Alignment align=null;
		if(style!=null){
			align=style.getAlign();
		}
		if(rowStyle!=null){
			align=rowStyle.getAlign();
		}
		if(colStyle!=null){
			align=colStyle.getAlign();
		}
		if(align!=null){
			sb.append("text-align:"+align.name()+";");
		}
		Alignment valign=null;
		if(style!=null){
			valign=style.getValign();
		}
		if(rowStyle!=null){
			valign=rowStyle.getValign();
		}
		if(colStyle!=null){
			valign=colStyle.getValign();
		}
		if(valign!=null){
			sb.append("vertical-align:"+valign.name()+";");
		}
		Border border=null;
		if(style!=null){
			border=style.getLeftBorder();
		}
		if(border!=null){
			sb.append("border-left:"+border.getStyle().name()+" "+border.getWidth()+"px rgb("+border.getColor()+");");
		}
		Border rightBorder=null;
		if(style!=null){
			rightBorder=style.getRightBorder();
		}
		if(rightBorder!=null){
			sb.append("border-right:"+rightBorder.getStyle().name()+" "+rightBorder.getWidth()+"px rgb("+rightBorder.getColor()+");");
		}
		Border topBorder=null;
		if(style!=null){
			topBorder=style.getTopBorder();
		}
		if(topBorder!=null){
			sb.append("border-top:"+topBorder.getStyle().name()+" "+topBorder.getWidth()+"px rgb("+topBorder.getColor()+");");
		}
		Border bottomBorder=null;
		if(style!=null){
			bottomBorder=style.getBottomBorder();
		}
		if(bottomBorder!=null){
			sb.append("border-bottom:"+bottomBorder.getStyle().name()+" "+bottomBorder.getWidth()+"px rgb("+bottomBorder.getColor()+");");
		}
		if(sb.length()>0){
			int colWidth=cell.getColumn().getWidth();
			sb.append("width:"+colWidth+"pt");
			sb.insert(0, "style=\"");
			sb.append("\"");
		}
		return sb.toString();
	}
	
	private int buildTableWidth(List<Column> columns){
		int width=0;
		for(Column col:columns){
			width+=col.getWidth();
		}
		return width;
	}
}
