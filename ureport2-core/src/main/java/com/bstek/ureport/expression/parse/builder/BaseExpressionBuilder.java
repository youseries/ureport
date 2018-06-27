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
package com.bstek.ureport.expression.parse.builder;

import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;

import com.bstek.ureport.Utils;
import com.bstek.ureport.dsl.ReportParserParser.CellNameExprConditionContext;
import com.bstek.ureport.dsl.ReportParserParser.ConditionContext;
import com.bstek.ureport.dsl.ReportParserParser.ConditionsContext;
import com.bstek.ureport.dsl.ReportParserParser.CurrentValueConditionContext;
import com.bstek.ureport.dsl.ReportParserParser.ExprConditionContext;
import com.bstek.ureport.dsl.ReportParserParser.ExprContext;
import com.bstek.ureport.dsl.ReportParserParser.JoinContext;
import com.bstek.ureport.dsl.ReportParserParser.PropertyConditionContext;
import com.bstek.ureport.dsl.ReportParserParser.SimpleValueContext;
import com.bstek.ureport.exception.ReportParseException;
import com.bstek.ureport.expression.ExpressionUtils;
import com.bstek.ureport.expression.model.Expression;
import com.bstek.ureport.expression.model.Op;
import com.bstek.ureport.expression.model.condition.BaseCondition;
import com.bstek.ureport.expression.model.condition.BothExpressionCondition;
import com.bstek.ureport.expression.model.condition.CellExpressionCondition;
import com.bstek.ureport.expression.model.condition.CurrentValueExpressionCondition;
import com.bstek.ureport.expression.model.condition.Join;
import com.bstek.ureport.expression.model.condition.PropertyExpressionCondition;
import com.bstek.ureport.expression.model.expr.BaseExpression;
import com.bstek.ureport.expression.model.expr.BooleanExpression;
import com.bstek.ureport.expression.model.expr.IntegerExpression;
import com.bstek.ureport.expression.model.expr.NullExpression;
import com.bstek.ureport.expression.model.expr.NumberExpression;
import com.bstek.ureport.expression.model.expr.StringExpression;

/**
 * @author Jacky.gao
 * @since 2016年12月26日
 */
public abstract class BaseExpressionBuilder implements ExpressionBuilder{
	protected BaseExpression parseSimpleValueContext(SimpleValueContext valueContext) {
		if(valueContext.BOOLEAN()!=null){
			return new BooleanExpression(Boolean.valueOf(valueContext.getText()));
		}else if(valueContext.INTEGER()!=null){
			return new IntegerExpression(Integer.valueOf(valueContext.INTEGER().getText()));
		}else if(valueContext.STRING()!=null){
			String text=valueContext.STRING().getText();
			text=text.substring(1,text.length()-1);
			return new StringExpression(text);
		}else if(valueContext.NUMBER()!=null){
			return new NumberExpression(Utils.toBigDecimal(valueContext.NUMBER().getText()));
		}else if(valueContext.NULL()!=null){
			return new NullExpression();
		}
		throw new ReportParseException("Unknow simple value context "+valueContext);
	}

	
	protected BaseCondition buildConditions(ConditionsContext conditionsContext) {
		List<ConditionContext> conditionContextList=conditionsContext.condition();
		List<JoinContext> joins=conditionsContext.join();
		BaseCondition condition=null;
		BaseCondition topCondition=null;
		int opIndex=0;
		for(ConditionContext conditionCtx:conditionContextList){
			if(condition==null){
				condition=parseCondition(conditionCtx);
				topCondition=condition;
			}else{
				BaseCondition nextCondition=parseCondition(conditionCtx);
				condition.setNextCondition(nextCondition);
				condition.setJoin(Join.parse(joins.get(opIndex).getText()));
				opIndex++;
				condition=nextCondition;
			}
		}
		return topCondition;
	}
	private BaseCondition parseCondition(ConditionContext context){
		if(context instanceof ExprConditionContext){
			ExprConditionContext ctx=(ExprConditionContext)context;
			BothExpressionCondition condition=new BothExpressionCondition();
			List<ExprContext> exprContexts=ctx.expr();
			String left=exprContexts.get(0).getText();
			condition.setLeft(left);
			Expression leftExpr=ExpressionUtils.parseExpression(left);
			condition.setLeftExpression(leftExpr);
			String rightExpr=exprContexts.get(1).getText();
			condition.setRight(rightExpr);
			condition.setRightExpression(ExpressionUtils.parseExpression(rightExpr));
			condition.setOp(parseOp(ctx.OP()));
			condition.setOperation(ctx.OP().getText());
			return condition;
		}else if(context instanceof CurrentValueConditionContext){
			CurrentValueConditionContext ctx=(CurrentValueConditionContext)context;
			CurrentValueExpressionCondition condition=new CurrentValueExpressionCondition();
			String rightExpr=ctx.expr().getText();
			condition.setRight(rightExpr);
			condition.setRightExpression(ExpressionUtils.parseExpression(rightExpr));
			condition.setOp(parseOp(ctx.OP()));
			return condition;
		}else if(context instanceof PropertyConditionContext){
			PropertyConditionContext ctx=(PropertyConditionContext)context;
			PropertyExpressionCondition condition=new PropertyExpressionCondition();
			String left=ctx.property().getText();
			condition.setLeft(left);
			condition.setLeftProperty(left);
			String rightExpr=ctx.expr().getText();
			condition.setRight(rightExpr);
			condition.setRightExpression(ExpressionUtils.parseExpression(rightExpr));
			condition.setOp(parseOp(ctx.OP()));
			return condition;
		}else if(context instanceof CellNameExprConditionContext){
			CellNameExprConditionContext ctx=(CellNameExprConditionContext)context;
			CellExpressionCondition condition=new CellExpressionCondition();
			String left=ctx.Cell().getText();
			condition.setLeft(left);
			condition.setCellName(left);
			String rightExpr=ctx.expr().getText();
			condition.setRight(rightExpr);
			condition.setRightExpression(ExpressionUtils.parseExpression(rightExpr));
			condition.setOp(parseOp(ctx.OP()));
			return condition;
		}
		throw new ReportParseException("Unknow condition context : "+context);
	}
	
	private Op parseOp(TerminalNode opNode){
		if(opNode.getText().equals(">")){
			return Op.GreatThen;
		}
		if(opNode.getText().equals("<")){
			return Op.LessThen;
		}
		if(opNode.getText().equals(">=")){
			return Op.EqualsGreatThen;
		}
		if(opNode.getText().equals("<=")){
			return Op.EqualsLessThen;
		}
		if(opNode.getText().equals("==")){
			return Op.Equals;
		}
		if(opNode.getText().equals("!=")){
			return Op.NotEquals;
		}
		throw new ReportParseException("Unknow operator :" +opNode);
	}
}
