<%--
  Created by IntelliJ IDEA.
  User: shanlin
  Date: 2017/8/31
  Time: 16:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="BASE" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>客户详情</h1>

<th>id :</th> <td>${customer.id}</td> <br>
<th>客户名称 :</th> <td>${customer.name}</td> <br>
<th>联系人 :</th> <td>${customer.contact}</td> <br>
<th>电话号码 :</th> <td>${customer.telephone}</td> <br>
<th>电子邮箱 :</th> <td>${customer.remark}</td> <br>

</body>
</html>
