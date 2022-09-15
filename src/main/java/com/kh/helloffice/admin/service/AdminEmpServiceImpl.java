package com.kh.helloffice.admin.service;

import com.kh.helloffice.admin.dao.AdminEmpDao;
import com.kh.helloffice.hr.entity.DeptDto;
import com.kh.helloffice.member.entity.DeptEmp;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminEmpServiceImpl implements AdminEmpService {

    private final AdminEmpDao dao;
    private final PasswordEncoder pe;

    @Override
    public List<DeptDto> getDeptList() throws Exception {
        return dao.getDeptList();
    }

    @Override
    public int addNewEmp(DeptEmp member) throws Exception {
        String pwd = "hello1234!";
        String encodedPwd = pe.encode(pwd);
        member.setEmpPwd(encodedPwd);
        return dao.addNewEmp(member);
    }

    @Override
    public List<DeptEmp> getEmpList() throws Exception {
        return dao.getEmpList();
    }

    @Override
    public DeptEmp getEmp(long empNo) throws Exception {
        return dao.getEmp(empNo);
    }

    @Override
    public int editEmp(DeptEmp member) throws Exception {
        return dao.editEmp(member);
    }
}
