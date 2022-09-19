package com.kh.helloffice.admin.service;

import com.kh.helloffice.admin.dao.AdminEmpDao;
import com.kh.helloffice.admin.entity.ExcelEmpDto;
import com.kh.helloffice.hr.entity.DeptDto;
import com.kh.helloffice.member.entity.DeptEmp;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
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

    @Override
    public int addByExcel(MultipartFile file) throws Exception {
        List<ExcelEmpDto> newEmpList = new ArrayList<>();
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        Workbook wb = null;

            if (ext.equals("xlsx")) {
                wb = new XSSFWorkbook(file.getInputStream());
            } else if (ext.equals("xls")) {
                wb = new HSSFWorkbook(file.getInputStream());
            }

        Sheet sheet = wb.getSheetAt(0);

        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            ExcelEmpDto data = new ExcelEmpDto();

            data.setDepNo((int)row.getCell(0).getNumericCellValue());
            data.setAdminLevel((int)row.getCell(1).getNumericCellValue());
            data.setEmpRank(row.getCell(2).getStringCellValue());
            data.setEmpPosition(row.getCell(3).getStringCellValue());
            data.setEmpName(row.getCell(4).getStringCellValue());
            data.setEmail(row.getCell(5).getStringCellValue());
            data.setPhone(row.getCell(6).getStringCellValue());
            data.setResiNo(row.getCell(7).getStringCellValue());
            data.setAddress(row.getCell(8).getStringCellValue());
            data.setBank(row.getCell(9).getStringCellValue());
            data.setBankAcc(row.getCell(10).getStringCellValue());

            String pwd = "12345";
            String encodedPwd = pe.encode(pwd);
            data.setEmpPwd(encodedPwd);
            newEmpList.add(data);
        }

        return dao.addNewEmpList(newEmpList);
    }
}
