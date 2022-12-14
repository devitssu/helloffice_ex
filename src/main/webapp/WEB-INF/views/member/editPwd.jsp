<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">

  <title>Helloffice 비밀번호변경</title>


  <!-- Favicons -->
  <link href="../resources/assets/img/favicon.png" rel="icon">
  <link href="../resources/assets/img/apple-touch-icon.png" rel="apple-touch-icon">

  <!-- Google Fonts -->
  <link href="https://fonts.gstatic.com" rel="preconnect">
  <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

  <!-- Vendor CSS Files -->
  <link href="../resources/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="../resources/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
  <link href="../resources/assets/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
  <link href="../resources/assets/vendor/quill/quill.snow.css" rel="stylesheet">
  <link href="../resources/assets/vendor/quill/quill.bubble.css" rel="stylesheet">
  <link href="../resources/assets/vendor/remixicon/remixicon.css" rel="stylesheet">
  <link href="../resources/assets/vendor/simple-datatables/style.css" rel="stylesheet">

  <!-- Template Main CSS File -->
  <link href="../resources/assets/css/style.css" rel="stylesheet">

  <style>
    .hint {
      font-size: x-small;
      color: grey;
    }

    .confirmMsg {
      font-size: x-small;
    }

    .pwdformMsg {
      font-size: x-small;
    }
  </style>

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>

<body>

  <main>
    <div class="container">

      <section class="section register min-vh-100 d-flex flex-column align-items-center justify-content-center py-4">
        <div class="container">
          <div class="row justify-content-center">
            <div class="col-lg-4 col-md-6 d-flex flex-column align-items-center justify-content-center">

              <div class="d-flex justify-content-center py-4">
                <a href="/helloffice/" class="logo d-flex align-items-center w-auto">
                  <img src="../resources/assets/img/logo.png" alt="">
                </a>
              </div><!-- End Logo -->

              <div class="card mb-3">

              <form class="row g-3 needs-validation" action="" method="post" onsubmit="return check()">
                <input type="hidden" name="_method" value="PATCH">
                <div class="card-body">
                  <div class="pt-4 pb-2">
                    <h5 class="card-title text-center pb-0 fs-4">비밀번호 변경</h5>
                  </div>
                  <input type="hidden" name="empNo" value="${loginEmp.empNo}">
                    
                    <div class="col-12">
                      <label class="form-label">비밀번호</label>
                      <input type="password" name="empPwd" class="form-control" id="empPwd" required onkeyup="pwdform();">
                      <label class="hint">영문자, 숫자, 특수문자 포함하여 8자 이상 입력하시오.</label><br>
                      <span id ="pwdformMsg" class="pwdformMsg"></span>
                    </div>
                    
                    <div class="col-12">
                      <label class="form-label">비밀번호 확인</label>
                      <input type="password" name="empPwdCheck" class="form-control" id="empPwdCheck" required onkeyup="validate();">
                      <span id ="confirmMsg" class="confirmMsg"></span>
                    </div>
                    <br>
                    <div class="col-12">
                      <input class="btn btn-primary w-100" type="submit" value="비밀번호 변경">
                    </div>
                   </div>
                </form>
                </div>
              </div>
            </div>
          </div>
	      </section>
        </div>
  </main><!-- End #main -->

  <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

  <!-- Vendor JS Files -->
  <script src="../resources/assets/vendor/apexcharts/apexcharts.min.js"></script>
  <script src="../resources/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script src="../resources/assets/vendor/chart.js/chart.min.js"></script>
  <script src="../resources/assets/vendor/echarts/echarts.min.js"></script>
  <script src="../resources/assets/vendor/quill/quill.min.js"></script>
  <script src="../resources/assets/vendor/simple-datatables/simple-datatables.js"></script>
  <script src="../resources/assets/vendor/tinymce/tinymce.min.js"></script>
  <script src="../resources/assets/vendor/php-email-form/validate.js"></script>

  <!-- Template Main JS File -->
  <script src="../resources/assets/js/main.js"></script>

  <script>

  function pwdform() {
    var empPwd= document.getElementById('empPwd').value;
    var wrongColor ="#ff0000";

    // 비밀번호 형식
    if(!/^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/.test(empPwd)) {
      pwdformMsg.style.color = wrongColor;
      pwdformMsg.innerHTML = "비밀번호를 다시 입력해 주세요.";
      return false;
    } else {
      pwdformMsg.innerHTML = "";
      return true;
    }
  }

  function validate() {
    var empPwd= document.getElementById('empPwd').value;
    var empPwdCheck = document.getElementById('empPwdCheck').value;
    var confrimMsg = document.getElementById('confirmMsg');
    var correctColor = "#0000ff";
    var wrongColor ="#ff0000";

    // 비밀번호 확인 검사
    if(empPwd == empPwdCheck) {
     confirmMsg.style.color = correctColor;
      confirmMsg.innerHTML ="비밀번호 일치";
      return true;
    } else{
     confirmMsg.style.color = wrongColor;
      confirmMsg.innerHTML ="비밀번호 불일치";
      return false;
		}
  }

  const check = () => {
    if(!pwdform()) {
      alert('비밀번호가 올바르지 않습니다.')
      return false;
    }else if(!validate()){
      alert('비밀번호가 일치하지 않습니다.')
      return false;
    }else{
      confirm('비밀번호를 변경하시겠습니까?')
    }
  }
  </script>
</body>


</html>