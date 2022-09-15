package com.kh.helloffice.admin.controller;

import com.kh.helloffice.admin.service.AdminService;
import com.kh.helloffice.hr.entity.DeptDto;
import com.kh.helloffice.member.entity.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
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
@RequestMapping("/admin/emp")
@RequiredArgsConstructor
@Slf4j
public class AdminEmpController {

    private final AdminService service;

    @GetMapping
    public String adminPage(Model model) throws Exception{
        List<MemberDto> empList = service.getEmpList();
        model.addAttribute("empList", empList);
        return "admin/emp/emp";
    }

    @GetMapping("/new")
    public String addForm(Model model) throws Exception {
        List<DeptDto> deptList = service.getDeptList();
        model.addAttribute("deptList", deptList);
        return "admin/emp/addForm";
    }

    @PostMapping("/new")
    public String addNewEmp(@RequestParam MemberDto member) throws Exception {
        int result = service.addNewEmp(member);
        if(result > 0) return "redirect:/admin/emp";
        else return "redirect:/admin/emp/new";
    }

    @GetMapping("/{empNo}")
    public String empDetail(@PathVariable long empNo, Model model) throws Exception {
        MemberDto emp = service.getEmp(empNo);
        List<DeptDto> deptList = service.getDeptList();

        model.addAttribute("deptList", deptList);
        model.addAttribute("emp", emp);
        return "admin/emp/emp-detail";
    }

    @PatchMapping("/{empNo}")
    public String editEmp(@PathVariable long empNo, MemberDto member) throws Exception {
        member.setEmpNo(empNo);
        int result = service.editEmp(member);
        if(result > 0)return "redirect:/admin/emp";
        else return "redirect:/admin/emp/" + empNo;
    }

    @GetMapping("/exel-download")
    public void exelDownload(HttpServletResponse response) throws Exception {
        List<MemberDto> empList = service.getEmpList();

        XSSFWorkbook wb= new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("사용자_목록");
        Row row;
        Cell cell;

        sheet.setColumnWidth(0,2800);
        sheet.setColumnWidth(1,3000);
        sheet.setColumnWidth(2,3500);
        sheet.setColumnWidth(3,3500);
        sheet.setColumnWidth(4,6000);
        sheet.setColumnWidth(5,6800);

        int cellCount = 0;
        int rowNum = 0;

        row = sheet.createRow(rowNum++);
        String[] category = {"사원번호", "부서", "사원명", "직급", "직무", "입사일"};

        XSSFCellStyle titleStyle = wb.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setBorderTop(BorderStyle.THIN);
        titleStyle.setBorderLeft(BorderStyle.THIN);
        titleStyle.setBorderBottom(BorderStyle.THIN);
        titleStyle.setBorderRight(BorderStyle.THIN);
        titleStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font font = wb.createFont();
        font.setBold(true);
        titleStyle.setFont(font);

        for (String s: category) {
            cell = row.createCell(cellCount++);
            cell.setCellValue(s);
            cell.setCellStyle(titleStyle);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        CellStyle contentStyle = wb.createCellStyle();
        contentStyle.setAlignment(HorizontalAlignment.CENTER);
        contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        contentStyle.setBorderTop(BorderStyle.THIN);
        contentStyle.setBorderLeft(BorderStyle.THIN);
        contentStyle.setBorderBottom(BorderStyle.THIN);
        contentStyle.setBorderRight(BorderStyle.THIN);

        for (MemberDto m: empList) {
            cellCount=0;
            row = sheet.createRow(rowNum++);

            cell = row.createCell(cellCount++);
            cell.setCellValue(m.getEmpNo());
            cell.setCellStyle(contentStyle);

            cell = row.createCell(cellCount++);
            cell.setCellValue(m.getDepName());
            cell.setCellStyle(contentStyle);

            cell = row.createCell(cellCount++);
            cell.setCellValue(m.getEmpName());
            cell.setCellStyle(contentStyle);

            cell = row.createCell(cellCount++);
            cell.setCellValue(m.getEmpRank());
            cell.setCellStyle(contentStyle);

            cell = row.createCell(cellCount++);
            cell.setCellValue(m.getEmpPosition());
            cell.setCellStyle(contentStyle);

            cell = row.createCell(cellCount);
            String entryDate = sdf.format(m.getEntryDate());
            cell.setCellValue(entryDate);
            cell.setCellStyle(contentStyle);

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
