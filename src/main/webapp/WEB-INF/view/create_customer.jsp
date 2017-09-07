<%--
  Created by IntelliJ IDEA.
  User: shanlin
  Date: 2017/8/29
  Time: 15:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri ="http://java.sun.com/jsp/jstl/core"%>
<c:set var="BASE" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>客户管理 -- 创建客户</title>
</head>
<body>
<h1>创建客户页面</h1>
<form id="create" action="customer" method="post">
    用户名： <input type="text" name="name" value="${customer.name}"/> <br>
    联系人： <input type="text" name="contact" value="${customer.contact}"/> <br>
    电话号码：<input type="text" name="telephone" value="${customer.telephone}"/> <br>
    电子邮件：<input type="text" name="email" value="${customer.email}"/> <br>
    备注：<input type="text" name="remark" value="${customer.remark}"/> <br>
    <input type="submit" value="提交" >
</form>

<form></form>



</body>
</html>
