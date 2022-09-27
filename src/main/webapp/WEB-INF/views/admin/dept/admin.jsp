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
    <h2>부서 관리자 관리</h2>

    <h3>부서 목록</h3>
    <table class="table table-hover">
      <thead>
      <tr>
        <th scope="col">부서번호</th>
        <th scope="col">부서명</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach items="${deptList}" var="l">
        <tr onClick="deptDetail(${l.depNo}, '${l.depName}')">
          <td>${l.depNo}</td>
          <td>${l.depName}</td>
        </tr>
      </c:forEach>
      </tbody>
    </table>

    <div class="modal fade" id="detailModal" tabindex="-1">
      <div class="modal-dialog modal-dialog-centered modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h3 class="modal-title"><span id="depName"></span></h3>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <h5>관리자 목록</h5>
            <table class="table table-hover">
              <thead>
              <tr>
                <th scope="col">사원 번호</th>
                <th scope="col">사원명</th>
                <th scope="col">직급</th>
                <th scope="col">직무</th>
                <th scope="col"></th>
              </tr>
              </thead>
              <tbody id="empList">
              </tbody>
            </table>
            <input type="hidden" id="depNo">
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
          </div>
        </div>
      </div>
    </div><!-- End Modal-->


  </main>
  <%@ include file="../../common/footer.jsp" %>
</body>
<script>
  let currentUrl = document.location.pathname;
  const deptDetail = (no, name) => {
    $('#depNo').val(no);
    $.ajax({
      type:'GET',
      dataType:'json',
      url: currentUrl + "/" + no
    }).done(function(data){
      renderDetail(data, name);
      $('#detailModal').modal('show');
    }).fail(function (){
      alert('부서원이 존재하지 않습니다.');
    });
  }

  const renderDetail = (data, name) => {
    $('#empList').empty();
    $('#depName').text(name);

    data.forEach( emp => {
      const template =
              `<tr>
                  <td>${ '${emp.empNo}' }</td>
                  <td>${ '${emp.empName}' }</td>
                  <td>${ '${emp.empRank}' }</td>
                  <td>${ '${emp.empPosition}' }</td>
                  <td><button  onClick="downLevel(${ '${emp.empNo}' })">해제</button></td>
                </tr>`;
      $('#empList').append(template);
    });
  }

  $('#addDept').on('click', function (){
    const name = prompt('추가할 부서명을 입력해주세요.', '부서명');

    if(name != null){
      const check = confirm('추가하시겠습니까?');
      if(check){
        $.ajax({
          type: 'POST',
          url: currentUrl,
          data: {'name':name}
        }).done(function(result){
          if(result > 0) {
            alert('추가되었습니다.');
            window.location.reload();
          }
          else alert('추가중 문제가 발생했습니다.');
        }).fail(function(){
          alert('추가중 문제가 발생했습니다.');
        });
      }
    }
  });

  const empDetail = (no) => {
    location.href = "emp/" + no;
  }

  const downLevel = (no) => {
    if(confirm('이 관리자를 해제하시겠습니까?')){
      $.ajax({
        type: 'PATCH',
        url: currentUrl + "/" + no
      }).done(function () {
        alert('해제되었습니다.')
        history.go(0);
      }).fail(function () {

      })
    }
  }


</script>
</html>
