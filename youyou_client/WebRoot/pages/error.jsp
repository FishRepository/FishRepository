<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@page import="com.np.util.UUIDUtil"%>
<%
String path = request.getContextPath();
String uuid=UUIDUtil.randomUUID();
//清空缓存
response.setDateHeader("expires", 0);
response.setHeader("Cache-Control", "no-cache");
response.setHeader("pragma", "no-cache");
%>
<c:set value="<%=path%>" var="path"></c:set>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta name="keywords" content="小马医疗,肛肠疾病,便秘,马应龙,移动,医疗,健康" />
		<meta name="description" content="小马医疗，专注于肛肠疾病领域研究。" />
		<meta name="renderer" content="webkit|ie-comp|ie-stand">
		<meta http-equiv="X-Ua-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link rel="stylesheet" href="${path }/css/mui.min.css" />
		<link rel="stylesheet" type="text/css" href="${path }/css/app.css"/>
		<link rel="stylesheet" href="${path }/css/main.css" />
		<script type="text/javascript" src="${path }/js/mui.min.js" ></script>
		<script type="text/javascript" src="${path }/js/jquery-1.11.1.js" ></script>
		
		<title></title>
	</head>
	<body>
		<header class="mui-bar mui-bar-nav public-top-bar">
			<a class="mui-icon mui-icon-left-nav mui-pull-left" onclick="javascript:window.history.back();"></a>
		    <h1 class="mui-title color-white">小马医疗</h1>
		</header>
		<div class="mui-content pub-body">
			<p style="text-align: center;margin-top: 20px;color: #000000; font-size: 18px;">程序猿开小差去了，稍后再试~~~</p>
		</div>
	</body>
</html>
