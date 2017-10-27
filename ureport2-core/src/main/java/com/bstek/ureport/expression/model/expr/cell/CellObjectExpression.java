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
package com.bstek.ureport.expression.model.expr.cell;

import org.apache.commons.lang.StringUtils;

import com.bstek.ureport.Utils;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.expression.model.data.ExpressionData;
import com.bstek.ureport.expression.model.data.ObjectExpressionData;
import com.bstek.ureport.expression.model.expr.BaseExpression;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2017年1月20日
 */
public class CellObjectExpression extends BaseExpression {
	private static final long serialVersionUID = 1558531964770533126L;
	private String property;
	public CellObjectExpression(String property) {
		this.property=property;
	}
	@Override
	protected ExpressionData<?> compute(Cell cell, Cell currentCell,Context context) {
		while(!context.isCellPocessed(cell.getName())){
			context.getReportBuilder().buildCell(context, null);
		}
		if(StringUtils.isNotBlank(property)){
			Object obj=Utils.getProperty(cell, property);
			return new ObjectExpressionData(obj);
		}
		return new ObjectExpressionData(cell);
	}
}
