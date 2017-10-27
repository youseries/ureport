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
package com.bstek.ureport.parser.impl.value;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

import com.bstek.ureport.definition.Order;
import com.bstek.ureport.definition.mapping.MappingItem;
import com.bstek.ureport.definition.mapping.MappingType;
import com.bstek.ureport.definition.value.AggregateType;
import com.bstek.ureport.definition.value.DatasetValue;
import com.bstek.ureport.definition.value.GroupItem;
import com.bstek.ureport.definition.value.Value;
import com.bstek.ureport.expression.ExpressionUtils;
import com.bstek.ureport.expression.model.Condition;
import com.bstek.ureport.expression.model.Op;
import com.bstek.ureport.expression.model.condition.Join;
import com.bstek.ureport.expression.model.condition.PropertyExpressionCondition;

/**
 * @author Jacky.gao
 * @since 2016年12月21日
 */
public class DatasetValueParser extends ValueParser {
	@Override
	public Value parse(Element element) {
		DatasetValue value=new DatasetValue();
		value.setAggregate(AggregateType.valueOf(element.attributeValue("aggregate")));
		value.setDatasetName(element.attributeValue("dataset-name"));
		value.setProperty(element.attributeValue("property"));
		String order=element.attributeValue("order");
		if(StringUtils.isNotBlank(order)){
			value.setOrder(Order.valueOf(order));
		}
		String mappingType=element.attributeValue("mapping-type");
		if(StringUtils.isNotBlank(mappingType)){
			value.setMappingType(MappingType.valueOf(mappingType));
		}
		value.setMappingDataset(element.attributeValue("mapping-dataset"));
		value.setMappingKeyProperty(element.attributeValue("mapping-key-property"));
		value.setMappingValueProperty(element.attributeValue("mapping-value-property"));
		List<GroupItem> groupItems=null;
		List<MappingItem> mappingItems=null;
		List<Condition> conditions=new ArrayList<Condition>();
		PropertyExpressionCondition topCondition=null;
		value.setConditions(conditions);
		for(Object obj:element.elements()){
			if(obj==null || !(obj instanceof Element)){
				continue;
			}
			Element ele=(Element)obj;
			if(ele.getName().equals("condition")){
				PropertyExpressionCondition condition = parseCondition(ele);
				conditions.add(condition);
				if(topCondition==null){
					topCondition=condition;
				}else{
					topCondition.setNextCondition(condition);
					topCondition.setJoin(condition.getJoin());
				}				
			}else if(ele.getName().equals("group-item")){
				if(groupItems==null){
					groupItems=new ArrayList<GroupItem>();
					value.setGroupItems(groupItems);
				}
				GroupItem item=new GroupItem();
				item.setName(ele.attributeValue("name"));
				groupItems.add(item);
				PropertyExpressionCondition groupItemTopCondition=null;
				List<Condition> itemConditions=new ArrayList<Condition>();
				for(Object o:ele.elements()){
					if(o==null || !(o instanceof Element)){
						continue;
					}
					PropertyExpressionCondition itemCondition=parseCondition((Element)o);
					itemConditions.add(itemCondition);
					if(groupItemTopCondition==null){
						groupItemTopCondition=itemCondition;
					}else{
						groupItemTopCondition.setNextCondition(itemCondition);
					}
				}
				item.setCondition(groupItemTopCondition);
				item.setConditions(itemConditions);
			}else if(ele.getName().equals("mapping-item")){
				MappingItem item=new MappingItem();
				item.setLabel(ele.attributeValue("label"));
				item.setValue(ele.attributeValue("value"));
				if(mappingItems==null){
					mappingItems=new ArrayList<MappingItem>();
				}
				mappingItems.add(item);
			}
		}
		if(topCondition!=null){
			value.setCondition(topCondition);
		}
		if(mappingItems!=null){
			value.setMappingItems(mappingItems);
		}
		return value;
	}

	private PropertyExpressionCondition parseCondition(Element ele) {
		PropertyExpressionCondition condition=new PropertyExpressionCondition();				
		String property=ele.attributeValue("property");
		condition.setLeftProperty(property);
		condition.setLeft(property);
		condition.setOp(Op.parse(ele.attributeValue("op")));
		for(Object o:ele.elements()){
			if(o==null || !(o instanceof Element)){
				continue;
			}
			Element e=(Element)o;
			if(!e.getName().equals("value")){
				continue;
			}
			String expr=e.getTextTrim();
			condition.setRightExpression(ExpressionUtils.parseExpression(expr));
			condition.setRight(expr);
			break;
		}
		String join=ele.attributeValue("join");
		if(StringUtils.isNotBlank(join)){
			condition.setJoin(Join.valueOf(join));
		}
		return condition;
	}
}
