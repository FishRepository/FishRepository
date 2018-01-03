package com.yy.util;

import com.alibaba.citrus.util.StringUtil;
import com.baidu.aip.speech.AipSpeech;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class BaiduSpeech {

    //设置APPID/AK/SK
    public static final String APP_ID = "10588181";
    public static final String API_KEY = "dP6hGay5H8dWqI2PjCrQ0aM2";
    public static final String SECRET_KEY = "ad571ee483a558215d0080327780fd47";

    public static String voice2word(String sourcePath, String lan){
        // 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理
        // 调用接口
        HashMap<String, Object> options = new HashMap<>();
        options.put("lan",lan);
        JSONObject res = client.asr(sourcePath, "wav", 16000, options);
        String result = "";
        if(null != res){
            if(!res.has("err_msg")){
                return "";
            }
            result = res.getString("err_msg");
            if(StringUtil.isBlank(result)){
                return "";
            }
            if(!result.equals("success.")){
                return "";
            }
            JSONArray result1 = (JSONArray)res.get("result");
            if(null==result1||result1.length()==0||null==result1.get(0)){
                return "";
            }
            String str = result1.get(0).toString();
            result =str.substring(0,str.length()-1);
        }
        return result;
    }

    public static void main(String[] args) {
        String filePath = "D:\\gitSpace\\Seework\\youyou_service\\WebRoot\\images\\file.wav";
        System.out.println(voice2word(filePath,"zh"));
    }
}
