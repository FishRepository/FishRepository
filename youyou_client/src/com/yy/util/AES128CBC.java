package com.yy.util;

import javax.crypto.Cipher;  
import javax.crypto.spec.IvParameterSpec;  
import javax.crypto.spec.SecretKeySpec;  

import org.apache.commons.codec.binary.Base64;
  
import sun.misc.BASE64Decoder;  
import sun.misc.BASE64Encoder;  
  
/**AES 是一种可逆加密算法，对用户的敏感信息加密处理 
* 对原始数据进行AES加密后，在进行Base64编码转化； 
*/  
public class AES128CBC {  
/* 
* 加密用的Key 可以用26个字母和数字组成 
* 此处使用AES-128-CBC加密模式，key需要为16位。 
*/   
    
    String content= "uGG0NsHmh95tYKsN3nK09goPixGlimAxJa42RX6bM/7ST/NRTkjgdDpICXqPpW+rCq5KEsSO+sAldI53Ht5lpEypHFGeUkJ9d31GSWN68qnyLI2APe12vRLTccH+/G9Rn9aq3Ib84v04AxYLc100opczYPupdqYAGJ1kLsVWYvmn5usdLPtsAkQpH9OhcUygc/Da2fxiuf6pz/vDI1cZyg==";  
    static String sKey="/FXv+IrQkpvWnVGxCZGaBg==";  
    static String ivParameter="3Vt1PmJElgPfujxdAukjWw==";  
    
    byte[] content_b = Base64.decodeBase64(content.getBytes());
	static byte[] key_b = Base64.decodeBase64(sKey.getBytes());
	static byte[] iv_b = Base64.decodeBase64(ivParameter.getBytes());
    
    private static AES128CBC instance=null;  
    private AES128CBC(){  
  
    }  
    public static AES128CBC getInstance(){  
        if (instance==null)  
            instance= new AES128CBC();  
        return instance;  
    }  
    // 加密  
    public String encrypt(String sSrc, String encodingFormat, byte[] key_b2, byte[] ivParameter) throws Exception {  
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  
        byte[] raw = key_b2;  
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");  
        IvParameterSpec iv = new IvParameterSpec(ivParameter);//使用CBC模式，需要一个向量iv，可增加加密算法的强度  
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);  
        byte[] encrypted = cipher.doFinal(sSrc.getBytes(encodingFormat));  
        return new BASE64Encoder().encode(encrypted);//此处使用BASE64做转码。  
}  
  
    // 解密  
    public String decrypt(String sSrc, String encodingFormat, byte[] key_b2, byte[] iv_b2) throws Exception {  
        try {  
            byte[] raw = Base64.decodeBase64(key_b2);  
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");  
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  
            IvParameterSpec iv = new IvParameterSpec(iv_b2);  
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);  
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);//先用base64解密  
            byte[] original = cipher.doFinal(encrypted1);  
            String originalString = new String(original,encodingFormat);  
            return originalString;  
        } catch (Exception ex) {  
            return null;  
        }  
}  
  
    public static void main(String[] args) throws Exception {  
        // 需要加密的字串  
        String cSrc = "uGG0NsHmh95tYKsN3nK09goPixGlimAxJa42RX6bM/7ST/NRTkjgdDpICXqPpW+rCq5KEsSO+sAldI53Ht5lpEypHFGeUkJ9d31GSWN68qnyLI2APe12vRLTccH+/G9Rn9aq3Ib84v04AxYLc100opczYPupdqYAGJ1kLsVWYvmn5usdLPtsAkQpH9OhcUygc/Da2fxiuf6pz/vDI1cZyg==";  
        System.out.println(cSrc);  
        // 加密  
        long lStart = System.currentTimeMillis();  
        String enString = AES128CBC.getInstance().encrypt(cSrc,"utf-8",key_b,iv_b);  
        System.out.println("加密后的字串是："+ enString);  
  
        long lUseTime = System.currentTimeMillis() - lStart;  
        System.out.println("加密耗时：" + lUseTime + "毫秒");  
        // 解密  
        lStart = System.currentTimeMillis();  
        String DeString = AES128CBC.getInstance().decrypt(enString,"utf-8",key_b,iv_b);  
        System.out.println("解密后的字串是：" + DeString);  
        lUseTime = System.currentTimeMillis() - lStart;  
        System.out.println("解密耗时：" + lUseTime + "毫秒");  
    }  
}