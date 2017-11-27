package com.yy.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import net.coobird.thumbnailator.Thumbnails;

import org.springframework.web.multipart.MultipartFile;

import sun.misc.BASE64Decoder;

public class Img64Util {
	public static byte[] GenerateImage(String img64Str) {
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] bytes = null;
		try {
			bytes = decoder.decodeBuffer(img64Str);
			for (int i = 0; i < bytes.length; i++) {
				if (bytes[i]<0) {
					bytes[i]+=256;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bytes;
	}
	public static String imgUrl(String img64Str, String path, String userId, String urlPath) throws IOException{
        hasFile(path);
		String y = Calendar.getInstance().get(Calendar.YEAR)+"";
		String m = Calendar.getInstance().get(Calendar.MONTH)+ 1 +"";
		String timeString = y+m;
		path += timeString + "/";
		hasFile(path);
		//String urlPath = "/pictures/";
		String pathString = null;
		String fileName = userId!=""?imgName(userId):new Date().getTime() + ".jpg";
		FileOutputStream os = new FileOutputStream(path + fileName);
		pathString = urlPath + timeString + "/" +fileName;
		os.write(GenerateImage(img64Str));
		os.flush();
		os.close();
		return pathString;
	}
	public static String imgName(String n){
		return MD5Util.string2MD5(n) + ".jpg";
	}
	private static boolean hasFile(String filename){
		File file =new File(filename);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}
		return true;
	}
	
	public static String updateFile(MultipartFile file, String path) throws IOException{
		System.out.println(file.getName());
		String y = Calendar.getInstance().get(Calendar.YEAR)+"";
		String m = Calendar.getInstance().get(Calendar.MONTH)+ 1 +"";
		String timeString = y+m;
		path += timeString + "/";
		hasFile(path);
		String urlPath = "/pictures/";
		String pathString = null;
		if (file != null && !file.isEmpty()) {
			System.out.println("文件长度: " + file.getSize());  
            System.out.println("文件类型: " + file.getContentType());  
            System.out.println("文件名称: " + file.getName());  
            System.out.println("文件原名: " + file.getOriginalFilename()); 
			InputStream is = file.getInputStream();
			String fileType = file.getOriginalFilename();
			System.out.println(fileType);
			String fileName = new Date().getTime() + fileType.substring(fileType.lastIndexOf("."),fileType.length());
			FileOutputStream os = new FileOutputStream(path + fileName);
			pathString = urlPath + timeString + "/" +fileName;
			int j = 0;
			while ((j = is.read()) != -1) {
				os.write(j);
			}
			Thumbnails.of(path + fileName)   
	        .size(750, 1333)  
	        .toFile(path + fileName);
			os.flush();
			os.close();
			is.close();
		}
		return pathString;
	}
}
