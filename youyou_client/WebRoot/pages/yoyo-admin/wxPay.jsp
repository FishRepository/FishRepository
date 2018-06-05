<%--
  Created by IntelliJ IDEA.
  User: qizhihang
  Date: 2018/6/5
  Time: 下午4:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String msg = request.getAttribute("msg").toString();

%>
<html>
<head>
    <title>Title</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <meta http-equiv="expires" content="0">

    <script src="<%=path%>/js/jquery-1.11.2.min.js"></script>
    <script src="<%=path%>/js/bootstrap.min.js"></script>
    <script src="<%=path%>/js/bootbox.min.js"></script>
    <script type="text/javascript" charset="utf8" src="<%=path%>/js/jquery.dataTables.min.js"></script>
    <link href="<%=path%>/css/bootstrap.min.css" rel="stylesheet"/>
    <style type="text/css">
        .main{
            height: 100%;
            background: #eee;
        }
        .msg{
            margin-top: 10rem;
            text-align: center;
        }
        .button{
            margin-top: 5rem;
            text-align: center;
        }
        .bigbtn{
            width: 20rem;
            height: 4rem;
            font-size: 2.5rem;
        }
        .foot{
            color: #9d9d9d;
        }
    </style>
</head>

<body>

    <div class="container-fluid main">
        <div class="row msg">
            <h4><%=msg%></h4>
        </div>
        <div class="row button">
            <button type="button" class="btn btn-success bigbtn">确认支付</button>
        </div>
        <div class="row navbar-fixed-bottom text-center foot">
            Yoyo-游友科技
        </div>
    </div>



    <script type="text/javascript">
        console.log(123123);
        //用户点击跳转地址（非静默授权） 参数appid为公众号的id redirect_uri为微信回调接口 state为可携带的参数
        // window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=http://你的域名/weChatpay/mainServlet&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";

    </script>
</body>
</html>
