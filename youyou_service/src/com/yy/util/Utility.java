package com.yy.util;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

//import com.adtech.e2qframe.core.util.Utility;

public class Utility {

	static final int[] wi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4,
			2, 1 };
	static final int[] vi = { 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 };
	static private int[] ai = new int[18];
	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat sdfshort = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static final SimpleDateFormat sdfsss = new SimpleDateFormat(
			"yyyyMMddHHmmssSSS");
	private static final Random randoms = new Random();

	/**
	 * yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		if (date == null)
			return "0000-00-00 00:00:00";

		return sdf.format(date);
	}

	public static String randNum(int n) {
		String rand = "";
		for (int i = 0; i < n; i++) {
			String rand_temp = String.valueOf(randoms.nextInt(10));
			rand += rand_temp;
		}
		return rand;
	}

	/**
	 * 获取时间随机数序号
	 * 
	 * @return
	 */
	public static String getNowDateRandomNumber() {
		return sdfsss.format(new Date()) + randoms.nextInt(10000);
	}

	/**
	 * yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String shortFormatDate(Date date) {
		if (date == null)
			return "00000000";

		return sdfshort.format(date);
	}

	/**
	 * yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static Date shortParseDate(String str) throws ParseException {
		if (str == null) {
			return null;
		}
		Date date = sdfshort.parse(str);
		return date;
	}

	/**
	 * yyyy-MM-dd HH:mm:ss
	 * 
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String str) throws ParseException {
		if (str == null)
			return null;

		Date date = sdf.parse(str);

		return date;
	}

	/**
	 * 判断字符是否是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumberString(String str) {
		if (str == null || str.length() == 0)
			return false;

		int i = 0;

		while (true) {
			if (i >= str.length())
				break;

			char ch = str.charAt(i);

			if (ch > '9' || ch < '0')
				break;

			i++;
		}

		return i == str.length();
	}

	/**
	 * 用户名为字母、数字或下划线、并不能以数字打头和纯数字
	 * 
	 * @param userName
	 * @return
	 */
	public static boolean isUserName(String userName) {
		boolean is = true;
		String regEx = "^[a-zA-Z]{1}([a-zA-Z0-9]|[_]){3,15}$";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(userName);
		is = m.find();
		return is;
	}

	/**
	 * 判断是否是邮箱地址
	 * 
	 * @param eMail
	 * @return
	 */
	public static boolean isEmail(String eMail) {
		boolean is = true;
		String regEx = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(eMail);
		is = m.find();
		return is;
	}

	/**
	 * 防SQL注入
	 * 
	 * @param sqlStr
	 * @return
	 */
	public static String escapeSql(String sqlStr) {
		sqlStr = StringEscapeUtils.escapeSql(sqlStr);
		return sqlStr;
	}

	/**
	 * 过滤request
	 * 
	 * @param sqlStr
	 * @return
	 */
	public static String requestEscape(String str) {
		if (str == null) {
			return null;
		}
		str = Utility.escapeSql(str);
		// str = Utility.wipescript(str);
		// str = str.replaceAll("[\\t\\n\\r]", " ");
		return str;
	}

	public static String wipescript(String html) {
		Pattern pattern1 = Pattern.compile("<script[^>]+>.*<\\/script>");
		Pattern pattern2 = Pattern.compile(" href *= *[\\s\\s]*script *:");
		Pattern pattern3 = Pattern.compile(" on[\\s\\s]*=");
		Pattern pattern4 = Pattern.compile("<iframe[\\s\\s]+</iframe *>");
		Pattern pattern5 = Pattern.compile("<frameset[\\s\\s]+</frameset *>");
		Matcher matcher1 = pattern1.matcher(html);
		html = matcher1.replaceAll(" ");
		Matcher matcher2 = pattern2.matcher(html);
		html = matcher2.replaceAll(" ");
		Matcher matcher3 = pattern3.matcher(html);
		html = matcher3.replaceAll(" ");
		Matcher matcher4 = pattern4.matcher(html);
		html = matcher4.replaceAll(" ");
		Matcher matcher5 = pattern5.matcher(html);
		html = matcher5.replaceAll(" ");
		html = html.replace("<", "");
		return html;
	}

	/** 生成序号 */
	public static String getRedPagerNo(String str) {
		String no = Utility.formatDate(new Date());
		no = no.replaceAll("-", "");
		no = no.replaceAll(":", "");
		no = no.replaceAll(" ", "");
		String sRand = "";
		for (int i = 0; i < 5; i++) {
			String rand = String.valueOf(randoms.nextInt(10));
			sRand += rand;
		}
		return str + no + sRand;
	}

	/**
	 * 生成uuid
	 * 
	 * @return
	 */
	public static String randomUUID() {
		String uuid = java.util.UUID.randomUUID().toString();
		uuid = uuid.toUpperCase();
		uuid = uuid.replaceAll("-", "_");
		return uuid;
	}

	public static Long dateToLong(Date date) {
		String strDate = formatDate(date);
		strDate = strDate.replaceAll("-", "");
		strDate = strDate.replaceAll(":", "");
		strDate = strDate.replaceAll(" ", "");
		return Long.valueOf(strDate);
	}

	/**
	 * 判断是否数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNum(String str) {
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	/**
	 * 获取分页参数
	 * 
	 * @param request
	 * @return
	 */
	public static HashMap<String, Object> getPagerParameter(
			HttpServletRequest request) {
		// 参数Map
		// Map properties = request.getParameterMap();
		// 当前页
		long intPage = Long
				.parseLong((request.getParameter("page") == null || request
						.getParameter("page").toString().equals("null")) ? "1"
						: Utility.requestEscape(request.getParameter("page")));
		// System.out.println(intPage);
		// 每页显示条数
		// System.out.println(request.getParameter("rows"));
		long number = Long
				.parseLong((request.getParameter("rows") == null || request
						.getParameter("rows").toString().equals("null")) ? "20"
						: Utility.requestEscape(request.getParameter("rows")));
		// System.out.println(number);
		// 每页的开始记录 第一页为1 第二页为number +1
		long start = (intPage - 1) * number;
		HashMap<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("page", intPage);
		parameter.put("rows", number);
		parameter.put("MIN_ROWS", start);
		parameter.put("MAX_ROWS", start + number);
		parameter.put("ORDER_BY_MENU",
				Utility.requestEscape(request.getParameter("sort")));
		parameter.put("ORDER_BY_DESC",
				Utility.requestEscape(request.getParameter("order")));
		return parameter;
	}

	/**
	 * 返回数据,主要用于ajax或$.post
	 * 
	 * @param response
	 * @param obj
	 * @throws IOException
	 */
	public static void getPagerResponse(HttpServletResponse response, Object obj)
			throws IOException {
		// 清空缓存
		response.setDateHeader("expires", 0);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("pragma", "no-cache");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.append(obj.toString());
		pw.flush();
		pw.close();
	}

	/**
	 * 遍历 Map<String, Object> 将Clob对象转换成String
	 * 
	 * @param tempMap
	 * @return
	 */
	public static Map<String, Object> getClob(Map<String, Object> tempMap) {
		Iterator keys = tempMap.keySet().iterator();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			Object fliedValue = tempMap.get(key);
			if (ValUtil.objNotNull(fliedValue)
					&& fliedValue.getClass().getName()
							.equals("oracle.sql.CLOB")) {
				Reader br = null;
				try {
					Method getValueMethod = fliedValue.getClass()
							.getDeclaredMethod("getCharacterStream", null);
					br = (Reader) getValueMethod.invoke(fliedValue);
					char[] datas = new char[10240];
					int temp;
					StringBuffer sbf = new StringBuffer("");
					while ((temp = br.read(datas)) != -1) {
						sbf.append(new String(datas, 0, temp));
					}
					tempMap.put(key, sbf.toString());
				} catch (Exception e1) {
					e1.printStackTrace();
				} finally {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				continue;
			}
		}
		return tempMap;
	}

	/**
	 * oracle BLOB to file
	 * 
	 * @param tempMap
	 * @return
	 */
	public static String getBlob(Object fliedValue, String newfile) {
		if (ValUtil.objNotNull(fliedValue)
				&& fliedValue.getClass().getName().equals("oracle.sql.BLOB")) {
			InputStream is = null;
			FileOutputStream fos = null;
			try {
				int ch = 0;
				fos = new FileOutputStream(newfile);
				Method getValueMethod = fliedValue.getClass()
						.getDeclaredMethod("getBinaryStream", null);
				is = (InputStream) getValueMethod.invoke(fliedValue);
				while ((ch = is.read()) != -1) {
					fos.write(ch);
				}
				fos.flush();
			} catch (Exception e1) {
				e1.printStackTrace();
				return null;
			} finally {
				try {
					if (null != is) {
						is.close();
					}
					if (null != fos) {
						fos.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}

		return newfile;
	}

	public static String getProps(String uri, String propName) {
		String propValue = "";
		InputStream is = (new Utility()).getClass().getResourceAsStream(uri);
		Properties props = new Properties();
		try {
			props.load(is);
			is.close();
			propValue = props.getProperty(propName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return propValue;
	}

	// 根据身份证取性别
	public static int getSexByIdCard(String idCard) {
		try {
			int length = idCard.length();
			int i = Integer.valueOf(idCard.substring(length - 2, length - 1));
			if (15 == length) {
				i = Integer.valueOf(idCard.substring(length - 1, length));
			}
			if (i % 2 == 0) {
				return 2;
			}
			return 1;
		} catch (Exception e) {
			return 1;
		}
	}

	// 根据身份证取出生日期
	public static String getBirthByIdCard(String idCard) {
		try {
			int length = idCard.length();
			String year = "";
			String month = "";
			String day = "";
			if (15 == length) {
				year = idCard.substring(6, 8);
				year = (Integer.valueOf(year) > 10 ? ("19" + year)
						: ("20" + year)) + "-";
				month = idCard.substring(8, 10) + "-";
				day = idCard.substring(10, 12);
			}
			if (18 == length) {
				year = idCard.substring(6, 10) + "-";
				month = idCard.substring(10, 12) + "-";
				day = idCard.substring(12, 14);
			}
			return year + month + day;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 校验身份证的合法性
	 * 
	 * @param idcard
	 * @return
	 */
	public static boolean Verify(String idcard) {
		boolean flag = false;
		try {
			if (idcard.length() == 15) {
				idcard = uptoeighteen(idcard);
			}
			if (idcard.length() != 18) {
				flag = false;
			}

			String verify = idcard.substring(17, 18);
			String birth = idcard.substring(6, 10) + "-"
					+ idcard.substring(10, 12) + "-" + idcard.substring(12, 14);
			if (Utility.isNum(idcard)) {
				if (verify.equals(getVerify(idcard))) {
					flag = true;
				}
			} else {
				flag = false;
			}
		} catch (EmptyStackException e) {
			flag = false;
		}
		return flag;
	}

	private static String getVerify(String eightcardid) {
		int remaining = 0;
		if (eightcardid.length() == 18) {
			eightcardid = eightcardid.substring(0, 17);
		}
		if (eightcardid.length() == 17) {
			int sum = 0;
			for (int i = 0; i < 17; i++) {
				String k = eightcardid.substring(i, i + 1);
				ai[i] = Integer.parseInt(k);
			}
			for (int i = 0; i < 17; i++) {
				sum = sum + wi[i] * ai[i];
			}
			remaining = sum % 11;
		}
		return remaining == 2 ? "X" : String.valueOf(vi[remaining]);
	}

	private static String uptoeighteen(String fifteencardid) {
		String eightcardid = fifteencardid.substring(0, 6);
		eightcardid = eightcardid + "19";
		eightcardid = eightcardid + fifteencardid.substring(6, 15);
		eightcardid = eightcardid + getVerify(eightcardid);
		return eightcardid;
	}
	
	
	/**
     * 将json对象转换成Map
     *
     * @param jsonObject json对象
     * @return Map对象
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> toMap(com.alibaba.dubbo.common.json.JSONObject jsonObject)
    {
        Map<String, String> result = new HashMap<String, String>();
        Iterator<String> iterator = jsonObject.keys();
        String key = null;
        String value = null;
        while (iterator.hasNext())
        {
            key = iterator.next();
            value = jsonObject.getString(key);
            result.put(key, value);
        }
        return result;
    }

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			/*
			 * for (String key : map.keySet()) { System.out.println(key + "--->"
			 * + map.get(key)); }
			 */
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

}