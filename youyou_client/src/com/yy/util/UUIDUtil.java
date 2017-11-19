package com.yy.util;

import java.util.UUID;

/**
 * UUID 产生码
 * 
 * @author Sunny
 * @Created Time:Jan 4, 2009 9:30:29 PM
 */

public class UUIDUtil {

	public static String randomUUID() {
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.toUpperCase().replaceAll("-", "");
		return uuid;
	}

	/**
	 * 预约挂号专用唯一码
	 */
	public static String genRegUniqueId() {
		return "GH_" + randomUUID();
	}

	/**
	 * 预约体检专用唯一码
	 */
	public static String genTjUniqueId() {
		return "TJ_" + randomUUID();
	}

	/**
	 * 定制专用唯一码
	 */
	public static String genDzUniqueId() {
		return "DZ_" + randomUUID();
	}

	/**
	 * 慢病专用唯一码
	 */
	public static String genMbUniqueId() {
		return "MB_" + randomUUID();
	}

	/**
	 * 话题专用唯一码
	 */
	public static String genHtUniqueId() {
		return "HT_" + randomUUID();
	}

	/**
	 * 标签专用唯一码
	 */
	public static String genBqUniqueId() {
		return "BQ_" + randomUUID();
	}
	
	/**
	 * 自测专用唯一码
	 */
	public static String genZcUniqueId() {
		return "ZC_" + randomUUID();
	}

	public static void main(String[] agr) {
		System.out.println(genHtUniqueId());
		System.out.println(genHtUniqueId().length());
	}
}
