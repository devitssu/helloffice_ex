package com.kh.helloffice.board.entity;

import lombok.Data;

@Data
public class BoardDto {
	
	private long seq;
	private String name;
	private long depNo;
	private long root;
	private long ref;
	private long depth;

}
