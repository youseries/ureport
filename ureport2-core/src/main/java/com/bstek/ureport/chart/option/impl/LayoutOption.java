package com.bstek.ureport.chart.option.impl;

import com.bstek.ureport.chart.option.Option;
import com.bstek.ureport.chart.option.Padding;

/**
 * @author Jacky.gao
 * @since 2017年6月8日
 */
public class LayoutOption implements Option {
	private Padding padding;
	@Override
	public String getConfiguration() {
		return null;
	}
	public Padding getPadding() {
		return padding;
	}

	public void setPadding(Padding padding) {
		this.padding = padding;
	}
}
