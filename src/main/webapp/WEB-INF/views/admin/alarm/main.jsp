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
    <h2>알림 관리</h2>
    <a href="alarm/new"><button type="button" class="btn btn-outline-primary">알림 작성</button></a>
    <h3>알림 목록</h3>
    <table class="table table-hover">
      <thead>
      <tr>
        <th scope="col">알림 번호</th>
        <th scope="col">내용</th>
        <th scope="col">시작일</th>
        <th scope="col">종료일</th>
        <th scope="col"></th>
      </tr>
      </thead>
      <tbody>
      <c:forEach items="${alarmList}" var="l">
        <tr>
          <td>${l.seq}</td>
          <td>${l.message}</td>
          <td>${l.startTime}</td>
          <td>${l.endTime}</td>
          <td><button onclick="edit(${l.seq})">수정</button></td>
        </tr>
      </c:forEach>
      </tbody>
    </table>

  </main>
  <%@ include file="../../common/footer.jsp" %>
</body>
<script>
  const edit = (no) => {
    location.href = "alarm/" + no;
  }


</script>
</html>
