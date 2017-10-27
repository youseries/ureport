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
