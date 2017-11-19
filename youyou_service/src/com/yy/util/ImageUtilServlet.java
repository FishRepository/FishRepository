package com.yy.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ImageUtilServlet extends HttpServlet {

	private static final long serialVersionUID = -629251554085870520L;

	private static final String[] chars = { "A", "B", "C", "D", "E", "F", "G",
			"H", "J", "K", "L", "M", "N", "P", "R", "S", "T", "U", "X", "Y",
			"Z" };
	private static final int SIZE = 5;// 字符长度
	private static final int LINES = 8;// 干扰线
	private static final int WIDTH = 120;
	private static final int HEIGHT = 30;
	private static final int FONT_SIZE = 30;// 字体大小

	public static Color getRandomColor() {
		Random ran = new Random();
		Color color = new Color(ran.nextInt(156), ran.nextInt(156),
				ran.nextInt(156));
		return color;
	}

	@Override
	protected void doGet(HttpServletRequest reqeust,
			HttpServletResponse response) throws ServletException, IOException {
		// 设置响应的类型格式为图片格式
		response.setContentType("image/jpeg");
		// 禁止图像缓存。
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		HttpSession session = reqeust.getSession();

		BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		Graphics graphic = image.getGraphics();
		graphic.setColor(Color.LIGHT_GRAY);
		graphic.fillRect(0, 0, WIDTH, HEIGHT);
		Random ran = new Random();
		StringBuilder sb = new StringBuilder("");
		// 画随机字符
		for (int i = 1; i <= SIZE; i++) {

			int r = ran.nextInt(chars.length);
			graphic.setColor(getRandomColor());
			graphic.setFont(new Font(null, Font.BOLD + Font.ITALIC, FONT_SIZE));
			graphic.drawString(chars[r], (i - 1) * WIDTH / SIZE, 26);
			sb.append(chars[r]);
		}

		String uuid = reqeust.getParameter("uuid");
		// 将字符保存，存入Session
		session.setAttribute("ValidateCodeServlet" + uuid, sb.toString()
				.toLowerCase());
		System.out.println("ValidateCodeServlet" + uuid + "====="
				+ sb.toString().toLowerCase());

		// 画干扰线
		for (int i = 1; i <= LINES; i++) {
			graphic.setColor(getRandomColor());
			graphic.drawLine(ran.nextInt(WIDTH), ran.nextInt(HEIGHT),
					ran.nextInt(WIDTH), ran.nextInt(HEIGHT));
		}
		// 图象生效
		graphic.dispose();

		// 输出图象到页面
		ServletOutputStream sos = response.getOutputStream();
		ImageIO.write(image, "JPEG", sos);
		sos.flush();
		sos.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
