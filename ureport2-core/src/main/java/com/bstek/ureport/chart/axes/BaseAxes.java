package com.bstek.ureport.chart.axes;

/**
 * @author Jacky.gao
 * @since 2017年6月14日
 */
public abstract class BaseAxes implements Axes {
	private int rotation;
	private ScaleLabel scaleLabel;
	public int getRotation() {
		return rotation;
	}
	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	public ScaleLabel getScaleLabel() {
		return scaleLabel;
	}
	public void setScaleLabel(ScaleLabel scaleLabel) {
		this.scaleLabel = scaleLabel;
	}
}
