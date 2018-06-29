
package com.demo.watchserver.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

/**
 * 字符串工具类
 * 
 * @author linyb1
 */
public class StringUtil extends StringUtils {

	/**
	 * 字符串格式化拼接
	 * 
	 * @param format,格式必须要有{}占位符
	 * @param argArray
	 * @return
	 */
	public static String format(String format, Object... argArray) {
		FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
		return ft.getMessage();
	}

	public static void main(String[] args) {
		System.out.println(format("字符串拼接，abc：{}，efg-->{}", 123, 456));

	}

}
