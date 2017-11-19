package com.yy.util;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

import net.sf.json.JSONArray;

import com.yy.wxCard.controller.apiConvert;



public class SHA1Util {
	/**/
	public static final String appid = "wxc7297bbde2f029da";
	public static final String secret = "81f574007ecd887ef5ffd6b05bc1abca";
	
	static String urlStr = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential" + 
	"&appid=" + appid +
	"&secret=" + secret;
	
	static String urlStr1 = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=wx_card";
	
	
	/*获取token*/
	public static JSONArray getToken(){
		String dString =  apiConvert.getConvert(urlStr);
		JSONArray jsonArray = JSONArray.fromObject("["+dString+"]");
		return jsonArray;
	}
	
	/*获取ticket*/
	public static JSONArray getTicket(Object object){
		String dString =  apiConvert.getConvert(urlStr1 +"&access_token="+object);
		JSONArray jsonArray = JSONArray.fromObject("["+dString+"]");
		return jsonArray;
	}
	
	/*随机码*/
	public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static String generateString(int length){
		StringBuffer stringBuffer = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			stringBuffer.append(allChar.charAt(random.nextInt(allChar.length())));
		}
		return stringBuffer.toString();
	}
	/*sha1加密*/
	public static String sha1(String str){
		if(null == str || 0 == str.length()){
			return null;
		}
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
	            'a', 'b', 'c', 'd', 'e', 'f'};
		try {
	        MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
	        mdTemp.update(str.getBytes("UTF-8"));
	         
	        byte[] md = mdTemp.digest();
	        int j = md.length;
	        char[] buf = new char[j * 2];
	        int k = 0;
	        for (int i = 0; i < j; i++) {
	            byte byte0 = md[i];
	            buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
	            buf[k++] = hexDigits[byte0 & 0xf];
	        }
	        return new String(buf);
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    }
		return "";
	}
}
