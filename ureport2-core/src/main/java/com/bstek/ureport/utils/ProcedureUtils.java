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

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.JdbcUtils;

import com.bstek.ureport.definition.dataset.Field;
import com.bstek.ureport.exception.ReportException;

/**
 * @author Jacky.gao
 * @since 2017年12月27日
 */
public class ProcedureUtils {
	public static boolean isProcedure(String sql){
		sql=sql.trim().toLowerCase();
		return sql.startsWith("call ");
	}
	
	public static List<Field> procedureColumnsQuery(String sql,Map<String, Object> pmap,Connection conn){
		StatementWrapper wrapper=buildProcedureCallableStatement(sql, pmap, conn);
		CallableStatement cs=wrapper.getCallableStatement();
		int oracleCursorIndex=wrapper.getOracleCursorIndex();
		ResultSet rs=null;
		try {
			if(oracleCursorIndex==-1){
				rs=cs.executeQuery();				
			}else{
				cs.executeUpdate();
				rs=(ResultSet)cs.getObject(oracleCursorIndex);
			}
			ResultSetMetaData metadata=rs.getMetaData();
			int columnCount=metadata.getColumnCount();
			List<Field> fields=new ArrayList<Field>();
			for(int i=1;i<=columnCount;i++){
				String columnName=metadata.getColumnLabel(i);
				fields.add(new Field(columnName));
			}
			return fields;
		} catch (SQLException e) {
			throw new ReportException(e);
		}finally{
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(cs);
			JdbcUtils.closeConnection(conn);
		}
	}
	
	
	public static List<Map<String,Object>> procedureQuery(String sql,Map<String, Object> pmap,Connection conn){
		StatementWrapper wrapper=buildProcedureCallableStatement(sql, pmap, conn);
		CallableStatement cs=wrapper.getCallableStatement();
		int oracleCursorIndex=wrapper.getOracleCursorIndex();
		ResultSet rs=null;
		try {
			if(oracleCursorIndex==-1){
				rs=cs.executeQuery();				
			}else{
				cs.executeUpdate();
				rs=(ResultSet)cs.getObject(oracleCursorIndex);
			}
			ResultSetMetaData metadata=rs.getMetaData();
			int columnCount=metadata.getColumnCount();
			List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
			while(rs.next()){
				Map<String,Object> map=new HashMap<String,Object>();
				for(int i=1;i<=columnCount;i++){
					String columnName=metadata.getColumnLabel(i);
					map.put(columnName, rs.getObject(columnName));
				}
				result.add(map);
			}
			return result;
		} catch (SQLException e) {
			throw new ReportException(e);
		}finally{
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(cs);
			JdbcUtils.closeConnection(conn);
		}
	}
	
	private static StatementWrapper buildProcedureCallableStatement(String sql,Map<String, Object> pmap,Connection conn){
		try {
			Map<String,Object> paramMap=new LinkedHashMap<String,Object>();
			int leftParnPos=sql.indexOf("(");
			int rightParnPos=sql.indexOf(")");
			String paramStr="";
			if(leftParnPos>-1 && rightParnPos>-1){
				paramStr=sql.substring(leftParnPos+1,rightParnPos);				
			}
			int oracleCursorIndex=-1,paramIndex=0;
			String[] str=paramStr.split(",");
			for(String param:str){
				paramIndex++;
				param=param.trim();
				if(param.toLowerCase().equals("oracle")){
					sql=sql.replaceFirst(param, "?");
					oracleCursorIndex=paramIndex;
					continue;
				}else if(!param.startsWith(":")){
					continue;
				}
				sql=sql.replaceFirst(param, "?");
				String paramName=param.substring(1,param.length());
				Object paramValue=pmap.get(paramName);
				paramMap.put(paramName, (paramValue==null ? "" : paramValue));
			}
			String procedure="{"+sql+"}";
			CallableStatement cs= conn.prepareCall(procedure);
			int index=1;
			for(String name:paramMap.keySet()){
				Object value=paramMap.get(name);
				if(value instanceof String){
					cs.setString(index,(String)value);									
				}else if(value instanceof Date){
					Date date=(Date)value;
					cs.setDate(index, new java.sql.Date(date.getTime()));
				}else if(value instanceof Integer){
					cs.setInt(index, (Integer)value);
				}else if(value instanceof Float){
					cs.setFloat(index, (Float)value);
				}else if(value instanceof Double){
					cs.setDouble(index, (Double)value);
				}else{
					cs.setObject(index, value);
				}
				index++;
			}
			if(oracleCursorIndex>-1){
				cs.registerOutParameter(oracleCursorIndex, -10);
			}
			return new StatementWrapper(cs,oracleCursorIndex);
		} catch (SQLException e) {
			throw new ReportException(e);
		}
	}
}
class StatementWrapper{
	private CallableStatement callableStatement;
	private int oracleCursorIndex;
	public StatementWrapper(CallableStatement callableStatement,int oracleCursorIndex) {
		this.callableStatement=callableStatement;
		this.oracleCursorIndex=oracleCursorIndex;
	}
	public CallableStatement getCallableStatement() {
		return callableStatement;
	}
	public int getOracleCursorIndex() {
		return oracleCursorIndex;
	}
}
