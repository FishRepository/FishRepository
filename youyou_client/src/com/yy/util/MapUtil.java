package com.yy.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class MapUtil {
	/**
	 * 计算地球上任意两点(经纬度)距离
	 * 
	 * @param long1
	 *            第一点经度
	 * @param lat1
	 *            第一点纬度
	 * @param long2
	 *            第二点经度
	 * @param lat2
	 *            第二点纬度
	 * @return 返回距离 单位：米
	 */
	public static double Distance(double long1, double lat1, double long2,
			double lat2) {
		double a, b, R;
		R = 6378137; // 地球半径
		lat1 = lat1 * Math.PI / 180.0;
		lat2 = lat2 * Math.PI / 180.0;
		a = lat1 - lat2;
		b = (long1 - long2) * Math.PI / 180.0;
		double d;
		double sa2, sb2;
		sa2 = Math.sin(a / 2.0);
		sb2 = Math.sin(b / 2.0);
		d = 2
				* R
				* Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
						* Math.cos(lat2) * sb2 * sb2));
		System.out.println(d);
		return d;
	}

	/**
	 * 根据地址获取百度经纬度
	 * 
	 * @param address,city
	 * @return
	 */
	public static Map<String, Double> getLngAndLat(String address, String city) {
		Map<String, Double> map = new HashMap<String, Double>();
		String url = "http://api.map.baidu.com/geocoder/v2/?address=" + address
				+ "&city=" + city
				+ "&output=json&ak=CNbyQjrxExuF9hlsLd0XTWIzwDnDlsda";

		String json = loadJSON(url);
		JSONObject obj = JSONObject.parseObject(json);
		if (obj.get("status").toString().equals("0")) {
			double lng = obj.getJSONObject("result").getJSONObject("location")
					.getDouble("lng");
			double lat = obj.getJSONObject("result").getJSONObject("location")
					.getDouble("lat");

			System.out.println("百度经度：" + lng + "---百度纬度：" + lat);

			map.put("lng", lng);
			map.put("lat", lat);
		} else {
			System.out.println("未找到相匹配的经纬度！");
		}
		return map;
	}

	/**
	 * 根据GPS经纬度获取百度经纬度
	 * 
	 * @param lngGps,latGps
	 * @return
	 */
	public static Map<String, Double> getLngAndLat4Gps(double lngGps,
			double latGps) {
		Map<String, Double> map = new HashMap<String, Double>();
		String url = "http://api.map.baidu.com/geoconv/v1/?coords=" + lngGps
				+ "," + latGps
				+ "&from=1&to=5&ak=CNbyQjrxExuF9hlsLd0XTWIzwDnDlsda";
		String json = loadJSON(url);
		JSONObject obj = JSONObject.parseObject(json);
		if (obj.get("status").toString().equals("0")) {
			JSONArray ja = obj.getJSONArray("result");
			double lng =ja.getJSONObject(0).getDouble("x");
			double lat = ja.getJSONObject(0).getDouble("y");

			System.out.println("百度经度：" + lng + "---百度纬度：" + lat);
			map.put("lng", lng);
			map.put("lat", lat);
		} else {
			System.out.println("未找到相匹配的经纬度！");
		}
		return map;
	}

	public static String loadJSON(String url) {
		StringBuilder json = new StringBuilder();
		try {
			URL oracle = new URL(url);
			URLConnection yc = oracle.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					yc.getInputStream()));
			String inputLine = null;
			while ((inputLine = in.readLine()) != null) {
				json.append(inputLine);
			}
			in.close();
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
		return json.toString();
	}

	public static void main(String[] args) {
		Map<String, Double> map1 = MapUtil
				.getLngAndLat("重庆市大渡口区翠华园", "重庆市");

		Map<String, Double> map2 = MapUtil.getLngAndLat4Gps(106.488516,
				29.530804);

		MapUtil.Distance(map2.get("lng"), map2.get("lat"), map1.get("lng"),
				map1.get("lat"));

	}

}
