package com.kh.helloffice.admin.service;

import com.kh.helloffice.admin.dao.CategoryDao;
import com.kh.helloffice.admin.entity.CategoryDto;
import com.kh.helloffice.board.entity.BoardDto;
import com.kh.helloffice.member.entity.DeptEmp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService{

    private final CategoryDao dao;
    @Override
    public boolean checkAdminPermission(long depNo, long boardNo) throws Exception {
        long result = dao.getDepNo(boardNo);
        if(depNo == result) return true;
        return false;
    }

    @Override
    public List<BoardDto> getCategoryList(long boardNo) throws Exception {
        long depNo = dao.getDepNo(boardNo);
        return dao.getCategoryList(depNo);
    }

    @Override
    public List<DeptEmp> getEmpList(long boardNo) throws Exception {
        long depNo = dao.getDepNo(boardNo);
        return dao.getEmpList(depNo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addCategory(CategoryDto category) throws Exception {
        long depNo = dao.getDepNo(category.getBoardNo());
        category.setDepNo(depNo);
        int result = dao.addCategory(category);

        if(result != 0){
            return dao.addCategoryUser(category);
        }
        return 0;
    }

    @Override
    public CategoryDto getCategoryDetail(long seq) throws Exception {
        return dao.getCategoryDetail(seq);
    }

    @Transactional
    @Override
    public int editCategory(CategoryDto category) throws Exception {
        // 원래 권한을 가지고 있던 직원들
        List<Long> origin = dao.getCategoryUser(category.getSeq());

        // 수정해서 넘어온 직원들
        long[] empNoArr = category.getEmpNoList();

        // 원래x 수정o -> 새로 추가된 직원들
        long[] arrToAdd = Arrays.stream(empNoArr)
                .filter(n -> !origin.contains(n))
                .toArray();

        // 원래o 수정x -> 권한 삭제할 직원들
        List<Long> empNoList = LongStream.of(empNoArr).boxed().collect(Collectors.toList());
        List<Long> listToDelete = origin.stream()
                .filter(o -> !empNoList.contains(o))
                .collect(Collectors.toList());

        int editResult = dao.editCategory(category);
        int addResult = 0;
        int deleteResult = 0;
        if(arrToAdd.length != 0){
            category.setEmpNoList(arrToAdd);
            addResult = dao.addCategoryUser(category);
        }else {
            addResult = 1;
        }

        if(listToDelete.size() != 0) {
            long[] arrToDelete = new long[listToDelete.size()];
            for (int i = 0; i < listToDelete.size(); i++) {
                arrToDelete[i] = listToDelete.get(i);
            }
            category.setEmpNoList(arrToDelete);
            deleteResult = dao.deleteCategoryUser(category);
        }else{
            deleteResult = 1;
        }
        return addResult * deleteResult * editResult;
    }

    @Override
    public int deleteCategory(long seq) throws Exception {
        return dao.deleteCategory(seq);
    }
}
