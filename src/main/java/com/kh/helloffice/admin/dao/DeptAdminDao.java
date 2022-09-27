package com.kh.helloffice.admin.dao;

import com.kh.helloffice.admin.entity.DeptEmp;

import java.util.List;

public interface DeptAdminDao {
    List<DeptEmp> getAdminList(long deptNo) throws Exception;

    int downLevel(long empNo) throws Exception;
}
