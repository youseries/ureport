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
package com.bstek.ureport.definition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.bstek.ureport.Range;
import com.bstek.ureport.definition.value.Value;
import com.bstek.ureport.model.Cell;


/**
 * @author Jacky.gao
 * @since 2016年11月1日
 */
public class CellDefinition implements Serializable{
	private static final long serialVersionUID = -2667510071560936139L;
	private int rowNumber;
	private int columnNumber;
	private int rowSpan;
	private int colSpan;
	private String name;
	private Value value;
	private CellStyle cellStyle=new CellStyle();
	
	private String linkUrl;
	private String linkTargetWindow;
	private List<LinkParameter> linkParameters;
	
	private boolean fillBlankRows;
	/**
	 * 允许填充空白行时fillBlankRows=true，要求当前数据行数必须是multiple定义的行数的倍数，否则就补充空白行
	 */
	private int multiple;
	
	private Expand expand=Expand.None;
	
	@JsonIgnore
	private Range duplicateRange;
	@JsonIgnore
	private List<String> increaseSpanCellNames=new ArrayList<String>();
	@JsonIgnore
	private Map<String,BlankCellInfo> newBlankCellsMap=new HashMap<String,BlankCellInfo>();
	@JsonIgnore
	private List<String> newCellNames=new ArrayList<String>();
	
	/**
	 * 当前单元格左父格名
	 */
	private String leftParentCellName;
	/**
	 * 当前单元格上父格名
	 */
	private String topParentCellName;
	/**
	 * 当前单元格左父格
	 */
	@JsonIgnore
	private CellDefinition leftParentCell;
	/**
	 * 当前单元格上父格
	 */
	@JsonIgnore
	private CellDefinition topParentCell;
	/**
	 * 当前单无格所在行的所有子格
	 */
	@JsonIgnore
	private List<CellDefinition> rowChildrenCells=new ArrayList<CellDefinition>();
	/**
	 * 当前单无格所在列的所有子格
	 */
	@JsonIgnore
	private List<CellDefinition> columnChildrenCells=new ArrayList<CellDefinition>();
	
	private List<ConditionPropertyItem> conditionPropertyItems;
	
	protected Cell newCell(){
		Cell cell=new Cell();
		cell.setValue(value);
		cell.setName(name);
		cell.setRowSpan(rowSpan);
		cell.setColSpan(colSpan);
		cell.setExpand(expand);
		cell.setCellStyle(cellStyle);
		cell.setNewBlankCellsMap(newBlankCellsMap);
		cell.setIncreaseSpanCellNames(increaseSpanCellNames);
		cell.setNewCellNames(newCellNames);
		cell.setDuplicateRange(duplicateRange);
		cell.setLinkParameters(linkParameters);
		cell.setLinkTargetWindow(linkTargetWindow);
		cell.setLinkUrl(linkUrl);
		cell.setConditionPropertyItems(conditionPropertyItems);
		cell.setFillBlankRows(fillBlankRows);
		cell.setMultiple(multiple);
		return cell;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public int getColumnNumber() {
		return columnNumber;
	}

	public void setColumnNumber(int columnNumber) {
		this.columnNumber = columnNumber;
	}

	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}
	
	public int getRowSpan() {
		return rowSpan;
	}

	public void setRowSpan(int rowSpan) {
		this.rowSpan = rowSpan;
	}

	public int getColSpan() {
		return colSpan;
	}

	public void setColSpan(int colSpan) {
		this.colSpan = colSpan;
	}

	public Expand getExpand() {
		return expand;
	}

	public void setExpand(Expand expand) {
		this.expand = expand;
	}
	
	public String getLeftParentCellName() {
		return leftParentCellName;
	}

	public void setLeftParentCellName(String leftParentCellName) {
		this.leftParentCellName = leftParentCellName;
	}

	public String getTopParentCellName() {
		return topParentCellName;
	}

	public void setTopParentCellName(String topParentCellName) {
		this.topParentCellName = topParentCellName;
	}

	public CellDefinition getLeftParentCell() {
		return leftParentCell;
	}

	public void setLeftParentCell(CellDefinition leftParentCell) {
		this.leftParentCell = leftParentCell;
	}

	public CellDefinition getTopParentCell() {
		return topParentCell;
	}

	public void setTopParentCell(CellDefinition topParentCell) {
		this.topParentCell = topParentCell;
	}

	public CellStyle getCellStyle() {
		return cellStyle;
	}
	
	public boolean isFillBlankRows() {
		return fillBlankRows;
	}

	public void setFillBlankRows(boolean fillBlankRows) {
		this.fillBlankRows = fillBlankRows;
	}

	public int getMultiple() {
		return multiple;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public Range getDuplicateRange() {
		return duplicateRange;
	}
	
	public void setDuplicateRange(Range duplicateRange) {
		this.duplicateRange = duplicateRange;
	}

	public void setCellStyle(CellStyle cellStyle) {
		this.cellStyle = cellStyle;
	}

	public List<CellDefinition> getRowChildrenCells() {
		return rowChildrenCells;
	}
	public List<CellDefinition> getColumnChildrenCells() {
		return columnChildrenCells;
	}
	public List<String> getIncreaseSpanCellNames() {
		return increaseSpanCellNames;
	}
	public Map<String, BlankCellInfo> getNewBlankCellsMap() {
		return newBlankCellsMap;
	}
	public List<String> getNewCellNames() {
		return newCellNames;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getLinkTargetWindow() {
		return linkTargetWindow;
	}

	public void setLinkTargetWindow(String linkTargetWindow) {
		this.linkTargetWindow = linkTargetWindow;
	}

	public List<LinkParameter> getLinkParameters() {
		return linkParameters;
	}

	public void setLinkParameters(List<LinkParameter> linkParameters) {
		this.linkParameters = linkParameters;
	}
	public List<ConditionPropertyItem> getConditionPropertyItems() {
		return conditionPropertyItems;
	}
	public void setConditionPropertyItems(
			List<ConditionPropertyItem> conditionPropertyItems) {
		this.conditionPropertyItems = conditionPropertyItems;
	}
}
