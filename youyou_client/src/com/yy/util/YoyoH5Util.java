package com.yy.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;

public class YoyoH5Util {
	private static String keyString = "7f86b2d529779abf05543da15be00eb5";
	private static String appid = "wxf6e6b87468a8357d";  //公众号
	private static String secret = "e7084d91bead78acebbd3cfe3f025040";
	private static String mch_id = "1482835582";  //商户号
	private static String body = "武汉游友科技有限公司"; //Yoyo-游友科技-
	private static String spbill_create_ip = "172.18.36.67"; //172.18.36.67
	private static String notify_url = "https://www.yoyotec.com";
	private static String trade_type = "MWEB";
	private static String apiUrlStr = "https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+secret+"&grant_type=authorization_code&js_code=";
	
	//生成25位随机字符串
	public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public String generateString(int length){
		StringBuffer stringBuffer = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			stringBuffer.append(allChar.charAt(random.nextInt(allChar.length())));
		}
		return stringBuffer.toString();
	}
	
	//api接口封装
	public static String getConvert(String urlStr){
		urlStr=apiUrlStr+urlStr;
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
	
	//生成签名sign
	public String returnSign(String nonce_str,String body, String orderNumb, String openid, String total_fee, String time_start, String trade_type, String ipString) throws Exception{
		String parmString = "appid=" + appid +
				"&body=" + body +
				"&mch_id=" + mch_id +
				"&nonce_str="+nonce_str +
				"&notify_url="+notify_url +
				"&openid="+openid +
				"&out_trade_no="+orderNumb +
				"&spbill_create_ip="+ipString +
				"&time_start="+time_start +
				"&total_fee="+total_fee +
				"&trade_type="+trade_type;
		String stringSignTemp = parmString+"&key="+keyString;
		return MD5Util.MD5(stringSignTemp).toUpperCase();
	}
	
	//生成订单号
	public String returnOrderNumb() {
		String orderString = new SimpleDateFormat("yyyyMMddHH").format(new Date()); 
		String charString = new YoyoH5Util().generateString(5);
		orderString = "D"+orderString;
		orderString+=charString;
		return orderString;
	}
	
	//生成订单时间
	public String returnOrderTime() {
		String orderTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); 
		return orderTime;
	}
	
	/*xml字符串*/
	private static String getXmlInfo(String payClass, String payMoney, String orderNumb, String orderTime, String openid, String ipString) throws Exception {
        StringBuilder sb = new StringBuilder();  
        String nonce_str = new YoyoH5Util().generateString(24);
        String sign = new YoyoH5Util().returnSign(nonce_str, body+payClass, orderNumb, openid, payMoney, orderTime, trade_type, ipString);
        sb.append("<xml>");  
        sb.append("<appid>"+appid+"</appid>"); 
        sb.append("<body>"+body+payClass+"</body>");//商品描述 payClass
        sb.append("<mch_id>"+mch_id+"</mch_id>");  
        sb.append("<nonce_str>"+nonce_str+"</nonce_str>");  //随机字符串
        sb.append("<notify_url>"+notify_url+"</notify_url>"); //通知地址
        sb.append("<openid>"+openid+"</openid>");  //交易类型
        sb.append("<out_trade_no>"+orderNumb+"</out_trade_no>"); //商户订单号 
        sb.append("<spbill_create_ip>"+ipString+"</spbill_create_ip>");  //终端IP
        sb.append("<time_start>"+orderTime+"</time_start>"); 
        sb.append("<total_fee>"+payMoney+"</total_fee>");  //总金额
        sb.append("<trade_type>"+trade_type+"</trade_type>");  //交易类型
		sb.append("<scene_info>{\"h5_info\": {\"type\":\"Wap\",\"wap_url\": \"https://www.yoyotec.com\",\"wap_name\": \"武汉游友科技有限公司\"}}</scene_info>");
        sb.append("<sign>"+sign+"</sign>");  //签名
        sb.append("</xml>"); 
        return sb.toString();  
    }  
	/*xml请求*/
	public static String doPost(String urlStr, String strInfo) {  
        String reStr="";  
        try {  
            URL url = new URL(urlStr);  
            URLConnection con = url.openConnection();  
            con.setDoOutput(true);  
            con.setRequestProperty("Pragma:", "no-cache");  
            con.setRequestProperty("Cache-Control", "no-cache");  
            con.setRequestProperty("Content-Type", "text/xml");  
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());  
            out.write(new String(strInfo.getBytes("utf-8")));  
            out.flush();  
            out.close();  
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));  
            String line = "";  
            for (line = br.readLine(); line != null; line = br.readLine()) {
                reStr += line;  
            }  
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return reStr;  
    } 
	
	
	public static Map<String, Object> xml2map(String protocolXML) {  
		 Map<String, Object> resMap = new HashMap<String, Object>();
        try {   
             Document doc=(Document)DocumentHelper.parseText(protocolXML);   
             Element books = doc.getRootElement();
             Iterator Elements = books.elementIterator();   
            while(Elements.hasNext()){   
                Element user = (Element)Elements.next();
                resMap.put(user.getName(), user.getText());   
            }   
         } catch (Exception e) {   
             e.printStackTrace();   
         }
        return resMap;
     }  
	
	
	//生成支付用的package参数
	public Map<String, Object> returnPackage(String payClass, String payMoney, String orderNumb, String orderTime, String openid, String ipString) throws Exception{
		String urlAPI = "https://api.mch.weixin.qq.com/pay/unifiedorder"; 
        String result = doPost(urlAPI, getXmlInfo(payClass, payMoney, orderNumb, orderTime, openid, ipString));
        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap = xml2map(result);
        return resMap;
	}
}
