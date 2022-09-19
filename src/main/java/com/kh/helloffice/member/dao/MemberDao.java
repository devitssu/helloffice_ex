package com.kh.helloffice.member.dao;

import com.kh.helloffice.member.entity.DeptEmp;

public interface MemberDao {

	DeptEmp getMember(DeptEmp dto) throws Exception;
	
	int emailCheck(String email) throws Exception;

	int getMemberSeq() throws Exception;

	int insertMember(DeptEmp dto) throws Exception;

	void insertProfile(DeptEmp dto) throws Exception;

    int editPwd(DeptEmp editEmp) throws Exception;
}
