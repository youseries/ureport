grammar ReportParser;
import ReportLexer;

expression : exprComposite
		   | ifExpr
		   | caseExpr
		   ;

exprComposite : expr 										#singleExprComposite
			  | ternaryExpr									#ternaryExprComposite
			  | LeftParen exprComposite RightParen 			#parenExprComposite
			  | exprComposite Operator exprComposite		#complexExprComposite
			  ;

ternaryExpr : ifCondition (join ifCondition)* '?' expr ':' expr ; 

caseExpr : 'case' '{' casePart (',' casePart)* '}' ;

casePart : ifCondition (join ifCondition)* ':'? 'return'? expr ;

ifExpr: ifPart elseIfPart* elsePart? ;

ifPart : 'if' '(' ifCondition (join ifCondition)* ')' '{' 'return'? expr ';'? '}';

elseIfPart : 'else' 'if' '(' ifCondition (join ifCondition)* ')' '{' 'return'? expr ';'? '}' ;

elsePart : 'else' '{' 'return'? expr ';'? '}' ;

expr : item (Operator item)* ;

ifCondition : expr OP expr ;

item : unit (Operator unit)*							#simpleJoin
	 | LeftParen item RightParen						#singleParenJoin
     | LeftParen item (Operator item)+ RightParen		#parenJoin
     ;

unit : dataset
	 | function
	 | set
	 | cellPosition
	 | relativeCell
	 | currentCellValue
	 | currentCellData
	 | cell
	 | INTEGER
	 | BOOLEAN
	 | STRING
	 | NUMBER
	 | NULL
	 ;

cellPosition : '&'Cell ;//表示单元格位置

relativeCell : '$'Cell ; //表示当前引用对应的单元格的值

currentCellValue : '#' ;//表示当前单元格值

currentCellData : '#''.'property ;//表示取当前单元绑定对象的某个属性值

cell : 'cell' ('.'property)? ;

dataset : Identifier '.' aggregate '(' property? (',' conditions )? (',' ORDER)? ')';

function : Identifier '(' functionParameter? ')' ;

functionParameter : set (','? set)* ;

set : simpleValue										#simpleData
	| Cell												#singleCell
	| Cell '['']'('{' conditions '}')?					#wholeCell 
	| Cell ':' Cell										#cellPair
	| Cell '{' conditions '}'							#singleCellCondition
	| Cell '[' cellCoordinate ']'						#singleCellCoordinate
	| Cell '[' cellCoordinate ']' '{' conditions '}'	#cellCoordinateCondition
	| set 'to' set										#range
	;

cellCoordinate : coordinate (';' coordinate)? ;

coordinate : cellIndicator (',' cellIndicator)* ;

cellIndicator : Cell									#relative
			  | Cell ':' EXCLAMATION? INTEGER			#absolute
			  ;
	
conditions : condition (join condition)* ;
		
condition : Cell OP expr 				#cellNameExprCondition
		  | property OP expr			#propertyCondition
		  | expr OP expr				#exprCondition
		  ;	  

property : Identifier 
		 | property '.' property
		 ;

simpleValue : INTEGER|NUMBER|STRING|BOOLEAN|NULL;

join : AND | OR ;

aggregate : Identifier;