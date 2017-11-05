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
