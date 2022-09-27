package com.kh.helloffice.admin.service;

import com.kh.helloffice.admin.entity.DeptEmp;

import java.util.List;

public interface DeptAdminService {
    List<DeptEmp> getAdminList(long deptNo) throws Exception;

    int downLevel(long empNo) throws Exception;
}
