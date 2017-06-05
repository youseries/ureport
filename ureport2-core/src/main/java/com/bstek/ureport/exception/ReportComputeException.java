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
package com.bstek.ureport.exception;

/**
 * @author Jacky.gao
 * @since 2016年12月26日
 */
public class ReportComputeException extends ReportException {
	private static final long serialVersionUID = -5079596691655241415L;
	public ReportComputeException(Exception ex) {
		super(ex);
	}
	public ReportComputeException(String msg) {
		super(msg);
	}
}
