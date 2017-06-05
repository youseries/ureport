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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bstek.ureport.build.cell.DuplicateType;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2017年2月25日
 */
public class DownDuplocatorWrapper {
	private String mainCellName;
	private List<CellDownDuplicator> mainCellChildren=new ArrayList<CellDownDuplicator>();
	private List<CellDownDuplicator> cellDuplicators=new ArrayList<CellDownDuplicator>();
	private Map<Cell,List<CellDownDuplicator>> createNewDuplicatorsMap=new HashMap<Cell,List<CellDownDuplicator>>();
//	private List<CellDownDuplicator> processedCellDuplicators=new ArrayList<CellDownDuplicator>();
	private List<Cell> duplicatorCells=new ArrayList<Cell>();
	public DownDuplocatorWrapper(String mainCellName) {
		this.mainCellName=mainCellName;
	}
	
	public void addCellDownDuplicator(CellDownDuplicator duplicator){
		if(duplicator.getDuplicateType().equals(DuplicateType.Duplicate)){
			addCellDownDuplicatorToMap(duplicator);
		}else{
			cellDuplicators.add(duplicator);
			duplicatorCells.add(duplicator.getCell());
		}
	}
	
	private void addCellDownDuplicatorToMap(CellDownDuplicator duplicator){
		Cell leftParentCell=duplicator.getCell().getLeftParentCell();
		if(leftParentCell.getName().equals(mainCellName)){
			mainCellChildren.add(duplicator);
		}
		List<CellDownDuplicator> list=null;
		if(createNewDuplicatorsMap.containsKey(leftParentCell)){
			list=createNewDuplicatorsMap.get(leftParentCell);
		}else{
			list=new ArrayList<CellDownDuplicator>();
			createNewDuplicatorsMap.put(leftParentCell, list);
		}
		list.add(duplicator);
	}
	
	public boolean contains(Cell cell){
		return duplicatorCells.contains(cell);
	}
	/*
	public CellDownDuplicator nextCellDuplicator(){
		if(cellDuplicators.size()>0){
			CellDownDuplicator target = cellDuplicators.get(0);
			cellDuplicators.remove(0);
			processedCellDuplicators.add(target);
			return target;
		}
		return null;
	}
	*/
	public List<CellDownDuplicator> getMainCellChildren() {
		return mainCellChildren;
	}
	
	public List<CellDownDuplicator> fetchChildrenDuplicator(Cell leftParentCell){
		return createNewDuplicatorsMap.get(leftParentCell);
	}
	/*
	public void reset(){
		cellDuplicators.addAll(processedCellDuplicators);
		processedCellDuplicators.clear();
	}
	*/
	public List<CellDownDuplicator> getCellDuplicators() {
		return cellDuplicators;
	}
}
