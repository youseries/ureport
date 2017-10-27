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
package com.bstek.ureport.definition.value;

import com.bstek.ureport.expression.model.Expression;

/**
 * @author Jacky.gao
 * @since 2017年1月24日
 */
public class ImageValue implements Value {
	private String path;
	private String expr;
	private Expression expression;
	private Source source;
	@Override
	public ValueType getType() {
		return ValueType.image;
	}
	@Override
	public String getValue() {
		if(this.source.equals(Source.text)){
			return path;
		}else{
			return expr;
		}
	}
	public void setSource(Source source) {
		this.source = source;
	}
	public Source getSource() {
		return source;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public void setExpr(String expr) {
		this.expr = expr;
	}
	public String getExpr() {
		return expr;
	}
	public Expression getExpression() {
		return expression;
	}
	public void setExpression(Expression expression) {
		this.expression = expression;
	}
}
