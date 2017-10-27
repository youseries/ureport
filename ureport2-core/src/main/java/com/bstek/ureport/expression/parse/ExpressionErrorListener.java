/*******************************************************************************
 * Copyright (C) 2017 Bstek.com
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.bstek.ureport.expression.parse;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

/**
 * @author Jacky.gao
 * @since 2016年12月6日
 */
public class ExpressionErrorListener extends BaseErrorListener {
	private StringBuffer sb;
	@Override
	public void syntaxError(Recognizer<?, ?> recognizer,Object offendingSymbol, 
			int line, int charPositionInLine,
			String msg, RecognitionException e) {
		if(sb==null){
			sb=new StringBuffer();
		}
		sb.append("["+offendingSymbol+"] is invalid:"+msg);
		sb.append("\r\n");
	}
	public String getErrorMessage(){
		if(sb==null){
			return null;
		}
		return sb.toString();
	}
}
