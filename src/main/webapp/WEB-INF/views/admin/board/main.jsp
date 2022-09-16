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
    <h2>게시판 관리</h2>
    <a href="board/post"><button type="button" class="btn btn-outline-primary">공지 작성</button></a>
    <h3>게시판 목록</h3>
    <table class="table table-hover">
      <thead>
      <tr>
        <th scope="col">게시판 번호</th>
        <th scope="col">게시판 이름</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach items="${boardList}" var="l">
        <tr onClick="boardDetail(${l.depNo})">
          <td>${l.depNo}</td>
          <c:if test="${l.depName eq '대표'}"><td>전체</td></c:if>
          <c:if test="${l.depName ne '대표'}"><td>${l.depName}</td></c:if>
        </tr>
      </c:forEach>
      </tbody>
    </table>

  </main>
  <%@ include file="../../common/footer.jsp" %>
</body>
<script>
  let currentUrl = document.location.pathname;
  const boardDetail = (no) => {
    location.href = "board/" + no;
  }


</script>
</html>
