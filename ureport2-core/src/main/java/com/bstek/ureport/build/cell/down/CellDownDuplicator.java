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
