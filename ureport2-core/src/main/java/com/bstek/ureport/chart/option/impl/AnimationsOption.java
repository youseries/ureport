package com.bstek.ureport.chart.option.impl;

import com.bstek.ureport.chart.option.Easing;
import com.bstek.ureport.chart.option.Option;

/**
 * @author Jacky.gao
 * @since 2017年6月8日
 */
public class AnimationsOption implements Option {
	private int duration=1000;
	private Easing easing=Easing.easeOutQuart;
	@Override
	public String getConfiguration() {
		return null;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public Easing getEasing() {
		return easing;
	}
	public void setEasing(Easing easing) {
		this.easing = easing;
	}
}
