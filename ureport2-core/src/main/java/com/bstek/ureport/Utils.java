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
package com.bstek.ureport;

import java.math.BigDecimal;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.bstek.ureport.build.Context;
import com.bstek.ureport.definition.datasource.BuildinDatasource;
import com.bstek.ureport.exception.ConvertException;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.model.Cell;
import com.bstek.ureport.model.Report;
import com.bstek.ureport.provider.image.ImageProvider;


/**
 * @author Jacky.gao
 * @since 2016年11月12日
 */
public class Utils implements ApplicationContextAware{
	private static ApplicationContext applicationContext;
	private static Collection<BuildinDatasource> buildinDatasources;
	private static Collection<ImageProvider> imageProviders;
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public static Collection<BuildinDatasource> getBuildinDatasources() {
		return buildinDatasources;
	}
	
	public static Collection<ImageProvider> getImageProviders() {
		return imageProviders;
	}
	
	public static Connection getBuildinConnection(String name){
		for(BuildinDatasource datasource:buildinDatasources){
			if(name.equals(datasource.name())){
				return datasource.getConnection();
			}
		}
		return null;
	}
	
	public static List<Cell> fetchTargetCells(Cell cell,Context context,String cellName){
		while(!context.isCellPocessed(cellName)){
			context.getReportBuilder().buildCell(context, null);
		}
		List<Cell> leftCells=fetchCellsByLeftParent(context,cell, cellName);
		List<Cell> topCells=fetchCellsByTopParent(context,cell, cellName);
		if(leftCells!=null && topCells!=null){
			int leftSize=leftCells.size(),topSize=topCells.size();
			if(leftSize==1 || topSize==0){
				return leftCells;
			}
			if(topSize==1 || leftSize==0){
				return topCells;
			}
			if(leftSize==0 && topSize==0){
				return new ArrayList<Cell>();
			}
			List<Cell> list=new ArrayList<Cell>();
			if(leftSize<=topSize){
				for(Cell c:leftCells){
					if(topCells.contains(c)){
						list.add(c);
					}
				}
			}else{
				for(Cell c:topCells){
					if(leftCells.contains(c)){
						list.add(c);
					}
				}
			}
			return list;
		}else if(leftCells!=null && topCells==null){
			return leftCells;
		}else if(leftCells==null && topCells!=null){
			return topCells;
		}else{
			Report report=context.getReport();
			return report.getCellsMap().get(cellName);
		}
	}

	private static List<Cell> fetchCellsByLeftParent(Context context,Cell cell,String cellName){
		Cell leftParentCell=cell.getLeftParentCell();
		if(leftParentCell==null){
			return null;
		}
		if(leftParentCell.getName().equals(cellName)){
			List<Cell> list=new ArrayList<Cell>();
			list.add(leftParentCell);
			return list;
		}
		Map<String,List<Cell>> childrenCellsMap=leftParentCell.getRowChildrenCellsMap();
		List<Cell> targetCells=childrenCellsMap.get(cellName);
		if(targetCells!=null){
			return targetCells;
		}
		return fetchCellsByLeftParent(context,leftParentCell,cellName);
	}
	
	private static List<Cell> fetchCellsByTopParent(Context context,Cell cell,String cellName){
		Cell topParentCell=cell.getTopParentCell();
		if(topParentCell==null){
			return null;
		}
		if(topParentCell.getName().equals(cellName)){
			List<Cell> list=new ArrayList<Cell>();
			list.add(topParentCell);
			return list;
		}
		Map<String,List<Cell>> childrenCellsMap=topParentCell.getColumnChildrenCellsMap();
		List<Cell> targetCells=childrenCellsMap.get(cellName);
		if(targetCells!=null){
			return targetCells;
		}
		return fetchCellsByTopParent(context,topParentCell,cellName);
	}
	
	public static Object getProperty(Object obj,String property){
		try{
			if(obj instanceof Map && property.indexOf(".")==-1){
				Map<?,?> map=(Map<?,?>)obj;
				return map.get(property);
			}
			return PropertyUtils.getProperty(obj, property);
		}catch(Exception ex){
			throw new ReportComputeException(ex);
		}
	}
	
	public static Date toDate(Object obj){
		if(obj instanceof Date){
			return (Date)obj;
		}else if(obj instanceof String){
			SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
			try{
				return sd.parse(obj.toString());
			}catch(Exception ex){
				sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try{
					return sd.parse(obj.toString());					
				}catch(Exception e){
					throw new ReportComputeException("Can not convert "+obj+" to Date.");
				}
			}
		}
		throw new ReportComputeException("Can not convert "+obj+" to Date.");
	}
	public static BigDecimal toBigDecimal(Object obj){
		if(obj==null){
			return null;
		}
		if(obj instanceof BigDecimal){
			return (BigDecimal)obj;
		}else if(obj instanceof String){
			if(obj.toString().trim().equals("")){
				return new BigDecimal(0);
			}
			try{
				String str=obj.toString().trim();
				return new BigDecimal(str);				
			}catch(Exception ex){
				throw new ConvertException("Can not convert "+obj+" to BigDecimal.");
			}
		}else if(obj instanceof Number){
			Number n=(Number)obj;
			return new BigDecimal(n.doubleValue());
		}
		throw new ConvertException("Can not convert "+obj+" to BigDecimal.");
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)throws BeansException {
		Utils.applicationContext=applicationContext;
		buildinDatasources=applicationContext.getBeansOfType(BuildinDatasource.class).values();
		imageProviders=applicationContext.getBeansOfType(ImageProvider.class).values();
	}
}
