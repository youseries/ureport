package com.bstek.ureport.chart.dataset.impl.category;

import com.bstek.ureport.build.Context;
import com.bstek.ureport.chart.dataset.DatasetType;
import com.bstek.ureport.model.Cell;

/**
 * @author Jacky.gao
 * @since 2017年6月8日
 */
public class HorizontalBarDataset extends BarDataset {
	@Override
	public String buildConfiguration(Context context,Cell cell) {
		return null;
	}
	@Override
	public DatasetType getDatasetType() {
		return DatasetType.HorizontalBar;
	}
}
