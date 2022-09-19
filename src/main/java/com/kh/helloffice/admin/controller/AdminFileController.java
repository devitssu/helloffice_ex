package com.kh.helloffice.admin.controller;

import com.kh.helloffice.admin.service.AdminFileService;
import com.kh.helloffice.board.entity.FileInfoDto;
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
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/file")
public class AdminFileController {

    private final AdminFileService service;

    @GetMapping
    public String fileAdminPage(Model model) throws Exception {
        List<FileInfoDto> fileList = service.getFileList();
        model.addAttribute("fileList", fileList);
        return "admin/file/main";
    }

    @PostMapping
    @ResponseBody
    public int deleteFiles(@RequestParam(value = "fileNo[]") List<String> fileNo) throws Exception {
        return service.deleteFiles(fileNo);
    }

    @GetMapping("/exel-download")
    public void exelDownload(HttpServletResponse response) throws Exception {
        List<FileInfoDto> fileList = service.getFileList();

        XSSFWorkbook wb= new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("첨부파일_목록");
        Row row;
        Cell cell;

        sheet.setColumnWidth(0,2800);
        sheet.setColumnWidth(1,3200);
        sheet.setColumnWidth(2,14000);
        sheet.setColumnWidth(3,10000);
        sheet.setColumnWidth(4,9000);
        sheet.setColumnWidth(5,5000);
        sheet.setColumnWidth(6,5000);

        int cellCount = 0;
        int rowNum = 0;

        row = sheet.createRow(rowNum++);
        String[] category = {"파일번호","첨부게시글번호", "저장경로", "파일명", "등록일", "파일크기", "다운로드수"};

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

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CellStyle contentStyle = wb.createCellStyle();
        contentStyle.setAlignment(HorizontalAlignment.CENTER);
        contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        contentStyle.setBorderTop(BorderStyle.THIN);
        contentStyle.setBorderLeft(BorderStyle.THIN);
        contentStyle.setBorderBottom(BorderStyle.THIN);
        contentStyle.setBorderRight(BorderStyle.THIN);

        for (FileInfoDto f: fileList) {
            cellCount=0;
            row = sheet.createRow(rowNum++);

            cell = row.createCell(cellCount++);
            cell.setCellValue(f.getSeq());
            cell.setCellStyle(contentStyle);

            cell = row.createCell(cellCount++);
            cell.setCellValue(f.getPostNo());
            cell.setCellStyle(contentStyle);

            cell = row.createCell(cellCount++);
            cell.setCellValue(f.getSavePath());
            cell.setCellStyle(contentStyle);

            cell = row.createCell(cellCount++);
            cell.setCellValue(f.getOriginName());
            cell.setCellStyle(contentStyle);

            cell = row.createCell(cellCount++);
            String regDate = sdf.format(f.getRegDate());
            cell.setCellValue(regDate);
            cell.setCellStyle(contentStyle);

            cell = row.createCell(cellCount++);
            cell.setCellValue(f.getFileSize());
            cell.setCellStyle(contentStyle);

            cell = row.createCell(cellCount);
            cell.setCellValue(f.getDownloadCnt());
            cell.setCellStyle(contentStyle);

        }

        // 컨텐츠 타입과 파일명 지정
        String fileName = new String("첨부파일_목록.xlsx".getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);  //파일이름지정.
        //response OutputStream에 엑셀 작성
        wb.write(response.getOutputStream());
        wb.close();
    }

}
