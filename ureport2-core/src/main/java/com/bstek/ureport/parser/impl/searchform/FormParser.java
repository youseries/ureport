package com.bstek.ureport.parser.impl.searchform;

import com.bstek.ureport.parser.Parser;

/**
 * @author Jacky.gao
 * @since 2017年10月24日
 */
public interface FormParser<T> extends Parser<T> {
	boolean support(String name);
}
