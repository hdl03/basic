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
<h1>客户列表</h1>
<table>
    <tr>
        <th>客户名称</th>
        <th>联系人</th>
        <th>电话号码</th>
        <th>电子邮箱</th>
        <th>操作</th>
    </tr>
    <c:forEach var="customer" items="${customers}">
        <tr>
            <td>${customer.name}</td>
            <td>${customer.contact}</td>
            <td>${customer.telephone}</td>
            <td>${customer.remark}</td>
            <td>
                <a href="${BASE}/edit_customer?id = ${customer.id}">编辑</a>
                <a href="${BASE}/delete_customer?id = ${customer.id}">删除</a>
            </td>
        </tr>
    </c:forEach>
</table>

<body>

</body>
</html>
