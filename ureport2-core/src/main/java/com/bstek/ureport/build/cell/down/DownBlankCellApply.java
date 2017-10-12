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
package com.bstek.ureport.build.cell.down;

import java.util.List;

import com.bstek.ureport.build.BindData;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.definition.ConditionPropertyItem;
import com.bstek.ureport.model.Cell;
import com.bstek.ureport.model.Column;
import com.bstek.ureport.model.Row;

/**
 * @author Jacky.gao
 * @since 2017年3月2日
 */
public class DownBlankCellApply {
	private int rowSize;
	private Cell cell;
	private Context context;
	private DownDuplocatorWrapper downDuplocatorWrapper;
	public DownBlankCellApply(int rowSize,Cell cell,Context context,DownDuplocatorWrapper downDuplocatorWrapper) {
		this.rowSize=rowSize;
		this.cell=cell;
		this.context=context;
		this.downDuplocatorWrapper=downDuplocatorWrapper;
	}
	public boolean useBlankCell(int index,BindData bindData){
		if(context.getBlankCellsMap().size()==0){
			return false;
		}
		int nextRowNumber=cell.getRow().getRowNumber()+rowSize*(index-1)+rowSize;
		Row nextRow=context.getRow(nextRowNumber);
		Cell blankCell=null;
		if(nextRow!=null){
			blankCell=context.getBlankCell(nextRow, cell.getColumn());
		}
		if(blankCell==null){
			return false;
		}
		context.removeBlankCell(blankCell);
		blankCell.setValue(cell.getValue());
		blankCell.setProcessed(true);
		blankCell.setData(bindData.getValue());
		blankCell.setFormatData(bindData.getLabel());
		blankCell.setBindData(bindData.getDataList());
		blankCell.setConditionPropertyItems(cell.getConditionPropertyItems());
		List<ConditionPropertyItem> conditionPropertyItems=blankCell.getConditionPropertyItems();
		if(conditionPropertyItems!=null && conditionPropertyItems.size()>0){
			context.getReport().getLazyComputeCells().add(blankCell);
		}else{
			blankCell.doFormat();
			blankCell.doDataWrapCompute(context);
		}
		
		processChildrenCell(cell,blankCell,index);
		return true;
	}
	private void processChildrenCell(Cell originalCell,Cell leftParentCell,int index){
		List<CellDownDuplicator> children=downDuplocatorWrapper.fetchChildrenDuplicator(originalCell);
		if(children==null){
			return;
		}
		for(CellDownDuplicator child:children){
			Cell childCell=child.getCell();
			Cell targetCell=getChildBlankCell(childCell,index);
			if(targetCell==null){
				continue;
			}
			context.removeBlankCell(targetCell);
			targetCell.setLeftParentCell(leftParentCell);
			targetCell.setValue(childCell.getValue());
			if(targetCell.getTopParentCell()==originalCell){
				targetCell.setTopParentCell(leftParentCell);
			}
			context.addUnprocessedCell(targetCell);
			processChildrenCell(childCell,targetCell,index);
		}
	}
	
	private Cell getChildBlankCell(Cell childCell,int index){
		int nextChildRowNumber=childCell.getRow().getRowNumber()+rowSize*(index-1)+rowSize;
		Row nextChildRow=context.getRow(nextChildRowNumber);
		Column col=childCell.getColumn();
		Cell targetCell=context.getBlankCell(nextChildRow, col);
		return targetCell;
	}
}
