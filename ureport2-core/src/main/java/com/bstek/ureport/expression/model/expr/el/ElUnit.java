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
package com.bstek.ureport.expression.model.expr.el;

import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.expression.model.Operator;
import com.bstek.ureport.utils.ArithUtils;

/**
 * @author Jacky.gao
 * @since 2017年4月25日
 */
public class ElUnit {
	private Object left;
	private Object right;
	private Operator op;
	private Operator nextOp;
	private ElUnit nextUnit;
	public Object compute(){
		if(right instanceof ElUnit){
			right=((ElUnit)right).compute();
		}
		switch(op){
		case Add:
			return ArithUtils.add(left, right);
		case Complementation:
			return ArithUtils.com(left, right);
		case Divide:
			return ArithUtils.div(left, right);
		case Multiply:
			return ArithUtils.mul(left, right);
		case Subtract:
			return ArithUtils.sub(left, right);
		}
		throw new ReportComputeException("Unknow operator :"+op);
	}
	public Object getLeft() {
		return left;
	}
	public void setLeft(Object left) {
		this.left = left;
	}
	public Object getRight() {
		return right;
	}
	public void setRight(Object right) {
		this.right = right;
	}
	public Operator getOp() {
		return op;
	}
	public void setOp(Operator op) {
		this.op = op;
	}
	public Operator getNextOp() {
		return nextOp;
	}
	public void setNextOp(Operator nextOp) {
		this.nextOp = nextOp;
	}
	public ElUnit getNextUnit() {
		return nextUnit;
	}
	public void setNextUnit(ElUnit nextUnit) {
		this.nextUnit = nextUnit;
	}
}
