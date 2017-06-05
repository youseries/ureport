// Generated from ReportParser.g4 by ANTLR 4.5.3
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
public class ReportParserLexer extends Lexer {
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
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "Cell", "Operator", 
		"OP", "ORDER", "BOOLEAN", "COLON", "COMMA", "NULL", "LeftParen", "RightParen", 
		"STRING", "AND", "OR", "INTEGER", "NUMBER", "EXCLAMATION", "EXP", "LETTER", 
		"Identifier", "Char", "DIGIT", "STRING_CONTENT", "EscapeSequence", "OctalEscape", 
		"UnicodeEscape", "HEX", "StartChar", "WS", "NL"
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


	public ReportParserLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "ReportParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2(\u0165\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\13\3\13"+
		"\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\20\3\21\3"+
		"\21\6\21\u008c\n\21\r\21\16\21\u008d\3\22\3\22\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u00ae\n\23\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\5\24\u00b7\n\24\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\5\25\u00c2\n\25\3\26\3\26\3\27\3\27\3\30\3\30\3\30\3\30"+
		"\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\5\33"+
		"\u00d9\n\33\3\34\3\34\3\34\3\34\3\34\5\34\u00e0\n\34\3\35\3\35\3\35\3"+
		"\35\5\35\u00e6\n\35\3\36\5\36\u00e9\n\36\3\36\6\36\u00ec\n\36\r\36\16"+
		"\36\u00ed\3\37\5\37\u00f1\n\37\3\37\6\37\u00f4\n\37\r\37\16\37\u00f5\3"+
		"\37\3\37\6\37\u00fa\n\37\r\37\16\37\u00fb\3\37\5\37\u00ff\n\37\3\37\5"+
		"\37\u0102\n\37\3\37\6\37\u0105\n\37\r\37\16\37\u0106\3\37\3\37\3\37\5"+
		"\37\u010c\n\37\3\37\6\37\u010f\n\37\r\37\16\37\u0110\5\37\u0113\n\37\3"+
		" \3 \3!\3!\5!\u0119\n!\3!\6!\u011c\n!\r!\16!\u011d\3\"\6\"\u0121\n\"\r"+
		"\"\16\"\u0122\3#\3#\7#\u0127\n#\f#\16#\u012a\13#\3$\3$\3$\3$\5$\u0130"+
		"\n$\3%\3%\3&\3&\7&\u0136\n&\f&\16&\u0139\13&\3\'\3\'\3\'\3\'\5\'\u013f"+
		"\n\'\3(\3(\3(\3(\3(\3(\3(\3(\3(\5(\u014a\n(\3)\3)\3)\3)\3)\3)\3)\3*\3"+
		"*\3+\5+\u0156\n+\3,\6,\u0159\n,\r,\16,\u015a\3,\3,\3-\5-\u0160\n-\3-\3"+
		"-\3-\3-\2\2.\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16"+
		"\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34"+
		"\67\359\36;\37= ?!A\"C#E$G%I&K\2M\2O\2Q\2S\2U\2W\'Y(\3\2\17\6\2\'\',-"+
		"//\61\61\4\2>>@@\4\2GGgg\4\2--//\3\2C\\\4\2//aa\5\2\u00b9\u00b9\u0302"+
		"\u0371\u2041\u2042\3\2\62;\4\2$$))\n\2$$))^^ddhhppttvv\5\2\62;CHch\t\2"+
		"C\\c|\u2072\u2191\u2c02\u2ff1\u3003\ud801\uf902\ufdd1\ufdf2\uffff\5\2"+
		"\13\f\17\17\"\"\u0187\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2"+
		"\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25"+
		"\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2"+
		"\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2"+
		"\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3"+
		"\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2"+
		"\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\3[\3\2\2\2\5"+
		"]\3\2\2\2\7b\3\2\2\2\td\3\2\2\2\13f\3\2\2\2\rm\3\2\2\2\17p\3\2\2\2\21"+
		"r\3\2\2\2\23w\3\2\2\2\25y\3\2\2\2\27{\3\2\2\2\31\u0080\3\2\2\2\33\u0082"+
		"\3\2\2\2\35\u0084\3\2\2\2\37\u0086\3\2\2\2!\u0089\3\2\2\2#\u008f\3\2\2"+
		"\2%\u00ad\3\2\2\2\'\u00b6\3\2\2\2)\u00c1\3\2\2\2+\u00c3\3\2\2\2-\u00c5"+
		"\3\2\2\2/\u00c7\3\2\2\2\61\u00cc\3\2\2\2\63\u00ce\3\2\2\2\65\u00d8\3\2"+
		"\2\2\67\u00df\3\2\2\29\u00e5\3\2\2\2;\u00e8\3\2\2\2=\u0112\3\2\2\2?\u0114"+
		"\3\2\2\2A\u0116\3\2\2\2C\u0120\3\2\2\2E\u0124\3\2\2\2G\u012f\3\2\2\2I"+
		"\u0131\3\2\2\2K\u0137\3\2\2\2M\u013e\3\2\2\2O\u0149\3\2\2\2Q\u014b\3\2"+
		"\2\2S\u0152\3\2\2\2U\u0155\3\2\2\2W\u0158\3\2\2\2Y\u015f\3\2\2\2[\\\7"+
		"A\2\2\\\4\3\2\2\2]^\7e\2\2^_\7c\2\2_`\7u\2\2`a\7g\2\2a\6\3\2\2\2bc\7}"+
		"\2\2c\b\3\2\2\2de\7\177\2\2e\n\3\2\2\2fg\7t\2\2gh\7g\2\2hi\7v\2\2ij\7"+
		"w\2\2jk\7t\2\2kl\7p\2\2l\f\3\2\2\2mn\7k\2\2no\7h\2\2o\16\3\2\2\2pq\7="+
		"\2\2q\20\3\2\2\2rs\7g\2\2st\7n\2\2tu\7u\2\2uv\7g\2\2v\22\3\2\2\2wx\7("+
		"\2\2x\24\3\2\2\2yz\7&\2\2z\26\3\2\2\2{|\7e\2\2|}\7g\2\2}~\7n\2\2~\177"+
		"\7n\2\2\177\30\3\2\2\2\u0080\u0081\7\60\2\2\u0081\32\3\2\2\2\u0082\u0083"+
		"\7]\2\2\u0083\34\3\2\2\2\u0084\u0085\7_\2\2\u0085\36\3\2\2\2\u0086\u0087"+
		"\7v\2\2\u0087\u0088\7q\2\2\u0088 \3\2\2\2\u0089\u008b\5C\"\2\u008a\u008c"+
		"\5I%\2\u008b\u008a\3\2\2\2\u008c\u008d\3\2\2\2\u008d\u008b\3\2\2\2\u008d"+
		"\u008e\3\2\2\2\u008e\"\3\2\2\2\u008f\u0090\t\2\2\2\u0090$\3\2\2\2\u0091"+
		"\u00ae\t\3\2\2\u0092\u0093\7?\2\2\u0093\u00ae\7?\2\2\u0094\u0095\7#\2"+
		"\2\u0095\u00ae\7?\2\2\u0096\u0097\7@\2\2\u0097\u00ae\7?\2\2\u0098\u0099"+
		"\7>\2\2\u0099\u00ae\7?\2\2\u009a\u009b\7k\2\2\u009b\u00ae\7p\2\2\u009c"+
		"\u009d\7p\2\2\u009d\u009e\7q\2\2\u009e\u009f\7v\2\2\u009f\u00a0\7\"\2"+
		"\2\u00a0\u00a1\7k\2\2\u00a1\u00ae\7p\2\2\u00a2\u00a3\7p\2\2\u00a3\u00a4"+
		"\7q\2\2\u00a4\u00a5\7v\2\2\u00a5\u00a6\7\"\2\2\u00a6\u00a7\7\"\2\2\u00a7"+
		"\u00a8\7k\2\2\u00a8\u00ae\7p\2\2\u00a9\u00aa\7n\2\2\u00aa\u00ab\7k\2\2"+
		"\u00ab\u00ac\7m\2\2\u00ac\u00ae\7g\2\2\u00ad\u0091\3\2\2\2\u00ad\u0092"+
		"\3\2\2\2\u00ad\u0094\3\2\2\2\u00ad\u0096\3\2\2\2\u00ad\u0098\3\2\2\2\u00ad"+
		"\u009a\3\2\2\2\u00ad\u009c\3\2\2\2\u00ad\u00a2\3\2\2\2\u00ad\u00a9\3\2"+
		"\2\2\u00ae&\3\2\2\2\u00af\u00b0\7f\2\2\u00b0\u00b1\7g\2\2\u00b1\u00b2"+
		"\7u\2\2\u00b2\u00b7\7e\2\2\u00b3\u00b4\7c\2\2\u00b4\u00b5\7u\2\2\u00b5"+
		"\u00b7\7e\2\2\u00b6\u00af\3\2\2\2\u00b6\u00b3\3\2\2\2\u00b7(\3\2\2\2\u00b8"+
		"\u00b9\7v\2\2\u00b9\u00ba\7t\2\2\u00ba\u00bb\7w\2\2\u00bb\u00c2\7g\2\2"+
		"\u00bc\u00bd\7h\2\2\u00bd\u00be\7c\2\2\u00be\u00bf\7n\2\2\u00bf\u00c0"+
		"\7u\2\2\u00c0\u00c2\7g\2\2\u00c1\u00b8\3\2\2\2\u00c1\u00bc\3\2\2\2\u00c2"+
		"*\3\2\2\2\u00c3\u00c4\7<\2\2\u00c4,\3\2\2\2\u00c5\u00c6\7.\2\2\u00c6."+
		"\3\2\2\2\u00c7\u00c8\7p\2\2\u00c8\u00c9\7w\2\2\u00c9\u00ca\7n\2\2\u00ca"+
		"\u00cb\7n\2\2\u00cb\60\3\2\2\2\u00cc\u00cd\7*\2\2\u00cd\62\3\2\2\2\u00ce"+
		"\u00cf\7+\2\2\u00cf\64\3\2\2\2\u00d0\u00d1\7$\2\2\u00d1\u00d2\5K&\2\u00d2"+
		"\u00d3\7$\2\2\u00d3\u00d9\3\2\2\2\u00d4\u00d5\7)\2\2\u00d5\u00d6\5K&\2"+
		"\u00d6\u00d7\7)\2\2\u00d7\u00d9\3\2\2\2\u00d8\u00d0\3\2\2\2\u00d8\u00d4"+
		"\3\2\2\2\u00d9\66\3\2\2\2\u00da\u00db\7c\2\2\u00db\u00dc\7p\2\2\u00dc"+
		"\u00e0\7f\2\2\u00dd\u00de\7(\2\2\u00de\u00e0\7(\2\2\u00df\u00da\3\2\2"+
		"\2\u00df\u00dd\3\2\2\2\u00e08\3\2\2\2\u00e1\u00e2\7q\2\2\u00e2\u00e6\7"+
		"t\2\2\u00e3\u00e4\7~\2\2\u00e4\u00e6\7~\2\2\u00e5\u00e1\3\2\2\2\u00e5"+
		"\u00e3\3\2\2\2\u00e6:\3\2\2\2\u00e7\u00e9\7/\2\2\u00e8\u00e7\3\2\2\2\u00e8"+
		"\u00e9\3\2\2\2\u00e9\u00eb\3\2\2\2\u00ea\u00ec\5I%\2\u00eb\u00ea\3\2\2"+
		"\2\u00ec\u00ed\3\2\2\2\u00ed\u00eb\3\2\2\2\u00ed\u00ee\3\2\2\2\u00ee<"+
		"\3\2\2\2\u00ef\u00f1\7/\2\2\u00f0\u00ef\3\2\2\2\u00f0\u00f1\3\2\2\2\u00f1"+
		"\u00f3\3\2\2\2\u00f2\u00f4\5I%\2\u00f3\u00f2\3\2\2\2\u00f4\u00f5\3\2\2"+
		"\2\u00f5\u00f3\3\2\2\2\u00f5\u00f6\3\2\2\2\u00f6\u00f7\3\2\2\2\u00f7\u00f9"+
		"\7\60\2\2\u00f8\u00fa\5I%\2\u00f9\u00f8\3\2\2\2\u00fa\u00fb\3\2\2\2\u00fb"+
		"\u00f9\3\2\2\2\u00fb\u00fc\3\2\2\2\u00fc\u00fe\3\2\2\2\u00fd\u00ff\5A"+
		"!\2\u00fe\u00fd\3\2\2\2\u00fe\u00ff\3\2\2\2\u00ff\u0113\3\2\2\2\u0100"+
		"\u0102\7/\2\2\u0101\u0100\3\2\2\2\u0101\u0102\3\2\2\2\u0102\u0104\3\2"+
		"\2\2\u0103\u0105\5I%\2\u0104\u0103\3\2\2\2\u0105\u0106\3\2\2\2\u0106\u0104"+
		"\3\2\2\2\u0106\u0107\3\2\2\2\u0107\u0108\3\2\2\2\u0108\u0109\5A!\2\u0109"+
		"\u0113\3\2\2\2\u010a\u010c\7/\2\2\u010b\u010a\3\2\2\2\u010b\u010c\3\2"+
		"\2\2\u010c\u010e\3\2\2\2\u010d\u010f\5I%\2\u010e\u010d\3\2\2\2\u010f\u0110"+
		"\3\2\2\2\u0110\u010e\3\2\2\2\u0110\u0111\3\2\2\2\u0111\u0113\3\2\2\2\u0112"+
		"\u00f0\3\2\2\2\u0112\u0101\3\2\2\2\u0112\u010b\3\2\2\2\u0113>\3\2\2\2"+
		"\u0114\u0115\7#\2\2\u0115@\3\2\2\2\u0116\u0118\t\4\2\2\u0117\u0119\t\5"+
		"\2\2\u0118\u0117\3\2\2\2\u0118\u0119\3\2\2\2\u0119\u011b\3\2\2\2\u011a"+
		"\u011c\5I%\2\u011b\u011a\3\2\2\2\u011c\u011d\3\2\2\2\u011d\u011b\3\2\2"+
		"\2\u011d\u011e\3\2\2\2\u011eB\3\2\2\2\u011f\u0121\t\6\2\2\u0120\u011f"+
		"\3\2\2\2\u0121\u0122\3\2\2\2\u0122\u0120\3\2\2\2\u0122\u0123\3\2\2\2\u0123"+
		"D\3\2\2\2\u0124\u0128\5U+\2\u0125\u0127\5G$\2\u0126\u0125\3\2\2\2\u0127"+
		"\u012a\3\2\2\2\u0128\u0126\3\2\2\2\u0128\u0129\3\2\2\2\u0129F\3\2\2\2"+
		"\u012a\u0128\3\2\2\2\u012b\u0130\5U+\2\u012c\u0130\t\7\2\2\u012d\u0130"+
		"\5I%\2\u012e\u0130\t\b\2\2\u012f\u012b\3\2\2\2\u012f\u012c\3\2\2\2\u012f"+
		"\u012d\3\2\2\2\u012f\u012e\3\2\2\2\u0130H\3\2\2\2\u0131\u0132\t\t\2\2"+
		"\u0132J\3\2\2\2\u0133\u0136\5M\'\2\u0134\u0136\n\n\2\2\u0135\u0133\3\2"+
		"\2\2\u0135\u0134\3\2\2\2\u0136\u0139\3\2\2\2\u0137\u0135\3\2\2\2\u0137"+
		"\u0138\3\2\2\2\u0138L\3\2\2\2\u0139\u0137\3\2\2\2\u013a\u013b\7^\2\2\u013b"+
		"\u013f\t\13\2\2\u013c\u013f\5Q)\2\u013d\u013f\5O(\2\u013e\u013a\3\2\2"+
		"\2\u013e\u013c\3\2\2\2\u013e\u013d\3\2\2\2\u013fN\3\2\2\2\u0140\u0141"+
		"\7^\2\2\u0141\u0142\4\62\65\2\u0142\u0143\4\629\2\u0143\u014a\4\629\2"+
		"\u0144\u0145\7^\2\2\u0145\u0146\4\629\2\u0146\u014a\4\629\2\u0147\u0148"+
		"\7^\2\2\u0148\u014a\4\629\2\u0149\u0140\3\2\2\2\u0149\u0144\3\2\2\2\u0149"+
		"\u0147\3\2\2\2\u014aP\3\2\2\2\u014b\u014c\7^\2\2\u014c\u014d\7w\2\2\u014d"+
		"\u014e\5S*\2\u014e\u014f\5S*\2\u014f\u0150\5S*\2\u0150\u0151\5S*\2\u0151"+
		"R\3\2\2\2\u0152\u0153\t\f\2\2\u0153T\3\2\2\2\u0154\u0156\t\r\2\2\u0155"+
		"\u0154\3\2\2\2\u0156V\3\2\2\2\u0157\u0159\t\16\2\2\u0158\u0157\3\2\2\2"+
		"\u0159\u015a\3\2\2\2\u015a\u0158\3\2\2\2\u015a\u015b\3\2\2\2\u015b\u015c"+
		"\3\2\2\2\u015c\u015d\b,\2\2\u015dX\3\2\2\2\u015e\u0160\7\17\2\2\u015f"+
		"\u015e\3\2\2\2\u015f\u0160\3\2\2\2\u0160\u0161\3\2\2\2\u0161\u0162\7\f"+
		"\2\2\u0162\u0163\3\2\2\2\u0163\u0164\b-\2\2\u0164Z\3\2\2\2!\2\u008d\u00ad"+
		"\u00b6\u00c1\u00d8\u00df\u00e5\u00e8\u00ed\u00f0\u00f5\u00fb\u00fe\u0101"+
		"\u0106\u010b\u0110\u0112\u0118\u011d\u0122\u0128\u012f\u0135\u0137\u013e"+
		"\u0149\u0155\u015a\u015f\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}