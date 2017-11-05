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
package com.bstek.ureport.build;

/**
 * @author Jacky.gao
 * @since 2017年6月19日
 */
public class Splash {
	public void doPrint(){
		StringBuilder sb=new StringBuilder();
		sb.append("\n");
		sb.append("_____  __________ __________________ _______ ________ ______________ ");
		sb.append("\n");
		sb.append("__   / / /___  __ \\___  ____/___  __ \\__  __ \\___  __ \\___  __/__|__ \\");
		sb.append("\n");
		sb.append("_  / / / __  /_/ /__  __/   __  /_/ /_  / / /__  /_/ /__  /   ____/ /");
		sb.append("\n");
		sb.append("/ /_/ /  _  _, _/ _  /___   _  ____/ / /_/ / _  _, _/ _  /    _  __/ ");
		sb.append("\n");
		sb.append("\\____/   /_/ |_|  /_____/   /_/      \\____/  /_/ |_|  /_/     /____/ ");
		sb.append("\n");
		sb.append("........................................................................................................");
		sb.append("\n");
		sb.append(".  uReport, is a Chinese style report engine");
		sb.append(" licensed under the Apache License 2.0,                    .");
		sb.append("\n");
		sb.append(".  which is opensource, easy to use,high-performance, with browser-based-designer.                     .");
		sb.append("\n");
		sb.append("........................................................................................................");
		sb.append("\n");
		System.out.println(sb.toString());
	}
}
