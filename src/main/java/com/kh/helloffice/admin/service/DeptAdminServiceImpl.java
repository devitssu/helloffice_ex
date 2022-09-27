package com.kh.helloffice.admin.service;

import com.kh.helloffice.admin.dao.DeptAdminDao;
import com.kh.helloffice.admin.entity.DeptEmp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeptAdminServiceImpl implements DeptAdminService{

    private final DeptAdminDao dao;
    @Override
    public List<DeptEmp> getAdminList(long deptNo) throws Exception {
        return dao.getAdminList(deptNo);
    }

    @Override
    public int downLevel(long empNo) throws Exception {
        return dao.downLevel(empNo);
    }
}
