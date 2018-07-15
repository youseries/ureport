// Generated from ReportParser.g4 by ANTLR 4.5.3
package com.bstek.ureport.dsl;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ReportParserParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, Cell=20, Operator=21, OP=22, ORDER=23, BOOLEAN=24, 
		COLON=25, COMMA=26, NULL=27, LeftParen=28, RightParen=29, STRING=30, AND=31, 
		OR=32, INTEGER=33, NUMBER=34, EXCLAMATION=35, EXP=36, Identifier=37, LETTER=38, 
		Char=39, DIGIT=40, WS=41, NL=42;
	public static final int
		RULE_entry = 0, RULE_expression = 1, RULE_exprComposite = 2, RULE_ternaryExpr = 3, 
		RULE_caseExpr = 4, RULE_casePart = 5, RULE_ifExpr = 6, RULE_ifPart = 7, 
		RULE_elseIfPart = 8, RULE_elsePart = 9, RULE_block = 10, RULE_exprBlock = 11, 
		RULE_returnExpr = 12, RULE_expr = 13, RULE_ifCondition = 14, RULE_variableAssign = 15, 
		RULE_item = 16, RULE_unit = 17, RULE_variable = 18, RULE_cellPosition = 19, 
		RULE_relativeCell = 20, RULE_currentCellValue = 21, RULE_currentCellData = 22, 
		RULE_cell = 23, RULE_dataset = 24, RULE_function = 25, RULE_functionParameter = 26, 
		RULE_set = 27, RULE_cellCoordinate = 28, RULE_coordinate = 29, RULE_cellIndicator = 30, 
		RULE_conditions = 31, RULE_condition = 32, RULE_property = 33, RULE_currentValue = 34, 
		RULE_simpleValue = 35, RULE_join = 36, RULE_aggregate = 37;
	public static final String[] ruleNames = {
		"entry", "expression", "exprComposite", "ternaryExpr", "caseExpr", "casePart", 
		"ifExpr", "ifPart", "elseIfPart", "elsePart", "block", "exprBlock", "returnExpr", 
		"expr", "ifCondition", "variableAssign", "item", "unit", "variable", "cellPosition", 
		"relativeCell", "currentCellValue", "currentCellData", "cell", "dataset", 
		"function", "functionParameter", "set", "cellCoordinate", "coordinate", 
		"cellIndicator", "conditions", "condition", "property", "currentValue", 
		"simpleValue", "join", "aggregate"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'?'", "'case'", "'{'", "'}'", "'if'", "'else'", "'return'", "';'", 
		"'var'", "'='", "'&'", "'$'", "'#'", "'.'", "'cell'", "'['", "']'", "'to'", 
		"'@'", null, null, null, null, null, "':'", "','", "'null'", "'('", "')'", 
		null, null, null, null, null, "'!'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, "Cell", "Operator", "OP", 
		"ORDER", "BOOLEAN", "COLON", "COMMA", "NULL", "LeftParen", "RightParen", 
		"STRING", "AND", "OR", "INTEGER", "NUMBER", "EXCLAMATION", "EXP", "Identifier", 
		"LETTER", "Char", "DIGIT", "WS", "NL"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "ReportParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ReportParserParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class EntryContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(ReportParserParser.EOF, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public EntryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_entry; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitEntry(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EntryContext entry() throws RecognitionException {
		EntryContext _localctx = new EntryContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_entry);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(76);
				expression();
				}
				}
				setState(79); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__4) | (1L << T__6) | (1L << T__8) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__14) | (1L << Cell) | (1L << BOOLEAN) | (1L << NULL) | (1L << LeftParen) | (1L << STRING) | (1L << INTEGER) | (1L << NUMBER) | (1L << Identifier))) != 0) );
			setState(81);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public ExprCompositeContext exprComposite() {
			return getRuleContext(ExprCompositeContext.class,0);
		}
		public IfExprContext ifExpr() {
			return getRuleContext(IfExprContext.class,0);
		}
		public CaseExprContext caseExpr() {
			return getRuleContext(CaseExprContext.class,0);
		}
		public ReturnExprContext returnExpr() {
			return getRuleContext(ReturnExprContext.class,0);
		}
		public VariableAssignContext variableAssign() {
			return getRuleContext(VariableAssignContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_expression);
		try {
			setState(88);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(83);
				exprComposite(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(84);
				ifExpr();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(85);
				caseExpr();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(86);
				returnExpr();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(87);
				variableAssign();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprCompositeContext extends ParserRuleContext {
		public ExprCompositeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exprComposite; }
	 
		public ExprCompositeContext() { }
		public void copyFrom(ExprCompositeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ComplexExprCompositeContext extends ExprCompositeContext {
		public List<ExprCompositeContext> exprComposite() {
			return getRuleContexts(ExprCompositeContext.class);
		}
		public ExprCompositeContext exprComposite(int i) {
			return getRuleContext(ExprCompositeContext.class,i);
		}
		public TerminalNode Operator() { return getToken(ReportParserParser.Operator, 0); }
		public ComplexExprCompositeContext(ExprCompositeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitComplexExprComposite(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SingleExprCompositeContext extends ExprCompositeContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public SingleExprCompositeContext(ExprCompositeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitSingleExprComposite(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParenExprCompositeContext extends ExprCompositeContext {
		public TerminalNode LeftParen() { return getToken(ReportParserParser.LeftParen, 0); }
		public ExprCompositeContext exprComposite() {
			return getRuleContext(ExprCompositeContext.class,0);
		}
		public TerminalNode RightParen() { return getToken(ReportParserParser.RightParen, 0); }
		public ParenExprCompositeContext(ExprCompositeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitParenExprComposite(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TernaryExprCompositeContext extends ExprCompositeContext {
		public TernaryExprContext ternaryExpr() {
			return getRuleContext(TernaryExprContext.class,0);
		}
		public TernaryExprCompositeContext(ExprCompositeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitTernaryExprComposite(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprCompositeContext exprComposite() throws RecognitionException {
		return exprComposite(0);
	}

	private ExprCompositeContext exprComposite(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprCompositeContext _localctx = new ExprCompositeContext(_ctx, _parentState);
		ExprCompositeContext _prevctx = _localctx;
		int _startState = 4;
		enterRecursionRule(_localctx, 4, RULE_exprComposite, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(97);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				{
				_localctx = new SingleExprCompositeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(91);
				expr();
				}
				break;
			case 2:
				{
				_localctx = new TernaryExprCompositeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(92);
				ternaryExpr();
				}
				break;
			case 3:
				{
				_localctx = new ParenExprCompositeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(93);
				match(LeftParen);
				setState(94);
				exprComposite(0);
				setState(95);
				match(RightParen);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(104);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ComplexExprCompositeContext(new ExprCompositeContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_exprComposite);
					setState(99);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(100);
					match(Operator);
					setState(101);
					exprComposite(2);
					}
					} 
				}
				setState(106);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class TernaryExprContext extends ParserRuleContext {
		public List<IfConditionContext> ifCondition() {
			return getRuleContexts(IfConditionContext.class);
		}
		public IfConditionContext ifCondition(int i) {
			return getRuleContext(IfConditionContext.class,i);
		}
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public List<JoinContext> join() {
			return getRuleContexts(JoinContext.class);
		}
		public JoinContext join(int i) {
			return getRuleContext(JoinContext.class,i);
		}
		public TernaryExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ternaryExpr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitTernaryExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TernaryExprContext ternaryExpr() throws RecognitionException {
		TernaryExprContext _localctx = new TernaryExprContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_ternaryExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(107);
			ifCondition();
			setState(113);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND || _la==OR) {
				{
				{
				setState(108);
				join();
				setState(109);
				ifCondition();
				}
				}
				setState(115);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(116);
			match(T__0);
			setState(117);
			block();
			setState(118);
			match(COLON);
			setState(119);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CaseExprContext extends ParserRuleContext {
		public List<CasePartContext> casePart() {
			return getRuleContexts(CasePartContext.class);
		}
		public CasePartContext casePart(int i) {
			return getRuleContext(CasePartContext.class,i);
		}
		public CaseExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_caseExpr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitCaseExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CaseExprContext caseExpr() throws RecognitionException {
		CaseExprContext _localctx = new CaseExprContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_caseExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(121);
			match(T__1);
			setState(122);
			match(T__2);
			setState(123);
			casePart();
			setState(128);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(124);
				match(COMMA);
				setState(125);
				casePart();
				}
				}
				setState(130);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(131);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CasePartContext extends ParserRuleContext {
		public List<IfConditionContext> ifCondition() {
			return getRuleContexts(IfConditionContext.class);
		}
		public IfConditionContext ifCondition(int i) {
			return getRuleContext(IfConditionContext.class,i);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public List<JoinContext> join() {
			return getRuleContexts(JoinContext.class);
		}
		public JoinContext join(int i) {
			return getRuleContext(JoinContext.class,i);
		}
		public CasePartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_casePart; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitCasePart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CasePartContext casePart() throws RecognitionException {
		CasePartContext _localctx = new CasePartContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_casePart);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(133);
			ifCondition();
			setState(139);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND || _la==OR) {
				{
				{
				setState(134);
				join();
				setState(135);
				ifCondition();
				}
				}
				setState(141);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(143);
			_la = _input.LA(1);
			if (_la==COLON) {
				{
				setState(142);
				match(COLON);
				}
			}

			setState(145);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfExprContext extends ParserRuleContext {
		public IfPartContext ifPart() {
			return getRuleContext(IfPartContext.class,0);
		}
		public List<ElseIfPartContext> elseIfPart() {
			return getRuleContexts(ElseIfPartContext.class);
		}
		public ElseIfPartContext elseIfPart(int i) {
			return getRuleContext(ElseIfPartContext.class,i);
		}
		public ElsePartContext elsePart() {
			return getRuleContext(ElsePartContext.class,0);
		}
		public IfExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifExpr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitIfExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfExprContext ifExpr() throws RecognitionException {
		IfExprContext _localctx = new IfExprContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_ifExpr);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(147);
			ifPart();
			setState(151);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(148);
					elseIfPart();
					}
					} 
				}
				setState(153);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			}
			setState(155);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				{
				setState(154);
				elsePart();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfPartContext extends ParserRuleContext {
		public List<IfConditionContext> ifCondition() {
			return getRuleContexts(IfConditionContext.class);
		}
		public IfConditionContext ifCondition(int i) {
			return getRuleContext(IfConditionContext.class,i);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public List<JoinContext> join() {
			return getRuleContexts(JoinContext.class);
		}
		public JoinContext join(int i) {
			return getRuleContext(JoinContext.class,i);
		}
		public IfPartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifPart; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitIfPart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfPartContext ifPart() throws RecognitionException {
		IfPartContext _localctx = new IfPartContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_ifPart);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(157);
			match(T__4);
			setState(158);
			match(LeftParen);
			setState(159);
			ifCondition();
			setState(165);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND || _la==OR) {
				{
				{
				setState(160);
				join();
				setState(161);
				ifCondition();
				}
				}
				setState(167);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(168);
			match(RightParen);
			setState(169);
			match(T__2);
			setState(170);
			block();
			setState(171);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElseIfPartContext extends ParserRuleContext {
		public List<IfConditionContext> ifCondition() {
			return getRuleContexts(IfConditionContext.class);
		}
		public IfConditionContext ifCondition(int i) {
			return getRuleContext(IfConditionContext.class,i);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public List<JoinContext> join() {
			return getRuleContexts(JoinContext.class);
		}
		public JoinContext join(int i) {
			return getRuleContext(JoinContext.class,i);
		}
		public ElseIfPartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elseIfPart; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitElseIfPart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElseIfPartContext elseIfPart() throws RecognitionException {
		ElseIfPartContext _localctx = new ElseIfPartContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_elseIfPart);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(173);
			match(T__5);
			setState(174);
			match(T__4);
			setState(175);
			match(LeftParen);
			setState(176);
			ifCondition();
			setState(182);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND || _la==OR) {
				{
				{
				setState(177);
				join();
				setState(178);
				ifCondition();
				}
				}
				setState(184);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(185);
			match(RightParen);
			setState(186);
			match(T__2);
			setState(187);
			block();
			setState(188);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElsePartContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ElsePartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elsePart; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitElsePart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElsePartContext elsePart() throws RecognitionException {
		ElsePartContext _localctx = new ElsePartContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_elsePart);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(190);
			match(T__5);
			setState(191);
			match(T__2);
			setState(192);
			block();
			setState(193);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public List<ExprBlockContext> exprBlock() {
			return getRuleContexts(ExprBlockContext.class);
		}
		public ExprBlockContext exprBlock(int i) {
			return getRuleContext(ExprBlockContext.class,i);
		}
		public ReturnExprContext returnExpr() {
			return getRuleContext(ReturnExprContext.class,0);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_block);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(198);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(195);
					exprBlock();
					}
					} 
				}
				setState(200);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			setState(202);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				{
				setState(201);
				returnExpr();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprBlockContext extends ParserRuleContext {
		public VariableAssignContext variableAssign() {
			return getRuleContext(VariableAssignContext.class,0);
		}
		public IfExprContext ifExpr() {
			return getRuleContext(IfExprContext.class,0);
		}
		public CaseExprContext caseExpr() {
			return getRuleContext(CaseExprContext.class,0);
		}
		public ExprBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exprBlock; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitExprBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprBlockContext exprBlock() throws RecognitionException {
		ExprBlockContext _localctx = new ExprBlockContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_exprBlock);
		try {
			setState(207);
			switch (_input.LA(1)) {
			case T__8:
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(204);
				variableAssign();
				}
				break;
			case T__4:
				enterOuterAlt(_localctx, 2);
				{
				setState(205);
				ifExpr();
				}
				break;
			case T__1:
				enterOuterAlt(_localctx, 3);
				{
				setState(206);
				caseExpr();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnExprContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ReturnExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnExpr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitReturnExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnExprContext returnExpr() throws RecognitionException {
		ReturnExprContext _localctx = new ReturnExprContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_returnExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(210);
			_la = _input.LA(1);
			if (_la==T__6) {
				{
				setState(209);
				match(T__6);
				}
			}

			setState(212);
			expr();
			setState(214);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				{
				setState(213);
				match(T__7);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public List<ItemContext> item() {
			return getRuleContexts(ItemContext.class);
		}
		public ItemContext item(int i) {
			return getRuleContext(ItemContext.class,i);
		}
		public List<TerminalNode> Operator() { return getTokens(ReportParserParser.Operator); }
		public TerminalNode Operator(int i) {
			return getToken(ReportParserParser.Operator, i);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_expr);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(216);
			item();
			setState(221);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(217);
					match(Operator);
					setState(218);
					item();
					}
					} 
				}
				setState(223);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfConditionContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode OP() { return getToken(ReportParserParser.OP, 0); }
		public IfConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifCondition; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitIfCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfConditionContext ifCondition() throws RecognitionException {
		IfConditionContext _localctx = new IfConditionContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_ifCondition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(224);
			expr();
			setState(225);
			match(OP);
			setState(226);
			expr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableAssignContext extends ParserRuleContext {
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public ItemContext item() {
			return getRuleContext(ItemContext.class,0);
		}
		public VariableAssignContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableAssign; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitVariableAssign(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableAssignContext variableAssign() throws RecognitionException {
		VariableAssignContext _localctx = new VariableAssignContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_variableAssign);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(229);
			_la = _input.LA(1);
			if (_la==T__8) {
				{
				setState(228);
				match(T__8);
				}
			}

			setState(231);
			variable();
			setState(232);
			match(T__9);
			setState(233);
			item();
			setState(235);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				{
				setState(234);
				match(T__7);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ItemContext extends ParserRuleContext {
		public ItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_item; }
	 
		public ItemContext() { }
		public void copyFrom(ItemContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class SingleParenJoinContext extends ItemContext {
		public TerminalNode LeftParen() { return getToken(ReportParserParser.LeftParen, 0); }
		public ItemContext item() {
			return getRuleContext(ItemContext.class,0);
		}
		public TerminalNode RightParen() { return getToken(ReportParserParser.RightParen, 0); }
		public SingleParenJoinContext(ItemContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitSingleParenJoin(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParenJoinContext extends ItemContext {
		public TerminalNode LeftParen() { return getToken(ReportParserParser.LeftParen, 0); }
		public List<ItemContext> item() {
			return getRuleContexts(ItemContext.class);
		}
		public ItemContext item(int i) {
			return getRuleContext(ItemContext.class,i);
		}
		public TerminalNode RightParen() { return getToken(ReportParserParser.RightParen, 0); }
		public List<TerminalNode> Operator() { return getTokens(ReportParserParser.Operator); }
		public TerminalNode Operator(int i) {
			return getToken(ReportParserParser.Operator, i);
		}
		public ParenJoinContext(ItemContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitParenJoin(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SimpleJoinContext extends ItemContext {
		public List<UnitContext> unit() {
			return getRuleContexts(UnitContext.class);
		}
		public UnitContext unit(int i) {
			return getRuleContext(UnitContext.class,i);
		}
		public List<TerminalNode> Operator() { return getTokens(ReportParserParser.Operator); }
		public TerminalNode Operator(int i) {
			return getToken(ReportParserParser.Operator, i);
		}
		public SimpleJoinContext(ItemContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitSimpleJoin(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ItemContext item() throws RecognitionException {
		ItemContext _localctx = new ItemContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_item);
		int _la;
		try {
			int _alt;
			setState(259);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				_localctx = new SimpleJoinContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(237);
				unit();
				setState(242);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(238);
						match(Operator);
						setState(239);
						unit();
						}
						} 
					}
					setState(244);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
				}
				}
				break;
			case 2:
				_localctx = new SingleParenJoinContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(245);
				match(LeftParen);
				setState(246);
				item();
				setState(247);
				match(RightParen);
				}
				break;
			case 3:
				_localctx = new ParenJoinContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(249);
				match(LeftParen);
				setState(250);
				item();
				setState(253); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(251);
					match(Operator);
					setState(252);
					item();
					}
					}
					setState(255); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==Operator );
				setState(257);
				match(RightParen);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnitContext extends ParserRuleContext {
		public DatasetContext dataset() {
			return getRuleContext(DatasetContext.class,0);
		}
		public FunctionContext function() {
			return getRuleContext(FunctionContext.class,0);
		}
		public SetContext set() {
			return getRuleContext(SetContext.class,0);
		}
		public CellPositionContext cellPosition() {
			return getRuleContext(CellPositionContext.class,0);
		}
		public RelativeCellContext relativeCell() {
			return getRuleContext(RelativeCellContext.class,0);
		}
		public CurrentCellValueContext currentCellValue() {
			return getRuleContext(CurrentCellValueContext.class,0);
		}
		public CurrentCellDataContext currentCellData() {
			return getRuleContext(CurrentCellDataContext.class,0);
		}
		public CellContext cell() {
			return getRuleContext(CellContext.class,0);
		}
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public TerminalNode INTEGER() { return getToken(ReportParserParser.INTEGER, 0); }
		public TerminalNode BOOLEAN() { return getToken(ReportParserParser.BOOLEAN, 0); }
		public TerminalNode STRING() { return getToken(ReportParserParser.STRING, 0); }
		public TerminalNode NUMBER() { return getToken(ReportParserParser.NUMBER, 0); }
		public TerminalNode NULL() { return getToken(ReportParserParser.NULL, 0); }
		public UnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unit; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitUnit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnitContext unit() throws RecognitionException {
		UnitContext _localctx = new UnitContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_unit);
		try {
			setState(275);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(261);
				dataset();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(262);
				function();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(263);
				set(0);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(264);
				cellPosition();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(265);
				relativeCell();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(266);
				currentCellValue();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(267);
				currentCellData();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(268);
				cell();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(269);
				variable();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(270);
				match(INTEGER);
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(271);
				match(BOOLEAN);
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(272);
				match(STRING);
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(273);
				match(NUMBER);
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(274);
				match(NULL);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(ReportParserParser.Identifier, 0); }
		public VariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitVariable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableContext variable() throws RecognitionException {
		VariableContext _localctx = new VariableContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_variable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(277);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CellPositionContext extends ParserRuleContext {
		public TerminalNode Cell() { return getToken(ReportParserParser.Cell, 0); }
		public CellPositionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cellPosition; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitCellPosition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CellPositionContext cellPosition() throws RecognitionException {
		CellPositionContext _localctx = new CellPositionContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_cellPosition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(279);
			match(T__10);
			setState(280);
			match(Cell);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RelativeCellContext extends ParserRuleContext {
		public TerminalNode Cell() { return getToken(ReportParserParser.Cell, 0); }
		public RelativeCellContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relativeCell; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitRelativeCell(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelativeCellContext relativeCell() throws RecognitionException {
		RelativeCellContext _localctx = new RelativeCellContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_relativeCell);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(282);
			match(T__11);
			setState(283);
			match(Cell);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CurrentCellValueContext extends ParserRuleContext {
		public CurrentCellValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_currentCellValue; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitCurrentCellValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CurrentCellValueContext currentCellValue() throws RecognitionException {
		CurrentCellValueContext _localctx = new CurrentCellValueContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_currentCellValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(285);
			match(T__12);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CurrentCellDataContext extends ParserRuleContext {
		public PropertyContext property() {
			return getRuleContext(PropertyContext.class,0);
		}
		public CurrentCellDataContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_currentCellData; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitCurrentCellData(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CurrentCellDataContext currentCellData() throws RecognitionException {
		CurrentCellDataContext _localctx = new CurrentCellDataContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_currentCellData);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(287);
			match(T__12);
			setState(288);
			match(T__13);
			setState(289);
			property(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CellContext extends ParserRuleContext {
		public PropertyContext property() {
			return getRuleContext(PropertyContext.class,0);
		}
		public CellContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cell; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitCell(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CellContext cell() throws RecognitionException {
		CellContext _localctx = new CellContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_cell);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(291);
			match(T__14);
			setState(294);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				{
				setState(292);
				match(T__13);
				setState(293);
				property(0);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DatasetContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(ReportParserParser.Identifier, 0); }
		public AggregateContext aggregate() {
			return getRuleContext(AggregateContext.class,0);
		}
		public PropertyContext property() {
			return getRuleContext(PropertyContext.class,0);
		}
		public ConditionsContext conditions() {
			return getRuleContext(ConditionsContext.class,0);
		}
		public TerminalNode ORDER() { return getToken(ReportParserParser.ORDER, 0); }
		public DatasetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dataset; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitDataset(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DatasetContext dataset() throws RecognitionException {
		DatasetContext _localctx = new DatasetContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_dataset);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(296);
			match(Identifier);
			setState(297);
			match(T__13);
			setState(298);
			aggregate();
			setState(299);
			match(LeftParen);
			setState(301);
			_la = _input.LA(1);
			if (_la==Identifier) {
				{
				setState(300);
				property(0);
				}
			}

			setState(305);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
			case 1:
				{
				setState(303);
				match(COMMA);
				setState(304);
				conditions();
				}
				break;
			}
			setState(309);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(307);
				match(COMMA);
				setState(308);
				match(ORDER);
				}
			}

			setState(311);
			match(RightParen);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(ReportParserParser.Identifier, 0); }
		public FunctionParameterContext functionParameter() {
			return getRuleContext(FunctionParameterContext.class,0);
		}
		public FunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionContext function() throws RecognitionException {
		FunctionContext _localctx = new FunctionContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_function);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(313);
			match(Identifier);
			setState(314);
			match(LeftParen);
			setState(316);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__14) | (1L << Cell) | (1L << BOOLEAN) | (1L << NULL) | (1L << LeftParen) | (1L << STRING) | (1L << INTEGER) | (1L << NUMBER) | (1L << Identifier))) != 0)) {
				{
				setState(315);
				functionParameter();
				}
			}

			setState(318);
			match(RightParen);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionParameterContext extends ParserRuleContext {
		public List<ItemContext> item() {
			return getRuleContexts(ItemContext.class);
		}
		public ItemContext item(int i) {
			return getRuleContext(ItemContext.class,i);
		}
		public FunctionParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionParameter; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitFunctionParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionParameterContext functionParameter() throws RecognitionException {
		FunctionParameterContext _localctx = new FunctionParameterContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_functionParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(320);
			item();
			setState(327);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__14) | (1L << Cell) | (1L << BOOLEAN) | (1L << COMMA) | (1L << NULL) | (1L << LeftParen) | (1L << STRING) | (1L << INTEGER) | (1L << NUMBER) | (1L << Identifier))) != 0)) {
				{
				{
				setState(322);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(321);
					match(COMMA);
					}
				}

				setState(324);
				item();
				}
				}
				setState(329);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SetContext extends ParserRuleContext {
		public SetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_set; }
	 
		public SetContext() { }
		public void copyFrom(SetContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CellPairContext extends SetContext {
		public List<TerminalNode> Cell() { return getTokens(ReportParserParser.Cell); }
		public TerminalNode Cell(int i) {
			return getToken(ReportParserParser.Cell, i);
		}
		public CellPairContext(SetContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitCellPair(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class WholeCellContext extends SetContext {
		public TerminalNode Cell() { return getToken(ReportParserParser.Cell, 0); }
		public ConditionsContext conditions() {
			return getRuleContext(ConditionsContext.class,0);
		}
		public WholeCellContext(SetContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitWholeCell(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CellCoordinateConditionContext extends SetContext {
		public TerminalNode Cell() { return getToken(ReportParserParser.Cell, 0); }
		public CellCoordinateContext cellCoordinate() {
			return getRuleContext(CellCoordinateContext.class,0);
		}
		public ConditionsContext conditions() {
			return getRuleContext(ConditionsContext.class,0);
		}
		public CellCoordinateConditionContext(SetContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitCellCoordinateCondition(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SingleCellConditionContext extends SetContext {
		public TerminalNode Cell() { return getToken(ReportParserParser.Cell, 0); }
		public ConditionsContext conditions() {
			return getRuleContext(ConditionsContext.class,0);
		}
		public SingleCellConditionContext(SetContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitSingleCellCondition(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SingleCellContext extends SetContext {
		public TerminalNode Cell() { return getToken(ReportParserParser.Cell, 0); }
		public SingleCellContext(SetContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitSingleCell(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SimpleDataContext extends SetContext {
		public SimpleValueContext simpleValue() {
			return getRuleContext(SimpleValueContext.class,0);
		}
		public SimpleDataContext(SetContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitSimpleData(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RangeContext extends SetContext {
		public List<SetContext> set() {
			return getRuleContexts(SetContext.class);
		}
		public SetContext set(int i) {
			return getRuleContext(SetContext.class,i);
		}
		public RangeContext(SetContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitRange(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SingleCellCoordinateContext extends SetContext {
		public TerminalNode Cell() { return getToken(ReportParserParser.Cell, 0); }
		public CellCoordinateContext cellCoordinate() {
			return getRuleContext(CellCoordinateContext.class,0);
		}
		public SingleCellCoordinateContext(SetContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitSingleCellCoordinate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SetContext set() throws RecognitionException {
		return set(0);
	}

	private SetContext set(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		SetContext _localctx = new SetContext(_ctx, _parentState);
		SetContext _prevctx = _localctx;
		int _startState = 54;
		enterRecursionRule(_localctx, 54, RULE_set, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(363);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,32,_ctx) ) {
			case 1:
				{
				_localctx = new SimpleDataContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(331);
				simpleValue();
				}
				break;
			case 2:
				{
				_localctx = new SingleCellContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(332);
				match(Cell);
				}
				break;
			case 3:
				{
				_localctx = new WholeCellContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(333);
				match(Cell);
				setState(334);
				match(T__15);
				setState(335);
				match(T__16);
				setState(340);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
				case 1:
					{
					setState(336);
					match(T__2);
					setState(337);
					conditions();
					setState(338);
					match(T__3);
					}
					break;
				}
				}
				break;
			case 4:
				{
				_localctx = new CellPairContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(342);
				match(Cell);
				setState(343);
				match(COLON);
				setState(344);
				match(Cell);
				}
				break;
			case 5:
				{
				_localctx = new SingleCellConditionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(345);
				match(Cell);
				setState(346);
				match(T__2);
				setState(347);
				conditions();
				setState(348);
				match(T__3);
				}
				break;
			case 6:
				{
				_localctx = new SingleCellCoordinateContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(350);
				match(Cell);
				setState(351);
				match(T__15);
				setState(352);
				cellCoordinate();
				setState(353);
				match(T__16);
				}
				break;
			case 7:
				{
				_localctx = new CellCoordinateConditionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(355);
				match(Cell);
				setState(356);
				match(T__15);
				setState(357);
				cellCoordinate();
				setState(358);
				match(T__16);
				setState(359);
				match(T__2);
				setState(360);
				conditions();
				setState(361);
				match(T__3);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(370);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new RangeContext(new SetContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_set);
					setState(365);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(366);
					match(T__17);
					setState(367);
					set(2);
					}
					} 
				}
				setState(372);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class CellCoordinateContext extends ParserRuleContext {
		public List<CoordinateContext> coordinate() {
			return getRuleContexts(CoordinateContext.class);
		}
		public CoordinateContext coordinate(int i) {
			return getRuleContext(CoordinateContext.class,i);
		}
		public CellCoordinateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cellCoordinate; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitCellCoordinate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CellCoordinateContext cellCoordinate() throws RecognitionException {
		CellCoordinateContext _localctx = new CellCoordinateContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_cellCoordinate);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(373);
			coordinate();
			setState(376);
			_la = _input.LA(1);
			if (_la==T__7) {
				{
				setState(374);
				match(T__7);
				setState(375);
				coordinate();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CoordinateContext extends ParserRuleContext {
		public List<CellIndicatorContext> cellIndicator() {
			return getRuleContexts(CellIndicatorContext.class);
		}
		public CellIndicatorContext cellIndicator(int i) {
			return getRuleContext(CellIndicatorContext.class,i);
		}
		public CoordinateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_coordinate; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitCoordinate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CoordinateContext coordinate() throws RecognitionException {
		CoordinateContext _localctx = new CoordinateContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_coordinate);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(378);
			cellIndicator();
			setState(383);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(379);
				match(COMMA);
				setState(380);
				cellIndicator();
				}
				}
				setState(385);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CellIndicatorContext extends ParserRuleContext {
		public CellIndicatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cellIndicator; }
	 
		public CellIndicatorContext() { }
		public void copyFrom(CellIndicatorContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AbsoluteContext extends CellIndicatorContext {
		public TerminalNode Cell() { return getToken(ReportParserParser.Cell, 0); }
		public TerminalNode INTEGER() { return getToken(ReportParserParser.INTEGER, 0); }
		public TerminalNode EXCLAMATION() { return getToken(ReportParserParser.EXCLAMATION, 0); }
		public AbsoluteContext(CellIndicatorContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitAbsolute(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RelativeContext extends CellIndicatorContext {
		public TerminalNode Cell() { return getToken(ReportParserParser.Cell, 0); }
		public RelativeContext(CellIndicatorContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitRelative(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CellIndicatorContext cellIndicator() throws RecognitionException {
		CellIndicatorContext _localctx = new CellIndicatorContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_cellIndicator);
		int _la;
		try {
			setState(393);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
			case 1:
				_localctx = new RelativeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(386);
				match(Cell);
				}
				break;
			case 2:
				_localctx = new AbsoluteContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(387);
				match(Cell);
				setState(388);
				match(COLON);
				setState(390);
				_la = _input.LA(1);
				if (_la==EXCLAMATION) {
					{
					setState(389);
					match(EXCLAMATION);
					}
				}

				setState(392);
				match(INTEGER);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionsContext extends ParserRuleContext {
		public List<ConditionContext> condition() {
			return getRuleContexts(ConditionContext.class);
		}
		public ConditionContext condition(int i) {
			return getRuleContext(ConditionContext.class,i);
		}
		public List<JoinContext> join() {
			return getRuleContexts(JoinContext.class);
		}
		public JoinContext join(int i) {
			return getRuleContext(JoinContext.class,i);
		}
		public ConditionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditions; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitConditions(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionsContext conditions() throws RecognitionException {
		ConditionsContext _localctx = new ConditionsContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_conditions);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(395);
			condition();
			setState(401);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND || _la==OR) {
				{
				{
				setState(396);
				join();
				setState(397);
				condition();
				}
				}
				setState(403);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionContext extends ParserRuleContext {
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
	 
		public ConditionContext() { }
		public void copyFrom(ConditionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ExprConditionContext extends ConditionContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode OP() { return getToken(ReportParserParser.OP, 0); }
		public ExprConditionContext(ConditionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitExprCondition(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CurrentValueConditionContext extends ConditionContext {
		public CurrentValueContext currentValue() {
			return getRuleContext(CurrentValueContext.class,0);
		}
		public TerminalNode OP() { return getToken(ReportParserParser.OP, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public CurrentValueConditionContext(ConditionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitCurrentValueCondition(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PropertyConditionContext extends ConditionContext {
		public PropertyContext property() {
			return getRuleContext(PropertyContext.class,0);
		}
		public TerminalNode OP() { return getToken(ReportParserParser.OP, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public PropertyConditionContext(ConditionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitPropertyCondition(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CellNameExprConditionContext extends ConditionContext {
		public TerminalNode Cell() { return getToken(ReportParserParser.Cell, 0); }
		public TerminalNode OP() { return getToken(ReportParserParser.OP, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public CellNameExprConditionContext(ConditionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitCellNameExprCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_condition);
		try {
			setState(419);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,39,_ctx) ) {
			case 1:
				_localctx = new CellNameExprConditionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(404);
				match(Cell);
				setState(405);
				match(OP);
				setState(406);
				expr();
				}
				break;
			case 2:
				_localctx = new PropertyConditionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(407);
				property(0);
				setState(408);
				match(OP);
				setState(409);
				expr();
				}
				break;
			case 3:
				_localctx = new CurrentValueConditionContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(411);
				currentValue();
				setState(412);
				match(OP);
				setState(413);
				expr();
				}
				break;
			case 4:
				_localctx = new ExprConditionContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(415);
				expr();
				setState(416);
				match(OP);
				setState(417);
				expr();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PropertyContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(ReportParserParser.Identifier, 0); }
		public List<PropertyContext> property() {
			return getRuleContexts(PropertyContext.class);
		}
		public PropertyContext property(int i) {
			return getRuleContext(PropertyContext.class,i);
		}
		public PropertyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_property; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitProperty(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertyContext property() throws RecognitionException {
		return property(0);
	}

	private PropertyContext property(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		PropertyContext _localctx = new PropertyContext(_ctx, _parentState);
		PropertyContext _prevctx = _localctx;
		int _startState = 66;
		enterRecursionRule(_localctx, 66, RULE_property, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(422);
			match(Identifier);
			}
			_ctx.stop = _input.LT(-1);
			setState(429);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,40,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new PropertyContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_property);
					setState(424);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(425);
					match(T__13);
					setState(426);
					property(2);
					}
					} 
				}
				setState(431);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,40,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class CurrentValueContext extends ParserRuleContext {
		public CurrentValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_currentValue; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitCurrentValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CurrentValueContext currentValue() throws RecognitionException {
		CurrentValueContext _localctx = new CurrentValueContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_currentValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(432);
			match(T__18);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SimpleValueContext extends ParserRuleContext {
		public TerminalNode INTEGER() { return getToken(ReportParserParser.INTEGER, 0); }
		public TerminalNode NUMBER() { return getToken(ReportParserParser.NUMBER, 0); }
		public TerminalNode STRING() { return getToken(ReportParserParser.STRING, 0); }
		public TerminalNode BOOLEAN() { return getToken(ReportParserParser.BOOLEAN, 0); }
		public TerminalNode NULL() { return getToken(ReportParserParser.NULL, 0); }
		public SimpleValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleValue; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitSimpleValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SimpleValueContext simpleValue() throws RecognitionException {
		SimpleValueContext _localctx = new SimpleValueContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_simpleValue);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(434);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << NULL) | (1L << STRING) | (1L << INTEGER) | (1L << NUMBER))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class JoinContext extends ParserRuleContext {
		public TerminalNode AND() { return getToken(ReportParserParser.AND, 0); }
		public TerminalNode OR() { return getToken(ReportParserParser.OR, 0); }
		public JoinContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_join; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitJoin(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JoinContext join() throws RecognitionException {
		JoinContext _localctx = new JoinContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_join);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(436);
			_la = _input.LA(1);
			if ( !(_la==AND || _la==OR) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AggregateContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(ReportParserParser.Identifier, 0); }
		public AggregateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_aggregate; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReportParserVisitor ) return ((ReportParserVisitor<? extends T>)visitor).visitAggregate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AggregateContext aggregate() throws RecognitionException {
		AggregateContext _localctx = new AggregateContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_aggregate);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(438);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 2:
			return exprComposite_sempred((ExprCompositeContext)_localctx, predIndex);
		case 27:
			return set_sempred((SetContext)_localctx, predIndex);
		case 33:
			return property_sempred((PropertyContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean exprComposite_sempred(ExprCompositeContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean set_sempred(SetContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean property_sempred(PropertyContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3,\u01bb\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\3\2\6\2P\n\2\r\2\16\2Q\3\2"+
		"\3\2\3\3\3\3\3\3\3\3\3\3\5\3[\n\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4d\n\4"+
		"\3\4\3\4\3\4\7\4i\n\4\f\4\16\4l\13\4\3\5\3\5\3\5\3\5\7\5r\n\5\f\5\16\5"+
		"u\13\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\7\6\u0081\n\6\f\6\16\6"+
		"\u0084\13\6\3\6\3\6\3\7\3\7\3\7\3\7\7\7\u008c\n\7\f\7\16\7\u008f\13\7"+
		"\3\7\5\7\u0092\n\7\3\7\3\7\3\b\3\b\7\b\u0098\n\b\f\b\16\b\u009b\13\b\3"+
		"\b\5\b\u009e\n\b\3\t\3\t\3\t\3\t\3\t\3\t\7\t\u00a6\n\t\f\t\16\t\u00a9"+
		"\13\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\7\n\u00b7\n\n\f"+
		"\n\16\n\u00ba\13\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\f\7"+
		"\f\u00c7\n\f\f\f\16\f\u00ca\13\f\3\f\5\f\u00cd\n\f\3\r\3\r\3\r\5\r\u00d2"+
		"\n\r\3\16\5\16\u00d5\n\16\3\16\3\16\5\16\u00d9\n\16\3\17\3\17\3\17\7\17"+
		"\u00de\n\17\f\17\16\17\u00e1\13\17\3\20\3\20\3\20\3\20\3\21\5\21\u00e8"+
		"\n\21\3\21\3\21\3\21\3\21\5\21\u00ee\n\21\3\22\3\22\3\22\7\22\u00f3\n"+
		"\22\f\22\16\22\u00f6\13\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\6\22"+
		"\u0100\n\22\r\22\16\22\u0101\3\22\3\22\5\22\u0106\n\22\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u0116\n\23"+
		"\3\24\3\24\3\25\3\25\3\25\3\26\3\26\3\26\3\27\3\27\3\30\3\30\3\30\3\30"+
		"\3\31\3\31\3\31\5\31\u0129\n\31\3\32\3\32\3\32\3\32\3\32\5\32\u0130\n"+
		"\32\3\32\3\32\5\32\u0134\n\32\3\32\3\32\5\32\u0138\n\32\3\32\3\32\3\33"+
		"\3\33\3\33\5\33\u013f\n\33\3\33\3\33\3\34\3\34\5\34\u0145\n\34\3\34\7"+
		"\34\u0148\n\34\f\34\16\34\u014b\13\34\3\35\3\35\3\35\3\35\3\35\3\35\3"+
		"\35\3\35\3\35\3\35\5\35\u0157\n\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35"+
		"\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35"+
		"\5\35\u016e\n\35\3\35\3\35\3\35\7\35\u0173\n\35\f\35\16\35\u0176\13\35"+
		"\3\36\3\36\3\36\5\36\u017b\n\36\3\37\3\37\3\37\7\37\u0180\n\37\f\37\16"+
		"\37\u0183\13\37\3 \3 \3 \3 \5 \u0189\n \3 \5 \u018c\n \3!\3!\3!\3!\7!"+
		"\u0192\n!\f!\16!\u0195\13!\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\""+
		"\3\"\3\"\3\"\3\"\5\"\u01a6\n\"\3#\3#\3#\3#\3#\3#\7#\u01ae\n#\f#\16#\u01b1"+
		"\13#\3$\3$\3%\3%\3&\3&\3\'\3\'\3\'\2\5\68D(\2\4\6\b\n\f\16\20\22\24\26"+
		"\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJL\2\4\6\2\32\32\35\35  #$\3"+
		"\2!\"\u01d6\2O\3\2\2\2\4Z\3\2\2\2\6c\3\2\2\2\bm\3\2\2\2\n{\3\2\2\2\f\u0087"+
		"\3\2\2\2\16\u0095\3\2\2\2\20\u009f\3\2\2\2\22\u00af\3\2\2\2\24\u00c0\3"+
		"\2\2\2\26\u00c8\3\2\2\2\30\u00d1\3\2\2\2\32\u00d4\3\2\2\2\34\u00da\3\2"+
		"\2\2\36\u00e2\3\2\2\2 \u00e7\3\2\2\2\"\u0105\3\2\2\2$\u0115\3\2\2\2&\u0117"+
		"\3\2\2\2(\u0119\3\2\2\2*\u011c\3\2\2\2,\u011f\3\2\2\2.\u0121\3\2\2\2\60"+
		"\u0125\3\2\2\2\62\u012a\3\2\2\2\64\u013b\3\2\2\2\66\u0142\3\2\2\28\u016d"+
		"\3\2\2\2:\u0177\3\2\2\2<\u017c\3\2\2\2>\u018b\3\2\2\2@\u018d\3\2\2\2B"+
		"\u01a5\3\2\2\2D\u01a7\3\2\2\2F\u01b2\3\2\2\2H\u01b4\3\2\2\2J\u01b6\3\2"+
		"\2\2L\u01b8\3\2\2\2NP\5\4\3\2ON\3\2\2\2PQ\3\2\2\2QO\3\2\2\2QR\3\2\2\2"+
		"RS\3\2\2\2ST\7\2\2\3T\3\3\2\2\2U[\5\6\4\2V[\5\16\b\2W[\5\n\6\2X[\5\32"+
		"\16\2Y[\5 \21\2ZU\3\2\2\2ZV\3\2\2\2ZW\3\2\2\2ZX\3\2\2\2ZY\3\2\2\2[\5\3"+
		"\2\2\2\\]\b\4\1\2]d\5\34\17\2^d\5\b\5\2_`\7\36\2\2`a\5\6\4\2ab\7\37\2"+
		"\2bd\3\2\2\2c\\\3\2\2\2c^\3\2\2\2c_\3\2\2\2dj\3\2\2\2ef\f\3\2\2fg\7\27"+
		"\2\2gi\5\6\4\4he\3\2\2\2il\3\2\2\2jh\3\2\2\2jk\3\2\2\2k\7\3\2\2\2lj\3"+
		"\2\2\2ms\5\36\20\2no\5J&\2op\5\36\20\2pr\3\2\2\2qn\3\2\2\2ru\3\2\2\2s"+
		"q\3\2\2\2st\3\2\2\2tv\3\2\2\2us\3\2\2\2vw\7\3\2\2wx\5\26\f\2xy\7\33\2"+
		"\2yz\5\26\f\2z\t\3\2\2\2{|\7\4\2\2|}\7\5\2\2}\u0082\5\f\7\2~\177\7\34"+
		"\2\2\177\u0081\5\f\7\2\u0080~\3\2\2\2\u0081\u0084\3\2\2\2\u0082\u0080"+
		"\3\2\2\2\u0082\u0083\3\2\2\2\u0083\u0085\3\2\2\2\u0084\u0082\3\2\2\2\u0085"+
		"\u0086\7\6\2\2\u0086\13\3\2\2\2\u0087\u008d\5\36\20\2\u0088\u0089\5J&"+
		"\2\u0089\u008a\5\36\20\2\u008a\u008c\3\2\2\2\u008b\u0088\3\2\2\2\u008c"+
		"\u008f\3\2\2\2\u008d\u008b\3\2\2\2\u008d\u008e\3\2\2\2\u008e\u0091\3\2"+
		"\2\2\u008f\u008d\3\2\2\2\u0090\u0092\7\33\2\2\u0091\u0090\3\2\2\2\u0091"+
		"\u0092\3\2\2\2\u0092\u0093\3\2\2\2\u0093\u0094\5\26\f\2\u0094\r\3\2\2"+
		"\2\u0095\u0099\5\20\t\2\u0096\u0098\5\22\n\2\u0097\u0096\3\2\2\2\u0098"+
		"\u009b\3\2\2\2\u0099\u0097\3\2\2\2\u0099\u009a\3\2\2\2\u009a\u009d\3\2"+
		"\2\2\u009b\u0099\3\2\2\2\u009c\u009e\5\24\13\2\u009d\u009c\3\2\2\2\u009d"+
		"\u009e\3\2\2\2\u009e\17\3\2\2\2\u009f\u00a0\7\7\2\2\u00a0\u00a1\7\36\2"+
		"\2\u00a1\u00a7\5\36\20\2\u00a2\u00a3\5J&\2\u00a3\u00a4\5\36\20\2\u00a4"+
		"\u00a6\3\2\2\2\u00a5\u00a2\3\2\2\2\u00a6\u00a9\3\2\2\2\u00a7\u00a5\3\2"+
		"\2\2\u00a7\u00a8\3\2\2\2\u00a8\u00aa\3\2\2\2\u00a9\u00a7\3\2\2\2\u00aa"+
		"\u00ab\7\37\2\2\u00ab\u00ac\7\5\2\2\u00ac\u00ad\5\26\f\2\u00ad\u00ae\7"+
		"\6\2\2\u00ae\21\3\2\2\2\u00af\u00b0\7\b\2\2\u00b0\u00b1\7\7\2\2\u00b1"+
		"\u00b2\7\36\2\2\u00b2\u00b8\5\36\20\2\u00b3\u00b4\5J&\2\u00b4\u00b5\5"+
		"\36\20\2\u00b5\u00b7\3\2\2\2\u00b6\u00b3\3\2\2\2\u00b7\u00ba\3\2\2\2\u00b8"+
		"\u00b6\3\2\2\2\u00b8\u00b9\3\2\2\2\u00b9\u00bb\3\2\2\2\u00ba\u00b8\3\2"+
		"\2\2\u00bb\u00bc\7\37\2\2\u00bc\u00bd\7\5\2\2\u00bd\u00be\5\26\f\2\u00be"+
		"\u00bf\7\6\2\2\u00bf\23\3\2\2\2\u00c0\u00c1\7\b\2\2\u00c1\u00c2\7\5\2"+
		"\2\u00c2\u00c3\5\26\f\2\u00c3\u00c4\7\6\2\2\u00c4\25\3\2\2\2\u00c5\u00c7"+
		"\5\30\r\2\u00c6\u00c5\3\2\2\2\u00c7\u00ca\3\2\2\2\u00c8\u00c6\3\2\2\2"+
		"\u00c8\u00c9\3\2\2\2\u00c9\u00cc\3\2\2\2\u00ca\u00c8\3\2\2\2\u00cb\u00cd"+
		"\5\32\16\2\u00cc\u00cb\3\2\2\2\u00cc\u00cd\3\2\2\2\u00cd\27\3\2\2\2\u00ce"+
		"\u00d2\5 \21\2\u00cf\u00d2\5\16\b\2\u00d0\u00d2\5\n\6\2\u00d1\u00ce\3"+
		"\2\2\2\u00d1\u00cf\3\2\2\2\u00d1\u00d0\3\2\2\2\u00d2\31\3\2\2\2\u00d3"+
		"\u00d5\7\t\2\2\u00d4\u00d3\3\2\2\2\u00d4\u00d5\3\2\2\2\u00d5\u00d6\3\2"+
		"\2\2\u00d6\u00d8\5\34\17\2\u00d7\u00d9\7\n\2\2\u00d8\u00d7\3\2\2\2\u00d8"+
		"\u00d9\3\2\2\2\u00d9\33\3\2\2\2\u00da\u00df\5\"\22\2\u00db\u00dc\7\27"+
		"\2\2\u00dc\u00de\5\"\22\2\u00dd\u00db\3\2\2\2\u00de\u00e1\3\2\2\2\u00df"+
		"\u00dd\3\2\2\2\u00df\u00e0\3\2\2\2\u00e0\35\3\2\2\2\u00e1\u00df\3\2\2"+
		"\2\u00e2\u00e3\5\34\17\2\u00e3\u00e4\7\30\2\2\u00e4\u00e5\5\34\17\2\u00e5"+
		"\37\3\2\2\2\u00e6\u00e8\7\13\2\2\u00e7\u00e6\3\2\2\2\u00e7\u00e8\3\2\2"+
		"\2\u00e8\u00e9\3\2\2\2\u00e9\u00ea\5&\24\2\u00ea\u00eb\7\f\2\2\u00eb\u00ed"+
		"\5\"\22\2\u00ec\u00ee\7\n\2\2\u00ed\u00ec\3\2\2\2\u00ed\u00ee\3\2\2\2"+
		"\u00ee!\3\2\2\2\u00ef\u00f4\5$\23\2\u00f0\u00f1\7\27\2\2\u00f1\u00f3\5"+
		"$\23\2\u00f2\u00f0\3\2\2\2\u00f3\u00f6\3\2\2\2\u00f4\u00f2\3\2\2\2\u00f4"+
		"\u00f5\3\2\2\2\u00f5\u0106\3\2\2\2\u00f6\u00f4\3\2\2\2\u00f7\u00f8\7\36"+
		"\2\2\u00f8\u00f9\5\"\22\2\u00f9\u00fa\7\37\2\2\u00fa\u0106\3\2\2\2\u00fb"+
		"\u00fc\7\36\2\2\u00fc\u00ff\5\"\22\2\u00fd\u00fe\7\27\2\2\u00fe\u0100"+
		"\5\"\22\2\u00ff\u00fd\3\2\2\2\u0100\u0101\3\2\2\2\u0101\u00ff\3\2\2\2"+
		"\u0101\u0102\3\2\2\2\u0102\u0103\3\2\2\2\u0103\u0104\7\37\2\2\u0104\u0106"+
		"\3\2\2\2\u0105\u00ef\3\2\2\2\u0105\u00f7\3\2\2\2\u0105\u00fb\3\2\2\2\u0106"+
		"#\3\2\2\2\u0107\u0116\5\62\32\2\u0108\u0116\5\64\33\2\u0109\u0116\58\35"+
		"\2\u010a\u0116\5(\25\2\u010b\u0116\5*\26\2\u010c\u0116\5,\27\2\u010d\u0116"+
		"\5.\30\2\u010e\u0116\5\60\31\2\u010f\u0116\5&\24\2\u0110\u0116\7#\2\2"+
		"\u0111\u0116\7\32\2\2\u0112\u0116\7 \2\2\u0113\u0116\7$\2\2\u0114\u0116"+
		"\7\35\2\2\u0115\u0107\3\2\2\2\u0115\u0108\3\2\2\2\u0115\u0109\3\2\2\2"+
		"\u0115\u010a\3\2\2\2\u0115\u010b\3\2\2\2\u0115\u010c\3\2\2\2\u0115\u010d"+
		"\3\2\2\2\u0115\u010e\3\2\2\2\u0115\u010f\3\2\2\2\u0115\u0110\3\2\2\2\u0115"+
		"\u0111\3\2\2\2\u0115\u0112\3\2\2\2\u0115\u0113\3\2\2\2\u0115\u0114\3\2"+
		"\2\2\u0116%\3\2\2\2\u0117\u0118\7\'\2\2\u0118\'\3\2\2\2\u0119\u011a\7"+
		"\r\2\2\u011a\u011b\7\26\2\2\u011b)\3\2\2\2\u011c\u011d\7\16\2\2\u011d"+
		"\u011e\7\26\2\2\u011e+\3\2\2\2\u011f\u0120\7\17\2\2\u0120-\3\2\2\2\u0121"+
		"\u0122\7\17\2\2\u0122\u0123\7\20\2\2\u0123\u0124\5D#\2\u0124/\3\2\2\2"+
		"\u0125\u0128\7\21\2\2\u0126\u0127\7\20\2\2\u0127\u0129\5D#\2\u0128\u0126"+
		"\3\2\2\2\u0128\u0129\3\2\2\2\u0129\61\3\2\2\2\u012a\u012b\7\'\2\2\u012b"+
		"\u012c\7\20\2\2\u012c\u012d\5L\'\2\u012d\u012f\7\36\2\2\u012e\u0130\5"+
		"D#\2\u012f\u012e\3\2\2\2\u012f\u0130\3\2\2\2\u0130\u0133\3\2\2\2\u0131"+
		"\u0132\7\34\2\2\u0132\u0134\5@!\2\u0133\u0131\3\2\2\2\u0133\u0134\3\2"+
		"\2\2\u0134\u0137\3\2\2\2\u0135\u0136\7\34\2\2\u0136\u0138\7\31\2\2\u0137"+
		"\u0135\3\2\2\2\u0137\u0138\3\2\2\2\u0138\u0139\3\2\2\2\u0139\u013a\7\37"+
		"\2\2\u013a\63\3\2\2\2\u013b\u013c\7\'\2\2\u013c\u013e\7\36\2\2\u013d\u013f"+
		"\5\66\34\2\u013e\u013d\3\2\2\2\u013e\u013f\3\2\2\2\u013f\u0140\3\2\2\2"+
		"\u0140\u0141\7\37\2\2\u0141\65\3\2\2\2\u0142\u0149\5\"\22\2\u0143\u0145"+
		"\7\34\2\2\u0144\u0143\3\2\2\2\u0144\u0145\3\2\2\2\u0145\u0146\3\2\2\2"+
		"\u0146\u0148\5\"\22\2\u0147\u0144\3\2\2\2\u0148\u014b\3\2\2\2\u0149\u0147"+
		"\3\2\2\2\u0149\u014a\3\2\2\2\u014a\67\3\2\2\2\u014b\u0149\3\2\2\2\u014c"+
		"\u014d\b\35\1\2\u014d\u016e\5H%\2\u014e\u016e\7\26\2\2\u014f\u0150\7\26"+
		"\2\2\u0150\u0151\7\22\2\2\u0151\u0156\7\23\2\2\u0152\u0153\7\5\2\2\u0153"+
		"\u0154\5@!\2\u0154\u0155\7\6\2\2\u0155\u0157\3\2\2\2\u0156\u0152\3\2\2"+
		"\2\u0156\u0157\3\2\2\2\u0157\u016e\3\2\2\2\u0158\u0159\7\26\2\2\u0159"+
		"\u015a\7\33\2\2\u015a\u016e\7\26\2\2\u015b\u015c\7\26\2\2\u015c\u015d"+
		"\7\5\2\2\u015d\u015e\5@!\2\u015e\u015f\7\6\2\2\u015f\u016e\3\2\2\2\u0160"+
		"\u0161\7\26\2\2\u0161\u0162\7\22\2\2\u0162\u0163\5:\36\2\u0163\u0164\7"+
		"\23\2\2\u0164\u016e\3\2\2\2\u0165\u0166\7\26\2\2\u0166\u0167\7\22\2\2"+
		"\u0167\u0168\5:\36\2\u0168\u0169\7\23\2\2\u0169\u016a\7\5\2\2\u016a\u016b"+
		"\5@!\2\u016b\u016c\7\6\2\2\u016c\u016e\3\2\2\2\u016d\u014c\3\2\2\2\u016d"+
		"\u014e\3\2\2\2\u016d\u014f\3\2\2\2\u016d\u0158\3\2\2\2\u016d\u015b\3\2"+
		"\2\2\u016d\u0160\3\2\2\2\u016d\u0165\3\2\2\2\u016e\u0174\3\2\2\2\u016f"+
		"\u0170\f\3\2\2\u0170\u0171\7\24\2\2\u0171\u0173\58\35\4\u0172\u016f\3"+
		"\2\2\2\u0173\u0176\3\2\2\2\u0174\u0172\3\2\2\2\u0174\u0175\3\2\2\2\u0175"+
		"9\3\2\2\2\u0176\u0174\3\2\2\2\u0177\u017a\5<\37\2\u0178\u0179\7\n\2\2"+
		"\u0179\u017b\5<\37\2\u017a\u0178\3\2\2\2\u017a\u017b\3\2\2\2\u017b;\3"+
		"\2\2\2\u017c\u0181\5> \2\u017d\u017e\7\34\2\2\u017e\u0180\5> \2\u017f"+
		"\u017d\3\2\2\2\u0180\u0183\3\2\2\2\u0181\u017f\3\2\2\2\u0181\u0182\3\2"+
		"\2\2\u0182=\3\2\2\2\u0183\u0181\3\2\2\2\u0184\u018c\7\26\2\2\u0185\u0186"+
		"\7\26\2\2\u0186\u0188\7\33\2\2\u0187\u0189\7%\2\2\u0188\u0187\3\2\2\2"+
		"\u0188\u0189\3\2\2\2\u0189\u018a\3\2\2\2\u018a\u018c\7#\2\2\u018b\u0184"+
		"\3\2\2\2\u018b\u0185\3\2\2\2\u018c?\3\2\2\2\u018d\u0193\5B\"\2\u018e\u018f"+
		"\5J&\2\u018f\u0190\5B\"\2\u0190\u0192\3\2\2\2\u0191\u018e\3\2\2\2\u0192"+
		"\u0195\3\2\2\2\u0193\u0191\3\2\2\2\u0193\u0194\3\2\2\2\u0194A\3\2\2\2"+
		"\u0195\u0193\3\2\2\2\u0196\u0197\7\26\2\2\u0197\u0198\7\30\2\2\u0198\u01a6"+
		"\5\34\17\2\u0199\u019a\5D#\2\u019a\u019b\7\30\2\2\u019b\u019c\5\34\17"+
		"\2\u019c\u01a6\3\2\2\2\u019d\u019e\5F$\2\u019e\u019f\7\30\2\2\u019f\u01a0"+
		"\5\34\17\2\u01a0\u01a6\3\2\2\2\u01a1\u01a2\5\34\17\2\u01a2\u01a3\7\30"+
		"\2\2\u01a3\u01a4\5\34\17\2\u01a4\u01a6\3\2\2\2\u01a5\u0196\3\2\2\2\u01a5"+
		"\u0199\3\2\2\2\u01a5\u019d\3\2\2\2\u01a5\u01a1\3\2\2\2\u01a6C\3\2\2\2"+
		"\u01a7\u01a8\b#\1\2\u01a8\u01a9\7\'\2\2\u01a9\u01af\3\2\2\2\u01aa\u01ab"+
		"\f\3\2\2\u01ab\u01ac\7\20\2\2\u01ac\u01ae\5D#\4\u01ad\u01aa\3\2\2\2\u01ae"+
		"\u01b1\3\2\2\2\u01af\u01ad\3\2\2\2\u01af\u01b0\3\2\2\2\u01b0E\3\2\2\2"+
		"\u01b1\u01af\3\2\2\2\u01b2\u01b3\7\25\2\2\u01b3G\3\2\2\2\u01b4\u01b5\t"+
		"\2\2\2\u01b5I\3\2\2\2\u01b6\u01b7\t\3\2\2\u01b7K\3\2\2\2\u01b8\u01b9\7"+
		"\'\2\2\u01b9M\3\2\2\2+QZcjs\u0082\u008d\u0091\u0099\u009d\u00a7\u00b8"+
		"\u00c8\u00cc\u00d1\u00d4\u00d8\u00df\u00e7\u00ed\u00f4\u0101\u0105\u0115"+
		"\u0128\u012f\u0133\u0137\u013e\u0144\u0149\u0156\u016d\u0174\u017a\u0181"+
		"\u0188\u018b\u0193\u01a5\u01af";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}