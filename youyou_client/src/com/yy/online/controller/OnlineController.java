package com.yy.online.controller;

import com.alibaba.citrus.util.StringUtil;
import com.yy.online.service.OnlineService;
import com.yy.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.portlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

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
	@RequestMapping(params="method=wxPay", method = RequestMethod.GET)
	public String wxPay(HttpServletRequest request, HttpServletResponse response){
		String title = request.getParameter("title");
		String pay = request.getParameter("pay");
		String openid = request.getParameter("openid");
		if(StringUtils.isBlank(title) || StringUtil.isBlank(pay)){
			request.setAttribute("msg","网络异常，请返回重试");
		}else{
			request.setAttribute("title",title);
			request.setAttribute("pay",pay);
			request.setAttribute("openid",openid);
			request.setAttribute("msg","您购买的《"+title+"》, 需要支付"+pay+"元!");
		}
		return "yoyo-admin/wxPay.jsp";
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
//		ipString = "119.97.231.230";
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
	public Map<String, Object> getUserByPage (@RequestParam(value = "start", defaultValue = "0") int start,
											  @RequestParam(value = "length", defaultValue = "10") int length,
											  @RequestParam(value = "userName") String userName){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("begin", start);
		parmMap.put("end", length);
		parmMap.put("userName", userName);
		resultMap = onlineService.getUserByPage(parmMap);
		int total = onlineService.countUsers(parmMap);
		resultMap.put("recordsTotal",total);
		resultMap.put("recordsFiltered",total);
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
        parmMap.put("payMoney", request.getParameter("payMoney"));
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
        parmMap.put("payMoney", request.getParameter("payMoney"));
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

    /*添加课程图片*/
    @RequestMapping(params="method=updateEnglishClassPic", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateEnglishClassPic (HttpServletRequest request, HttpServletResponse response) throws IOException{
        Map<String, Object> parmMap = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String realPath = request.getSession().getServletContext().getRealPath("/") + "pictures/";
        String urlPath = "/pictures/";
        parmMap.put("id", request.getParameter("topicId"));
        parmMap.put("classPic", request.getParameter("topicImage")==null?"":Img64Util.imgUrl(request.getParameter("topicImage"),realPath,"", urlPath));
        resultMap = onlineService.updateEnglishClassPic(parmMap);
        return resultMap;
    }

    /*获取所有英语课程*/
    @RequestMapping(params="method=getSelClass", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getSelClass (HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap = onlineService.getAllEnglishClass();
        return resultMap;
    }

    /*getEnglishClassChap 获取英语课程章节*/
    @RequestMapping(params="method=getEnglishClassChap", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getEnglishClassChap (HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> parmMap = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        parmMap.put("classId", request.getParameter("classId"));
        parmMap.put("chapName", request.getParameter("chapName"));
        resultMap = onlineService.getEnglishChap(parmMap);
        return resultMap;
    }

    /*addEnglishClassChap 添加英语课程章节*/
    @RequestMapping(params="method=addEnglishClassChap", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addEnglishClassChap (HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> parmMap = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        parmMap.put("classId", request.getParameter("classId"));
        parmMap.put("chapName", request.getParameter("chapName"));
        resultMap = onlineService.addEnglishChap(parmMap);
        return resultMap;
    }

    /*updEnglishClassChap 修改英语课程章节*/
    @RequestMapping(params="method=updEnglishClassChap", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updEnglishClassChap (HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> parmMap = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        parmMap.put("id", request.getParameter("id"));
        parmMap.put("chapName", request.getParameter("chapName"));
        resultMap = onlineService.updateEnglishChap(parmMap);
        return resultMap;
    }

    /*delEnglishClassChap 删除英语课程*/
    @RequestMapping(params="method=delEnglishClassChap", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delEnglishClassChap (HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> parmMap = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        parmMap.put("id", request.getParameter("id"));
        resultMap = onlineService.deleteEnglishChap(parmMap);
        return resultMap;
    }

    /*根据课程获取章节*/
    @RequestMapping(params="method=getChapByClassId", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getChapByClassId (HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> parmMap = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        parmMap.put("classId", request.getParameter("classId"));
        resultMap = onlineService.getChapByClassId(parmMap);
        return resultMap;
    }

    /*导入英语试题*/
    @RequestMapping(params="method=addEnglishQuestion", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addEnglishQuestion(@RequestParam("xls") String xls, @RequestParam("classId") String classId, @RequestParam("chapId") String chapId){
        Map<String, Object> parmMap;
		Map<String, Object> resultMap = new HashMap<>();
		JSONArray xlsArr = JSONArray.fromObject(xls);
		JSONObject object;
		for (int i = 0; i <xlsArr.size() ; i++) {
			object = xlsArr.getJSONObject(i);
			parmMap = new HashMap<>();
			parmMap.put("chapId", chapId);
			parmMap.put("qtType", object.optInt("type",1));
			parmMap.put("qtContent", object.optString("title",""));
			parmMap.put("optionA", object.optString("option_a",""));
			parmMap.put("optionB", object.optString("option_b",""));
			parmMap.put("optionC", object.optString("option_c",""));
			parmMap.put("optionD", object.optString("option_d",""));
			parmMap.put("rightOption", object.optInt("right_option",1));
			parmMap.put("explanTxt", object.optString("explain",""));
			parmMap.put("qtDif", object.optInt("qt_dif",2));
			onlineService.addEnglishQuestion(parmMap);
			parmMap.clear();
		}
		resultMap.put("RESULT","SUCCESS");
		return resultMap;
//        parmMap.put("chapId", request.getParameter("chapId"));
//        parmMap.put("qtType", request.getParameter("type"));
//        parmMap.put("qtContent", request.getParameter("title"));
//        parmMap.put("optionA", request.getParameter("option_a") != null?request.getParameter("option_a"):"");
//        parmMap.put("optionB", request.getParameter("option_b") != null?request.getParameter("option_b"):"");
//        parmMap.put("optionC", request.getParameter("option_c") != null?request.getParameter("option_c"):"");
//        parmMap.put("optionD", request.getParameter("option_d") != null?request.getParameter("option_d"):"");
//        parmMap.put("rightOption", request.getParameter("right_option") != null?request.getParameter("right_option"):1);
//        parmMap.put("explanTxt", request.getParameter("explain") != null?request.getParameter("explain"):"");
//        parmMap.put("qtDif", request.getParameter("qt_dif") != null?request.getParameter("qt_dif"):2);
//        resultMap = onlineService.addEnglishQuestion(parmMap);

    }

    /*getEnglishQuestion 根据英语课程、章节获取试题*/
    @RequestMapping(params="method=getEnglishQuestion", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getEnglishQuestion (HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> parmMap = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        parmMap.put("chapId", request.getParameter("chapId"));
        resultMap = onlineService.getEnglishQuestion(parmMap);
        return resultMap;
    }

    /*updEnglishQuestion 修改英语试题*/
    @RequestMapping(params="method=updEnglishQuestion", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updEnglishQuestion (HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> parmMap = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        parmMap.put("id", request.getParameter("id"));
        parmMap.put("qtType", request.getParameter("qtType"));
        parmMap.put("qtDif", request.getParameter("qtDif"));
        parmMap.put("qtContent", request.getParameter("qtContent"));
        parmMap.put("optionA", request.getParameter("optionA"));
        parmMap.put("optionB", request.getParameter("optionB"));
        parmMap.put("optionC", request.getParameter("optionC"));
        parmMap.put("optionD", request.getParameter("optionD"));
        parmMap.put("rightOption", request.getParameter("rightOption"));
        parmMap.put("explanTxt", request.getParameter("explanTxt"));
        resultMap = onlineService.updEnglishQuestion(parmMap);
        return resultMap;
    }

    /*delEnglishQuestion 删除英语试题*/
    @RequestMapping(params="method=delEnglishQuestion", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delEnglishQuestion (HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> parmMap = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        parmMap.put("id", request.getParameter("id"));
        resultMap = onlineService.delEnglishQuestion(parmMap);
        return resultMap;
    }

    /*添加英语试题图片*/
    @RequestMapping(params="method=updateEnglishQuestionPic", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateEnglishQuestionPic (HttpServletRequest request, HttpServletResponse response) throws IOException{
        Map<String, Object> parmMap = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String realPath = request.getSession().getServletContext().getRealPath("/") + "pictures/";
        String urlPath = "/pictures/";
        parmMap.put("id", request.getParameter("topicId"));
        parmMap.put("picUrl", request.getParameter("topicImage")==null?"":Img64Util.imgUrl(request.getParameter("topicImage"),realPath,"", urlPath));
        resultMap = onlineService.updateEnglishQuestionPic(parmMap);
        return resultMap;
    }

    /*添加英语试题图片*/
    @RequestMapping(params="method=updateEnglishQuestionVol", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateEnglishQuestionVol (HttpServletRequest request,@RequestParam(value="targetId")String targetId, @RequestBody MultipartFile audio) throws IOException{
        Map<String, Object> parmMap = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String realPath = request.getSession().getServletContext().getRealPath("/") + "audio/";
        String urlPath = "/audio/";
        parmMap.put("id",targetId);
        parmMap.put("voUrl",AudioUploadUtil.storeAudio(realPath,urlPath,audio));
        resultMap=onlineService.updateEnglishQuestionVol(parmMap);
        return resultMap;
    }

    /*获取所有英语课程*/
    @RequestMapping(params="method=getAllEnglishClassData", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getAllEnglishClassData (HttpServletRequest request, HttpServletResponse response) throws IOException{
        Map<String, Object> parmMap = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        parmMap.put("WID",request.getParameter("WID"));
        resultMap = onlineService.getAllEnglishData(parmMap);
        return resultMap;
    }

    /*根据课程ID获取章节列表*/
    @RequestMapping(params="method=getAllEnglishChapDataByClassId", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getAllEnglishChapDataByClassId (HttpServletRequest request, HttpServletResponse response) throws IOException{
        Map<String, Object> parmMap = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        parmMap.put("classId", request.getParameter("classId"));
        resultMap = onlineService.getAllEnglishChapDataByClassId(parmMap);
        return resultMap;
    }

    /*根据章节ID获取所有试题列表*/
    @RequestMapping(params="method=getAllEnglishQuestionDataByChapId", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getAllEnglishQuestionDataByChapId (HttpServletRequest request, HttpServletResponse response) throws IOException{
        Map<String, Object> parmMap = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        parmMap.put("chapId", request.getParameter("chapId"));
        resultMap = onlineService.getAllEnglishQuestionDataByChapId(parmMap);
        return resultMap;
    }

    /*根据章节ID获取听力试题列表*/
    @RequestMapping(params="method=getAllEnglishQuestionListenDataByChapId", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getAllEnglishQuestionListenDataByChapId (HttpServletRequest request, HttpServletResponse response) throws IOException{
        Map<String, Object> parmMap = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        parmMap.put("chapId", request.getParameter("chapId"));
        resultMap = onlineService.getAllEnglishQuestionListenDataByChapId(parmMap);
        return resultMap;
    }

    /*根据章节ID获取会话试题列表*/
    @RequestMapping(params="method=getAllEnglishQuestionTalkDataByChapId", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getAllEnglishQuestionTalkDataByChapId (HttpServletRequest request, HttpServletResponse response) throws IOException{
        Map<String, Object> parmMap = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        parmMap.put("chapId", request.getParameter("chapId"));
        resultMap = onlineService.getAllEnglishQuestionTalkDataByChapId(parmMap);
        return resultMap;
    }

    /*插入英语课程购买记录*/
    @RequestMapping(params="method=addEnglishPayRecord", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> addEnglishPayRecord (HttpServletRequest request, HttpServletResponse response) throws IOException{
        Map<String, Object> parmMap = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
		parmMap.put("WID", request.getParameter("wid"));
		parmMap.put("CLASS_ID", request.getParameter("CLASS_ID"));
		parmMap.put("ORDER_ID", request.getParameter("ORDER_ID"));
        resultMap = onlineService.addEnglishPayRecord(parmMap);
        return resultMap;
    }

    /*查询英语课程购买记录*/
    @RequestMapping(params="method=getEnglishPayRecord", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getEnglishPayRecord (HttpServletRequest request, HttpServletResponse response) throws IOException{
        Map<String, Object> parmMap = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        parmMap.put("WID", request.getParameter("wid"));
        resultMap = onlineService.getEnglishPayRecord(parmMap);
        return resultMap;
    }


	/*查询用户是否购买了盖英语课程*/
	@RequestMapping(params="method=getEnglishPayRecordCount", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getEnglishPayRecordCount (HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, Object> parmMap = new HashMap();
		Map<String, Object> resultMap;
		parmMap.put("WID", request.getParameter("WID"));
		parmMap.put("CLASS_ID", request.getParameter("CLASS_ID"));
		resultMap = onlineService.getEnglishPayRecordCount(parmMap);
		return resultMap;
	}

	/*记录英语订单*/
	@RequestMapping(params="method=englishPay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> englishPay (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		GregorianCalendar worldTour = new GregorianCalendar();
		worldTour.add(GregorianCalendar.DATE, 30);

		Date date = worldTour.getTime();
		SimpleDateFormat  df = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = df.format(date);

		parmMap.put("OPENID", request.getParameter("openid"));
		parmMap.put("WID", request.getParameter("openid"));
		parmMap.put("ORDER_NUMBER", request.getParameter("order_number"));
		parmMap.put("ORDER_STATE", request.getParameter("order_state"));
		parmMap.put("ORDER_TYPE", request.getParameter("order_type"));
		parmMap.put("ORDER_MONEY", request.getParameter("order_money"));
		parmMap.put("CLASS_ID", request.getParameter("CLASS_ID"));
		parmMap.put("TIME",time);
		parmMap.put("USER_ORDER_TIME", request.getParameter("user_order_time"));
		resultMap = onlineService.englishPay(parmMap);
		return resultMap;
	}

	/*英语语音识别*/
	@RequestMapping(params="method=englishASR", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  englishASR(@RequestParam(value = "file") CommonsMultipartFile file, HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<>();
		String fileName = request.getParameter("fileName");
		String preContext = request.getParameter("preContext");
		String filePath = request.getSession().getServletContext().getRealPath("")+"\\preASRVoice\\"+fileName;
		System.out.println("####################fileName="+fileName+", preContext="+preContext+", filePath="+filePath);
		SaveFileFromWX save = new SaveFileFromWX();
		boolean b = save.saveFile(request, file);
		if(b){
			Map<String, Object> parmMap = new HashMap<>();
			parmMap.put("filePath", filePath);
			parmMap.put("preContext", preContext);
			resultMap = RASUtil.englishRSA(parmMap);
			resultMap.put("ret",1);
		}else{
			resultMap.put("ret",0);
		}
		return resultMap;
	}

	/*根据topicId查询题目详情*/
	@RequestMapping(params="method=getTopicById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getTopicById(HttpServletRequest request) {
		Map<String, Object> parmMap = new HashMap<>();
		Map<String, Object> resultMap;
		parmMap.put("payType", request.getParameter("payType"));
		parmMap.put("topicId", request.getParameter("topicId"));
		resultMap = onlineService.getTopicById(parmMap);
		return resultMap;
	}

	/*根据topicId修改题目详情*/
	@RequestMapping(params="method=editeTopicById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> editeTopicById(HttpServletRequest request) {
		Map<String, Object> parmMap = new HashMap<>();
		Map<String, Object> resultMap;
		parmMap.put("payType", Integer.parseInt(request.getParameter("payType")));
		parmMap.put("TITLE", StringUtil.isBlank(request.getParameter("TITLE"))?"":request.getParameter("TITLE"));
		parmMap.put("OPTION_A", StringUtil.isBlank(request.getParameter("OPTION_A"))?"":request.getParameter("OPTION_A"));
		parmMap.put("OPTION_B", StringUtil.isBlank(request.getParameter("OPTION_B"))?"":request.getParameter("OPTION_B"));
		parmMap.put("OPTION_C", StringUtil.isBlank(request.getParameter("OPTION_C"))?"":request.getParameter("OPTION_C"));
		parmMap.put("OPTION_D", StringUtil.isBlank(request.getParameter("OPTION_D"))?"":request.getParameter("OPTION_D"));
		parmMap.put("RIGHT_OPTION", StringUtil.isBlank(request.getParameter("RIGHT_OPTION"))?"":request.getParameter("RIGHT_OPTION"));
		parmMap.put("EXPLAIN_TEXT", StringUtil.isBlank(request.getParameter("EXPLAIN_TEXT"))?"":request.getParameter("EXPLAIN_TEXT"));
		parmMap.put("TOPIC_ID", Integer.parseInt(request.getParameter("topicId")));
		parmMap.put("TYPE", Integer.parseInt(request.getParameter("TYPE")));
		resultMap = onlineService.editeTopicById(parmMap);
		return resultMap;
	}

	/*支付*/
	@RequestMapping(params="method=payH5Money", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> payH5Money (HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<>();

		String payClass = request.getParameter("payClass");
		String payMoney = request.getParameter("payMoney");
		String openid = request.getParameter("openid");
		String ipString = request.getRemoteAddr();
//		ipString = "119.97.231.230";
		YoyoH5Util yoyoH5Util = new YoyoH5Util();

		String orderNumb = yoyoH5Util.returnOrderNumb();
		String orderTime = yoyoH5Util.returnOrderTime();

		resultMap.put("result", yoyoH5Util.returnPackage(payClass, payMoney , orderNumb, orderTime, openid, ipString));
		resultMap.put("orderNumber", orderNumb);
		resultMap.put("payMoney", payMoney);
		resultMap.put("orderType", payClass);
		resultMap.put("orderTime", orderTime);

		return resultMap;
	}
}
