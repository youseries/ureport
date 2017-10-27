/*******************************************************************************
 * Copyright (C) 2017 Bstek.com
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
		sb.append(" licensed under the GNU GENERAL PUBLIC LICENSE Version 3,  .");
		sb.append("\n");
		sb.append(".  which is opensource, easy to use,high-performance, with browser-based-designer.                     .");
		sb.append("\n");
		sb.append("........................................................................................................");
		sb.append("\n");
		System.out.println(sb.toString());
	}
}
