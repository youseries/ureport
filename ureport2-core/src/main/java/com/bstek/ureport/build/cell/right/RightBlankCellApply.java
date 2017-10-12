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
public class RightBlankCellApply {
	private int colSize;
	private Cell cell;
	private Context context;
	private RightDuplocatorWrapper rightDuplocatorWrapper;
	public RightBlankCellApply(int colSize,Cell cell,Context context,RightDuplocatorWrapper rightDuplocatorWrapper) {
		this.colSize=colSize;
		this.cell=cell;
		this.context=context;
		this.rightDuplocatorWrapper=rightDuplocatorWrapper;
	}
	public boolean useBlankCell(int index,BindData bindData){
		if(context.getBlankCellsMap().size()==0){
			return false;
		}
		int nextColNumber=cell.getColumn().getColumnNumber()+colSize*(index-1)+colSize;
		Column nextCol=context.getColumn(nextColNumber);
		Cell blankCell=null;
		if(nextCol!=null){
			blankCell=context.getBlankCell(cell.getRow(), nextCol);
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
	private void processChildrenCell(Cell originalCell,Cell topParentCell,int index){
		List<CellRightDuplicator> children=rightDuplocatorWrapper.fetchChildrenDuplicator(originalCell);
		if(children==null){
			return;
		}
		for(CellRightDuplicator child:children){
			Cell childCell=child.getCell();
			int nextChildColNumber=childCell.getColumn().getColumnNumber()+colSize*(index-1)+colSize;
			Column nextChildCol=context.getColumn(nextChildColNumber);
			Row row=childCell.getRow();
			Cell targetCell=context.getBlankCell(row, nextChildCol);
			if(targetCell==null){
				continue;
			}
			context.removeBlankCell(targetCell);
			targetCell.setTopParentCell(topParentCell);			
			targetCell.setValue(childCell.getValue());
			if(originalCell==targetCell.getLeftParentCell()){
				targetCell.setLeftParentCell(topParentCell);
			}
			context.addUnprocessedCell(targetCell);
			processChildrenCell(childCell,targetCell,index);
		}
	}
}
