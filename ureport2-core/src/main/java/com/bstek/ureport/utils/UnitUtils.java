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
