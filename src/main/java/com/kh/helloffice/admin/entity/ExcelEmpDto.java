package com.kh.helloffice.admin.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class ExcelEmpDto {

    private int depNo; // 부서번호
    private int adminLevel; // 관리 레벨
    private String empRank; // 직급
    private String empPosition; // 직무
    private String empName; // 사원명
    private String email; // 이메일(계정 ID)
    private String empPwd; // 계정 비밀번호
    private String phone; // 전화번호
    private String resiNo; // 주민등록번호
    private String address; // 주소
    private String bank; // 은행이름
    private String bankAcc; // 급여계좌
}
