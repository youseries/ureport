// Generated from ReportLexer.g4 by ANTLR 4.5.3
package com.bstek.ureport.dsl;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ReportLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		Cell=1, Operator=2, OP=3, ORDER=4, BOOLEAN=5, COLON=6, COMMA=7, NULL=8, 
		LeftParen=9, RightParen=10, STRING=11, AND=12, OR=13, INTEGER=14, NUMBER=15, 
		EXCLAMATION=16, EXP=17, Identifier=18, LETTER=19, Char=20, DIGIT=21, WS=22, 
		NL=23;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"Cell", "Operator", "OP", "ORDER", "BOOLEAN", "COLON", "COMMA", "NULL", 
		"LeftParen", "RightParen", "STRING", "AND", "OR", "INTEGER", "NUMBER", 
		"EXCLAMATION", "EXP", "Identifier", "LETTER", "Char", "DIGIT", "STRING_CONTENT", 
		"EscapeSequence", "OctalEscape", "UnicodeEscape", "HEX", "StartChar", 
		"WS", "NL"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, null, null, null, "':'", "','", "'null'", "'('", "')'", 
		null, null, null, null, null, "'!'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "Cell", "Operator", "OP", "ORDER", "BOOLEAN", "COLON", "COMMA", 
		"NULL", "LeftParen", "RightParen", "STRING", "AND", "OR", "INTEGER", "NUMBER", 
		"EXCLAMATION", "EXP", "Identifier", "LETTER", "Char", "DIGIT", "WS", "NL"
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


	public ReportLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "ReportLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\31\u0119\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\3\2\3\2\6\2@\n"+
		"\2\r\2\16\2A\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4b\n"+
		"\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5k\n\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\5\6v\n\6\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\13\3\13"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\5\f\u008d\n\f\3\r\3\r\3\r\3\r\3\r\5\r"+
		"\u0094\n\r\3\16\3\16\3\16\3\16\5\16\u009a\n\16\3\17\5\17\u009d\n\17\3"+
		"\17\6\17\u00a0\n\17\r\17\16\17\u00a1\3\20\5\20\u00a5\n\20\3\20\6\20\u00a8"+
		"\n\20\r\20\16\20\u00a9\3\20\3\20\6\20\u00ae\n\20\r\20\16\20\u00af\3\20"+
		"\5\20\u00b3\n\20\3\20\5\20\u00b6\n\20\3\20\6\20\u00b9\n\20\r\20\16\20"+
		"\u00ba\3\20\3\20\3\20\5\20\u00c0\n\20\3\20\6\20\u00c3\n\20\r\20\16\20"+
		"\u00c4\5\20\u00c7\n\20\3\21\3\21\3\22\3\22\5\22\u00cd\n\22\3\22\6\22\u00d0"+
		"\n\22\r\22\16\22\u00d1\3\23\3\23\7\23\u00d6\n\23\f\23\16\23\u00d9\13\23"+
		"\3\24\6\24\u00dc\n\24\r\24\16\24\u00dd\3\25\3\25\3\25\3\25\5\25\u00e4"+
		"\n\25\3\26\3\26\3\27\3\27\7\27\u00ea\n\27\f\27\16\27\u00ed\13\27\3\30"+
		"\3\30\3\30\3\30\5\30\u00f3\n\30\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31"+
		"\3\31\5\31\u00fe\n\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\33\3\33\3\34"+
		"\5\34\u010a\n\34\3\35\6\35\u010d\n\35\r\35\16\35\u010e\3\35\3\35\3\36"+
		"\5\36\u0114\n\36\3\36\3\36\3\36\3\36\2\2\37\3\3\5\4\7\5\t\6\13\7\r\b\17"+
		"\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+"+
		"\27-\2/\2\61\2\63\2\65\2\67\29\30;\31\3\2\17\6\2\'\',-//\61\61\4\2>>@"+
		"@\4\2GGgg\4\2--//\3\2C\\\4\2//aa\5\2\u00b9\u00b9\u0302\u0371\u2041\u2042"+
		"\3\2\62;\4\2$$))\n\2$$))^^ddhhppttvv\5\2\62;CHch\t\2C\\c|\u2072\u2191"+
		"\u2c02\u2ff1\u3003\ud801\uf902\ufdd1\ufdf2\uffff\5\2\13\f\17\17\"\"\u013b"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\29\3\2\2\2\2;\3\2\2"+
		"\2\3=\3\2\2\2\5C\3\2\2\2\7a\3\2\2\2\tj\3\2\2\2\13u\3\2\2\2\rw\3\2\2\2"+
		"\17y\3\2\2\2\21{\3\2\2\2\23\u0080\3\2\2\2\25\u0082\3\2\2\2\27\u008c\3"+
		"\2\2\2\31\u0093\3\2\2\2\33\u0099\3\2\2\2\35\u009c\3\2\2\2\37\u00c6\3\2"+
		"\2\2!\u00c8\3\2\2\2#\u00ca\3\2\2\2%\u00d3\3\2\2\2\'\u00db\3\2\2\2)\u00e3"+
		"\3\2\2\2+\u00e5\3\2\2\2-\u00eb\3\2\2\2/\u00f2\3\2\2\2\61\u00fd\3\2\2\2"+
		"\63\u00ff\3\2\2\2\65\u0106\3\2\2\2\67\u0109\3\2\2\29\u010c\3\2\2\2;\u0113"+
		"\3\2\2\2=?\5\'\24\2>@\5+\26\2?>\3\2\2\2@A\3\2\2\2A?\3\2\2\2AB\3\2\2\2"+
		"B\4\3\2\2\2CD\t\2\2\2D\6\3\2\2\2Eb\t\3\2\2FG\7?\2\2Gb\7?\2\2HI\7#\2\2"+
		"Ib\7?\2\2JK\7@\2\2Kb\7?\2\2LM\7>\2\2Mb\7?\2\2NO\7k\2\2Ob\7p\2\2PQ\7p\2"+
		"\2QR\7q\2\2RS\7v\2\2ST\7\"\2\2TU\7k\2\2Ub\7p\2\2VW\7p\2\2WX\7q\2\2XY\7"+
		"v\2\2YZ\7\"\2\2Z[\7\"\2\2[\\\7k\2\2\\b\7p\2\2]^\7n\2\2^_\7k\2\2_`\7m\2"+
		"\2`b\7g\2\2aE\3\2\2\2aF\3\2\2\2aH\3\2\2\2aJ\3\2\2\2aL\3\2\2\2aN\3\2\2"+
		"\2aP\3\2\2\2aV\3\2\2\2a]\3\2\2\2b\b\3\2\2\2cd\7f\2\2de\7g\2\2ef\7u\2\2"+
		"fk\7e\2\2gh\7c\2\2hi\7u\2\2ik\7e\2\2jc\3\2\2\2jg\3\2\2\2k\n\3\2\2\2lm"+
		"\7v\2\2mn\7t\2\2no\7w\2\2ov\7g\2\2pq\7h\2\2qr\7c\2\2rs\7n\2\2st\7u\2\2"+
		"tv\7g\2\2ul\3\2\2\2up\3\2\2\2v\f\3\2\2\2wx\7<\2\2x\16\3\2\2\2yz\7.\2\2"+
		"z\20\3\2\2\2{|\7p\2\2|}\7w\2\2}~\7n\2\2~\177\7n\2\2\177\22\3\2\2\2\u0080"+
		"\u0081\7*\2\2\u0081\24\3\2\2\2\u0082\u0083\7+\2\2\u0083\26\3\2\2\2\u0084"+
		"\u0085\7$\2\2\u0085\u0086\5-\27\2\u0086\u0087\7$\2\2\u0087\u008d\3\2\2"+
		"\2\u0088\u0089\7)\2\2\u0089\u008a\5-\27\2\u008a\u008b\7)\2\2\u008b\u008d"+
		"\3\2\2\2\u008c\u0084\3\2\2\2\u008c\u0088\3\2\2\2\u008d\30\3\2\2\2\u008e"+
		"\u008f\7c\2\2\u008f\u0090\7p\2\2\u0090\u0094\7f\2\2\u0091\u0092\7(\2\2"+
		"\u0092\u0094\7(\2\2\u0093\u008e\3\2\2\2\u0093\u0091\3\2\2\2\u0094\32\3"+
		"\2\2\2\u0095\u0096\7q\2\2\u0096\u009a\7t\2\2\u0097\u0098\7~\2\2\u0098"+
		"\u009a\7~\2\2\u0099\u0095\3\2\2\2\u0099\u0097\3\2\2\2\u009a\34\3\2\2\2"+
		"\u009b\u009d\7/\2\2\u009c\u009b\3\2\2\2\u009c\u009d\3\2\2\2\u009d\u009f"+
		"\3\2\2\2\u009e\u00a0\5+\26\2\u009f\u009e\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1"+
		"\u009f\3\2\2\2\u00a1\u00a2\3\2\2\2\u00a2\36\3\2\2\2\u00a3\u00a5\7/\2\2"+
		"\u00a4\u00a3\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5\u00a7\3\2\2\2\u00a6\u00a8"+
		"\5+\26\2\u00a7\u00a6\3\2\2\2\u00a8\u00a9\3\2\2\2\u00a9\u00a7\3\2\2\2\u00a9"+
		"\u00aa\3\2\2\2\u00aa\u00ab\3\2\2\2\u00ab\u00ad\7\60\2\2\u00ac\u00ae\5"+
		"+\26\2\u00ad\u00ac\3\2\2\2\u00ae\u00af\3\2\2\2\u00af\u00ad\3\2\2\2\u00af"+
		"\u00b0\3\2\2\2\u00b0\u00b2\3\2\2\2\u00b1\u00b3\5#\22\2\u00b2\u00b1\3\2"+
		"\2\2\u00b2\u00b3\3\2\2\2\u00b3\u00c7\3\2\2\2\u00b4\u00b6\7/\2\2\u00b5"+
		"\u00b4\3\2\2\2\u00b5\u00b6\3\2\2\2\u00b6\u00b8\3\2\2\2\u00b7\u00b9\5+"+
		"\26\2\u00b8\u00b7\3\2\2\2\u00b9\u00ba\3\2\2\2\u00ba\u00b8\3\2\2\2\u00ba"+
		"\u00bb\3\2\2\2\u00bb\u00bc\3\2\2\2\u00bc\u00bd\5#\22\2\u00bd\u00c7\3\2"+
		"\2\2\u00be\u00c0\7/\2\2\u00bf\u00be\3\2\2\2\u00bf\u00c0\3\2\2\2\u00c0"+
		"\u00c2\3\2\2\2\u00c1\u00c3\5+\26\2\u00c2\u00c1\3\2\2\2\u00c3\u00c4\3\2"+
		"\2\2\u00c4\u00c2\3\2\2\2\u00c4\u00c5\3\2\2\2\u00c5\u00c7\3\2\2\2\u00c6"+
		"\u00a4\3\2\2\2\u00c6\u00b5\3\2\2\2\u00c6\u00bf\3\2\2\2\u00c7 \3\2\2\2"+
		"\u00c8\u00c9\7#\2\2\u00c9\"\3\2\2\2\u00ca\u00cc\t\4\2\2\u00cb\u00cd\t"+
		"\5\2\2\u00cc\u00cb\3\2\2\2\u00cc\u00cd\3\2\2\2\u00cd\u00cf\3\2\2\2\u00ce"+
		"\u00d0\5+\26\2\u00cf\u00ce\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1\u00cf\3\2"+
		"\2\2\u00d1\u00d2\3\2\2\2\u00d2$\3\2\2\2\u00d3\u00d7\5\67\34\2\u00d4\u00d6"+
		"\5)\25\2\u00d5\u00d4\3\2\2\2\u00d6\u00d9\3\2\2\2\u00d7\u00d5\3\2\2\2\u00d7"+
		"\u00d8\3\2\2\2\u00d8&\3\2\2\2\u00d9\u00d7\3\2\2\2\u00da\u00dc\t\6\2\2"+
		"\u00db\u00da\3\2\2\2\u00dc\u00dd\3\2\2\2\u00dd\u00db\3\2\2\2\u00dd\u00de"+
		"\3\2\2\2\u00de(\3\2\2\2\u00df\u00e4\5\67\34\2\u00e0\u00e4\t\7\2\2\u00e1"+
		"\u00e4\5+\26\2\u00e2\u00e4\t\b\2\2\u00e3\u00df\3\2\2\2\u00e3\u00e0\3\2"+
		"\2\2\u00e3\u00e1\3\2\2\2\u00e3\u00e2\3\2\2\2\u00e4*\3\2\2\2\u00e5\u00e6"+
		"\t\t\2\2\u00e6,\3\2\2\2\u00e7\u00ea\5/\30\2\u00e8\u00ea\n\n\2\2\u00e9"+
		"\u00e7\3\2\2\2\u00e9\u00e8\3\2\2\2\u00ea\u00ed\3\2\2\2\u00eb\u00e9\3\2"+
		"\2\2\u00eb\u00ec\3\2\2\2\u00ec.\3\2\2\2\u00ed\u00eb\3\2\2\2\u00ee\u00ef"+
		"\7^\2\2\u00ef\u00f3\t\13\2\2\u00f0\u00f3\5\63\32\2\u00f1\u00f3\5\61\31"+
		"\2\u00f2\u00ee\3\2\2\2\u00f2\u00f0\3\2\2\2\u00f2\u00f1\3\2\2\2\u00f3\60"+
		"\3\2\2\2\u00f4\u00f5\7^\2\2\u00f5\u00f6\4\62\65\2\u00f6\u00f7\4\629\2"+
		"\u00f7\u00fe\4\629\2\u00f8\u00f9\7^\2\2\u00f9\u00fa\4\629\2\u00fa\u00fe"+
		"\4\629\2\u00fb\u00fc\7^\2\2\u00fc\u00fe\4\629\2\u00fd\u00f4\3\2\2\2\u00fd"+
		"\u00f8\3\2\2\2\u00fd\u00fb\3\2\2\2\u00fe\62\3\2\2\2\u00ff\u0100\7^\2\2"+
		"\u0100\u0101\7w\2\2\u0101\u0102\5\65\33\2\u0102\u0103\5\65\33\2\u0103"+
		"\u0104\5\65\33\2\u0104\u0105\5\65\33\2\u0105\64\3\2\2\2\u0106\u0107\t"+
		"\f\2\2\u0107\66\3\2\2\2\u0108\u010a\t\r\2\2\u0109\u0108\3\2\2\2\u010a"+
		"8\3\2\2\2\u010b\u010d\t\16\2\2\u010c\u010b\3\2\2\2\u010d\u010e\3\2\2\2"+
		"\u010e\u010c\3\2\2\2\u010e\u010f\3\2\2\2\u010f\u0110\3\2\2\2\u0110\u0111"+
		"\b\35\2\2\u0111:\3\2\2\2\u0112\u0114\7\17\2\2\u0113\u0112\3\2\2\2\u0113"+
		"\u0114\3\2\2\2\u0114\u0115\3\2\2\2\u0115\u0116\7\f\2\2\u0116\u0117\3\2"+
		"\2\2\u0117\u0118\b\36\2\2\u0118<\3\2\2\2!\2Aaju\u008c\u0093\u0099\u009c"+
		"\u00a1\u00a4\u00a9\u00af\u00b2\u00b5\u00ba\u00bf\u00c4\u00c6\u00cc\u00d1"+
		"\u00d7\u00dd\u00e3\u00e9\u00eb\u00f2\u00fd\u0109\u010e\u0113\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}