package com.bstek.ureport.chart.axes.impl;

import com.bstek.ureport.chart.axes.BaseAxes;
import com.bstek.ureport.chart.axes.ScaleLabel;
import com.bstek.ureport.chart.axes.XPosition;

/**
 * @author Jacky.gao
 * @since 2017年6月14日
 */
public class XAxes extends BaseAxes {
	private XPosition xposition=XPosition.bottom;
	@Override
	public String toJson() {
		StringBuilder sb=new StringBuilder();
		sb.append("{");
		sb.append("position:\""+xposition+"\",");
		sb.append("minRotation:"+getRotation()+",");
		ScaleLabel scaleLabel=getScaleLabel();
		if(scaleLabel!=null){
			sb.append("scaleLabel:"+scaleLabel.toJson());	
		}
		sb.append("}");
		return sb.toString();
	}
	public XPosition getXposition() {
		return xposition;
	}
	public void setXposition(XPosition xposition) {
		this.xposition = xposition;
	}
}
