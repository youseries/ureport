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
package com.bstek.ureport.export.builder.right;

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
public class TopParentCellCreator {
	public List<Range> buildParentCells(CellDefinition cell){
		List<Range> rangeList=new ArrayList<Range>();
		Range childRange=buildChildrenCellRange(cell);
		List<CellDefinition> parentCells=new ArrayList<CellDefinition>();
		collectParentCells(cell,parentCells);
		buildParents(cell, parentCells, childRange, rangeList);
		return rangeList;
	}
	private void collectParentCells(CellDefinition cell,List<CellDefinition> parentCells){
		CellDefinition topParentCell=cell.getTopParentCell();
		if(topParentCell==null){
			return;
		}
		parentCells.add(topParentCell);
		collectParentCells(topParentCell,parentCells);
	}
	
	private void buildParents(CellDefinition mainCell,List<CellDefinition> parentCells,Range childRange,List<Range> rangeList){
		int colNumberStart=mainCell.getColumnNumber();
		int colNumberEnd=BuildUtils.buildColNumberEnd(mainCell, colNumberStart);
		rangeList.add(new Range(colNumberStart,colNumberEnd));
		
		int start=childRange.getStart(),end=childRange.getEnd();
		Map<String,BlankCellInfo> newBlankCellsMap=mainCell.getNewBlankCellsMap();
		boolean increase=true;
		for(CellDefinition parentCell:parentCells){
			String parentCellName=parentCell.getName();
			int parentColNumberStart=parentCell.getColumnNumber();
			int parentColNumberEnd=BuildUtils.buildColNumberEnd(parentCell,parentColNumberStart);
			int offset=parentColNumberStart-colNumberStart;
			int parentColSpan=parentCell.getColSpan();
			boolean isOut=assertOut(parentCell, mainCell, childRange);
			if(isOut){
				increase=false;
				boolean doBlank=assertDoBlank(parentCell.getTopParentCell(), parentCell, mainCell, childRange);
				if(doBlank){
					newBlankCellsMap.put(parentCellName, new BlankCellInfo(offset,parentColSpan,true));
					rangeList.add(new Range(parentColNumberStart,parentColNumberEnd));
				}
				continue;
			}
			if((start!=-1 && start<parentColNumberStart) || end>parentColNumberEnd){
				newBlankCellsMap.put(parentCellName, new BlankCellInfo(offset,parentColSpan,true));
				rangeList.add(new Range(parentColNumberStart,parentColNumberEnd));
				increase=false;
				continue;
			}
			if(increase){
				mainCell.getIncreaseSpanCellNames().add(parentCellName);				
			}else{
				newBlankCellsMap.put(parentCellName, new BlankCellInfo(offset,parentColSpan,true));
				rangeList.add(new Range(parentColNumberStart,parentColNumberEnd));
			}
		}
	}
	
	
	private boolean assertDoBlank(CellDefinition nextParentCell,CellDefinition parentCell,CellDefinition mainCell,Range childRange){
		if(nextParentCell==null){
			return false;
		}
		boolean isOut=assertOut(nextParentCell, mainCell, childRange);
		if(isOut){
			return assertDoBlank(nextParentCell.getTopParentCell(), parentCell, mainCell, childRange);
		}
		int start=parentCell.getColumnNumber(),end=BuildUtils.buildColNumberEnd(parentCell, start);
		int nextStart=nextParentCell.getColumnNumber();
		if(nextStart<=end){
			return true;
		}
		return assertDoBlank(nextParentCell.getTopParentCell(), parentCell, mainCell, childRange);
	}
	
	private boolean assertOut(CellDefinition parentCell,CellDefinition mainCell,Range childRange){
		int start=parentCell.getColumnNumber(),end=BuildUtils.buildColNumberEnd(parentCell, start);
		int rangeStart=childRange.getStart(),rangeEnd=childRange.getEnd();
		if(rangeStart!=-1){
			if((start>=rangeStart && start<=rangeEnd) || (end>=rangeStart && end<=rangeEnd)){
				return false;
			}
		}
		int colStart=mainCell.getColumnNumber(),colEnd=BuildUtils.buildColNumberEnd(mainCell, colStart);
		if((start>=colStart && start<=colEnd) || (end>=colStart && end<=colEnd) || (start<=colStart && end>=colEnd)){
			return false;
		}
		return true;
	}
	
	private Range buildChildrenCellRange(CellDefinition mainCell){
		Range range=new Range();
		List<CellDefinition> childrenCells=mainCell.getColumnChildrenCells();
		for(CellDefinition childCell:childrenCells){
			int childColumnNumberStart=childCell.getColumnNumber();
			int childColSpan=childCell.getColSpan();
			childColSpan=childColSpan>0 ? childColSpan-1 :childColSpan;
			int childColumnNumberEnd=childColumnNumberStart+childColSpan;
			if(range.getStart()==-1 || childColumnNumberStart<range.getStart()){
				range.setStart(childColumnNumberStart);
			}
			if(childColumnNumberEnd>range.getEnd()){
				range.setEnd(childColumnNumberEnd);
			}
		}
		return range;
	}
}
