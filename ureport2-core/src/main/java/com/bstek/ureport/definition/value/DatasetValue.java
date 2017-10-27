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
package com.bstek.ureport.definition.value;

import com.bstek.ureport.expression.model.expr.dataset.DatasetExpression;

/**
 * @author Jacky.gao
 * @since 2016年12月21日
 */
public class DatasetValue extends DatasetExpression implements Value{
	private static final long serialVersionUID = 1892973888854385049L;

	@Override
	public ValueType getType() {
		return ValueType.dataset;
	}
	
	@Override
	public String getValue() {
		StringBuffer sb=new StringBuffer();
		sb.append(getDatasetName());
		sb.append(".");
		sb.append(getAggregate().name());
		sb.append("(");
		String prop=getProperty();
		if(prop!=null){
			if(prop.length()>13){
				prop=prop.substring(0,10)+"...";
			}
			sb.append(prop);
		}
		sb.append(")");
		return sb.toString();
	}
}
