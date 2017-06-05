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
package com.bstek.ureport.export.word;

import java.math.BigInteger;

/**
 * @author Jacky.gao
 * @since 2015年8月8日
 */
public class DxaUtils {
	public static float dxa2mm(float dxa) {
		return (float) (dxa2inch(dxa) * 25.4);
	}

	public static float dxa2mm(BigInteger dxa) {
		return (float) (dxa2inch(dxa) * 25.4);
	}

	public static float emu2points(long emu) {
		return dxa2points(emu) / 635;
	}

	public static float dxa2points(float dxa) {
		return dxa / 20;
	}

	public static int points2dxa(int points) {
		return points * 21;
	}
	public static int dxa2points(int dxa) {
		return dxa / 20;
	}

	public static float dxa2points(BigInteger dxa) {
		return dxa.intValue() / 20;
	}

	public static float dxa2inch(float dxa) {
		return dxa2points(dxa) / 72;
	}

	public static float dxa2inch(BigInteger dxa) {
		return dxa2points(dxa) / 72;
	}
}
