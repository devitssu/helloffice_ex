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
    <h2>첨부 파일 관리</h2>
    <h3>첨부 파일 목록</h3>
    <button type="button" id="delFile" class="btn btn-outline-danger">선택 삭제</button>
    <a href="${url}/exel-download"><button type="button" class="btn btn-outline-secondary">엑셀 다운로드</button></a>
    <table class="table table-hover">
      <thead>
      <tr>
        <th scope="col">파일 선택</th>
        <th scope="col">파일 번호</th>
        <th scope="col">파일 이름</th>
        <th scope="col">파일 크기</th>
        <th scope="col">등록일</th>
        <th scope="col">다운로드수</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach items="${fileList}" var="l">
        <tr onClick="fileDetail(${l.postNo})">
          <td class="ckBoxCol"><input class="ckBox" data-no="${l.seq}" type="checkbox"></td>
          <td>${l.seq}</td>
          <td>${l.originName}</td>
          <td>${l.fileSize}</td>
          <td>${l.regDate}</td>
          <td>${l.downloadCnt}</td>
        </tr>
      </c:forEach>
      </tbody>
    </table>

  </main>
  <%@ include file="../../common/footer.jsp" %>
</body>
<script>
  let currentUrl = document.location.pathname;
  const fileDetail = (no) => {
    location.href = "/helloffice/board/1/" + no;
  }

  $('.ckBoxCol').on('click', function (e){
    e.stopPropagation();
  });

  $('#delFile').on('click', function (){
    const checkedArr = [];
    $('.ckBox:checked').each(function (){
      checkedArr.push($(this).data("no"));
    });

    const data = {"fileNo": checkedArr};

    $.ajax({
      type: 'POST',
      url: '/helloffice/admin/file',
      data: data,
      dataType: 'json'
    }).done(function(){
      alert('선택한 파일이 삭제되었습니다.')
      history.go(0);
    }).fail(function (){
      alert('파일 삭제중 오류가 발생했습니다.')
    });
  });

</script>
</html>
