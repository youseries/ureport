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
package com.bstek.ureport.export.builder.down;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bstek.ureport.Range;
import com.bstek.ureport.definition.BlankCellInfo;
import com.bstek.ureport.definition.CellDefinition;
import com.bstek.ureport.parser.BuildUtils;

/**
 * @author Jacky.gao
 * @since 2017年2月27日
 */
public class LeftParentCellCreator {
	public List<Range> buildParentCells(CellDefinition cell){
		List<Range> rangeList=new ArrayList<Range>();
		Range childRange=buildChildrenCellRange(cell);
		List<CellDefinition> parentCells=new ArrayList<CellDefinition>();
		collectParentCells(cell,parentCells);
		buildParents(cell, parentCells, childRange, rangeList);
		return rangeList;
	}
	private void collectParentCells(CellDefinition cell,List<CellDefinition> parentCells){
		CellDefinition leftParentCell=cell.getLeftParentCell();
		if(leftParentCell==null){
			return;
		}
		parentCells.add(leftParentCell);
		collectParentCells(leftParentCell,parentCells);
	}
	
	private void buildParents(CellDefinition mainCell,List<CellDefinition> parentCells,Range childRange,List<Range> rangeList){
		int rowNumberStart=mainCell.getRowNumber();
		int rowNumberEnd=BuildUtils.buildRowNumberEnd(mainCell, rowNumberStart);
		rangeList.add(new Range(rowNumberStart,rowNumberEnd));
		
		int start=childRange.getStart(),end=childRange.getEnd();
		Map<String,BlankCellInfo> newBlankCellsMap=mainCell.getNewBlankCellsMap();
		boolean increase=true;
		for(CellDefinition parentCell:parentCells){
			String parentCellName=parentCell.getName();
			int parentRowNumberStart=parentCell.getRowNumber();
			int parentRowNumberEnd=BuildUtils.buildRowNumberEnd(parentCell,parentRowNumberStart);
			int offset=parentRowNumberStart-rowNumberStart;
			int parentRowSpan=parentCell.getRowSpan();
			boolean isOut=assertOut(parentCell, mainCell, childRange);
			if(isOut){
				increase=false;
				boolean doBlank=assertDoBlank(parentCell.getLeftParentCell(), parentCell, mainCell, childRange);
				if(doBlank){
					newBlankCellsMap.put(parentCellName, new BlankCellInfo(offset,parentRowSpan,true));
					rangeList.add(new Range(parentRowNumberStart,parentRowNumberEnd));
				}
				continue;
			}
			if((start!=-1 && start<parentRowNumberStart) || end>parentRowNumberEnd){
				newBlankCellsMap.put(parentCellName, new BlankCellInfo(offset,parentRowSpan,true));
				rangeList.add(new Range(parentRowNumberStart,parentRowNumberEnd));
				increase=false;
				continue;
			}
			if(increase){
				mainCell.getIncreaseSpanCellNames().add(parentCellName);				
			}else{
				newBlankCellsMap.put(parentCellName, new BlankCellInfo(offset,parentRowSpan,true));
				rangeList.add(new Range(parentRowNumberStart,parentRowNumberEnd));
			}
		}
	}
	
	private boolean assertDoBlank(CellDefinition nextParentCell,CellDefinition parentCell,CellDefinition mainCell,Range childRange){
		if(nextParentCell==null){
			return false;
		}
		boolean isOut=assertOut(nextParentCell, mainCell, childRange);
		if(isOut){
			return assertDoBlank(nextParentCell.getLeftParentCell(), parentCell, mainCell, childRange);
		}
		int start=parentCell.getRowNumber(),end=BuildUtils.buildRowNumberEnd(parentCell, start);
		int nextStart=nextParentCell.getRowNumber();
		if(nextStart<=end){
			return true;
		}
		return assertDoBlank(nextParentCell.getLeftParentCell(), parentCell, mainCell, childRange);
	}
	
	private boolean assertOut(CellDefinition parentCell,CellDefinition mainCell,Range childRange){
		int start=parentCell.getRowNumber(),end=BuildUtils.buildRowNumberEnd(parentCell, start);
		int rangeStart=childRange.getStart(),rangeEnd=childRange.getEnd();
		if(rangeStart!=-1){
			if((start>=rangeStart && start<=rangeEnd) || (end>=rangeStart && end<=rangeEnd)){
				return false;
			}
		}
		int rowStart=mainCell.getRowNumber(),rowEnd=BuildUtils.buildRowNumberEnd(mainCell, rowStart);
		if((start>=rowStart && start<=rowEnd) || (end>=rowStart && end<=rowEnd) || (start<=rowStart && end>=rowEnd)){
			return false;
		}
		return true;
	}
	
	private Range buildChildrenCellRange(CellDefinition mainCell){
		Range range=new Range();
		List<CellDefinition> childrenCells=mainCell.getRowChildrenCells();
		for(CellDefinition childCell:childrenCells){
			int childRowNumberStart=childCell.getRowNumber();
			int childRowSpan=childCell.getRowSpan();
			childRowSpan=childRowSpan>0 ? childRowSpan-1 :childRowSpan;
			int childRowNumberEnd=childRowNumberStart+childRowSpan;
			if(range.getStart()==-1 || childRowNumberStart<range.getStart()){
				range.setStart(childRowNumberStart);
			}
			if(childRowNumberEnd>range.getEnd()){
				range.setEnd(childRowNumberEnd);
			}
		}
		return range;
	}
}
