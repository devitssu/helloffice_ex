package com.kh.helloffice.admin.service;

import com.kh.helloffice.admin.entity.CategoryDto;
import com.kh.helloffice.board.entity.BoardDto;
import com.kh.helloffice.member.entity.DeptEmp;

import java.util.List;

public interface CategoryService {
    boolean checkAdminPermission(long depNo, long boardNo) throws Exception;

    List<BoardDto> getCategoryList(long boardNo) throws Exception;

    List<DeptEmp> getEmpList(long boardNo) throws Exception;

    int addCategory(CategoryDto category) throws Exception;

    CategoryDto getCategoryDetail(long seq) throws Exception;

    int editCategory(CategoryDto category) throws Exception;

    int deleteCategory(long seq) throws Exception;
}
