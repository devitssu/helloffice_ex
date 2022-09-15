package com.kh.helloffice.admin.service;

import com.kh.helloffice.admin.dao.AdminDeptDao;
import com.kh.helloffice.admin.entity.DeptDetail;
import com.kh.helloffice.hr.entity.DeptDto;
import com.kh.helloffice.member.entity.DeptEmp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminDeptServiceImpl implements AdminDeptService{

    private final AdminDeptDao dao;

    @Override
    public List<DeptDto> getDeptList() throws Exception {
        return dao.getDeptList();
    }

    @Override
    public int addNewDept(String name) throws Exception {
        return dao.addNewDept(name);
    }

    @Override
    public DeptDetail getDeptEmpList(long depNo) throws Exception {
        DeptDetail detail = dao.getDeptDetail(depNo);
        if(detail != null){
            List<DeptEmp> deptEmpList = dao.getDeptEmpList(depNo);
            detail.setEmpList(deptEmpList);
        }
        return detail;
    }

    @Override
    public int editDeptName(DeptDto changeDept) throws Exception {
        return dao.editDeptName(changeDept);
    }
}
