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

import com.bstek.ureport.expression.model.Expression;

/**
 * @author Jacky.gao
 * @since 2017年3月31日
 */
public class LinkParameter implements Serializable{
	private static final long serialVersionUID = -5156733452111427492L;
	private String name;
	private String value;
	private Expression valueExpression;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Expression getValueExpression() {
		return valueExpression;
	}
	public void setValueExpression(Expression valueExpression) {
		this.valueExpression = valueExpression;
	}
}
