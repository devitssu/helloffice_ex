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
    <h2>카테고리 추가</h2>
    <form action="" method="post">
      <div class="col-md-8">
        <div class="row">
          <label for="inputTitle" class="col-sm-2 col-form-label">카테고리명</label>
          <div class="col-sm-9">
            <input type="text" class="form-control" id="inputTitle" name="name" placeholder="카테고리명을 입력하세요">
          </div>
        </div>
      </div>
    <table class="table table-hover">
      <thead>
      <tr>
        <th scope="col"><input type="checkbox" id="selectAll"></th>
        <th scope="col">사원명</th>
        <th scope="col">직급</th>
        <th scope="col">직무</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach items="${empList}" var="l">
        <tr>
          <td class="ckBoxCol"><input class="ckBox" name="empNoList" value="${l.empNo}" type="checkbox"></td>
          <td>${l.empName}</td>
          <td>${l.empRank}</td>
          <td>${l.empPosition}</td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
      <button type="submit" class="btn btn-primary">추가하기</button>
    </form>
  </main>
  <%@ include file="../../common/footer.jsp" %>
</body>
<script>
$('#selectAll').click(function () {
  if($('#selectAll').is(':checked')){
    $('.ckBox').prop('checked', true);
  }else{
    $('.ckBox').prop('checked', false);
  }
})

</script>
</html>
