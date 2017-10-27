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
package com.bstek.ureport.export.builder.right;

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
public class RightCellbuilder {
	private TopParentCellCreator topParentCellCreator=new TopParentCellCreator();
	public void buildParentCell(CellDefinition cell,List<CellDefinition> cells){
		List<Range> rangeList=topParentCellCreator.buildParentCells(cell);
		Range childRange=buildChildrenCells(cell,rangeList);
		buildChildrenBlankCells(cell,cells,childRange);
		Range colRange=buildColumnRange(rangeList);
		buildColumnsBlankCells(cell,cells,colRange);
		int start=colRange.getStart(),end=colRange.getEnd();
		int colNumberStart=cell.getColumnNumber(),colNumberEnd=cell.getColumnNumber();
		int colSpan=cell.getColSpan();
		if(colSpan>0){
			colNumberEnd+=colSpan-1;
		}
		int rangeStart=0,rangeEnd=0;
		if(start!=-1){
			rangeStart=start-colNumberStart;
		}
		if(end>colNumberStart && end>colNumberEnd){
			rangeEnd=end-colNumberStart;
		}else{
			rangeEnd=colNumberEnd-colNumberStart;
		}
		Range duplicateRange=new Range(rangeStart,rangeEnd);
		cell.setDuplicateRange(duplicateRange);
	}
	

	private void buildColumnsBlankCells(CellDefinition cell,List<CellDefinition> cells,Range range){
		Map<String,BlankCellInfo> blankCellNamesMap=cell.getNewBlankCellsMap();
		int start=range.getStart(),end=range.getEnd();
		int nextEnd=0;
		for(int i=start;i<=end;i++){
			for(CellDefinition cellDef:cells){
				String name=cellDef.getName();
				if(cellPrcessed(cell, name)){
					continue;
				}
				int colNumber=cellDef.getColumnNumber();
				if(colNumber==i){
					int offset=colNumber-cell.getColumnNumber();
					blankCellNamesMap.put(name, new BlankCellInfo(offset,cellDef.getColSpan(),false));
				}else if(colNumber<i){
					int endColNumber=BuildUtils.buildColNumberEnd(cellDef, colNumber);
					if(endColNumber>=i){
						int offset=colNumber-cell.getColumnNumber();
						blankCellNamesMap.put(name, new BlankCellInfo(offset,cellDef.getColSpan(),false));
						if(i>end && i>nextEnd){
							nextEnd=i;
						}
					}
				}
			}
		}
		if(nextEnd>end){
			buildColumnsBlankCells(cell,cells,new Range(end,nextEnd));
		}
	}
	

	private Range buildColumnRange(List<Range> rangeList){
		Range colRange=new Range();
		for(Range range:rangeList){
			for(int i=range.getStart();i<=range.getEnd();i++){
				if(colRange.getStart()==-1 || i<colRange.getStart()){
					colRange.setStart(i);
				}
				if(colRange.getEnd()<i){
					colRange.setEnd(i);
				}
			}
		}
		return colRange;
	}
	
	private Range buildChildrenCells(CellDefinition cell,List<Range> rangeList){
		Range range=new Range();
		List<CellDefinition> childrenCells=cell.getColumnChildrenCells();
		for(CellDefinition childCell:childrenCells){
			cell.getNewCellNames().add(childCell.getName());
			int colNumber=childCell.getColumnNumber();
			int endColNumber=BuildUtils.buildColNumberEnd(childCell, colNumber);
			rangeList.add(new Range(colNumber,endColNumber));
			if(endColNumber>range.getEnd()){
				range.setEnd(endColNumber);
			}
			if(range.getStart()==-1 || colNumber<range.getStart()){
				range.setStart(colNumber);
			}
		}
		return range;
	}
	
	
	private void buildChildrenBlankCells(CellDefinition cell,List<CellDefinition> cells,Range childRange){
		int startColNumber=cell.getColumnNumber();
		int endColNumber=BuildUtils.buildColNumberEnd(cell, startColNumber);
		int start=childRange.getStart(),end=childRange.getEnd();
		if(start!=-1 && start<startColNumber){
			startColNumber=start;
		}
		if(end>endColNumber){
			endColNumber=end;
		}
		Map<String,BlankCellInfo> blankCellNamesMap=cell.getNewBlankCellsMap();
		for(int i=startColNumber;i<=endColNumber;i++){
			for(CellDefinition c : cells){
				if(c.getColumnNumber()!=i){
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
				int offset=c.getColumnNumber()-cell.getColumnNumber();
				blankCellNamesMap.put(name, new BlankCellInfo(offset,c.getColSpan(),false));
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
