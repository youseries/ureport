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
package com.bstek.ureport.expression.model;

import com.bstek.ureport.exception.ReportParseException;

/**
 * @author Jacky.gao
 * @since 2016年11月22日
 */
public enum Op {
	GreatThen,EqualsGreatThen,LessThen,EqualsLessThen,Equals,NotEquals,In,NotIn,Like;
	public static Op parse(String op){
		op=op.trim();
		if(op.equals(">")){
			return GreatThen;
		}else if(op.equals(">=")){
			return EqualsGreatThen;
		}else if(op.equals("==")){
			return Equals;
		}else if(op.equals("<")){
			return LessThen;
		}else if(op.equals("<=")){
			return EqualsLessThen;
		}else if(op.equals("!=")){
			return NotEquals;
		}else if(op.equals("in")){
			return In;
		}else if(op.equals("not in") || op.equals("not  in")){
			return NotIn;
		}else if(op.equals("like")){
			return Like;
		}
		throw new ReportParseException("Unknow op :"+op);
	}
	@Override
	public String toString() {
		switch(this){
		case GreatThen:
			return ">";
		case EqualsGreatThen:
			return ">=";
		case LessThen:
			return "<";
		case EqualsLessThen:
			return "<=";
		case Equals:
			return "==";
		case NotEquals:
			return "!=";
		case In:
			return " in ";
		case NotIn:
			return " not in ";
		case Like:
			return " like ";
		}
		return super.toString();
	}
}
