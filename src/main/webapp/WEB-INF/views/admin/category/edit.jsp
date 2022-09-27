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
    <h2>카테고리 수정</h2>
    <form action="" method="post">
      <input type="hidden" name="_method" value="PATCH">
      <div class="col-md-8">
        <div class="row">
          <label for="inputTitle" class="col-sm-2 col-form-label">카테고리명</label>
          <div class="col-sm-9">
            <input type="text" class="form-control" id="inputTitle" name="name" value="${detail.name}">
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
      <button type="submit" class="btn btn-primary">수정하기</button>
      <button type="button" class="btn btn-danger" id="deleteBtn">삭제하기</button>
    </form>
  </main>
  <%@ include file="../../common/footer.jsp" %>
</body>
<script>
  let currentUrl = document.location.pathname;
  let idx = currentUrl.lastIndexOf('/');
  idx = currentUrl.lastIndexOf('/', idx -1);
  let target = currentUrl.substring(0,idx);
  console.log(target);

  $(document).ready(function () {
    const empNoList = new Array();
    <c:forEach items="${detail.empNoList}" var="n">
      empNoList.push(${n});
    </c:forEach>
    empNoList.forEach(function (n){
      $('input[value='+n+']').attr('checked', true);
    })
  })

  $('#selectAll').click(function () {
    if($('#selectAll').is(':checked')){
      $('.ckBox').prop('checked', true);
    }else{
      $('.ckBox').prop('checked', false);
    }
  })

  $('#deleteBtn').click(function (){
    if(confirm('삭제하시겠습니까?')){
      $.ajax({
        type: 'DELETE',
        url: currentUrl
      }).done(function (){
        alert('삭제되었습니다.')
        location.href = target + '/categories'
      }).fail(function (){
        alert('문제가 발생했습니다.')
      })
    }
  });

</script>
</html>
