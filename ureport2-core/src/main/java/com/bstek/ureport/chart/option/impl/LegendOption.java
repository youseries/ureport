package com.bstek.ureport.chart.option.impl;

import com.bstek.ureport.chart.option.Labels;
import com.bstek.ureport.chart.option.Option;
import com.bstek.ureport.chart.option.Position;

/**
 * @author Jacky.gao
 * @since 2017年6月8日
 */
public class LegendOption implements Option {
	private boolean display=true;
	private Position position=Position.top;
	private Labels labels;
	@Override
	public String getConfiguration() {
		return null;
	}
	public boolean isDisplay() {
		return display;
	}
	public void setDisplay(boolean display) {
		this.display = display;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public Labels getLabels() {
		return labels;
	}
	public void setLabels(Labels labels) {
		this.labels = labels;
	}
}
