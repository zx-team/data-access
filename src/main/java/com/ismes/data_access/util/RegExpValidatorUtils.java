package com.ismes.data_access.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpValidatorUtils {
	/**
	 * @param regex
	 *            正则表达式字符串
	 * @param str
	 *            要匹配的字符串
	 * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
	 */
	public static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	public static boolean isIndexof(String str, String completestr) {
		if (str.indexOf(completestr) == -1) {
			return false;
		}
		return true;
	}

	// 判断是不是空
	public static boolean isNull(String str) {
		if (null != str && !"".equals(str)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 验证是否符合日期格式
	 * 
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 * @throws ParseException
	 */
	public static boolean isDateFormat(String dateformat, String str) {
		SimpleDateFormat format = new SimpleDateFormat(dateformat);
		// 设置lenient为false.
		// 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
		format.setLenient(false);
		try {
			format.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
