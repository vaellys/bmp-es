<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Java Jest Sample</title>
    <meta charset="utf-8" >
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/style/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/style/main.css"/>
</head>
<body>

<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">

			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
					aria-expanded="false">
					<span class="sr-only">Toggle navigation</span><span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">jest</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="myTab">
				<ul class="nav navbar-nav">
					<li class="active"><a href='<spring:url value="/" htmlEscape="true"/>'>首页 <span class="sr-only">(current)</span></a></li>
					<li><a href='<spring:url value="/es/index.do" htmlEscape="true"/>'>创建索引</a></li>
				</ul>
				<form class="navbar-form navbar-right" role="search" action='<spring:url value="/es/search.do" htmlEscape="true"/>' method="get">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search" name="keywords">
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>
			</div>

	</div>
   <%--  <div class="navbar-inner">
        <div class="container">
            <a href='<spring:url value="/" htmlEscape="true"/>' class="brand">jest 简单例子</a>

            <div class="nav-collapse">
                <ul class="nav">
                    <li><a href='<spring:url value="/" htmlEscape="true"/>'>首页</a></li>
                    <li><a href='<spring:url value="/search/create" htmlEscape="true"/>'>创建索引</a></li>
                    <li><a href='<spring:url value="/about" htmlEscape="true"/>'>关于</a></li>
                </ul>
                <ul class="nav pull-right">
                    <li class="divider-vertical"></li>
                    <form action='<spring:url value="/search" htmlEscape="true"/>' method="get" class="navbar-search pull-left">
                        <input type="text" placeholder="Search" class="search-query span2" name="q">
                    </form>
                </ul>
            </div>
            <!-- /.nav-collapse -->
        </div>
    </div> --%>
</div>
<div class="container">

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script>
    $(function(){
    	$("myTab a").click(function(e){
    		 e.preventDefault();//阻止a链接的跳转行为 
    		 $(this).tab("show");
    	});
    });
</script>

