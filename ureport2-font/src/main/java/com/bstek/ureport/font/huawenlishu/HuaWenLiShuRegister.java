package com.bstek.ureport.font.huawenlishu;

import com.bstek.ureport.export.pdf.font.FontRegister;

/**
 * @author ldk
 * @since 2018年12月22日
 */
public class HuaWenLiShuRegister implements FontRegister {

	public String getFontName() {
		return "华文隶书";
	}

	public String getFontPath() {
		return "com/bstek/ureport/font/huawenlishu/STLITI.TTF";
	}
}
