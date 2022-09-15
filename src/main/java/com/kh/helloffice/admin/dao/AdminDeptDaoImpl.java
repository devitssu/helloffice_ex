package com.kh.helloffice.admin.dao;

import com.kh.helloffice.admin.entity.DeptDetail;
import com.kh.helloffice.hr.entity.DeptDto;
import com.kh.helloffice.member.entity.DeptEmp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AdminDeptDaoImpl implements AdminDeptDao{

    private final SqlSession session;

    @Override
    public List<DeptDto> getDeptList() throws Exception {
        return session.selectList("admin.getDeptList");
    }

    @Override
    public int addNewDept(String name) throws Exception {
        return session.insert("admin.addDept", name);
    }

    @Override
    public DeptDetail getDeptDetail(long depNo) throws Exception {
        return session.selectOne("admin.getDeptDetail", depNo);
    }

    @Override
    public List<DeptEmp> getDeptEmpList(long depNo) throws Exception {
        return session.selectList("admin.getDeptEmpList", depNo);
    }

    @Override
    public int editDeptName(DeptDto changeDept) throws Exception {
        return session.update("admin.changeDept", changeDept);
    }
}
