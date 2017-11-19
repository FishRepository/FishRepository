package com.yy.wxCard.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.yy.util.SHA1Util;


@Controller("/wxCard.do")
public class wxCardController {
	//String signString =  SHA1Util.sha1("abc");
	@RequestMapping(params="method=getSign", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getSign(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String, Object>();

		HttpSession session = request.getSession();
		session.setAttribute("ticket", "");
		String sessionId = session.getId();
		session.setMaxInactiveInterval(60*60*2);
		long timestamp = new Date().getTime();
		String api_ticket;
		String nonce_str = SHA1Util.generateString(30);
		String card_id = request.getParameter("card_id");
		
		if (session.isNew()) {
			List<Map<String, Object>> tokenData =  SHA1Util.getToken();
			JSONArray tokenArray = JSONArray.fromObject(tokenData);
			List<Map<String, Object>> ticketData = SHA1Util.getTicket(tokenArray.getJSONObject(0).get("access_token"));
			JSONArray tickenArray = JSONArray.fromObject(ticketData);
			session.setAttribute("ticket", tickenArray.getJSONObject(0).get("ticket"));
			api_ticket = (String) tickenArray.getJSONObject(0).get("ticket");
		} else {
			api_ticket = (String) session.getAttribute("ticket");
		}
		resultMap.put("cardId", card_id);
		resultMap.put("cardExt", "{'code': '', 'openid': '', 'timestamp': " + timestamp + ", 'signature':" + SHA1Util.sha1(timestamp+api_ticket+nonce_str+card_id) + "}");
		return resultMap;
	}
}
