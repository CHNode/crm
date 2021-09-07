<%--
  Created by IntelliJ IDEA.
  User: 陈浩
  Date: 2021/8/13
  Time: 18:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
$.ajax({
url:"",
data:{

},
type :"",
dataType :"json",
success : function (data){

}
})


String  createTime= DateTimeUtil.getSysTime();
String  createBy=((User)request.getSession().getAttribute("user")).getName();
<body>

</body>
</html>
