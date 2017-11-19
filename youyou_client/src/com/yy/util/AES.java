package com.yy.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;

public class AES {
	public static void main(String[] args) {  
        // TODO Auto-generated method stub  
  
        String content= "你好啊";  
        String key="/FXv+IrQkpvWnVGxCZGaBg==";  
        String iv="3Vt1PmJElgPfujxdAukjWw==";  
        
        byte[] content_b = Base64.decodeBase64(content.getBytes());
		byte[] key_b = Base64.decodeBase64(key.getBytes());
		byte[] iv_b = Base64.decodeBase64(iv.getBytes());
		
		System.out.println(content.getBytes());
		System.out.println("Base64解码后content------"+content_b);
		System.out.println("Base64解码后key------"+key_b);
		System.out.println("Base64解码后iv------"+iv_b);
		
		System.out.println("加密前："+byteToHexString(content_b)); 
		
		byte[ ] encrypted=AES_CBC_Encrypt(content_b, key_b, iv_b);  
		System.out.println("加密后："+byteToHexString(encrypted));  
		
		byte[ ] decrypted=AES_CBC_Decrypt(encrypted, key_b, iv_b);
		System.out.println("解密后："+ byteToHexString(decrypted));
          
   
    }  
      
    public static byte[] AES_CBC_Encrypt(byte[] content, byte[] keyBytes, byte[] iv){  
          
        try{  
            KeyGenerator keyGenerator=KeyGenerator.getInstance("AES");  
            keyGenerator.init(128, new SecureRandom(keyBytes));  
            SecretKey key=keyGenerator.generateKey();  
            Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");  
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));  
            byte[] result=cipher.doFinal(content);  
            return result;  
        }catch (Exception e) {  
            // TODO Auto-generated catch block  
            System.out.println("exception:"+e.toString());  
        }   
        return null;  
    }  
      
    public static byte[] AES_CBC_Decrypt(byte[] content, byte[] keyBytes, byte[] iv){  
          
        try{  
            KeyGenerator keyGenerator=KeyGenerator.getInstance("AES");  
            keyGenerator.init(128, new SecureRandom(keyBytes));//key长可设为128，192，256位，这里只能设为128  
            SecretKey key=keyGenerator.generateKey();  
            Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");  
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));  
            byte[] result=cipher.doFinal(content);  
            return result;  
        }catch (Exception e) {  
            // TODO Auto-generated catch block  
            System.out.println("exception:"+e.toString());  
        }   
        return null;  
    }  
          
    public static String byteToHexString(byte[] bytes) {  
        StringBuffer sb = new StringBuffer(bytes.length);  
        String sTemp;  
        for (int i = 0; i < bytes.length; i++) {  
            sTemp = Integer.toHexString(0xFF & bytes[i]);  
            if (sTemp.length() < 2)  
                sb.append(0);  
            sb.append(sTemp.toUpperCase());  
        }  
        return sb.toString();  
    }  
}
