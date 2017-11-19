/**
 * @author：sky
 * @company：重庆亚德科技
 * @date：2013-6-17 下午03:57:25
 */
package com.yy.util;

import javax.servlet.http.HttpSession;

public class MyToken {
	public static final String TOKEN = "myToken";
	public static final String reason = "系统警告，请勿重复点击操作按钮或浏览器后退按钮。如果信息填写错误，请重试，谢谢！";
	
	
	/**
	 * 生成token字符串，并存入session
	 */
	public static String getToken(HttpSession session){
		
		String myToken = new Long(System.currentTimeMillis()).toString();
		session.setAttribute(TOKEN, myToken);
		return myToken;
	}
	

	/**
	 * 验证传过来的token字符串，与存在session中的比较，如果匹配，则删除token
	 */
	public static boolean isTokenValid(String token, HttpSession session){
		if(session != null){
			String myToken = (String) session.getAttribute(TOKEN);
			if (myToken != null && myToken.equals(token)){
				session.removeAttribute(TOKEN);
				return true;
			}
		}
		return false;
	}
}

/*
在jsp页面端。
首先import该类：
<%@ page import="com.adtech.util.MyToken" %>
表单包含隐藏的token字符串:
<form>
<input type="hidden" name="<%=MyToken.TOKEN %>" value="<%=MyToken.getToken(session) %>">
</form>

在Servlet中添加如下代码。

if(MyToken.isTokenValid(request.getParameter(MyToken.TOKEN), request.getSession())){
//进行正常业务流程
}else{
//进行防重复提交处理流程，可以不做任何处理
}

<postfield name="<%=MyToken.TOKEN %>" value="<%=MyToken.getToken(session) %>" />

if(MyToken.isTokenStringValid(request.getParameter(MyToken.TOKEN), request.getSession()) == false){
	//重复点击按钮
	request.setAttribute("errorinfo", MyToken.reason);
	return new ModelAndView("pages/wap/errorinfo.jsp");
}

*/