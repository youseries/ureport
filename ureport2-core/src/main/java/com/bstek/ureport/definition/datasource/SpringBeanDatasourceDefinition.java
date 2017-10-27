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
package com.bstek.ureport.definition.datasource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.bstek.ureport.build.Dataset;
import com.bstek.ureport.definition.dataset.BeanDatasetDefinition;
import com.bstek.ureport.definition.dataset.DatasetDefinition;

/**
 * @author Jacky.gao
 * @since 2016年12月27日
 */
public class SpringBeanDatasourceDefinition implements DatasourceDefinition {
	private String beanId;
	private String name;
	private List<DatasetDefinition> datasets;
	
	public List<Dataset> getDatasets(ApplicationContext applicationContext,Map<String,Object> parameters){
		Object targetBean=applicationContext.getBean(beanId);
		List<Dataset> list=new ArrayList<Dataset>();
		for(DatasetDefinition dsDef:datasets){
			BeanDatasetDefinition beanDef=(BeanDatasetDefinition)dsDef;
			Dataset ds=beanDef.buildDataset(name,targetBean, parameters);
			list.add(ds);
		}
		return list;
	}
	
	@Override
	public DatasourceType getType() {
		return DatasourceType.spring;
	}

	@Override
	public List<DatasetDefinition> getDatasets() {
		return datasets;
	}
	public void setDatasets(List<DatasetDefinition> datasets) {
		this.datasets = datasets;
	}
	@Override
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}
	public String getBeanId() {
		return beanId;
	}
}
