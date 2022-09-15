package com.kh.helloffice.admin.controller;

import com.kh.helloffice.admin.service.AdminService;
import com.kh.helloffice.hr.entity.DeptDto;
import com.kh.helloffice.member.entity.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AdminService service;

    @GetMapping("/emp")
    public String adminPage(Model model) throws Exception{
        List<MemberDto> empList = service.getEmpList();
        model.addAttribute("empList", empList);
        return "admin/emp";
    }

    @GetMapping("/emp/new")
    public String addForm(Model model) throws Exception {
        List<DeptDto> deptList = service.getDeptList();
        model.addAttribute("deptList", deptList);
        return "admin/addForm";
    }

    @PostMapping("/emp/new")
    public String addNewEmp(@RequestParam MemberDto member) throws Exception {
        int result = service.addNewEmp(member);
        if(result > 0) return "redirect:/admin/emp";
        else return "redirect:/admin/emp/new";
    }

    @GetMapping("/emp/{empNo}")
    public String empDetail(@PathVariable long empNo, Model model) throws Exception {
        MemberDto emp = service.getEmp(empNo);
        List<DeptDto> deptList = service.getDeptList();

        model.addAttribute("deptList", deptList);
        model.addAttribute("emp", emp);
        return "admin/emp-detail";
    }

    @PatchMapping("/emp/{empNo}")
    public String editEmp(@PathVariable long empNo, MemberDto member) throws Exception {
        member.setEmpNo(empNo);
        int result = service.editEmp(member);
        if(result > 0)return "redirect:/admin/emp";
        else return "redirect:/admin/emp/" + empNo;
    }

    @GetMapping("/emp/exel-download")
    public void exelDownload(HttpServletResponse response) throws Exception {
        List<MemberDto> empList = service.getEmpList();

        XSSFWorkbook wb= new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("사용자_목록");
        Row row;
        Cell cell;

        //첫행   열 이름 표기
        int cellCount = 0;
        int rowNum = 0;

        row = sheet.createRow(rowNum++);
        String[] category = {"사원번호", "부서", "사원명", "직급", "직무", "입사일"};

        for (String s: category) {
            cell = row.createCell(cellCount++);
            cell.setCellValue(s);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (MemberDto m: empList) {
            cellCount=0;
            row = sheet.createRow(rowNum++);

            cell = row.createCell(cellCount++);
            cell.setCellValue(m.getEmpNo());

            cell = row.createCell(cellCount++);
            cell.setCellValue(m.getDepName());

            cell = row.createCell(cellCount++);
            cell.setCellValue(m.getEmpName());

            cell = row.createCell(cellCount++);
            cell.setCellValue(m.getEmpRank());

            cell = row.createCell(cellCount++);
            cell.setCellValue(m.getEmpPosition());

            cell = row.createCell(cellCount);
            String entryDate = sdf.format(m.getEntryDate());
            cell.setCellValue(entryDate);

        }

        // 컨텐츠 타입과 파일명 지정
        String fileName = new String("사용자_목록.xlsx".getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);  //파일이름지정.
        //response OutputStream에 엑셀 작성
        wb.write(response.getOutputStream());
        wb.close();
    }

}
