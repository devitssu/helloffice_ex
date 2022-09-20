package com.kh.helloffice.admin.controller;

import com.kh.helloffice.AdminLevel;
import com.kh.helloffice.Level;
import com.kh.helloffice.admin.entity.DeptDetail;
import com.kh.helloffice.admin.service.AdminDeptService;
import com.kh.helloffice.hr.entity.DeptDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/dept")
@RequiredArgsConstructor
@Slf4j
@Level(adminLevel = AdminLevel.ADMIN)
public class AdminDeptController {

    private final AdminDeptService service;

    @GetMapping
    public String deptAdminPage(Model model) throws Exception {

        List<DeptDto> deptList = service.getDeptList();
        model.addAttribute("deptList", deptList);

        return "admin/dept/main";
    }

    @PostMapping
    @ResponseBody
    public int addNewDept(@RequestParam String name) throws Exception {
        int result = service.addNewDept(name);
        return result;
    }

    @GetMapping("/{depNo}")
    @ResponseBody
    public DeptDetail deptEmpList(@PathVariable long depNo) throws Exception {
        DeptDetail result = service.getDeptEmpList(depNo);
        return result;
    }

    @PatchMapping("/{depNo}")
    @ResponseBody
    public int changeDepName(@PathVariable int depNo, @RequestBody String name) throws Exception {
        log.info("name={}",name);
        DeptDto changeDept = new DeptDto();
        changeDept.setDepNo(depNo);
        changeDept.setDepName(name);
        int result = service.editDeptName(changeDept);
        return result;
    }

}
