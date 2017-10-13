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
package com.bstek.ureport.build.cell.right;

import java.util.List;
import java.util.Map;

import com.bstek.ureport.Range;
import com.bstek.ureport.build.BindData;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.build.cell.DuplicateType;
import com.bstek.ureport.build.cell.ExpandBuilder;
import com.bstek.ureport.definition.BlankCellInfo;
import com.bstek.ureport.definition.ConditionPropertyItem;
import com.bstek.ureport.model.Cell;
import com.bstek.ureport.model.Column;

/**
 * @author Jacky.gao
 * @since 2016年11月1日
 */
public class RightExpandBuilder extends ExpandBuilder {
	@Override
	public Cell buildCell(List<BindData> dataList, Cell cell, Context context) {
		Range duplicateRange=cell.getDuplicateRange();
		int mainCellColNumber=cell.getColumn().getColumnNumber();
		Range colRange = buildColRange(cell,duplicateRange,mainCellColNumber);
		RightDuplocatorWrapper rightDuplocatorWrapper=buildCellRightDuplicator(cell,context,colRange);
		int colSize=colRange.getEnd()-colRange.getStart()+1;
		RightBlankCellApply rightBlankCellApply=new RightBlankCellApply(colSize,cell,context,rightDuplocatorWrapper);
		CellRightDuplicateUnit unit=new CellRightDuplicateUnit(context,rightDuplocatorWrapper,cell,mainCellColNumber,colSize);
		Cell lastCell=cell;
		for (int i = 0; i < dataList.size(); i++) {
			BindData bindData = dataList.get(i);
			if (i == 0) {
				cell.setData(bindData.getValue());
				cell.setFormatData(bindData.getLabel());
				cell.setBindData(bindData.getDataList());
				List<ConditionPropertyItem> conditionPropertyItems=cell.getConditionPropertyItems();
				if(conditionPropertyItems!=null && conditionPropertyItems.size()>0){
					context.getReport().getLazyComputeCells().add(cell);
				}else{
					cell.doFormat();
					cell.doDataWrapCompute(context);
				}
				continue;
			}
			boolean useBlank=rightBlankCellApply.useBlankCell(i, bindData);
			if(useBlank){
				continue;
			}
			Cell newCell = cell.newCell();
			newCell.setData(bindData.getValue());
			newCell.setFormatData(bindData.getLabel());
			newCell.setBindData(bindData.getDataList());
			newCell.setProcessed(true);
			Cell topParentCell=cell.getTopParentCell();
			if(topParentCell!=null){
				topParentCell.addColumnChild(newCell);
			}
			Cell leftParentCell=cell.getLeftParentCell();
			if(leftParentCell!=null){
				leftParentCell.addRowChild(newCell);
			}
			unit.duplicate(newCell,i);
			lastCell=newCell;
		}
		unit.complete();
		return lastCell;
	}
	
	private Range buildColRange(Cell cell,Range range,int mainCellColNumber){
		int start=mainCellColNumber+range.getStart();
		int end=mainCellColNumber+range.getEnd();
		return new Range(start,end);
	}
	
	private RightDuplocatorWrapper buildCellRightDuplicator(Cell cell,Context context,Range range){
		RightDuplocatorWrapper duplicatorWrapper=new RightDuplocatorWrapper(cell.getName());
		buildParentCellDuplicators(cell,cell,duplicatorWrapper);
		for(int i=range.getStart();i<=range.getEnd();i++){
			Column col=context.getColumn(i);
			List<Cell> colCells=col.getCells();
			for(Cell colCell:colCells){
				buildDuplicator(duplicatorWrapper,cell, colCell,i);
			}
		}
		return duplicatorWrapper;
	}
	
	private void buildParentCellDuplicators(Cell cell,Cell mainCell,RightDuplocatorWrapper duplicatorWrapper){
		Cell topParentCell=cell.getTopParentCell();
		if(topParentCell==null){
			return;
		}
		buildDuplicator(duplicatorWrapper,mainCell,topParentCell,0);			
		buildParentCellDuplicators(topParentCell,mainCell,duplicatorWrapper);
	}

	private void buildDuplicator(RightDuplocatorWrapper duplicatorWrapper,Cell mainCell,Cell currentCell,int currentCellColNumber) {
		if(mainCell.equals(currentCell)){
			return;
		}
		String name=currentCell.getName();
		Map<String, BlankCellInfo> newBlankCellNamesMap=mainCell.getNewBlankCellsMap();
		List<String> increaseCellNames=mainCell.getIncreaseSpanCellNames();
		List<String> newCellNames=mainCell.getNewCellNames();
		if(newBlankCellNamesMap.containsKey(name)){
			if(!duplicatorWrapper.contains(currentCell)){
				CellRightDuplicator cellDuplicator=new CellRightDuplicator(currentCell,DuplicateType.Blank,newBlankCellNamesMap.get(name),currentCellColNumber);
				duplicatorWrapper.addCellRightDuplicator(cellDuplicator);				
			}
		}else if(increaseCellNames.contains(name)){
			if(!duplicatorWrapper.contains(currentCell)){
				CellRightDuplicator cellDuplicator=new CellRightDuplicator(currentCell,DuplicateType.IncreseSpan,currentCellColNumber);
				duplicatorWrapper.addCellRightDuplicator(cellDuplicator);					
			}
		}else if(newCellNames.contains(name)){
			CellRightDuplicator cellDuplicator=new CellRightDuplicator(currentCell,DuplicateType.Duplicate,currentCellColNumber);
			duplicatorWrapper.addCellRightDuplicator(cellDuplicator);				
		}else if(mainCell.getName().equals(name)){
			CellRightDuplicator cellDuplicator=new CellRightDuplicator(currentCell,DuplicateType.Self,currentCellColNumber);
			duplicatorWrapper.addCellRightDuplicator(cellDuplicator);
		}
	}
}
