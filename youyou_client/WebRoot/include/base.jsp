<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page contentType="text/html; charset=utf-8"%>

<%
String path = request.getContextPath();
//清空缓存
response.setDateHeader("expires", 0);
response.setHeader("Cache-Control", "no-cache");
response.setHeader("pragma", "no-cache");
%>
<meta name="format-detection" content="telephone=no"/>
<meta content="email=no" name="format-detection"/>
<script src="<%=path%>/js/jquery-1.11.2.min.js"></script>
<script src="<%=path%>/js/bootstrap.min.js"></script>
<script src="<%=path%>/js/tools.js"></script>

<script src="<%=path%>/js/xlsx.full.min.js"></script>
<script src="<%=path%>/js/admin.js"></script>
<script src="<%=path%>/js/bootbox.min.js"></script>

<link href="<%=path%>/css/bootstrap.min.css" rel="stylesheet"/>
<link href="<%=path%>/css/admin.css" rel="stylesheet"/>