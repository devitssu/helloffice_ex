package com.kh.helloffice.hr.dao;

import java.util.List;

import com.kh.helloffice.hr.entity.AcademicDto;
import com.kh.helloffice.hr.entity.CareerDto;
import com.kh.helloffice.hr.entity.DeptDto;
import com.kh.helloffice.member.entity.DeptEmp;

public interface HrMyPageDao {

	int editInsaPage(DeptEmp dto) throws Exception;

	DeptEmp getMember(DeptEmp dto) throws Exception;

	int editBasicPage(DeptEmp dto) throws Exception;

	List<DeptDto> getDeptList() throws Exception;

	List<DeptEmp> getInsaPageInfo(int empNo) throws Exception;

	List<DeptEmp> getBasicPageInfo(int empNo) throws Exception;

	List<CareerDto> getCareerInfo(int empNo) throws Exception;

	CareerDto getMyCareer(long empNo) throws Exception;

	AcademicDto getMyAca(long empNo) throws Exception;

	int createMyCareer(CareerDto dto) throws Exception;

	int udtMyAca(int empNo) throws Exception;

	int careerUdt(CareerDto dto) throws Exception;

	int createMyAca(AcademicDto dto) throws Exception;

	int academicUdt(AcademicDto dto) throws Exception;

}
