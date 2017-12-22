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

import com.bstek.ureport.build.Context;
import com.bstek.ureport.model.Cell;
import com.bstek.ureport.model.Column;

/**
 * @author Jacky.gao
 * @since 2016年11月7日
 */
public class CellRightDuplicateUnit {
	private Cell mainCell;
	private int mainCellColNumber;
	private Context context;
	private RightDuplicate rightDuplicate;
	private RightDuplocatorWrapper rightDuplocatorWrapper;
	public CellRightDuplicateUnit(Context context,RightDuplocatorWrapper rightDuplocatorWrapper,Cell mainCell,int mainCellColNumber,int colSize) {
		this.context=context;
		this.rightDuplocatorWrapper=rightDuplocatorWrapper;
		this.mainCell=mainCell;
		this.mainCellColNumber=mainCellColNumber;
		this.rightDuplicate=new RightDuplicate(mainCell,colSize,context);
	}
	public void duplicate(Cell cell,int index){
		rightDuplicate.setIndex(index);
		for(CellRightDuplicator childDuplicator:rightDuplocatorWrapper.getMainCellChildren()){
			Cell newCell=childDuplicator.duplicateChildrenCell(rightDuplicate,cell,mainCell,mainCell,cell,false);
			processChildrenCells(newCell,childDuplicator.getCell(),cell,rightDuplicate,context,childDuplicator.isNonChild());
			childDuplicator.setNonChild(false);
		}
		for(CellRightDuplicator cellRightDuplicator:rightDuplocatorWrapper.getCellDuplicators()){
			cellRightDuplicator.duplicate(rightDuplicate,cell);
		}
		Column newCol=rightDuplicate.newColumn(cell.getColumn(),mainCellColNumber);
		cell.setColumn(newCol);
		newCol.getCells().add(cell);
		cell.getRow().getCells().add(cell);
		context.addReportCell(cell);
		rightDuplicate.reset();
	}
	
	public void complete(){
		rightDuplicate.complete();
	}
	
	private void processChildrenCells(Cell cell,Cell originalCell,Cell newMainCell,RightDuplicate rightDuplicate,Context context,boolean parentNonChild){
		List<CellRightDuplicator> childCellRightDuplicators=rightDuplocatorWrapper.fetchChildrenDuplicator(originalCell);
		if(childCellRightDuplicators==null){
			return;
		}
		for(CellRightDuplicator duplicator:childCellRightDuplicators){				
			Cell newCell=duplicator.duplicateChildrenCell(rightDuplicate,cell,originalCell,mainCell,newMainCell,parentNonChild);
			processChildrenCells(newCell,duplicator.getCell(),newMainCell,rightDuplicate,context,duplicator.isNonChild());
			duplicator.setNonChild(false);
		}
	}
}
