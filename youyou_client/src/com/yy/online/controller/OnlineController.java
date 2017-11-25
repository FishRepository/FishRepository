package com.yy.online.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.yy.online.service.OnlineService;
import com.yy.util.Img64Util;
import com.yy.util.YoyoUtil;

@Controller("admin.do")
public class OnlineController {
	@Autowired
	private OnlineService onlineService;
	
	/*初始化解析币*/
	String coin = "5";
	
	/*用户收集WeChat*/
	@RequestMapping(params="method=regUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> regUser (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("WID", request.getParameter("WID"));
		parmMap.put("NAME", request.getParameter("NAME"));
		parmMap.put("SEX", request.getParameter("SEX"));
		parmMap.put("COIN", coin);
		resultMap = onlineService.regUser(parmMap);
		return resultMap;
	}
	
	/*index*/
	@RequestMapping(params="method=index", method = RequestMethod.GET)
	public String indexPage (){
		return "yoyo-admin/index.jsp";
	}
	/*login*/
	@RequestMapping(params="method=login", method = RequestMethod.GET)
	public String loginPage (){
		return "yoyo-admin/login.jsp";
	}
	/*posts*/
	@RequestMapping(params="method=post", method = RequestMethod.GET)
	public String postPage (){
		return "yoyo-admin/posts.jsp";
	}
	/*topics*/
	@RequestMapping(params="method=topic", method = RequestMethod.GET)
	public String topicPage (){
		return "yoyo-admin/topics.jsp";
	}
	/*user_list*/
	@RequestMapping(params="method=user_list", method = RequestMethod.GET)
	public String userList (){
		return "yoyo-admin/user_list.jsp";
	}
	
	/*添加岗位*/
	@RequestMapping(params="method=addPost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPost (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("NAME", request.getParameter("postInput"));
		resultMap = onlineService.addPost(parmMap);
		return resultMap;
	}
	
	/*获取岗位*/
	@RequestMapping(params="method=getPost", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getPost (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = onlineService.getPost(parmMap);
		return resultMap;
	}
	
	/*添加证书*/
	@RequestMapping(params="method=addCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCert (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("NAME", request.getParameter("certInput"));
		parmMap.put("CLASS", request.getParameter("postType"));
		parmMap.put("SHARE_TYPE", request.getParameter("shareType"));
		parmMap.put("TOTAL_SCORE", request.getParameter("totalScore"));
		parmMap.put("PASS_SCORE", request.getParameter("passScroe"));
		parmMap.put("EXAM_TIME", request.getParameter("examTime"));
		parmMap.put("TOPIC_TYPE_A", request.getParameter("certType1"));
		parmMap.put("TOPIC_TYPE_A_SCORE", request.getParameter("certTypeScore1"));
		parmMap.put("TOPIC_TYPE_B", request.getParameter("certType2"));
		parmMap.put("TOPIC_TYPE_B_SCORE", request.getParameter("certTypeScore2"));
		parmMap.put("TOPIC_TYPE_C", request.getParameter("certType3"));
		parmMap.put("TOPIC_TYPE_C_SCORE", request.getParameter("certTypeScore3"));
		resultMap = onlineService.addCert(parmMap);
		return resultMap;
	}
	
	/*获取所有证书*/
	@RequestMapping(params="method=getCert", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getCert (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = onlineService.getCert(parmMap);
		return resultMap;
	}
	
	/*获取所有证书*/
	@RequestMapping(params="method=getCertByPost", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getCertByPost (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("CLASS", request.getParameter("postId"));
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = onlineService.getCertByPost(parmMap);
		return resultMap;
	}
	
	/*导入topic*/
	@RequestMapping(params="method=addXls", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addXls (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("PAY_TYPE", request.getParameter("pays_type"));
		parmMap.put("POST_TYPE", request.getParameter("post_type"));
		parmMap.put("CERT_TYPE", request.getParameter("cert_type"));
		parmMap.put("CERT_TEXT", request.getParameter("cert_text"));
		parmMap.put("CERT_CLASS", request.getParameter("cert_class"));
		parmMap.put("TYPE", request.getParameter("type"));
		parmMap.put("ARTICLE", request.getParameter("article")!=null?request.getParameter("article"):"");
		parmMap.put("TITLE", request.getParameter("title"));
		parmMap.put("IMAGE", request.getParameter("image") != null?request.getParameter("image"):"");
		parmMap.put("OPTION_A", request.getParameter("option_a") != null?request.getParameter("option_a"):"");
		parmMap.put("OPTION_B", request.getParameter("option_b") != null?request.getParameter("option_b"):"");
		parmMap.put("OPTION_C", request.getParameter("option_c") != null?request.getParameter("option_c"):"");
		parmMap.put("OPTION_D", request.getParameter("option_d") != null?request.getParameter("option_d"):"");
		parmMap.put("EXPLAIN_TEXT", request.getParameter("explain") != null?request.getParameter("explain"):"");
		parmMap.put("RIGHT_OPTION", request.getParameter("right_option"));
		resultMap = onlineService.addXls(parmMap);
		return resultMap;
	}
	
	/*搜索试题*/
	@RequestMapping(params="method=searchTopic", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> searchTopic (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("PAY_TYPE", request.getParameter("pay_type"));
		parmMap.put("POST_TYPE", request.getParameter("post_type"));
		parmMap.put("CERT_TYPE", request.getParameter("cert_type"));
		parmMap.put("SHARE_TYPE", request.getParameter("share_type"));
		parmMap.put("TITLE", request.getParameter("title"));
		resultMap = onlineService.searchTopic(parmMap);
		return resultMap;
	}
	
	/*试题添加图片*/
	@RequestMapping(params="method=updateTopicImage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateTopicImage (HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String realPath = request.getSession().getServletContext().getRealPath("/") + "pictures/";
		String urlPath = "/pictures/";
		parmMap.put("ID", request.getParameter("topicId"));
		parmMap.put("PAY_TYPE", request.getParameter("topicPay"));
		parmMap.put("IMAGE", request.getParameter("topicImage")==null?"":Img64Util.imgUrl(request.getParameter("topicImage"),realPath,"", urlPath));
		resultMap = onlineService.updateTopicImage(parmMap);
		return resultMap;
	}
	
	/*获取试题*/
	@RequestMapping(params="method=getTopicByRang", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getTopicByRang (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("PAY_TYPE", request.getParameter("pay_type"));
		parmMap.put("POST_TYPE", request.getParameter("post_type"));
		parmMap.put("CERT_TYPE", request.getParameter("cert_type"));
		parmMap.put("SHARE_TYPE", request.getParameter("share_type"));
		parmMap.put("EXAM_ID", request.getParameter("examId")==null?"":request.getParameter("examId"));
		resultMap = onlineService.getTopicByRang(parmMap);
		return resultMap;
	}
	
	/*获取付费试题*/
	@RequestMapping(params="method=getPayTopicByRang", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getPayTopicByRang (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("PAY_TYPE", request.getParameter("pay_type"));
		parmMap.put("POST_TYPE", request.getParameter("post_type"));
		parmMap.put("CERT_TYPE", request.getParameter("cert_type"));
		parmMap.put("SHARE_TYPE", request.getParameter("share_type"));
		parmMap.put("EXAM_ID", request.getParameter("examId"));
		resultMap = onlineService.getPayTopicByRang(parmMap);
		return resultMap;
	}
	
	/*获取用户信息*/
	@RequestMapping(params="method=getUserInfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUserInfo (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("OPENID", request.getParameter("WID"));
		resultMap = onlineService.getUserInfo(parmMap);
		return resultMap;
	}
	
	/*修改用户信息*/
	@RequestMapping(params="method=updateUserInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateUserInfo (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("OPENID", request.getParameter("openid"));
		parmMap.put("NAME", request.getParameter("userName"));
		parmMap.put("SEX", request.getParameter("userSex"));
		parmMap.put("PHONE", request.getParameter("userPhone"));
		parmMap.put("EMAIL", request.getParameter("userEmail"));
		resultMap = onlineService.updateUserInfo(parmMap);
		return resultMap;
	}
	
	/*记录订单*/
	@RequestMapping(params="method=pay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> pay (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		GregorianCalendar worldTour = new GregorianCalendar();
        worldTour.add(GregorianCalendar.DATE, 30);
		
		Date date = worldTour.getTime();
		SimpleDateFormat  df = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = df.format(date);
        
		parmMap.put("OPENID", request.getParameter("openid"));
		parmMap.put("ORDER_NUMBER", request.getParameter("order_number"));
		parmMap.put("ORDER_STATE", request.getParameter("order_state"));
		parmMap.put("ORDER_TYPE", request.getParameter("order_type"));
		parmMap.put("ORDER_MONEY", request.getParameter("order_money"));
		parmMap.put("EXAM_ID", request.getParameter("examId"));
		parmMap.put("TIME",time);
		parmMap.put("USER_ORDER_TIME", request.getParameter("user_order_time"));
		resultMap = onlineService.pay(parmMap);
		return resultMap;
	}
	
	/*支付*/
	@RequestMapping(params="method=payMoney", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> payMoney (HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String payClass = request.getParameter("payClass");
		String payMoney = request.getParameter("payMoney");
		String openid = request.getParameter("openid");
		String ipString = request.getRemoteAddr();

		YoyoUtil yoyoUtil = new YoyoUtil();
		
		String orderNumb = yoyoUtil.returnOrderNumb();
		String orderTime = yoyoUtil.returnOrderTime();
		
		resultMap.put("result", yoyoUtil.returnPackage(payClass, payMoney , orderNumb, orderTime, openid, ipString));
		resultMap.put("orderNumber", orderNumb);
		resultMap.put("payMoney", payMoney);
		resultMap.put("orderType", payClass);
		resultMap.put("orderTime", orderTime);
		
		return resultMap;
	}
	
	/*获取用户解析币*/
	@RequestMapping(params="method=getUserCoin", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUserCoin (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("OPENID", request.getParameter("WID"));
		resultMap = onlineService.getUserCoin(parmMap);
		return resultMap;
	}
	
	/*获取用户解析币*/
	@RequestMapping(params="method=rechargeCoin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> rechargeCoin (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("OPENID", request.getParameter("WID"));
		parmMap.put("COIN", request.getParameter("COIN"));
		resultMap = onlineService.rechargeCoin(parmMap);
		return resultMap;
	}
	
	/*获取用户解析币*/
	@RequestMapping(params="method=subCoin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> subCoin (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("OPENID", request.getParameter("WID"));
		parmMap.put("COIN", request.getParameter("COIN"));
		resultMap = onlineService.subCoin(parmMap);
		return resultMap;
	}
	
	/*获取用户解析币*/
	@RequestMapping(params="method=getTopicExplain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getTopicExplain (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("TOPIC_ID", request.getParameter("topicId"));
		parmMap.put("PAY_TYPE", request.getParameter("payType"));
		resultMap = onlineService.getTopicExplain(parmMap);
		return resultMap;
	}
	
	/*获取用户解析币*/
	@RequestMapping(params="method=getOrderList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getOrderList (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("WID", request.getParameter("openid"));
		resultMap = onlineService.getOrderList(parmMap);
		return resultMap;
	}
	
	/*删除试题*/
	@RequestMapping(params="method=delTopic", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delTopic (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("ID", request.getParameter("topicId"));
		parmMap.put("PAY_TYPE", request.getParameter("payType"));
		resultMap = onlineService.delTopic(parmMap);
		return resultMap;
	}

	@RequestMapping(params="method=doLogin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doLogin(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("adminName", request.getParameter("adminName"));
		parmMap.put("adminPassword", request.getParameter("adminPassword"));
		resultMap = onlineService.adminLogin(parmMap);
		return resultMap;
	}
	
	/*获取用户列表by区间*/
	@RequestMapping(params="method=getUserByPage", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUserByPage (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("begin", Integer.parseInt(request.getParameter("begin")));
		parmMap.put("end", Integer.parseInt(request.getParameter("end")));
		resultMap = onlineService.getUserByPage(parmMap);
		return resultMap;
	}
	
	/*添加试卷*/
	@RequestMapping(params="method=addExamClass", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addExamClass (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("postType", request.getParameter("postType"));
		parmMap.put("certType", request.getParameter("certType"));
		parmMap.put("totalScore", request.getParameter("totalScore"));
		parmMap.put("passScroe", request.getParameter("passScroe"));
		parmMap.put("examPay", request.getParameter("examPay"));
		parmMap.put("examName", request.getParameter("examName"));
		parmMap.put("certType1", request.getParameter("certType1"));
		parmMap.put("certTypeScore1", request.getParameter("certTypeScore1"));
		parmMap.put("certType2", request.getParameter("certType2"));
		parmMap.put("certTypeScore2", request.getParameter("certTypeScore2"));
		parmMap.put("certType3", request.getParameter("certType3"));
		parmMap.put("certTypeScore3", request.getParameter("certTypeScore3"));
		resultMap = onlineService.addExamClass(parmMap);
		return resultMap;
	}
	
	/*获取用户列表by区间*/
	@RequestMapping(params="method=getExamList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getExamList (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("postType", request.getParameter("postType"));
		parmMap.put("certType", request.getParameter("certType"));
		resultMap = onlineService.getExamList(parmMap);
		return resultMap;
	}
	
	/*获取用户列表by区间*/
	@RequestMapping(params="method=getAllExam", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAllExam (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = onlineService.getAllExam(parmMap);
		return resultMap;
	}
	
	/*导入topic*/
	@RequestMapping(params="method=addExamXls", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addExamXls (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("TYPE", request.getParameter("type"));
		parmMap.put("EXAM_CLASS", request.getParameter("exams"));
		parmMap.put("ARTICLE", request.getParameter("article")!=null?request.getParameter("article"):"");
		parmMap.put("TITLE", request.getParameter("title"));
		parmMap.put("IMAGE", request.getParameter("image") != null?request.getParameter("image"):"");
		parmMap.put("OPTION_A", request.getParameter("option_a") != null?request.getParameter("option_a"):"");
		parmMap.put("OPTION_B", request.getParameter("option_b") != null?request.getParameter("option_b"):"");
		parmMap.put("OPTION_C", request.getParameter("option_c") != null?request.getParameter("option_c"):"");
		parmMap.put("OPTION_D", request.getParameter("option_d") != null?request.getParameter("option_d"):"");
		parmMap.put("EXPLAIN_TEXT", request.getParameter("explain") != null?request.getParameter("explain"):"");
		parmMap.put("RIGHT_OPTION", request.getParameter("right_option"));
		resultMap = onlineService.addExamXls(parmMap);
		return resultMap;
	}
	
	/*获取用户列表by区间*/
	@RequestMapping(params="method=getExamByClass", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getExamByClass (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("EXAM_CLASS", request.getParameter("examClass"));
		resultMap = onlineService.getExamByClass(parmMap);
		return resultMap;
	}
	
	/*获取用户所有套卷*/
	@RequestMapping(params="method=getUserExamList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUserExamList (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("openid", request.getParameter("openid"));
		resultMap = onlineService.getUserExamList(parmMap);
		return resultMap;
	}
	
	/*获取用户所有套卷*/
	@RequestMapping(params="method=addAdv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAdv (HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String realPath = request.getSession().getServletContext().getRealPath("/") + "pictures/";
		String urlPath = "/pictures/";
		parmMap.put("NAME", request.getParameter("advName"));
		parmMap.put("ADV_SHOW", request.getParameter("advShow"));
		parmMap.put("IMAGE", request.getParameter("advImage")==null?"":Img64Util.imgUrl(request.getParameter("advImage"),realPath,"", urlPath));
		resultMap = onlineService.addAdv(parmMap);
		return resultMap;
	}
	/*获取用户所有套卷*/
	@RequestMapping(params="method=getAllAdv", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAllAdv (HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = onlineService.getAllAdv(parmMap);
		return resultMap;
	}
	
	/*更新广告*/
	@RequestMapping(params="method=updataAdv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updataAdv (HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String realPath = request.getSession().getServletContext().getRealPath("/") + "pictures/";
		String urlPath = "/pictures/";
		parmMap.put("ID", request.getParameter("advId"));
		parmMap.put("NAME", request.getParameter("advName"));
		parmMap.put("IMAGE", request.getParameter("advImage")==null?"":Img64Util.imgUrl(request.getParameter("advImage"),realPath,"", urlPath));
		resultMap = onlineService.updataAdv(parmMap);
		return resultMap;
	}
	
	/*不显示广告*/
	@RequestMapping(params="method=updataAdvShow", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updataAdvShow (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("ID", request.getParameter("advId"));
		parmMap.put("ADV_SHOW", request.getParameter("advShow"));
		resultMap = onlineService.updataAdvShow(parmMap);
		return resultMap;
	}
	
	@RequestMapping(params="method=getUserExam", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUserExam (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("openid", request.getParameter("openid"));
		parmMap.put("examId", request.getParameter("examId"));
		resultMap = onlineService.getUserExam(parmMap);
		return resultMap;
	}
	
	@RequestMapping(params="method=updateAdvInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAdvInfo (HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String realPath = request.getSession().getServletContext().getRealPath("/") + "pictures/";
		String urlPath = "/pictures/";
		parmMap.put("ID", request.getParameter("advId"));
		parmMap.put("IMAGE", request.getParameter("advImage")==null?"":Img64Util.imgUrl(request.getParameter("advImage"),realPath,"", urlPath));
		resultMap = onlineService.updateAdvInfo(parmMap);
		return resultMap;
	}
	
	@RequestMapping(params="method=getAdvInfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAdvInfo (HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("advId", request.getParameter("advId"));
		resultMap = onlineService.getAdvInfo(parmMap);
		return resultMap;
	}
	
	@RequestMapping(params="method=searchPayTopic", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> searchPayTopic (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("TITLE", request.getParameter("title"));
		resultMap = onlineService.searchPayTopic(parmMap);
		return resultMap;
	}
	
	@RequestMapping(params="method=addSection", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addSection (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("name", request.getParameter("name"));
		parmMap.put("certType", request.getParameter("certType"));
		parmMap.put("postType", request.getParameter("postType"));
		resultMap = onlineService.addSection(parmMap);
		return resultMap;
	}
	
	@RequestMapping(params="method=delSection", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delSection (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("id", request.getParameter("id"));
		resultMap = onlineService.delSection(parmMap);
		return resultMap;
	}
	
	@RequestMapping(params="method=updataSection", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updataSection (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("id", request.getParameter("id"));
		parmMap.put("name", request.getParameter("name"));
		resultMap = onlineService.delSection(parmMap);
		return resultMap;
	}
	
	@RequestMapping(params="method=getSection", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getSection (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("certType", request.getParameter("certType"));
		parmMap.put("postType", request.getParameter("postType"));
		resultMap = onlineService.getSection(parmMap);
		return resultMap;
	}
	
	/*导入topic*/
	@RequestMapping(params="method=addSectionTopic", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addSectionTopic (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("PAY_TYPE", request.getParameter("pays_type"));
		parmMap.put("POST_TYPE", request.getParameter("post_type"));
		parmMap.put("CERT_TYPE", request.getParameter("cert_type"));
		parmMap.put("SECTION_TYPE", request.getParameter("section_type"));
		parmMap.put("CERT_TEXT", request.getParameter("cert_text"));
		parmMap.put("CERT_CLASS", request.getParameter("cert_class"));
		parmMap.put("TYPE", request.getParameter("type"));
		parmMap.put("ARTICLE", request.getParameter("article")!=null?request.getParameter("article"):"");
		parmMap.put("TITLE", request.getParameter("title"));
		parmMap.put("IMAGE", request.getParameter("image") != null?request.getParameter("image"):"");
		parmMap.put("OPTION_A", request.getParameter("option_a") != null?request.getParameter("option_a"):"");
		parmMap.put("OPTION_B", request.getParameter("option_b") != null?request.getParameter("option_b"):"");
		parmMap.put("OPTION_C", request.getParameter("option_c") != null?request.getParameter("option_c"):"");
		parmMap.put("OPTION_D", request.getParameter("option_d") != null?request.getParameter("option_d"):"");
		parmMap.put("EXPLAIN_TEXT", request.getParameter("explain") != null?request.getParameter("explain"):"");
		parmMap.put("RIGHT_OPTION", request.getParameter("right_option"));
		resultMap = onlineService.addSectionTopic(parmMap);
		return resultMap;
	}
	
	/*搜索试题*/
	@RequestMapping(params="method=searchSectionTopic", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> searchSectionTopic (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("SECTION_TYPE", request.getParameter("section_type"));
		parmMap.put("POST_TYPE", request.getParameter("post_type"));
		parmMap.put("CERT_TYPE", request.getParameter("cert_type"));
		parmMap.put("TITLE", request.getParameter("title"));
		resultMap = onlineService.searchSectionTopic(parmMap);
		return resultMap;
	}
	
	/*删除章节试题*/
	@RequestMapping(params="method=delSectionTopic", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delSectionTopic (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("id", request.getParameter("id"));
		resultMap = onlineService.delSectionTopic(parmMap);
		return resultMap;
	}
	
	/*修改章节试题  添加图片*/
	@RequestMapping(params="method=updateSectionTopicImage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateSectionTopicImage (HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String realPath = request.getSession().getServletContext().getRealPath("/") + "pictures/";
		String urlPath = "/pictures/";
		parmMap.put("ID", request.getParameter("topicId"));
		parmMap.put("IMAGE", request.getParameter("topicImage")==null?"":Img64Util.imgUrl(request.getParameter("topicImage"),realPath,"", urlPath));
		resultMap = onlineService.updateSectionTopicImage(parmMap);
		return resultMap;
	}
	
	/*getSectionTopic 获取章节试题*/
	@RequestMapping(params="method=getSectionTopic", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getSectionTopic (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("POST_TYPE", request.getParameter("post_type"));
		parmMap.put("CERT_TYPE", request.getParameter("cert_type"));
		parmMap.put("SECTION_TYPE", request.getParameter("section_type"));
		resultMap = onlineService.getSectionTopic(parmMap);
		return resultMap;
	}
	
	/*获取用户解析币*/
	@RequestMapping(params="method=getSectionExplain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getSectionExplain (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("TOPIC_ID", request.getParameter("topicId"));
		resultMap = onlineService.getSectionExplain(parmMap);
		return resultMap;
	}
	
	/*获取openid*/
	@RequestMapping(params="method=getOpenId", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getOpenId (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String codeString = request.getParameter("js_code");
		resultMap.put("data", JSONArray.fromObject("["+YoyoUtil.getConvert(codeString)+"]"));
		return resultMap;
	}

    /*getEnglishClass 获取英语课程*/
    @RequestMapping(params="method=getEnglishClass", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getEnglishClass (HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> parmMap = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        parmMap.put("className", request.getParameter("className"));
        parmMap.put("isPay", request.getParameter("isPay"));
        resultMap = onlineService.getEnglishClass(parmMap);
        return resultMap;
    }

    /*addEnglishClass 添加英语课程*/
    @RequestMapping(params="method=addEnglishClass", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addEnglishClass (HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> parmMap = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        parmMap.put("className", request.getParameter("className"));
        parmMap.put("isPay", request.getParameter("isPay"));
        resultMap = onlineService.addEnglishClass(parmMap);
        return resultMap;
    }

    /*updEnglishClass 修改英语课程*/
    @RequestMapping(params="method=updEnglishClass", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updEnglishClass (HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> parmMap = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        parmMap.put("id", request.getParameter("id"));
        parmMap.put("className", request.getParameter("className"));
        parmMap.put("isPay", request.getParameter("isPay"));
        resultMap = onlineService.updateEnglishClass(parmMap);
        return resultMap;
    }

    /*delEnglishClass 删除英语课程*/
    @RequestMapping(params="method=delEnglishClass", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delEnglishClass (HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> parmMap = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        parmMap.put("id", request.getParameter("id"));
        resultMap = onlineService.deleteEnglishClass(parmMap);
        return resultMap;
    }
}
