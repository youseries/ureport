package com.bstek.ureport.echarts.options;

import com.bstek.ureport.echarts.options.prop.PolarTooltip;

/**
 * @author Jacky.gao
 * @since 2017年12月27日
 */
public class PolarOption {
	private String center;
	private PolarTooltip tooltip;
	public String getCenter() {
		return center;
	}
	public void setCenter(String center) {
		this.center = center;
	}
	public PolarTooltip getTooltip() {
		return tooltip;
	}
	public void setTooltip(PolarTooltip tooltip) {
		this.tooltip = tooltip;
	}
}
