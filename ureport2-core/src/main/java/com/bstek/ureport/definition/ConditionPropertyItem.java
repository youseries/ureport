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

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.bstek.ureport.expression.model.Condition;
import com.bstek.ureport.expression.model.Expression;

/**
 * @author Jacky.gao
 * @since 2017年4月10日
 */
public class ConditionPropertyItem {
	private String name;
	
	@JsonIgnore 
	private Condition condition;
	/**
	 * 此属性给设计器使用，引擎不使用该属性
	 */
	private List<Condition> conditions;
	
	private int rowHeight=-1;
	private int colWidth=-1;
	
	private String newValue;
	private String linkUrl;
	private String linkTargetWindow;
	private List<LinkParameter> linkParameters;
	
	private ConditionCellStyle cellStyle;
	
	private ConditionPaging paging;
	
	@JsonIgnore
	private Expression expression;
	
	private String expr;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	public List<Condition> getConditions() {
		return conditions;
	}

	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
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

	public ConditionCellStyle getCellStyle() {
		return cellStyle;
	}

	public void setCellStyle(ConditionCellStyle cellStyle) {
		this.cellStyle = cellStyle;
	}

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public String getExpr() {
		return expr;
	}

	public void setExpr(String expr) {
		this.expr = expr;
	}

	public int getRowHeight() {
		return rowHeight;
	}

	public void setRowHeight(int rowHeight) {
		this.rowHeight = rowHeight;
	}

	public int getColWidth() {
		return colWidth;
	}

	public void setColWidth(int colWidth) {
		this.colWidth = colWidth;
	}

	public ConditionPaging getPaging() {
		return paging;
	}

	public void setPaging(ConditionPaging paging) {
		this.paging = paging;
	}
}
