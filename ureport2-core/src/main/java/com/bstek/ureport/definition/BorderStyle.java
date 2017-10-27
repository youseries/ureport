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
package com.bstek.ureport.definition;
/**
 * @author Jacky.gao
 * @since 2014年3月31日
 */
public enum BorderStyle {
	solid,dashed,doublesolid;
	public static BorderStyle toBorderStyle(String name){
		if(name.equals("double")){
			return BorderStyle.doublesolid;
		}else{
			return BorderStyle.valueOf(name);
		}
	}
	@Override
	public String toString() {
		if(this.equals(BorderStyle.doublesolid)){
			return "double";
		}
		return super.toString();
	}
}
