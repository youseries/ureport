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
public class RightDuplocatorWrapper {
	private String mainCellName;
	private List<CellRightDuplicator> mainCellChildren=new ArrayList<CellRightDuplicator>();
	private List<CellRightDuplicator> cellDuplicators=new ArrayList<CellRightDuplicator>();
	private Map<Cell,List<CellRightDuplicator>> createNewDuplicatorsMap=new HashMap<Cell,List<CellRightDuplicator>>();
	private List<Cell> duplicatorCells=new ArrayList<Cell>();
	public RightDuplocatorWrapper(String mainCellName) {
		this.mainCellName=mainCellName;
	}
	
	public void addCellRightDuplicator(CellRightDuplicator duplicator){
		if(duplicator.getDuplicateType().equals(DuplicateType.Duplicate)){
			addCellRightDuplicatorToMap(duplicator);
		}else{
			cellDuplicators.add(duplicator);
			duplicatorCells.add(duplicator.getCell());
		}
	}
	
	private void addCellRightDuplicatorToMap(CellRightDuplicator duplicator){
		Cell topParentCell=duplicator.getCell().getTopParentCell();
		if(topParentCell.getName().equals(mainCellName)){
			mainCellChildren.add(duplicator);
		}
		List<CellRightDuplicator> list=null;
		if(createNewDuplicatorsMap.containsKey(topParentCell)){
			list=createNewDuplicatorsMap.get(topParentCell);
		}else{
			list=new ArrayList<CellRightDuplicator>();
			createNewDuplicatorsMap.put(topParentCell, list);
		}
		list.add(duplicator);
	}
	
	public boolean contains(Cell cell){
		return duplicatorCells.contains(cell);
	}
	
	public List<CellRightDuplicator> getMainCellChildren() {
		return mainCellChildren;
	}
	
	public List<CellRightDuplicator> fetchChildrenDuplicator(Cell topParentCell){
		return createNewDuplicatorsMap.get(topParentCell);
	}
	
	public List<CellRightDuplicator> getCellDuplicators() {
		return cellDuplicators;
	}
}
