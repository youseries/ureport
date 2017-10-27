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
package com.bstek.ureport.build.assertor;

import java.math.BigDecimal;

import com.bstek.ureport.Utils;

/**
 * @author Jacky.gao
 * @since 2017年1月12日
 */
public class EqualsAssertor extends AbstractAssertor {
	@Override
	public boolean eval(Object left, Object right) {
		if(left == null && right==null){
			return true;
		}
		if(left==null || right==null){
			return false;
		}
		if(left instanceof Number && right instanceof Number){
			BigDecimal b1=Utils.toBigDecimal(left);
			BigDecimal b2=Utils.toBigDecimal(right);
			return b1.compareTo(b2)==0;
		}else if(left instanceof Number){
			BigDecimal b1=Utils.toBigDecimal(left);
			BigDecimal b2=null;
			try{
				b2=Utils.toBigDecimal(right);
			}catch(Exception ex){}
			if(b2!=null){
				return b1.compareTo(b2)==0;
			}
		}else if(right instanceof Number){
			BigDecimal b1=Utils.toBigDecimal(right);
			BigDecimal b2=null;
			try{
				b2=Utils.toBigDecimal(left);
			}catch(Exception ex){}
			if(b2!=null){
				return b1.compareTo(b2)==0;
			}
		}
		right=buildObject(right);
		return left.equals(right);
	}
}
