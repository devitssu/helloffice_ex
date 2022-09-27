package com.kh.helloffice.admin.dao;

import com.kh.helloffice.admin.entity.CategoryDto;
import com.kh.helloffice.board.entity.BoardDto;
import com.kh.helloffice.member.entity.DeptEmp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j

public class CategoryDaoImpl implements CategoryDao{

    private final SqlSession session;

    @Override
    public long getDepNo(long boardNo) throws Exception {
        return session.selectOne("board.getBoardDepNo", boardNo);
    }

    @Override
    public List<BoardDto> getCategoryList(long depNo) throws Exception {
        return session.selectList("board.getCategoryList", depNo);
    }

    @Override
    public List<DeptEmp> getEmpList(long depNo) throws Exception {
        return session.selectList("admin.getEmpListByDepNo", depNo);
    }

    @Override
    public int addCategory(CategoryDto category) throws Exception {
        return session.insert("admin.insertCategory", category);
    }

    @Override
    public int addCategoryUser(CategoryDto category) throws Exception {
        return session.insert("admin.insertCategoryUser", category);
    }

    @Override
    public CategoryDto getCategoryDetail(long seq) throws Exception {
        return session.selectOne("admin.getCategoryDetail",seq);
    }

    @Override
    public List<Long> getCategoryUser(long seq) throws Exception {
        return session.selectList("admin.getCategoryUser", seq);
    }

    @Override
    public int deleteCategoryUser(CategoryDto category) throws Exception {
        return session.delete("admin.deleteCategoryUser", category);
    }

    @Override
    public int editCategory(CategoryDto category) throws Exception {
        return session.update("admin.editCategoryName", category);
    }

    @Override
    public int deleteCategory(long seq) throws Exception {
        int result1 = session.delete("admin.deleteCategoryUserAll", seq);
        int result2 = session.delete("admin.deleteCategory", seq);
        return result1 * result2;
    }
}
