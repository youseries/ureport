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
package com.bstek.ureport.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;

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
import com.bstek.ureport.Utils;

/**
 * @author Jacky.gao
 * @since 2018年6月28日
 */
public class ElCompute {
	private Stack<Object> dataStack=new Stack<Object>();
	private Stack<Character> operateStack=new Stack<Character>();
	public static void main(String[] args) {
		long start=System.currentTimeMillis();
		String expr="(2+20)*3-10+\"super man\"";
		for(int i=0;i<1;i++) {
			ElCompute el=new ElCompute();
			Object data=el.doCompute(expr);
			System.out.println(data);
		}
		long end=System.currentTimeMillis();
		System.out.println(end-start);
	}
	
	public Object doCompute(String expr) {
		init(expr);
		return dataStack.pop();
	}
	private void init(String expr){
		StringBuilder dataSb=new StringBuilder();
		char prevQuote='0';
		for(int i=0;i<expr.length();i++){
			char c=expr.charAt(i);
			switch(c){
			case '+':
				addDataStack(dataSb);
				doCalculate(0);
				operateStack.push(c);
				break;
			case '-':
				doMinus(dataSb, prevQuote);
				break;
			case '*':
				addDataStack(dataSb);
				doCalculate(2);
				operateStack.push(c);
				break;
			case '/':
				addDataStack(dataSb);
				doCalculate(2);
				operateStack.push(c);
				break;
			case '%':
				addDataStack(dataSb);
				doCalculate(2);
				operateStack.push(c);
				break;
			case '(':
				operateStack.push(c);
				break;
			case ')':
				addDataStack(dataSb);
				doCalculate(1);
				break;
			case '"':
				if(prevQuote=='"'){
					prevQuote='0';
					dataStack.push(dataSb.toString());
					dataSb.setLength(0);
				}else{
					prevQuote='"';
				}
				break;
			case ' ':
				if(prevQuote=='"') {
					dataSb.append(c);
				}
				break;
			default:
				dataSb.append(c);
			}
		}
		if(dataSb.length()>0){
			addDataStack(dataSb);
		}
		doCalculate(0);
	}
	
	private void doMinus(StringBuilder dataSb,char prevQuote){
		if(dataSb.length()==0){
			dataSb.append('-');
		}else{
			addDataStack(dataSb);				
			doCalculate(0);
			operateStack.push('-');
		}
	}
		
	
	private void doCalculate(int current) {
		if(operateStack.empty()){
			return;
		}
		char prevOp=operateStack.peek();
		if(prevOp=='('){
			return;
		}
		if(current==0 || current==1){
			char op=operateStack.pop();
			do{				
				Object right=dataStack.pop();
				Object left=dataStack.pop();
				Object result=calculate(left, op, right);
				dataStack.push(result);
				if(operateStack.isEmpty()){
					break;
				}
				op=operateStack.pop();
			}while(op!='(');
		}else if(current==2){
			while(prevOp=='*' || prevOp=='/' || prevOp=='%') {
				Object right=dataStack.pop();
				Object left=dataStack.pop();
				char op=operateStack.pop();
				Object result=calculate(left, op, right);
				dataStack.push(result);
				if(operateStack.isEmpty()){
					break;
				}
				prevOp=operateStack.peek();
				if(prevOp=='('){
					break;
				}
			}
		}
	}
	
	private Object calculate(Object left,char op,Object right){
		if((op=='*' || op=='/' || op=='%' || op=='-')){
			if(right instanceof String || left instanceof String){
				throw new RuntimeException(left + " and "+right+" can't do "+op+"!");				
			}
			BigDecimal b1=(BigDecimal)left;
			BigDecimal b2=(BigDecimal)right;
			if(op=='*'){
				return b1.multiply(b2);
			}else if(op=='/'){
				return b1.divide(b2,10,RoundingMode.HALF_UP).stripTrailingZeros();
			}else if(op=='%'){
				return b1.divideAndRemainder(b2)[1];
			}else if(op=='-'){
				return b1.subtract(b2);
			}
		}else if(op=='+'){
			if(right instanceof String || left instanceof String){
				return left.toString()+right.toString();
			}else{
				BigDecimal b1=(BigDecimal)left;
				BigDecimal b2=(BigDecimal)right;
				return b1.add(b2);
			}
		}
		throw new RuntimeException("Unkown operate "+op+"");
	}
	

	private void addDataStack(StringBuilder dataSb) {
		if(dataSb.length()==0)return;
		String data=dataSb.toString();
		dataSb.setLength(0);
		try{
			BigDecimal bd=Utils.toBigDecimal(data);
			dataStack.push(bd);
		}catch(Exception ex){
			dataStack.push(data);
		}
	}
}
