package com.bstek.ureport.font.lishu;

import com.bstek.ureport.export.pdf.font.FontRegister;

/**
 * @author ldk
 * @since 2018年12月22日
 */
public class LiShuRegister implements FontRegister {

	public String getFontName() {
		return "隶书";
	}

	public String getFontPath() {
		return "com/bstek/ureport/font/lishu/SIMLI.TTF";
	}
}
