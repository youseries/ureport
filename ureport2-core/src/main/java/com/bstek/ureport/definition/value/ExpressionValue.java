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

import com.bstek.ureport.expression.ExpressionUtils;
import com.bstek.ureport.expression.model.Expression;

/**
 * @author Jacky.gao
 * @since 2016年12月24日
 */
public class ExpressionValue implements Value{
	private String text;
	private Expression expression;
	public ExpressionValue(String text) {
		this.text=text;
		expression=ExpressionUtils.parseExpression(text);
	}
	
	@Override
	public ValueType getType() {
		return ValueType.expression;
	}
	@Override
	public String getValue() {
		return text;
	}
	public Expression getExpression() {
		return expression;
	}
}
