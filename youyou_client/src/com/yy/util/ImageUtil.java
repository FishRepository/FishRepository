package com.yy.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ImageUtil {
	/**
	 * @Descriptionmap 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	 * @author zeer
	 * @param path
	 *            :图片路径
	 * @param b
	 *            :图片路径,是否加入头文件(data:image/jpg;base64,)
	 * @return
	 */
	public static String imageToBase64(String path, boolean b) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		byte[] data = null;
		// 读取图片字节数组
		try {
			InputStream in = new FileInputStream(path);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		// 返回Base64编码过的字节数组字符串
		// true返回的有头文件,false没有
		if (b) {
			return new StringBuilder("data:image/")
					.append(path.substring(path.lastIndexOf(".") + 1,
							path.length())).append(";base64,")
					.append(encoder.encode(data)).toString();
		} else {
			return encoder.encode(data);

		}
	}

	/**
	 * @Descriptionmap 对字节数组字符串进行Base64解码并生成图片
	 * @author zeer
	 * @param base64
	 *            图片Base64数据
	 * @param path
	 *            图片路径
	 * @return
	 */
	public static boolean base64ToImage(String base64, String path) {// 对字节数组字符串进行Base64解码并生成图片
		if (base64 == null) { // 图像数据为空
			return false;
		} else if (base64.indexOf("data:image") != -1
				&& base64.indexOf("base64,") != -1) {// 包含base64文件头时,去掉
			base64 = base64.substring(base64.indexOf("base64,") + 7,
					base64.length());
		}
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] bytes = decoder.decodeBuffer(base64);
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {// 调整异常数据
					bytes[i] += 256;
				}
			}
			// 生成jpeg图片
			OutputStream out = new FileOutputStream(path);
			out.write(bytes);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static void main(String[] args) {
		String base64 = ImageUtil.imageToBase64(
				"E:\\Down\\14237944_1200x1000_0.jpg", true);

		ImageUtil.base64ToImage(
				"data:image/jpg;base64,/9j/4AAQSkZJRgABAQEAZABkAAD/",
				"E:\\123.jpg");

		System.out.println(base64);

	}
}
