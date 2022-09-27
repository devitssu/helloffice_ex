<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="../../common/head.jsp" %>
<body>
  <%@ include file="../../common/header.jsp" %>
  <%
    String url = request.getAttribute("javax.servlet.forward.request_uri").toString();
    request.setAttribute("url", url);
  %>
  <main id="main" class="main">
    <h2>카테고리 관리</h2>
    <a href="category"><button type="button" class="btn btn-outline-primary">카테고리 추가</button></a>
    <h3>카테고리 목록</h3>
    <table class="table table-hover">
      <thead>
      <tr>
        <th scope="col">카테고리 이름</th>
        <th scope="col"></th>
      </tr>
      </thead>
      <tbody>
      <c:forEach items="${categoryList}" var="l">
        <tr onClick="categoryDetail(${l.seq})">
          <c:if test="${l.ref eq 0}"><td>자유게시판</td></c:if>
          <c:if test="${l.ref ne 0}"><td>${l.name}</td></c:if>
        </tr>
      </c:forEach>
      </tbody>
    </table>

  </main>
  <%@ include file="../../common/footer.jsp" %>
</body>
<script>
  let currentUrl = document.location.pathname;
  const categoryDetail = (no) => {
    location.href = "category/" + no;
  }


</script>
</html>
