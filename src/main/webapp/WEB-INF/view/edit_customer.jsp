<%--
  Created by IntelliJ IDEA.
  User: shanlin
  Date: 2017/8/31
  Time: 16:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri ="http://java.sun.com/jsp/jstl/core"%>
<c:set var="BASE" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>客户管理</title>
</head>
<body>
private String name;
private String contact;
private String telephone;
private String email;
private String remark;
<form action="${BASE}/customer" method=’post’>
    <input type="text" name="id" value="${customer.id}" />
    <input type="text" name="name" value="${customer.name}" />
    <input type="text" name="contact" value="${customer.contact}" />
    <input type="text" name="telephone" value="${customer.telephone}" />
    <input type="text" name="email" value="${customer.email}" />
    <input type="text" name="remark" value="${customer.remark}" /> <br>
    < type=’submit’ value=’修改'/>

</form>

</body>
</html>
