package com.bstek.ureport.chart.dataset;

import com.bstek.ureport.build.Context;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2017年6月8日
 */
public interface Dataset {
	String buildConfiguration(Context context,Cell cell);
	DatasetType getDatasetType();
	String type();
}
