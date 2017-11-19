package com.yy.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SendMsg {
	
	private static ExecutorService pool = null;
	
	static {
		String fixedThreadPoolNum = Utility.getProps("/reg.properties", "fixedThreadPoolNum");
		int num;
		try {
			num = Integer.parseInt(fixedThreadPoolNum);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			num = 3;
		}
		if(pool == null){
			pool = Executors.newFixedThreadPool(num);
		}
	}
	
	public static void sendMessage(String mobile,String message){
		pool.execute(new MyThread(mobile, message));
	}
	
	public static void main(String[] args) {
		System.out.println("!11111111111111111111");
		sendMessage("18680894255","te st\n32 3");
		System.out.println("!1222222222222222222");
	}
}

class MyThread implements Runnable {

	private String mobile;
	private String message;
	
	MyThread(String mobile, String message){
		this.mobile= mobile;
		this.message = message;
	}
	
	@Override
	public void run() {
		String iurl = "http://www.jkwin.com.cn/telecomSmsSendService/send_sys_sms.do?method=sendSms&userName=adtechJkwin&passWord=adtechJkwin";
		iurl += "&telephone=" + mobile;
		try {
			String shortMessage = URLEncoder.encode(message, "UTF-8");
			iurl += "&shortMessage=" + shortMessage;
			
			System.out.println(iurl);
			URL url = new URL(iurl);
			URLConnection conn = url.openConnection();
			InputStream is = conn.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(is,"UTF-8"));
			String jsonString = null;
			while((jsonString = in.readLine())!=null){
				System.out.println(jsonString);
			}
			System.out.println(Thread.currentThread().getName()+"=>发短信完成！=>mobile="+mobile+",message="+message);
		} catch (IOException e) {
			System.out.println(Thread.currentThread().getName()+"=>发短信异常！=>mobile="+mobile+",message="+message);
			e.printStackTrace();
		}
	}
}
