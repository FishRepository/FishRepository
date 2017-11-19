package com.yy.util;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 集合 字符串判断null
 * 
 * @author Administrator
 * 
 */
public class ValUtil {

	public static boolean listNotEmpty(List<?> list) {
		if (list == null) {
			return false;
		} else if (list.size() <= 0) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean strNotNull(String str) {
		if (str == null) {
			return false;
		} else if (str.trim().length() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean strNull(String str) {
		if (str == null) {
			return true;
		} else if (str.trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean objNotNull(Object obj) {
		if ("".equals(obj) || null == obj) {
			return false;
		} else if (obj instanceof Map && ((Map) obj).size() > 0) {
			return true;
		} else if (obj.toString().trim().length() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean objNull(Object obj) {
		if (null == obj || "".equals(obj)) {
			return true;
		} else if (obj.toString().trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		System.out.println(strNull("   "));
	}

	/**
	 * 判断身份证格式
	 */
	public static boolean isIdCard(String idCard) {
		if (strNotNull(idCard)) {
			return IdcardValidator.IDCheck(idCard);
		} else {
			return false;
		}
	}

	/**
	 * 身份证计算年龄
	 */
	public static int getAgeByIdCard(String idCard) {
		if (strNotNull(idCard)) {
			return IdcardValidator.getAgeByIdCard(idCard);
		} else {
			return -1;
		}
	}

	public static boolean isChild(String idcard) {
		if (strNull(idcard)) {
			return false;
		}
		int age = getAgeByIdCard(idcard);
		if (age >= 0 && age <= 18) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是否是手机号码判断
	 */
	public static boolean isMobile(String mobile) {
		boolean flag = false;
		if (strNotNull(mobile)) {
			try {
				Pattern p = Pattern.compile("^1\\d{10}$");
				// Pattern p = Pattern.compile("^1(3|4|5|8)\\d{9}$");
				Matcher m = p.matcher(mobile);
				flag = m.matches();
			} catch (Exception e) {
				e.printStackTrace();
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * 是否中国移动手机号码判断
	 */
	public static boolean isCMCC(String mobile) {
		boolean flag = false;
		try {
			Pattern p = Pattern
					.compile("^((134[0-8])\\d{7})|(((13[5-9])|(14[7])|(15[0-2,7-9])|(18[2-4,7-8]))\\d{8})$");
			Matcher m = p.matcher(mobile);
			flag = m.matches();
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	public static boolean checkChinese(String str) {
		if (strNotNull(str)) {
			String reg = "^[\u4E00-\u9FA5]+$";
			Pattern p = Pattern.compile(reg);

			str = new String(str.getBytes());// 用GBK编码
			Matcher m = p.matcher(str);
			return m.matches();
		} else {
			return false;
		}
	}

}
