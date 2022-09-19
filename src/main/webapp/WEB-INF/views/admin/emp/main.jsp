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
    <button type="button" class="btn btn-outline-success" id="excelUpload">엑셀로 신규 추가</button>
    <form action="excel" method="post" enctype="multipart/form-data" style="display: none" id="uploadForm">
      <input type="file" id="fileInput" name="excelFile">
      <button type="button" class="btn btn-primary" id="excelSubmit">제출</button>
      <button type="reset" class="btn btn-secondary">취소</button>
    </form>

    <h3>사용자 목록</h3>
    <a href="${url}/excel"><button type="button" class="btn btn-outline-secondary">엑셀 다운로드</button></a>
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

  $('#excelUpload').on('click',  function (){
    $('#uploadForm').toggle();
  });

  $('#excelSubmit').on('click', function (){
    if(checkFile()){
      if(confirm('제출하시겠습니까?')){
        const data = new FormData($('#uploadForm')[0]);
        $.ajax({
          type: 'POST',
          url: '/helloffice/admin/emp/upload',
          enctype: 'multipart/form-data',
          data: data,
          contentType: false,
          processData: false
        }).done(function(){
          alert('업로드한 사용자가 등록되었습니다.')
          history.go(0);
        }).fail(function (){
          alert('업로드중 오류가 발생했습니다.')
        });
      }
    }else{
      $('#fileInput').val("");
    }
  });

  const checkFile = () => {
    const file = $('#fileInput').val();
    if(file == "" || file == null){
      alert('파일을 선택해주세요.');
      return false;
    }else if(!checkExt(file)){
      alert('엑셀 파일만 업로드할 수 있습니다.');
      return false;
    }else return true;
  }

  const checkExt = (file) => {
    let ext = file.split('.').pop();
    if(ext == 'xls' || ext == 'xlsx') return true;
    else return false;
  }

</script>
</html>
