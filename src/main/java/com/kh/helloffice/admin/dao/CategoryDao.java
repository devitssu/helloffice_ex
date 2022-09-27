package com.kh.helloffice.admin.dao;

import com.kh.helloffice.admin.entity.CategoryDto;
import com.kh.helloffice.board.entity.BoardDto;
import com.kh.helloffice.member.entity.DeptEmp;

import java.util.List;

public interface CategoryDao {
    long getDepNo(long boardNo) throws Exception;

    List<BoardDto> getCategoryList(long depNo) throws Exception;

    List<DeptEmp> getEmpList(long depNo) throws Exception;

    int addCategory(CategoryDto category) throws Exception;

    int addCategoryUser(CategoryDto category) throws Exception;

    CategoryDto getCategoryDetail(long seq) throws Exception;

    List<Long> getCategoryUser(long seq) throws Exception;

    int deleteCategoryUser(CategoryDto category) throws Exception;

    int editCategory(CategoryDto category) throws Exception;

    int deleteCategory(long seq) throws Exception;
}
