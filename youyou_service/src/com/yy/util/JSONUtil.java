package com.yy.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JSONUtil {
	public static void responseHtml(HttpServletResponse response, String str){
		response.setContentType("text/html;charset=utf-8");
		Writer writer = null;
		try {
			writer = response.getWriter();
			writer.write(str);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(writer != null){
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/***
	 * 返回前台html对象
	 * 
	 * @param response
	 * @param obj
	 * @return true发送成功，false发送失败
	 */
	public static boolean sendHtmlObject(HttpServletResponse response,
			Object obj) {
		response.setContentType("text/html;charset=utf-8");
		// 清空缓存
		response.setDateHeader("expires", 0);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("pragma", "no-cache");
		// 转换为JSON字符串
		String responseString = "";
		if (obj instanceof Collection) {
			responseString = JSONArray.toJSON(obj).toString();
		} else {
			responseString = JSONObject.toJSON(obj).toString();
		}
		try {
			PrintWriter out = response.getWriter();
			out.print(responseString.toString());
			out.flush();
			out.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();

			return false;
		}
	}

	/***
	 * 返回前台JSON对象
	 * 
	 * @param response
	 * @param obj
	 * @return true发送成功，false发送失败
	 */
	public static boolean sendJsonObject(HttpServletResponse response,
			Object obj) {
		response.setContentType("text/json;charset=utf-8");
		// 清空缓存
		response.setDateHeader("expires", 0);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("pragma", "no-cache");

		// 转换为JSON字符串
		String responseString = "";
		if (obj instanceof Collection) {
			responseString = JSONArray.toJSON(obj).toString();
		} else {
			responseString = JSONObject.toJSON(obj).toString();
		}

		try {
			PrintWriter out = response.getWriter();
			out.print(responseString);
			out.flush();
			out.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();

			return false;
		}
	}

	/***
	 * 返回前台JSONP对象
	 * 
	 * @param response
	 * @param obj
	 * @return true发送成功，false发送失败
	 */
	public static boolean sendJsonP(HttpServletResponse response, String str) {
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		// 清空缓存
		response.setDateHeader("expires", 0);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("pragma", "no-cache");
		try {
			response.getWriter().write(str);
			return true;
		} catch (IOException e) {
			e.printStackTrace();

			return false;
		}
	}

	public static String toJsonString(Object obj){
		JSONObject json = new JSONObject();
		return json.toJSONString(obj);
	}
	
	public static void main(String[] args) {
		/*List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("KEY", "value");
		list.add(obj);
		System.out.println(JSONArray.toJSON(list).toString());*/
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("1", "one");
		System.out.println(JSONUtil.toJsonString(map));;
	}
}
