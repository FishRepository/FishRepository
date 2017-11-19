<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>INDEX</title>
    <script type="text/javascript">
    window.location.href.split("?")?window.location.href="/mylm/index/main.do?"+window.location.href.split("?")[1]:window.location.href="/mylm/index/main.do";
    </script>
</head>
<body>
	<c:if test="${!fn:containsIgnoreCase(wayCode, 'MYLIOS')&&!fn:containsIgnoreCase(wayCode, 'MYLADR')}">
	</c:if>

</body>
</html>