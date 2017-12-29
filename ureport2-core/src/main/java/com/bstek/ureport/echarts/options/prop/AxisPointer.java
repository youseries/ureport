package com.bstek.ureport.echarts.options.prop;

/**
 * @author Jacky.gao
 * @since 2017年12月27日
 */
public class AxisPointer {
	private boolean show;
	private AxisPointerType type;
	private AxisPointerLabel label;
	private AxisPointerHandle handle;
	public boolean isShow() {
		return show;
	}
	public void setShow(boolean show) {
		this.show = show;
	}
	public AxisPointerType getType() {
		return type;
	}
	public void setType(AxisPointerType type) {
		this.type = type;
	}
	public AxisPointerLabel getLabel() {
		return label;
	}
	public void setLabel(AxisPointerLabel label) {
		this.label = label;
	}
	public AxisPointerHandle getHandle() {
		return handle;
	}
	public void setHandle(AxisPointerHandle handle) {
		this.handle = handle;
	}
}
