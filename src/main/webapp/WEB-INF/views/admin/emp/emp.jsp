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
    <h2>사용자 관리</h2>
    <a href="${url}/new"><button type="button" class="btn btn-outline-primary">신규 추가</button></a>

    <h3>사용자 목록</h3>
    <a href="${url}/exel-download"><button type="button" class="btn btn-outline-secondary">엑셀 다운로드</button></a>
    <table class="table table-hover">
      <thead>
      <tr>
        <th scope="col">사원번호</th>
        <th scope="col">부서</th>
        <th scope="col">사원명</th>
        <th scope="col">직급</th>
        <th scope="col">직무</th>
        <th scope="col">입사일</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach items="${empList}" var="l">
        <tr onClick="empDetail(${l.empNo})">
          <td>${l.empNo}</td>
          <td>${l.depName}</td>
          <td>${l.empName}</td>
          <td>${l.empRank}</td>
          <td>${l.empPosition}</td>
          <td>${l.entryDate}</td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </main>
  <%@ include file="../../common/footer.jsp" %>
</body>
<script>
  function empDetail(no){
    location.href = "emp/" + no;
  }
</script>
</html>
