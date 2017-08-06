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
package com.bstek.ureport.expression.parse;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;

import com.bstek.ureport.dsl.ReportParserBaseVisitor;
import com.bstek.ureport.dsl.ReportParserParser.CaseExprContext;
import com.bstek.ureport.dsl.ReportParserParser.CasePartContext;
import com.bstek.ureport.dsl.ReportParserParser.ComplexExprCompositeContext;
import com.bstek.ureport.dsl.ReportParserParser.ElseIfPartContext;
import com.bstek.ureport.dsl.ReportParserParser.ElsePartContext;
import com.bstek.ureport.dsl.ReportParserParser.ExprCompositeContext;
import com.bstek.ureport.dsl.ReportParserParser.ExprContext;
import com.bstek.ureport.dsl.ReportParserParser.ExpressionContext;
import com.bstek.ureport.dsl.ReportParserParser.IfConditionContext;
import com.bstek.ureport.dsl.ReportParserParser.IfExprContext;
import com.bstek.ureport.dsl.ReportParserParser.IfPartContext;
import com.bstek.ureport.dsl.ReportParserParser.ItemContext;
import com.bstek.ureport.dsl.ReportParserParser.JoinContext;
import com.bstek.ureport.dsl.ReportParserParser.ParenExprCompositeContext;
import com.bstek.ureport.dsl.ReportParserParser.ParenJoinContext;
import com.bstek.ureport.dsl.ReportParserParser.SimpleJoinContext;
import com.bstek.ureport.dsl.ReportParserParser.SingleExprCompositeContext;
import com.bstek.ureport.dsl.ReportParserParser.SingleParenJoinContext;
import com.bstek.ureport.dsl.ReportParserParser.TernaryExprCompositeContext;
import com.bstek.ureport.dsl.ReportParserParser.TernaryExprContext;
import com.bstek.ureport.dsl.ReportParserParser.UnitContext;
import com.bstek.ureport.exception.ReportParseException;
import com.bstek.ureport.expression.model.Expression;
import com.bstek.ureport.expression.model.Op;
import com.bstek.ureport.expression.model.Operator;
import com.bstek.ureport.expression.model.condition.Join;
import com.bstek.ureport.expression.model.expr.BaseExpression;
import com.bstek.ureport.expression.model.expr.JoinExpression;
import com.bstek.ureport.expression.model.expr.ParenExpression;
import com.bstek.ureport.expression.model.expr.ifelse.ElseExpression;
import com.bstek.ureport.expression.model.expr.ifelse.ElseIfExpression;
import com.bstek.ureport.expression.model.expr.ifelse.ExpressionCondition;
import com.bstek.ureport.expression.model.expr.ifelse.ExpressionConditionList;
import com.bstek.ureport.expression.model.expr.ifelse.IfExpression;
import com.bstek.ureport.expression.parse.builder.ExpressionBuilder;

/**
 * @author Jacky.gao
 * @since 2016年11月18日
 */
public class ExpressionVisitor extends ReportParserBaseVisitor<Expression>{
	private List<ExpressionBuilder> expressionBuilders;
	public ExpressionVisitor(List<ExpressionBuilder> expressionBuilders) {
		this.expressionBuilders=expressionBuilders;
	}
	@Override
	public Expression visitExpression(ExpressionContext ctx) {
		ExprCompositeContext exprCompositeContext=ctx.exprComposite();
		IfExprContext ifExprContext=ctx.ifExpr();
		CaseExprContext caseExprContext=ctx.caseExpr();
		if(exprCompositeContext!=null){
			return parseExprComposite(exprCompositeContext);
		}else if(ifExprContext!=null){
			IfExpression expr=new IfExpression();
			expr.setExpr(ctx.getText());
			IfPartContext ifPartContext=ifExprContext.ifPart();
			List<IfConditionContext> ifConditionContexts=ifPartContext.ifCondition();
			List<JoinContext> joinContexts=ifPartContext.join();
			expr.setConditionList(parseCondtionList(ifConditionContexts,joinContexts));
			ExprContext ec=ifPartContext.expr();
			Expression expression=parseExpr(ec);
			expr.setExpression(expression);
			
			List<ElseIfPartContext> elseIfPartContexts=ifExprContext.elseIfPart();
			if(elseIfPartContexts!=null && elseIfPartContexts.size()>0){
				List<ElseIfExpression> elseIfExpressionList=new ArrayList<ElseIfExpression>();
				for(ElseIfPartContext elseIfContext:elseIfPartContexts){
					ifConditionContexts=elseIfContext.ifCondition();
					joinContexts=elseIfContext.join();
					ElseIfExpression elseIfExpr=new ElseIfExpression();
					elseIfExpr.setConditionList(parseCondtionList(ifConditionContexts,joinContexts));
					ec=elseIfContext.expr();
					elseIfExpr.setExpr(elseIfContext.getText());
					elseIfExpr.setExpression(parseExpr(ec));
					elseIfExpressionList.add(elseIfExpr);
				}
				expr.setElseIfExpressions(elseIfExpressionList);
			}
			
			ElsePartContext elsePartContext=ifExprContext.elsePart();
			if(elsePartContext!=null){
				ec=elsePartContext.expr();
				ElseExpression elseExpression=new ElseExpression();
				elseExpression.setExpr(elsePartContext.getText());
				elseExpression.setExpression(parseExpr(ec));
				expr.setElseExpression(elseExpression);
			}
			return expr;
		}else if(caseExprContext!=null){
			IfExpression expr=new IfExpression();
			expr.setExpr(ctx.getText());
			List<ElseIfExpression> elseIfExpressionList=new ArrayList<ElseIfExpression>();
			expr.setElseIfExpressions(elseIfExpressionList);
			List<CasePartContext> casePartContexts=caseExprContext.casePart();
			for(CasePartContext casePartContext:casePartContexts){
				List<IfConditionContext> ifConditionContexts=casePartContext.ifCondition();
				List<JoinContext> joinContexts=casePartContext.join();
				ElseIfExpression elseIfExpr=new ElseIfExpression();
				elseIfExpr.setConditionList(parseCondtionList(ifConditionContexts,joinContexts));
				elseIfExpr.setExpr(casePartContext.getText());
				ExprContext ec=casePartContext.expr();
				elseIfExpr.setExpression(parseExpr(ec));
				elseIfExpressionList.add(elseIfExpr);
			}
			return expr;
		}else{
			throw new ReportParseException("Expression ["+ctx.getText()+"] is invalid.");
		}
	}
	private Expression parseExprComposite(ExprCompositeContext exprCompositeContext) {
		if(exprCompositeContext instanceof SingleExprCompositeContext){
			SingleExprCompositeContext singleExprCompositeContext=(SingleExprCompositeContext)exprCompositeContext;
			ExprContext exprContext=singleExprCompositeContext.expr();
			return parseExpr(exprContext);
		}else if(exprCompositeContext instanceof ParenExprCompositeContext){
			ParenExprCompositeContext parenExprCompositeContext=(ParenExprCompositeContext)exprCompositeContext;
			ExprCompositeContext childExprCompositeContext=parenExprCompositeContext.exprComposite();
			return parseExprComposite(childExprCompositeContext);
		}else if(exprCompositeContext instanceof TernaryExprCompositeContext){
			TernaryExprCompositeContext ternaryExprCompositeContext=(TernaryExprCompositeContext)exprCompositeContext;
			TernaryExprContext ternaryExprContext=ternaryExprCompositeContext.ternaryExpr();
			List<IfConditionContext> ifConditionContexts=ternaryExprContext.ifCondition();
			IfExpression expr=new IfExpression();
			expr.setConditionList(parseCondtionList(ifConditionContexts, ternaryExprContext.join()));
			ExprContext firstExprContext=ternaryExprContext.expr(0);
			ExprContext secondExprContext=ternaryExprContext.expr(1);
			expr.setExpression(parseExpr(firstExprContext));
			ElseExpression elseExpr=new ElseExpression();
			elseExpr.setExpression(parseExpr(secondExprContext));
			expr.setElseExpression(elseExpr);
			return expr;
		}else if(exprCompositeContext instanceof ComplexExprCompositeContext){
			ComplexExprCompositeContext complexExprCompositeContext=(ComplexExprCompositeContext)exprCompositeContext;
			ExprCompositeContext leftExprCompositeContext=complexExprCompositeContext.exprComposite(0);
			Expression leftExpression=parseExprComposite(leftExprCompositeContext);
			ExprCompositeContext rightExprCompositeContext=complexExprCompositeContext.exprComposite(1);
			Expression rightExpression=parseExprComposite(rightExprCompositeContext);
			String op=complexExprCompositeContext.Operator().getText();
			Operator operator=Operator.parse(op);
			List<BaseExpression> expressions=new ArrayList<BaseExpression>();
			expressions.add((BaseExpression)leftExpression);
			expressions.add((BaseExpression)rightExpression);
			List<Operator> operators=new ArrayList<Operator>();
			operators.add(operator);
			ParenExpression expression=new ParenExpression(operators, expressions);
			expression.setExpr(complexExprCompositeContext.getText());
			return expression;
		}else{
			throw new ReportParseException("Unknow context :"+exprCompositeContext);
		}
	}
	
	private Expression parseExpr(ExprContext exprContext) {
		List<BaseExpression> expressions=new ArrayList<BaseExpression>();
		List<Operator> operators=new ArrayList<Operator>();
		List<ItemContext> itemContexts = exprContext.item();
		List<TerminalNode> operatorNodes=exprContext.Operator();
		for(int i=0;i<itemContexts.size();i++){
			ItemContext itemContext=itemContexts.get(i);
			BaseExpression expr=parseItemContext(itemContext);
			expressions.add(expr);
			if(i>0){
				TerminalNode operatorNode=operatorNodes.get(i-1);
				String op=operatorNode.getText();
				operators.add(Operator.parse(op));
			}
		}
		ParenExpression expression=new ParenExpression(operators, expressions);
		expression.setExpr(exprContext.getText());
		return expression;
	}
	
	private ExpressionConditionList parseCondtionList(List<IfConditionContext> ifConditionContexts,List<JoinContext> joinContexts){
		List<ExpressionCondition> list=new ArrayList<ExpressionCondition>();
		List<Join> joins=new ArrayList<Join>();
		for(int i=0;i<ifConditionContexts.size();i++){
			IfConditionContext context=ifConditionContexts.get(i);
			ExprContext left=context.expr(0);
			ExprContext right=context.expr(1);
			Expression leftExpr=parseExpr(left);
			Expression rightExpr=parseExpr(right);
			Op op=Op.parse(context.OP().getText());
			ExpressionCondition condition=new ExpressionCondition(leftExpr,op,rightExpr);
			list.add(condition);
			if(i>0){
				JoinContext joinContext=joinContexts.get(i-1);
				Join join=Join.valueOf(joinContext.getText());
				joins.add(join);
			}
		}
		return new ExpressionConditionList(list,joins);
	}
	
	public BaseExpression parseItemContext(ItemContext itemContext){
		BaseExpression expression=null;
		if(itemContext instanceof SimpleJoinContext){
			SimpleJoinContext simpleJoinContext=(SimpleJoinContext)itemContext;
			expression=visitSimpleJoin(simpleJoinContext);
		}else if(itemContext instanceof ParenJoinContext){
			ParenJoinContext parenJoinContext=(ParenJoinContext)itemContext;
			expression=visitParenJoin(parenJoinContext);		
		}else if(itemContext instanceof SingleParenJoinContext){
			SingleParenJoinContext singleContext=(SingleParenJoinContext)itemContext;
			ItemContext childItemContext=singleContext.item();
			expression=parseItemContext(childItemContext);
		}else{
			throw new ReportParseException("Unknow context :"+itemContext);
		}
		return expression;
	}
	
	@Override
	public BaseExpression visitSimpleJoin(SimpleJoinContext ctx) {
		List<BaseExpression> expressions=new ArrayList<BaseExpression>();
		List<Operator> operators=new ArrayList<Operator>();
		List<UnitContext> unitContexts=ctx.unit();
		List<TerminalNode> operatorNodes=ctx.Operator();
		for(int i=0;i<unitContexts.size();i++){
			UnitContext unitContext=unitContexts.get(i);
			BaseExpression expr=buildExpression(unitContext);
			expressions.add(expr);
			if(i>0){
				TerminalNode operatorNode=operatorNodes.get(i-1);
				String op=operatorNode.getText();
				operators.add(Operator.parse(op));
			}
		}
		JoinExpression expression=new JoinExpression(operators,expressions);
		expression.setExpr(ctx.getText());
		return expression;
	}
	
	@Override
	public BaseExpression visitParenJoin(ParenJoinContext ctx) {
		List<BaseExpression> expressions=new ArrayList<BaseExpression>();
		List<Operator> operators=new ArrayList<Operator>();
		List<ItemContext> itemContexts=ctx.item();
		List<TerminalNode> operatorNodes=ctx.Operator();
		for(int i=0;i<itemContexts.size();i++){
			ItemContext itemContext=itemContexts.get(i);
			BaseExpression expr=parseItemContext(itemContext);
			expressions.add(expr);
			if(i>0){
				TerminalNode operatorNode=operatorNodes.get(i-1);
				String op=operatorNode.getText();
				operators.add(Operator.parse(op));
			}
		}
		ParenExpression expression=new ParenExpression(operators, expressions);
		expression.setExpr(ctx.getText());
		return expression;
	}
	
	private BaseExpression buildExpression(UnitContext unitContext){
		for(ExpressionBuilder builder:expressionBuilders){
			if(builder.support(unitContext)){
				return builder.build(unitContext);
			}
		}
		throw new ReportParseException("Unknow context :"+unitContext);
	}
}
