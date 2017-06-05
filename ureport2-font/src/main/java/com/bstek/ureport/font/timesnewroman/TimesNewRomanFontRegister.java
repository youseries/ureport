package com.bstek.ureport.font.timesnewroman;

import com.bstek.ureport.export.pdf.font.FontRegister;

/**
 * @author Jacky.gao
 * @since 2014年5月7日
 */
public class TimesNewRomanFontRegister implements FontRegister {

	public String getFontName() {
		return "Times New Roman";
	}

	public String getFontPath() {
		return "com/bstek/ureport/font/timesnewroman/TIMES.TTF";
	}
}
