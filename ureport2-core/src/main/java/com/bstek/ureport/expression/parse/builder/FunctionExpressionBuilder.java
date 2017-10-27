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
package com.bstek.ureport.expression.parse.builder;

import java.util.ArrayList;
import java.util.List;

import com.bstek.ureport.dsl.ReportParserParser.FunctionContext;
import com.bstek.ureport.dsl.ReportParserParser.FunctionParameterContext;
import com.bstek.ureport.dsl.ReportParserParser.ItemContext;
import com.bstek.ureport.dsl.ReportParserParser.UnitContext;
import com.bstek.ureport.expression.ExpressionUtils;
import com.bstek.ureport.expression.model.expr.BaseExpression;
import com.bstek.ureport.expression.model.expr.FunctionExpression;

/**
 * @author Jacky.gao
 * @since 2016年12月26日
 */
public class FunctionExpressionBuilder extends BaseExpressionBuilder {
	@Override
	public BaseExpression build(UnitContext unitContext) {
		FunctionContext ctx=unitContext.function();
		FunctionExpression expr=new FunctionExpression();
		expr.setExpr(ctx.getText());
		expr.setName(ctx.Identifier().getText());
		FunctionParameterContext functionParameterContext=ctx.functionParameter();
		if(functionParameterContext!=null){
			List<BaseExpression> exprList=new ArrayList<BaseExpression>();
			List<ItemContext> itemContexts=functionParameterContext.item();
			if(itemContexts!=null){
				for(int i=0;i<itemContexts.size();i++){
					ItemContext itemContext=itemContexts.get(i);
					BaseExpression baseExpr=ExpressionUtils.getExprVisitor().parseItemContext(itemContext);
					exprList.add(baseExpr);
				}
			}
			expr.setExpressions(exprList);
		}
		return expr;
	}

	@Override
	public boolean support(UnitContext unitContext) {
		return unitContext.function()!=null;
	}
}
