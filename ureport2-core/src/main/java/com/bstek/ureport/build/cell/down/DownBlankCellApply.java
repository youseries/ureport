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
