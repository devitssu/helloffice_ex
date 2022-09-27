package com.kh.helloffice.admin.entity;

import lombok.Data;


@Data
public class CategoryDto {
    private long seq;
    private String name;
    private long boardNo;
    private long depNo;
    private long[] empNoList;
}
