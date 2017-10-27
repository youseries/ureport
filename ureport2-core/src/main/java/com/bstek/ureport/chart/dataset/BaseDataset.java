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
package com.bstek.ureport.chart.dataset;


/**
 * @author Jacky.gao
 * @since 2017年6月9日
 */
public abstract class BaseDataset implements Dataset {
	protected String getRgbColor(int index){
		String[] COLORS={"255, 99, 132","54, 162, 235","255, 205, 86","75, 192, 192",
				"255, 159, 64","153,102,255","53,202,25","201,203,207","205,92,92",
				"255,127,80","173,255,47","127,255,0","138,43,226","147,112,219",
				"198,226,255","rbg(154,50,205","193,205,205","238,238,224",
				"24,116,205","137,104,205","180,82,205","205,50,120","205,0,0","148,0,211"};
		if(index>=COLORS.length){
			index=index % COLORS.length;
		}
		return COLORS[index];
	}
}
