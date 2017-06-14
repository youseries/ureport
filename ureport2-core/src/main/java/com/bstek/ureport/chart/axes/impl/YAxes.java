package com.bstek.ureport.chart.axes.impl;

import com.bstek.ureport.chart.axes.BaseAxes;
import com.bstek.ureport.chart.axes.ScaleLabel;
import com.bstek.ureport.chart.axes.YPosition;

/**
 * @author Jacky.gao
 * @since 2017年6月14日
 */
public class YAxes extends BaseAxes {
	private YPosition yposition;
	@Override
	public String toJson() {
		StringBuilder sb=new StringBuilder();
		sb.append("{");
		sb.append("position:\""+yposition+"\",");
		sb.append("minRotation:"+getRotation()+",");
		ScaleLabel scaleLabel=getScaleLabel();
		if(scaleLabel!=null){
			sb.append("scaleLabel:"+scaleLabel.toJson());	
		}
		sb.append("}");
		return sb.toString();
	}
	public YPosition getYposition() {
		return yposition;
	}
	public void setYposition(YPosition yposition) {
		this.yposition = yposition;
	}
}
