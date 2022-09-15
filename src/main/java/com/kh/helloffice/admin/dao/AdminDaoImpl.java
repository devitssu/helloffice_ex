package com.kh.helloffice.admin.dao;

import com.kh.helloffice.hr.entity.DeptDto;
import com.kh.helloffice.member.entity.MemberDto;
import lombok.AllArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class AdminDaoImpl implements AdminDao{

    private final SqlSession session;
    @Override
    public List<DeptDto> getDeptList() throws Exception {
        return session.selectList("admin.getDeptList");
    }

    @Override
    public int addNewEmp(MemberDto member) throws Exception {
        return session.insert("admin.insertEmp", member);
    }

    @Override
    public List<MemberDto> getEmpList() throws Exception {
        return session.selectList("admin.getEmpList");
    }

    @Override
    public MemberDto getEmp(long empNo) throws Exception {
        return session.selectOne("admin.getEmp",empNo);
    }

    @Override
    public int editEmp(MemberDto member) throws Exception {
        return session.update("admin.editEmp", member);
    }
}
