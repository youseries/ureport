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
package com.bstek.ureport.build;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bstek.ureport.build.aggregate.Aggregate;
import com.bstek.ureport.build.aggregate.AvgAggregate;
import com.bstek.ureport.build.aggregate.CountAggregate;
import com.bstek.ureport.build.aggregate.CustomGroupAggregate;
import com.bstek.ureport.build.aggregate.GroupAggregate;
import com.bstek.ureport.build.aggregate.MaxAggregate;
import com.bstek.ureport.build.aggregate.MinAggregate;
import com.bstek.ureport.build.aggregate.RegroupAggregate;
import com.bstek.ureport.build.aggregate.ReselectAggregate;
import com.bstek.ureport.build.aggregate.SelectAggregate;
import com.bstek.ureport.build.aggregate.SumAggregate;
import com.bstek.ureport.definition.value.AggregateType;
import com.bstek.ureport.exception.CellComputeException;
import com.bstek.ureport.expression.model.expr.dataset.DatasetExpression;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2016年12月26日
 */
public class DatasetUtils {
	private static Map<AggregateType,Aggregate> aggregates=new HashMap<AggregateType,Aggregate>();
	static{
		aggregates.put(AggregateType.group,new GroupAggregate());
		aggregates.put(AggregateType.select,new SelectAggregate());
		aggregates.put(AggregateType.reselect,new ReselectAggregate());
		aggregates.put(AggregateType.regroup,new RegroupAggregate());
		aggregates.put(AggregateType.avg,new AvgAggregate());
		aggregates.put(AggregateType.count,new CountAggregate());
		aggregates.put(AggregateType.sum,new SumAggregate());
		aggregates.put(AggregateType.min,new MinAggregate());
		aggregates.put(AggregateType.max,new MaxAggregate());
		aggregates.put(AggregateType.customgroup,new CustomGroupAggregate());
	}
	
	public static List<BindData> computeDatasetExpression(DatasetExpression expr,Cell cell,Context context){
		AggregateType aggregateType=expr.getAggregate();
		Aggregate aggregate=aggregates.get(aggregateType);
		if(aggregate!=null){
			return aggregate.aggregate(expr,cell,context);
		}else{			
			throw new CellComputeException("Unknow aggregate : "+aggregateType);
		}
	}
}
