package com.yy.util;

import javax.servlet.http.HttpServletRequest;

public class UrlUtil {
	
	public static String getContentPath(HttpServletRequest request){
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
		System.out.println(basePath);
		return basePath;
	}

}
