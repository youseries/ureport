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
 * @since 2016年11月18日
 */
public enum Operator {
	Add,Subtract,Multiply,Divide,Complementation;
	public static Operator parse(String op){
		if(op.equals("+")){
			return Add;
		}else if(op.equals("-")){
			return Subtract;
		}else if(op.equals("*")){
			return Multiply;
		}else if(op.equals("/")){
			return Divide;
		}else if(op.equals("%")){
			return Complementation;
		}else{
			throw new ReportParseException("Unknow operator :"+op);
		}
	}
	@Override
	public String toString() {
		switch(this){
		case Add:
			return "+";
		case Divide:
			return "/";
		case Multiply:
			return "*";
		case Subtract:
			return "-";
		case Complementation:
			return "%";
		}
		throw new IllegalArgumentException("Unknow operator: ["+this+"]");
	};
}
