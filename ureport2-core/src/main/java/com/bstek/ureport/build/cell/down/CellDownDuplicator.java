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

import com.bstek.ureport.build.Context;
import com.bstek.ureport.build.cell.DuplicateType;
import com.bstek.ureport.definition.BlankCellInfo;
import com.bstek.ureport.definition.value.SimpleValue;
import com.bstek.ureport.definition.value.Value;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.model.Cell;
import com.bstek.ureport.model.Row;

/**
 * @author Jacky.gao
 * @since 2016年11月7日
 */
public class CellDownDuplicator {
	private Cell cell;
	private int cellRowNumber;
	private DuplicateType duplicateType;
	private BlankCellInfo blankCellInfo;
	private boolean nonChild=false;
	public CellDownDuplicator(Cell cell,DuplicateType duplicateType,int cellRowNumber) {
		this.cell=cell;
		this.cellRowNumber=cellRowNumber;										
		this.duplicateType=duplicateType;
	}
	public CellDownDuplicator(Cell cell,DuplicateType duplicateType,BlankCellInfo blankCellInfo,int cellRowNumber) {
		this.cell=cell;
		if(cellRowNumber==0){
			this.cellRowNumber=cell.getRow().getRowNumber();			
		}else{
			this.cellRowNumber=cellRowNumber;			
		}
		this.duplicateType=duplicateType;
		this.blankCellInfo=blankCellInfo;
	}
	
	public Cell duplicate(DownDuplicate downDuplicate,Cell newMainCell){
		switch(duplicateType){
		case Blank:
			processBlankCell(downDuplicate,newMainCell);
			break;
		case Self:
			processSelfBlankCell(downDuplicate);
			break;
		case IncreseSpan:
			processIncreaseSpanCell(downDuplicate);
			break;
		case Duplicate:
			throw new ReportComputeException("Invalid duplicator.");
		}
		return null;
	}
	
	public Cell duplicateChildrenCell(DownDuplicate downDuplicate,Cell leftParent,Cell originalCell,boolean parentNonChild){
		Cell newCell=cell.newCell();
		Row newRow=downDuplicate.newRow(newCell.getRow(),cellRowNumber);
		newRow.getCells().add(newCell);
		newCell.getColumn().getCells().add(newCell);
		newCell.setRow(newRow);
		if(newCell.getLeftParentCell()==originalCell){
			newCell.setLeftParentCell(leftParent);
			if(parentNonChild){
				nonChild=true;				
			}
		}else{
			nonChild=true;
		}
		if(newCell.getTopParentCell()==originalCell){
			newCell.setTopParentCell(leftParent);
		}
		Cell leftParentCell=newCell.getLeftParentCell();
		if(leftParentCell!=null){
			leftParentCell.addRowChild(newCell);
		}
		Cell topParentCell=newCell.getTopParentCell();
		if(topParentCell!=null){
			topParentCell.addColumnChild(newCell);
		}
		Context context=downDuplicate.getContext();
		Value value=newCell.getValue();
		if(value instanceof SimpleValue){
			newCell.setData(value.getValue());
			newCell.setProcessed(true);
			context.addReportCell(newCell);
		}else{
			if(nonChild){
				newCell.setValue(new SimpleValue(""));
				context.addBlankCell(newCell);
			}else{
				context.addCell(newCell);									
			}
		}
		return newCell;
	}
	
	private void processBlankCell(DownDuplicate downDuplicate,Cell newMainCell){
		Context context=downDuplicate.getContext();
		Cell newBlankCell=cell.newRowBlankCell(context,blankCellInfo,downDuplicate.getMainCell());
		if(blankCellInfo.isParent() && newMainCell.getLeftParentCell()==cell){
			newMainCell.setLeftParentCell(newBlankCell);
		}
		Row newRow=downDuplicate.newRow(newBlankCell.getRow(),cellRowNumber);
		newRow.getCells().add(newBlankCell);
		newBlankCell.getColumn().getCells().add(newBlankCell);
		newBlankCell.setRow(newRow);
		context.addReportCell(newBlankCell);
	}
	
	private void processSelfBlankCell(DownDuplicate downDuplicate){
		Cell newBlankCell=cell.newCell();
		newBlankCell.setValue(new SimpleValue(""));
		Row newRow=downDuplicate.newRow(newBlankCell.getRow(),cellRowNumber);
		newRow.getCells().add(newBlankCell);
		newBlankCell.getColumn().getCells().add(newBlankCell);
		newBlankCell.setRow(newRow);
		Cell leftParentCell=newBlankCell.getLeftParentCell();
		if(leftParentCell!=null){
			leftParentCell.addRowChild(newBlankCell);
		}
		Cell topParentCell=newBlankCell.getTopParentCell();
		if(topParentCell!=null){
			topParentCell.addColumnChild(newBlankCell);
		}
		Context context=downDuplicate.getContext();
		context.addBlankCell(newBlankCell);
	}
	
	
	private void processIncreaseSpanCell(DownDuplicate downDuplicate){
		int rowSpan=cell.getRowSpan();
		rowSpan+=downDuplicate.getRowSize();
		if(rowSpan==1){
			rowSpan++;
		}
		cell.setRowSpan(rowSpan);
	}
	
	public DuplicateType getDuplicateType() {
		return duplicateType;
	}
	public Cell getCell() {
		return cell;
	}
	public boolean isNonChild() {
		return nonChild;
	}
	public void setNonChild(boolean nonChild) {
		this.nonChild = nonChild;
	}
}
