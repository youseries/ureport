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
