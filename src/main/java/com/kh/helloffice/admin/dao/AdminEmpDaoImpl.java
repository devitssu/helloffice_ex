package com.kh.helloffice.admin.dao;

import com.kh.helloffice.admin.entity.ExcelEmpDto;
import com.kh.helloffice.hr.entity.DeptDto;
import com.kh.helloffice.member.entity.DeptEmp;
import lombok.AllArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class AdminEmpDaoImpl implements AdminEmpDao {

    private final SqlSession session;
    @Override
    public List<DeptDto> getDeptList() throws Exception {
        return session.selectList("admin.getDeptList");
    }

    @Override
    public int addNewEmp(DeptEmp member) throws Exception {
        return session.insert("admin.insertEmp", member);
    }

    @Override
    public List<DeptEmp> getEmpList() throws Exception {
        return session.selectList("admin.getEmpList");
    }

    @Override
    public DeptEmp getEmp(long empNo) throws Exception {
        return session.selectOne("admin.getEmp",empNo);
    }

    @Override
    public int editEmp(DeptEmp member) throws Exception {
        return session.update("admin.editEmp", member);
    }

    @Override
    public int addNewEmpList(List<ExcelEmpDto> newEmpList) throws Exception {
        int result = 0;
        for (ExcelEmpDto emp: newEmpList) {
            result += session.insert("admin.insertEmpByExcel", emp);
        }
        return result;
    }
}
