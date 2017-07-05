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
package com.bstek.ureport.definition;

import java.io.Serializable;

import com.bstek.ureport.exception.ReportComputeException;

/**
 * @author Jacky.gao
 * @since 2014年4月23日
 */
public enum PaperType implements Serializable{
	A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B0, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, CUSTOM;
	public PaperSize getPaperSize(){
		switch(this){
		case A0:
			return new PaperSize(2384,3370);
		case A1:
			return new PaperSize(1684,2384);
		case A2:
			return new PaperSize(1191,1684);
		case A3:
			return new PaperSize(842,1191);
		case A4:
			return new PaperSize(595,842);
		case A5:
			return new PaperSize(420,595);
		case A6:
			return new PaperSize(298,420);
		case A7:
			return new PaperSize(210,298);
		case A8:
			return new PaperSize(147,210);
		case A9:
			return new PaperSize(105,147);
		case A10:
			return new PaperSize(74,105);
		case B0:
			return new PaperSize(2834,4008);
		case B1:
			return new PaperSize(2004,2834);
		case B2:
			return new PaperSize(1417,2004);
		case B3:
			return new PaperSize(1001,1417);
		case B4:
			return new PaperSize(709,1001);
		case B5:
			return new PaperSize(499,709);
		case B6:
			return new PaperSize(354,499);
		case B7:
			return new PaperSize(249,354);
		case B8:
			return new PaperSize(176,249);
		case B9:
			return new PaperSize(125,176);
		case B10:
			return new PaperSize(88,125);
		default:
			throw new ReportComputeException("Unsupport "+this+" paper.");
		}
	}
}
