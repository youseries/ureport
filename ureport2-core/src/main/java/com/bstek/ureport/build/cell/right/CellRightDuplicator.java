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

import com.bstek.ureport.build.Context;
import com.bstek.ureport.build.cell.DuplicateType;
import com.bstek.ureport.definition.BlankCellInfo;
import com.bstek.ureport.definition.value.SimpleValue;
import com.bstek.ureport.definition.value.Value;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.model.Cell;
import com.bstek.ureport.model.Column;

/**
 * @author Jacky.gao
 * @since 2016年11月7日
 */
public class CellRightDuplicator {
	private Cell cell;
	private int cellColNumber;
	private DuplicateType duplicateType;
	private BlankCellInfo blankCellInfo;
	private boolean nonChild=false;
	public CellRightDuplicator(Cell cell,DuplicateType duplicateType,int cellColNumber) {
		this.cell=cell;
		this.duplicateType=duplicateType;
		this.cellColNumber=cellColNumber;
	}
	public CellRightDuplicator(Cell cell,DuplicateType duplicateType,BlankCellInfo blankCellInfo,int cellColNumber) {
		this.cell=cell;
		this.duplicateType=duplicateType;
		this.blankCellInfo=blankCellInfo;
		if(cellColNumber>0){
			this.cellColNumber=cellColNumber;
		}else{
			this.cellColNumber=cell.getColumn().getColumnNumber();
		}
	}
	
	public Cell duplicate(RightDuplicate rightDuplicate,Cell newMainCell){
		switch(duplicateType){
		case Blank:
			processBlankCell(rightDuplicate,newMainCell);
			break;
		case Self:
			processSelfBlankCell(rightDuplicate);
			break;
		case IncreseSpan:
			processIncreaseSpanCell(rightDuplicate);
			break;
		case Duplicate:
			throw new ReportComputeException("Invalid duplicator.");
		}
		return null;
	}
	
	
	private void processSelfBlankCell(RightDuplicate rightDuplicate){
		Cell newBlankCell=cell.newCell();
		newBlankCell.setValue(new SimpleValue(""));
		Column newCol=rightDuplicate.newColumn(newBlankCell.getColumn(),cellColNumber);
		newCol.getCells().add(newBlankCell);
		newBlankCell.getRow().getCells().add(newBlankCell);
		newBlankCell.setColumn(newCol);
		Cell leftParentCell=newBlankCell.getLeftParentCell();
		if(leftParentCell!=null){
			leftParentCell.addRowChild(newBlankCell);
		}
		Cell topParentCell=newBlankCell.getTopParentCell();
		if(topParentCell!=null){
			topParentCell.addColumnChild(newBlankCell);
		}
		Context context=rightDuplicate.getContext();
		context.addBlankCell(newBlankCell);
	}
	
	public Cell duplicateChildrenCell(RightDuplicate rightDuplicate,Cell topParent,Cell originalCell,boolean parentNonChild){
		Cell newCell=cell.newCell();
		Column newCol=rightDuplicate.newColumn(newCell.getColumn(),cellColNumber);
		newCol.getCells().add(newCell);
		newCell.getRow().getCells().add(newCell);
		newCell.setColumn(newCol);
		if(newCell.getTopParentCell()==originalCell){
			newCell.setTopParentCell(topParent);
			if(parentNonChild){
				nonChild=true;				
			}
		}else{
			nonChild=true;
		}
		if(newCell.getLeftParentCell()==originalCell){
			newCell.setLeftParentCell(topParent);
		}
		Cell leftParentCell=newCell.getLeftParentCell();
		if(leftParentCell!=null){
			leftParentCell.addRowChild(newCell);
		}
		Cell topParentCell=newCell.getTopParentCell();
		if(topParentCell!=null){
			topParentCell.addColumnChild(newCell);
		}
		Context context=rightDuplicate.getContext();
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
	
	private void processIncreaseSpanCell(RightDuplicate rightDuplicate){
		int colSpan=cell.getColSpan();
		colSpan+=rightDuplicate.getColSize();
		if(colSpan==1){
			colSpan++;
		}
		cell.setColSpan(colSpan);
	}
	
	private void processBlankCell(RightDuplicate rightDuplicate,Cell newMainCell){
		Context context=rightDuplicate.getContext();
		Cell newBlankCell=cell.newColumnBlankCell(context,blankCellInfo,rightDuplicate.getMainCell());
		if(blankCellInfo.isParent() && newMainCell.getTopParentCell()==cell){
			newMainCell.setTopParentCell(newBlankCell);
		}
		Column col=rightDuplicate.newColumn(newBlankCell.getColumn(),cellColNumber);
		col.getCells().add(newBlankCell);
		newBlankCell.getRow().getCells().add(newBlankCell);
		newBlankCell.setColumn(col);
		context.addReportCell(newBlankCell);
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
