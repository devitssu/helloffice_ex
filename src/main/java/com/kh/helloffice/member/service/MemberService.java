package com.kh.helloffice.member.service;

import com.kh.helloffice.member.entity.DeptEmp;

public interface MemberService {

	DeptEmp login(DeptEmp dto) throws Exception;
	
	int emailCheck(String email) throws Exception;

	int join(DeptEmp dto) throws Exception;

    int editPwd(DeptEmp editEmp) throws Exception;
}
