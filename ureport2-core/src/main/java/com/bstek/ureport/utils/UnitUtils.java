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
package com.bstek.ureport.utils;

import com.bstek.ureport.Utils;

/**
 * @author Jacky.gao
 * @since 2017年3月16日
 */
public class UnitUtils {
	public static int pointToPixel(double point){
		double value=point * 1.33;
		return Utils.toBigDecimal(value).intValue();
	}
	public static int pixelToPoint(double pixel){
		double value=pixel * 0.75;
		return Utils.toBigDecimal(value).intValue();
	}
	public static final float pointToInche(final float value) {
	    return value / 72f;
	}
	public static int pointToTwip(int point) {
		return point * 20; 
	}
}
