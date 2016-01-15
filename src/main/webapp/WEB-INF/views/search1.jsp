<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="layout/header.jsp"/>

<div class="container">
    <div class="page-header">
        <h1>
            Search Results</h1>
    </div>

    <div class="row">
        <div class="span12">
            <table class="table table-hover table-bordered ">
                <thead>
                <tr>
                    <th>
                                                        文档名称
                    </th>
                    <th>
                                                        文档描述
                    </th>
                    <th>
                                                        文档路径
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${esDtos}" var="dto">
                    <tr>
                        <td>${dto.name}</td>
                        <td>${dto.content}</td>
                        <td>${dto.path}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<c:import url="layout/footer.jsp"/>