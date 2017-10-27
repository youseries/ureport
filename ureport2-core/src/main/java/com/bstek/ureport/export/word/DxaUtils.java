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
