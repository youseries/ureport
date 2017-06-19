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
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, Cell=16, Operator=17, 
		OP=18, ORDER=19, BOOLEAN=20, COLON=21, COMMA=22, NULL=23, LeftParen=24, 
		RightParen=25, STRING=26, AND=27, OR=28, INTEGER=29, NUMBER=30, EXCLAMATION=31, 
		EXP=32, LETTER=33, Identifier=34, Char=35, DIGIT=36, WS=37, NL=38;
	public static final int
		RULE_expression = 0, RULE_exprComposite = 1, RULE_ternaryExpr = 2, RULE_caseExpr = 3, 
		RULE_casePart = 4, RULE_ifExpr = 5, RULE_ifPart = 6, RULE_elseIfPart = 7, 
		RULE_elsePart = 8, RULE_expr = 9, RULE_ifCondition = 10, RULE_item = 11, 
		RULE_unit = 12, RULE_cellPosition = 13, RULE_relativeCell = 14, RULE_cell = 15, 
		RULE_dataset = 16, RULE_function = 17, RULE_functionParameter = 18, RULE_set = 19, 
		RULE_cellCoordinate = 20, RULE_coordinate = 21, RULE_cellIndicator = 22, 
		RULE_conditions = 23, RULE_condition = 24, RULE_property = 25, RULE_simpleValue = 26, 
		RULE_join = 27, RULE_aggregate = 28;
	public static final String[] ruleNames = {
		"expression", "exprComposite", "ternaryExpr", "caseExpr", "casePart", 
		"ifExpr", "ifPart", "elseIfPart", "elsePart", "expr", "ifCondition", "item", 
		"unit", "cellPosition", "relativeCell", "cell", "dataset", "function", 
		"functionParameter", "set", "cellCoordinate", "coordinate", "cellIndicator", 
		"conditions", "condition", "property", "simpleValue", "join", "aggregate"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'?'", "'case'", "'{'", "'}'", "'return'", "'if'", "';'", "'else'", 
		"'&'", "'$'", "'cell'", "'.'", "'['", "']'", "'to'", null, null, null, 
		null, null, "':'", "','", "'null'", "'('", "')'", null, null, null, null, 
		null, "'!'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, "Cell", "Operator", "OP", "ORDER", "BOOLEAN", 
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
			setState(61);
			switch (_input.LA(1)) {
			case T__8:
			case T__9:
			case T__10:
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
				setState(58);
				exprComposite(0);
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 2);
				{
				setState(59);
				ifExpr();
				}
				break;
			case T__1:
				enterOuterAlt(_localctx, 3);
				{
				setState(60);
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
			setState(70);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				{
				_localctx = new SingleExprCompositeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(64);
				expr();
				}
				break;
			case 2:
				{
				_localctx = new TernaryExprCompositeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(65);
				ternaryExpr();
				}
				break;
			case 3:
				{
				_localctx = new ParenExprCompositeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(66);
				match(LeftParen);
				setState(67);
				exprComposite(0);
				setState(68);
				match(RightParen);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(77);
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
					setState(72);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(73);
					match(Operator);
					setState(74);
					exprComposite(2);
					}
					} 
				}
				setState(79);
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
			setState(80);
			ifCondition();
			setState(86);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND || _la==OR) {
				{
				{
				setState(81);
				join();
				setState(82);
				ifCondition();
				}
				}
				setState(88);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(89);
			match(T__0);
			setState(90);
			expr();
			setState(91);
			match(COLON);
			setState(92);
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
			setState(94);
			match(T__1);
			setState(95);
			match(T__2);
			setState(96);
			casePart();
			setState(101);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(97);
				match(COMMA);
				setState(98);
				casePart();
				}
				}
				setState(103);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(104);
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
			setState(106);
			ifCondition();
			setState(112);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND || _la==OR) {
				{
				{
				setState(107);
				join();
				setState(108);
				ifCondition();
				}
				}
				setState(114);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(116);
			_la = _input.LA(1);
			if (_la==COLON) {
				{
				setState(115);
				match(COLON);
				}
			}

			setState(119);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(118);
				match(T__4);
				}
			}

			setState(121);
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
			setState(123);
			ifPart();
			setState(127);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(124);
					elseIfPart();
					}
					} 
				}
				setState(129);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			}
			setState(131);
			_la = _input.LA(1);
			if (_la==T__7) {
				{
				setState(130);
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
			setState(133);
			match(T__5);
			setState(134);
			match(LeftParen);
			setState(135);
			ifCondition();
			setState(141);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND || _la==OR) {
				{
				{
				setState(136);
				join();
				setState(137);
				ifCondition();
				}
				}
				setState(143);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(144);
			match(RightParen);
			setState(145);
			match(T__2);
			setState(147);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(146);
				match(T__4);
				}
			}

			setState(149);
			expr();
			setState(151);
			_la = _input.LA(1);
			if (_la==T__6) {
				{
				setState(150);
				match(T__6);
				}
			}

			setState(153);
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
			setState(155);
			match(T__7);
			setState(156);
			match(T__5);
			setState(157);
			match(LeftParen);
			setState(158);
			ifCondition();
			setState(164);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND || _la==OR) {
				{
				{
				setState(159);
				join();
				setState(160);
				ifCondition();
				}
				}
				setState(166);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(167);
			match(RightParen);
			setState(168);
			match(T__2);
			setState(170);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(169);
				match(T__4);
				}
			}

			setState(172);
			expr();
			setState(174);
			_la = _input.LA(1);
			if (_la==T__6) {
				{
				setState(173);
				match(T__6);
				}
			}

			setState(176);
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
			setState(178);
			match(T__7);
			setState(179);
			match(T__2);
			setState(181);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(180);
				match(T__4);
				}
			}

			setState(183);
			expr();
			setState(185);
			_la = _input.LA(1);
			if (_la==T__6) {
				{
				setState(184);
				match(T__6);
				}
			}

			setState(187);
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
			setState(189);
			item();
			setState(194);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(190);
					match(Operator);
					setState(191);
					item();
					}
					} 
				}
				setState(196);
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
			setState(197);
			expr();
			setState(198);
			match(OP);
			setState(199);
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
			setState(223);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				_localctx = new SimpleJoinContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(201);
				unit();
				setState(206);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(202);
						match(Operator);
						setState(203);
						unit();
						}
						} 
					}
					setState(208);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
				}
				}
				break;
			case 2:
				_localctx = new SingleParenJoinContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(209);
				match(LeftParen);
				setState(210);
				item();
				setState(211);
				match(RightParen);
				}
				break;
			case 3:
				_localctx = new ParenJoinContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(213);
				match(LeftParen);
				setState(214);
				item();
				setState(217); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(215);
					match(Operator);
					setState(216);
					item();
					}
					}
					setState(219); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==Operator );
				setState(221);
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
			setState(236);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(225);
				dataset();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(226);
				function();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(227);
				set(0);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(228);
				cellPosition();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(229);
				relativeCell();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(230);
				cell();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(231);
				match(INTEGER);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(232);
				match(BOOLEAN);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(233);
				match(STRING);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(234);
				match(NUMBER);
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(235);
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
			setState(238);
			match(T__8);
			setState(239);
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
			setState(241);
			match(T__9);
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
		enterRule(_localctx, 30, RULE_cell);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(244);
			match(T__10);
			setState(247);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				{
				setState(245);
				match(T__11);
				setState(246);
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
		enterRule(_localctx, 32, RULE_dataset);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(249);
			match(Identifier);
			setState(250);
			match(T__11);
			setState(251);
			aggregate();
			setState(252);
			match(LeftParen);
			setState(254);
			_la = _input.LA(1);
			if (_la==Identifier) {
				{
				setState(253);
				property(0);
				}
			}

			setState(258);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				{
				setState(256);
				match(COMMA);
				setState(257);
				conditions();
				}
				break;
			}
			setState(262);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(260);
				match(COMMA);
				setState(261);
				match(ORDER);
				}
			}

			setState(264);
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
		enterRule(_localctx, 34, RULE_function);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(266);
			match(Identifier);
			setState(267);
			match(LeftParen);
			setState(269);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Cell) | (1L << BOOLEAN) | (1L << NULL) | (1L << STRING) | (1L << INTEGER) | (1L << NUMBER))) != 0)) {
				{
				setState(268);
				functionParameter();
				}
			}

			setState(271);
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
		enterRule(_localctx, 36, RULE_functionParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(273);
			set(0);
			setState(280);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Cell) | (1L << BOOLEAN) | (1L << COMMA) | (1L << NULL) | (1L << STRING) | (1L << INTEGER) | (1L << NUMBER))) != 0)) {
				{
				{
				setState(275);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(274);
					match(COMMA);
					}
				}

				setState(277);
				set(0);
				}
				}
				setState(282);
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
		int _startState = 38;
		enterRecursionRule(_localctx, 38, RULE_set, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(316);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
			case 1:
				{
				_localctx = new SimpleDataContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(284);
				simpleValue();
				}
				break;
			case 2:
				{
				_localctx = new SingleCellContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(285);
				match(Cell);
				}
				break;
			case 3:
				{
				_localctx = new WholeCellContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(286);
				match(Cell);
				setState(287);
				match(T__12);
				setState(288);
				match(T__13);
				setState(293);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
				case 1:
					{
					setState(289);
					match(T__2);
					setState(290);
					conditions();
					setState(291);
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
				setState(295);
				match(Cell);
				setState(296);
				match(COLON);
				setState(297);
				match(Cell);
				}
				break;
			case 5:
				{
				_localctx = new SingleCellConditionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(298);
				match(Cell);
				setState(299);
				match(T__2);
				setState(300);
				conditions();
				setState(301);
				match(T__3);
				}
				break;
			case 6:
				{
				_localctx = new SingleCellCoordinateContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(303);
				match(Cell);
				setState(304);
				match(T__12);
				setState(305);
				cellCoordinate();
				setState(306);
				match(T__13);
				}
				break;
			case 7:
				{
				_localctx = new CellCoordinateConditionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(308);
				match(Cell);
				setState(309);
				match(T__12);
				setState(310);
				cellCoordinate();
				setState(311);
				match(T__13);
				setState(312);
				match(T__2);
				setState(313);
				conditions();
				setState(314);
				match(T__3);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(323);
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
					setState(318);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(319);
					match(T__14);
					setState(320);
					set(2);
					}
					} 
				}
				setState(325);
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
		enterRule(_localctx, 40, RULE_cellCoordinate);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(326);
			coordinate();
			setState(329);
			_la = _input.LA(1);
			if (_la==T__6) {
				{
				setState(327);
				match(T__6);
				setState(328);
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
		enterRule(_localctx, 42, RULE_coordinate);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(331);
			cellIndicator();
			setState(336);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(332);
				match(COMMA);
				setState(333);
				cellIndicator();
				}
				}
				setState(338);
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
		enterRule(_localctx, 44, RULE_cellIndicator);
		int _la;
		try {
			setState(346);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
			case 1:
				_localctx = new RelativeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(339);
				match(Cell);
				}
				break;
			case 2:
				_localctx = new AbsoluteContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(340);
				match(Cell);
				setState(341);
				match(COLON);
				setState(343);
				_la = _input.LA(1);
				if (_la==EXCLAMATION) {
					{
					setState(342);
					match(EXCLAMATION);
					}
				}

				setState(345);
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
		enterRule(_localctx, 46, RULE_conditions);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(348);
			condition();
			setState(354);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND || _la==OR) {
				{
				{
				setState(349);
				join();
				setState(350);
				condition();
				}
				}
				setState(356);
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
		enterRule(_localctx, 48, RULE_condition);
		try {
			setState(368);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
			case 1:
				_localctx = new CellNameExprConditionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(357);
				match(Cell);
				setState(358);
				match(OP);
				setState(359);
				expr();
				}
				break;
			case 2:
				_localctx = new PropertyConditionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(360);
				property(0);
				setState(361);
				match(OP);
				setState(362);
				expr();
				}
				break;
			case 3:
				_localctx = new ExprConditionContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(364);
				expr();
				setState(365);
				match(OP);
				setState(366);
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
		int _startState = 50;
		enterRecursionRule(_localctx, 50, RULE_property, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(371);
			match(Identifier);
			}
			_ctx.stop = _input.LT(-1);
			setState(378);
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
					setState(373);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(374);
					match(T__11);
					setState(375);
					property(2);
					}
					} 
				}
				setState(380);
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
		enterRule(_localctx, 52, RULE_simpleValue);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(381);
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
		enterRule(_localctx, 54, RULE_join);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(383);
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
		enterRule(_localctx, 56, RULE_aggregate);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(385);
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
		case 19:
			return set_sempred((SetContext)_localctx, predIndex);
		case 25:
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3(\u0186\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\3\2\3\2\3\2\5\2@\n"+
		"\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3I\n\3\3\3\3\3\3\3\7\3N\n\3\f\3\16\3"+
		"Q\13\3\3\4\3\4\3\4\3\4\7\4W\n\4\f\4\16\4Z\13\4\3\4\3\4\3\4\3\4\3\4\3\5"+
		"\3\5\3\5\3\5\3\5\7\5f\n\5\f\5\16\5i\13\5\3\5\3\5\3\6\3\6\3\6\3\6\7\6q"+
		"\n\6\f\6\16\6t\13\6\3\6\5\6w\n\6\3\6\5\6z\n\6\3\6\3\6\3\7\3\7\7\7\u0080"+
		"\n\7\f\7\16\7\u0083\13\7\3\7\5\7\u0086\n\7\3\b\3\b\3\b\3\b\3\b\3\b\7\b"+
		"\u008e\n\b\f\b\16\b\u0091\13\b\3\b\3\b\3\b\5\b\u0096\n\b\3\b\3\b\5\b\u009a"+
		"\n\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\7\t\u00a5\n\t\f\t\16\t\u00a8"+
		"\13\t\3\t\3\t\3\t\5\t\u00ad\n\t\3\t\3\t\5\t\u00b1\n\t\3\t\3\t\3\n\3\n"+
		"\3\n\5\n\u00b8\n\n\3\n\3\n\5\n\u00bc\n\n\3\n\3\n\3\13\3\13\3\13\7\13\u00c3"+
		"\n\13\f\13\16\13\u00c6\13\13\3\f\3\f\3\f\3\f\3\r\3\r\3\r\7\r\u00cf\n\r"+
		"\f\r\16\r\u00d2\13\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\6\r\u00dc\n\r\r\r"+
		"\16\r\u00dd\3\r\3\r\5\r\u00e2\n\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3"+
		"\16\3\16\3\16\3\16\5\16\u00ef\n\16\3\17\3\17\3\17\3\20\3\20\3\20\3\21"+
		"\3\21\3\21\5\21\u00fa\n\21\3\22\3\22\3\22\3\22\3\22\5\22\u0101\n\22\3"+
		"\22\3\22\5\22\u0105\n\22\3\22\3\22\5\22\u0109\n\22\3\22\3\22\3\23\3\23"+
		"\3\23\5\23\u0110\n\23\3\23\3\23\3\24\3\24\5\24\u0116\n\24\3\24\7\24\u0119"+
		"\n\24\f\24\16\24\u011c\13\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3"+
		"\25\3\25\5\25\u0128\n\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\5\25\u013f"+
		"\n\25\3\25\3\25\3\25\7\25\u0144\n\25\f\25\16\25\u0147\13\25\3\26\3\26"+
		"\3\26\5\26\u014c\n\26\3\27\3\27\3\27\7\27\u0151\n\27\f\27\16\27\u0154"+
		"\13\27\3\30\3\30\3\30\3\30\5\30\u015a\n\30\3\30\5\30\u015d\n\30\3\31\3"+
		"\31\3\31\3\31\7\31\u0163\n\31\f\31\16\31\u0166\13\31\3\32\3\32\3\32\3"+
		"\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\5\32\u0173\n\32\3\33\3\33\3\33"+
		"\3\33\3\33\3\33\7\33\u017b\n\33\f\33\16\33\u017e\13\33\3\34\3\34\3\35"+
		"\3\35\3\36\3\36\3\36\2\5\4(\64\37\2\4\6\b\n\f\16\20\22\24\26\30\32\34"+
		"\36 \"$&(*,.\60\62\64\668:\2\4\6\2\26\26\31\31\34\34\37 \3\2\35\36\u01a2"+
		"\2?\3\2\2\2\4H\3\2\2\2\6R\3\2\2\2\b`\3\2\2\2\nl\3\2\2\2\f}\3\2\2\2\16"+
		"\u0087\3\2\2\2\20\u009d\3\2\2\2\22\u00b4\3\2\2\2\24\u00bf\3\2\2\2\26\u00c7"+
		"\3\2\2\2\30\u00e1\3\2\2\2\32\u00ee\3\2\2\2\34\u00f0\3\2\2\2\36\u00f3\3"+
		"\2\2\2 \u00f6\3\2\2\2\"\u00fb\3\2\2\2$\u010c\3\2\2\2&\u0113\3\2\2\2(\u013e"+
		"\3\2\2\2*\u0148\3\2\2\2,\u014d\3\2\2\2.\u015c\3\2\2\2\60\u015e\3\2\2\2"+
		"\62\u0172\3\2\2\2\64\u0174\3\2\2\2\66\u017f\3\2\2\28\u0181\3\2\2\2:\u0183"+
		"\3\2\2\2<@\5\4\3\2=@\5\f\7\2>@\5\b\5\2?<\3\2\2\2?=\3\2\2\2?>\3\2\2\2@"+
		"\3\3\2\2\2AB\b\3\1\2BI\5\24\13\2CI\5\6\4\2DE\7\32\2\2EF\5\4\3\2FG\7\33"+
		"\2\2GI\3\2\2\2HA\3\2\2\2HC\3\2\2\2HD\3\2\2\2IO\3\2\2\2JK\f\3\2\2KL\7\23"+
		"\2\2LN\5\4\3\4MJ\3\2\2\2NQ\3\2\2\2OM\3\2\2\2OP\3\2\2\2P\5\3\2\2\2QO\3"+
		"\2\2\2RX\5\26\f\2ST\58\35\2TU\5\26\f\2UW\3\2\2\2VS\3\2\2\2WZ\3\2\2\2X"+
		"V\3\2\2\2XY\3\2\2\2Y[\3\2\2\2ZX\3\2\2\2[\\\7\3\2\2\\]\5\24\13\2]^\7\27"+
		"\2\2^_\5\24\13\2_\7\3\2\2\2`a\7\4\2\2ab\7\5\2\2bg\5\n\6\2cd\7\30\2\2d"+
		"f\5\n\6\2ec\3\2\2\2fi\3\2\2\2ge\3\2\2\2gh\3\2\2\2hj\3\2\2\2ig\3\2\2\2"+
		"jk\7\6\2\2k\t\3\2\2\2lr\5\26\f\2mn\58\35\2no\5\26\f\2oq\3\2\2\2pm\3\2"+
		"\2\2qt\3\2\2\2rp\3\2\2\2rs\3\2\2\2sv\3\2\2\2tr\3\2\2\2uw\7\27\2\2vu\3"+
		"\2\2\2vw\3\2\2\2wy\3\2\2\2xz\7\7\2\2yx\3\2\2\2yz\3\2\2\2z{\3\2\2\2{|\5"+
		"\24\13\2|\13\3\2\2\2}\u0081\5\16\b\2~\u0080\5\20\t\2\177~\3\2\2\2\u0080"+
		"\u0083\3\2\2\2\u0081\177\3\2\2\2\u0081\u0082\3\2\2\2\u0082\u0085\3\2\2"+
		"\2\u0083\u0081\3\2\2\2\u0084\u0086\5\22\n\2\u0085\u0084\3\2\2\2\u0085"+
		"\u0086\3\2\2\2\u0086\r\3\2\2\2\u0087\u0088\7\b\2\2\u0088\u0089\7\32\2"+
		"\2\u0089\u008f\5\26\f\2\u008a\u008b\58\35\2\u008b\u008c\5\26\f\2\u008c"+
		"\u008e\3\2\2\2\u008d\u008a\3\2\2\2\u008e\u0091\3\2\2\2\u008f\u008d\3\2"+
		"\2\2\u008f\u0090\3\2\2\2\u0090\u0092\3\2\2\2\u0091\u008f\3\2\2\2\u0092"+
		"\u0093\7\33\2\2\u0093\u0095\7\5\2\2\u0094\u0096\7\7\2\2\u0095\u0094\3"+
		"\2\2\2\u0095\u0096\3\2\2\2\u0096\u0097\3\2\2\2\u0097\u0099\5\24\13\2\u0098"+
		"\u009a\7\t\2\2\u0099\u0098\3\2\2\2\u0099\u009a\3\2\2\2\u009a\u009b\3\2"+
		"\2\2\u009b\u009c\7\6\2\2\u009c\17\3\2\2\2\u009d\u009e\7\n\2\2\u009e\u009f"+
		"\7\b\2\2\u009f\u00a0\7\32\2\2\u00a0\u00a6\5\26\f\2\u00a1\u00a2\58\35\2"+
		"\u00a2\u00a3\5\26\f\2\u00a3\u00a5\3\2\2\2\u00a4\u00a1\3\2\2\2\u00a5\u00a8"+
		"\3\2\2\2\u00a6\u00a4\3\2\2\2\u00a6\u00a7\3\2\2\2\u00a7\u00a9\3\2\2\2\u00a8"+
		"\u00a6\3\2\2\2\u00a9\u00aa\7\33\2\2\u00aa\u00ac\7\5\2\2\u00ab\u00ad\7"+
		"\7\2\2\u00ac\u00ab\3\2\2\2\u00ac\u00ad\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae"+
		"\u00b0\5\24\13\2\u00af\u00b1\7\t\2\2\u00b0\u00af\3\2\2\2\u00b0\u00b1\3"+
		"\2\2\2\u00b1\u00b2\3\2\2\2\u00b2\u00b3\7\6\2\2\u00b3\21\3\2\2\2\u00b4"+
		"\u00b5\7\n\2\2\u00b5\u00b7\7\5\2\2\u00b6\u00b8\7\7\2\2\u00b7\u00b6\3\2"+
		"\2\2\u00b7\u00b8\3\2\2\2\u00b8\u00b9\3\2\2\2\u00b9\u00bb\5\24\13\2\u00ba"+
		"\u00bc\7\t\2\2\u00bb\u00ba\3\2\2\2\u00bb\u00bc\3\2\2\2\u00bc\u00bd\3\2"+
		"\2\2\u00bd\u00be\7\6\2\2\u00be\23\3\2\2\2\u00bf\u00c4\5\30\r\2\u00c0\u00c1"+
		"\7\23\2\2\u00c1\u00c3\5\30\r\2\u00c2\u00c0\3\2\2\2\u00c3\u00c6\3\2\2\2"+
		"\u00c4\u00c2\3\2\2\2\u00c4\u00c5\3\2\2\2\u00c5\25\3\2\2\2\u00c6\u00c4"+
		"\3\2\2\2\u00c7\u00c8\5\24\13\2\u00c8\u00c9\7\24\2\2\u00c9\u00ca\5\24\13"+
		"\2\u00ca\27\3\2\2\2\u00cb\u00d0\5\32\16\2\u00cc\u00cd\7\23\2\2\u00cd\u00cf"+
		"\5\32\16\2\u00ce\u00cc\3\2\2\2\u00cf\u00d2\3\2\2\2\u00d0\u00ce\3\2\2\2"+
		"\u00d0\u00d1\3\2\2\2\u00d1\u00e2\3\2\2\2\u00d2\u00d0\3\2\2\2\u00d3\u00d4"+
		"\7\32\2\2\u00d4\u00d5\5\30\r\2\u00d5\u00d6\7\33\2\2\u00d6\u00e2\3\2\2"+
		"\2\u00d7\u00d8\7\32\2\2\u00d8\u00db\5\30\r\2\u00d9\u00da\7\23\2\2\u00da"+
		"\u00dc\5\30\r\2\u00db\u00d9\3\2\2\2\u00dc\u00dd\3\2\2\2\u00dd\u00db\3"+
		"\2\2\2\u00dd\u00de\3\2\2\2\u00de\u00df\3\2\2\2\u00df\u00e0\7\33\2\2\u00e0"+
		"\u00e2\3\2\2\2\u00e1\u00cb\3\2\2\2\u00e1\u00d3\3\2\2\2\u00e1\u00d7\3\2"+
		"\2\2\u00e2\31\3\2\2\2\u00e3\u00ef\5\"\22\2\u00e4\u00ef\5$\23\2\u00e5\u00ef"+
		"\5(\25\2\u00e6\u00ef\5\34\17\2\u00e7\u00ef\5\36\20\2\u00e8\u00ef\5 \21"+
		"\2\u00e9\u00ef\7\37\2\2\u00ea\u00ef\7\26\2\2\u00eb\u00ef\7\34\2\2\u00ec"+
		"\u00ef\7 \2\2\u00ed\u00ef\7\31\2\2\u00ee\u00e3\3\2\2\2\u00ee\u00e4\3\2"+
		"\2\2\u00ee\u00e5\3\2\2\2\u00ee\u00e6\3\2\2\2\u00ee\u00e7\3\2\2\2\u00ee"+
		"\u00e8\3\2\2\2\u00ee\u00e9\3\2\2\2\u00ee\u00ea\3\2\2\2\u00ee\u00eb\3\2"+
		"\2\2\u00ee\u00ec\3\2\2\2\u00ee\u00ed\3\2\2\2\u00ef\33\3\2\2\2\u00f0\u00f1"+
		"\7\13\2\2\u00f1\u00f2\7\22\2\2\u00f2\35\3\2\2\2\u00f3\u00f4\7\f\2\2\u00f4"+
		"\u00f5\7\22\2\2\u00f5\37\3\2\2\2\u00f6\u00f9\7\r\2\2\u00f7\u00f8\7\16"+
		"\2\2\u00f8\u00fa\5\64\33\2\u00f9\u00f7\3\2\2\2\u00f9\u00fa\3\2\2\2\u00fa"+
		"!\3\2\2\2\u00fb\u00fc\7$\2\2\u00fc\u00fd\7\16\2\2\u00fd\u00fe\5:\36\2"+
		"\u00fe\u0100\7\32\2\2\u00ff\u0101\5\64\33\2\u0100\u00ff\3\2\2\2\u0100"+
		"\u0101\3\2\2\2\u0101\u0104\3\2\2\2\u0102\u0103\7\30\2\2\u0103\u0105\5"+
		"\60\31\2\u0104\u0102\3\2\2\2\u0104\u0105\3\2\2\2\u0105\u0108\3\2\2\2\u0106"+
		"\u0107\7\30\2\2\u0107\u0109\7\25\2\2\u0108\u0106\3\2\2\2\u0108\u0109\3"+
		"\2\2\2\u0109\u010a\3\2\2\2\u010a\u010b\7\33\2\2\u010b#\3\2\2\2\u010c\u010d"+
		"\7$\2\2\u010d\u010f\7\32\2\2\u010e\u0110\5&\24\2\u010f\u010e\3\2\2\2\u010f"+
		"\u0110\3\2\2\2\u0110\u0111\3\2\2\2\u0111\u0112\7\33\2\2\u0112%\3\2\2\2"+
		"\u0113\u011a\5(\25\2\u0114\u0116\7\30\2\2\u0115\u0114\3\2\2\2\u0115\u0116"+
		"\3\2\2\2\u0116\u0117\3\2\2\2\u0117\u0119\5(\25\2\u0118\u0115\3\2\2\2\u0119"+
		"\u011c\3\2\2\2\u011a\u0118\3\2\2\2\u011a\u011b\3\2\2\2\u011b\'\3\2\2\2"+
		"\u011c\u011a\3\2\2\2\u011d\u011e\b\25\1\2\u011e\u013f\5\66\34\2\u011f"+
		"\u013f\7\22\2\2\u0120\u0121\7\22\2\2\u0121\u0122\7\17\2\2\u0122\u0127"+
		"\7\20\2\2\u0123\u0124\7\5\2\2\u0124\u0125\5\60\31\2\u0125\u0126\7\6\2"+
		"\2\u0126\u0128\3\2\2\2\u0127\u0123\3\2\2\2\u0127\u0128\3\2\2\2\u0128\u013f"+
		"\3\2\2\2\u0129\u012a\7\22\2\2\u012a\u012b\7\27\2\2\u012b\u013f\7\22\2"+
		"\2\u012c\u012d\7\22\2\2\u012d\u012e\7\5\2\2\u012e\u012f\5\60\31\2\u012f"+
		"\u0130\7\6\2\2\u0130\u013f\3\2\2\2\u0131\u0132\7\22\2\2\u0132\u0133\7"+
		"\17\2\2\u0133\u0134\5*\26\2\u0134\u0135\7\20\2\2\u0135\u013f\3\2\2\2\u0136"+
		"\u0137\7\22\2\2\u0137\u0138\7\17\2\2\u0138\u0139\5*\26\2\u0139\u013a\7"+
		"\20\2\2\u013a\u013b\7\5\2\2\u013b\u013c\5\60\31\2\u013c\u013d\7\6\2\2"+
		"\u013d\u013f\3\2\2\2\u013e\u011d\3\2\2\2\u013e\u011f\3\2\2\2\u013e\u0120"+
		"\3\2\2\2\u013e\u0129\3\2\2\2\u013e\u012c\3\2\2\2\u013e\u0131\3\2\2\2\u013e"+
		"\u0136\3\2\2\2\u013f\u0145\3\2\2\2\u0140\u0141\f\3\2\2\u0141\u0142\7\21"+
		"\2\2\u0142\u0144\5(\25\4\u0143\u0140\3\2\2\2\u0144\u0147\3\2\2\2\u0145"+
		"\u0143\3\2\2\2\u0145\u0146\3\2\2\2\u0146)\3\2\2\2\u0147\u0145\3\2\2\2"+
		"\u0148\u014b\5,\27\2\u0149\u014a\7\t\2\2\u014a\u014c\5,\27\2\u014b\u0149"+
		"\3\2\2\2\u014b\u014c\3\2\2\2\u014c+\3\2\2\2\u014d\u0152\5.\30\2\u014e"+
		"\u014f\7\30\2\2\u014f\u0151\5.\30\2\u0150\u014e\3\2\2\2\u0151\u0154\3"+
		"\2\2\2\u0152\u0150\3\2\2\2\u0152\u0153\3\2\2\2\u0153-\3\2\2\2\u0154\u0152"+
		"\3\2\2\2\u0155\u015d\7\22\2\2\u0156\u0157\7\22\2\2\u0157\u0159\7\27\2"+
		"\2\u0158\u015a\7!\2\2\u0159\u0158\3\2\2\2\u0159\u015a\3\2\2\2\u015a\u015b"+
		"\3\2\2\2\u015b\u015d\7\37\2\2\u015c\u0155\3\2\2\2\u015c\u0156\3\2\2\2"+
		"\u015d/\3\2\2\2\u015e\u0164\5\62\32\2\u015f\u0160\58\35\2\u0160\u0161"+
		"\5\62\32\2\u0161\u0163\3\2\2\2\u0162\u015f\3\2\2\2\u0163\u0166\3\2\2\2"+
		"\u0164\u0162\3\2\2\2\u0164\u0165\3\2\2\2\u0165\61\3\2\2\2\u0166\u0164"+
		"\3\2\2\2\u0167\u0168\7\22\2\2\u0168\u0169\7\24\2\2\u0169\u0173\5\24\13"+
		"\2\u016a\u016b\5\64\33\2\u016b\u016c\7\24\2\2\u016c\u016d\5\24\13\2\u016d"+
		"\u0173\3\2\2\2\u016e\u016f\5\24\13\2\u016f\u0170\7\24\2\2\u0170\u0171"+
		"\5\24\13\2\u0171\u0173\3\2\2\2\u0172\u0167\3\2\2\2\u0172\u016a\3\2\2\2"+
		"\u0172\u016e\3\2\2\2\u0173\63\3\2\2\2\u0174\u0175\b\33\1\2\u0175\u0176"+
		"\7$\2\2\u0176\u017c\3\2\2\2\u0177\u0178\f\3\2\2\u0178\u0179\7\16\2\2\u0179"+
		"\u017b\5\64\33\4\u017a\u0177\3\2\2\2\u017b\u017e\3\2\2\2\u017c\u017a\3"+
		"\2\2\2\u017c\u017d\3\2\2\2\u017d\65\3\2\2\2\u017e\u017c\3\2\2\2\u017f"+
		"\u0180\t\2\2\2\u0180\67\3\2\2\2\u0181\u0182\t\3\2\2\u01829\3\2\2\2\u0183"+
		"\u0184\7$\2\2\u0184;\3\2\2\2*?HOXgrvy\u0081\u0085\u008f\u0095\u0099\u00a6"+
		"\u00ac\u00b0\u00b7\u00bb\u00c4\u00d0\u00dd\u00e1\u00ee\u00f9\u0100\u0104"+
		"\u0108\u010f\u0115\u011a\u0127\u013e\u0145\u014b\u0152\u0159\u015c\u0164"+
		"\u0172\u017c";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}