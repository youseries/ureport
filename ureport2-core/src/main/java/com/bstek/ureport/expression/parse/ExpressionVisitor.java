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
import com.bstek.ureport.dsl.ReportParserParser.BlockContext;
import com.bstek.ureport.dsl.ReportParserParser.CaseExprContext;
import com.bstek.ureport.dsl.ReportParserParser.CasePartContext;
import com.bstek.ureport.dsl.ReportParserParser.ComplexExprCompositeContext;
import com.bstek.ureport.dsl.ReportParserParser.ElseIfPartContext;
import com.bstek.ureport.dsl.ReportParserParser.ElsePartContext;
import com.bstek.ureport.dsl.ReportParserParser.EntryContext;
import com.bstek.ureport.dsl.ReportParserParser.ExprBlockContext;
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
import com.bstek.ureport.dsl.ReportParserParser.ReturnExprContext;
import com.bstek.ureport.dsl.ReportParserParser.SimpleJoinContext;
import com.bstek.ureport.dsl.ReportParserParser.SingleExprCompositeContext;
import com.bstek.ureport.dsl.ReportParserParser.SingleParenJoinContext;
import com.bstek.ureport.dsl.ReportParserParser.TernaryExprCompositeContext;
import com.bstek.ureport.dsl.ReportParserParser.TernaryExprContext;
import com.bstek.ureport.dsl.ReportParserParser.UnitContext;
import com.bstek.ureport.dsl.ReportParserParser.VariableAssignContext;
import com.bstek.ureport.dsl.ReportParserParser.VariableContext;
import com.bstek.ureport.exception.ReportParseException;
import com.bstek.ureport.expression.model.Expression;
import com.bstek.ureport.expression.model.Op;
import com.bstek.ureport.expression.model.Operator;
import com.bstek.ureport.expression.model.condition.Join;
import com.bstek.ureport.expression.model.expr.BaseExpression;
import com.bstek.ureport.expression.model.expr.ExpressionBlock;
import com.bstek.ureport.expression.model.expr.JoinExpression;
import com.bstek.ureport.expression.model.expr.ParenExpression;
import com.bstek.ureport.expression.model.expr.VariableAssignExpression;
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
	public Expression visitEntry(EntryContext ctx) {
		StringBuilder sb=new StringBuilder();
		List<ExpressionContext> exprs=ctx.expression();
		List<Expression> list=new ArrayList<Expression>();
		for(ExpressionContext exprContext:exprs){
			sb.append(exprContext.getText());
			Expression expr=visitExpression(exprContext);
			list.add(expr);
		}
		ExpressionBlock block=new ExpressionBlock();
		block.setExpressionList(list);
		block.setExpr(sb.toString());
		return block;
	}
	
	@Override
	public Expression visitExpression(ExpressionContext ctx) {
		ExprCompositeContext exprCompositeContext=ctx.exprComposite();
		IfExprContext ifExprContext=ctx.ifExpr();
		CaseExprContext caseExprContext=ctx.caseExpr();
		VariableAssignContext assignCtx=ctx.variableAssign();
		ReturnExprContext returnCtx=ctx.returnExpr();
		if(exprCompositeContext!=null){
			return parseExprComposite(exprCompositeContext);
		}else if(ifExprContext!=null){
			IfExpression expr = parseIfExprContext(ifExprContext);
			return expr;
		}else if(caseExprContext!=null){
			IfExpression expr = parseCaseExprContext(caseExprContext);
			return expr;
		}else if(assignCtx!=null){
			VariableAssignExpression expr=new VariableAssignExpression();
			expr.setExpr(assignCtx.getText());
			expr.setVariable(assignCtx.variable().Identifier().getText());
			expr.setExpression(parseItemContext(assignCtx.item()));
			return expr;
		}else if(returnCtx!=null){
			return parseExpr(returnCtx.expr());
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
			BlockContext firstBlockContext=ternaryExprContext.block(0);
			expr.setExpression(parseBlock(firstBlockContext));
			
			BlockContext secondBlockContext=ternaryExprContext.block(1);
			ElseExpression elseExpr=new ElseExpression();
			elseExpr.setExpression(parseBlock(secondBlockContext));
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
	
	private ExpressionBlock parseExpressionBlock(List<ExprBlockContext> contexts){
		StringBuilder sb=new StringBuilder();
		List<Expression> expressionList=new ArrayList<Expression>();
		for(ExprBlockContext ctx:contexts){
			sb.append(ctx.getText());
			VariableAssignContext assignContext=ctx.variableAssign();
			if(assignContext!=null){
				VariableContext varCtx=assignContext.variable();
				String variableName=varCtx.Identifier().getText();
				VariableAssignExpression assignExpr=new VariableAssignExpression();
				assignExpr.setExpr(assignContext.getText());
				assignExpr.setVariable(variableName);
				ItemContext itemCtx=assignContext.item();
				BaseExpression itemExpr=parseItemContext(itemCtx);
				assignExpr.setExpression(itemExpr);
				expressionList.add(assignExpr);
			}
			IfExprContext ifCtx=ctx.ifExpr();
			if(ifCtx!=null){
				IfExpression ifExpr=parseIfExprContext(ifCtx);
				expressionList.add(ifExpr);
			}
			CaseExprContext caseCtx=ctx.caseExpr();
			if(caseCtx!=null){
				IfExpression caseExpr = parseCaseExprContext(caseCtx);
				expressionList.add(caseExpr);
			}
		}
		ExpressionBlock blockExpr=new ExpressionBlock();
		blockExpr.setExpressionList(expressionList);
		blockExpr.setExpr(sb.toString());
		return blockExpr;
	}
	
	private IfExpression parseIfExprContext(IfExprContext ifExprContext) {
		IfExpression expr=new IfExpression();
		expr.setExpr(ifExprContext.getText());
		IfPartContext ifPartContext=ifExprContext.ifPart();
		List<IfConditionContext> ifConditionContexts=ifPartContext.ifCondition();
		List<JoinContext> joinContexts=ifPartContext.join();
		expr.setConditionList(parseCondtionList(ifConditionContexts,joinContexts));
		ExpressionBlock blockExpr=parseBlock(ifPartContext.block());
		expr.setExpression(blockExpr);
		List<ElseIfPartContext> elseIfPartContexts=ifExprContext.elseIfPart();
		if(elseIfPartContexts!=null && elseIfPartContexts.size()>0){
			List<ElseIfExpression> elseIfExpressionList=new ArrayList<ElseIfExpression>();
			for(ElseIfPartContext elseIfContext:elseIfPartContexts){
				ifConditionContexts=elseIfContext.ifCondition();
				joinContexts=elseIfContext.join();
				ElseIfExpression elseIfExpr=new ElseIfExpression();
				elseIfExpr.setConditionList(parseCondtionList(ifConditionContexts,joinContexts));
				elseIfExpr.setExpression(parseBlock(elseIfContext.block()));
				elseIfExpressionList.add(elseIfExpr);
			}
			expr.setElseIfExpressions(elseIfExpressionList);
		}
		
		ElsePartContext elsePartContext=ifExprContext.elsePart();
		if(elsePartContext!=null){
			ElseExpression elseExpression=new ElseExpression();
			elseExpression.setExpression(parseBlock(elsePartContext.block()));
			expr.setElseExpression(elseExpression);
		}
		return expr;
	}
	
	private ExpressionBlock parseBlock(BlockContext blockCtx){
		List<ExprBlockContext> exprBlockCtxs=blockCtx.exprBlock();
		ReturnExprContext returnCtx=blockCtx.returnExpr();
		ExpressionBlock block=null;
		if(exprBlockCtxs!=null){
			block=parseExpressionBlock(exprBlockCtxs);
		}
		if(returnCtx!=null){
			if(block==null)block=new ExpressionBlock();
			block.setReturnExpression(parseExpr(returnCtx.expr()));
		}
		return block;
	}
	
	private IfExpression parseCaseExprContext(CaseExprContext caseExprContext) {
		IfExpression expr=new IfExpression();
		List<ElseIfExpression> elseIfExpressionList=new ArrayList<ElseIfExpression>();
		expr.setElseIfExpressions(elseIfExpressionList);
		List<CasePartContext> casePartContexts=caseExprContext.casePart();
		for(CasePartContext casePartContext:casePartContexts){
			List<IfConditionContext> ifConditionContexts=casePartContext.ifCondition();
			List<JoinContext> joinContexts=casePartContext.join();
			ElseIfExpression elseIfExpr=new ElseIfExpression();
			elseIfExpr.setConditionList(parseCondtionList(ifConditionContexts,joinContexts));
			elseIfExpr.setExpr(casePartContext.getText());
			ExpressionBlock blockExpr=parseBlock(casePartContext.block());
			elseIfExpr.setExpression(blockExpr);
			elseIfExpressionList.add(elseIfExpr);
		}
		return expr;
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
				String text=joinContext.getText();
				Join join=Join.and;
				if(text.equals("or") || text.equals("||")){
					join=Join.or;
				}
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
		if(operators.size()==0 && expressions.size()==1){
			return expressions.get(0);
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
