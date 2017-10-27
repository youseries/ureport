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
