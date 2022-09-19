package com.kh.helloffice.admin.dao;

import com.kh.helloffice.admin.entity.ExcelEmpDto;
import com.kh.helloffice.hr.entity.DeptDto;
import com.kh.helloffice.member.entity.DeptEmp;

import java.util.List;

public interface AdminEmpDao {
    List<DeptDto> getDeptList() throws Exception;

    int addNewEmp(DeptEmp member) throws Exception;

    List<DeptEmp> getEmpList() throws Exception;

    DeptEmp getEmp(long empNo) throws Exception;

    int editEmp(DeptEmp member) throws Exception;

    int addNewEmpList(List<ExcelEmpDto> newEmpList) throws Exception;
}
