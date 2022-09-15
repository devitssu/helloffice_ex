package com.kh.helloffice.admin.dao;

import com.kh.helloffice.admin.entity.DeptDetail;
import com.kh.helloffice.hr.entity.DeptDto;
import com.kh.helloffice.member.entity.DeptEmp;

import java.util.List;

public interface AdminDeptDao {
    List<DeptDto> getDeptList() throws Exception;

    int addNewDept(String name) throws Exception;

    DeptDetail getDeptDetail(long depNo) throws Exception;

    List<DeptEmp> getDeptEmpList(long depNo) throws Exception;

    int editDeptName(DeptDto changeDept) throws Exception;
}
