package com.kh.helloffice.admin.service;

import com.kh.helloffice.admin.dao.AdminDao;
import com.kh.helloffice.hr.entity.DeptDto;
import com.kh.helloffice.member.entity.MemberDto;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final AdminDao dao;
    private final PasswordEncoder pe;

    @Override
    public List<DeptDto> getDeptList() throws Exception {
        return dao.getDeptList();
    }

    @Override
    public int addNewEmp(MemberDto member) throws Exception {
        String pwd = "hello1234!";
        String encodedPwd = pe.encode(pwd);
        member.setEmpPwd(encodedPwd);
        return dao.addNewEmp(member);
    }

    @Override
    public List<MemberDto> getEmpList() throws Exception {
        return dao.getEmpList();
    }

    @Override
    public MemberDto getEmp(long empNo) throws Exception {
        return dao.getEmp(empNo);
    }

    @Override
    public int editEmp(MemberDto member) throws Exception {
        return dao.editEmp(member);
    }
}
