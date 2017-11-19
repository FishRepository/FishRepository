package com.yy.wxCard.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class apiConvert {
	
	public static String getConvert(String urlStr){
		String data = null;
		try {
			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			//连接超时
			connection.setConnectTimeout(20000);
			//读取数据超时
			connection.setReadTimeout(19000);
			connection.connect();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
			String line;
			StringBuilder stringBuilder = new StringBuilder();
			while ((line = bufferedReader.readLine())!=null) {
				stringBuilder.append(line);
			}
			bufferedReader.close();
			connection.disconnect();
			data = stringBuilder.toString();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(data);
		return data;
	}
}
