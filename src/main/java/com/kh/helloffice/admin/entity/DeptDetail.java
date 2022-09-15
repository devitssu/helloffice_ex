package com.kh.helloffice.admin.entity;

import com.kh.helloffice.member.entity.DeptEmp;
import lombok.Data;

import java.util.List;
@Data
public class DeptDetail {

    private String depName;
    private int totalNum;
    private List<DeptEmp> empList;
}
