lexer grammar ReportLexer;

Cell : LETTER DIGIT+ ;

Operator : '+'
		 | '-'
		 | '*'
		 | '/'
		 | '%' 
		 ;
		
OP : '>'
   | '<' 
   | '=='
   | '!='
   | '>='
   | '<='
   | 'in'
   | 'not in'
   | 'not  in'
   | 'like'
   ;

   
ORDER : 'desc' | 'asc' ;

BOOLEAN : 'true' | 'false' ;

COLON : ':';

COMMA : ',' ;

NULL : 'null';

LeftParen : '(' ;

RightParen : ')' ;

STRING : '"' STRING_CONTENT '"' 
	   | '\'' STRING_CONTENT '\''
	   ;

AND : 'and' | '&&' ;

OR : 'or' | '||' ;

INTEGER : ('-')? DIGIT+;

NUMBER
:
	'-'? DIGIT+ '.' DIGIT+ EXP? // ('-'? INT '.' INT EXP?)1.35, 1.35E-9, 0.3, -4.5
	| '-'? DIGIT+ EXP // 1e10 -3e4
	| '-'? DIGIT+ // -3, 45
;

EXCLAMATION : '!';

EXP
:
	[Ee] [+\-]? DIGIT+
;

Identifier : StartChar Char* ;

LETTER : [A-Z]+ ;

Char    :   StartChar
            |   '-' | '_' | DIGIT 
            |   '\u00B7'
            |   '\u0300'..'\u036F'
            |   '\u203F'..'\u2040'
            ;

DIGIT : [0-9];      


fragment
STRING_CONTENT : 
	( EscapeSequence | ~('"'|'\''))*
;

fragment
EscapeSequence
    :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
    |   UnicodeEscape
    |   OctalEscape
    ;

fragment
OctalEscape
    :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7')
    ;

fragment
UnicodeEscape
:
	'\\' 'u' HEX HEX HEX HEX
;

fragment
HEX
:
	[0-9a-fA-F]
;      

fragment
StartChar
            :   [a-zA-Z]
            |   '\u2070'..'\u218F' 
            |   '\u2C00'..'\u2FEF' 
            |   '\u3001'..'\uD7FF' 
            |   '\uF900'..'\uFDCF' 
            |   '\uFDF0'..'\uFFFD'
            ;

WS
:
	[ \t\r\n]+ -> channel(HIDDEN)
;
 
NL
:
    '\r'? '\n' ->channel(HIDDEN)
;
