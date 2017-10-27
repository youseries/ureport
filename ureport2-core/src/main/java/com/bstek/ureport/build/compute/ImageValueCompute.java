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
package com.bstek.ureport.build.compute;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.bstek.ureport.build.BindData;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.definition.value.ImageValue;
import com.bstek.ureport.definition.value.Source;
import com.bstek.ureport.definition.value.ValueType;
import com.bstek.ureport.expression.model.Expression;
import com.bstek.ureport.expression.model.data.ExpressionData;
import com.bstek.ureport.image.ImageType;
import com.bstek.ureport.model.Cell;
import com.bstek.ureport.model.Image;
import com.bstek.ureport.utils.ImageUtils;

/**
 * @author Jacky.gao
 * @since 2017年1月24日
 */
public class ImageValueCompute implements ValueCompute{
	@Override
	public List<BindData> compute(Cell cell, Context context) {
		ImageValue value=(ImageValue)cell.getValue();
		Source source=value.getSource();
		List<BindData> list=new ArrayList<BindData>();
		if(source.equals(Source.text)){
			String base64Data=ImageUtils.getImageBase64Data(ImageType.image, value.getValue());
			list.add(new BindData(new Image(base64Data,value.getValue(),-1,-1)));			
		}else{
			Expression expression=value.getExpression();
			ExpressionData<?> data=expression.execute(cell,cell, context);
			Object obj=data.getData();
			if(obj instanceof List){
				List<?> listData=(List<?>)obj;
				for(Object o:listData){
					if(o==null){
						continue;
					}
					String path=null;
					if(o instanceof BindData){
						BindData bindData=(BindData)o;
						Object valueData=bindData.getValue();
						if(valueData!=null){
							path=valueData.toString();
						}
					}else{
						path=o.toString();
					}
					if(StringUtils.isBlank(path)){
						continue;
					}
					String base64Data=ImageUtils.getImageBase64Data(ImageType.image, path);
					list.add(new BindData(new Image(base64Data,path,-1,-1)));						
				}
			}else if(obj instanceof BindData){
				BindData bindData=(BindData)obj;
				String path=null;
				Object valueData=bindData.getValue();
				if(valueData!=null){
					path=valueData.toString();
				}
				if(StringUtils.isNotBlank(path)){
					String base64Data=ImageUtils.getImageBase64Data(ImageType.image, path);
					list.add(new BindData(new Image(base64Data,path,-1,-1)));
				}
			}else if(obj instanceof String){
				String text=obj.toString();
				if(text.startsWith("\"") && text.endsWith("\"")){
					text=text.substring(1,text.length()-1);
				}
				String base64Data=ImageUtils.getImageBase64Data(ImageType.image, text);
				list.add(new BindData(new Image(base64Data,text,-1,-1)));			
			}else{
				if(obj!=null && StringUtils.isNotBlank(obj.toString())){
					String base64Data=ImageUtils.getImageBase64Data(ImageType.image, obj.toString());
					list.add(new BindData(new Image(base64Data,obj.toString(),-1,-1)));					
				}
			}
		}
		return list;
	}
	@Override
	public ValueType type() {
		return ValueType.image;
	}
}
