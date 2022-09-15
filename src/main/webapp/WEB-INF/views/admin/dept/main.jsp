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
    <h2>부서 관리</h2>
    <button type="button" class="btn btn-outline-primary" id="addDept">부서 추가</button>

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
        <tr onClick="deptDetail(${l.depNo})">
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
            <h3 class="modal-title"><span id="depName"></span><i class="bi bi-gear manage-icon" onclick="changeName()"></i></h3>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <h5>부서원 목록</h5>
            <small>현재 인원: <span id="totalNum"></span>명</small>
            <table class="table table-hover">
              <thead>
              <tr>
                <th scope="col">사원 번호</th>
                <th scope="col">사원명</th>
                <th scope="col">직급</th>
                <th scope="col">직무</th>
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
  const deptDetail = (no) => {
    $('#depNo').val(no);
    $.ajax({
      type:'GET',
      dataType:'json',
      url: currentUrl + "/" + no
    }).done(function(data){
      renderDetail(data);
      $('#detailModal').modal('show');
    }).fail(function (){
      alert('부서원이 존재하지 않습니다.');
    });
  }

  const renderDetail = (data) => {
    $('#empList').empty();
    const depName = data.depName;
    const totalNum = data.totalNum;
    const empList = data.empList;

    $('#depName').text(depName);
    $('#totalNum').text(totalNum);

    empList.forEach( emp => {
      const template =
              `<tr onClick="empDetail(${ '${emp.empNo}' })">
                  <td>${ '${emp.empNo}' }</td>
                  <td>${ '${emp.empName}' }</td>
                  <td>${ '${emp.empRank}' }</td>
                  <td>${ '${emp.empPosition}' }</td>
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

  const changeName = () => {
    const origin = $('#depName').text();
    const name = prompt('변경할 부서명을 입력해주세요.', origin);
    const depNo = $('#depNo').val();

    if(name != null && name !== origin){
      console.log(name);
      const check = confirm('변경하시겠습니까?');
      if(check){
        $.ajax({
          type: 'PATCH',
          url: currentUrl + "/" + depNo,
          contentType: 'application/json; charset=UTF-8',
          data: name
        }).done(function(result){
          if(result > 0) {
            alert('변경되었습니다.');
            window.location.reload();
          }
          else alert('변경중 문제가 발생했습니다.');
        }).fail(function(){
          alert('변경중 문제가 발생했습니다.');
        });
      }
    }
  }

  const empDetail = (no) => {
    location.href = "emp/" + no;
  }


</script>
</html>
