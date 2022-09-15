package com.kh.helloffice.hr.service;

import java.util.List;

import com.kh.helloffice.hr.entity.AllDto;
import com.kh.helloffice.hr.entity.DeptDto;
import com.kh.helloffice.hr.entity.InsaNoteDto;
import com.kh.helloffice.member.entity.DeptEmp;

public interface HrService {

	List<DeptEmp> getTeamList() throws Exception;

	List<DeptDto> getDeptList() throws Exception;

	int cntDepName(String depName) throws Exception;

	int insertDept(DeptDto deptDto) throws Exception;

	int updDeptName(DeptDto deptDto) throws Exception;

	int delDeptName(String depName) throws Exception;

	List<DeptEmp> getMemberListByDept(String deptName) throws Exception;

	List<DeptEmp> getMyTeamList(String depName) throws Exception;

	AllDto getMemberInfo(int empNo) throws Exception;

	List<DeptEmp> getSearchList(DeptEmp memberDto) throws Exception;

	List<InsaNoteDto> getInsanote(int empNo) throws Exception;

	int insaDel(int delNo) throws Exception;

	int addInsaNote(InsaNoteDto dto) throws Exception;


}
