package com.kh.helloffice.admin.dao;

import com.kh.helloffice.admin.entity.DeptEmp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class DeptAdminDaoImpl implements DeptAdminDao{

    private final SqlSession session;

    @Override
    public List<DeptEmp> getAdminList(long deptNo) throws Exception {
        return session.selectList("admin.getAdminList", deptNo);
    }

    @Override
    public int downLevel(long empNo) throws Exception {
        return session.update("admin.downLevel", empNo);
    }
}
