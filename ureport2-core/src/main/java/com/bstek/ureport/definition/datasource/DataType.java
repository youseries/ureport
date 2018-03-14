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
package com.bstek.ureport.definition.datasource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bstek.ureport.Utils;
import com.bstek.ureport.exception.ReportComputeException;

/**
 * @author Jacky.gao
 * @since 2016年12月27日
 */
public enum DataType {
	Integer,Float,Boolean,String,Date,List;
	public Object parse(Object obj){
		if(obj==null)return null;
		switch(this){
		case Boolean:
			if(obj.toString().equals("")){
				return null;
			}
			if(obj instanceof Boolean){
				return (Boolean)obj;
			}else{
				return java.lang.Boolean.valueOf(obj.toString());
			}
		case Float:
			if(obj.toString().equals("")){
				return null;
			}
			if(obj instanceof Float){
				return (Float)obj;
			}else{
				return Utils.toBigDecimal(obj).doubleValue();
			}
		case Integer:
			if(obj.toString().equals("")){
				return null;
			}
			if(obj instanceof Integer){
				return (Integer)obj;
			}else{
				return Utils.toBigDecimal(obj).intValue();
			}
		case String:
			if(obj instanceof String){
				return (String)obj;
			}else{
				return obj.toString();
			}
		case List:
			if(obj.toString().equals("")){
				return null;
			}
			if(obj instanceof List){
				return (List<?>)obj;
			}else{
				String[] arrs=obj.toString().split(",");
				List<String> list=new ArrayList<String>();
				for(int i=0;i<arrs.length;i++){
					list.add(arrs[i]);
				}
				return list;
			}
		case Date:
			if(obj.toString().equals("")){
				return null;
			}
			if(obj instanceof Date){
				return (Date)obj;
			}else{
				Date date=null;
				SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try{
					date=sd.parse(obj.toString());
				}catch(ParseException e){
					sd=new SimpleDateFormat("yyyy-MM-dd");
					try{
						date=sd.parse(obj.toString());						
					}catch(ParseException ex){
						throw new ReportComputeException("Date parameter value pattern must be \"yyyy-MM-dd\" or \"yyyy-MM-dd HH:mm:ss\".");
					}
				}
				return date;
			}
		}
		throw new ReportComputeException("Unknow parameter type : " + this);
	}
}
