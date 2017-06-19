/*******************************************************************************
 * Copyright 2017 Bstek
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
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
