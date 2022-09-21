package com.kh.helloffice.admin.controller;

import com.kh.helloffice.AdminLevel;
import com.kh.helloffice.Level;
import com.kh.helloffice.admin.entity.AlarmDto;
import com.kh.helloffice.admin.service.AdminAlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("admin/alarm")
@Level(adminLevel = AdminLevel.ADMIN)
public class AdminAlarmController {

    private final AdminAlarmService service;

    @GetMapping
    public String main(Model model) throws Exception {

        List<AlarmDto> alarmList = service.getAlarmList();
        model.addAttribute("alarmList", alarmList);

        return "admin/alarm/main";
    }

    @GetMapping("/new")
    public String newAlarmPage() {
        return "admin/alarm/add";
    }

    @PostMapping("/new")
    public String addAlarm(AlarmDto alarm) throws Exception {

        int result = service.addAlarm(alarm);
        if(result > 0) return "redirect:/admin/alarm";
        else return "redirect:/admin/alarm/new";
    }

    @GetMapping("/{no}")
    public String editPage(@PathVariable long no, Model model) throws Exception {
        AlarmDto alarm = service.getAlarm(no);
        model.addAttribute("alarm", alarm);
        return "admin/alarm/edit";
    }

    @PatchMapping("/{no}")
    public String edit(@PathVariable long no, AlarmDto alarm) throws Exception {
        alarm.setSeq(no);
        int result = service.editAlarm(alarm);
        if(result > 0) return "redirect:/admin/alarm";
        return "redirect:/admin/alarm/" + no;
    }

}
