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
package com.bstek.ureport.expression;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

/**
 * @author Jacky.gao
 * @since 2016年7月26日
 */
public class ScriptErrorListener extends BaseErrorListener {
	private List<ErrorInfo> infos=new ArrayList<ErrorInfo>();
	@Override
	public void syntaxError(Recognizer<?, ?> recognizer,Object offendingSymbol, int line, int charPositionInLine,String msg, RecognitionException e) {
		infos.add(new ErrorInfo(line,charPositionInLine,msg));
	}
	public List<ErrorInfo> getInfos() {
		return infos;
	}
}
