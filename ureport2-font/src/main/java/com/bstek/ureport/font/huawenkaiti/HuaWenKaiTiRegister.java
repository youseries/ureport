package com.bstek.ureport.font.huawenkaiti;

import com.bstek.ureport.export.pdf.font.FontRegister;

/**
 * @author ldk
 * @since 2018年12月22日
 */
public class HuaWenKaiTiRegister implements FontRegister {

	public String getFontName() {
		return "华文楷体";
	}

	public String getFontPath() {
		return "com/bstek/ureport/font/huawenkaiti/STKAITI.TTF";
	}
}
