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
package com.bstek.ureport.console.designer;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;
import org.springframework.jdbc.core.namedparam.ParsedSql;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.JdbcUtils;

import com.bstek.ureport.Utils;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.console.RenderPageServletAction;
import com.bstek.ureport.console.exception.ReportDesignException;
import com.bstek.ureport.definition.dataset.Field;
import com.bstek.ureport.definition.datasource.BuildinDatasource;
import com.bstek.ureport.definition.datasource.DataType;
import com.bstek.ureport.expression.ExpressionUtils;
import com.bstek.ureport.expression.model.Expression;
import com.bstek.ureport.expression.model.data.ExpressionData;
import com.bstek.ureport.expression.model.data.ObjectExpressionData;
import com.bstek.ureport.utils.ProcedureUtils;

/**
 * @author Jacky.gao
 * @since 2017年2月6日
 */
public class DatasourceServletAction extends RenderPageServletAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method=retriveMethod(req);
		if(method!=null){
			invokeMethod(method, req, resp);
		}
	}
	
	public void loadBuildinDatasources(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<String> datasources=new ArrayList<String>();
		for(BuildinDatasource datasource:Utils.getBuildinDatasources()){
			datasources.add(datasource.name());
		}
		writeObjectToJson(resp, datasources);
	}
	
	public void loadMethods(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String beanId=req.getParameter("beanId");
		Object obj=applicationContext.getBean(beanId);
		Class<?> clazz=obj.getClass();
		Method[] methods=clazz.getMethods();
		List<String> result=new ArrayList<String>();
		for(Method method:methods){
			Class<?>[] types=method.getParameterTypes();
			if(types.length!=3){
				continue;
			}
			Class<?> typeClass1=types[0];
			Class<?> typeClass2=types[1];
			Class<?> typeClass3=types[2];
			if(!String.class.isAssignableFrom(typeClass1)){
				continue;
			}
			if(!String.class.isAssignableFrom(typeClass2)){
				continue;
			}
			if(!Map.class.isAssignableFrom(typeClass3)){
				continue;
			}
			result.add(method.getName());
		}
		writeObjectToJson(resp, result);
	}
	
	public void buildClass(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String clazz=req.getParameter("clazz");
		List<Field> result=new ArrayList<Field>();
		try{
			Class<?> targetClass=Class.forName(clazz);
			PropertyDescriptor[] propertyDescriptors=PropertyUtils.getPropertyDescriptors(targetClass);
			for(PropertyDescriptor pd:propertyDescriptors){
				String name=pd.getName();
				if("class".equals(name)){
					continue;
				}
				result.add(new Field(name));
			}
			writeObjectToJson(resp, result);
		}catch(Exception ex){
			throw new ReportDesignException(ex);
		}
	}
	
	public void buildDatabaseTables(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn=null;
		ResultSet rs = null;
		try{
			conn=buildConnection(req);
			DatabaseMetaData metaData = conn.getMetaData();
			String url = metaData.getURL();
			String schema = null;
			if (url.toLowerCase().contains("oracle")) {
				schema = metaData.getUserName();
			}
			List<Map<String,String>> tables = new ArrayList<Map<String,String>>();
			rs = metaData.getTables(null, schema, "%", new String[] { "TABLE","VIEW" });
			while (rs.next()) {
				Map<String,String> table = new HashMap<String,String>();
				table.put("name",rs.getString("TABLE_NAME"));
				table.put("type",rs.getString("TABLE_TYPE"));
				tables.add(table);
			}
			writeObjectToJson(resp, tables);
		}catch(Exception ex){
			throw new ServletException(ex);
		}finally{
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeConnection(conn);
		}
	}
	
	public void buildFields(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String sql=req.getParameter("sql");
		String parameters=req.getParameter("parameters");
		Connection conn=null;
		final List<Field> fields=new ArrayList<Field>();
		try{
			conn=buildConnection(req);
			Map<String, Object> map = buildParameters(parameters);
			sql=parseSql(sql, map);
			if(ProcedureUtils.isProcedure(sql)){
				List<Field> fieldsList = ProcedureUtils.procedureColumnsQuery(sql, map, conn);
				fields.addAll(fieldsList);
			}else{
				DataSource dataSource=new SingleConnectionDataSource(conn,false);
				NamedParameterJdbcTemplate jdbc=new NamedParameterJdbcTemplate(dataSource);
				PreparedStatementCreator statementCreator=getPreparedStatementCreator(sql,new MapSqlParameterSource(map));
				jdbc.getJdbcOperations().execute(statementCreator, new PreparedStatementCallback<Object>() {
					@Override
					public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
						ResultSet rs = null;
						try {
							rs = ps.executeQuery();
							ResultSetMetaData metadata=rs.getMetaData();
							int columnCount=metadata.getColumnCount();
							for(int i=0;i<columnCount;i++){
								String columnName=metadata.getColumnLabel(i+1);
								fields.add(new Field(columnName));
							}
							return null;
						}finally {
							JdbcUtils.closeResultSet(rs);
						}
					}
				});
			}
			writeObjectToJson(resp, fields);
		}catch(Exception ex){
			throw new ReportDesignException(ex);
		}finally{
			JdbcUtils.closeConnection(conn);
		}
	}
	
	protected PreparedStatementCreator getPreparedStatementCreator(String sql, SqlParameterSource paramSource) {
		ParsedSql parsedSql = NamedParameterUtils.parseSqlStatement(sql);
		String sqlToUse = NamedParameterUtils.substituteNamedParameters(parsedSql, paramSource);
		Object[] params = NamedParameterUtils.buildValueArray(parsedSql, paramSource, null);
		List<SqlParameter> declaredParameters = NamedParameterUtils.buildSqlParameterList(parsedSql, paramSource);
		PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(sqlToUse, declaredParameters);
		return pscf.newPreparedStatementCreator(params);
	}

	public void previewData(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String sql=req.getParameter("sql");
		String parameters=req.getParameter("parameters");
		Map<String, Object> map = buildParameters(parameters);
		sql=parseSql(sql, map);
		Connection conn=null;
		try{
			conn=buildConnection(req);
			List<Map<String,Object>> list=null;
			if(ProcedureUtils.isProcedure(sql)){
				list=ProcedureUtils.procedureQuery(sql, map, conn);
			}else{
				DataSource dataSource=new SingleConnectionDataSource(conn,false);
				NamedParameterJdbcTemplate jdbc=new NamedParameterJdbcTemplate(dataSource);
				list=jdbc.queryForList(sql, map);				
			}
			int size=list.size();
			int currentTotal=size;
			if(currentTotal>500){
				currentTotal=500;
			}
			List<Map<String,Object>> ls=new ArrayList<Map<String,Object>>();
			for(int i=0;i<currentTotal;i++){
				ls.add(list.get(i));
			}
			DataResult result=new DataResult();
			List<String> fields=new ArrayList<String>();
			if(size>0){
				Map<String,Object> item=list.get(0);
				for(String name:item.keySet()){
					fields.add(name);
				}
			}
			result.setFields(fields);
			result.setCurrentTotal(currentTotal);
			result.setData(ls);
			result.setTotal(size);
			writeObjectToJson(resp, result);
		}catch(Exception ex){
			throw new ServletException(ex);
		}finally{
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private String parseSql(String sql,Map<String,Object> parameters){
		sql=sql.trim();
		Context context=new Context(applicationContext, parameters);
		if(sql.startsWith(ExpressionUtils.EXPR_PREFIX) && sql.endsWith(ExpressionUtils.EXPR_SUFFIX)){
			sql=sql.substring(2, sql.length()-1);
			Expression expr=ExpressionUtils.parseExpression(sql);
			sql=executeSqlExpr(expr,context);
			return sql;
		}else{
			String sqlForUse=sql;
			Pattern pattern=Pattern.compile("\\$\\{.*?\\}");
			Matcher matcher=pattern.matcher(sqlForUse);
			while(matcher.find()){
				String substr=matcher.group();
				String sqlExpr=substr.substring(2,substr.length()-1);
				Expression expr=ExpressionUtils.parseExpression(sqlExpr);
				String result=executeSqlExpr(expr, context);
				sqlForUse=sqlForUse.replace(substr, result);
			}
			Utils.logToConsole("DESIGN SQL:"+sqlForUse);
			return sqlForUse;
		}
	}
	
	private String executeSqlExpr(Expression sqlExpr,Context context){
		String sqlForUse=null;
		ExpressionData<?> exprData=sqlExpr.execute(null, null, context);
		if(exprData instanceof ObjectExpressionData){
			ObjectExpressionData data=(ObjectExpressionData)exprData;
			Object obj=data.getData();
			if(obj!=null){
				String s=obj.toString();
				s=s.replaceAll("\\\\", "");
				sqlForUse=s;
			}
		}
		return sqlForUse;
	}
	
	public void testConnection(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username=req.getParameter("username");
		String password=req.getParameter("password");
		String driver=req.getParameter("driver");
		String url=req.getParameter("url");
		Connection conn=null;
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			Class.forName(driver);
			conn=DriverManager.getConnection(url, username, password);
			map.put("result", true);
		}catch(Exception ex){
			map.put("error", ex.toString());
			map.put("result", false);
		}finally{
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		writeObjectToJson(resp, map);
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Object> buildParameters(String parameters) throws IOException, JsonParseException, JsonMappingException {
		Map<String,Object> map=new HashMap<String,Object>();
		if(StringUtils.isBlank(parameters)){
			return map;
		}
		ObjectMapper mapper=new ObjectMapper();
		List<Map<String,Object>> list=mapper.readValue(parameters, ArrayList.class);
		for(Map<String,Object> param:list){
			String name=param.get("name").toString();
			DataType type=DataType.valueOf(param.get("type").toString());
			String defaultValue=(String)param.get("defaultValue");
			if(defaultValue==null || defaultValue.equals("")){
				switch(type){
				case Boolean:
					map.put(name, false);
				case Date:
					map.put(name, new Date());
				case Float:
					map.put(name, new Float(0));
				case Integer:
					map.put(name, 0);
				case String:
					if(defaultValue!=null && defaultValue.equals("")){
						map.put(name, "");						
					}else{
						map.put(name, "null");						
					}
					break;
				case List:
					map.put(name, new ArrayList<Object>());
				}				
			}else{
				map.put(name, type.parse(defaultValue));			
			}
		}
		return map;
	}
	
	private Connection buildConnection(HttpServletRequest req) throws Exception{
		String type=req.getParameter("type");
		if(type.equals("jdbc")){			
			String username=req.getParameter("username");
			String password=req.getParameter("password");
			String driver=req.getParameter("driver");
			String url=req.getParameter("url");
			
			Class.forName(driver);
			Connection conn=DriverManager.getConnection(url, username, password);
			return conn;
		}else{
			String name=req.getParameter("name");
			Connection conn=Utils.getBuildinConnection(name);
			if(conn==null){
				throw new ReportDesignException("Buildin datasource ["+name+"] not exist.");
			}
			return conn;
		}
	}
	
	@Override
	public String url() {
		return "/datasource";
	}
}
