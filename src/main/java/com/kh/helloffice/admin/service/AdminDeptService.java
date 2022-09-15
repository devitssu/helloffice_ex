package com.kh.helloffice.admin.service;

import com.kh.helloffice.admin.entity.DeptDetail;
import com.kh.helloffice.hr.entity.DeptDto;

import java.util.List;

public interface AdminDeptService {
    List<DeptDto> getDeptList() throws Exception;

    int addNewDept(String name) throws Exception;

    DeptDetail getDeptEmpList(long depNo) throws Exception;

    int editDeptName(DeptDto changeDept) throws Exception;
}
