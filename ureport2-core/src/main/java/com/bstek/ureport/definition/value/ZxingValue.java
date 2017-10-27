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
 * @since 2017年3月26日
 */
public class ZxingValue implements Value {
	private int width;
	private int height;
	private Source source;
	private String text;
	private String expr;
	private String format;
	private Expression expression;
	private ZxingCategory category;
	private boolean codeDisplay;
	@Override
	public String getValue() {
		return text;
	}

	@Override
	public ValueType getType() {
		return ValueType.zxing;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getExpr() {
		return expr;
	}

	public void setExpr(String expr) {
		this.expr = expr;
	}

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public ZxingCategory getCategory() {
		return category;
	}

	public void setCategory(ZxingCategory category) {
		this.category = category;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public boolean isCodeDisplay() {
		return codeDisplay;
	}

	public void setCodeDisplay(boolean codeDisplay) {
		this.codeDisplay = codeDisplay;
	}
}
