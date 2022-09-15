package com.kh.helloffice.admin.dao;

import com.kh.helloffice.hr.entity.DeptDto;
import com.kh.helloffice.member.entity.MemberDto;

import java.util.List;

public interface AdminDao {
    List<DeptDto> getDeptList() throws Exception;

    int addNewEmp(MemberDto member) throws Exception;

    List<MemberDto> getEmpList() throws Exception;

    MemberDto getEmp(long empNo) throws Exception;

    int editEmp(MemberDto member) throws Exception;
}
