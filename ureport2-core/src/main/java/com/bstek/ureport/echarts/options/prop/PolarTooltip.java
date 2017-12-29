package com.bstek.ureport.echarts.options.prop;

/**
 * @author Jacky.gao
 * @since 2017年12月27日
 */
public class PolarTooltip {
	private boolean show=true;
	private Trigger trigger;
	private AxisPointer axisPointer;
	private String formatter;
	private String backgroundColor;
	private TextStyle textStyle;
	public boolean isShow() {
		return show;
	}
	public void setShow(boolean show) {
		this.show = show;
	}
	public Trigger getTrigger() {
		return trigger;
	}
	public void setTrigger(Trigger trigger) {
		this.trigger = trigger;
	}
	public AxisPointer getAxisPointer() {
		return axisPointer;
	}
	public void setAxisPointer(AxisPointer axisPointer) {
		this.axisPointer = axisPointer;
	}
	public String getFormatter() {
		return formatter;
	}
	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public TextStyle getTextStyle() {
		return textStyle;
	}
	public void setTextStyle(TextStyle textStyle) {
		this.textStyle = textStyle;
	}
}
