package com.kh.helloffice.admin.controller;

import com.kh.helloffice.AdminLevel;
import com.kh.helloffice.Level;
import com.kh.helloffice.admin.entity.DeptEmp;
import com.kh.helloffice.admin.service.AdminDeptService;
import com.kh.helloffice.admin.service.DeptAdminService;
import com.kh.helloffice.hr.entity.DeptDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@Level(adminLevel = AdminLevel.ADMIN)
@RequestMapping("/admin/dept-admin")
public class DeptAdminController {

    private final DeptAdminService service;
    private final AdminDeptService deptService;

    @GetMapping
    public String main(Model model) throws Exception {
        List<DeptDto> deptList = deptService.getDeptList();
        model.addAttribute("deptList", deptList);
        return "admin/dept/admin";
    }

    @GetMapping("/{deptNo}")
    @ResponseBody
    public List<DeptEmp> adminList(@PathVariable long deptNo) throws Exception {
        return service.getAdminList(deptNo);
    }

    @PatchMapping("/{empNo}")
    @ResponseBody
    public int downLevel(@PathVariable long empNo) throws Exception {
        return service.downLevel(empNo);
    }
}
