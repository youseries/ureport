package com.bstek.ureport.chart.option.impl;

import com.bstek.ureport.chart.option.FontStyle;
import com.bstek.ureport.chart.option.Option;

/**
 * @author Jacky.gao
 * @since 2017年6月8日
 */
public class TooltipOption implements Option {
	private boolean enabled=true;
	private String titleFontFamily="'Helvetica Neue', 'Helvetica', 'Arial', sans-serif";
	private int titleFontSize=12;
	private FontStyle titleFontStyle=FontStyle.bold;
	private String titleFontColor="#fff";
	private String bodyFontFamily="'Helvetica Neue', 'Helvetica', 'Arial', sans-serif";
	private int bodyFontSize=12;
	private FontStyle bodyFontStyle=FontStyle.normal;
	private String bodyFontColor="#fff";
	
	@Override
	public String getConfiguration() {
		return null;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getTitleFontFamily() {
		return titleFontFamily;
	}

	public void setTitleFontFamily(String titleFontFamily) {
		this.titleFontFamily = titleFontFamily;
	}

	public int getTitleFontSize() {
		return titleFontSize;
	}

	public void setTitleFontSize(int titleFontSize) {
		this.titleFontSize = titleFontSize;
	}

	public FontStyle getTitleFontStyle() {
		return titleFontStyle;
	}

	public void setTitleFontStyle(FontStyle titleFontStyle) {
		this.titleFontStyle = titleFontStyle;
	}

	public String getTitleFontColor() {
		return titleFontColor;
	}

	public void setTitleFontColor(String titleFontColor) {
		this.titleFontColor = titleFontColor;
	}

	public String getBodyFontFamily() {
		return bodyFontFamily;
	}

	public void setBodyFontFamily(String bodyFontFamily) {
		this.bodyFontFamily = bodyFontFamily;
	}

	public int getBodyFontSize() {
		return bodyFontSize;
	}

	public void setBodyFontSize(int bodyFontSize) {
		this.bodyFontSize = bodyFontSize;
	}

	public FontStyle getBodyFontStyle() {
		return bodyFontStyle;
	}

	public void setBodyFontStyle(FontStyle bodyFontStyle) {
		this.bodyFontStyle = bodyFontStyle;
	}

	public String getBodyFontColor() {
		return bodyFontColor;
	}

	public void setBodyFontColor(String bodyFontColor) {
		this.bodyFontColor = bodyFontColor;
	}
	
}
