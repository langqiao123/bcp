<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel='stylesheet' href='${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css'>
<link rel='stylesheet' href='${pageContext.request.contextPath}/bootstrap/css/bootstrap-theme.min.css'>
<script src='${pageContext.request.contextPath}/bootstrap/js/jquery.min.js'></script>
</head>
<body>
 <c:import url="${page}"></c:import>  
</body>
<script src='${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js'></script>
<script src='${pageContext.request.contextPath}/js/common.js'></script>
</html>