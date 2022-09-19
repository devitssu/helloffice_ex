package com.kh.helloffice.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.helloffice.member.dao.MemberDao;
import com.kh.helloffice.member.entity.DeptEmp;

@Service
@Transactional
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberDao dao;
	
	@Autowired
	private PasswordEncoder pe;

	@Override
	public DeptEmp login(DeptEmp dto) throws Exception {
		
		DeptEmp dbEmp = dao.getMember(dto);
		
		if(pe.matches(dto.getEmpPwd(), dbEmp.getEmpPwd())) {
			if(pe.matches("12345", dbEmp.getEmpPwd())){
				dbEmp.setFirst(true);
			}
			return dbEmp;
		} else {
			return null;
		}
	}

	@Override
	public int emailCheck(String email) throws Exception {
		
		return dao.emailCheck(email);
	}

	@Override
	public int join(DeptEmp dto) throws Exception {
		int no = dao.getMemberSeq();
		dto.setEmpNo(no);
		dto.setEmpPwd(pe.encode(dto.getEmpPwd()));
		int result = dao.insertMember(dto);
		
		return result;
	}

	@Override
	public int editPwd(DeptEmp editEmp) throws Exception {
		editEmp.setEmpPwd(pe.encode(editEmp.getEmpPwd()));
		return dao.editPwd(editEmp);
	}

}
