package com.kh.helloffice.admin.controller;

import com.kh.helloffice.NoAccessException;
import com.kh.helloffice.admin.entity.CategoryDto;
import com.kh.helloffice.admin.service.CategoryService;
import com.kh.helloffice.board.entity.BoardDto;
import com.kh.helloffice.member.entity.DeptEmp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/board/{boardNo}")
public class CategoryController {

    private final CategoryService service;

    @GetMapping("/categories")
    public String boardManagePage(@PathVariable long boardNo,
                                  HttpSession session,
                                  Model model) throws Exception {
        if(checkAdminPermission(session, boardNo)){
            List<BoardDto> categoryList = service.getCategoryList(boardNo);
            model.addAttribute("categoryList", categoryList);
        }else throw new NoAccessException("접근 권한이 없습니다.");

        return "admin/category/main";
    }

    @GetMapping("/category")
    public String newCategoryPage(@PathVariable long boardNo,
                                  HttpSession session,
                                  Model model) throws Exception {
        if(checkAdminPermission(session, boardNo)){
           List<DeptEmp> empList = service.getEmpList(boardNo);
           model.addAttribute("empList", empList);
        }else throw new NoAccessException("접근 권한이 없습니다.");
        return "admin/category/add";
    }

    @PostMapping("/category")
    public String addCategory(@PathVariable long boardNo, CategoryDto category) throws Exception {
        category.setBoardNo(boardNo);
        int result = service.addCategory(category);
        if(result > 0) return "redirect:/admin/board/" + boardNo + "/categories";
        return "redirect:/admin/board/" + boardNo + "/category";
    }

    @GetMapping("/category/{seq}")
    public String categoryEditPage(@PathVariable long boardNo,
                                   @PathVariable long seq,
                                   HttpSession session,
                                   Model model) throws Exception {
        if(checkAdminPermission(session, boardNo)){
            CategoryDto detail = service.getCategoryDetail(seq);
            List<DeptEmp> empList = service.getEmpList(boardNo);

            model.addAttribute("detail", detail);
            model.addAttribute("empList", empList);
        }else throw new NoAccessException("접근 권한이 없습니다.");
        return "admin/category/edit";
    }

    @PatchMapping("/category/{seq}")
    public String editCategory(@PathVariable long boardNo,
                               @PathVariable long seq,
                               CategoryDto category) throws Exception {
        category.setSeq(seq);
        int result = service.editCategory(category);
        if(result > 0) return "redirect:/admin/board/" + boardNo + "/categories";
        return "redirect:/admin/board/" + boardNo + "/category/" + seq;
    }

    @DeleteMapping("/category/{seq}")
    public int deleteCategory(@PathVariable long seq) throws Exception {
        return service.deleteCategory(seq);
    }

    private boolean checkAdminPermission(HttpSession session, long boardNo) throws Exception {
        DeptEmp user = (DeptEmp) session.getAttribute("loginEmp");
        if(user.getAdminLevel() == 1) return false;

        long depNo = user.getDepNo();
        return service.checkAdminPermission(depNo, boardNo);
    }
}
