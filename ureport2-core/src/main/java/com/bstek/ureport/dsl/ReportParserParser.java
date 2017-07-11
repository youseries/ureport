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
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, Cell=17, 
		Operator=18, OP=19, ORDER=20, BOOLEAN=21, COLON=22, COMMA=23, NULL=24, 
		LeftParen=25, RightParen=26, STRING=27, AND=28, OR=29, INTEGER=30, NUMBER=31, 
		EXCLAMATION=32, EXP=33, LETTER=34, Identifier=35, Char=36, DIGIT=37, WS=38, 
		NL=39;
	public static final int
		RULE_expression = 0, RULE_exprComposite = 1, RULE_ternaryExpr = 2, RULE_caseExpr = 3, 
		RULE_casePart = 4, RULE_ifExpr = 5, RULE_ifPart = 6, RULE_elseIfPart = 7, 
		RULE_elsePart = 8, RULE_expr = 9, RULE_ifCondition = 10, RULE_item = 11, 
		RULE_unit = 12, RULE_cellPosition = 13, RULE_relativeCell = 14, RULE_currentCellValue = 15, 
		RULE_cell = 16, RULE_dataset = 17, RULE_function = 18, RULE_functionParameter = 19, 
		RULE_set = 20, RULE_cellCoordinate = 21, RULE_coordinate = 22, RULE_cellIndicator = 23, 
		RULE_conditions = 24, RULE_condition = 25, RULE_property = 26, RULE_simpleValue = 27, 
		RULE_join = 28, RULE_aggregate = 29;
	public static final String[] ruleNames = {
		"expression", "exprComposite", "ternaryExpr", "caseExpr", "casePart", 
		"ifExpr", "ifPart", "elseIfPart", "elsePart", "expr", "ifCondition", "item", 
		"unit", "cellPosition", "relativeCell", "currentCellValue", "cell", "dataset", 
		"function", "functionParameter", "set", "cellCoordinate", "coordinate", 
		"cellIndicator", "conditions", "condition", "property", "simpleValue", 
		"join", "aggregate"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'?'", "'case'", "'{'", "'}'", "'return'", "'if'", "';'", "'else'", 
		"'&'", "'$'", "'#'", "'cell'", "'.'", "'['", "']'", "'to'", null, null, 
		null, null, null, "':'", "','", "'null'", "'('", "')'", null, null, null, 
		null, null, "'!'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, "Cell", "Operator", "OP", "ORDER", "BOOLEAN", 
		"COLON", "COMMA", "NULL", "LeftParen", "RightParen", "STRING", "AND", 
		"OR", "INTEGER", "NUMBER", "EXCLAMATION", "EXP", "LETTER", "Identifier", 
		"Char", "DIGIT", "WS", "NL"
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
		enterRule(_localctx, 0, RULE_expression);
		try {
			setState(63);
			switch (_input.LA(1)) {
			case T__8:
			case T__9:
			case T__10:
			case T__11:
			case Cell:
			case BOOLEAN:
			case NULL:
			case LeftParen:
			case STRING:
			case INTEGER:
			case NUMBER:
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(60);
				exprComposite(0);
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 2);
				{
				setState(61);
				ifExpr();
				}
				break;
			case T__1:
				enterOuterAlt(_localctx, 3);
				{
				setState(62);
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
		int _startState = 2;
		enterRecursionRule(_localctx, 2, RULE_exprComposite, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				{
				_localctx = new SingleExprCompositeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(66);
				expr();
				}
				break;
			case 2:
				{
				_localctx = new TernaryExprCompositeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(67);
				ternaryExpr();
				}
				break;
			case 3:
				{
				_localctx = new ParenExprCompositeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(68);
				match(LeftParen);
				setState(69);
				exprComposite(0);
				setState(70);
				match(RightParen);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(79);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ComplexExprCompositeContext(new ExprCompositeContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_exprComposite);
					setState(74);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(75);
					match(Operator);
					setState(76);
					exprComposite(2);
					}
					} 
				}
				setState(81);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
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
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
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
		enterRule(_localctx, 4, RULE_ternaryExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82);
			ifCondition();
			setState(88);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND || _la==OR) {
				{
				{
				setState(83);
				join();
				setState(84);
				ifCondition();
				}
				}
				setState(90);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(91);
			match(T__0);
			setState(92);
			expr();
			setState(93);
			match(COLON);
			setState(94);
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
		enterRule(_localctx, 6, RULE_caseExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(96);
			match(T__1);
			setState(97);
			match(T__2);
			setState(98);
			casePart();
			setState(103);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(99);
				match(COMMA);
				setState(100);
				casePart();
				}
				}
				setState(105);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(106);
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
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
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
		enterRule(_localctx, 8, RULE_casePart);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(108);
			ifCondition();
			setState(114);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND || _la==OR) {
				{
				{
				setState(109);
				join();
				setState(110);
				ifCondition();
				}
				}
				setState(116);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(118);
			_la = _input.LA(1);
			if (_la==COLON) {
				{
				setState(117);
				match(COLON);
				}
			}

			setState(121);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(120);
				match(T__4);
				}
			}

			setState(123);
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
		enterRule(_localctx, 10, RULE_ifExpr);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(125);
			ifPart();
			setState(129);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(126);
					elseIfPart();
					}
					} 
				}
				setState(131);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			}
			setState(133);
			_la = _input.LA(1);
			if (_la==T__7) {
				{
				setState(132);
				elsePart();
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

	public static class IfPartContext extends ParserRuleContext {
		public List<IfConditionContext> ifCondition() {
			return getRuleContexts(IfConditionContext.class);
		}
		public IfConditionContext ifCondition(int i) {
			return getRuleContext(IfConditionContext.class,i);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
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
		enterRule(_localctx, 12, RULE_ifPart);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(135);
			match(T__5);
			setState(136);
			match(LeftParen);
			setState(137);
			ifCondition();
			setState(143);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND || _la==OR) {
				{
				{
				setState(138);
				join();
				setState(139);
				ifCondition();
				}
				}
				setState(145);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(146);
			match(RightParen);
			setState(147);
			match(T__2);
			setState(149);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(148);
				match(T__4);
				}
			}

			setState(151);
			expr();
			setState(153);
			_la = _input.LA(1);
			if (_la==T__6) {
				{
				setState(152);
				match(T__6);
				}
			}

			setState(155);
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
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
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
		enterRule(_localctx, 14, RULE_elseIfPart);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(157);
			match(T__7);
			setState(158);
			match(T__5);
			setState(159);
			match(LeftParen);
			setState(160);
			ifCondition();
			setState(166);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND || _la==OR) {
				{
				{
				setState(161);
				join();
				setState(162);
				ifCondition();
				}
				}
				setState(168);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(169);
			match(RightParen);
			setState(170);
			match(T__2);
			setState(172);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(171);
				match(T__4);
				}
			}

			setState(174);
			expr();
			setState(176);
			_la = _input.LA(1);
			if (_la==T__6) {
				{
				setState(175);
				match(T__6);
				}
			}

			setState(178);
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
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
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
		enterRule(_localctx, 16, RULE_elsePart);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(180);
			match(T__7);
			setState(181);
			match(T__2);
			setState(183);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(182);
				match(T__4);
				}
			}

			setState(185);
			expr();
			setState(187);
			_la = _input.LA(1);
			if (_la==T__6) {
				{
				setState(186);
				match(T__6);
				}
			}

			setState(189);
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
		enterRule(_localctx, 18, RULE_expr);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(191);
			item();
			setState(196);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(192);
					match(Operator);
					setState(193);
					item();
					}
					} 
				}
				setState(198);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
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
		enterRule(_localctx, 20, RULE_ifCondition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(199);
			expr();
			setState(200);
			match(OP);
			setState(201);
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
		enterRule(_localctx, 22, RULE_item);
		int _la;
		try {
			int _alt;
			setState(225);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				_localctx = new SimpleJoinContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(203);
				unit();
				setState(208);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(204);
						match(Operator);
						setState(205);
						unit();
						}
						} 
					}
					setState(210);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
				}
				}
				break;
			case 2:
				_localctx = new SingleParenJoinContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(211);
				match(LeftParen);
				setState(212);
				item();
				setState(213);
				match(RightParen);
				}
				break;
			case 3:
				_localctx = new ParenJoinContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(215);
				match(LeftParen);
				setState(216);
				item();
				setState(219); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(217);
					match(Operator);
					setState(218);
					item();
					}
					}
					setState(221); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==Operator );
				setState(223);
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
		public CellContext cell() {
			return getRuleContext(CellContext.class,0);
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
		enterRule(_localctx, 24, RULE_unit);
		try {
			setState(239);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(227);
				dataset();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(228);
				function();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(229);
				set(0);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(230);
				cellPosition();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(231);
				relativeCell();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(232);
				currentCellValue();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(233);
				cell();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(234);
				match(INTEGER);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(235);
				match(BOOLEAN);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(236);
				match(STRING);
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(237);
				match(NUMBER);
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(238);
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
		enterRule(_localctx, 26, RULE_cellPosition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(241);
			match(T__8);
			setState(242);
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
		enterRule(_localctx, 28, RULE_relativeCell);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(244);
			match(T__9);
			setState(245);
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
		enterRule(_localctx, 30, RULE_currentCellValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(247);
			match(T__10);
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
		enterRule(_localctx, 32, RULE_cell);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(249);
			match(T__11);
			setState(252);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				{
				setState(250);
				match(T__12);
				setState(251);
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
		enterRule(_localctx, 34, RULE_dataset);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(254);
			match(Identifier);
			setState(255);
			match(T__12);
			setState(256);
			aggregate();
			setState(257);
			match(LeftParen);
			setState(259);
			_la = _input.LA(1);
			if (_la==Identifier) {
				{
				setState(258);
				property(0);
				}
			}

			setState(263);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				{
				setState(261);
				match(COMMA);
				setState(262);
				conditions();
				}
				break;
			}
			setState(267);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(265);
				match(COMMA);
				setState(266);
				match(ORDER);
				}
			}

			setState(269);
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
		enterRule(_localctx, 36, RULE_function);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(271);
			match(Identifier);
			setState(272);
			match(LeftParen);
			setState(274);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Cell) | (1L << BOOLEAN) | (1L << NULL) | (1L << STRING) | (1L << INTEGER) | (1L << NUMBER))) != 0)) {
				{
				setState(273);
				functionParameter();
				}
			}

			setState(276);
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
		public List<SetContext> set() {
			return getRuleContexts(SetContext.class);
		}
		public SetContext set(int i) {
			return getRuleContext(SetContext.class,i);
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
		enterRule(_localctx, 38, RULE_functionParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(278);
			set(0);
			setState(285);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Cell) | (1L << BOOLEAN) | (1L << COMMA) | (1L << NULL) | (1L << STRING) | (1L << INTEGER) | (1L << NUMBER))) != 0)) {
				{
				{
				setState(280);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(279);
					match(COMMA);
					}
				}

				setState(282);
				set(0);
				}
				}
				setState(287);
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
		int _startState = 40;
		enterRecursionRule(_localctx, 40, RULE_set, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(321);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
			case 1:
				{
				_localctx = new SimpleDataContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(289);
				simpleValue();
				}
				break;
			case 2:
				{
				_localctx = new SingleCellContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(290);
				match(Cell);
				}
				break;
			case 3:
				{
				_localctx = new WholeCellContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(291);
				match(Cell);
				setState(292);
				match(T__13);
				setState(293);
				match(T__14);
				setState(298);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
				case 1:
					{
					setState(294);
					match(T__2);
					setState(295);
					conditions();
					setState(296);
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
				setState(300);
				match(Cell);
				setState(301);
				match(COLON);
				setState(302);
				match(Cell);
				}
				break;
			case 5:
				{
				_localctx = new SingleCellConditionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(303);
				match(Cell);
				setState(304);
				match(T__2);
				setState(305);
				conditions();
				setState(306);
				match(T__3);
				}
				break;
			case 6:
				{
				_localctx = new SingleCellCoordinateContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(308);
				match(Cell);
				setState(309);
				match(T__13);
				setState(310);
				cellCoordinate();
				setState(311);
				match(T__14);
				}
				break;
			case 7:
				{
				_localctx = new CellCoordinateConditionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(313);
				match(Cell);
				setState(314);
				match(T__13);
				setState(315);
				cellCoordinate();
				setState(316);
				match(T__14);
				setState(317);
				match(T__2);
				setState(318);
				conditions();
				setState(319);
				match(T__3);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(328);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new RangeContext(new SetContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_set);
					setState(323);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(324);
					match(T__15);
					setState(325);
					set(2);
					}
					} 
				}
				setState(330);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
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
		enterRule(_localctx, 42, RULE_cellCoordinate);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(331);
			coordinate();
			setState(334);
			_la = _input.LA(1);
			if (_la==T__6) {
				{
				setState(332);
				match(T__6);
				setState(333);
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
		enterRule(_localctx, 44, RULE_coordinate);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(336);
			cellIndicator();
			setState(341);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(337);
				match(COMMA);
				setState(338);
				cellIndicator();
				}
				}
				setState(343);
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
		enterRule(_localctx, 46, RULE_cellIndicator);
		int _la;
		try {
			setState(351);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
			case 1:
				_localctx = new RelativeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(344);
				match(Cell);
				}
				break;
			case 2:
				_localctx = new AbsoluteContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(345);
				match(Cell);
				setState(346);
				match(COLON);
				setState(348);
				_la = _input.LA(1);
				if (_la==EXCLAMATION) {
					{
					setState(347);
					match(EXCLAMATION);
					}
				}

				setState(350);
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
		enterRule(_localctx, 48, RULE_conditions);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(353);
			condition();
			setState(359);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND || _la==OR) {
				{
				{
				setState(354);
				join();
				setState(355);
				condition();
				}
				}
				setState(361);
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
		enterRule(_localctx, 50, RULE_condition);
		try {
			setState(373);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
			case 1:
				_localctx = new CellNameExprConditionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(362);
				match(Cell);
				setState(363);
				match(OP);
				setState(364);
				expr();
				}
				break;
			case 2:
				_localctx = new PropertyConditionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(365);
				property(0);
				setState(366);
				match(OP);
				setState(367);
				expr();
				}
				break;
			case 3:
				_localctx = new ExprConditionContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(369);
				expr();
				setState(370);
				match(OP);
				setState(371);
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
		int _startState = 52;
		enterRecursionRule(_localctx, 52, RULE_property, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(376);
			match(Identifier);
			}
			_ctx.stop = _input.LT(-1);
			setState(383);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,39,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new PropertyContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_property);
					setState(378);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(379);
					match(T__12);
					setState(380);
					property(2);
					}
					} 
				}
				setState(385);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,39,_ctx);
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
		enterRule(_localctx, 54, RULE_simpleValue);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(386);
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
		enterRule(_localctx, 56, RULE_join);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(388);
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
		enterRule(_localctx, 58, RULE_aggregate);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(390);
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
		case 1:
			return exprComposite_sempred((ExprCompositeContext)_localctx, predIndex);
		case 20:
			return set_sempred((SetContext)_localctx, predIndex);
		case 26:
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3)\u018b\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\3\2\3\2\3"+
		"\2\5\2B\n\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3K\n\3\3\3\3\3\3\3\7\3P\n\3"+
		"\f\3\16\3S\13\3\3\4\3\4\3\4\3\4\7\4Y\n\4\f\4\16\4\\\13\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\5\3\5\3\5\3\5\3\5\7\5h\n\5\f\5\16\5k\13\5\3\5\3\5\3\6\3\6\3\6"+
		"\3\6\7\6s\n\6\f\6\16\6v\13\6\3\6\5\6y\n\6\3\6\5\6|\n\6\3\6\3\6\3\7\3\7"+
		"\7\7\u0082\n\7\f\7\16\7\u0085\13\7\3\7\5\7\u0088\n\7\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\7\b\u0090\n\b\f\b\16\b\u0093\13\b\3\b\3\b\3\b\5\b\u0098\n\b\3\b"+
		"\3\b\5\b\u009c\n\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\7\t\u00a7\n\t\f"+
		"\t\16\t\u00aa\13\t\3\t\3\t\3\t\5\t\u00af\n\t\3\t\3\t\5\t\u00b3\n\t\3\t"+
		"\3\t\3\n\3\n\3\n\5\n\u00ba\n\n\3\n\3\n\5\n\u00be\n\n\3\n\3\n\3\13\3\13"+
		"\3\13\7\13\u00c5\n\13\f\13\16\13\u00c8\13\13\3\f\3\f\3\f\3\f\3\r\3\r\3"+
		"\r\7\r\u00d1\n\r\f\r\16\r\u00d4\13\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\6"+
		"\r\u00de\n\r\r\r\16\r\u00df\3\r\3\r\5\r\u00e4\n\r\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u00f2\n\16\3\17\3\17\3\17"+
		"\3\20\3\20\3\20\3\21\3\21\3\22\3\22\3\22\5\22\u00ff\n\22\3\23\3\23\3\23"+
		"\3\23\3\23\5\23\u0106\n\23\3\23\3\23\5\23\u010a\n\23\3\23\3\23\5\23\u010e"+
		"\n\23\3\23\3\23\3\24\3\24\3\24\5\24\u0115\n\24\3\24\3\24\3\25\3\25\5\25"+
		"\u011b\n\25\3\25\7\25\u011e\n\25\f\25\16\25\u0121\13\25\3\26\3\26\3\26"+
		"\3\26\3\26\3\26\3\26\3\26\3\26\3\26\5\26\u012d\n\26\3\26\3\26\3\26\3\26"+
		"\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26"+
		"\3\26\3\26\3\26\5\26\u0144\n\26\3\26\3\26\3\26\7\26\u0149\n\26\f\26\16"+
		"\26\u014c\13\26\3\27\3\27\3\27\5\27\u0151\n\27\3\30\3\30\3\30\7\30\u0156"+
		"\n\30\f\30\16\30\u0159\13\30\3\31\3\31\3\31\3\31\5\31\u015f\n\31\3\31"+
		"\5\31\u0162\n\31\3\32\3\32\3\32\3\32\7\32\u0168\n\32\f\32\16\32\u016b"+
		"\13\32\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\5\33\u0178"+
		"\n\33\3\34\3\34\3\34\3\34\3\34\3\34\7\34\u0180\n\34\f\34\16\34\u0183\13"+
		"\34\3\35\3\35\3\36\3\36\3\37\3\37\3\37\2\5\4*\66 \2\4\6\b\n\f\16\20\22"+
		"\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<\2\4\6\2\27\27\32\32\35\35 "+
		"!\3\2\36\37\u01a7\2A\3\2\2\2\4J\3\2\2\2\6T\3\2\2\2\bb\3\2\2\2\nn\3\2\2"+
		"\2\f\177\3\2\2\2\16\u0089\3\2\2\2\20\u009f\3\2\2\2\22\u00b6\3\2\2\2\24"+
		"\u00c1\3\2\2\2\26\u00c9\3\2\2\2\30\u00e3\3\2\2\2\32\u00f1\3\2\2\2\34\u00f3"+
		"\3\2\2\2\36\u00f6\3\2\2\2 \u00f9\3\2\2\2\"\u00fb\3\2\2\2$\u0100\3\2\2"+
		"\2&\u0111\3\2\2\2(\u0118\3\2\2\2*\u0143\3\2\2\2,\u014d\3\2\2\2.\u0152"+
		"\3\2\2\2\60\u0161\3\2\2\2\62\u0163\3\2\2\2\64\u0177\3\2\2\2\66\u0179\3"+
		"\2\2\28\u0184\3\2\2\2:\u0186\3\2\2\2<\u0188\3\2\2\2>B\5\4\3\2?B\5\f\7"+
		"\2@B\5\b\5\2A>\3\2\2\2A?\3\2\2\2A@\3\2\2\2B\3\3\2\2\2CD\b\3\1\2DK\5\24"+
		"\13\2EK\5\6\4\2FG\7\33\2\2GH\5\4\3\2HI\7\34\2\2IK\3\2\2\2JC\3\2\2\2JE"+
		"\3\2\2\2JF\3\2\2\2KQ\3\2\2\2LM\f\3\2\2MN\7\24\2\2NP\5\4\3\4OL\3\2\2\2"+
		"PS\3\2\2\2QO\3\2\2\2QR\3\2\2\2R\5\3\2\2\2SQ\3\2\2\2TZ\5\26\f\2UV\5:\36"+
		"\2VW\5\26\f\2WY\3\2\2\2XU\3\2\2\2Y\\\3\2\2\2ZX\3\2\2\2Z[\3\2\2\2[]\3\2"+
		"\2\2\\Z\3\2\2\2]^\7\3\2\2^_\5\24\13\2_`\7\30\2\2`a\5\24\13\2a\7\3\2\2"+
		"\2bc\7\4\2\2cd\7\5\2\2di\5\n\6\2ef\7\31\2\2fh\5\n\6\2ge\3\2\2\2hk\3\2"+
		"\2\2ig\3\2\2\2ij\3\2\2\2jl\3\2\2\2ki\3\2\2\2lm\7\6\2\2m\t\3\2\2\2nt\5"+
		"\26\f\2op\5:\36\2pq\5\26\f\2qs\3\2\2\2ro\3\2\2\2sv\3\2\2\2tr\3\2\2\2t"+
		"u\3\2\2\2ux\3\2\2\2vt\3\2\2\2wy\7\30\2\2xw\3\2\2\2xy\3\2\2\2y{\3\2\2\2"+
		"z|\7\7\2\2{z\3\2\2\2{|\3\2\2\2|}\3\2\2\2}~\5\24\13\2~\13\3\2\2\2\177\u0083"+
		"\5\16\b\2\u0080\u0082\5\20\t\2\u0081\u0080\3\2\2\2\u0082\u0085\3\2\2\2"+
		"\u0083\u0081\3\2\2\2\u0083\u0084\3\2\2\2\u0084\u0087\3\2\2\2\u0085\u0083"+
		"\3\2\2\2\u0086\u0088\5\22\n\2\u0087\u0086\3\2\2\2\u0087\u0088\3\2\2\2"+
		"\u0088\r\3\2\2\2\u0089\u008a\7\b\2\2\u008a\u008b\7\33\2\2\u008b\u0091"+
		"\5\26\f\2\u008c\u008d\5:\36\2\u008d\u008e\5\26\f\2\u008e\u0090\3\2\2\2"+
		"\u008f\u008c\3\2\2\2\u0090\u0093\3\2\2\2\u0091\u008f\3\2\2\2\u0091\u0092"+
		"\3\2\2\2\u0092\u0094\3\2\2\2\u0093\u0091\3\2\2\2\u0094\u0095\7\34\2\2"+
		"\u0095\u0097\7\5\2\2\u0096\u0098\7\7\2\2\u0097\u0096\3\2\2\2\u0097\u0098"+
		"\3\2\2\2\u0098\u0099\3\2\2\2\u0099\u009b\5\24\13\2\u009a\u009c\7\t\2\2"+
		"\u009b\u009a\3\2\2\2\u009b\u009c\3\2\2\2\u009c\u009d\3\2\2\2\u009d\u009e"+
		"\7\6\2\2\u009e\17\3\2\2\2\u009f\u00a0\7\n\2\2\u00a0\u00a1\7\b\2\2\u00a1"+
		"\u00a2\7\33\2\2\u00a2\u00a8\5\26\f\2\u00a3\u00a4\5:\36\2\u00a4\u00a5\5"+
		"\26\f\2\u00a5\u00a7\3\2\2\2\u00a6\u00a3\3\2\2\2\u00a7\u00aa\3\2\2\2\u00a8"+
		"\u00a6\3\2\2\2\u00a8\u00a9\3\2\2\2\u00a9\u00ab\3\2\2\2\u00aa\u00a8\3\2"+
		"\2\2\u00ab\u00ac\7\34\2\2\u00ac\u00ae\7\5\2\2\u00ad\u00af\7\7\2\2\u00ae"+
		"\u00ad\3\2\2\2\u00ae\u00af\3\2\2\2\u00af\u00b0\3\2\2\2\u00b0\u00b2\5\24"+
		"\13\2\u00b1\u00b3\7\t\2\2\u00b2\u00b1\3\2\2\2\u00b2\u00b3\3\2\2\2\u00b3"+
		"\u00b4\3\2\2\2\u00b4\u00b5\7\6\2\2\u00b5\21\3\2\2\2\u00b6\u00b7\7\n\2"+
		"\2\u00b7\u00b9\7\5\2\2\u00b8\u00ba\7\7\2\2\u00b9\u00b8\3\2\2\2\u00b9\u00ba"+
		"\3\2\2\2\u00ba\u00bb\3\2\2\2\u00bb\u00bd\5\24\13\2\u00bc\u00be\7\t\2\2"+
		"\u00bd\u00bc\3\2\2\2\u00bd\u00be\3\2\2\2\u00be\u00bf\3\2\2\2\u00bf\u00c0"+
		"\7\6\2\2\u00c0\23\3\2\2\2\u00c1\u00c6\5\30\r\2\u00c2\u00c3\7\24\2\2\u00c3"+
		"\u00c5\5\30\r\2\u00c4\u00c2\3\2\2\2\u00c5\u00c8\3\2\2\2\u00c6\u00c4\3"+
		"\2\2\2\u00c6\u00c7\3\2\2\2\u00c7\25\3\2\2\2\u00c8\u00c6\3\2\2\2\u00c9"+
		"\u00ca\5\24\13\2\u00ca\u00cb\7\25\2\2\u00cb\u00cc\5\24\13\2\u00cc\27\3"+
		"\2\2\2\u00cd\u00d2\5\32\16\2\u00ce\u00cf\7\24\2\2\u00cf\u00d1\5\32\16"+
		"\2\u00d0\u00ce\3\2\2\2\u00d1\u00d4\3\2\2\2\u00d2\u00d0\3\2\2\2\u00d2\u00d3"+
		"\3\2\2\2\u00d3\u00e4\3\2\2\2\u00d4\u00d2\3\2\2\2\u00d5\u00d6\7\33\2\2"+
		"\u00d6\u00d7\5\30\r\2\u00d7\u00d8\7\34\2\2\u00d8\u00e4\3\2\2\2\u00d9\u00da"+
		"\7\33\2\2\u00da\u00dd\5\30\r\2\u00db\u00dc\7\24\2\2\u00dc\u00de\5\30\r"+
		"\2\u00dd\u00db\3\2\2\2\u00de\u00df\3\2\2\2\u00df\u00dd\3\2\2\2\u00df\u00e0"+
		"\3\2\2\2\u00e0\u00e1\3\2\2\2\u00e1\u00e2\7\34\2\2\u00e2\u00e4\3\2\2\2"+
		"\u00e3\u00cd\3\2\2\2\u00e3\u00d5\3\2\2\2\u00e3\u00d9\3\2\2\2\u00e4\31"+
		"\3\2\2\2\u00e5\u00f2\5$\23\2\u00e6\u00f2\5&\24\2\u00e7\u00f2\5*\26\2\u00e8"+
		"\u00f2\5\34\17\2\u00e9\u00f2\5\36\20\2\u00ea\u00f2\5 \21\2\u00eb\u00f2"+
		"\5\"\22\2\u00ec\u00f2\7 \2\2\u00ed\u00f2\7\27\2\2\u00ee\u00f2\7\35\2\2"+
		"\u00ef\u00f2\7!\2\2\u00f0\u00f2\7\32\2\2\u00f1\u00e5\3\2\2\2\u00f1\u00e6"+
		"\3\2\2\2\u00f1\u00e7\3\2\2\2\u00f1\u00e8\3\2\2\2\u00f1\u00e9\3\2\2\2\u00f1"+
		"\u00ea\3\2\2\2\u00f1\u00eb\3\2\2\2\u00f1\u00ec\3\2\2\2\u00f1\u00ed\3\2"+
		"\2\2\u00f1\u00ee\3\2\2\2\u00f1\u00ef\3\2\2\2\u00f1\u00f0\3\2\2\2\u00f2"+
		"\33\3\2\2\2\u00f3\u00f4\7\13\2\2\u00f4\u00f5\7\23\2\2\u00f5\35\3\2\2\2"+
		"\u00f6\u00f7\7\f\2\2\u00f7\u00f8\7\23\2\2\u00f8\37\3\2\2\2\u00f9\u00fa"+
		"\7\r\2\2\u00fa!\3\2\2\2\u00fb\u00fe\7\16\2\2\u00fc\u00fd\7\17\2\2\u00fd"+
		"\u00ff\5\66\34\2\u00fe\u00fc\3\2\2\2\u00fe\u00ff\3\2\2\2\u00ff#\3\2\2"+
		"\2\u0100\u0101\7%\2\2\u0101\u0102\7\17\2\2\u0102\u0103\5<\37\2\u0103\u0105"+
		"\7\33\2\2\u0104\u0106\5\66\34\2\u0105\u0104\3\2\2\2\u0105\u0106\3\2\2"+
		"\2\u0106\u0109\3\2\2\2\u0107\u0108\7\31\2\2\u0108\u010a\5\62\32\2\u0109"+
		"\u0107\3\2\2\2\u0109\u010a\3\2\2\2\u010a\u010d\3\2\2\2\u010b\u010c\7\31"+
		"\2\2\u010c\u010e\7\26\2\2\u010d\u010b\3\2\2\2\u010d\u010e\3\2\2\2\u010e"+
		"\u010f\3\2\2\2\u010f\u0110\7\34\2\2\u0110%\3\2\2\2\u0111\u0112\7%\2\2"+
		"\u0112\u0114\7\33\2\2\u0113\u0115\5(\25\2\u0114\u0113\3\2\2\2\u0114\u0115"+
		"\3\2\2\2\u0115\u0116\3\2\2\2\u0116\u0117\7\34\2\2\u0117\'\3\2\2\2\u0118"+
		"\u011f\5*\26\2\u0119\u011b\7\31\2\2\u011a\u0119\3\2\2\2\u011a\u011b\3"+
		"\2\2\2\u011b\u011c\3\2\2\2\u011c\u011e\5*\26\2\u011d\u011a\3\2\2\2\u011e"+
		"\u0121\3\2\2\2\u011f\u011d\3\2\2\2\u011f\u0120\3\2\2\2\u0120)\3\2\2\2"+
		"\u0121\u011f\3\2\2\2\u0122\u0123\b\26\1\2\u0123\u0144\58\35\2\u0124\u0144"+
		"\7\23\2\2\u0125\u0126\7\23\2\2\u0126\u0127\7\20\2\2\u0127\u012c\7\21\2"+
		"\2\u0128\u0129\7\5\2\2\u0129\u012a\5\62\32\2\u012a\u012b\7\6\2\2\u012b"+
		"\u012d\3\2\2\2\u012c\u0128\3\2\2\2\u012c\u012d\3\2\2\2\u012d\u0144\3\2"+
		"\2\2\u012e\u012f\7\23\2\2\u012f\u0130\7\30\2\2\u0130\u0144\7\23\2\2\u0131"+
		"\u0132\7\23\2\2\u0132\u0133\7\5\2\2\u0133\u0134\5\62\32\2\u0134\u0135"+
		"\7\6\2\2\u0135\u0144\3\2\2\2\u0136\u0137\7\23\2\2\u0137\u0138\7\20\2\2"+
		"\u0138\u0139\5,\27\2\u0139\u013a\7\21\2\2\u013a\u0144\3\2\2\2\u013b\u013c"+
		"\7\23\2\2\u013c\u013d\7\20\2\2\u013d\u013e\5,\27\2\u013e\u013f\7\21\2"+
		"\2\u013f\u0140\7\5\2\2\u0140\u0141\5\62\32\2\u0141\u0142\7\6\2\2\u0142"+
		"\u0144\3\2\2\2\u0143\u0122\3\2\2\2\u0143\u0124\3\2\2\2\u0143\u0125\3\2"+
		"\2\2\u0143\u012e\3\2\2\2\u0143\u0131\3\2\2\2\u0143\u0136\3\2\2\2\u0143"+
		"\u013b\3\2\2\2\u0144\u014a\3\2\2\2\u0145\u0146\f\3\2\2\u0146\u0147\7\22"+
		"\2\2\u0147\u0149\5*\26\4\u0148\u0145\3\2\2\2\u0149\u014c\3\2\2\2\u014a"+
		"\u0148\3\2\2\2\u014a\u014b\3\2\2\2\u014b+\3\2\2\2\u014c\u014a\3\2\2\2"+
		"\u014d\u0150\5.\30\2\u014e\u014f\7\t\2\2\u014f\u0151\5.\30\2\u0150\u014e"+
		"\3\2\2\2\u0150\u0151\3\2\2\2\u0151-\3\2\2\2\u0152\u0157\5\60\31\2\u0153"+
		"\u0154\7\31\2\2\u0154\u0156\5\60\31\2\u0155\u0153\3\2\2\2\u0156\u0159"+
		"\3\2\2\2\u0157\u0155\3\2\2\2\u0157\u0158\3\2\2\2\u0158/\3\2\2\2\u0159"+
		"\u0157\3\2\2\2\u015a\u0162\7\23\2\2\u015b\u015c\7\23\2\2\u015c\u015e\7"+
		"\30\2\2\u015d\u015f\7\"\2\2\u015e\u015d\3\2\2\2\u015e\u015f\3\2\2\2\u015f"+
		"\u0160\3\2\2\2\u0160\u0162\7 \2\2\u0161\u015a\3\2\2\2\u0161\u015b\3\2"+
		"\2\2\u0162\61\3\2\2\2\u0163\u0169\5\64\33\2\u0164\u0165\5:\36\2\u0165"+
		"\u0166\5\64\33\2\u0166\u0168\3\2\2\2\u0167\u0164\3\2\2\2\u0168\u016b\3"+
		"\2\2\2\u0169\u0167\3\2\2\2\u0169\u016a\3\2\2\2\u016a\63\3\2\2\2\u016b"+
		"\u0169\3\2\2\2\u016c\u016d\7\23\2\2\u016d\u016e\7\25\2\2\u016e\u0178\5"+
		"\24\13\2\u016f\u0170\5\66\34\2\u0170\u0171\7\25\2\2\u0171\u0172\5\24\13"+
		"\2\u0172\u0178\3\2\2\2\u0173\u0174\5\24\13\2\u0174\u0175\7\25\2\2\u0175"+
		"\u0176\5\24\13\2\u0176\u0178\3\2\2\2\u0177\u016c\3\2\2\2\u0177\u016f\3"+
		"\2\2\2\u0177\u0173\3\2\2\2\u0178\65\3\2\2\2\u0179\u017a\b\34\1\2\u017a"+
		"\u017b\7%\2\2\u017b\u0181\3\2\2\2\u017c\u017d\f\3\2\2\u017d\u017e\7\17"+
		"\2\2\u017e\u0180\5\66\34\4\u017f\u017c\3\2\2\2\u0180\u0183\3\2\2\2\u0181"+
		"\u017f\3\2\2\2\u0181\u0182\3\2\2\2\u0182\67\3\2\2\2\u0183\u0181\3\2\2"+
		"\2\u0184\u0185\t\2\2\2\u01859\3\2\2\2\u0186\u0187\t\3\2\2\u0187;\3\2\2"+
		"\2\u0188\u0189\7%\2\2\u0189=\3\2\2\2*AJQZitx{\u0083\u0087\u0091\u0097"+
		"\u009b\u00a8\u00ae\u00b2\u00b9\u00bd\u00c6\u00d2\u00df\u00e3\u00f1\u00fe"+
		"\u0105\u0109\u010d\u0114\u011a\u011f\u012c\u0143\u014a\u0150\u0157\u015e"+
		"\u0161\u0169\u0177\u0181";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}