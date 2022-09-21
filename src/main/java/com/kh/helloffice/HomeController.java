package com.kh.helloffice;

import java.util.List;

import com.kh.helloffice.admin.service.AdminAlarmService;
import com.kh.helloffice.member.entity.DeptEmp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.kh.helloffice.board.entity.PostDto;
import com.kh.helloffice.board.service.BoardService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
	
	private final BoardService boardService;
	private final AdminAlarmService alarmService;

	@GetMapping()
	public String main(Model model) throws Exception {
		List<PostDto> notices = boardService.getRecentList();
		model.addAttribute("notices", notices);
		return "main";
	}

	@GetMapping("/alarm")
	@ResponseBody
	public List<String> alarm(HttpSession session) throws Exception {
		DeptEmp loginEmp = (DeptEmp) session.getAttribute("loginEmp");
		long empNo = loginEmp.getEmpNo();
		return alarmService.getValidAlarm(empNo);
	}

	@PatchMapping("{empNo}")
	public void updateLogin(@PathVariable long empNo) throws Exception {
		int result = alarmService.updateLogin(empNo);
		if(result == 0 ) throw new RuntimeException("로그인 시간 업데이트 실패");
	}
	
}
