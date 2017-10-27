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
package com.bstek.ureport.export.builder.down;

import java.util.List;
import java.util.Map;

import com.bstek.ureport.Range;
import com.bstek.ureport.definition.BlankCellInfo;
import com.bstek.ureport.definition.CellDefinition;
import com.bstek.ureport.parser.BuildUtils;

/**
 * @author Jacky.gao
 * @since 2017年2月24日
 */
public class DownCellbuilder {
	private LeftParentCellCreator leftParentCellCreator=new LeftParentCellCreator();
	public void buildParentCell(CellDefinition cell,List<CellDefinition> cells){
		List<Range> rangeList=leftParentCellCreator.buildParentCells(cell);
		Range childRange=buildChildrenCells(cell,rangeList);
		buildChildrenBlankCells(cell,cells,childRange);
		Range rowRange=buildRowRange(rangeList);
		buildRowsBlankCells(cell,cells,rowRange);
		int start=rowRange.getStart(),end=rowRange.getEnd();
		int rowNumberStart=cell.getRowNumber(),rowNumberEnd=cell.getRowNumber();
		int rowSpan=cell.getRowSpan();
		if(rowSpan>0){
			rowNumberEnd+=rowSpan-1;
		}
		int rangeStart=0,rangeEnd=0;
		if(start!=-1){
			rangeStart=start-rowNumberStart;
		}
		if(end>rowNumberStart && end>rowNumberEnd){
			rangeEnd=end-rowNumberStart;
		}else{
			rangeEnd=rowNumberEnd-rowNumberStart;
		}
		Range duplicateRange=new Range(rangeStart,rangeEnd);
		cell.setDuplicateRange(duplicateRange);
	}
	

	private void buildRowsBlankCells(CellDefinition cell,List<CellDefinition> cells,Range range){
		Map<String,BlankCellInfo> blankCellNamesMap=cell.getNewBlankCellsMap();
		int start=range.getStart(),end=range.getEnd();
		int nextEnd=0;
		for(int i=start;i<=end;i++){
			for(CellDefinition cellDef:cells){
				String name=cellDef.getName();
				if(cellPrcessed(cell, name)){
					continue;
				}
				int rowNumber=cellDef.getRowNumber();
				if(rowNumber==i){
					int offset=rowNumber-cell.getRowNumber();
					blankCellNamesMap.put(name, new BlankCellInfo(offset,cellDef.getRowSpan(),false));
				}else if(rowNumber<i){
					int endRowNumber=BuildUtils.buildRowNumberEnd(cellDef, rowNumber);
					if(endRowNumber>=i){
						int offset=rowNumber-cell.getRowNumber();
						blankCellNamesMap.put(name, new BlankCellInfo(offset,cellDef.getRowSpan(),false));
						if(i>end && i>nextEnd){
							nextEnd=i;
						}
					}
				}
			}
		}
		if(nextEnd>end){
			buildRowsBlankCells(cell,cells,new Range(end,nextEnd));
		}
	}
	

	private Range buildRowRange(List<Range> rangeList){
		Range rowRange=new Range();
		for(Range range:rangeList){
			for(int i=range.getStart();i<=range.getEnd();i++){
				if(rowRange.getStart()==-1 || i<rowRange.getStart()){
					rowRange.setStart(i);
				}
				if(rowRange.getEnd()<i){
					rowRange.setEnd(i);
				}
			}
		}
		return rowRange;
	}
	
	private Range buildChildrenCells(CellDefinition cell,List<Range> rangeList){
		Range range=new Range();
		List<CellDefinition> rowChildrenCells=cell.getRowChildrenCells();
		for(CellDefinition childCell:rowChildrenCells){
			cell.getNewCellNames().add(childCell.getName());
			int rowNumber=childCell.getRowNumber();
			int endRowNumber=BuildUtils.buildRowNumberEnd(childCell, rowNumber);
			rangeList.add(new Range(rowNumber,endRowNumber));
			if(endRowNumber>range.getEnd()){
				range.setEnd(endRowNumber);
			}
			if(range.getStart()==-1 || rowNumber<range.getStart()){
				range.setStart(rowNumber);
			}
		}
		return range;
	}
	
	
	private void buildChildrenBlankCells(CellDefinition cell,List<CellDefinition> cells,Range childRange){
		int startRowNumber=cell.getRowNumber();
		int endRowNumber=BuildUtils.buildRowNumberEnd(cell, startRowNumber);
		int start=childRange.getStart(),end=childRange.getEnd();
		if(start!=-1 && start<startRowNumber){
			startRowNumber=start;
		}
		if(end>endRowNumber){
			endRowNumber=end;
		}
		Map<String,BlankCellInfo> blankCellNamesMap=cell.getNewBlankCellsMap();
		for(int i=startRowNumber;i<=endRowNumber;i++){
			for(CellDefinition c : cells){
				if(c.getRowNumber()!=i){
					continue;
				}
				if(c.equals(cell)){
					continue;
				}
				String name=c.getName();
				boolean contain=cellPrcessed(cell,name);
				if(contain){
					continue;
				}
				int offset=c.getRowNumber()-cell.getRowNumber();
				blankCellNamesMap.put(name, new BlankCellInfo(offset,c.getRowSpan(),false));
			}			
		}
	}
	
	private boolean cellPrcessed(CellDefinition cell,String name){
		List<String> newCellNames=cell.getNewCellNames();
		List<String> increaseCellNames=cell.getIncreaseSpanCellNames();
		Map<String,BlankCellInfo> blankCellNamesMap=cell.getNewBlankCellsMap();
		boolean contain=false;
		if(cell.getName().equals(name)){
			contain=true;			
		}
		if(newCellNames.contains(name)){
			contain=true;
		}
		if(increaseCellNames.contains(name)){
			contain=true;
		}
		if(blankCellNamesMap.containsKey(name)){
			contain=true;					
		}
		return contain;
	}
}
