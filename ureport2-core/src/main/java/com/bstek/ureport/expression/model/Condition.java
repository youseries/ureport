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
package com.bstek.ureport.expression.model;

import com.bstek.ureport.build.Context;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2016年11月18日
 */
public interface Condition {
	/**
	 * @param cell 当前Condition所在的单元格
	 * @param currentCell 当前Condition正在处理的单元格
	 * @param obj 要判断的对象
	 * @param context 上下文对象
	 * @return 返回是否符合条件
	 */
	boolean filter(Cell cell,Cell currentCell,Object obj,Context context);
}
